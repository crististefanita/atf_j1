# Reporting — J1 consumer feedback

## At a glance

This register keeps J1-specific Reporting integration gaps. Reproduce each
finding against the selected Reporting coordinate and preserve the generated
report as evidence.


<a id="ui-recovery-audit-01--inspectable-attempts-in-extent"></a>

## UI-RECOVERY-AUDIT-01 — make every recovery observation inspectable in Extent

**Status: Verified and closed on 2026-11-21.** The component checks create local
Extent probes from each consumer's captured recovery records. Each probe builds
a synthetic scenario and outcome-bearing step; the ordered J1 records render
in one initially collapsed `Audit observations` disclosure, exposed as
`details.atf-ui-observations`. This proves local consumer projection and
rendering, not a suite-generated aggregate parent step or a public-site E2E
report.

| Code evidence | What is recorded |
|---|---|
| `UiBackendRecovery.recording` | Emits one INFO action per trace: ACTION is `BE observation #n`, TARGET is semantic `catalog-readiness` |
| Synthetic consumer probe | Shows the event and target in separate action-table columns and asserts the semantic target |
| Actual JUnit report projection | The typed consumer observation displays `Operation = catalog-readiness` and keeps `Event = Backend observation #n` separate; see [actual recovery reports](#j1-recovery-runner-trace-2026-11-21) |
| UiRecoveryTrace.description | Decision, reason, captured signal summary when available, probe/backoff/total durations, and policy source |
| Current consumer README | Describes the synthetic renderer probe and opt-in actual Maven report acceptance; IntelliJ output parity remains separate |
| 2026-11-21 report checks | AutoExBackendRecoveryComponentTest passed 5/5 and ShopBackendRecoveryComponentTest 6/6 in both editions; each component test opens its own synthetic local Extent probe in Playwright, separately from the JUnit suite report |

- [x] Run deterministic recovered and terminal exhausted scenarios in both consumers.
- [x] Verify the outcome-bearing step title and ordered attempts in one initially collapsed observations disclosure.
- [x] Expand the disclosure and verify all rows, decisions, safe signal metadata, durations, policy source, and redaction of request bodies and local URLs.
- [x] Verify the initial collapsed state, expand-all behavior, full row visibility, and redaction in the local component/browser tests.

**Closure evidence:** both consumer tests pass in EN and RO.

- Playwright confirms the disclosure starts closed and stays closed after
  `Expand all nodes`; opening it reveals every attempt in order.
- The component check builds a synthetic scenario and outcome-bearing step;
  J1's captured rows pass through the selected Reporting renderer.
- This finding verifies the synthetic action-table projection and disclosure.
  Suite-generated report acceptance is tracked separately below; neither check
  claims public-site E2E acceptance.
- Generated HTML excludes the fixture URL and private response body.

The verified test-change control is retained in the
[baseline](../ATF_Test_Changes_Baseline.md#recovery-extent-report-rendering).

<a id="j1-recovery-runner-trace-2026-11-21"></a>

## J1-RECOVERY-RUNNER-TRACE-2026-11-21 — include recovery traces in actual component reports

**Status: Verified and closed for Maven component reports on 2026-11-22.**
Both consumer projections now publish semantic and typed recovery data in the
real JUnit `TEST BODY` phase, beside the ordinary `Component assertions` step.

| Artifact evidence | Observed behavior |
|---|---|
| Actual Shop component report | `Operation` displays `catalog-readiness`; `Event` displays `Backend observation #N`. Attempts remain ordered and INFO, with policy, decision, reason, signal, and typed probe/backoff/total durations. |
| Actual AutoEx component report | The same consumer-owned fields and terminal outcomes appear through `AutoExExtentTestExtension`; assertions, verdict, and count remain intact. |
| Maven consumer runs | Shop passed 6/6 and AutoEx 5/5 against the pinned Reporting J26/Test Kit K3 artifacts. |
| Playwright rendering | Opt-in consumer checks open the actual suite-generated HTML for both `RECOVERED` and `EXHAUSTED`. They verify owner, `TEST BODY`, standard assertions, collapsed disclosure after `Expand all nodes`, complete attempt fields and ISO-8601 timings, INFO result, body/URL redaction, and a viewport capture. Synthetic renderer checks separately verify action-table targets. |
| Safety and outcome | Report acceptance reads an explicitly supplied artifact path and does not overwrite that input. Screenshots stay under staging `target`; the response body and local fixture address remain absent. |

- [x] Keep semantic operation, event label, and typed durations as separate
  consumer-owned fields.
- [x] Verify recovered and exhausted traces in both actual Maven-generated
  reports, including disclosure state, row completeness, redaction, and verdict.
- [x] Keep selector and projection ownership local to each consumer under
  architecture governance.

IntelliJ output-root parity is a separate open item in
[J1 recovery IDE report parity](#j1-recovery-ide-report-parity-2026-11-22).

<a id="j1-recovery-ide-report-parity-2026-11-22"></a>

## J1-RECOVERY-IDE-REPORT-PARITY-2026-11-22 — verify IntelliJ's report path

**Status: Open; owner:** J1 consumer test-launch configuration.

The new Playwright acceptance opens an actual report by explicit path and
verifies the Maven-generated Shop and AutoEx artifacts. No IntelliJ-launched
report has yet been checked with that same oracle.

- Run each recovery component test from IntelliJ with its normal run
  configuration and preserve the emitted report path.
- Open each IDE-generated artifact with the matching
  `ShopRecoveryActualReportAcceptanceTest` or
  `AutoExRecoveryActualReportAcceptanceTest` property.
- Confirm the same owner, `TEST BODY`, standard assertions, collapsed trace,
  ordered semantic operation/event/timing fields, redaction, and verdict.

Keep this finding open until both IDE-generated artifacts pass the same browser
acceptance as the Maven reports.

<a id="j1-launch-context-146"></a>

## J1-LAUNCH-CONTEXT-146 — record the actual launch scope

**Status: Still open after the 2026-11-21 recheck. Owner:** J1 launch metadata
and consumer composition. Business and rendering checks pass; audit metadata
still omits the JUnit root and the effective test selection.

| Witness | Observed fact |
|---|---|
| AutoEx composition | `AutoExRunFactory` shares a report resource from the JUnit root store, but names the report from the test method and each scenario from the child test class/display name; it does not publish the root suite or effective selection |
| Shop composition | `ShopE2eTestSupport` shares a root-store report; `ShopRunReportFactory` receives configuration, evidence profile, and environment, not JUnit launch identity |
| Ambiguous filename | `AutoExReportSession.fileName` derives the report name from the method, so a direct method run and a suite run can share the same name prefix |
| Existing boundary | `ReportEnvironmentField.SUITE` is intentionally hidden from operational System; launch facts belong in secondary Tests/trace metadata |
| 2026-11-20 controlled check | `AutoExE2eOverviewNarrativeComponentTest` passed two UI/UI_API fixture scenarios in suite scope and verified report expansion; it does not assert the canonical root suite or effective selection |

The 2026-11-21 recovery report checks and whole-reactor governance pass do not
assert launch identity. They leave this metadata finding open.

- [ ] Capture immutable launch scope, canonical suite root when known, selected
  class/method, and effective selection count at the JUnit boundary; retain an
  explicit unknown value when unavailable.
- [ ] Publish those safe facts through the existing neutral metadata boundary,
  outside operational System; preserve business step titles, one report per
  suite, and the 240-character preview.
- [ ] Add J1 contracts for direct and filtered canonical-suite launches,
  including same-title classes and misleading file names. Do not infer scope
  from artifact names or displayed step text.
- [ ] If the selected Test Kit contract cannot expose the required context,
  record the exact library coordinate and a minimal J1 reproducer; keep browser
  acquisition and application behavior outside this metadata item.

**Closure:** a reader identifies the JUnit root suite, effective selection, and
selected tests from each HTML report without source checkout or launcher logs.
Direct and filtered launches pass positive and negative contracts; passing
business or rendering checks alone does not close this metadata gap.
