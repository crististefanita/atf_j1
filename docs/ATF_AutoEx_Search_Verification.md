# AutoEx search — owned response and fresh DOM

## Scope and cause

J1 owns this consumer fix. Core C3 and Reporting J18 remain unchanged;
the source/test owners and independent consumers retain their existing boundaries.

| Observation | Evidence and limit |
|---|---|
| Original retained UI failure | The final PNG shows `This website is under heavy load (queue full)` at `/products?search=Blue%20Top` |
| Original HTTP status | Not captured in that UI-only receipt; no status is inferred from later successful calls |
| Real Search handler | `#submit_search` sets `window.location='/products?search='+search`; the result is a main-frame GET HTML document |
| Later HTML probe | HTTP 200; 12,976 characters; first product Blue Top |
| Later catalog API probe | `/api/productsList`: HTTP 200, responseCode 200, 34 products; Blue Top id 1, Rs. 500 |
| Later direct search API probe | POST `/api/searchProduct`, `search_product=Blue+Top`: HTTP 200, responseCode 200, one Blue Top result |
| Causal separation | Those healthy probes are separate observations and cannot certify the earlier failed search document |

The previous implementation waited for the product after URL commit, so a
terminal queue-full page exhausted the locator budget. Independent local
fixtures also exposed false PASS for HTTP 525 with a product, history-only URL
change with stale DOM, and a visible busy marker beside the product.

## Preserved execution contract

| Boundary | Current behavior |
|---|---|
| Activation | One native pointer; no test retry, pointer replay or recovery GET |
| Response | Main-frame Request identity owns method, URL and HTTP outcome; XHR and subframes are excluded |
| Destination | Same configured origin/path and exactly one decoded configured search parameter |
| DOM | New `performance.timeOrigin`, parsed document and visible configured first product |
| Terminal result | Non-2xx or visible queue-full remains FAIL with an explicit cause |
| Budget | Existing 700 ms component action budget and 20 s live action budget; no relaxed thresholds |
| Evidence | INFO response receipt, timed Click verdict, original exception chain and configured PNG policy |
| Missing response | Explicit `HTTP unobserved`; the request URL is identified as a request, without inventing response metadata |
| Ownership | `AutoExSearchDocument` scopes passive listeners through the existing UI `UiListenerScope`; no generic library change |
| Reporting | Existing collapsible assertions and default 240-character preview; complete evidence remains available |

The `queue full` fix improves classification and auditability; it cannot make
an unavailable public server healthy. A rejected response stops the journey
before downstream cart/account/payment operations.

## Permanent regressions

| Cases | Owner |
|---|---|
| delayed first product, replaced document, wrong product/query, one click | [existing readiness contracts](../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/pages/AutoExSearchReadinessComponentTest.java) |
| queue-full HTTP 200/503; HTTP 525 with matching product | [owned response contracts](../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/pages/AutoExSearchResponseComponentTest.java) |
| history-only stale DOM; visible busy marker precedence; hidden marker; successful GET | same response contracts |
| healthy independent API with absent/failed document; transport failure | same response contracts |

Run in an [owned TMP stage](ATF_Build_And_Execution_Safety.md), never in live:

```powershell
mvn -B -pl atf-test-auto-ex -am "-Dtest=AutoExSearchReadinessComponentTest,AutoExSearchResponseComponentTest" "-Dsurefire.failIfNoSpecifiedTests=false" test
```

## Verification record

| Gate | Result |
|---|---|
| Original implementation plus initial seven response cases | Five failures exposing missing classification/false PASS; original three readiness cases pass |
| First corrected response/DOM gate | 10 passed, zero failures/errors/skips, with the original budgets |
| Expanded first adjacency | 52 AutoEx and 5 UI passed; bounded search suite 13 passed; real cart journey 1 passed |
| Later neutral UI fixture witness | One of three unchanged pointer cases failed before activation while waiting for stability at 698.884001 ms; retained separately as [UI-FIRST-PAINT-144](ATF_UI_First_Frame_Verification.md) |
| Final bounded search suite | 15 passed, zero failures/errors/skips; existing three readiness cases plus twelve response cases, with one shared report |
| Final real UI cart journey | 1 passed, zero failures/errors/skips; search, quantity recalculation and removal on the public store, action 20 s/navigation 45 s unchanged |
| Final missing-response diagnostics | 3 passed: history-only, independent API and aborted transport; explicit unobserved HTTP, one pointer and independent request counts |
| Controlled runtime artifact | Two scenarios: healthy owned GET is PASS; HTTP 200 queue-full is FAIL with original cause/PNG; one GET each, no replay |
| Chromium rendering | Four artifacts checked offline: controlled, real UI, component and suite; comparisons initially collapsed, disclosure/table layout, four tabs, failure deep link and PNG viewer pass without JavaScript errors |
| Visual review | Actual browser captures inspected: failed Click, INFO HTTP/DOM receipt, queue-full PNG and separate phase execution/publication timing |
| Final governance | 42 passed, zero failures/errors/skips: test-change controls, readability, consumer architecture and physical Core/Reporting surfaces |
| Current documentation | All seven changed Markdown files pass current source validators for links/anchors, prose, structure, diagrams and dynamic paths |
| Final rendered artifacts | Four fresh artifact selections pass Chromium inspection; retained captures in `render-final-captures`, outcomes in `render-final.log` |
| Live reconciliation | Eleven guarded files promoted; all 372 relevant source/resource/POM/library inputs match the verified stage after the concurrent live commit |
| Fresh post-sync build | 4 browser component cases plus current-HEAD test-change governance passed from the new live snapshot, with zero failures/errors/skips |

- **Suite scope:** the bounded selector exists only in TMP and is not delivered.
  Canonical `AutoExComponentSuite` already selects both permanent classes through
  its existing component package; bounded acceptance exercises the same suite scope.
- **Retained individual failures:** the intermediate 54-case adjacency retained
  one input-fill error and one pre-activation native-click failure, before the
  search postcondition. [UI-FIRST-PAINT-144](ATF_UI_First_Frame_Verification.md)
  preserves them with the neutral UI witness; no green individual aggregate or
  hidden action/test retry is claimed.
- **Final diagnostic labels:** three focused cases verify `HTTP unobserved`
  and request-URL wording after the 15-case suite and live execution; action,
  route, DOM and deadlines are unchanged.

## Follow-up — initial viewport preparation

The later [UI-FIRST-PAINT-144 verification](ATF_UI_First_Frame_Verification.md)
resolves the adjacent initial-fixture boundary and corrects the validation
runner's silent Node override. With the SDK-default environment, all fifteen
AutoEx cases pass individually and through suite scope at the original limits.

The initial document now shares one 5 s preparation deadline across load,
native attachment and PNG capture. Search-owned response/DOM observation retains
its existing action deadline, request counts and independent negative oracles;
this follow-up does not rewrite the historical results above.

<details>
<summary><strong>Execution location and retained outputs</strong></summary>

All builds and artifacts belong to the owned TMP workspace
`ATF_AUTOEX_SEARCH_ACTIVE_DO_NOT_CLEAN_WYOHyf`; the captured J1 review base is
`94b88a3e4d3f9aa8ba300e16e99409d5b0da28ac`.

Live subsequently advanced to `f2aaa41e1f0939b93ccdab509492f862c09d18cc`.
A fresh `post-sync/atf_j1` snapshot retains that independent Git state;
the technical baseline adds a separate control without rebasing old records.

| Evidence | Retained entry |
|---|---|
| Initial independent failures | `search-component-baseline-atf_j1.log` |
| First full successful adjacency | `search-regression-fixed-oracle-atf_j1.log` |
| Final suite / public journey | `search-suite-owner-final-atf_j1.log` / `search-live-owner-final-atf_j1.log` |
| Missing-response label | `search-unobserved-atf_j1.log` |
| Unhidden preparation failures | `search-regression-final-atf_j1.log` / `search-regression-owner-final-atf_j1.log` |
| Actual browser audit | `render.log` and `render-captures` |
| Controlled artifact | [one healthy and one queue-full scenario](http://127.0.0.1:55726/autoex-search-controlled.html#test-view) |
| Real UI artifact | [public cart journey](http://127.0.0.1:55726/autoex-search-live.html#test-view) |
| Promotion | `delivery-plan.json` and Core guard manifest; source/document allowlist only, libraries unchanged |
| Fresh live snapshot | `post-sync-snapshot.json` and `search-post-sync-atf_j1.log`; current Git control is separate from the initial review |

Local review URLs require that retained review server; their unavailability
later does not remove the timestamped HTML or convert it into a new execution.

</details>

The original failure artifact remains immutable. Fresh outputs, logs and
captures are kept outside live; a later healthy journey does not rewrite it.

[Owner contract](../atf-test-auto-ex/README.md#catalog-search-confirmation)
and [current feedback](feedback/ATF_Consumer_Business_Test_Focus_Feedback.md)
track this consumer increment separately from library acceptance.
