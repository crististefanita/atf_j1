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
| UiBackendRecovery.recording | One information record per observation, titled by attempt and labeled with the operation |
| UiRecoveryTrace.description | Decision, reason, captured signal summary when available, probe/backoff/total durations, and policy source |
| Current consumer README | Describes local component report probes and distinguishes their synthetic outcome-bearing step from suite-generated report output |
| 2026-11-21 report checks | AutoExBackendRecoveryComponentTest passed 5/5 and ShopBackendRecoveryComponentTest 6/6 in both editions, generating local Extent reports and opening them in Playwright |

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

**Status: Active — Maven acceptance passed; IntelliJ acceptance remains open.**
J1 now consumes Reporting J26 and Test Kit K3. Both component adapters carry
the immutable recovery observations into the real JUnit `TEST BODY` phase,
beside the ordinary `Component assertions` step.

| Artifact evidence | Observed behavior |
|---|---|
| Actual Shop component report | `ShopExtentTestExtension` publishes a separate `Backend recovery trace · <outcome>` INFO node beside `Component assertions`; the test verdict and count are unchanged. |
| Actual AutoEx component report | `AutoExExtentTestExtension` publishes the same trace boundary for the recovery component suite. |
| Maven consumer runs | Shop passed 6/6 and AutoEx 5/5 in EN and RO against the physical J26/K3 JARs. |
| Playwright rendering | All four actual suite reports were opened. `Expand all nodes` opens the normal hierarchy while each trace disclosure stays closed; opening it shows ordered attempts with operation, policy, observation, measured probe/backoff/total timing, and INFO result exactly once. |
| Safety and outcome | The response-body canary and fixture URL are absent; `EXHAUSTED` remains an expected business outcome in a passing test. |

- [x] Publish the ordered recovery trace from each consumer's actual JUnit
  component run in a separate disclosure, initially collapsed, with operation,
  policy, observation, measured timing, INFO result, and redacted values. Keep
  the standard assertion step, test verdict, and test count intact.
- [x] Open the artifacts from Maven runs in Playwright. Verify `Expand all nodes`
  opens the regular class/body/assertion hierarchy while the trace disclosure
  remains closed, and verify every attempt exactly once after opening it.
- [ ] Open the actual generated artifact after an IntelliJ run. Verify the same
  report directory, assertion hierarchy, independently collapsed trace, ordered
  attempt fields, and redaction in the IDE-launched report.

**Remaining closure evidence:** repeat the actual report inspection after
IntelliJ launches the component tests. The repository workspace has no IntelliJ
runner available in this verification environment, so Maven success does not
close IDE output-root parity.

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
