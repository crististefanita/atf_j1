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
real JUnit component result. The rendered `TEST BODY` phase contains separate
expandable `Component assertions` and INFO recovery-trace steps.

| Artifact evidence | Observed behavior |
|---|---|
| Actual Shop component report | `Operation` displays `catalog-readiness`; `Event` displays `Backend observation #N`. Attempts remain ordered and INFO, with policy, decision, reason, signal, and typed probe/backoff/total durations. |
| Actual AutoEx component report | The same consumer-owned fields and terminal outcomes appear through `AutoExExtentTestExtension`; assertions, verdict, and count remain intact. |
| Maven consumer runs | Shop passed 6/6 and AutoEx 5/5 against the pinned Reporting J26/Test Kit K3 artifacts. |
| Playwright rendering | Opt-in consumer checks open the actual suite-generated HTML for both `RECOVERED` and `EXHAUSTED`. They expand the component result, `TEST BODY`, and standard assertion step, then independently verify the recovery disclosure remains collapsed after `Expand all nodes`; all attempt fields, ISO-8601 timings, INFO result, body/URL redaction, and a viewport capture are checked. Synthetic renderer checks separately verify action-table targets. |
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
- Confirm the same owner and expanded `TEST BODY` phase, with separate assertion
  and trace steps, a collapsed trace, ordered semantic operation/event/timing
  fields, redaction, and verdict.

Keep this finding open until both IDE-generated artifacts pass the same browser
acceptance as the Maven reports.

<a id="j1-launch-context-146"></a>

## J1-LAUNCH-CONTEXT-146 — complete discovery-plan metadata

**Status: Partially delivered on 2026-11-22; remaining owner:** launcher-to-consumer integration.

- Delivered: immutable source class/method and outermost suite from JUnit unique-ID ancestry, published in the collapsed INFO `Launch context` phase in Tests. Operational System and business steps retain their contracts.
- Verified: direct class/method, canonical suite, tag-filtered suite, duplicate display titles, misleading filenames, repeated launches, and expansion of all six metadata fields in both consumers. Existing real E2E fixture checks retain shared-report ownership, original failures, and scenario isolation.
- Remaining: expose the actual launcher selectors and discovered selection count through a neutral per-launch contract. `ExtensionContext` supplies neither; both fields explicitly say `unknown`. A report filename, root-store lifetime, or completed-test count is not discovery evidence.
- Reproducer: run `ShopLaunchMetadataComponentTest` or `AutoExLaunchMetadataComponentTest`; the method-only case discovers one invocation but correctly keeps selector/count unknown in the consumer observation. Pass a captured immutable plan snapshot at the JUnit boundary before replacing those markers; cover concurrent and nested launches, filters, and dynamic tests.
- Selected Test Kit: `atf.api:atf-test-kit:1.0.0-k3`; JAR SHA-256 `7304152b14aef03f4e81dca9bf9f6421c8f17e554bdfc92dbca38b3d32bcf7bc`, POM SHA-256 `0533a3f67c59c491a22b48df61f71dbe74009a5f839900a085e07eaad0a2a766`. Its scope API has no discovery-plan bridge; this is a missing integration capability, not a renderer failure.

**Closure still required:** actual launcher selection and discovered count become inspectable in direct and filtered suite reports. Keep this item open until that source exists; do not reconstruct selection from execution results.

<details>
<summary><strong>Change control — consumer launch identity</strong></summary>

```yaml
testChanges:
  - id: J1-LAUNCH-IDENTITY-20261122
    status: ACTIVE
    base: e0647199fec5bf64120ceedb9700f5302f212ab3
    expires: 2026-12-22
    reason: Publish immutable JUnit source identity locally, verify unavailable discovery facts explicitly, and govern consumer ownership.
    proof: mvn -B -ntp -pl atf-test-practice-shop,atf-test-auto-ex,atf-tests -am "-Dtest=ShopLaunchMetadataComponentTest,AutoExLaunchMetadataComponentTest,ShopEvidenceProfileIsolationComponentTest,AutoExE2eReportPathComponentTest,WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=e0647199fec5bf64120ceedb9700f5302f212ab3" "-Dsurefire.failIfNoSpecifiedTests=false" test
    paths:
      - atf-test-auto-ex/src/test/java/atf/autoex/tests/component/reporting/AutoExE2eReportPathComponentTest.java
      - atf-test-auto-ex/src/test/java/atf/autoex/tests/component/reporting/AutoExLaunchMetadataComponentTest.java
      - atf-test-auto-ex/src/test/java/atf/autoex/tests/extension/AutoExRunFactory.java
      - atf-test-auto-ex/src/test/java/atf/autoex/tests/support/reporting/AutoExLaunchMetadata.java
      - atf-test-auto-ex/src/test/java/atf/autoex/tests/support/reporting/AutoExLaunchReportProbe.java
      - atf-tests/src/test/java/atf/api/tests/governance/consumers/ConsumerModuleArchitectureGovernanceTest.java
```

</details>
