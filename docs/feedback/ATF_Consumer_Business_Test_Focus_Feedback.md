# Consumer business verification — current status

## AutoEx search — delivered contract

The [AutoEx search record](../ATF_AutoEx_Search_Verification.md) owns the
GET/fresh-DOM contract, the original incident and the regression evidence.
The [live purchase receipt](../ATF_J1_Live_Screenshot_Verification.md) records
the separate AutoEx and Shop purchase and rendering checks.

<a id="autoex-catalog-evidence-146"></a>
## AUTOEX-CATALOG-EVIDENCE-146 — bind comparison evidence to its observed exchange

**Priority: 48/100.**

The source fix and focused component and local-browser report checks pass.

- The valid report assigns the comparison once to the selected GET despite a
  same-path POST and repeated GET.
- Missing or duplicate-sequence evidence produces a failed API-evidence assertion
  without attaching the business comparison.
- The browser check confirms that the assertion group and request details expand
  in the rendered Extent report.
- Component and local-browser evidence is recorded below; the suite-scoped
  artifact remains outstanding.

- [x] Run the controlled capture and inspect its generated report to prove the
  comparison appears only under the selected observed exchange.
- [x] Run the correlation regression for method, path and captured sequence;
      confirm missing or ambiguous capture fails and checks are not duplicated
      across path matches.
- [x] Keep the behavior in AutoEx main support and consumer tests; generic
  renderer and ordinal regressions remain owned by J0 Reporting Tests.
- [x] Run the component and local-browser presentation checks; inspect the
  generated report for one comparison owner, preview limit 240, no synthetic
  exchange, and expandable API/request evidence.
- [ ] Inspect the suite-scoped Extent artifact from a controlled suite run and
  confirm it retains the same comparison ownership and expandable hierarchy.

**Current evidence**

- On 2026-11-20, the two selected correlation cases in `AutoExRuntimeBoundaryComponentTest` passed 2/2 with no failures or errors in both canonical TMP editions.
- The run generated valid, missing-capture, and ambiguous-capture HTML reports under `atf-test-auto-ex\artifacts\reports\j1-extent-api-20261120` in each staged checkout.
- `AutoExCatalogReportPresentationE2eTest` passed 1/1 in each edition; `PlaywrightPagePreparationTest` passed 7/7.
- The browser test expanded the scenario, test phase, catalog API step, API validation, selected GET, and API assertion group; it confirmed the assertion starts collapsed, becomes visible on expansion, and request details open.
- Component assertions confirmed one comparison owner on the selected GET, four captured exchanges, no synthetic fifth exchange, and the 240-character preview limit.
- The full `AutoExE2eSuite` was not run, so its suite-scoped artifact remains outstanding.
<a id="shop-billing-sanity"></a>
## SHOP-BILLING-SANITY — isolate the existing UI/lookup failures

Priority: 88/100. During Console/Mock binary adoption, the unchanged ShopBillingSynchronizationComponentTest had two assertion failures and one error among thirteen invocations. The race invocation took 122 seconds despite the configured 1.1-second action and 3-second setup budgets; a thread witness shows nested Playwright readiness/isEnabled inside postal lookup selection.

- Reproduce the race, pending and duplicate cases individually on controlled DOM/API fixtures; compare source and binary consumption before attributing the cause.
- Keep the original independent address, dispatch/completion and no-retyping oracles. Trace the remaining budget through nested native readiness instead of adding retries or larger timeouts.
- Verify the postal-lookup adjacent negatives and individual/suite Extent rendering after fixing the actual owner.

The broad Shop suite was deliberately stopped after preserving its failures and thread witness; it is not a passing acceptance. Focused library-adoption sanity remains separate from this unresolved UI boundary. Evidence stays beside the package verification in owned TMP.

**Focused rerun, 2026-11-19.** The same component class ran in an owned TMP stage: 13 tests, 0 failures, 0 errors, 0 skips, 37.045 seconds. The earlier failure was not reproduced. This does not close the item: the broad suite and source-versus-JAR comparison remain outstanding.

<a id="autoex-ui-cart-confirmation-2026-11-19"></a>

## AUTOEX-UI-CART-CONFIRMATION-2026-11-19 — recheck the public add-to-cart confirmation

**Status: observed once; root cause and priority remain unranked pending reproduction.**

- The opt-in `AutoExPurchaseUiE2eTest` reached Automation Exercise, searched for `Blue Top`, and clicked `button.cart`.
- The wait for `#cartModal` timed out after 20 seconds while the modal remained hidden; the report contains the action sequence and diagnostics.
- The test stopped before account registration and checkout. This run does not establish whether the site changed, the click failed to mutate the cart, or the confirmation locator is stale.

- [ ] Reproduce with a fresh browser and capture the cart state and relevant network exchange immediately after the click.
- [ ] Distinguish a public-site behavior change from a locator or readiness defect before changing the journey.
- [ ] Keep the failure report inspectable and verify cleanup if a later reproduction reaches account creation.
