# Consumer business verification — current status

## AutoEx search — delivered contract

AUTOEX-SEARCH-READINESS-143 is resolved by the owned GET/fresh-DOM contract and
permanent consumer regressions. The [verification record](../ATF_AutoEx_Search_Verification.md)
retains the original queue-full PNG, unknown original HTTP status, separate API
probes, source behavior and individual/suite/rendering evidence.

| Status | Current owner and evidence |
|---|---|
| Search classification | J1 AutoEx; one pointer/GET, explicit terminal HTTP/DOM failure, no replay or timeout increase |
| Permanent coverage | Existing readiness plus twelve response cases; final suite 15 passed and focused missing-response diagnostics 3 passed |
| Fresh real purchase acceptance | [2026-11-03](../ATF_J1_Live_Screenshot_Verification.md): AutoEx and Shop, individually and through filtered canonical suites, all four pass; full-context 70% screenshots and Extent rendering audited |
| Fixture preparation resolved | [UI-FIRST-PAINT-144 verification](../ATF_UI_First_Frame_Verification.md): SDK-default runtime, original budgets, 39 individual and 39 suite cases pass |
| Test-change authorization | Verified control in the [technical baseline](../ATF_Test_Changes_Baseline.md#autoex-search-owned-response) |

No resolved search ToDo remains here. Public-service unavailability still
produces an honest FAIL; successful later probes do not rewrite earlier evidence.

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
