package atf.api.tests.governance.quality;

import atf.api.tests.governance.ArchitectureModuleBoundaryTest;
import atf.api.tests.governance.ArchitectureOwnershipBoundaryTest;
import atf.api.tests.governance.AtfPackageCohesionGovernanceTest;
import atf.api.tests.governance.AtfSourceReadabilityGovernanceTest;
import atf.api.tests.governance.ConstantsOwnershipGovernanceTest;
import atf.api.tests.governance.consumers.ConsumerModuleArchitectureGovernanceTest;
import atf.api.tests.governance.WholeAtfGovernanceSuiteTest;
import atf.api.tests.governance.layers.AtfTestLayerArchitectureTest;
import atf.api.tests.governance.layers.ConsumerSuiteCoverageGovernanceTest;
import atf.testkit.AtfComponentTest;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Prevents the whole-reactor suite from dropping its executable code-practice gates. */
@AtfComponentTest
public class CodeGovernanceSuiteCoverageTest {
    private static final Set<Class<?>> REQUIRED_CODE_GATES = Set.of(
            ArchitectureModuleBoundaryTest.class,
            ArchitectureOwnershipBoundaryTest.class,
            AtfPackageCohesionGovernanceTest.class,
            AtfSourceReadabilityGovernanceTest.class,
            ConstantsOwnershipGovernanceTest.class,
            ConsumerModuleArchitectureGovernanceTest.class,
            AtfTestLayerArchitectureTest.class,
            ConsumerSuiteCoverageGovernanceTest.class);

    @Test
    void wholeReactorSuiteSelectsEveryCodeBestPracticeGate() {
        SelectClasses selection = WholeAtfGovernanceSuiteTest.class.getAnnotation(SelectClasses.class);
        assertTrue(selection != null, "The whole-reactor governance suite must select its checks explicitly");
        Set<Class<?>> selected = Set.copyOf(Arrays.asList(selection.value()));
        assertEquals(Set.of(), missingCodeGates(selected),
                "Every code and test architecture gate must remain in the standard suite");
        assertTrue(selected.contains(CodeGovernanceSuiteCoverageTest.class),
                "The suite-coverage guard must protect its own presence");
    }

    @Test
    void omissionMutantsAreDetectedForEveryRequiredCodeGate() {
        assertEquals(8, REQUIRED_CODE_GATES.size());
        for (Class<?> required : REQUIRED_CODE_GATES) {
            var mutant = new HashSet<>(REQUIRED_CODE_GATES);
            mutant.remove(required);
            assertEquals(Set.of(required), missingCodeGates(mutant),
                    "Removing a required gate must produce an explicit missing-gate result");
        }
    }

    private static Set<Class<?>> missingCodeGates(Set<Class<?>> selected) {
        var missing = new HashSet<>(REQUIRED_CODE_GATES);
        missing.removeAll(selected);
        return Set.copyOf(missing);
    }
}
