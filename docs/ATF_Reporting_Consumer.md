# ATF J1 — Reporting library consumption

<a id="at-a-glance"></a>

## At a glance

J1 selects Reporting from the root POM and consumes its versioned JAR/POM pair
from `lib`. This guide covers the published contract, local integration, and
the evidence that J1 can verify against the selected bytes.

**Quick links:** [Reporting library guide](../lib/README.Reporting.md) ·
[artifact catalog](ATF_Workspace_Synchronization.md#consumed-library-catalog) ·
[component report checks](#component-report-acceptance).

| Concern | J1 rule |
|---|---|
| Library selection | The root POM pins one Reporting coordinate; the matching JAR/POM pair is stored in `lib`. |
| Local behavior | Shop/AutoEx adapters, configuration, business expectations, and consumer tests stay in their J1 modules. |
| Report evidence | Consumer tests check the generated report against the selected library and local scenarios. |
| Execution | Tests, generated reports, and review copies stay in owned TMP staging. |

<a id="consumed-libraries"></a>

## Consumed libraries

| Library | Coordinate selected by the root POM | Contract used in J1 |
|---|---|---|
| Core | `atf.api:atf-core:1.0.0-c4` | HTTP, configuration, JSON, and lifecycle primitives |
| Reporting | `atf.api:atf-reporting:1.0.0-j26` | Extent/fallback publication and auditable report evidence |
| Console | `atf.api:atf-console:1.0.0-c2` | Neutral event formatting and diagnostics |
| Mock | `atf.api:atf-mock:1.0.0-m2` | Deterministic HTTP-port replacement |
| Test Kit | `atf.api:atf-test-kit:1.0.0-k3` | JUnit classification, comparisons, and lifecycle support |

These are binary dependencies of the J1 reactor, not source modules in it.
Describe a dependency by its coordinate, version, and observable contract.
Keep application adapters, fixtures, and expected business values local.

<a id="resolve-and-accept"></a>

## Resolve and accept

~~~mermaid
flowchart LR
    Pin["Root POM<br/>selected coordinate"] -->|selects| Pair["lib/<br/>versioned JAR + POM"]
    Pair -->|validate installs| Repo["Maven repository"]
    Repo -->|resolves for| Modules["J1 modules"]
    Modules -->|exercise| Contract["local adapter + scenario"]
    Contract -->|produces| Evidence["assertions + Extent report"]
~~~

<details>
<summary><strong>Legend — dependency and consumer evidence</strong></summary>

- The root POM selects the coordinate and version used by the local reactor.
- `mvn validate` installs the adjacent JAR/POM pair into the selected Maven
  repository; the consumer resolves those bytes.
- Consumer acceptance covers only the modules and scenarios selected by that
  run. It does not imply tests outside this reactor were executed.

</details>

<a id="component-report-acceptance"></a>

## Component report acceptance

For a component reporting check, inspect both the test result and the generated
HTML. The local Extent acceptance should show the scenario, its completed steps,
and its assertion evidence as separately expandable sections.

Confirm that **Expand all** reveals every nested step and assertion, that
collapsing a sibling preserves the other expanded branches, and that the
rendered report keeps the expected status, values, and navigation links. Record
the exact library coordinate and report path with any reproducible mismatch.

<a id="change-a-library-version"></a>

## Change a library version

1. Read the selected coordinate in the root POM and the matching pair in `lib`.
2. Run the smallest affected J1 component or integration test from an owned TMP
   copy using the intended Maven settings and repository.
3. For Reporting, open the generated HTML and verify the relevant expandable
   test, step, and assertion nodes in the report viewer.
4. Record the scenario, expected and observed behavior, coordinate, and relevant
   JAR/POM hashes in the local feedback entry.

Use [the Reporting library guide](../lib/README.Reporting.md) for its published
surface and [artifact synchronization](ATF_Workspace_Synchronization.md) for
the local installation and provenance rules.
