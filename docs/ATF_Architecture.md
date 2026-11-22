# ATF J1 Architecture

This document describes the ATF J1 Maven reactor in this checkout. Its six local modules and their tests are the subject of the architecture. Five versioned ATF libraries are consumed as binary dependencies selected by the root POM.

The reactor retains exactly 6 existing modules.

## At a glance

- The reactor contains six modules: Validation, UI, Debug UI, Practice Shop, AutoEx, and governance tests.
- Core, Reporting, Console, Mock, and Test Kit are consumed from the versioned JAR/POM pairs under lib.
- The root POM and each module POM are authoritative for module membership, versions, and dependency scopes.
- [Library synchronization](ATF_Workspace_Synchronization.md#consumed-library-catalog) records the selected artifacts; [library guides](../lib/README.md) describe their consumer-facing contracts.

## Local reactor modules

| Module | Responsibility in J1 | Main verification |
|---|---|---|
| atf-validation | Response contracts, validation rules, and verdict publication through the local validation port | Unit and component tests |
| atf-ui | Shared Playwright adapter and browser policies used by local consumers | Unit and component tests |
| atf-debug-ui | Read-only local server for inspecting generated HTML artifacts | Component and browser checks |
| atf-test-practice-shop | Practice Shop configuration, pages, API/UI journeys, and application-specific evidence | Component, API, UI, and E2E suites |
| atf-test-auto-ex | AutoEx configuration, API/UI journeys, and application-specific evidence | Component, API, UI, and opt-in E2E suites |
| atf-tests | Architecture, documentation, dependency, and packaging governance for this reactor | Governance suites |

The two application modules keep Test Kit in test scope. Their direct ATF dependencies are:

| Consumer | `compile` | `test` |
|---|---|---|
| Shop | Core, Console, Reporting, UI | Test Kit |
| AutoEx | Core, Reporting, UI | Console, Test Kit |

## Consumed library artifacts

| Library | Selected coordinate | J1 use |
|---|---|---|
| Core | atf.api:atf-core:1.0.0-c4 | API request, response, execution, and configuration contracts |
| Reporting | atf.api:atf-reporting:1.0.0-j26 | Extent model and HTML report publication consumed by local tests |
| Console | atf.api:atf-console:1.0.0-c2 | Structured diagnostic output |
| Mock | atf.api:atf-mock:1.0.0-m2 | Deterministic fake transport in governance and test scenarios |
| Test Kit | atf.api:atf-test-kit:1.0.0-k4 | Test-level annotations, resolvers, and test support |

J1 verifies the exact selected JAR/POM bytes and the integrations exercised by its own tests. The library guides describe the available contract; local acceptance does not claim to execute a library's independent test suite.

## Dependency map

Arrows run from a dependency to its consumer; labels show the declared Maven scope. Dotted arrows identify the test-scoped dependencies of `atf-tests`. Governance checks are described separately.

~~~mermaid
flowchart LR
  subgraph Libraries[Versioned libraries under lib]
    Core[Core]
    Reporting[Reporting]
    Console[Console]
    Mock[Mock]
    Kit[Test Kit]
  end

  subgraph Reactor[Six local J1 modules]
    Validation[atf-validation]
    UI[atf-ui]
    Debug[atf-debug-ui]
    Shop[atf-test-practice-shop]
    AutoEx[atf-test-auto-ex]
    Governance[atf-tests]
  end

  Core -->|compile| Validation
  Kit -->|test| Validation
  Core -->|compile| UI
  Kit -->|test| UI
  Console -->|compile| Debug
  Kit -->|test| Debug
  Core -->|compile| Shop
  Console -->|compile| Shop
  Reporting -->|compile| Shop
  UI -->|compile| Shop
  Kit -->|test| Shop
  Core -->|compile| AutoEx
  Reporting -->|compile| AutoEx
  UI -->|compile| AutoEx
  Console -->|test| AutoEx
  Kit -->|test| AutoEx
  Core -.->|test| Governance
  Reporting -.->|test| Governance
  Console -.->|test| Governance
  UI -.->|test| Governance
  Kit -.->|test| Governance
  Mock -.->|test| Governance
~~~

<details>
<summary>Legend — ownership and verification boundary</summary>

Arrows run from a dependency to its consumer and carry the declared Maven scope. Dotted edges identify test dependencies of the governance module; they do not turn library implementations into local reactor modules. The POMs remain the factual source.

</details>


Governance also checks the local module graph and documentation. It does not change dependency scopes or replace module-level acceptance.

<a id="ui-traffic-capture-and-selection"></a>

## UI traffic capture and selection

The application modules own scenario selection, setup baselines, expected values, and verdicts. The shared `atf-ui` module performs browser actions and returns neutral observations; local adapters map those observations into scenario evidence. The selected Reporting library publishes the report model as Extent HTML.

~~~mermaid
flowchart LR
  subgraph Consumers[Local J1 consumer modules]
    Shop[Shop scenarios and expectations]
    ShopAdapter[Shop evidence adapter and assertions]
    AutoEx[AutoEx scenarios and expectations]
    AutoExAdapter[AutoEx evidence adapter and assertions]
  end
  UI[atf-ui<br/>browser actions and neutral observations]
  Reporting[Selected Reporting JAR]
  HTML[Extent HTML]
  Checks[Local component and UI checks]

  Shop -->|executes through| UI
  AutoEx -->|executes through| UI
  UI -->|returns observations| ShopAdapter
  UI -->|returns observations| AutoExAdapter
  Shop -->|owns| ShopAdapter
  AutoEx -->|owns| AutoExAdapter
  ShopAdapter -->|publishes local report model| Reporting
  AutoExAdapter -->|publishes local report model| Reporting
  Reporting -->|renders| HTML
  HTML -->|is inspected when selected| Checks
~~~

<details>
<summary>Legend — ownership and verification boundary</summary>

The UI module returns neutral observations to the application adapter. Shop and AutoEx own expectations, comparisons and scenario evidence; Reporting renders their supplied model, and selected consumer tests inspect the resulting artifact. These are runtime/evidence relationships rather than dependency arrows.

</details>

- Shop and AutoEx keep application journeys, setup baselines, business expectations, and verdicts in their local modules.
- `atf-ui` owns generic browser execution and neutral observations; it does not select business assertions or publish HTML.
- Reporting is a consumed binary dependency. It owns the generic report renderer; J1 verifies the integrations used by its local tests.

## Consumer acceptance and Extent reports

Practice Shop and AutoEx own their application scenarios. Their report-producing component and UI tests verify the J1 adapters, scenario evidence, generated Extent output, and browser-visible behavior selected by those suites. The local component tests keep assertion details in expandable report steps; browser checks verify the rendered report when the scenario requires it.

A green J1 consumer test is evidence for the executed local path and selected library version. It is not evidence that every library contract or every application journey was exercised. See the [testing guide](ATF_Testing_Guide.md), [reporting consumer guide](ATF_Reporting_Consumer.md), and [active feedback](feedback/README.md).

## Change control

When a local module, dependency, or report assertion changes, update the owning module README and the diagram or matrix above where the architecture changes. The documentation-edition governance compares the English and Romanian structures and links; documentation governance verifies that the described library coordinates match the root POM and local artifact pairs.
