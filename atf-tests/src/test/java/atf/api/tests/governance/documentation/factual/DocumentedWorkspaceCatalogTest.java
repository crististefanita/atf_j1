package atf.api.tests.governance.documentation.factual;

import atf.testkit.AtfComponentTest;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static atf.api.tests.governance.AtfGovernanceTestSupport.locateProjectRoot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AtfComponentTest
public class DocumentedWorkspaceCatalogTest {
    @Test
    void currentWorkspaceCountsRolesAndReportingVersionsMatchLivePoms() throws Exception {
        Path root = locateProjectRoot();
        assertEquals(List.of(), inspect(root, synchronization(root), atlas(root), readme(root)));
    }

    @Test
    void aSingleWorkspaceCountMutantIsRejected() throws Exception {
        Path root = locateProjectRoot();
        String source = synchronization(root);
        String row = source.lines().filter(line -> workspaceRow(line, 4)).findFirst().orElseThrow();
        String changed = source.replace(row, row.replace("| 3 |", "| 4 |"));
        assertNotEquals(source, changed);
        var failures = inspect(root, changed, atlas(root), readme(root));
        assertTrue(failures.stream().anyMatch(failure ->
                failure.contains("J4 module count: declared=4; observed=3; source=pom.xml")));
    }

    @Test
    void aSingleWorkspaceRoleMutantIsRejected() throws Exception {
        Path root = locateProjectRoot();
        String source = synchronization(root);
        String row = source.lines().filter(line -> workspaceRow(line, 0)).findFirst().orElseThrow();
        int rowStart = source.indexOf(row);
        int roleStart = source.indexOf("producer", rowStart);
        String currentRole = "producer";
        if (roleStart < rowStart || roleStart >= rowStart + row.length()) {
            currentRole = "producător";
            roleStart = source.indexOf(currentRole, rowStart);
        }
        assertTrue(roleStart >= rowStart && roleStart < rowStart + row.length(), "J0 catalog row must declare a role");
        String replacement = currentRole.equals("producer") ? "consumer" : "consumator";
        String changed = source.substring(0, roleStart) + replacement
                + source.substring(roleStart + currentRole.length());
        assertNotEquals(source, changed);
        var failures = inspect(root, changed, atlas(root), readme(root));
        assertTrue(failures.stream().anyMatch(failure ->
                failure.contains("J0 boundary role: declared=consumer; observed=producer; source=pom.xml modules")));
    }

    @Test
    void aSingleReportingVersionMutantIsRejected() throws Exception {
        Path root = locateProjectRoot();
        String source = synchronization(root);
        String row = source.lines().filter(line -> workspaceRow(line, 1)).findFirst().orElseThrow();
        String changed = source.replace(row, row.replace("1.0.0-j25", "1.0.0-j24"));
        assertNotEquals(source, changed);
        var failures = inspect(root, changed, atlas(root), readme(root));
        assertTrue(failures.stream().anyMatch(failure ->
                failure.contains("J1 Reporting: declared=1.0.0-j24; observed=1.0.0-j25")));
    }

    @Test
    void theAtlasAndExecutiveReadmeCannotRetainStaleModuleCounts() throws Exception {
        Path root = locateProjectRoot();
        String atlas = atlas(root).replace("J2 WIP 5", "J2 WIP 6");
        var atlasFailures = inspect(root, synchronization(root), atlas, readme(root));
        assertFalse(atlasFailures.isEmpty());
        assertTrue(atlasFailures.stream().anyMatch(failure ->
                failure.contains("ATF_Context_Atlas.md J2 module count: declared=6; observed=5")));

        String currentReadme = readme(root);
        String row = currentReadme.lines().filter(line -> line.startsWith("| J2") && line.contains("WIP"))
                .findFirst().orElseThrow();
        String[] cells = row.split("\\|", -1);
        cells[2] = cells[2].replaceFirst("\\d+", "6");
        String readme = currentReadme.replace(row, String.join("|", cells));
        var readmeFailures = inspect(root, synchronization(root), atlas(root), readme);
        assertTrue(readmeFailures.stream().anyMatch(failure ->
                failure.contains("README J2 module count: declared=6; observed=5")));
    }

    @Test
    void wholeAtfDeploymentMatrixTracksLivePomsAndTemporaryReportingHarness() throws Exception {
        Path root = locateProjectRoot();
        String source = governanceReadme(root);
        assertEquals(List.of(), inspect(root, synchronization(root), atlas(root), readme(root), source));

        String row = source.lines().filter(line -> line.startsWith("| J1 ")
                && line.split("\\|", -1).length == 5).findFirst().orElseThrow();
        String[] cells = row.split("\\|", -1);
        cells[3] = cells[3].replaceFirst("\\d+", "8");
        String changed = source.replace(row, String.join("|", cells));
        assertNotEquals(source, changed);
        var failures = inspect(root, synchronization(root), atlas(root), readme(root), changed);
        assertTrue(failures.stream().anyMatch(failure -> failure.contains(
                "deployment J1 modules with Reporting Tests in TMP: declared=8; observed=7")));
    }

    private static List<String> inspect(Path root, String synchronization, String atlas, String readme)
            throws Exception {
        return inspect(root, synchronization, atlas, readme, governanceReadme(root));
    }

    private static List<String> inspect(Path root, String synchronization, String atlas, String readme,
                                        String governanceReadme) throws Exception {
        return DocumentedWorkspaceCatalog.inspect(root, synchronization, atlas, readme, governanceReadme);
    }

    private static boolean workspaceRow(String line, int index) {
        return line.startsWith("| ") && line.contains("atf_j" + index + String.valueOf((char) 96));
    }

    private static String synchronization(Path root) throws Exception {
        return Files.readString(root.resolve("docs/ATF_Workspace_Synchronization.md"));
    }

    private static String atlas(Path root) throws Exception {
        return Files.readString(root.resolve("docs/ATF_Context_Atlas.md"));
    }

    private static String readme(Path root) throws Exception {
        return Files.readString(root.resolve("README.md"));
    }

    private static String governanceReadme(Path root) throws Exception {
        return Files.readString(root.resolve("atf-tests/README.md"));
    }
}
