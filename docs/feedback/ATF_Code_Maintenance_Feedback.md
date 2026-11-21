# Code maintenance feedback

<a id="gov-package-cohesion-2026-11-19"></a>
## GOV-PACKAGE-COHESION-2026-11-19 — reconcile existing cohesion hotspots

**Status: Verified on 2026-11-20; owners:** `atf-tests` governance and `atf-ui` package owners.

The 2026-11-19 suite run recorded 163 tests: 161 passed and two package-cohesion checks failed. The fixes moved the documentation checker to its responsibility package and retained a reviewed exception for the stable Playwright boundary.

On 2026-11-20, earlier reruns passed 177/177 and 179/179. The 186/186 run
added root-README governance and an explicit guard for mandatory code-quality
gates. It is retained as historical evidence; the later context-artifact
governance re-verification is recorded below.

| Historical hotspot | Resolution | Current control |
|---|---|---|
| `atf.api.tests.governance.documentation` had 9 test classes against the limit of 8 | Moved `DocumentationReadmeStructureGovernanceTest` into `atf.api.tests.governance.documentation.structure` | The general eight-class limit and negative mutants remain active |
| `atf.ui.playwright` had 9 production and 10 test classes against the limit of 8 | Kept the public Playwright entry points, action owners, session, and evidence recorder together under a reviewed exception | Revisit if unrelated types enter or a boundary becomes independently replaceable; remove the exception when a split needs no new cross-package API |

The exception documents the actual behavioral boundary; it does not waive package cohesion globally. The E2E Extent report check remains classified in the consumer E2E package.

The initial 2026-11-20 verification ran `WholeAtfGovernanceSuiteTest` from the Git-backed canonical TMP staging layout against the review bases then in use:

- `main` (`4ec3e14b357e0968776399477592fd13c3713631`): `mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=4ec3e14b357e0968776399477592fd13c3713631" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test`
- `main_RO` (`754eb315045e565c150ca819498cf63154bee9b4`): `mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=754eb315045e565c150ca819498cf63154bee9b4" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test`

Those executions passed 186/186 in each edition and are historical; their
commands and bases above are retained as the original proof.

### Previous committed re-verification — 2026-11-20

After the context-artifact cleanup and governance update, the complete
`WholeAtfGovernanceSuiteTest` passed 190/190 in both live editions, with no
failures, errors, or skipped tests:

- `main`: `e1504f69fdbfefe2670236024cb93f92b9f69ac5`
- `main_RO`: `3520c2cae2481d1eba6d05c54559259ebd52c4e7`

- Documentation-edition Node tests passed 10/10; the Git-backed TMP comparison
  reports 0 problems across 52 documents. A comparison of the existing live
  worktrees flagged two `.mjs` files, but their Git blob IDs match and their
  normalized text is equal; [documentation feedback](ATF_Documentation_Feedback.md#doc-edition-comparator-20261120)
  records this as worktree line-ending drift.
- The governance result does not include the full `AutoExE2eSuite`; consumer
  evidence remains tracked separately where suite-scoped artifacts are required.
- After the feedback edits, focused documentation governance passed 18/18 in
  both editions on 2026-11-21.

### Focused consumer checks — 2026-11-21

Fresh Git-backed EN and RO review worktrees based on
`72c387e98735b03fe1afd65cab1913b76b1d86b4` and
`07a1fac012bbdab02916a2ef6d6cd8a735b3d3c0` passed the focused consumer checks:

- `ShopBillingSynchronizationComponentTest`: 13/13.
- `ShopBackendRecoveryComponentTest`: 6/6; `AutoExBackendRecoveryComponentTest`: 5/5.

### Current whole-reactor governance — 2026-11-21

After the recovery-report documentation correction, `WholeAtfGovernanceSuiteTest`
passed 190/190 with no failures, errors, or skips at these reviewed commits:

- `main`: `94434f1458171ada958e83f3577f9d6a9f776203`.
- `main_RO`: `06709dde637f3a08929932d6bee8130bc1eefed8`.

The test-change controls used the focused-review bases above. Documentation
edition Node tests then passed 10/10 in both editions; the Git-backed TMP pair
comparison found 0 problems across all 50 maintained Markdown documents.

These results cover local fixtures and report-rendering probes. The public AutoEx
cart finding remains open, and no public-site E2E suite result is inferred.

<a id="oop-recovery-scenarios-2026-11-21"></a>
## OOP-RECOVERY-SCENARIOS-2026-11-21 — make component scenarios and report ownership explicit

**Status: Closed on 2026-11-21; owners:** AutoEx and Shop component-test maintainers.

The recovery component tests used positional primitive arguments to represent
response status, capture policy, expected readiness, validation mode, and report
rendering. They now pass a named `RecoveryScenario` record with outcome enums and
scenario factories. The parameterized validation cases use enum sources, so the
test inputs stay typed from declaration through execution.

AutoEx report-page selectors and disclosure checks now live in the test-scoped
`AutoExRecoveryExtentReportProbe`, alongside the existing Shop report facade.
`ConsumerModuleArchitectureGovernanceTest` enforces that ownership boundary.
This is test-code organization only; consumer runtime behavior and reporting
contracts were not changed.

The controlled verification passed on the source state recorded in these commits:

| Edition | Source commit | Shop recovery component | AutoEx recovery component | Whole-ATF governance |
|---|---|---:|---:|---:|
| EN | `9f55590530cef94999d4ca3cd34e916bbca5f440` | 6/6 | 5/5 | 191/191 |
| RO | `0716679d7f2809b21ae8d395d623fc20d1249871` | 6/6 | 5/5 | 191/191 |

- `ConsumerModuleArchitectureGovernanceTest` passed 14/14 in each edition,
  including the new selector-ownership check.
- The protected test-change control tracks the AutoEx component, its report
  facade, and the architecture guard.
- The Shop component source remains outside the control's established
  protected-path set. Its 6/6 result is recorded separately and is not
  described as baseline-protected.

The first post-update governance run flagged this entry's four-sentence
paragraph against the rapid-scan prose limit. That text is now split into
bullets. The final rerun passed 191/191 in both editions with no failures,
errors, or skipped tests.
