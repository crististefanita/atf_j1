package atf.api.tests.governance.documentation.structure;

import atf.api.tests.governance.documentation.DocumentationGovernanceSupport;
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
import java.util.stream.Stream;

import static atf.api.tests.governance.AtfGovernanceTestSupport.locateProjectRoot;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Keeps project maps and standalone verification records out of maintained J1 documentation. */
@AtfComponentTest
public class DocumentationContextArtifactGovernanceTest {
    private static final Set<String> IGNORED_DIRECTORIES = Set.of(
            ".git", "target", "artifacts", ".idea", "migration-copy-rejected",
            "migration-quarantine", ".presentation-build");
    private static final Pattern CONTEXT_ARTIFACT = Pattern.compile(
            "(?i)(?:\\bcontext[ _-]+(?:map|atlas)\\b"
                    + "|\\b(?:atf|j1|project|workspace)[ _-]+(?:map|atlas)\\b"
                    + "|atlas(?:ul)?\\s+de\\s+context|hart[ăa]\\s+(?:de\\s+)?context)");
    private static final Pattern CONTEXT_REFERENCE = Pattern.compile(
            "(?i)\\]\\([^)]*(?:context[ _-]?(?:map|atlas)"
                    + "|(?:atf|j1|project|workspace)[ _-]+(?:map|atlas)"
                    + "|atlas(?:ul)?[ _-]+de[ _-]+context|hart[ăa][ _-]+(?:de[ _-]+)?context)[^)]*\\)");
    private static final Pattern RELEASE_SNAPSHOT = Pattern.compile(
            "(?i)(?=.*(?:verification|acceptance|verificare|acceptanță|acceptare))"
                    + "(?=.*(?:20\\d{2}-\\d{2}-\\d{2}|core\\s+c\\d+|reporting\\s+j\\d+)).*");
    private static final Pattern STANDALONE_VERIFICATION_FILENAME = Pattern.compile(
            "(?iu)(?:^|/)[^/]*(?:verification|acceptance|verificare|acceptare|acceptanță)\\.md$");
    private static final Pattern STANDALONE_VERIFICATION_TITLE = Pattern.compile(
            "(?iu)^#\\s+(?:verification|acceptance|verificare|acceptare|acceptanță)"
                    + "\\s+(?:record|report|snapshot|evidence)\\s*$");
    private static final Pattern PROVENANCE_SNAPSHOT_REFERENCE = Pattern.compile(
            "(?i)\"docs/[^\"\\r\\n]*(?:verification|acceptance)[^\"\\r\\n]*\\.md\"");

    @Test
    void maintainedDocumentationContainsNoProjectMapsOrVerificationRecords() throws Exception {
        DocumentationGovernanceSupport.assertNoViolations(
                "project map and standalone verification record governance", inspect(locateProjectRoot()));
    }

    @Test
    void rejectsProjectMapsAndStandaloneVerificationRecords(@TempDir Path root)
            throws Exception {
        Path docs = Files.createDirectories(root.resolve("docs"));
        Path module = Files.createDirectories(root.resolve("module"));
        Files.writeString(docs.resolve("README.md"),
                "# Owner\nSee [the context map](docs/ATF_Context_Atlas.md).\n"
                        + "See [the project map](docs/ATF_Project_Map.md).\n");
        Files.writeString(docs.resolve("ATF_Context_Atlas.md"), "# ATF Context Atlas\n");
        Files.writeString(docs.resolve("ATF_Project_Map.md"), "# J1 Project Map\n");
        Files.writeString(module.resolve("ATF_Workspace_Map.md"), "# Workspace Map\n");
        Files.writeString(docs.resolve("ATF_Core_C3_Verification.md"),
                "# Core C3 — J1 Consumer Verification 2026-11-02\n");
        Files.writeString(docs.resolve("ATF_J1_Live_Screenshot_Verification.md"),
                "# J1 live screenshot acceptance — 2026-11-03\n");
        Files.writeString(docs.resolve("ATF_JSON_Binary_Verification.md"),
                "# JSON and Core C2 / Reporting J9 Libraries — Acceptance\n");
        Files.writeString(docs.resolve("ATF_Component_Verification.md"), "# Component verification\n");
        Files.writeString(docs.resolve("evidence.md"), "# Acceptance record\n");
        Files.writeString(root.resolve("library-provenance.json"),
                "{\"evidence\": \"docs/ATF_Reporting_J13_Verification.md\"}\n");

        List<DocumentationGovernanceSupport.Violation> violations = inspect(root);
        assertEquals(3, violations.stream().filter(v -> v.code().equals("DOC-CONTEXT-ARTIFACT")).count(),
                violations::toString);
        assertEquals(2, violations.stream().filter(v -> v.code().equals("DOC-CONTEXT-REFERENCE")).count(),
                violations::toString);
        assertEquals(3, violations.stream().filter(v -> v.code().equals("DOC-RELEASE-SNAPSHOT")).count(),
                violations::toString);
        assertEquals(5, violations.stream()
                        .filter(v -> v.code().equals("DOC-STANDALONE-VERIFICATION")).count(),
                violations::toString);
        assertTrue(violations.stream().anyMatch(v -> v.code().equals("DOC-PROVENANCE-SNAPSHOT")),
                violations::toString);
    }

    private static List<DocumentationGovernanceSupport.Violation> inspect(Path root) throws IOException {
        List<DocumentationGovernanceSupport.Violation> violations = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(root)) {
            for (Path file : paths.filter(path -> isMaintainedMarkdown(root, path)).toList()) {
                inspectMarkdown(root, file, violations);
            }
        }
        inspectProvenance(root, violations);
        return violations;
    }

    private static boolean isMaintainedMarkdown(Path root, Path path) {
        Path normalized = path.toAbsolutePath().normalize();
        Path projectRoot = root.toAbsolutePath().normalize();
        if (!normalized.startsWith(projectRoot) || !Files.isRegularFile(normalized)
                || !normalized.getFileName().toString().toLowerCase().endsWith(".md")) {
            return false;
        }
        for (Path part : projectRoot.relativize(normalized)) {
            if (IGNORED_DIRECTORIES.contains(part.toString())) return false;
        }
        return true;
    }

    private static void inspectMarkdown(Path root, Path file,
                                        List<DocumentationGovernanceSupport.Violation> violations)
            throws IOException {
        List<String> lines = Files.readAllLines(file);
        String relative = root.toAbsolutePath().normalize().relativize(file.toAbsolutePath().normalize())
                .toString().replace('\\', '/');
        String identity = relative.replace('_', ' ').replace('-', ' ');
        int titleLine = 1;
        String title = "";
        for (int index = 0; index < lines.size(); index++) {
            String line = lines.get(index);
            if (line.matches("^#\\s+.+")) {
                titleLine = index + 1;
                title = line;
                break;
            }
        }
        if (CONTEXT_ARTIFACT.matcher(identity + " " + title).find()) {
            add(violations, "DOC-CONTEXT-ARTIFACT", root, file, titleLine,
                    "a project-wide map or context atlas is present: " + relative,
                    "maintained documentation belongs to a concrete module or stable contract owner",
                    "remove the map and link directly to the architecture or owner README");
        }
        if (RELEASE_SNAPSHOT.matcher(relative + " " + title).matches()) {
            add(violations, "DOC-RELEASE-SNAPSHOT", root, file, titleLine,
                    "a dated or library-versioned verification/acceptance snapshot is present: " + title,
                    "execution evidence is temporary; durable behavior belongs to the owning contract and tests",
                    "remove the snapshot and keep any required run evidence in owned TMP/provenance");
        }
        if (STANDALONE_VERIFICATION_FILENAME.matcher(relative).find()
                || STANDALONE_VERIFICATION_TITLE.matcher(title).matches()) {
            add(violations, "DOC-STANDALONE-VERIFICATION", root, file, titleLine,
                    "a standalone verification or acceptance record is maintained: " + relative,
                    "durable behavior belongs in an owner contract; run-specific evidence belongs in owned TMP",
                    "move stable rules to the owning README and remove the report from maintained documentation");
        }
        for (int index = 0; index < lines.size(); index++) {
            if (CONTEXT_REFERENCE.matcher(lines.get(index)).find()) {
                add(violations, "DOC-CONTEXT-REFERENCE", root, file, index + 1,
                        "documentation links to a project-wide map or context atlas",
                        "references must lead to a concrete owner README or stable contract",
                        "replace the project map link with the owning module README or architecture section");
            }
        }
    }

    private static void inspectProvenance(Path root,
                                          List<DocumentationGovernanceSupport.Violation> violations)
            throws IOException {
        Path file = root.resolve("library-provenance.json");
        if (!Files.isRegularFile(file)) return;
        String content = Files.readString(file);
        Matcher matcher = PROVENANCE_SNAPSHOT_REFERENCE.matcher(content);
        while (matcher.find()) {
            int line = (int) content.substring(0, matcher.start()).chars().filter(ch -> ch == '\n').count() + 1;
            add(violations, "DOC-PROVENANCE-SNAPSHOT", root, file, line,
                    "provenance points to a committed verification/acceptance report: " + matcher.group(),
                    "release evidence belongs in owned TMP; provenance may point to its artifact evidence",
                    "remove the repository report link and retain only the applicable artifact/evidence metadata");
        }
    }

    private static void add(List<DocumentationGovernanceSupport.Violation> violations,
                            String code, Path root, Path file, int line,
                            String finding, String rule, String fix) {
        violations.add(DocumentationGovernanceSupport.violation(
                code, root, file, line, finding, rule, fix));
    }
}
