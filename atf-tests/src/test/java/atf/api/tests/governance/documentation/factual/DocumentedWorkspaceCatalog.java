package atf.api.tests.governance.documentation.factual;

import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Checks the workspace catalog and deployment matrix against the live sibling POMs. */
final class DocumentedWorkspaceCatalog {
    private static final Pattern INVENTORY_ENTRY =
            Pattern.compile("^J([0-7])(?:\\s+\\S+)*\\s+(\\d+)\\s*$");

    private DocumentedWorkspaceCatalog() { }

    static List<String> inspect(Path j1Root, String synchronization, String atlas, String readme,
                                String atfTestsReadme)
            throws Exception {
        Path group = j1Root.toAbsolutePath().normalize().getParent();
        List<Workspace> workspaces = new ArrayList<>();
        List<String> failures = new ArrayList<>();
        for (int index = 0; index <= 7; index++) {
            String name = "atf_j" + index;
            Path workspace = group.resolve(name);
            Path pomPath = workspace.resolve("pom.xml");
            if (!Files.isRegularFile(pomPath)) {
                failures.add("DOC-WORKSPACE-FACT " + name + ": missing live POM " + pomPath);
                continue;
            }
            Element project = pom(pomPath);
            List<Element> modules = children(child(project, "modules"), "module");
            int reportingHarnessModules = (int) modules.stream()
                    .filter(module -> "atf-reporting-tests".equals(module.getTextContent().strip()))
                    .count();
            boolean producer = modules.stream().anyMatch(module ->
                    "atf-reporting".equals(module.getTextContent().strip()));
            String reporting = producer ? "source" : reportingVersion(workspace, project, modules);
            workspaces.add(new Workspace(index, modules.size(), producer ? "producer" : "consumer",
                    reporting, reportingHarnessModules));
        }

        Map<Integer, List<Row>> rows = synchronizationRows(synchronization);
        for (Workspace workspace : workspaces) {
            List<Row> declarations = rows.getOrDefault(workspace.index(), List.of());
            if (declarations.size() != 1) {
                failures.add("DOC-WORKSPACE-FACT docs/ATF_Workspace_Synchronization.md: catalog J"
                        + workspace.index() + ": declared rows=" + declarations.size() + "; expected=1");
                continue;
            }
            Row row = declarations.getFirst();
            compare(failures, row.line(), "J" + workspace.index() + " module count",
                    row.modules(), Integer.toString(workspace.modules()), "pom.xml");
            String boundary = row.boundary().toLowerCase(java.util.Locale.ROOT);
            String declaredRole = boundary.startsWith("producer") || boundary.startsWith("producător")
                    ? "producer"
                    : boundary.startsWith("consumer") || boundary.startsWith("consumator")
                    ? "consumer" : "missing or ambiguous role";
            compare(failures, row.line(), "J" + workspace.index() + " boundary role",
                    declaredRole, workspace.role(), "pom.xml modules");
            compare(failures, row.line(), "J" + workspace.index() + " Reporting",
                    row.reporting(), workspace.reporting(), "pom.xml");
        }

        inspectInventoryClaim(failures, atlas, "docs/ATF_Context_Atlas.md",
                "POM-uri live:", workspaces);
        inspectReadmeClaims(failures, readme, workspaces);
        inspectDeploymentMatrix(failures, atfTestsReadme, workspaces);
        return List.copyOf(failures);
    }

    private static void inspectDeploymentMatrix(List<String> failures, String document,
                                                List<Workspace> workspaces) {
        Map<Integer, List<DeploymentRow>> rows = new LinkedHashMap<>();
        int lineNumber = 0;
        for (String line : document.lines().toList()) {
            lineNumber++;
            if (!line.startsWith("| J")) continue;
            String[] cells = line.split("\\|", -1);
            if (cells.length != 5) continue;
            Matcher id = Pattern.compile("J([0-2]) (?:source canonical|binary consumer)").matcher(cells[1].strip());
            if (!id.matches()) continue;
            int index = Integer.parseInt(id.group(1));
            rows.computeIfAbsent(index, ignored -> new ArrayList<>()).add(new DeploymentRow(
                    lineNumber, cells[2].strip(), cells[3].strip()));
        }
        for (int index = 0; index <= 2; index++) {
            int workspaceIndex = index;
            Workspace workspace = workspaces.stream().filter(value -> value.index() == workspaceIndex)
                    .findFirst().orElse(null);
            if (workspace == null) continue;
            List<DeploymentRow> declarations = rows.getOrDefault(index, List.of());
            if (declarations.size() != 1) {
                failures.add("DOC-WORKSPACE-FACT atf-tests/README.md: deployment J" + index
                        + " rows=" + declarations.size() + "; expected=1");
                continue;
            }
            DeploymentRow row = declarations.getFirst();
            compare(failures, row.line(), "deployment J" + index + " module count",
                    row.modules(), Integer.toString(workspace.modules()), "pom.xml");
            int harnessModules = workspaces.stream().filter(value -> value.index() == 0)
                    .mapToInt(Workspace::reportingHarnessModules).findFirst().orElse(0);
            if (harnessModules != 1) failures.add("DOC-WORKSPACE-FACT atf_j0/pom.xml: atf-reporting-tests module count="
                    + harnessModules + "; expected=1");
            String withHarness = index == 0 ? "included"
                    : Integer.toString(workspace.modules() + harnessModules);
            compare(failures, row.line(), "deployment J" + index + " modules with Reporting Tests in TMP",
                    normalizeInclusion(row.withHarness()), withHarness,
                    index == 0 ? "pom.xml" : "pom.xml + atf_j0/pom.xml atf-reporting-tests module");
        }
    }

    private static Map<Integer, List<Row>> synchronizationRows(String document) {
        Map<Integer, List<Row>> rows = new LinkedHashMap<>();
        int lineNumber = 0;
        for (String line : document.lines().toList()) {
            lineNumber++;
            if (!line.startsWith("|") || !line.contains("atf_j")) continue;
            String[] cells = line.split("\\|", -1);
            if (cells.length != 7) continue;
            String name = cells[1].strip();
            char tick = (char) 96;
            if (name.length() < 4 || name.charAt(0) != tick || name.charAt(name.length() - 1) != tick) continue;
            Matcher id = Pattern.compile("atf_j([0-7])").matcher(name.substring(1, name.length() - 1));
            if (!id.matches()) continue;
            int index = Integer.parseInt(id.group(1));
            rows.computeIfAbsent(index, ignored -> new ArrayList<>()).add(new Row(
                    lineNumber, cells[2].strip(), cells[3].strip(), cells[4].strip()));
        }
        return rows;
    }

    private static void inspectInventoryClaim(List<String> failures, String document, String path,
                                              String marker, List<Workspace> workspaces) {
        List<String> claims = document.lines().filter(line -> line.contains(marker)
                || line.contains("Live POMs:")).toList();
        if (claims.size() != 1) {
            failures.add("DOC-WORKSPACE-FACT " + path + ": inventory claim count="
                    + claims.size() + "; expected=1");
            return;
        }
        String line = claims.getFirst();
        String activeMarker = line.contains(marker) ? marker : "Live POMs:";
        String claim = line.substring(line.indexOf(activeMarker) + activeMarker.length()).strip();
        int end = claim.indexOf(';');
        if (end < 0) end = claim.indexOf('|');
        if (end >= 0) claim = claim.substring(0, end).strip();
        inspectEntries(failures, path, claim, workspaces);
    }

    private static void inspectReadmeClaims(List<String> failures, String document,
                                            List<Workspace> workspaces) {
        Map<Integer, Workspace> actual = new LinkedHashMap<>();
        workspaces.stream().filter(workspace -> workspace.index() <= 2)
                .forEach(workspace -> actual.put(workspace.index(), workspace));
        Map<Integer, List<ReadmeRow>> rows = new LinkedHashMap<>();
        int lineNumber = 0;
        for (String line : document.lines().toList()) {
            lineNumber++;
            if (!line.startsWith("| J")) continue;
            String[] cells = line.split("\\|", -1);
            if (cells.length < 4) continue;
            Matcher id = Pattern.compile("J([0-2])").matcher(cells[1].strip());
            Matcher count = Pattern.compile("(\\d+)\\s+(?:(?:source|Maven)\\s+)?modules?\\b",
                    Pattern.CASE_INSENSITIVE).matcher(cells[2].strip());
            if (!id.find() || !count.find()) continue;
            int index = Integer.parseInt(id.group(1));
            rows.computeIfAbsent(index, ignored -> new ArrayList<>()).add(
                    new ReadmeRow(lineNumber, count.group(1)));
        }
        for (var entry : actual.entrySet()) {
            List<ReadmeRow> declarations = rows.getOrDefault(entry.getKey(), List.of());
            if (declarations.size() != 1) {
                failures.add("DOC-WORKSPACE-FACT README.md: J" + entry.getKey()
                        + " row count=" + declarations.size() + "; expected=1");
                continue;
            }
            compare(failures, declarations.getFirst().line(), "README J" + entry.getKey() + " module count",
                    declarations.getFirst().modules(), Integer.toString(entry.getValue().modules()), "pom.xml");
        }
    }

    private static void inspectEntries(List<String> failures, String path, String claim,
                                       List<Workspace> workspaces) {
        Map<Integer, List<String>> declared = new LinkedHashMap<>();
        for (String piece : claim.split(",")) {
            Matcher entry = INVENTORY_ENTRY.matcher(piece.strip());
            if (entry.matches()) {
                int index = Integer.parseInt(entry.group(1));
                declared.computeIfAbsent(index, ignored -> new ArrayList<>()).add(entry.group(2));
            }
        }
        for (Workspace workspace : workspaces) {
            List<String> counts = declared.getOrDefault(workspace.index(), List.of());
            if (counts.size() != 1) {
                failures.add("DOC-WORKSPACE-FACT " + path + ": J" + workspace.index()
                        + " inventory entries=" + counts.size() + "; expected=1");
                continue;
            }
            compare(failures, 1, path + " J" + workspace.index() + " module count",
                    counts.getFirst(), Integer.toString(workspace.modules()), "pom.xml");
        }
    }

    private static String reportingVersion(Path workspace, Element project, List<Element> modules)
            throws Exception {
        Element properties = child(project, "properties");
        String propertyVersion = value(properties, "atf.reporting.version");
        if (!propertyVersion.isBlank()) return resolve(propertyVersion, properties);
        List<String> versions = new ArrayList<>();
        for (Element module : modules) {
            Path modulePom = workspace.resolve(module.getTextContent().strip()).resolve("pom.xml");
            if (!Files.isRegularFile(modulePom)) continue;
            Element moduleProject = pom(modulePom);
            for (Element dependency : children(child(moduleProject, "dependencies"), "dependency")) {
                if (!"atf-reporting".equals(value(dependency, "artifactId"))) continue;
                String version = value(dependency, "version");
                if (!version.isBlank()) versions.add(resolve(version, child(moduleProject, "properties")));
            }
        }
        return versions.size() == 1 ? versions.getFirst() : "missing or ambiguous";
    }

    private static String resolve(String value, Element properties) {
        Matcher property = Pattern.compile("^\\$\\{([^}]+)}$").matcher(value);
        return property.matches() ? value(properties, property.group(1)) : value;
    }

    private static Element pom(Path path) throws Exception {
        var factory = DocumentBuilderFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        return factory.newDocumentBuilder().parse(path.toFile()).getDocumentElement();
    }

    private static Element child(Element parent, String name) {
        for (Element element : children(parent, name)) return element;
        return null;
    }

    private static List<Element> children(Element parent, String name) {
        List<Element> result = new ArrayList<>();
        if (parent != null) for (var node = parent.getFirstChild(); node != null; node = node.getNextSibling())
            if (node instanceof Element element && name.equals(element.getTagName())) result.add(element);
        return result;
    }

    private static String value(Element parent, String childName) {
        Element element = child(parent, childName);
        return element == null ? "" : element.getTextContent().strip();
    }

    private static void compare(List<String> failures, int line, String fact, String declared,
                                String actual, String source) {
        if (!declared.equals(actual)) failures.add("DOC-WORKSPACE-FACT line " + line + " " + fact
                + ": declared=" + declared + "; observed=" + actual + "; source=" + source);
    }

    private static String normalizeInclusion(String declaration) {
        return switch (declaration.toLowerCase(java.util.Locale.ROOT)) {
            case "inclus", "inclusă", "inclusa", "included" -> "included";
            default -> declaration;
        };
    }

    private record Workspace(int index, int modules, String role, String reporting, int reportingHarnessModules) { }
    private record Row(int line, String boundary, String modules, String reporting) { }
    private record ReadmeRow(int line, String modules) { }
    private record DeploymentRow(int line, String modules, String withHarness) { }
}
