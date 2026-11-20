# ATF J1 — active feedback register

## At a glance

This register tracks reproducible gaps in J1 code, documentation, and consumer
acceptance. J1 consumes the versioned libraries listed in [`lib`](../../lib/README.md);
record library observations by coordinate, version, hash, and local reproducer.

## Active findings

| Finding | Local owner | Next evidence |
|---|---|---|
| [UI-RECOVERY-AUDIT-01](ATF_Reporting_Feedback.md#ui-recovery-audit-01--inspectable-attempts-in-extent) | J1 recovery evidence and report acceptance | render recovered and terminal cases; verify one collapsed group expands fully and remains redacted |
| [J1-LAUNCH-CONTEXT-146](ATF_Reporting_Feedback.md#j1-launch-context-146) | Reporting metadata composition | capture the JUnit root and effective selection in audit metadata |
| [SHOP-BILLING-SANITY](ATF_Consumer_Business_Test_Focus_Feedback.md#shop-billing-sanity) | Shop billing journey | separate browser startup from lookup settlement timing |
| [AUTOEX-UI-CART-CONFIRMATION-2026-11-19](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-ui-cart-confirmation-2026-11-19) | AutoEx purchase journey | reproduce the public cart confirmation with a fresh browser |

## Delivered consumer evidence

| Contract | Current record |
|---|---|
| AutoEx catalog comparison and expandable Extent evidence | [Closed on 2026-11-20](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-catalog-evidence-146); controlled component and browser report checks passed |
| J1 documentation facts and consumed-library scope | [Closed on 2026-11-20](ATF_Documentation_Feedback.md#doc-library-contracts-147); at initial closure, focused proof 65/65 and Whole-ATF 179/179 in EN and RO; editions align across 56 documents |
| Local-first root overview and module inventory | [Closed on 2026-11-20](ATF_Documentation_Feedback.md#doc-root-overview-20261120); governance 187/187 in EN and RO and edition comparison is clean |
| UI screenshot context | [Live screenshot verification](../ATF_J1_Live_Screenshot_Verification.md) records its exact environment and evidence boundary |
| Consumer contracts | [Context atlas](../ATF_Context_Atlas.md), module READMEs, and executable tests define current behavior |

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
