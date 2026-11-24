# ATF Libraries

## At a glance

Java 21 and Maven; six thin JARs, with transitive dependencies declared in
each POM. These guides describe the artifacts and their observable contracts
without requiring library source.

| Guide | Artifact, group `atf.api` | Use |
|---|---|---|
| [Core](README.Core.md) | `atf-core` | HTTP, configuration, JSON, and lifecycle. |
| [Reporting](README.Reporting.md) | `atf-reporting` | Extent/fallback and auditable evidence. |
| [Console](README.Console.md) | `atf-console` | Maven and IntelliJ diagnostics. |
| [Mock](README.Mock.md) | `atf-mock` | Components and offline HTTP contracts. |
| [Test Kit](README.TestKit.md) | `atf-test-kit` | JUnit classification, comparisons, and test support. |
| [Debug UI](README.DebugUi.md) | `atf-debug-ui` | Read-only local inspection of an existing HTML report; test scope only. |

## Integration

1. In a TMP copy of an ATF consumer, `mvn -B -ntp -N validate` installs the
   pairs from `lib` into the selected Maven repository.
2. Declare the dependency in the calling module; its coordinate and version
   are in the POM beside the JAR.
3. IntelliJ uses the same JDK, Maven settings, and local repository; reload the
   root POM after selecting them.

<details>
<summary>New consumer without the ATF reactor</summary>

Copy the JAR/POM pairs into `lib` and install them from the new project in TMP
with the Maven Install Plugin, passing each JAR and its adjacent POM.

For example, declare `atf.api:atf-reporting:<version from the POM>` as a
dependency. Use `compile` scope for support in `src/main` and `test` only in
`src/test`; Maven resolves transitive dependencies.

</details>

## Identity and verification

- One JAR/POM pair per selected library, the index, and six guides, directly in `lib`.
- The root `pom.xml` selects versions; `library-provenance.json` records
  coordinates, hashes, and package-origin evidence.
- Governance and acceptance run in TMP; each consumer verifies its own
  dependency profile and classpath.

<a id="maintenance-and-feedback"></a>

## Maintenance and feedback

Record a library finding against its Maven coordinate, version, and relevant
JAR/POM SHA-256 values. Include a minimal reproducer and expected/observed
behavior. Keep adapter findings with the affected consumer module; describe
shared library behavior through the relevant published contract instead of
duplicating unresolved library criteria in a consumer README.

<a id="shared-library-source"></a>

> **Library sources:** [Catalog · ATF J0](https://github.com/crististefanita/atf_j0/blob/main/lib/README.md).
> This project’s POM, provenance and tests define the consumed version and local acceptance.
