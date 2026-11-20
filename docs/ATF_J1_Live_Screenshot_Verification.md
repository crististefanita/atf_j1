# J1 live screenshot acceptance — 2026-11-03

## Scope and outcome

One real AutoEx UI purchase and one real Shop guest UI/API purchase run
individually and through their existing canonical suites. All four invocations
pass; each execution produces one new Extent artifact.

| Application and launch | Selected case | Result | Assertions | PNG references | Review |
|---|---|---|---:|---:|---|
| AutoEx individual | AutoExPurchaseUiE2eTest | 1 PASS | 49 | 13 | [individual report](http://127.0.0.1:53827/autoex-individual.html#test-view) |
| AutoExE2eSuite, filtered | same class | 1 PASS | 49 | 13 | [suite report](http://127.0.0.1:53827/autoex-suite.html#test-view) |
| Shop individual | ShopPurchaseE2eTest | 1 PASS | 150 | 10 | [individual report](http://127.0.0.1:53827/shop-individual.html#test-view) |
| ShopE2eSuite, filtered | same class | 1 PASS | 150 | 10 | [suite report](http://127.0.0.1:53827/shop-suite.html#test-view) |

AutoEx registers during checkout, completes its demo order and removes the
disposable account. Shop completes a guest purchase with observed UI/API evidence.
The counts distinguish JUnit invocations, comparison rows and screenshot references.

## Exact suite selection

The external launcher selects the actual AutoExE2eSuite or ShopE2eSuite and
uses a post-discovery filter for the requested purchase class. It verifies
exactly one discovered case before any execution and requires one success,
zero skips/aborts and no container failure afterward.

| Boundary | Preserved behavior |
|---|---|
| Canonical code | Existing suite classes, annotations and production selectors remain unchanged |
| External selector | Lives only in TMP; discovery IDs retain the suite engine and actual canonical class |
| Execution | The filtered discovered plan is executed serially; unrelated suite cases are excluded |
| Evidence | Same business class and assertion population individually and through suite scope |
| Limit | These four runs do not certify every case in either complete suite |

## Complete context at 70 percent

All 46 screenshot references resolve to valid native PNGs with complete
1440 × 1000 viewport geometry, within the existing one-pixel engine rounding.
Overview records 2057 × 1429 CSS layout and DPR 0.70; no region capture or
after-capture crop is used.

| Browser inspection | Observed result |
|---|---|
| Assertions | Same 49/150 rows per application in both modes; comparisons initially collapsed and targeted paths open correctly |
| UI actions | Native ordered action tables expand with the existing dynamic column layout |
| Screenshots | Every reference decodes; first/middle/last viewer selections preserve the exact original PNG and every edge |
| Viewer context | Fit uses the available screen and original aspect ratio; Escape retains owner, disclosures and keyboard focus |
| Navigation | Tests, Category, Fails and Overview work without JavaScript errors |
| Visual review | Captures inspected from all four artifacts: AutoEx cart/store and Shop checkout/order context |
| Preview | The default remains 240; no assertion or action deadline is changed |

## Phase timing and execution limits

| Test body | Execution ms | Capture ms | Publication ms |
|---|---:|---:|---:|
| AutoEx individual | 31766 | 19038 | 375 |
| AutoEx suite | 32994 | 6399 | 269 |
| Shop individual | 15073 | 1560 | 860 |
| Shop suite | 10303 | 1397 | 505 |

These are captured phase axes, with final HTML flush excluded. JUnit duration
also covers resource acquisition and lifecycle work; it is not substituted for
the test-body execution axis. UI action/navigation limits retain 20 s/45 s.

Shop uses a temporary YAML copy changing only headless=false to true. Both
modules retain configured 70% zoom, demo inputs and native browser behavior;
the SDK-selected Node runtime is inherited without an agent override.

## Remaining improvement

The business and rendering acceptance above passes. The HTML does not retain
the actual canonical suite identity for these filtered runs; the AutoEx filename
also contains purchase-suite for an individual launch. The scoped
[J1 launch-context criterion](feedback/ATF_Reporting_Feedback.md#j1-launch-context-146--record-the-actual-launch-scope)
keeps this auditability improvement separate from generic renderer defects.

<details>
<summary><strong>Execution, binary and cleanup provenance</strong></summary>

| Item | Retained evidence |
|---|---|
| Owned TMP root | ATF_LIVE_SCREEN_AUDIT_ACTIVE_DO_NOT_CLEAN_85211735 |
| J1 captured base | f2aaa41e1f0939b93ccdab509492f862c09d18cc; existing local work retained |
| library artifact documentation base | 6bef40515954acd121c93a66e1bf544db14e97b5 |
| Individual runs | live-autoex-individual-atf_j1.log and live-shop-individual-atf_j1.log; Surefire XML retained |
| Canonical suite runs | suite-autoex.log, suite-shop.log and their discovery/metrics JSON receipts |
| Browser verification | render-audit.log, render-captures/audit.json and inspected PNG captures |
| Runtime launch context | run-context-probe.log; no actual canonical suite name in either suite artifact |
| Reporting | physical J18 JAR SHA-256 63655536DE4EBA02A8AF6CA765D445BBCE7D89967979FF5BF9247FDC0F79AD02 |
| Core | physical C3 JAR SHA-256 605407B9D40E67C9C352EE6E94DA758E50BD3CFB14CC0D7A0DE3359C71088447 |
| Document changes | Source/runtime/JAR bytes remain unchanged; only reviewed documentation is promoted |

Review URLs require the retained loopback server. Builds, reports and captures
remain in owned TMP; Chromium with inspected captures uses the previously
approved alternative to unavailable TAB MCP.

</details>
