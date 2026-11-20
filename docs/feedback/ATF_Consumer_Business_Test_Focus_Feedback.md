# Consumer business verification — current status

## AutoEx search — delivered contract

The [AutoEx search record](../ATF_AutoEx_Search_Verification.md) owns the
GET/fresh-DOM contract, the original incident and the regression evidence.
The [live purchase receipt](../ATF_J1_Live_Screenshot_Verification.md) records
the separate AutoEx and Shop purchase and rendering checks.

<a id="autoex-catalog-evidence-146"></a>
## AUTOEX-CATALOG-EVIDENCE-146 — bind comparison evidence to its observed exchange

**Status: Verified and closed on 2026-11-20.** The controlled suite artifact
now proves comparison ownership and browser expansion in the same report.

The source fix and focused component and local-browser report checks pass.

- The valid report assigns the comparison once to the selected GET despite a
  same-path POST and repeated GET.
- Missing or duplicate-sequence evidence produces a failed API-evidence assertion
  without attaching the business comparison.
- The browser check confirms that the assertion group and request details expand
  in the rendered Extent report.
- Component and local-browser evidence is recorded below; the controlled
  suite-scoped artifact closes the remaining criterion.

- [x] Run the controlled capture and inspect its generated report to prove the
  comparison appears only under the selected observed exchange.
- [x] Run the correlation regression for method, path and captured sequence;
      confirm missing or ambiguous capture fails and checks are not duplicated
      across path matches.
- [x] Keep the behavior in AutoEx main support and consumer tests; generic renderer and ordinal contracts remain owned by the Reporting library;
  J1 verifies them through its local consumer fixtures and rendered reports.
- [x] Run the component and local-browser presentation checks; inspect the
  generated report for one comparison owner, preview limit 240, no synthetic
  exchange, and expandable API/request evidence.
- [x] Inspect the suite-scoped Extent artifact from a controlled suite run and
  confirm it retains the same comparison ownership and expandable hierarchy.

**Current evidence**

- On 2026-11-20, the two selected correlation cases in `AutoExRuntimeBoundaryComponentTest` passed 2/2 with no failures or errors in both canonical TMP editions.
- The run generated valid, missing-capture, and ambiguous-capture HTML reports under `atf-test-auto-ex\artifacts\reports\j1-extent-api-20261120` in each staged checkout.
- `AutoExCatalogReportPresentationE2eTest` passed 1/1 in each edition; `PlaywrightPagePreparationTest` passed 7/7.
- The browser test expanded the scenario, test phase, catalog API step, API validation, selected GET, and API assertion group; it confirmed the assertion starts collapsed, becomes visible on expansion, and request details open.
- Component assertions confirmed one comparison owner on the selected GET, four captured exchanges, no synthetic fifth exchange, and the 240-character preview limit.
- On 2026-11-20, `AutoExE2eOverviewNarrativeComponentTest` passed 1/1, with no
  failures, errors, or skips, in both the `main` and `main_RO` TMP stages. Its
  explicit suite-scoped JUnit launch ran one UI and one UI_API fixture against
  a loopback store and generated one Extent artifact for that suite.
- Playwright opened that generated artifact and expanded the UI/API scenario,
  `TEST BODY`, the terminal checkpoint, `API validation`, the one observed
  `GET /api/productsList`, and its `API assertions` group. It verified that the
  comparison begins collapsed, becomes visible when expanded, and stays under
  the selected GET; the separate document GET has no copied comparison, and
  request details expand.
- This controlled suite proof closes the recorded criterion. The canonical
  public-site `AutoExE2eSuite` was not run and is not represented as live-site
  acceptance by this offline test.

<a id="shop-billing-sanity"></a>
## SHOP-BILLING-SANITY — attribute the lookup-race latency

**Status: Open.** The original failure was not reproduced, but race-path latency remains unattributed.

- During Console/Mock adoption, `ShopBillingSynchronizationComponentTest` had two assertion failures and one error among thirteen invocations.
- The original race invocation took 122 seconds. A thread witness showed nested Playwright readiness/`isEnabled` during postal lookup selection; this is a lead, not a confirmed root cause.

**Focused rechecks**

- On 2026-11-19, all 13 invocations passed in 37.045 seconds.
- On 2026-11-20, all 13 passed in 59.19 seconds. The `race,true,true` invocation took 16.986 seconds; the other invocations took 2.075–7.124 seconds. The selected test source hash matched the live checkout.
- The timed JUnit invocation includes browser/session startup and fixture navigation. It does not establish that postal lookup itself consumed 16.986 seconds. The test asserts settled requests, final address values and no retyping, but has no elapsed-time oracle. Its local fixture uses a 300 ms input debounce and injects a 500 ms delayed application for `race`.

**Remaining evidence**

- Measure browser startup/navigation separately from `inputsApplied` through request completion, DOM application and `ShopPostalLookup` settlement. Compare the input-to-settlement interval with the existing observation budget.
- Keep the address, request ownership, completion and no-retyping oracles. Do not increase timeouts or add retries to hide the latency.
- If a source change is warranted, rerun the 13-case matrix and inspect its component Extent evidence.

The earlier broad Shop run stopped after preserving its failures and thread witness; neither focused pass establishes broad-suite acceptance. Close this item only after repeated race runs show bounded lookup settlement and any cold browser cost is reported separately.

<a id="autoex-ui-cart-confirmation-2026-11-19"></a>

## AUTOEX-UI-CART-CONFIRMATION-2026-11-19 — recheck the public add-to-cart confirmation

**Status: observed once; root cause and priority remain unranked pending reproduction.**

- The opt-in `AutoExPurchaseUiE2eTest` reached Automation Exercise, searched for `Blue Top`, and clicked `button.cart`.
- The wait for `#cartModal` timed out after 20 seconds while the modal remained hidden; the report contains the action sequence and diagnostics.
- The test stopped before account registration and checkout. This run does not establish whether the site changed, the click failed to mutate the cart, or the confirmation locator is stale.
- [`AutoExPurchaseBusinessComponentTest`](../../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/journey/AutoExPurchaseBusinessComponentTest.java) checks immediate and delayed modal evidence on local fixtures; it does not reproduce this public-site incident.

- [ ] Reproduce with a fresh browser and capture the cart state and relevant network exchange immediately after the click.
- [ ] Distinguish a public-site behavior change from a locator or readiness defect before changing the journey.
- [ ] Keep the failure report inspectable and verify cleanup if a later reproduction reaches account creation.
