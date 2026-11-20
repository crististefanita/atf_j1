# ATF J1 — register of active gaps

## At a glance

J1 owns the AutoEx/Shop tests and Reporting JAR integration; Reporting implementation and generic regressions are owned by J0. Delivered contracts are documented under [their owners](../ATF_Context_Atlas.md); this register retains only the gaps and closure paths.

The [synchronization protocol](../ATF_Workspace_Synchronization.md) inventories J0–J7: J0 owns the sources, J1 the web tests, and J2 remains WIP for Kafka/messaging with setup in Podman. Results and priorities do not transfer between them automatically.

## Library feedback

Findings about Core, Reporting, Console, Mock, and Test Kit go to their J0 owner. J1 retains domain issues and local acceptance. See the [library guide](../../lib/README.md).


## Top active feedback

Scores are priorities, not quality grades or coverage percentages. The formula is impact /40 + gap /30 + feasibility /20 + documentation debt /10; definitions and evidence remain with the owner.

| J1 criterion | Local owner | Remaining evidence |
|---|---|---|
| Launch identity | [J1-LAUNCH-CONTEXT-146](ATF_Reporting_Feedback.md#j1-launch-context-146) | real suite identity in audit metadata |
| Suite-scoped artifact catalog | [AUTOEX-CATALOG-EVIDENCE-146](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-catalog-evidence-146) | component and local browser checks passed; inspect comparison ownership in the artifact from a controlled suite run |
| AutoEx cart confirmation | [AUTOEX-UI-CART-CONFIRMATION-2026-11-19](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-ui-cart-confirmation-2026-11-19) | reproduce the timeout and observe cart state after the click |
| Shop lookup | [SHOP-BILLING-SANITY](ATF_Consumer_Business_Test_Focus_Feedback.md#shop-billing-sanity) | readiness budget and lookup-race oracles |
| Documentation contract fidelity | [DOC-LIBRARY-CONTRACTS-147](ATF_Documentation_Feedback.md#doc-library-contracts-147) | J1 prose, link and factual gates pass; producer chronology controls and the two J0 cross-workspace findings remain open |

[Reporting](ATF_Reporting_Feedback.md) and [Reporting Tests](ATF_Reporting_Tests_Feedback.md) point to the canonical J0 backlog and its current priorities. J1 retains reproduction and acceptance for its own JAR without a competing copy of the criteria.

## Verified contracts and controls

| Delivered contract | Verification authority |
|---|---|
| UI preparation and contextual screenshots | [UI-FIRST-PAINT-144](../ATF_UI_First_Frame_Verification.md) |
| AutoEx search with GET and fresh DOM | [AutoEx verification](../ATF_AutoEx_Search_Verification.md) |
| Strict JSON and exact numeric matching | [J1 Core](../ATF_Core_Consumer.md#complete-json-documents-and-faithful-evidence) · [J0 Mock](../../../atf_j0/atf-mock/README.md#json-strict-și-potrivire-numerică-exactă) |
| Package cohesion | [GOV-PACKAGE-COHESION-2026-11-19](ATF_Code_Maintenance_Feedback.md#gov-package-cohesion-2026-11-19) | whole-ATF 171/171; one package split and one bounded, reviewed Playwright exception |

The [Core contract](../ATF_Core_Consumer.md) defines the source and native
evidence in J0, JAR consumption in J1–J7, and separate acceptance. Resolved
distribution criteria and deterministic timeout evidence are in the owner's
documentation and baseline; no active Core defects remain in feedback.

The [atlas](../ATF_Context_Atlas.md), [architecture](../ATF_Architecture.md),
and [testing guide](../ATF_Testing_Guide.md) own the stable contracts and
starting evidence. Technical controls are in the [baseline](../ATF_Test_Changes_Baseline.md#verified-technical-controls), using the project's actual bases.

## AI design — separate assessment

[AI evaluation](README_AI_Evaluation.md) separates the existing comparator from the proposed agentic design. The design backlog does not expand the maintenance increment, and the [editorial note](../README_Atf_DocumentationBestPractices.md#editorial-review-rubric) does not certify implementation readiness.

## Retention, promotion, and verification

| Situation | Action and authority |
|---|---|
| contract already implemented | keep the rule in the owner's README; the index points to it through the [atlas](../ATF_Context_Atlas.md) |
| real gap | trigger, observation, owner, priority, and closure criterion in domain feedback |
| partial result | state the limit and remaining work; a passing guard does not close another scenario |
| verified Git control | [baseline](../ATF_Test_Changes_Baseline.md); do not rebase or extend it implicitly |

The [documentation protocol](../README_Atf_DocumentationBestPractices.md#retaining-and-deleting-feedback) and [TMP build guidance](../ATF_Build_And_Execution_Safety.md) govern each increment. Remove a criterion only after evidence and promotion of the stable contract.

<a id="cerințe-comune-preluate-din-j0--2026-11-02"></a>

## Coordination boundaries with J0

| J1 owner | Retained criteria | Boundary |
|---|---|---|
| [Test Kit](ATF_Common_Test_Support_Feedback.md) | Validation adoption diagnostics | code, API, and Unicode preview contract remain in J0 |
| [Documentation governance](ATF_Documentation_Feedback.md) | DOC-CONTEXT-DEPLOYMENT-111 (81) | J1 documentation gates pass; the J0 README inventory, retired J4 anchor, and producer chronology controls remain open |
| [AI](README_AI_Evaluation.md) | existing AI-EVAL/AI-WORKFLOW IDs | separate design; moving it does not authorize new functionality |
| [Android coordination](ATF_Consumer_Reporting_Handoff_Feedback.md) | J4 device/business responsibilities and evidence | J4 retains oracles and Android execution; J0 retains the generic renderer |

The Unicode preview contract is documented in the [Test Kit guide](../../lib/README.TestKit.md). Validation evidence remains specific to J1.
