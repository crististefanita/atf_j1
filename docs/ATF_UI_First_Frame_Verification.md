# UI first frame — preparation, runtime identity and native input

## Scope

J1 owns the UI helper and AutoEx fixture integration. Core C3 and Reporting
J18 retain their existing physical pairs; this increment does not alter
business actions, reporting assets or the default 240-character preview.

| Observation | Meaning and limit |
|---|---|
| Retained UI-FIRST-PAINT-144 logs | Native Fill/Click expired at 700 ms before their postcondition; the exact historic host/GPU scheduling cannot be reconstructed |
| Controlled renderer work after LOAD | A 1200 ms fixture-only initialization reproduces both refusals, with zero trusted events |
| Prepared viewport comparison | Same input cap; native click/fill complete in 187/57 ms after an explicit viewport preparation |
| Native PNG oracle | Current-document lime pixels after controlled initialization, expected viewport size at 70/100%, and fresh red/blue replacements |
| Actionability oracle | Disabled, covered, moving and readonly targets remain refused; preparation neither changes a target nor pre-dispatches input |
| Validation runner discrepancy | The external runner silently forced Codex Node 24.22.1; the published Playwright Java 1.49.0 driver contains Node 22.11.0 |
| Runtime A/B, same candidate and 384 MiB heap | Forced agent Node: 34/39; SDK default: 39/39; no deadline, assertion or source change between these executions |
| Interpretation | Restore the actual SDK environment for acceptance; the comparison is not a universal claim that every Node 24 installation is incompatible |

The source fix and runner correction address separate boundaries. A document
LOAD is not evidence of a rendered viewport; a validation environment override
is not evidence that the IDE's default driver behaves the same way.

## Execution contract

| Stage | Owner | Limit |
|---|---|---|
| Load or replace the fixture document | `PlaywrightPagePreparation` | existing navigation deadline starts once |
| Attach the native root and capture viewport PNG | same preparation scope | borrow the remaining deadline; animations remain allowed |
| Return a receipt | same scope | late capture or capture failure cannot authorize input; PNG bytes are defensive copies |
| Fill/Click plus declared observation | existing native action recorder | unchanged 700 ms fixture cap; one trusted activation and no replay |
| Search-produced document | AutoEx runtime | retains the original shared action deadline; initial preparation grants no extra time |

Preparation is explicit and does not inspect a business target. It returns
current pixels and URL, with no fabricated recorder PASS; native visibility,
stability, hit testing, readonly state and postconditions remain independent.

## Permanent tests

| Owner | Independent oracle |
|---|---|
| [Preparation clock tests](../atf-ui/src/test/java/atf/ui/playwright/PlaywrightPagePreparationTest.java) | one budget across load/attachment/capture, exact expiry, original failure identity and defensive bytes |
| [Preparation browser tests](../atf-ui/src/test/java/atf/ui/playwright/PlaywrightPagePreparationComponentTest.java) | actual PNG pixels, cold initialization, trusted events, unchanged document mode and refused targets |
| [Native postcondition tests](../atf-ui/src/test/java/atf/ui/playwright/PlaywrightClickPostconditionComponentTest.java) | one pointer, false observation, missing/ambiguous target and genuinely uncommitted child response |
| [Existing action clock tests](../atf-ui/src/test/java/atf/ui/playwright/ObservationBudgetComponentTest.java) | pointer and successive confirmations never renew the measured deadline |
| [AutoEx readiness](../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/pages/AutoExSearchReadinessComponentTest.java) | fixture URL/PNG, delayed product, wrong route/product and repeated document replacement |
| [AutoEx response contracts](../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/pages/AutoExSearchResponseComponentTest.java) | owned response, terminal outcomes, missing response, one GET and one trusted click |

Run only in an [owned TMP stage](ATF_Build_And_Execution_Safety.md):

```powershell
mvn -B -pl atf-ui -am "-Dtest=PlaywrightPagePreparationTest,PlaywrightPagePreparationComponentTest,PlaywrightClickPostconditionComponentTest,ObservationBudgetComponentTest" "-Dsurefire.failIfNoSpecifiedTests=false" test
```

## Verification status

| Gate | Observed result |
|---|---|
| Initial individual native UI gate | 24 passed, zero failures/errors/skips |
| Initial individual AutoEx gate with forced agent Node | 12 passed, three failures after activation/transport observation; retained as an environment witness |
| Same-code bounded suites with forced agent Node | 34/39; five failures, no skips or container failures |
| Same-code bounded suites with SDK Node | 39/39; no skips or container failures, unchanged 700 ms/5 s and heap |
| Final individual, strengthened trusted-event/frame cleanup | UI 24/24 and AutoEx 15/15; no failures/errors/skips |
| Final suite, latest source | 39/39, fifteen successful containers; no failures/skips |
| Adjacent governance | 42/42: test changes, readability, consumer architecture and physical Core/Reporting surfaces |
| Verified-control follow-up | Current test-change governance passes after moving UI-FIRST-PAINT-144 to the baseline |
| Chromium artifact audit | Four fresh artifacts: controlled UI, both individual AutoEx classes and their one shared suite report; no JavaScript errors |
| Visual acceptance | Actual browser captures inspected: preparation/action tables, native refusal PNG and individual/suite comparisons |

The SDK-default suite produced one new shared AutoEx artifact for its fifteen
consumer cases. Unit tests remain a separate category; native UI library
contracts are not given consumer E2E reporting semantics.

<details>
<summary><strong>Retained build and diagnostic provenance</strong></summary>

Owned TMP workspace: `ATF_UI_FIRST_PAINT_ACTIVE_DO_NOT_CLEAN_3182723c`.
Captured J1 review base: `f2aaa41e1f0939b93ccdab509492f862c09d18cc`.

| Evidence | Retained entry |
|---|---|
| Native startup observations | `observations/all.json` and per-case JSON/native PNG files |
| Initial source gates | `ui-preparation-first-atf_j1.log`, `ui-preparation-individual-atf_j1.log` |
| Agent-runtime suite | `suites.log`, `suite-codex-node24-metrics.json`, `suite-gc.log` |
| SDK-runtime suite | `suites-sdk.log`, `suite-sdk-metrics.json`, `suite-sdk-gc.log` |
| Final individual gate | `ui-preparation-sdk-individual-atf_j1.log` |
| Final suite | `suites-final.log`, `suite-final-metrics.json`, `suite-final-gc.log` |
| Governance | `ui-preparation-governance-atf_j1.log`; verified-control follow-up retained separately |
| Rendering | `render-audit.log` and `render-captures`; targeted disclosure paths, no global Expand all |
| Controlled UI | [one healthy case and one intentional native refusal](http://127.0.0.1:52278/first-frame-ui.html#test-view) |
| Individual AutoEx | [readiness](http://127.0.0.1:52278/first-frame-readiness.html#test-view), [owned responses](http://127.0.0.1:52278/first-frame-response.html#test-view) |
| Suite AutoEx | [one shared artifact for fifteen cases](http://127.0.0.1:52278/first-frame-suite.html#test-view) |

Review URLs require the retained loopback server; timestamped HTML remains
inside the owned TMP workspace. Chromium and inspected captures use the user-approved
alternative to unavailable TAB MCP.

Browser captures and full failed logs remain outside live. A later successful
execution never rewrites the earlier artifact or its original cause.

</details>

[UI owner contract](../atf-ui/README.md#explicit-fixture-preparation) and
[current feedback](feedback/ATF_UI_Interaction_Feedback.md) retain the execution boundary.

## Full-context screenshot follow-up

Screenshots retain the full page viewport and surrounding context at the configured
scale; Shop/AutoEx journeys use 70%. Android retains its whole display, while
the report viewer fits the complete source image with every edge and its original aspect ratio.

| Gate | Status |
|---|---|
| Capture-path audit | Shop/AutoEx use page capture without a region clip; Android uses full-display ADB screencap |
| Permanent pixel oracle | Both 70/100% cases pass, with every corner and additional lower-page context at 70% |
| Existing full-document contract | 1/1 passes; explicit full-page output retains document height |
| Consumer sanity | AutoEx 2/2 and Shop 2/2 pass; configuration, native geometry and complete PNG dimensions |
| Governance | 8/8 passes; verified-control follow-up is recorded separately |
| Chromium rendering | Three fresh artifacts pass, with no JavaScript errors; comparisons, tabs, timing, failure link and whole-image fit checked |
| Visual review | Actual healthy/refused viewer captures and Shop assertions inspected; all four frame edges remain visible |
| Deployment | Test/document increment only; Core C3 and Reporting J18 bytes retain their existing provenance |

<details>
<summary><strong>Follow-up provenance</strong></summary>

Owned TMP workspace: `ATF_SCREEN_CONTEXT_ACTIVE_DO_NOT_CLEAN_838c4418`.
The captured J1 Git base remains `f2aaa41e1f0939b93ccdab509492f862c09d18cc`;
prior UI-FIRST-PAINT-144 evidence above is retained unchanged.

| Evidence | Retained entry |
|---|---|
| Components and governance | `screen-components.log`, `screen-governance.log`, `screen-context-atf_j1.status.json` |
| Pixel and viewer artifacts | `controlled-report-final`, `render-audit.log`, `render-captures` |
| Whole-context UI report | [70% complete viewport and intentional native refusal](http://127.0.0.1:64792/screen-context.html#test-view) |
| Consumer artifacts | [AutoEx geometry](http://127.0.0.1:64792/screen-context-autoex.html#test-view), [Shop geometry](http://127.0.0.1:64792/screen-context-shop.html#test-view) |
| Retained TMP probe correction | Initial external probe compared native DPR `0.699999988079071` as a decimal string; final probe uses the existing independent geometry precision `1e-6`, with no source threshold or deadline change |

Review links require the retained loopback server. Chromium and inspected captures
use the approved alternative to unavailable TAB MCP; Android capture was inspected
in source, without claiming a new device execution.

</details>
