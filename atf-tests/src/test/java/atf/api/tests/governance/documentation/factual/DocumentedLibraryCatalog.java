package atf.api.tests.governance.documentation.factual;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Checks the documented local library coordinates against the reactor POM and lib files. */
final class DocumentedLibraryCatalog {
    private static final List<Library> LIBRARIES = List.of(
            new Library("Core", "atf-core", "atf.core.version", "README.Core.md"),
            new Library("Reporting", "atf-reporting", "atf.reporting.version", "README.Reporting.md"),
            new Library("Console", "atf-console", "atf.console.version", "README.Console.md"),
            new Library("Mock", "atf-mock", "atf.mock.version", "README.Mock.md"),
            new Library("Test Kit", "atf-test-kit", "atf.testkit.version", "README.TestKit.md"));

    private DocumentedLibraryCatalog() { }

    static List<String> inspect(Path root, String catalog, String libraryIndex) throws Exception {
        Path project = root.toAbsolutePath().normalize();
        Element pom = parse(Files.readString(project.resolve("pom.xml"), StandardCharsets.UTF_8));
        Element properties = child(pom, "properties");
        List<String> failures = new ArrayList<>();

        for (Library library : LIBRARIES) {
            String version = value(properties, library.versionProperty());
            if (version.isBlank()) {
                failures.add("DOC-LIBRARY-FACT pom.xml: missing " + library.versionProperty());
                continue;
            }
            String coordinate = "atf.api:" + library.artifactId() + ":" + version;
            String jar = "lib/" + library.artifactId() + "-" + version + ".jar";
            String publishedPom = "lib/" + library.artifactId() + "-" + version + ".pom";
            if (!Files.isRegularFile(project.resolve(jar))) {
                failures.add("DOC-LIBRARY-FACT " + jar + ": selected library JAR is missing");
            }
            Path publishedPomPath = project.resolve(publishedPom);
            if (!Files.isRegularFile(publishedPomPath)) {
                failures.add("DOC-LIBRARY-FACT " + publishedPom + ": selected library POM is missing");
            } else {
                Element artifactPom = parse(Files.readString(publishedPomPath, StandardCharsets.UTF_8));
                String observed = value(artifactPom, "groupId") + ":" + value(artifactPom, "artifactId")
                        + ":" + value(artifactPom, "version");
                if (!coordinate.equals(observed)) {
                    failures.add("DOC-LIBRARY-FACT " + publishedPom + ": coordinate=" + observed
                            + "; expected=" + coordinate);
                }
            }

            List<String> declarations = catalog.lines().filter(line -> line.contains(coordinate)).toList();
            if (declarations.size() != 1) {
                failures.add("DOC-LIBRARY-FACT docs/ATF_Workspace_Synchronization.md: " + coordinate
                        + " rows=" + declarations.size() + "; expected=1");
            } else {
                String row = declarations.getFirst();
                if (!row.contains(jar) || !row.contains(publishedPom)) {
                    failures.add("DOC-LIBRARY-FACT docs/ATF_Workspace_Synchronization.md: " + coordinate
                            + " must name " + jar + " and " + publishedPom);
                }
            }
            long guideRows = libraryIndex.lines().filter(line ->
                    line.contains(library.guide()) && line.contains(library.artifactId())).count();
            if (guideRows != 1) {
                failures.add("DOC-LIBRARY-FACT lib/README.md: " + library.name()
                        + " guide rows=" + guideRows + "; expected=1");
            }
        }
        return List.copyOf(failures);
    }

    private static Element parse(String xml) throws Exception {
        var factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        return factory.newDocumentBuilder()
                .parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))).getDocumentElement();
    }

    private static Element child(Element parent, String name) {
        if (parent == null) return null;
        for (Node node = parent.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node instanceof Element element
                    && name.equals(element.getLocalName() == null ? element.getNodeName() : element.getLocalName())) {
                return element;
            }
        }
        return null;
    }

    private static String value(Element parent, String name) {
        Element element = child(parent, name);
        return element == null ? "" : element.getTextContent().strip();
    }

    private record Library(String name, String artifactId, String versionProperty, String guide) { }
}
