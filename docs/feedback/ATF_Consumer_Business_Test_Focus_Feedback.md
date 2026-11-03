# Consumer business verification — current findings

## AUTOEX-SEARCH-READINESS-143 — capture the failed search transition

**Priority: P2. Owner:** AutoEx interaction/consumer in J1; generic Reporting
is owned by J0. The final J18 replay retained an actual search failure before
the cart assertions; the independently rerun case passed with unchanged code.

| Witness | Observed fact |
|---|---|
| first E2E | pointer click completed; the first product Blue Top was not confirmed within the existing action budget |
| captured URL | https://automationexercise.com/products?search=Blue%20Top |
| later HTTP probe | 200, 12,976 characters and Blue Top present; a separate observation, not proof of the earlier response |
| independent UI recheck | same J18, selectors, configuration and timeouts; one case passes |
| Reporting | the original FAIL report and its captured evidence remain; no verdict is rewritten |

- [ ] Capture the actual search response/status, redirect chain, first product text and local DOM around the failed click with the existing interaction receipts.
- [ ] Reproduce the exact transition with an independent consumer component oracle; distinguish empty/reordered response, transport failure and unconfirmed UI state from evidence rather than the final URL alone.
- [ ] Retain screenshot, cause and timing inline in Extent; do not increase timeouts, accept URL-only success or add an automatic retry to hide the witness.

The root cause remains unestablished. [The retained failure](http://127.0.0.1:59987/j1-autoex-search-failure-j18.html#test-view)
and [library acceptance](../ATF_Reporting_J18_Verification.md) have distinct scopes.
