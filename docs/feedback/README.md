# ATF J1 — active feedback register

## At a glance

This register tracks reproducible gaps in J1 code, documentation, and consumer
acceptance. J1 consumes the versioned libraries listed in [`lib`](../../lib/README.md);
record library observations by coordinate, version, hash, and local reproducer.

## Active findings

| Finding | Local owner | Next evidence |
|---|---|---|
| [J1-LAUNCH-CONTEXT-146](ATF_Reporting_Feedback.md#j1-launch-context-146) | Launcher metadata integration | source class/method and suite ancestry are delivered; supply actual launcher selectors and discovered count through a neutral per-launch snapshot |
| [AUTOEX-UI-CART-CONFIRMATION-2026-11-19](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-ui-cart-confirmation-2026-11-19) | AutoEx purchase journey | reproduce the public cart confirmation with a fresh browser |
| [J1-RECOVERY-IDE-REPORT-PARITY-2026-11-22](ATF_Reporting_Feedback.md#j1-recovery-ide-report-parity-2026-11-22) | Consumer launch configuration | run both recovery components from IntelliJ and validate those emitted artifacts with the existing browser acceptance |

## Delivered consumer evidence

| Contract | Current record |
|---|---|
| Recovery scenario clarity and report-selector ownership | [Closed on 2026-11-21](ATF_Code_Maintenance_Feedback.md#oop-recovery-scenarios-2026-11-21); named typed scenarios and test-scoped report selectors passed component checks 6/6 Shop, 5/5 AutoEx, and Whole-ATF governance 191/191 in both editions |
| Recovery evidence in local consumer Extent probes | [Closed on 2026-11-21](ATF_Reporting_Feedback.md#ui-recovery-audit-01--inspectable-attempts-in-extent); both consumers verify collapsed state, expand-all, ordered trace rows, and redaction in both editions; each local probe builds a synthetic outcome-bearing step and does not establish suite-generated report output |
| Typed recovery evidence projection | [Closed on 2026-11-22](ATF_Code_Maintenance_Feedback.md#j1-recovery-trace-typing-2026-11-21); consumer-owned projections separate semantic operation from event and use typed durations; common UI and Reporting APIs are unchanged |
| Actual Maven recovery component reports | [Closed on 2026-11-22](ATF_Reporting_Feedback.md#j1-recovery-runner-trace-2026-11-21); Shop and AutoEx browser acceptance checks both outcomes, expanded assertion rows, ordered trace fields, disclosure behavior, redaction, and verdict. IntelliJ output-root parity remains a separate open finding |
| Shop postal lookup settlement timing | [Closed on 2026-11-21](ATF_Consumer_Business_Test_Focus_Feedback.md#shop-billing-sanity); three 13-case runs pass, with race settlement measured at 494–524 ms against the 1,100 ms budget and browser startup/navigation measured separately |
| AutoEx catalog comparison and expandable Extent evidence | [Closed on 2026-11-20](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-catalog-evidence-146); controlled component and browser report checks passed |
| J1 documentation facts and consumed-library scope | [Closed on 2026-11-20](ATF_Documentation_Feedback.md#doc-library-contracts-147); current Whole-ATF governance is 191/191 in EN and RO; all maintained Markdown structures and link destinations match |
| Local-first root overview and module inventory | [Closed on 2026-11-20](ATF_Documentation_Feedback.md#doc-root-overview-20261120); current governance is 191/191 in EN and RO; Markdown edition structure remains aligned |
| Project maps and standalone verification records | [Closed on 2026-11-21](ATF_Documentation_Feedback.md#doc-verification-record-scope-20261120); durable contracts remain with their owners and governance rejects dated and undated report forms |
| Documentation edition review and worktree comparison | [Verified on 2026-11-21](ATF_Documentation_Feedback.md#doc-edition-comparator-20261120); 50 documents pass the commit-pinned and live worktree checks, Node tests 11/11 cover CRLF/LF normalization and semantic drift, and library POMs remain byte-exact |
| Screenshot evidence policy | [Evidence capture contract](../ATF_Evidence_Capture_Contract.md), the UI module guide, and native browser geometry tests define the current behavior |
| Consumer contracts | Module READMEs and executable tests define current behavior |

## Record a library observation

1. Reproduce it in the smallest affected J1 test against the selected physical
   JAR.
2. Record the Maven coordinate, version, JAR/POM hashes, expected and observed
   behavior, and report path when applicable.
3. Keep application policy and adapter findings with the local module. Describe
   library behavior through its published guide and the evidence J1 can inspect.

## Closure and history

Move a stable rule into the relevant README and remove a feedback item only when
its closure evidence passes. Verified test-change controls and their Git bases
remain in the [baseline](../ATF_Test_Changes_Baseline.md); do not infer a new run
from a copied folder or a renamed artifact.
