# ATF — platform library consumption

This guide describes the five versioned library artifacts consumed by the J1
reactor and the checks that protect their use.

## At a glance

J1 resolves Core, Reporting, Console, Mock, and Test Kit from flat JAR/POM pairs
in `lib`. The root POM selects versions; local adapters, configuration,
business scenarios, and acceptance tests stay in their owning J1 modules.
Library details are summarized in the [artifact catalog](ATF_Workspace_Synchronization.md#consumed-library-catalog)
and the [local library index](../lib/README.md).

## Library contracts

| Library | Main contract used here |
|---|---|
| Core | HTTP execution, configuration, JSON, and lifecycle primitives |
| Reporting | Extent/fallback rendering and auditable report evidence |
| Console | Neutral event formatting and configured diagnostics |
| Mock | Deterministic HTTP-port replacement and strict request matching |
| Test Kit | JUnit classification, comparisons, and test lifecycle support |

- The Console JUnit bridge is optional. Consumer modules that use it declare
  Test Kit directly because optional Maven dependencies are not transitive.
- Console and Mock do not depend on Reporting, Validation, or application
  modules.
- Business fixtures and YAML catalogs remain in their consumer modules.

## Resolution and acceptance

~~~mermaid
flowchart LR
    Coordinates["Root POM<br/>library versions"] -->|selects| Pair["lib/<br/>five JAR/POM pairs"]
    Pair -->|validate installs| Maven["Maven local repository"]
    Maven -->|dependency resolution| Consumer["J1 modules"]
    Consumer -->|local scenarios| Acceptance["Component and integration evidence"]
    Provenance["library-provenance.json"] -. hashes and origin .-> Pair
    Governance["J1 governance"] -. checks .-> Pair
~~~

<details>
<summary><strong>Legend — binary dependencies and local evidence</strong></summary>

- The arrows describe dependency resolution and consumer acceptance.
- J1 tests execute against the physical library JARs selected by the root POM.
- Governance verifies coordinates, POM/JAR consistency, hashes, dependency
  boundaries, and the flat `lib` inventory.
- Native library source and tests are not included in this reactor. Provenance
  may record package evidence, but a J1 consumer run does not imply those tests
  were executed here.

</details>

## Maven and IntelliJ

Run from a disposable TMP copy:

~~~powershell
mvn -B -ntp -N validate
mvn -B -ntp -DskipTests test-compile
~~~

The root `validate` phase installs the five real POM/JAR pairs into the selected
Maven repository. Use the same JDK, Maven settings, and repository in IntelliJ,
then reload the root POM. Dependency versions are selected with
`atf.core.version`, `atf.reporting.version`, `atf.console.version`,
`atf.mock.version`, and `atf.testkit.version`.

Do not select nested JUnit fixture classes directly: their intentional failures
are asserted by their enclosing tests. Verify the physical JAR `CodeSource`
when diagnosing classpath conflicts.

## Integrity and maintenance

- `lib` contains five current JAR/POM pairs, an index, and one usage guide per
  library. It has no nested repository, source tree, or stale version pair.
- `library-provenance.json` records coordinates, binary/POM hashes, and
  package-input evidence. Historical entries remain available for audit.
- The embedded Maven POM must match the adjacent published POM byte for byte.
  Governance checks the physical artifacts, source-shadow rejection,
  dependency boundaries, and flat inventory.
- Builds, caches, generated reports, recovery copies, and temporary package
  notes stay in owned TMP staging.
- Preserve the 240-character default preview and auditable, collapsible
  component/E2E assertions. Offline checks do not imply device, external broker,
  or ASR acceptance.

See [artifact synchronization](ATF_Workspace_Synchronization.md) and
[isolated execution](ATF_Build_And_Execution_Safety.md), plus the individual
[Console](../lib/README.Console.md) and [Mock](../lib/README.Mock.md) guides.
