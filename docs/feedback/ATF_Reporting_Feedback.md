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
| UiBackendRecovery.recording | Emits one INFO action per trace: ACTION is `BE observation #n`, TARGET is semantic `catalog-readiness`; the component audit currently projects ACTION as its `Operation` field and omits TARGET |
| UiRecoveryTrace.description | Decision, reason, captured signal summary when available, probe/backoff/total durations, and policy source |
| Current consumer README | Describes local component report probes and distinguishes their synthetic outcome-bearing step from suite-generated report output |
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
- This verifies local consumer projection and disclosure rendering. It does not
  verify a suite-generated parent step or public-site E2E report.
- Generated HTML excludes the fixture URL and private response body.

The verified test-change control is retained in the
[baseline](../ATF_Test_Changes_Baseline.md#recovery-extent-report-rendering).

<a id="j1-recovery-runner-trace-2026-11-21"></a>

## J1-RECOVERY-RUNNER-TRACE-2026-11-21 — include recovery traces in actual component reports

**Status: Active — Maven tests pass; actual-report browser acceptance is partial; IntelliJ acceptance remains open.**
J1 now consumes Reporting J26 and Test Kit K3. Both component adapters carry
the immutable recovery observations into the real JUnit `TEST BODY` phase,
beside the ordinary `Component assertions` step.

| Artifact evidence | Observed behavior |
|---|---|
| Actual Shop component report | `ShopExtentTestExtension` publishes a separate `Backend recovery trace · <outcome>` INFO node beside `Component assertions`; its `Attempt N · Operation` value is `Backend observation #N`, not the semantic recovery operation. The test verdict and count are unchanged. |
| Actual AutoEx component report | `AutoExExtentTestExtension` publishes the same trace boundary and currently uses the same event label in the `Operation` field. |
| Maven consumer runs | Shop passed 6/6 and AutoEx 5/5 in EN and RO against the physical J26/K3 JARs. |
| Playwright rendering | Consumer-owned Playwright checks open only their synthetic local HTML. One separate browser check opened the actual Shop JUnit report and verified navigation, collapsed nested disclosures after `Expand all nodes`, visible `TEST BODY` and assertion table, and rendered observation rows; it then stopped at a fixture-specific `not captured` expectation. No actual AutoEx JUnit report was browser-checked. The semantic operation is not asserted in the component-audit projection; the synthetic action-table probe shows it only in TARGET. |
| Safety and outcome | A source search of 17 generated Shop/AutoEx JUnit reports found neither the private response-body canary nor `127.0.0.1`; `EXHAUSTED` remains an expected business outcome in a passing test. Browser-based redaction acceptance of the actual AutoEx report remains open. |

- [x] Publish the ordered recovery trace from each consumer's actual JUnit
  component run in a separate disclosure, initially collapsed, with the event
  label, policy, observation, measured timing, INFO result, and redacted values. Keep
  the standard assertion step, test verdict, and test count intact.
- [x] Run each consumer-owned Playwright check against its synthetic local
  recovery HTML. Verify `Expand all nodes`, independently collapsed evidence,
  complete ordered rows, and redaction there.
- [ ] Complete browser acceptance against the actual Shop and AutoEx JUnit
  reports with a fixture-neutral owner/scenario oracle. The current Shop probe
  reaches the assertion table and observation rows but stops at its hardcoded
  `not captured` fixture marker; the actual AutoEx report has not been opened.
- [ ] Open the actual generated artifact after an IntelliJ run. Verify the same
  report directory, assertion hierarchy, independently collapsed trace, ordered
  attempt fields, and redaction in the IDE-launched report.

**Remaining closure evidence:** repeat the actual report inspection after
IntelliJ launches the component tests. The repository workspace has no IntelliJ
runner available in this verification environment, so Maven success does not
close IDE output-root parity.

The semantic-operation label gap and typed trace projection remain separately
tracked in [J1-RECOVERY-TRACE-TYPING-2026-11-21](ATF_Code_Maintenance_Feedback.md#j1-recovery-trace-typing-2026-11-21).

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
