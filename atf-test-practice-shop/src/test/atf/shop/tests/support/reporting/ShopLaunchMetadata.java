package atf.shop.tests.support.reporting;

import atf.reporting.contract.AtfReportStatus;
import atf.reporting.model.ReportField;
import atf.reporting.model.ReportObservation;
import atf.reporting.ui.UiExtentReportSession;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.engine.UniqueId;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** Immutable JUnit source facts; launcher selection is deliberately unavailable here. */
public record ShopLaunchMetadata(Optional<String> rootSuite, String testClass, String testMethod) {
    public ShopLaunchMetadata {
        java.util.Objects.requireNonNull(rootSuite, "rootSuite");
        java.util.Objects.requireNonNull(testClass, "testClass");
        java.util.Objects.requireNonNull(testMethod, "testMethod");
    }

    public static ShopLaunchMetadata capture(ExtensionContext context) {
        var suite = UniqueId.parse(context.getUniqueId()).getSegments().stream()
                .filter(segment -> segment.getType().equals("suite"))
                .map(UniqueId.Segment::getValue).findFirst();
        String method = context.getTestMethod().map(value -> value.getName() + "("
                + Arrays.stream(value.getParameterTypes()).map(Class::getTypeName)
                        .collect(Collectors.joining(",")) + ")").orElse("unknown (no test method)");
        return new ShopLaunchMetadata(suite, context.getRequiredTestClass().getName(), method);
    }

    public ReportObservation observation() {
        return new ReportObservation("JUnit launch identity", List.of(
                new ReportField("Execution ancestry", rootSuite.isPresent()
                        ? "suite descendant" : "no suite ancestor exposed"),
                new ReportField("Root suite", rootSuite.orElse("unknown (no suite ancestor exposed)")),
                new ReportField("JUnit class", testClass),
                new ReportField("JUnit method", testMethod),
                new ReportField("Launcher selection", "unknown (launcher selectors unavailable)"),
                new ReportField("Effective selection count", "unknown (TestPlan unavailable)")),
                List.of(), List.of());
    }

    public void publish(UiExtentReportSession.UiScenario scenario) {
        try (var phase = scenario.startPhase("Launch context")) {
            phase.observation(observation());
            phase.complete(AtfReportStatus.INFO);
        }
    }
}
