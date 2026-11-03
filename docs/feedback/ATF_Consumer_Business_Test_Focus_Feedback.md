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
