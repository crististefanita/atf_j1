# ATF Libraries

## At a glance

Java 21 and Maven; five thin JARs, with transitive dependencies declared in the POM. The guides are self-contained and independent of the producer source.

| Guide | Artifact, `atf.api` group | Use |
|---|---|---|
| [Core](README.Core.md) | `atf-core` | HTTP, configuration, JSON, and lifecycle. |
| [Reporting](README.Reporting.md) | `atf-reporting` | Extent/fallback and auditable evidence. |
| [Console](README.Console.md) | `atf-console` | Maven and IntelliJ diagnostics. |
| [Mock](README.Mock.md) | `atf-mock` | Components and offline HTTP contracts. |
| [Test Kit](README.TestKit.md) | `atf-test-kit` · neutral JUnit audit and lifecycle | Classification, comparisons, and test support. |

## Integration

1. In a TMP copy of an ATF consumer, `mvn -B -ntp -N validate` installs the pairs from lib into the selected Maven repository.
2. Declare the dependency in the calling module; its coordinates and version are in the POM next to the JAR.
3. IntelliJ uses the same JDK and Maven repository; reload the root POM.

<details>
<summary>New consumer without the ATF reactor</summary>

Copy the JAR/POM pairs into lib; from the new project, in TMP:

```powershell
Get-ChildItem lib\*.pom | ForEach-Object {
    mvn org.apache.maven.plugins:maven-install-plugin:3.2.0:install-file "-Dfile=$([IO.Path]::ChangeExtension($_.FullName,'jar'))" "-DpomFile=$($_.FullName)" -DgeneratePom=false
    if ($LASTEXITCODE) { throw "Instalare eșuată: $($_.Name)" }
}
```

For example, declare `atf.api:atf-reporting:<versiunea din POM>` as a dependency. Use `compile` scope for support in src/main and `test` only in src/test; Maven resolves transitive dependencies.

</details>

## Identity and verification

- One JAR/POM pair per library, the index, and the five guides, directly in lib.
- In ATF, pom.xml selects the versions; library-provenance.json records the hashes and origin.
- Governance and acceptance run in TMP; each consumer verifies its own profile and classpath.

## Maintenance and feedback

Library code, native tests, and feedback belong to `atf_j0` (`atf_j0/docs/feedback/README.md`). Fixes and regressions in J0 precede synchronization and local acceptance; feedback includes the JAR/hash, reproduction steps, and expected/observed results.
