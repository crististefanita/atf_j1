package atf.api.tests.governance.documentation.relationship;

import atf.api.tests.governance.documentation.DocumentationGovernanceSupport;
import atf.api.tests.governance.documentation.links.MarkdownAnchorIndex;
import atf.testkit.AtfComponentTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static atf.api.tests.governance.AtfGovernanceTestSupport.locateProjectRoot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Keeps J1 documentation focused on local contracts and consumed libraries. */
@AtfComponentTest
public class DocumentationDirectRelationshipGovernanceTest {
    // Reject other ATF workspace identifiers and paths while allowing library release labels such as J18.
    private static final Pattern OTHER_ATF_PROJECT = Pattern.compile(
            "(?i)(?<![\\p{L}\\p{N}])(?:ATF[-_ ]?J(?:0|[2-9]|[1-9]\\d+)|atf[_-]j(?:0|[2-9]|[1-9]\\d+)|J0)(?![\\p{L}\\p{N}])");
    private static final String HISTORICAL_BASELINE = "docs/ATF_Test_Changes_Baseline.md";
    private static final String SOURCE_MARKER = "<a id=\"shared-library-source\"></a>";
    private static final List<String> LIBRARY_GUIDES = List.of(
            "README.md", "README.Core.md", "README.Reporting.md", "README.Console.md",
            "README.Mock.md", "README.TestKit.md");
    private static final List<String> IGNORED_DIRECTORIES = List.of(
            ".git", "target", "artifacts", ".idea", "migration-copy-rejected",
            "migration-quarantine", ".presentation-build", "node_modules", "dist");

    @Test
    void allJ1DocumentationNamesOnlyJ1AndConsumedLibraryArtifacts() throws Exception {
        var violations = inspect(locateProjectRoot());
        DocumentationGovernanceSupport.assertNoViolations("J1 library-focused documentation", violations);
    }

    @Test
    void localConsumerAndLibraryCoordinatesAreAllowed(@org.junit.jupiter.api.io.TempDir Path root) throws Exception {
        Path readme = root.resolve("README.md");
        Files.writeString(readme, "# ATF-J1\n\nJ1 consumes atf.api:atf-reporting:1.0.0-j18.\n"
                + "The Reporting release label J18 identifies a library version.\n"
                + "[local contract](docs/contract.md)\n");
        Files.createDirectories(root.resolve("docs"));
        Files.writeString(root.resolve("docs/contract.md"), "# Contract\n");

        assertEquals(List.of(), inspect(root));
    }

    @Test
    void theFormerSiblingProjectIdentifierIsRejected(@org.junit.jupiter.api.io.TempDir Path root)
            throws Exception {
        Path readme = root.resolve("README.md");
        Files.writeString(readme, "# ATF-J1\n\nThe library is discussed through ATF-J0.\n");

        var violations = inspect(root);
        assertEquals(1, violations.size());
        assertTrue(violations.getFirst().finding().contains("ATF-J0"));
        assertTrue(violations.getFirst().rule().contains("consumed library"));
    }

    @Test
    void linksIntoAnotherWorkspaceAreRejected(@org.junit.jupiter.api.io.TempDir Path root) throws Exception {
        Path readme = root.resolve("README.md");
        Files.writeString(readme, "# ATF-J1\\n\\n[external source](../../atf_j0/atf-reporting/README.md)\\n");

        var violations = inspect(root);
        assertEquals(1, violations.size());
        assertTrue(violations.getFirst().finding().contains("ATF-J0"));
    }

    @Test
    void anotherAtfProjectMentionIsRejectedWithFileAndLine(@org.junit.jupiter.api.io.TempDir Path root)
            throws Exception {
        Path document = root.resolve("docs/feedback/README.md");
        Files.createDirectories(document.getParent());
        Files.writeString(document, "# Feedback\n\nATF-J4 owns the device-specific acceptance.\n");

        var violations = inspect(root);
        assertEquals(1, violations.size());
        String diagnostic = violations.getFirst().toString();
        assertTrue(diagnostic.replace('\\', '/').contains("docs/feedback/README.md:3"), diagnostic);
        assertTrue(diagnostic.contains("ATF-J4"), diagnostic);
        assertTrue(diagnostic.contains("J1 documentation may name only J1 and its consumed library artifacts"), diagnostic);
    }

    @Test
    void projectReferencesInsideFencedExamplesAreAlsoRejected(@org.junit.jupiter.api.io.TempDir Path root)
            throws Exception {
        Path readme = root.resolve("README.md");
        Files.writeString(readme, "# ATF-J1\n\n~~~text\nATF-J8 command example\n~~~\n");

        var violations = inspect(root);
        assertEquals(1, violations.size());
        String diagnostic = violations.getFirst().toString();
        assertTrue(diagnostic.replace('\\', '/').contains("README.md:4"), diagnostic);
        assertTrue(diagnostic.contains("ATF-J8"), diagnostic);
    }

    @Test
    void anUnboundedAtfProjectNumberIsAlsoRejected(@org.junit.jupiter.api.io.TempDir Path root) throws Exception {
        Path readme = root.resolve("README.md");
        Files.writeString(readme, "# ATF-J1\n\nATF_J12 owns this unrelated contract.\n");

        var violations = inspect(root);
        assertEquals(1, violations.size());
        assertTrue(violations.getFirst().finding().contains("ATF-J12"));
    }

    @Test
    void historicalTestChangeRegisterCannotBypassRelationshipRules(@org.junit.jupiter.api.io.TempDir Path root)
            throws Exception {
        Path baseline = root.resolve(HISTORICAL_BASELINE);
        Files.createDirectories(baseline.getParent());
        Files.writeString(baseline, "# Historical controls\n\nOriginal ATF-J0 evidence.\n");

        var violations = inspect(root);
        assertEquals(1, violations.size());
        assertTrue(violations.getFirst().file().toString().replace('\\', '/').equals(HISTORICAL_BASELINE));
    }

    @Test
    void allMarkdownAndNestedAgentInstructionsAreGoverned(@org.junit.jupiter.api.io.TempDir Path root)
            throws Exception {
        Files.writeString(root.resolve("CONTRIBUTING.md"), "ATF-J0\n");
        Path nestedInstructions = root.resolve("module/AGENTS.md");
        Files.createDirectories(nestedInstructions.getParent());
        Files.writeString(nestedInstructions, "atf_j4\n");

        var violations = inspect(root);
        assertEquals(2, violations.size());
        assertTrue(violations.stream().anyMatch(v ->
                v.file().toString().replace('\\', '/').equals("CONTRIBUTING.md")));
        assertTrue(violations.stream().anyMatch(v ->
                v.file().toString().replace('\\', '/').equals("module/AGENTS.md")));
    }

    @Test
    void canonicalSourceNotesAreAllowedOnlyForTheirLibraryGuides(@org.junit.jupiter.api.io.TempDir Path root)
            throws Exception {
        Files.createDirectories(root.resolve("lib"));
        for (String guide : LIBRARY_GUIDES) {
            String name = guide.equals("README.md") ? "Catalog"
                    : guide.substring(7, guide.length() - 3).replace("TestKit", "Test Kit");
            String link = "[" + name + " · ATF J0](https://github.com/crististefanita/atf_j0/blob/HEAD/lib/" + guide + ").";
            for (String label : guide.equals("README.md") ? List.of("Library sources", "Sursa bibliotecilor")
                    : List.of("Library source", "Sursa bibliotecii")) {
                Files.writeString(root.resolve("lib/" + guide), "# Library\n\n" + SOURCE_MARKER
                        + "\n\n> **" + label + ":** " + link + "\n");
                assertEquals(List.of(), inspect(root), guide + ": " + label);
            }
        }
    }

    @Test
    void sourceNoteCannotHideWrongRoutesFencedExamplesOrOtherWorkspaceText(
            @org.junit.jupiter.api.io.TempDir Path root) throws Exception {
        Path guide = Files.createDirectories(root.resolve("lib")).resolve("README.Core.md");
        String note = SOURCE_MARKER + "\n\n> **Library source:** [Core · ATF J0]"
                + "(https://github.com/crististefanita/atf_j0/blob/HEAD/lib/README.Core.md).\n";
        for (String invalid : List.of(note.replace(SOURCE_MARKER, ""),
                note.replace("/README.Core.md", "/README.Reporting.md"),
                "~~~markdown\n" + note + "~~~\n", note + "\n" + SOURCE_MARKER + "\n",
                note.replace("atf_j0/blob", "atf_j2/blob"), note.replace(").", "). ATF-J2"),
                note + "\nATF-J4 owns another contract.\n")) {
            Files.writeString(guide, "# Library\n\n" + invalid);
            assertTrue(!inspect(root).isEmpty(), invalid);
        }
    }

    @Test
    void canonicalSourceNoteDoesNotAuthorizeRootOrNestedLibraryDirectories(
            @org.junit.jupiter.api.io.TempDir Path folder) throws Exception {
        String note = "# Libraries\n\n" + SOURCE_MARKER + "\n\n> **Library sources:** [Catalog · ATF J0]"
                + "(https://github.com/crististefanita/atf_j0/blob/HEAD/lib/README.md).\n";
        for (String file : List.of("README.md", "docs/lib/README.md", "lib/nested/README.md")) {
            Path root = Files.createTempDirectory(folder, "boundary-");
            Path guide = root.resolve(file);
            Files.createDirectories(guide.getParent());
            Files.writeString(guide, note);
            assertTrue(!inspect(root).isEmpty(), file);
        }
    }

    public static List<DocumentationGovernanceSupport.Violation> inspect(Path root) throws IOException {
        var violations = new ArrayList<DocumentationGovernanceSupport.Violation>();
        try (var files = Files.walk(root)) {
            for (Path path : files.filter(Files::isRegularFile).toList()) {
                if (!isCurrentDocumentation(root, path)) continue;
                List<String> lines = Files.readAllLines(path);
                List<String> visible = MarkdownAnchorIndex.withoutFences(lines);
                for (int index = 0; index < lines.size(); index++) {
                    if (isLibrarySourceNote(root.relativize(path), index, visible)) continue;
                    Matcher matcher = OTHER_ATF_PROJECT.matcher(lines.get(index));
                    while (matcher.find()) {
                        String project = matcher.group().toUpperCase(java.util.Locale.ROOT).replace('_', '-');
                        violations.add(DocumentationGovernanceSupport.violation(
                                "DOC-DIRECT-RELATIONSHIP", root, path, index + 1,
                                "reference to another ATF workspace: " + project,
                                "J1 documentation may name only J1 and its consumed library artifacts",
                                "describe dependencies by Maven coordinate/version or link a local J1 contract"));
                    }
                }
            }
        }
        return List.copyOf(violations);
    }

    private static boolean isLibrarySourceNote(Path relative, int index, List<String> visible) {
        if (relative.getNameCount() != 2 || !relative.getName(0).toString().equals("lib")) return false;
        String guide = relative.getFileName().toString();
        if (!LIBRARY_GUIDES.contains(guide) || index < 2 || !visible.get(index - 1).isBlank()
                || !visible.get(index - 2).equals(SOURCE_MARKER)
                || visible.stream().filter(SOURCE_MARKER::equals).count() != 1) return false;
        boolean catalog = guide.equals("README.md");
        String library = catalog ? "Catalog" : guide.substring(7, guide.length() - 3).replace("TestKit", "Test Kit");
        String link = "[" + library + " · ATF J0](https://github.com/crististefanita/atf_j0/blob/HEAD/lib/" + guide + ").";
        return visible.get(index).equals("> **" + (catalog ? "Library sources" : "Library source") + ":** " + link)
                || visible.get(index).equals("> **" + (catalog ? "Sursa bibliotecilor" : "Sursa bibliotecii") + ":** " + link);
    }

    private static boolean isCurrentDocumentation(Path root, Path path) {
        Path normalized = path.toAbsolutePath().normalize();
        Path projectRoot = root.toAbsolutePath().normalize();
        if (!normalized.startsWith(projectRoot)) return false;
        for (Path part : projectRoot.relativize(normalized)) {
            if (IGNORED_DIRECTORIES.contains(part.toString())) return false;
        }
        String fileName = normalized.getFileName().toString();
        if (fileName.equalsIgnoreCase("AGENTS.md")) return true;
        if (!fileName.toLowerCase(java.util.Locale.ROOT).endsWith(".md")) return false;
        return true;
    }
}
