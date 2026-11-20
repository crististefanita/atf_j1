package atf.api.tests.governance;
import atf.api.tests.governance.changes.TestChangeGovernanceTest;
import atf.api.tests.governance.changes.TestChangePolicyTest;
import atf.api.tests.governance.changes.GitTestChangesTest;
import atf.api.tests.governance.console.ConsoleDefaultsGovernanceTest;

import atf.testkit.AtfIntegrationTest;
import atf.api.tests.governance.distribution.GovernanceDeploymentTest;
import atf.api.tests.governance.distribution.ReportingGovernanceSurfaceTest;
import atf.api.tests.governance.distribution.ReportingHarnessOwnershipTest;
import atf.api.tests.governance.documentation.structure.DocumentationContextArtifactGovernanceTest;
import atf.api.tests.governance.documentation.DocumentationDiagramGovernanceTest;
import atf.api.tests.governance.documentation.relationship.DocumentationDirectRelationshipGovernanceTest;
import atf.api.tests.governance.documentation.language.DocumentationLanguageGovernanceTest;
import atf.api.tests.governance.documentation.DocumentationProseGovernanceTest;
import atf.api.tests.governance.documentation.structure.DocumentationReadmeStructureGovernanceTest;
import atf.api.tests.governance.documentation.DocumentationStyleGovernanceTest;
import atf.api.tests.governance.documentation.DocumentationTargetGovernanceTest;
import atf.api.tests.governance.documentation.links.DocumentationLinkGovernanceTest;
import atf.api.tests.governance.documentation.DocumentationAnchorGovernanceTest;
import atf.api.tests.governance.documentation.structure.RootReadmeGovernanceTest;
import atf.api.tests.governance.documentation.factual.DocumentedBuildContractTest;
import atf.api.tests.governance.documentation.factual.DocumentedLibraryCatalogTest;
import atf.api.tests.governance.documentation.factual.DocumentedUiGeometryContractTest;
import atf.api.tests.governance.documentation.DynamicPathDocumentationArchitectureTest;
import atf.api.tests.governance.consumers.ConsumerModuleArchitectureGovernanceTest;
import atf.api.tests.governance.quality.CodeGovernanceSuiteCoverageTest;
import atf.api.tests.governance.layers.AtfTestLayerArchitectureTest;
import atf.api.tests.governance.layers.ConsumerSuiteCoverageGovernanceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/** Explicit entry point for the governance checks that inspect the complete reactor. */
@AtfIntegrationTest
@Suite
@SelectClasses({
        atf.api.tests.governance.distribution.core.CoreGovernanceSurfaceTest.class,
        GovernanceDeploymentTest.class,
        ReportingGovernanceSurfaceTest.class,
        ReportingHarnessOwnershipTest.class,
        TestChangeGovernanceTest.class,
        TestChangePolicyTest.class,
        GitTestChangesTest.class,
        ConsoleDefaultsGovernanceTest.class,
        ArchitectureModuleBoundaryTest.class,
        ArchitectureOwnershipBoundaryTest.class,
        AtfPackageCohesionGovernanceTest.class,
        AtfSourceReadabilityGovernanceTest.class,
        ConstantsOwnershipGovernanceTest.class,
        DynamicPathDocumentationArchitectureTest.class,
        DocumentationContextArtifactGovernanceTest.class,
        DocumentationDiagramGovernanceTest.class,
        DocumentationDirectRelationshipGovernanceTest.class,
        DocumentationLanguageGovernanceTest.class,
        DocumentationProseGovernanceTest.class,
        DocumentationReadmeStructureGovernanceTest.class,
        DocumentationStyleGovernanceTest.class,
        DocumentationTargetGovernanceTest.class,
        DocumentationLinkGovernanceTest.class,
        DocumentationAnchorGovernanceTest.class,
        RootReadmeGovernanceTest.class,
        DocumentedBuildContractTest.class,
        DocumentedLibraryCatalogTest.class,
        DocumentedUiGeometryContractTest.class,
        ConsumerModuleArchitectureGovernanceTest.class,
        CodeGovernanceSuiteCoverageTest.class,
        AtfTestLayerArchitectureTest.class,
        ConsumerSuiteCoverageGovernanceTest.class
})
public class WholeAtfGovernanceSuiteTest {
}
