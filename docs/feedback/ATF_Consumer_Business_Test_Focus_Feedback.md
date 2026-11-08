# Consumer business verification — current status

## AutoEx search — delivered contract

The [AutoEx search record](../ATF_AutoEx_Search_Verification.md) owns the
GET/fresh-DOM contract, the original incident and the regression evidence.
The [live purchase receipt](../ATF_J1_Live_Screenshot_Verification.md) records
the separate AutoEx and Shop purchase and rendering checks.

## AUTOEX-CATALOG-EVIDENCE-146 — bind comparison evidence to its observed exchange

**Priority: 82/100.** Source-confirmed association risk in [AutoExE2eRun.apiCheckpoint](../../atf-test-auto-ex/src/main/java/atf/autoex/runtime/AutoExE2eRun.java); this review does not claim a new live application failure. Awaiting a GET uses method/path, while the subsequent assertion attachment selects exchanges by path alone.

- [ ] Characterize a controlled capture containing GET and POST with the same catalog path, repeated GETs and unrelated neighbors; prove the business comparison is attached only to its selected observed exchange.
- [ ] Select through method and captured request identity/sequence, with explicit ambiguity handling; preserve independently evaluated expectations rather than copying them to every path match.
- [ ] Keep this application correlation in AutoEx main support and consumer tests; neutral rendering/ordinal regressions remain at J0 Reporting Tests.
- [ ] Verify individual/suite reports with one auditable comparison owner, unchanged preview 240 and no synthetic HTTP exchange.


## SHOP-BILLING-SANITY — isolate the existing UI/lookup failures

Priority: 88/100. During Console/Mock binary adoption, the unchanged ShopBillingSynchronizationComponentTest had two assertion failures and one error among thirteen invocations. The race invocation took 122 seconds despite the configured 1.1-second action and 3-second setup budgets; a thread witness shows nested Playwright readiness/isEnabled inside postal lookup selection.

- Reproduce the race, pending and duplicate cases individually on controlled DOM/API fixtures; compare source and binary consumption before attributing the cause.
- Keep the original independent address, dispatch/completion and no-retyping oracles. Trace the remaining budget through nested native readiness instead of adding retries or larger timeouts.
- Verify the postal-lookup adjacent negatives and individual/suite Extent rendering after fixing the actual owner.

The broad Shop suite was deliberately stopped after preserving its failures and thread witness; it is not a passing acceptance. Focused library-adoption sanity remains separate from this unresolved UI boundary. Evidence stays beside the package verification in owned TMP.
