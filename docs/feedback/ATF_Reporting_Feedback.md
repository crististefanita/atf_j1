# Reporting — consumer feedback

## At a glance

IntelliJ report parity remains open and is deferred for the current review.
The closed findings retain their consumer contracts and executable oracles.

<a id="ui-recovery-audit-01--inspectable-attempts-in-extent"></a>
## UI-RECOVERY-AUDIT-01 — recovery disclosure

**Status: Verified and closed.** Both local consumer probes verify ordered
INFO observations, semantic action targets, redaction and a collapsed disclosure
that stays closed after Expand all and exposes every row when opened.

<a id="j1-recovery-runner-trace-2026-11-21"></a>
## J1-RECOVERY-RUNNER-TRACE-2026-11-21 — actual Maven recovery reports

**Status: Verified and closed for Maven.** Shop and AutoEx publish typed
operation/event/decision/signal/timing fields in actual component reports.
The browser oracles expand the result, TEST BODY and assertion step, then
independently inspect the recovered and exhausted traces, redaction and verdict.

The [Shop](../../atf-test-practice-shop/README.md) and
[AutoEx](../../atf-test-auto-ex/README.md) READMEs document the opt-in actual-report
acceptance. Historical artifact paths and package identity remain in
[provenance](../../library-provenance.json).

<a id="j1-recovery-ide-report-parity-2026-11-22"></a>
## J1-RECOVERY-IDE-REPORT-PARITY-2026-11-22 — IntelliJ report parity

**Status: Open; deferred at the user's request. Owner:** consumer launch configuration.
Maven reports passed the browser oracles; an actual IntelliJ-generated report
has not passed those same checks for both consumers.

- Run each recovery component from IntelliJ and preserve the emitted report path.
- Supply that path to ShopRecoveryActualReportAcceptanceTest or AutoExRecoveryActualReportAcceptanceTest.
- Verify assertion/trace separation, expansion, ordered typed fields, redaction, verdict and output-root handling.

Keep this finding open until both IDE-generated artifacts pass.

<a id="j1-launch-context-146"></a>
## J1-LAUNCH-CONTEXT-146 — actual selection and discovery plan

**Status: Verified and closed.** Test Kit supplies request selectors, filtered
inventory and discovery count; consumers publish them in Launch context.
Disabled tests remain in the initial count and dynamic registrations stay separate.

Class, method and filtered/canonical-suite probes verify exact fields and full
browser expansion. Native source and physical-JAR contracts cover isolated
nested/parallel launches, subsequent plans and absent observer/request provenance.
Verified controls remain in the [baseline](../ATF_Test_Changes_Baseline.md).
