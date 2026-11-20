# ATF Java — AI evaluation against the current code

## At a glance

**Quick links:** [delivered versus proposed](../../atf-validation/README.md#ai-comparator--implemented-contract) ·
[gaps](#remaining-evaluator-gaps) ·
[readiness](#readiness-against-the-current-code) · [checks](#verification-of-the-delivered-comparator)

This document separates AI evaluator gaps from implementation readiness.
The target design and patterns extracted from ATF1/.NET are in the
[project AI design](../ATF_AI_Evaluation_Design.md).

The AI backlog is not part of the active priority order in the shared register.
The proposed implementation preserves J1's existing boundaries; the absence
of a new module is not a gap or a penalty.

| Status | Owner / boundary |
|---|---|
| Implemented and tested | [AI comparator in ATF Validation](../../atf-validation/README.md#ai-comparator--implemented-contract): separate expected values, typed comparisons, hard-fail, invocation evidence, allowlisted grounding, and immutable snapshot |
| Partial | Whole-ATF governance and `testChanges` scopes; a complete proposal/handoff does not exist yet |
| Open | Grounding ambiguity, run ledger, lifecycle, review/state, business rubric, reporting, and regressions |
| Design only | agentic composition in existing modules, guards, and conceptual YAML; these are not delivered options |

An implementation closes only with positive and negative evidence at the
correct boundary. Moving a document, adding a class, or producing a polished
report does not raise the implementation score. Governance test changes follow
the owner rule in the [ATF Tests README](../../atf-tests/README.md#test-change-governance).

<a id="gap-uri-rămase-pentru-evaluator"></a>
## Remaining evaluator gaps

| ID | Missing requirement | Closure criterion |
|---|---|---|
| `AI-EVAL-04` | ambiguous grounding classification | evaluator emits `AMBIGUOUS` with a reason and reference; a negative case verifies the verdict and that it is not promoted to `SUPPORTED` |
| `AI-EVAL-05` | review/state | the action and transitions are evaluated separately, including missing review and forbidden transitions |
| `AI-EVAL-06` | invocation/fallback integration in the lifecycle | the run correlates real/mock/fallback mode, output, and evidence; missing or misclassified evidence fails explicitly |
| `AI-EVAL-07` | business AI catalog and per-case rubric | versioned rules and negative cases cover intent/review/state; hard-fail takes precedence regardless of score |
| `AI-EVAL-08` | run-level evidence ledger | index/correlation/trace links invocation, grounding, and snapshot; missing or foreign references are rejected |
| `AI-EVAL-09` | publication and reporting parity | the AI snapshot is faithfully published in Extent/fallback; tests compare the same semantics and mismatch diagnostics |
| `AI-EVAL-10` | regression delta | comparator between versioned snapshots with new/fixed/changed cases and an incompatible baseline |
| `AI-EVAL-11` | AI coverage governance | every new public contract has positive and negative evidence; the guard identifies removed evidence |
| `AI-EVAL-12` | UI proof | the browser verifies visible review/state/evidence correlated with the snapshot and without duplicate statements |

<a id="gap-uri-de-design-pentru-workflow-ul-agentic"></a>
## Agentic workflow design gaps

| ID | Gap | Priority | Required evidence |
|---|---|---:|---|
| `AI-WORKFLOW-01` | no bounded change proposal for the agent | P1 | valid/invalid proposal and diagnostics |
| `AI-WORKFLOW-02` | handoff is not machine-readable | P1 | complete handoff with diff/gates/evidence |
| `AI-WORKFLOW-03` | agentic proposal lacks owner/layer/proof-plan validation | P1 | fail-closed governance tests, separate from the general module inventory |
| `AI-WORKFLOW-04` | agent lacks standard compiled feedback | P1 | feedback with rule, fact, fix, and next proof |
| `AI-WORKFLOW-05` | agent-delta documentation impact has no executable guard | P2 | documentation drift proof |
| `AI-WORKFLOW-06` | score separation is designed but lacks two runtime snapshots | P1 | workflow score and AI quality have distinct verdicts, not calculated from agent text |
| `AI-WORKFLOW-07` | pretraining is guidance, not a contract | P2 | bounded cold-baseline card |
| `AI-EVAL-13` | generated test is not checked against observed facts | P1 | literal verdict guard plus evidence binding |
| `AI-EVAL-15` | regression feedback is not an agent handoff | P2 | semantic delta → next proof |

These requirements describe a future design, not a delivered module or option.
Any implementation remains a separate change with its own owner, boundary, and
positive/negative evidence.

## Readiness against the current code

The assessment below concerns implementation readiness, not a model quality
score or the central register's priorities. The implemented contract remains
with its owner; proposed design is not evidence of delivery.

<a id="scorecard-executiv"></a>
### Executive scorecard

Implementation remains **50/100**, according to the existing assessment: the
comparator, invocation provenance, and typed snapshot are delivered; `AMBIGUOUS`
classification, the run ledger, AI lifecycle, and report projection are missing.
This editorial cleanup does not recalculate readiness or close requirements.

The executable contract is in [Validation](../../atf-validation/README.md#ai-comparator--implemented-contract);
generic [reporting](../ATF_Reporting_Contract.md), [UI observation](../../atf-ui/README.md),
and [governance](../../atf-tests/README.md) infrastructure does not by itself
prove an AI integration. The gap tables above retain the ID, boundary, and
missing evidence; design and conceptual options remain in the project document.

### Adoption plan in the correct order

1. evaluator-produced AMBIGUOUS + negative evidence
2. run-level evidence ledger + lifecycle correlation
3. versioned business catalog: intent, review/state, and hard-fail
4. semantic UI/API/AI checkpoint + publication and Extent/fallback parity
5. regression delta + proposal/handoff guards

The first pilot uses the existing comparator, one controlled-input case, and
two negative outputs. Minimum evidence is component/integration; the browser
later verifies the observable effect. The real provider needs its own
integration evidence and does not replace the deterministic matrix.

## Closure and recalibration criteria

- `AMBIGUOUS` must be produced by the evaluator and tested negatively, not just declared in an enum.
- The run ledger links invocation, output, grounding, and snapshot; missing or foreign references fail explicitly.
- Publication projects the AI snapshot without recalculating the verdict and preserves Extent/fallback parity.
- Versioned cases verify intent, review/state, and hard-fail; the real provider has its own integration evidence.
- The agentic workflow has valid and invalid proposal/handoff cases; it cannot change its own gates.
- At closure, the demonstrated contract moves to the owner's README and leaves this backlog.

Domain ordering is in the [feedback register](README.md); contracts and
designed options are in the [AI design](../ATF_AI_Evaluation_Design.md).

## Verification of the delivered comparator

| Question | Source and evidence | Limit |
|---|---|---|
| Is expected data and the verdict independent of the renderer? | [Evaluator](../../atf-validation/src/main/java/atf/validation/ai/AiEvaluationEvaluator.java) and [AiEvaluationEvaluatorTest](../../atf-validation/src/test/java/atf/validation/ai/AiEvaluationEvaluatorTest.java) | local comparison; does not invoke an AI provider |
| What does the module publish? | [Validation contract](../../atf-validation/README.md#ai-comparator--implemented-contract) and [local check](../../atf-validation/README.md#quick-verification) | the snapshot is not yet an AI projection in Extent |
| What remains design only? | [AI design](../ATF_AI_Evaluation_Design.md) and the criteria in this feedback | documenting conceptual YAML does not make it a runtime option |

The existing readiness score is not recalculated by an editorial improvement.
The quality of this README and the completeness of the proposed functionality
are separate assessments, according to the [documentation rubric](../README_Atf_DocumentationBestPractices.md#editorial-review-rubric).
