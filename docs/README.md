# ATF J1 Documentation

This index describes the six-module J1 reactor and the five versioned libraries it consumes. Start with [Architecture](ATF_Architecture.md) for local modules and dependency scope, then use the relevant guide for implementation details.

## At a glance

| Need | Start here |
|---|---|
| Understand local modules and selected libraries | [Architecture](ATF_Architecture.md), [library catalog](ATF_Workspace_Synchronization.md#consumed-library-catalog), and the root POM |
| Follow a request or add an API scenario | [API test guide](ATF_Api_Test_Guide.md), [Core guide](ATF_Core_Consumer.md), and the consumer module README |
| Understand UI, API, and Extent output | [Reporting consumer guide](ATF_Reporting_Consumer.md), [reporting contract](ATF_Reporting_Contract.md), and [display contract](ATF_Reporting_Display_Contract.md) |
| Choose a test level and suite | [Testing guide](ATF_Testing_Guide.md) and [test annotations](ATF_Test_Infrastructure_Annotations.md) |
| Work with selected library artifacts | [Library index](../lib/README.md) and [artifact synchronization](ATF_Workspace_Synchronization.md) |
| See an active gap or closure condition | [Feedback register](feedback/README.md) and the linked owner document |
| Update documentation safely | [Documentation best practices](README_Atf_DocumentationBestPractices.md) and [build safety](ATF_Build_And_Execution_Safety.md) |

~~~mermaid
flowchart LR
  POM[Root POM] -->|selects versions| Libraries[Five library JAR/POM pairs]
  POM -->|declares| Modules[Six local J1 modules]
  Modules -->|own scenarios and tests| Evidence[Local test evidence and Extent reports]
  Governance[atf-tests] -.->|checks facts, links, and boundaries| Docs[English and Romanian documentation]
~~~

The root POM is authoritative for the reactor and dependency versions. The
module POMs define direct dependency scopes; the documentation explains how
those dependencies are used and how local behavior is verified.

## Reading order

| Topic | Documents |
|---|---|
| Architecture | [Architecture](ATF_Architecture.md) · [root README](../README.md) |
| Library contracts | [Core](ATF_Core_Consumer.md) · [Reporting](ATF_Reporting_Library_Atlas.md) · [Console](../lib/README.Console.md) · [Mock](../lib/README.Mock.md) · [Test Kit](../lib/README.TestKit.md) |
| Consumers | [Practice Shop](../atf-test-practice-shop/README.md) · [AutoEx](../atf-test-auto-ex/README.md) |
| Evidence and rendered reports | [Evidence contract](ATF_Evidence_Capture_Contract.md) · [Reporting consumer](ATF_Reporting_Consumer.md) · [Reporting contract](ATF_Reporting_Contract.md) · [Display contract](ATF_Reporting_Display_Contract.md) |
| Test design and execution | [API guide](ATF_Api_Test_Guide.md) · [Testing guide](ATF_Testing_Guide.md) · [annotations](ATF_Test_Infrastructure_Annotations.md) · [build safety](ATF_Build_And_Execution_Safety.md) |
| Governance and feedback | [ATF Tests](../atf-tests/README.md) · [active feedback](feedback/README.md) · [test-change controls](ATF_Test_Changes_Baseline.md) |
| Best practices and proposal | [Best practices](README_Atf_BestPractices.md) · [code practices](README_Atf_CodeBestPractices.md) · [documentation practices](README_Atf_DocumentationBestPractices.md) · [AI evaluation design](ATF_AI_Evaluation_Design.md) |

## Working rule for changes

~~~text
inspect live code and feedback → stage the smallest change → run the relevant governance and behavior checks → inspect generated evidence → reconcile EN/RO → selectively synchronize
~~~

Preserve active runs. Keep generated reports, caches, and temporary verification files outside source directories. The detailed procedure is in [Build and execution safety](ATF_Build_And_Execution_Safety.md).

## Documentation rules

- Module READMEs describe the local module's responsibility, public usage, and verification.
- Library guides describe the selected artifact and the contract visible to J1 consumers.
- English and Romanian editions keep the same section order, tables, diagrams, and relative link destinations.
- Diagrams show current POM dependencies and ownership; update them when code or module boundaries change.
- Stable capabilities belong in owner documentation. Feedback records only a reproducible unresolved gap and its closure evidence.
- Do not put quality scores, generated report names, or temporary run state in normative READMEs.
- Treat POMs, code, and tests as the factual sources. Do not document behavior that the selected tests do not establish.

## Rendered report verification

For a report-producing component or UI test, inspect the generated HTML against the assertions and scenario:

1. Confirm the expected test and scenario hierarchy appears.
2. Expand the relevant test and assertion groups; verify all expected steps and messages are present.
3. Check visible status, timing, and evidence against the test result.
4. For browser-visible behavior, verify the rendered report and its controls in the browser.
5. Record the selected library coordinate and the local test that establishes the behavior.

## Terms

| Term | Meaning in this documentation |
|---|---|
| J1 reactor | The six Maven modules listed by the root POM |
| Consumed library | A versioned JAR/POM pair under lib selected by the root POM |
| Consumer acceptance | Evidence produced by a J1 module for the scenarios it actually runs |
| Governance | Tests in atf-tests that check local architecture, documentation, and artifact identity |
| Feedback | A register of open, reproducible gaps and their closure criteria |

## Verification

Run the documentation edition checks from the reactor root:

~~~powershell
node atf-tests/tools/documentation-editions.mjs compare <english-root> <romanian-root>
node atf-tests/tools/documentation-editions.test.mjs
~~~

The governance suite also checks the documented library catalog and direct documentation relationships. See the [ATF Tests README](../atf-tests/README.md) for the exact gates and the [test-change baseline](ATF_Test_Changes_Baseline.md) for protected-test controls.
