# Reporting — J1 consumer feedback

## At a glance

This register keeps J1-specific Reporting integration gaps. Reproduce each
finding against the selected Reporting coordinate and preserve the generated
report as evidence.


<a id="ui-recovery-audit-01--inspectable-attempts-in-extent"></a>

## UI-RECOVERY-AUDIT-01 — make every recovery observation inspectable in Extent

**Status:** Open · **Owner:** J1 recovery evidence and report acceptance. The
local recording adapter emits one information record per UiRecoveryTrace, while
consumer documentation describes only an aggregate indicator. A rendered
report has not yet confirmed that the full trace appears in one expandable group.

| Code evidence | What is recorded |
|---|---|
| UiBackendRecovery.recording | One information record per observation, titled by attempt and labeled with the operation |
| UiRecoveryTrace.description | Decision, reason, captured signal summary when available, probe/backoff/total durations, and policy source |
| Current consumer README | Describes an aggregate recovery indicator; it does not specify the visible hierarchy for the full attempt sequence |
| 2026-11-20 component checks | AutoExBackendRecoveryComponentTest passed 5/5 and ShopBackendRecoveryComponentTest passed 6/6; both assert trace safety and dispatch limits, but neither produces an Extent report |

- [ ] Run a deterministic recovered scenario and a terminal refused/exhausted scenario in each consumer that enables recovery.
- [ ] Verify the generated Extent report groups all observations under one collapsed recovery step, in attempt order, with an aggregate outcome visible at the parent.
- [ ] Expand the group and verify each attempt's decision, reason, safe signal metadata, duration, and policy source appears once. Confirm that request bodies and private URLs are absent.
- [ ] Add or retain a local component/browser assertion for initial collapsed state, expand-all behavior, full child visibility, and redaction. Keep the recovery assertions separate from business verdicts.

**Closure:** a generated J1 report and a local rendered-report check prove that all
attempts are discoverable from one collapsed recovery group and remain complete
after expansion, including expand-all. Both recovered and terminal outcomes
are covered; no sensitive request content is published.

<a id="j1-launch-context-146"></a>

## J1-LAUNCH-CONTEXT-146 — record the actual launch scope

**Status: Open. Owner:** J1 launch metadata and consumer composition. Business
and rendering checks pass; audit metadata still omits the JUnit root and the
effective test selection.

| Witness | Observed fact |
|---|---|
| AutoEx composition | `AutoExRunFactory` shares a report resource from the JUnit root store, but names the report from the test method and each scenario from the child test class/display name; it does not publish the root suite or effective selection |
| Shop composition | `ShopE2eTestSupport` shares a root-store report; `ShopRunReportFactory` receives configuration, evidence profile, and environment, not JUnit launch identity |
| Ambiguous filename | `AutoExReportSession.fileName` derives the report name from the method, so a direct method run and a suite run can share the same name prefix |
| Existing boundary | `ReportEnvironmentField.SUITE` is intentionally hidden from operational System; launch facts belong in secondary Tests/trace metadata |
| 2026-11-20 controlled check | `AutoExE2eOverviewNarrativeComponentTest` passed two UI/UI_API fixture scenarios in suite scope and verified report expansion; it does not assert the canonical root suite or effective selection |

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
