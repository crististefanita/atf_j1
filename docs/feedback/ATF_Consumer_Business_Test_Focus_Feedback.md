# Consumer business verification — current status

## AutoEx search — delivered contract

The [AutoEx consumer README](../../atf-test-auto-ex/README.md) owns the
GET/fresh-DOM contract and its permanent regression coverage.

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

**Status: Verified and closed on 2026-11-21.** The lookup settlement interval is now measured separately from browser startup and fixture navigation; repeated local runs bound the race path inside its existing observation deadline.

- During Console/Mock adoption, `ShopBillingSynchronizationComponentTest` had two assertion failures and one error among thirteen invocations.
- The original race invocation took 122 seconds. A thread witness showed nested Playwright readiness/`isEnabled` during postal lookup selection; this is a lead, not a confirmed root cause.

**Focused rechecks**

- Three focused `ShopBillingSynchronizationComponentTest` executions passed 13/13 each (39 invocations), with no failures or errors.
- The 2026-11-21 recheck also passed 13/13 in both fresh EN and RO worktrees, with no failures or errors.
- A new test oracle measures the `Await visible` action after `inputsApplied`; successful settlement must remain within the configured 1,100 ms budget. Expected timeout refusals allow at most 25 ms for Playwright/recording completion, without changing the configured timeout.
- Two measured `race,true,true` runs settled in 524 ms and 494 ms. Their browser startup and fixture navigation measured 11,904 ms and 9,233 ms separately. **Inference:** setup dominates the recent long race invocation; these measurements do not establish the cause of the historical 122-second event.
- The local fixture retains its 300 ms input debounce and 500 ms delayed application for `race`. Request ownership, completion, final address values, and no-retyping assertions remain in place.

**Retained regression guard**

- Keep the timing assertion with the existing request-ownership, completion, address, and no-retyping oracles. Do not increase timeouts or add retries to hide latency.

The earlier broad Shop run and historical 122-second race remain unreproduced and are not claimed as explained. Current evidence closes the measured settlement criterion only; it does not establish broad-suite acceptance.

<a id="autoex-ui-cart-confirmation-2026-11-19"></a>

## AUTOEX-UI-CART-CONFIRMATION-2026-11-19 — recheck the public add-to-cart confirmation

**Status: observed once; root cause and priority remain unranked pending reproduction.**

- The opt-in `AutoExPurchaseUiE2eTest` reached Automation Exercise, searched for `Blue Top`, and clicked `button.cart`.
- The wait for `#cartModal` timed out after 20 seconds while the modal remained hidden; the report contains the action sequence and diagnostics.
- The test stopped before account registration and checkout. This run does not establish whether the site changed, the click failed to mutate the cart, or the confirmation locator is stale.
- [`AutoExPurchaseBusinessComponentTest`](../../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/journey/AutoExPurchaseBusinessComponentTest.java) checks immediate and delayed modal evidence on local fixtures; it does not reproduce this public-site incident.
- The 2026-11-21 review did not rerun the public journey; this finding remains active.

- [ ] Reproduce with a fresh browser and capture the cart state and relevant network exchange immediately after the click.
- [ ] Distinguish a public-site behavior change from a locator or readiness defect before changing the journey.
- [ ] Keep the failure report inspectable and verify cleanup if a later reproduction reaches account creation.
