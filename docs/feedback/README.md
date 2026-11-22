# ATF J1 — active feedback register

## At a glance

This register tracks reproducible gaps in J1 code, documentation, and consumer
acceptance. J1 consumes the versioned libraries listed in [`lib`](../../lib/README.md);
record library observations by coordinate, version, hash, and local reproducer.

## Active findings

| Finding | Local owner | Next evidence |
|---|---|---|
| [AUTOEX-UI-CART-CONFIRMATION-2026-11-19](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-ui-cart-confirmation-2026-11-19) | AutoEx purchase journey | reproduce the public cart confirmation with a fresh browser |
| [J1-RECOVERY-IDE-REPORT-PARITY-2026-11-22](ATF_Reporting_Feedback.md#j1-recovery-ide-report-parity-2026-11-22) | Consumer launch configuration | run both recovery components from IntelliJ and validate those emitted artifacts with the existing browser acceptance |

## Deferred AI design backlog

[AI evaluator and workflow gaps](README_AI_Evaluation.md) remain open design
criteria, outside the current maintenance priority order. The delivered
comparator is separate from the proposed ledger, lifecycle and report projection.

## Current contract owners

| Contract | Owner |
|---|---|
| Library identity and local acceptance | [Library catalog](../../lib/README.md) and module tests |
| Reporting and launch evidence | [Testing guide](../ATF_Testing_Guide.md) and consumer module READMEs |
| Complete screenshot evidence | [Capture contract](../ATF_Evidence_Capture_Contract.md) |
| Documentation and language editions | [Documentation standard](../README_Atf_DocumentationBestPractices.md) |

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
