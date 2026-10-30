package atf.autoex.tests.e2e.failures;

import atf.autoex.tests.support.business.AutoExBusinessFault;
import atf.autoex.tests.support.business.AutoExBusinessFault.Point;
import atf.testkit.AtfE2eTest;
import atf.testkit.channel.AtfTestChannel;
import atf.testkit.channel.TestChannel;
import atf.testkit.evidence.*;
import atf.testkit.scope.TestRunScope;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.platform.engine.DiscoverySelector;
import org.junit.platform.launcher.core.*;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.suite.api.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.platform.engine.discovery.DiscoverySelectors.*;

/** Explicit live acceptance probe. Nested negative E2E fixtures are deliberately red; this probe verifies their exact cause and artifacts. */
public abstract class AutoExBusinessFailureAcceptance {
    private static final Map<String, Observation> OBSERVATIONS = new LinkedHashMap<>();
    private static final List<Map<String,Object>> RESULTS = new ArrayList<>();
    private static final String OPT_IN = "atf.business.failures.live.enabled";
    private AutoExBusinessFailureAcceptance() { }

    public static void main(String[] args) throws Exception {
        assertEquals("true", System.getProperty(OPT_IN), "controlled business failures require their own live opt-in");
        assertEquals("true", System.getProperty("atf.autoex.e2e.enabled"), "the consumer E2E opt-in remains independent");
        String mode = args.length == 0 ? "all" : args[0];
        assertTrue(Set.of("all","individual","suite").contains(mode));
        String selectedCase=System.getProperty("atf.business.probe.case");
        assertTrue(selectedCase==null || Set.of("AccountUi","AccountApi","CartUi","CartApi","PurchaseUi","PurchaseApi").contains(selectedCase), "unknown controlled fixture");
        if (!mode.equals("suite")) {
            for (Class<?> owner : List.of(AccountUi.class, AccountApi.class, CartUi.class, CartApi.class, PurchaseUi.class, PurchaseApi.class)) {
                if (!System.getProperty("atf.business.probe.case", owner.getSimpleName()).equals(owner.getSimpleName())) continue;
                launch("individual-"+owner.getSimpleName(), selectClass(owner), 1, 0);
                boolean api = owner.getAnnotation(AtfTestChannel.class).value() == TestChannel.UI_API;
                String method = owner.getSimpleName().replace("Api", "").replace("Ui", "").toLowerCase(Locale.ROOT);
                launch("following-"+owner.getSimpleName(), selectMethod(api ? HealthyUi.class : HealthyApi.class,
                        method, atf.autoex.runtime.AutoExE2eRun.class), 0, 1);
            }
        }
        if (!mode.equals("individual")) launch("suite", selectClass(FixtureSuite.class), 6, 6);
        atf.console.AtfConsoleReporter.configuredStdout().info("BUSINESS_FAILURE_ACCEPTANCE=PASS; launches="+RESULTS.size());
    }

    private static void launch(String label, DiscoverySelector selector, long failures, long successes) throws Exception {
        Path root = Path.of(System.getProperty("atf.business.probe.root", ".")).toAbsolutePath().normalize();
        assertTrue(Files.isRegularFile(root.resolve("atf-ui/pom.xml")), "launch from the reactor root or pass atf.business.probe.root");
        Path directory = Files.createDirectories(root.resolve("artifacts/reports/business-failures/autoex/"+label+"-"+UUID.randomUUID()));
        var yaml = new ObjectMapper(new YAMLFactory());
        var document = yaml.readTree(atf.testkit.files.AtfTestResourceReader.readResourceUtf8(AutoExBusinessFailureAcceptance.class, "atf-auto-ex.yaml"));
        var consumer = (ObjectNode) document.path("automationExercise");
        ((ObjectNode) consumer.path("reports")).put("uiBaseDirectory", root.relativize(directory.resolve("reports")).toString());
        Path configuration = directory.resolve("consumer.yaml"); yaml.writeValue(configuration.toFile(), document);
        String previous = System.getProperty("atf.autoex.config");
        OBSERVATIONS.clear();
        var listener = new SummaryGeneratingListener();
        try {
            System.setProperty("atf.autoex.config", configuration.toString());
            LauncherFactory.create().execute(LauncherDiscoveryRequestBuilder.request().selectors(selector)
                    .configurationParameter("junit.jupiter.execution.parallel.enabled", "false").build(), listener);
        } finally { if (previous == null) System.clearProperty("atf.autoex.config"); else System.setProperty("atf.autoex.config", previous); }
        var result = listener.getSummary();
        result.getFailures().forEach(f -> atf.console.AtfConsoleReporter.configuredStdout().info("OBSERVED_CAUSE="+f.getException().getClass().getName()+": "+f.getException().getMessage()));
        var row = new LinkedHashMap<String,Object>();
        row.put("launch",label); row.put("expectedFailures",failures); row.put("failures",result.getTestsFailedCount());
        row.put("successes",result.getTestsSucceededCount()); row.put("skipped",result.getTestsSkippedCount());
        List<Path> reports;
        try (var files = Files.walk(directory)) { reports = files.filter(Files::isRegularFile)
                .filter(p -> p.toString().endsWith(".html") && !p.toString().contains(".atf-extent-session-")).toList(); }
        row.put("reports", reports.stream().map(Path::toString).toList()); RESULTS.add(row);
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(root.resolve("artifacts/reports/business-failures/autoex/acceptance-results.json").toFile(),RESULTS);
        assertEquals(0, result.getContainersFailedCount(), label+": containers");
        assertEquals(0, result.getTestsSkippedCount(), label+": no skipped acceptance fixture");
        assertEquals(failures,result.getTestsFailedCount(),label+": expected controlled failures only");
        assertEquals(successes,result.getTestsSucceededCount(),label+": healthy successors");
        assertEquals(failures+successes, OBSERVATIONS.size(),label+": every invocation observed");
        assertEquals(1,reports.size(),label+": exactly one HTML per launch");
        String html=Files.readString(reports.getFirst()).replaceAll("(?is)<script\\b[^>]*>.*?</script>|<style\\b[^>]*>.*?</style>", "");
        for(var observed:OBSERVATIONS.values()) {
            atf.console.AtfConsoleReporter.configuredStdout().info("MUTATION_WIRE="+observed.wire);
            observed.verify();
            if(observed.primary!=null) assertTrue(result.getFailures().stream().anyMatch(f -> f.getException()==observed.primary),
                    label+": JUnit keeps the exact original business cause");
        }
        if(failures>0) {
            assertTrue(html.contains("data-atf-evidence-status=\"fail\""),label+": HTML preserves FAIL");
            assertTrue(hasFailureMarker(html),label+": original failed DOM was captured before cleanup");
        }
        row.put("observations",OBSERVATIONS.entrySet().stream().map(entry -> {
            var observed=entry.getValue(); var detail=new LinkedHashMap<String,Object>();
            detail.put("fixture",entry.getKey()); detail.put("point",observed.point.name()); detail.put("negative",observed.negative);
            detail.put("contextCloses",observed.nativeCloses.get()); detail.put("registrations",observed.registrations.get());
            detail.put("cartAdds",observed.additions.get()); detail.put("orders",observed.payments.get());
            detail.put("deletionRequestEvents",observed.deletions.get()); detail.put("deletionNavigations",observed.deletionNavigations.get());
            detail.put("cause",observed.primary==null?"":observed.primary.getClass().getName()); return detail;
        }).toList());
        row.put("verified",true);
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(directory.resolve("acceptance.json").toFile(),row);
        new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(root.resolve("artifacts/reports/business-failures/autoex/acceptance-results.json").toFile(),RESULTS);
        atf.console.AtfConsoleReporter.configuredStdout().info("VERIFIED_LAUNCH="+label+"; failures="+failures+"; successes="+successes+"; report="+reports.getFirst());
    }

    private static boolean hasFailureMarker(String html) throws Exception {
        var images=Pattern.compile("data:image/png;base64,([A-Za-z0-9+/=]+)").matcher(html);
        while(images.find()) {
            var image=ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(images.group(1))));
            if(image==null)continue;
            int magenta=0;
            for(int y=0;y<Math.min(image.getHeight(),160);y++) for(int x=0;x<Math.min(image.getWidth(),350);x++)
                if((image.getRGB(x,y)&0xFFFFFF)==0xFF00FF && ++magenta>=500)return true;
        }
        return false;
    }

    private static void execute(Point point, boolean negative, atf.autoex.runtime.AutoExE2eRun run, String key) {
        var observation = new Observation(point,negative,run.page(),run.configuration().baseUrl()); observation.auto=run;
        OBSERVATIONS.put(key,observation);
        var fault = new AutoExBusinessFault(run.page(),point); observation.fault=fault;
        var session = negative ? fault.session(run) : (atf.autoex.support.journey.AutoExPurchaseSession) run;
        try {
            switch(point) {
                case ACCOUNT -> atf.autoex.support.journey.AutoExAccountJourney.registersAndDeletesOwnAccount(session);
                case CART -> atf.autoex.support.cart.AutoExCartJourney.preservesGuestCartAfterLogin(session);
                case PURCHASE -> atf.autoex.support.journey.AutoExPurchaseJourney.execute(session);
            }
            if(negative)throw new AssertionError("Controlled business failure was not reached: "+point);
        } catch(RuntimeException|Error primary) { observation.primary=primary; throw primary; }
    }
    public abstract static class Failure {
        protected abstract Point point();
        @Test void businessFailure(atf.autoex.runtime.AutoExE2eRun run) { execute(point(),true,run,getClass().getSimpleName()); }
    }
    public abstract static class Healthy {
        @Test @Order(1) void account(atf.autoex.runtime.AutoExE2eRun run) { execute(Point.ACCOUNT,false,run,getClass().getSimpleName()+"-account"); }
        @Test @Order(2) void cart(atf.autoex.runtime.AutoExE2eRun run) { execute(Point.CART,false,run,getClass().getSimpleName()+"-cart"); }
        @Test @Order(3) void purchase(atf.autoex.runtime.AutoExE2eRun run) { execute(Point.PURCHASE,false,run,getClass().getSimpleName()+"-purchase"); }
    }

    private static final class Observation {
        final Point point; final boolean negative; final Page page;
        final AtomicInteger nativeCloses=new AtomicInteger(), registrations=new AtomicInteger(), additions=new AtomicInteger(), payments=new AtomicInteger(), deletions=new AtomicInteger();
        final AtomicInteger deletionNavigations=new AtomicInteger(), unexpectedDeletionRequests=new AtomicInteger();
        final List<String> wire=new ArrayList<>();
        Throwable primary;
        AutoExBusinessFault fault; atf.autoex.runtime.AutoExE2eRun auto;
        Observation(Point point,boolean negative,Page page,java.net.URI configuredOrigin) {
            String origin=configuredOrigin.resolve("/").toString();
            this.point=point;this.negative=negative;this.page=page;
            assertEquals("about:blank",page.url(),"fresh browser per business invocation");
            page.context().onClose(ignored->nativeCloses.incrementAndGet());
            page.onRequest(request->{
                if(!request.url().startsWith(origin))return;
                String path=java.net.URI.create(request.url().split("[?#]",2)[0]).getPath();
                if(request.method().equals("POST") || path.startsWith("/add_to_cart/") || path.equals("/delete_account"))wire.add(request.method()+" "+path);
                String form=Objects.toString(request.postData(), "");
                if(request.method().equals("POST") && path.equals("/signup") && form.contains("password=") && form.contains("address1="))registrations.incrementAndGet();
                if(path.startsWith("/add_to_cart/"))additions.incrementAndGet();
                if(request.method().equals("POST") && path.equals("/payment"))payments.incrementAndGet();
                if(path.equals("/delete_account")) {
                    deletions.incrementAndGet();
                    if(request.isNavigationRequest())deletionNavigations.incrementAndGet();
                    boolean expectedKind=request.isNavigationRequest()?request.resourceType().equals("document"):request.resourceType().equals("other");
                    if(!request.method().equals("GET") || !expectedKind)unexpectedDeletionRequests.incrementAndGet();
                }
            });
        }
        void verify() {
            assertEquals(1,nativeCloses.get(),point+": real context closes exactly once");
            assertTrue(page.isClosed(),point+": no surviving browser page");
            assertEquals(negative,primary!=null,point+": original failure only in negative fixture");
            assertEquals(negative,fault.injected(),point+": intended DOM failure was reached");
            assertEquals(1,registrations.get(),point+": one owned account creation");
            assertEquals(1,deletionNavigations.get(),point+": exactly one owned-account GET document navigation, never replayed");
            assertEquals(0,unexpectedDeletionRequests.get(),point+": supplementary events retain their explicit OTHER classification");
            assertTrue(auto.accountLifecycle().deletionWasConfirmed(),point+": owned cleanup is confirmed");
            if(negative) {
                assertEquals(1,fault.cleanupCalls(),point+": original registered failure cleanup invoked once");
                assertEquals("UiCheckpointFailure",primary.getClass().getSimpleName(),point+": real checkpoint cause");
                String expectedCheckpoint=switch(point) {
                    case ACCOUNT -> "the disposable customer signs in with the same identity";
                    case CART -> "signing in preserves the guest cart and its calculated total";
                    case PURCHASE -> "the demo checkout confirms the order";
                };
                assertTrue(primary.getMessage().contains(expectedCheckpoint),point+": the exact intended business checkpoint fails");
                assertEquals(0,primary.getSuppressed().length,point+": no hidden cleanup/report failure");
            }
            assertEquals(point==Point.ACCOUNT?0:1,additions.get(),point+": cart add is never replayed");
            assertEquals(point==Point.PURCHASE?1:0,payments.get(),point+": payment runs only at the purchase boundary");
        }
    }
    @Order(1) @EnabledIfSystemProperty(named=OPT_IN,matches="true")
    @AtfTestChannel(TestChannel.UI)
    @AtfUiEvidence(screenshots=AtfUiScreenshotPolicy.FINAL_AND_FAILURE)
    @atf.autoex.tests.extension.AutoExE2eTest @atf.autoex.tests.extension.AutoExNetworkDocuments({"/"})
    public static class AccountUi extends Failure { @Override protected Point point() { return Point.ACCOUNT; } }
    @Order(2) @EnabledIfSystemProperty(named=OPT_IN,matches="true")
    @AtfTestChannel(TestChannel.UI_API)
    @AtfUiEvidence(screenshots=AtfUiScreenshotPolicy.FINAL_AND_FAILURE)
    @atf.autoex.tests.extension.AutoExE2eTest @atf.autoex.tests.extension.AutoExNetworkDocuments({"/"})
    public static class AccountApi extends Failure { @Override protected Point point() { return Point.ACCOUNT; } }
    @Order(3) @EnabledIfSystemProperty(named=OPT_IN,matches="true")
    @AtfTestChannel(TestChannel.UI)
    @AtfUiEvidence(screenshots=AtfUiScreenshotPolicy.FINAL_AND_FAILURE)
    @atf.autoex.tests.extension.AutoExE2eTest @atf.autoex.tests.extension.AutoExNetworkDocuments({"/"})
    public static class CartUi extends Failure { @Override protected Point point() { return Point.CART; } }
    @Order(4) @EnabledIfSystemProperty(named=OPT_IN,matches="true")
    @AtfTestChannel(TestChannel.UI_API)
    @AtfUiEvidence(screenshots=AtfUiScreenshotPolicy.FINAL_AND_FAILURE)
    @atf.autoex.tests.extension.AutoExE2eTest @atf.autoex.tests.extension.AutoExNetworkDocuments({"/"})
    public static class CartApi extends Failure { @Override protected Point point() { return Point.CART; } }
    @Order(5) @EnabledIfSystemProperty(named=OPT_IN,matches="true")
    @AtfTestChannel(TestChannel.UI)
    @AtfUiEvidence(screenshots=AtfUiScreenshotPolicy.FINAL_AND_FAILURE)
    @atf.autoex.tests.extension.AutoExE2eTest @atf.autoex.tests.extension.AutoExNetworkDocuments({"/"})
    public static class PurchaseUi extends Failure { @Override protected Point point() { return Point.PURCHASE; } }
    @Order(6) @EnabledIfSystemProperty(named=OPT_IN,matches="true")
    @AtfTestChannel(TestChannel.UI_API)
    @AtfUiEvidence(screenshots=AtfUiScreenshotPolicy.FINAL_AND_FAILURE)
    @atf.autoex.tests.extension.AutoExE2eTest @atf.autoex.tests.extension.AutoExNetworkDocuments({"/"})
    public static class PurchaseApi extends Failure { @Override protected Point point() { return Point.PURCHASE; } }
    @Order(7) @EnabledIfSystemProperty(named=OPT_IN,matches="true")
    @AtfTestChannel(TestChannel.UI)
    @AtfUiEvidence(screenshots=AtfUiScreenshotPolicy.FINAL_AND_FAILURE)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @atf.autoex.tests.extension.AutoExE2eTest @atf.autoex.tests.extension.AutoExNetworkDocuments({"/"})
    public static class HealthyUi extends Healthy { }
    @Order(8) @EnabledIfSystemProperty(named=OPT_IN,matches="true")
    @AtfTestChannel(TestChannel.UI_API)
    @AtfUiEvidence(screenshots=AtfUiScreenshotPolicy.FINAL_AND_FAILURE)
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    @atf.autoex.tests.extension.AutoExE2eTest @atf.autoex.tests.extension.AutoExNetworkDocuments({"/"})
    public static class HealthyApi extends Healthy { }
    @Suite @AtfE2eTest
    @SelectClasses({AccountUi.class,AccountApi.class,CartUi.class,CartApi.class,PurchaseUi.class,PurchaseApi.class,HealthyUi.class,HealthyApi.class})
    @ConfigurationParameter(key=TestRunScope.CONFIGURATION_PARAMETER,value="suite")
    @ConfigurationParameter(key="junit.jupiter.testclass.order.default",value="org.junit.jupiter.api.ClassOrderer$OrderAnnotation")
    @ConfigurationParameter(key="junit.jupiter.execution.parallel.enabled",value="false")
    public static class FixtureSuite { }
}
