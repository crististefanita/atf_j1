# ATF Governance — J1 artifacts and module boundaries

## At a glance

This package verifies the J1 reactor declared by its root POM and the five
versioned library artifacts consumed from `lib`. It reads only this checkout's
POM, artifact files, and provenance.

## Local library catalog

| Library | Coordinate | Local pair |
|---|---|---|
| Core | `atf.api:atf-core:1.0.0-c4` | `lib/atf-core-1.0.0-c4.jar` + POM |
| Reporting | `atf.api:atf-reporting:1.0.0-j26` | `lib/atf-reporting-1.0.0-j26.jar` + POM |
| Console | `atf.api:atf-console:1.0.0-c2` | `lib/atf-console-1.0.0-c2.jar` + POM |
| Mock | `atf.api:atf-mock:1.0.0-m2` | `lib/atf-mock-1.0.0-m2.jar` + POM |
| Test Kit | `atf.api:atf-test-kit:1.0.0-k4` | `lib/atf-test-kit-1.0.0-k4.jar` + POM |

The root POM selects the versions. `library-provenance.json` identifies the
binary/POM hashes and retained package evidence.

## Verification surfaces

| Entry point | Responsibility | Proof |
|---|---|---|
| [GovernanceDeployment](GovernanceDeployment.java) | strict local Maven catalog and declared deployment | [module and target checks](GovernanceDeploymentTest.java) |
| [CoreGovernanceSurface](core/CoreGovernanceSurface.java) | Core artifact identity, bytecode boundary, and loaded class | [Core mutants](core/CoreGovernanceSurfaceTest.java) |
| [ReportingGovernanceSurface](ReportingGovernanceSurface.java) | Reporting POM, provenance, and source/JAR boundary | [provenance and mutants](ReportingGovernanceSurfaceTest.java) |
| [DeploymentFixture](DeploymentFixture.java) / [BinaryReportingFixture](BinaryReportingFixture.java) | isolated local deployment mutations | the live reactor and published artifacts stay unchanged |

## Evidence flow

~~~mermaid
flowchart LR
    POM["J1 root POM"] -->|selects| Pair["Library pairs"]
    Pair -->|resolved by| Reactor["J1 modules"]
    Reactor -->|exercised by| Tests["Consumer checks"]
    Tests -->|record| Evidence["Provenance<br/>and reports"]
    Guard["artifact<br/>governance"] -. checks .-> Pair
~~~

<details>
<summary><strong>Legend — local artifact evidence</strong></summary>

- The POM defines local modules and the five selected library versions.
- Governance verifies artifact coordinates, bytes, dependency boundaries, and
  loaded identity without reading an adjacent workspace.
- A native-test catalog in provenance records package evidence; it does not
  claim that those tests ran in J1.
- Consumer acceptance covers only the J1 scenarios that were selected and run.

</details>

## Invariants and limits

- The J1 POM defines its local module inventory; missing declared modules fail.
- Binary distribution reads the matching JAR/POM pairs from `lib`.
- Governance rejects source shadows, missing files, duplicate coordinates, and
  undeclared modules.
- The embedded POM must match the distributed POM; CodeSource and JDK `jdeps`
  checks identify a different loaded binary or forbidden bytecode dependency.
- Documentation facts are compared with the root POM, physical pairs, and local
  library index.
- Test-change governance uses this checkout's review base.

## Maintenance

A library version change updates the root property, matching JAR/POM pair,
provenance, documentation catalog, and affected consumer proof in one reviewed
increment. A missing or renamed file does not imply that the dependency has
been removed.
