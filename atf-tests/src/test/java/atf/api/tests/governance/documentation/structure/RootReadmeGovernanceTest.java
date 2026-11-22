package atf.api.tests.governance.documentation.structure;

import atf.api.tests.governance.documentation.DocumentationGovernanceSupport;
import atf.api.tests.governance.documentation.language.DocumentationLanguagePolicy;
import atf.testkit.AtfComponentTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static atf.api.tests.governance.AtfGovernanceTestSupport.locateProjectRoot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Protects the local overview, factual evidence boundaries, and three complementary diagram perspectives. */
@AtfComponentTest
public class RootReadmeGovernanceTest {
    private static final int MAX_LINES = 240;
    private static final int FIRST_SCREEN_LINES = 20;
    private static final Pattern H1 = Pattern.compile("^#\\s+[^#].*$");
    private static final Pattern H2 = Pattern.compile("^##\\s+(.+?)\\s*$");
    private static final Pattern MODULE = Pattern.compile("<module>\\s*([^<]+?)\\s*</module>");
    private static final Pattern OTHER_ATF_PROJECT = Pattern.compile(
            "(?i)(?<![\\p{L}\\p{N}])(?:ATF[-_ ]?J(?:0|[2-9]|[1-9]\\d+)|atf[_-]j(?:0|[2-9]|[1-9]\\d+))(?![\\p{L}\\p{N}])");
    private static final Pattern LIBRARY_MENTION = Pattern.compile(
            "(?i)\\b(?:consumed\\s+|shared\\s+)?libraries?\\b|\\bbibliotec\\w*\\b|\\bJAR/POM\\b|lib/README\\.md");
    private static final Pattern LIBRARY_INDEX_LINK = Pattern.compile("\\[[^\\]]+\\]\\(lib/README\\.md\\)");
    private static final Pattern SCORE_OR_RUN_RESULT = Pattern.compile(
            "(?i)\\b\\d{1,3}\\s*/\\s*\\d{1,3}\\b|\\b(?:score|scorecard|rating|nota)\\b");
    private static final Pattern MERMAID_FENCE = Pattern.compile("^\\s*```mermaid\\s*$");

    private static final List<String> LOCAL_MODULES = List.of(
            "atf-validation", "atf-ui", "atf-debug-ui",
            "atf-test-practice-shop", "atf-test-auto-ex", "atf-tests");

    @Test
    void liveRootReadmeExplainsLocalOwnershipEvidenceAndVerification() throws Exception {
        Path root = locateProjectRoot();
        String readme = Files.readString(root.resolve("README.md"));
        List<DocumentationGovernanceSupport.Violation> violations =
                inspect(root, readme, DocumentationLanguagePolicy.read(root), modules(root));
        DocumentationGovernanceSupport.assertNoViolations("root README governance", violations);
    }

    @Test
    void equivalentEnglishAndRomanianOverviewsPass(@TempDir Path root) {
        assertEquals(List.of(), inspect(root, fixture("en"), "en", Set.copyOf(LOCAL_MODULES)));
        assertEquals(List.of(), inspect(root, fixture("ro"), "ro", Set.copyOf(LOCAL_MODULES)));
    }

    @Test
    void siblingProjectNamesAndRunScoresAreRejected(@TempDir Path root) {
        String broken = fixture("en")
                .replace("Java/Maven project for testing", "Java/Maven ATF-J0 project for testing")
                .replace("## At a glance", "Recent score: 179/179.\n\n## At a glance");
        var violations = inspect(root, broken, "en", Set.copyOf(LOCAL_MODULES));
        assertTrue(violations.stream().anyMatch(v -> v.code().equals("DOC-ROOT-SCOPE")), violations.toString());
        assertTrue(violations.stream().anyMatch(v -> v.code().equals("DOC-ROOT-RUN-RESULT")), violations.toString());
    }

    @Test
    void libraryIndexCannotMoveIntoTheOpeningOrRepeat(@TempDir Path root) {
        String opening = fixture("en")
                .replace("## At a glance", "This checkout uses shared libraries.\n\n## At a glance");
        var openingViolations = inspect(root, opening, "en", Set.copyOf(LOCAL_MODULES));
        assertTrue(openingViolations.stream().anyMatch(v -> v.code().equals("DOC-ROOT-LIBRARY-POSITION")),
                openingViolations.toString());

        String repeated = fixture("en") + "\n[More libraries](lib/README.md)\n";
        var repeatedViolations = inspect(root, repeated, "en", Set.copyOf(LOCAL_MODULES));
        assertTrue(repeatedViolations.stream().anyMatch(v -> v.code().equals("DOC-ROOT-LIBRARY-INDEX")),
                repeatedViolations.toString());
    }

    @Test
    void missingLocalModuleAndOversizedOverviewAreRejected(@TempDir Path root) {
        String missing = fixture("en").replace("| atf-debug-ui | Read-only report inspection |\n", "");
        var missingViolations = inspect(root, missing, "en", Set.copyOf(LOCAL_MODULES));
        assertTrue(missingViolations.stream().anyMatch(v -> v.code().equals("DOC-ROOT-MODULES")),
                missingViolations.toString());

        String oversized = fixture("en") + "\n".repeat(MAX_LINES + 1);
        var sizeViolations = inspect(root, oversized, "en", Set.copyOf(LOCAL_MODULES));
        assertTrue(sizeViolations.stream().anyMatch(v -> v.code().equals("DOC-ROOT-SIZE")),
                sizeViolations.toString());
    }

    @Test
    void projectPurposeReportContractAndConnectedFlowAreRequired(@TempDir Path root) {
        String vague = fixture("en").replace("Java/Maven project for testing", "Java project");
        var purposeViolations = inspect(root, vague, "en", Set.copyOf(LOCAL_MODULES));
        assertTrue(purposeViolations.stream().anyMatch(v -> v.code().equals("DOC-ROOT-PURPOSE")),
                purposeViolations.toString());

        String missingReportRule = fixture("en").replace("collapsible", "visible");
        var reportViolations = inspect(root, missingReportRule, "en", Set.copyOf(LOCAL_MODULES));
        assertTrue(reportViolations.stream().anyMatch(v -> v.code().equals("DOC-ROOT-REPORT-CONTRACT")),
                reportViolations.toString());

        String disconnected = fixture("en").replace("  Evidence -->|renders as| Report", "  Report -->|renders as| Evidence");
        var flowViolations = inspect(root, disconnected, "en", Set.copyOf(LOCAL_MODULES));
        assertTrue(flowViolations.stream().anyMatch(v -> v.code().equals("DOC-ROOT-FLOW-CONTENT")),
                flowViolations.toString());
    }

    @Test
    void inventedValidationRouteAndRepeatedPerspectiveAreRejected(@TempDir Path root) {
        String invented = fixture("en").replace("  API -->|returns responses| Checks",
                "  API -->|validates| Validation\n  Validation -->|passes| Checks");
        assertTrue(inspect(root, invented, "en", Set.copyOf(LOCAL_MODULES)).stream()
                .anyMatch(v -> v.code().equals("DOC-ROOT-FLOW-CONTENT")));
        String repeated = fixture("en") + "\n" + String.valueOf((char) 96).repeat(3)
                + "mermaid\nflowchart LR\n  A -->|duplicates| B\n" + String.valueOf((char) 96).repeat(3);
        assertTrue(inspect(root, repeated, "en", Set.copyOf(LOCAL_MODULES)).stream()
                .anyMatch(v -> v.code().equals("DOC-ROOT-FLOW")));
    }

    @Test
    void edgeLabelsMayBeLocalizedWithoutChangingOwnership(@TempDir Path root) {
        String localized = fixture("ro").replace("|returns responses|", "|întoarce răspunsuri|")
                .replace("|binds expectations|", "|asociază așteptări|");
        assertEquals(List.of(), inspect(root, localized, "ro", Set.copyOf(LOCAL_MODULES)));
    }

    static List<DocumentationGovernanceSupport.Violation> inspect(
            Path root, String markdown, String language, Set<String> declaredModules) {
        var violations = new ArrayList<DocumentationGovernanceSupport.Violation>();
        List<String> lines = markdown.lines().toList();
        Path readme = root.resolve("README.md");

        List<Integer> titles = matchingLines(lines, H1);
        if (titles.size() != 1 || !lines.get(titles.isEmpty() ? 0 : titles.getFirst() - 1).equals("# ATF J1")) {
            add(violations, root, readme, "DOC-ROOT-TITLE", 1,
                    "root README must have one ATF J1 title",
                    "the entry page identifies this project directly",
                    "keep one first-level title named ATF J1");
        }
        if (lines.size() > MAX_LINES) {
            add(violations, root, readme, "DOC-ROOT-SIZE", MAX_LINES + 1,
                    "root README has " + lines.size() + " lines; maximum is " + MAX_LINES,
                    "the root page is a concise route to owned documentation",
                    "move detailed contracts and historical evidence to their owner documents");
        }

        List<String> expectedHeadings = headings(language);
        List<Integer> headingLines = matchingLines(lines, H2);
        List<String> observedHeadings = headingLines.stream()
                .map(line -> H2.matcher(lines.get(line - 1)).replaceFirst("$1"))
                .toList();
        if (!observedHeadings.equals(expectedHeadings)) {
            add(violations, root, readme, "DOC-ROOT-STRUCTURE", 1,
                    "root README section order does not match the compact local overview",
                    "the reader sees local purpose, ownership, verification, navigation, then the library index",
                    "keep the required sections in their declared order and the library note last");
        }

        String introduction = beforeFirstSection(lines);
        List<String> purposeTerms = language.equals("ro")
                ? List.of("java/maven", "testarea automată", "practice shop",
                        "automation exercise", "autoex", "fluxuri api", "comportamentul în browser")
                : List.of("java/maven", "project for testing", "practice shop",
                        "automation exercise", "autoex", "api flows", "browser behavior");
        if (!containsAll(introduction, purposeTerms)) {
            add(violations, root, readme, "DOC-ROOT-PURPOSE", 1,
                    "root README does not identify the project purpose and application test scope",
                    "the opening describes this Java/Maven project and its API/browser consumers",
                    "name both local application consumers and the API/browser behavior they exercise");
        }

        String overview = sectionBody(lines, headings(language).getFirst());
        List<String> reportTerms = language.equals("ro")
                ? List.of("html extent", "component assertions", "colapsabil",
                        "testele unitare nu publică rapoarte extent")
                : List.of("extent html", "component assertions", "collapsible",
                        "unit tests do not publish extent reports");
        if (!containsAll(overview, reportTerms)) {
            add(violations, root, readme, "DOC-ROOT-REPORT-CONTRACT", 1,
                    "root overview omits a required report or assertion-step boundary",
                    "readers can distinguish report-producing consumer checks from unit tests",
                    "state Extent HTML output, the collapsible component assertion step, and the unit-test exemption");
        }

        String diagram = mermaidDiagram(lines);
        Set<String> expectedFlow = Set.of("App -> API", "App -> Browser", "API -> Checks",
                "Browser -> Checks", "Checks -> Evidence", "Evidence -> Report", "Report -> Inspector");
        if (!flowEdges(diagram).equals(expectedFlow)
                || !containsAll(diagram, List.of("shop", "autoex", "atf-ui", "extent html", "atf-debug-ui"))) {
            add(violations, root, readme, "DOC-ROOT-FLOW-CONTENT", 1,
                    "root flow does not preserve consumer-owned checks and the evidence path",
                    "API responses and neutral browser observations reach domain checks before report publication",
                    "connect the consumer through API/browser observations and its own checks to Extent inspection; "
                            + "do not invent an automatic atf-validation stage");
        }

        int librarySection = headingLines.isEmpty() ? -1 : headingLines.getLast() - 1;
        List<String> opening = lines.subList(0, Math.min(FIRST_SCREEN_LINES, lines.size()));
        if (opening.stream().anyMatch(line -> LIBRARY_MENTION.matcher(line).find())) {
            add(violations, root, readme, "DOC-ROOT-LIBRARY-POSITION", 1,
                    "library material appears in the first screen",
                    "the first screen explains the local project before the consumed-library index",
                    "move the single library note to the final section");
        }
        if (librarySection >= 0 && lines.subList(0, librarySection).stream()
                .anyMatch(line -> LIBRARY_MENTION.matcher(line).find())) {
            add(violations, root, readme, "DOC-ROOT-LIBRARY-POSITION", 1,
                    "library material appears before the final library-index section",
                    "the main README keeps libraries secondary to local ownership",
                    "move library details to the final index note");
        }
        long indexLinks = LIBRARY_INDEX_LINK.matcher(markdown).results().count();
        if (indexLinks != 1 || librarySection < 0
                || lines.subList(Math.max(0, librarySection), lines.size()).stream()
                        .noneMatch(line -> LIBRARY_INDEX_LINK.matcher(line).find())) {
            add(violations, root, readme, "DOC-ROOT-LIBRARY-INDEX", 1,
                    "root README must contain exactly one final link to lib/README.md",
                    "the consumed-library catalog is discoverable without leading the project overview",
                    "keep one semantic link to lib/README.md in the final section");
        }

        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            Matcher sibling = OTHER_ATF_PROJECT.matcher(line);
            if (sibling.find()) {
                add(violations, root, readme, "DOC-ROOT-SCOPE", index + 1,
                        "root README names another ATF workspace: " + sibling.group(),
                        "the project entry page describes only local J1 ownership",
                        "remove sibling-project identity and keep any dependency description at the library index");
            }
            if (SCORE_OR_RUN_RESULT.matcher(line).find()) {
                add(violations, root, readme, "DOC-ROOT-RUN-RESULT", index + 1,
                        "root README contains a score or run-result snapshot",
                        "normative entry pages describe stable contracts rather than dated results",
                        "move active gaps to feedback and keep run results out of the root README");
            }
        }

        long diagrams = lines.stream().filter(line -> MERMAID_FENCE.matcher(line).matches()).count();
        if (diagrams != 3
                || !sectionBody(lines, headings(language).get(2)).contains("sequenceDiagram")
                || !sectionBody(lines, headings(language).get(4)).contains("flowchart LR")) {
            add(violations, root, readme, "DOC-ROOT-FLOW", 1,
                    "root README does not contain the three complementary overview perspectives",
                    "execution, report lifecycle and governed changes answer different reader questions",
                    "keep one execution flow, one report sequence and one verification flow within the page budget");
        }

        for (String module : declaredModules) {
            long rows = lines.stream().filter(line -> line.startsWith("| " + module + " |")).count();
            if (rows != 1) {
                add(violations, root, readme, "DOC-ROOT-MODULES", 1,
                        module + " appears in " + rows + " module rows",
                        "every root-POM module has one concise ownership row",
                        "list each locally declared module exactly once");
            }
        }
        for (String module : LOCAL_MODULES) {
            if (!declaredModules.contains(module)) {
                add(violations, root, readme, "DOC-ROOT-MODULES", 1,
                        "expected local module is absent from the root POM: " + module,
                        "the overview reflects the actual local reactor",
                        "reconcile the module inventory with the root POM before changing the overview");
            }
        }
        return List.copyOf(violations);
    }

    private static Set<String> modules(Path root) throws IOException {
        Matcher matcher = MODULE.matcher(Files.readString(root.resolve("pom.xml")));
        var modules = new java.util.LinkedHashSet<String>();
        while (matcher.find()) modules.add(matcher.group(1).trim());
        return Set.copyOf(modules);
    }

    private static List<String> headings(String language) {
        if (language.equals("ro")) {
            return List.of("Pe scurt", "Module locale", "Fluxul dovezilor",
                    "Niveluri de testare", "Verificare", "Lecturi suplimentare",
                    "Indexul bibliotecilor consumate");
        }
        return List.of("At a glance", "Local modules", "Local evidence flow",
                "Test layers", "Verify", "Further reading", "Consumed library index");
    }

    private static String fixture(String language) {
        boolean romanian = language.equals("ro");
        String[] rows = romanian
                ? new String[]{"| atf-validation | Validare |", "| atf-ui | Browser |",
                    "| atf-debug-ui | Inspectare raport doar pentru citire |",
                    "| atf-test-practice-shop | Practice Shop |", "| atf-test-auto-ex | AutoEx |",
                    "| atf-tests | Guvernanță |"}
                : new String[]{"| atf-validation | Validation |", "| atf-ui | Browser |",
                    "| atf-debug-ui | Read-only report inspection |",
                    "| atf-test-practice-shop | Practice Shop |", "| atf-test-auto-ex | AutoEx |",
                    "| atf-tests | Governance |"};
        String introduction = romanian
                ? "ATF J1 este un proiect Java/Maven pentru testarea automată a două aplicații demonstrative: "
                        + "Practice Shop și Automation Exercise (AutoEx). Acoperă fluxuri API și comportamentul "
                        + "în browser; fiecare modul deține rezultatele așteptate și verdictul."
                : "ATF J1 is a Java/Maven project for testing two sample applications: "
                        + "Practice Shop and Automation Exercise (AutoEx). It exercises their API flows and "
                        + "browser behavior; each consumer module owns the application's expected results "
                        + "and verdicts.";
        String ownershipSummary = romanian
                ? "Modulele Practice Shop și AutoEx dețin datele aplicației, scenariile și verdictele; "
                        + "atf-validation verifică răspunsurile, iar atf-ui oferă acțiuni Playwright comune."
                : "Practice Shop and AutoEx own application data, journeys, and verdicts; atf-validation "
                        + "checks responses and atf-ui supplies shared Playwright behavior.";
        String reportSummary = romanian
                ? "Suitele cu raportarea în contract publică rapoarte HTML Extent. Verificările auditate "
                        + "de componentă apar în pasul colapsabil Component assertions; testele unitare nu "
                        + "publică rapoarte Extent."
                : "Application suites with a report contract publish Extent HTML. Audited component checks "
                        + "appear in the collapsible Component assertions step; unit tests do not publish "
                        + "Extent reports.";
        String inspectorSummary = romanian
                ? "atf-debug-ui citește rapoartele HTML generate; atf-tests verifică regulile reactorului."
                : "atf-debug-ui reads generated HTML reports; atf-tests checks reactor rules.";
        String detailsTitle = romanian ? "Legendă — flux și limite" : "Legend — flow and boundaries";
        String detailsText = romanian
                ? "Raportul prezintă dovezile scenariilor; inspectorul citește HTML-ul generat."
                : "The report displays scenario evidence; the inspector reads generated HTML.";
        String layerHeader = romanian
                ? "| Nivel | Utilizare | Dovezi |"
                : "| Layer | Use | Evidence |";
        String[] layerRows = romanian
                ? new String[]{"| Unitar | Reguli pure | Fără raport Extent |",
                    "| Componentă | Adaptor controlat | Aserțiuni colapsabile |",
                    "| Integrare | Module locale | Comportament compus |",
                    "| E2E | Browser | Raport randat |",
                    "| Suport | Date de test | Fără verdict independent |"}
                : new String[]{"| Unit | Pure rules | No Extent report |",
                    "| Component | Controlled adapter | Collapsible assertions |",
                    "| Integration | Local modules | Composed behavior |",
                    "| E2E | Browser | Rendered report |",
                    "| Support | Test input | No independent verdict |"};
        String layerIntro = romanian
                ? "Alege nivelul minim care demonstrează comportamentul. Vezi "
                        + "[ghidul de testare](docs/ATF_Testing_Guide.md)."
                : "Choose the smallest layer that proves the behavior. See "
                        + "[testing guide](docs/ATF_Testing_Guide.md).";
        String verifyText = romanian ? "Rulează în staging." : "Run in staging.";
        String architectureLink = romanian
                ? "[Arhitectură](docs/ATF_Architecture.md)"
                : "[Architecture](docs/ATF_Architecture.md)";
        String libraryNote = romanian
                ? "Vezi [indexul bibliotecilor](lib/README.md)."
                : "See the [library index](lib/README.md).";
        var lines = new ArrayList<String>(List.of(
                "# ATF J1", "", introduction, "",
                "## " + headings(language).getFirst(), "",
                "- " + ownershipSummary,
                "- " + reportSummary,
                "- " + inspectorSummary,
                "", "## " + headings(language).get(1), "",
                romanian ? "| Modul | Responsabilitate |" : "| Module | Responsibility |",
                "|---|---|"));
        lines.addAll(List.of(rows));
        String fence = String.valueOf((char) 96).repeat(3);
        String[] diagram = {
                "flowchart LR",
                "  App[\"Shop · AutoEx\"]",
                "  API[\"API execution\"]",
                "  Browser[\"atf-ui\"]",
                "  Checks[\"Domain checks\"]",
                "  Evidence[\"Scenario evidence\"]",
                "  Report[\"Extent HTML\"]",
                "  Inspector[\"atf-debug-ui\"]",
                "  App -->|calls| API", "  App -->|drives| Browser",
                "  API -->|returns responses| Checks", "  Browser -->|returns observations| Checks",
                "  Checks -->|binds expectations| Evidence",
                "  Evidence -->|renders as| Report", "  Report -->|opens in| Inspector"
        };
        lines.addAll(List.of("", "## " + headings(language).get(2), "", fence + "mermaid"));
        lines.addAll(List.of(diagram));
        lines.addAll(List.of(fence, "", "<details>",
                "<summary><strong>" + detailsTitle + "</strong></summary>", "",
                detailsText, "", "</details>", "", fence + "mermaid",
                "sequenceDiagram", "  Test->>Session: publish assertions", fence, "",
                "<details>", "<summary>Legend — report lifecycle</summary>", "", detailsText,
                "", "</details>", "", "## " + headings(language).get(3), "",
                layerHeader, "|---|---|---|"));
        lines.addAll(List.of(layerRows));
        lines.addAll(List.of("", layerIntro, "", "## " + headings(language).get(4), "",
                verifyText, "", fence + "powershell",
                "mvn -B -ntp -pl atf-tests -Dtest=WholeAtfGovernanceSuiteTest test",
                fence, "", fence + "mermaid", "flowchart LR", "  Contract -->|verify| Evidence", fence, "",
                "<details>", "<summary>Legend — governed changes</summary>", "", detailsText,
                "", "</details>", "", "## " + headings(language).get(5), "",
                architectureLink, "", "## " + headings(language).get(6), "", libraryNote));
        return String.join("\n", lines);
    }

    private static String beforeFirstSection(List<String> lines) {
        int end = lines.size();
        for (int index = 0; index < lines.size(); index++) {
            if (H2.matcher(lines.get(index)).matches()) {
                end = index;
                break;
            }
        }
        return String.join("\n", lines.subList(0, end));
    }

    private static String sectionBody(List<String> lines, String title) {
        int start = -1;
        int end = lines.size();
        for (int index = 0; index < lines.size(); index++) {
            Matcher heading = H2.matcher(lines.get(index));
            if (!heading.matches()) continue;
            if (start < 0 && heading.group(1).equals(title)) start = index + 1;
            else if (start >= 0) {
                end = index;
                break;
            }
        }
        return start < 0 ? "" : String.join("\n", lines.subList(start, end));
    }

    private static String mermaidDiagram(List<String> lines) {
        int start = -1;
        for (int index = 0; index < lines.size(); index++) {
            if (MERMAID_FENCE.matcher(lines.get(index)).matches()) {
                start = index + 1;
                break;
            }
        }
        if (start < 0) return "";
        int end = start;
        String closingFence = String.valueOf((char) 96).repeat(3);
        while (end < lines.size() && !lines.get(end).trim().equals(closingFence)) end++;
        return String.join("\n", lines.subList(start, Math.min(end, lines.size())));
    }

    private static Set<String> flowEdges(String diagram) {
        var edges = new java.util.LinkedHashSet<String>();
        Matcher matcher = Pattern.compile("(?m)^\\h*([A-Za-z]\\w*)\\h*-->\\|[^|]+\\|\\h*([A-Za-z]\\w*)")
                .matcher(diagram);
        while (matcher.find()) edges.add(matcher.group(1) + " -> " + matcher.group(2));
        return Set.copyOf(edges);
    }

    private static boolean containsAll(String text, List<String> requiredTerms) {
        String normalized = text.replaceAll("\\s+", " ").toLowerCase(java.util.Locale.ROOT);
        return requiredTerms.stream().allMatch(normalized::contains);
    }

    private static List<Integer> matchingLines(List<String> lines, Pattern pattern) {
        var matches = new ArrayList<Integer>();
        for (int index = 0; index < lines.size(); index++) {
            if (pattern.matcher(lines.get(index)).matches()) matches.add(index + 1);
        }
        return matches;
    }

    private static void add(List<DocumentationGovernanceSupport.Violation> violations,
                            Path root, Path file, String rule, int line, String finding,
                            String contract, String remediation) {
        violations.add(DocumentationGovernanceSupport.violation(
                rule, root, file, line, finding, contract, remediation));
    }
}
