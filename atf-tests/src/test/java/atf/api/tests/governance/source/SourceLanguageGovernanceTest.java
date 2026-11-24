package atf.api.tests.governance.source;

import atf.api.tests.governance.layers.AtfJavaSourceInventory;
import atf.testkit.AtfComponentTest;
import atf.testkit.governance.AtfSourceLanguageScanner;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static atf.api.tests.governance.AtfGovernanceTestSupport.locateProjectRoot;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Adopts the J0 source-language guard for J1's maintained Java and MJS roots. */
@AtfComponentTest
public class SourceLanguageGovernanceTest {
    @Test
    void maintainedJavaAndModuleScriptsUseEnglishIdentifiersAndComments() throws Exception {
        Path root = locateProjectRoot();
        List<Path> sources = new ArrayList<>(AtfJavaSourceInventory.allSources(root));
        Path tools = root.resolve("atf-tests/tools");
        if (Files.isDirectory(tools)) {
            try (var paths = Files.walk(tools)) {
                sources.addAll(paths.filter(Files::isRegularFile)
                        .filter(path -> path.toString().endsWith(".mjs"))
                        .sorted().toList());
            }
        }

        List<String> violations = new ArrayList<>();
        for (Path source : sources.stream().distinct().sorted().toList()) {
            String relative = root.relativize(source).toString().replace('\\', '/');
            for (AtfSourceLanguageScanner.Finding finding : AtfSourceLanguageScanner.inspect(
                    relative, Files.readString(source))) {
                violations.add(relative + ":" + finding.line() + " [" + finding.construct()
                        + "] " + finding.token());
            }
        }

        assertFalse(sources.isEmpty(), "The Maven source inventory must not silently become empty");
        assertTrue(violations.isEmpty(), () -> "Use English for developer-authored identifiers and comments: "
                + violations);
    }

    @Test
    void sharedBinaryRejectsRomanianSourceAndPreservesLocalizedTestData() {
        String source = "class Fixture {\n"
                + "  // Jucătorul alege culoarea.\n"
                + "  void adaugaProdus() {\n"
                + "    String localizedLabel = \"Jucătorul mută\";\n"
                + "  }\n"
                + "}\n";

        List<AtfSourceLanguageScanner.Finding> findings =
                AtfSourceLanguageScanner.inspect("Fixture.java", source);

        assertFalse(findings.isEmpty());
        assertTrue(findings.stream().anyMatch(finding -> finding.line() == 2
                && finding.construct() == AtfSourceLanguageScanner.Construct.COMMENT));
        assertTrue(findings.stream().anyMatch(finding -> finding.line() == 3
                && finding.construct() == AtfSourceLanguageScanner.Construct.IDENTIFIER));
        assertTrue(findings.stream().noneMatch(finding -> finding.token().equals("mută")));

        List<AtfSourceLanguageScanner.Finding> diagnostic = AtfSourceLanguageScanner.inspect(
                "Failure.java", "class Failure { void fail() { throw new IllegalStateException(\"jucator invalid\"); } }");
        assertTrue(diagnostic.stream().anyMatch(finding ->
                finding.construct() == AtfSourceLanguageScanner.Construct.DEVELOPER_MESSAGE));
    }
}
