# ATF J1 — library artifact synchronization

This guide records how this J1 reactor selects, installs, and verifies its five
consumed libraries. The reactor and its acceptance criteria remain local to this
checkout; dependencies are identified by Maven coordinates and artifact bytes.

## At a glance

The root POM pins each library version, and `lib` stores its matching JAR/POM
pair. `mvn validate` installs those exact files into the selected Maven
repository for consumer modules to compile and run against. `library-provenance.json`
records artifact hashes and package origin.

<a id="consumed-library-catalog"></a>

## Consumed library catalog

| Library | Maven coordinate | JAR | POM |
|---|---|---|---|
| Core | `atf.api:atf-core:1.0.0-c4` | `lib/atf-core-1.0.0-c4.jar` | `lib/atf-core-1.0.0-c4.pom` |
| Reporting | `atf.api:atf-reporting:1.0.0-j25` | `lib/atf-reporting-1.0.0-j25.jar` | `lib/atf-reporting-1.0.0-j25.pom` |
| Console | `atf.api:atf-console:1.0.0-c2` | `lib/atf-console-1.0.0-c2.jar` | `lib/atf-console-1.0.0-c2.pom` |
| Mock | `atf.api:atf-mock:1.0.0-m2` | `lib/atf-mock-1.0.0-m2.jar` | `lib/atf-mock-1.0.0-m2.pom` |
| Test Kit | `atf.api:atf-test-kit:1.0.0-k2` | `lib/atf-test-kit-1.0.0-k2.jar` | `lib/atf-test-kit-1.0.0-k2.pom` |

The versions above must match the root POM properties and the filenames in
`lib`. Library usage and dependency boundaries are described in the
[local library index](../lib/README.md).

## Artifact flow

~~~mermaid
flowchart LR
    POM["Root POM<br/>selected coordinates"] -->|pins| Pair["lib/<br/>versioned JAR + POM"]
    Pair -->|mvn validate installs| Repo["Selected Maven repository"]
    Repo -->|resolves for| Modules["J1 modules"]
    Modules -->|component and integration checks| Evidence["Consumer acceptance"]
    Pair -. hashes and origin .-> Provenance["library-provenance.json"]
    Governance["Artifact governance"] -. verifies bytes and boundaries .-> Pair
~~~

<details>
<summary><strong>Legend — coordinates, bytes, and consumer proof</strong></summary>

- The root POM selects the library versions used by this reactor.
- The adjacent JAR/POM files are the resolved artifacts; consumer tests use those
  published bytes, not copied library source.
- Provenance identifies each artifact with coordinates and hashes and retains
  package-origin evidence.
- Consumer acceptance proves only the local integrations and scenarios that
  were executed. It does not claim a separate library test run.

</details>

## Install and verify

Run from a disposable TMP copy, using the same Maven settings and local
repository as IntelliJ:

~~~powershell
mvn -B -ntp -N validate
mvn -B -ntp -DskipTests test-compile
~~~

The root `validate` phase installs the five adjacent JAR/POM pairs. Reload the
root POM in IntelliJ after selecting the same JDK, settings file, and local
repository. Do not add library source modules to this reactor to satisfy
imports.

## Change and evidence handling

1. Identify the dependency by group, artifact, and version from the root POM.
2. Reproduce the behavior in the smallest affected J1 consumer test.
3. Record expected and observed behavior, the exact scenario, and relevant
   artifact/POM SHA-256 values.
4. Keep consumer-adapter findings with the local module and describe artifact
   behavior through the library's published contract.
5. Preserve existing provenance history; record new package evidence only when
   the artifact bytes or the verification run actually change.

Builds, caches, generated reports, and temporary verification notes stay in
owned TMP staging. Keep the 240-character default report preview and the
collapsible, auditable component/E2E assertion groups.
