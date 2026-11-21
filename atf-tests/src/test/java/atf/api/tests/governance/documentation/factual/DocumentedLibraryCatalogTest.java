package atf.api.tests.governance.documentation.factual;

import atf.testkit.AtfComponentTest;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static atf.api.tests.governance.AtfGovernanceTestSupport.locateProjectRoot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AtfComponentTest
public class DocumentedLibraryCatalogTest {
    @Test
    void documentedCoordinatesAndPairsMatchTheLocalPomAndLibraryFiles() throws Exception {
        Path root = locateProjectRoot();
        assertEquals(List.of(), inspect(root, catalog(root), libraryIndex(root)));
    }

    @Test
    void staleVersionInTheCatalogIsRejected() throws Exception {
        Path root = locateProjectRoot();
        String current = catalog(root);
        String stale = current.replace("atf.api:atf-reporting:1.0.0-j26",
                "atf.api:atf-reporting:1.0.0-j24");
        assertTrue(!current.equals(stale), "the Reporting coordinate must be present in the catalog");
        var failures = inspect(root, stale, libraryIndex(root));
        assertTrue(failures.stream().anyMatch(failure ->
                failure.contains("atf.api:atf-reporting:1.0.0-j26 rows=0; expected=1")));
    }

    @Test
    void catalogMustNameTheSelectedPhysicalPair() throws Exception {
        Path root = locateProjectRoot();
        String current = catalog(root);
        String changed = current.replace("lib/atf-core-1.0.0-c4.jar", "lib/atf-core-1.0.0-c3.jar");
        assertTrue(!current.equals(changed), "the selected Core JAR must be documented");
        var failures = inspect(root, changed, libraryIndex(root));
        assertTrue(failures.stream().anyMatch(failure ->
                failure.contains("atf.api:atf-core:1.0.0-c4 must name lib/atf-core-1.0.0-c4.jar")));
    }

    @Test
    void libraryIndexMustKeepOneGuideForEachConsumedArtifact() throws Exception {
        Path root = locateProjectRoot();
        String current = libraryIndex(root);
        String changed = current.replace("README.Mock.md) | " + (char) 96 + "atf-mock" + (char) 96,
                "README.Mock.md) | " + (char) 96 + "atf-stub" + (char) 96);
        assertTrue(!current.equals(changed), "the Mock guide row must be present");
        var failures = inspect(root, catalog(root), changed);
        assertTrue(failures.stream().anyMatch(failure ->
                failure.contains("lib/README.md: Mock guide rows=0; expected=1")));
    }

    private static List<String> inspect(Path root, String catalog, String libraryIndex) throws Exception {
        return DocumentedLibraryCatalog.inspect(root, catalog, libraryIndex);
    }

    private static String catalog(Path root) throws Exception {
        return Files.readString(root.resolve("docs/ATF_Workspace_Synchronization.md"));
    }

    private static String libraryIndex(Path root) throws Exception {
        return Files.readString(root.resolve("lib/README.md"));
    }
}
