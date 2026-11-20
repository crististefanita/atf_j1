# Android consumer coordination — J1 to J4

## At a glance

J1 retains coordination of non-library requirements transferred from J0 on
2026-11-02. J4 owns ADB, the model, oracles, and device execution; [J0 Reporting](../../../atf_j0/docs/feedback/ATF_Android_UI_Reporting_Feedback.md)
retains only generic renderer contracts and source/JAR evidence.

The records below are dated handoffs, not claims about the current release.
Details already demonstrated provide context for consumer acceptance; neither
the verdict nor the test count is recalculated from a checkpoint.

<details>
<summary><strong>Consumer handoffs retained in full</strong></summary>

## 2026-11-01 — complete match and state evidence, J11 consumer

- J4 received the physical J11 pair.
- The producer's synthetic-PNG test does not close Android consumer acceptance.
- The live target is 19 hands, 109 tricks, and 436 cards, followed by the same
  deals/bids/moves in Whist Lab with results calculated by the local engine.
- The run is in progress: hand one has 40 cards accounted for and observed
  scores `[10,15,2,3]`, reconciled independently.
- The full scenario is not yet accepted.

**Firm contract and evidence requirements, with no Whist logic in Reporting:**

1. **Per-claim provenance.** OBSERVE can describe product state and independent
   calculation in addition to the UI capture. Old UI XML, a changed pixel, and
   freshly saved state are different evidence. Do not turn OBSERVE into ACTION
   or interpretation into a fact automatically read from a PNG. State parsing
   and the oracle remain in J4; the Reporting model remains neutral.
2. **Reproduction from the report.** Input, rule condition, expected/actual,
   verdict, exact inline capture, and source artifact with SHA-256 remain
   correlated with their step/scenario. Preview remains 240 characters; the
   full comparison must be openable and readable. Lists of legal values must
   not disappear from HTML.
3. **Stable counts.** JUnit tests, live scenarios, checkpoints, assertions,
   moves, and captures have different populations. Hundreds of assertions do
   not become hundreds of tests in Overview. A rule/score FAIL remains FAIL
   even when visual acceptance is WARNING.
4. **Volume test.** The two applications produce hundreds of checkpoints with
   inline Base64 PNGs at 1080×1920. Measure publish/close, HTML size, memory,
   and browser navigation with an equivalent synthetic test. Verify the final
   step's media and assertions. If all hidden images are decoded on open,
   propose on-demand loading while keeping HTML standalone and payloads complete.
5. **Uninstrumented timing.** Distinguish observation/calculation without
   instrumented timing from an action measured at 0 ms. If the current surface
   supports metadata/observations, document its use before extending the API.
6. **Auditable continuation.** A harness failure retains the trace and cause.
   Continuation revalidates retained checkpoints and preserves the original
   log. Do not match captures by filename alone or hide phase provenance when
   publishing the final verdict.

- J4 uses the existing J11 API.
- If the requirements are covered, deliver evidence against the JAR and
  concrete consumption instructions.
- If a gap is found, only J0 changes, tests, and packages the library.
- Raw `.ab` backups can contain application preferences; J4 attaches only
  game state and required evidence to the report, not raw archives.

## 2026-11-02 — J4: Romanian rules, components, and AI before Android

The J4 consumer adds the native `RuleComponentExtentReportTest` report without
a private HTML renderer. The Romanian profile is separate from Oh Hell; JVM
tests produce inputs, state, and expected/actual for every card, without an
emulator. The Reporting contracts required for this consumption are concrete:

1. Overview must distinguish JUnit cases, model matches, AI comparisons,
   moves, and JaCoCo branches. 6,375 configurations from one test are not
   6,375 JUnit cases. No match step automatically becomes a new test.
2. A component flow has SETUP/TEST BODY/TEARDOWN and domain state, without a
   screenshot. The absence of a UI capture here is normal, not a product
   warning. When returning to Android, images remain real inline Base64 PNGs.
3. Expected/Actual must retain the full hand, legal-card list, trump, actor,
   winner, tricks, and totals. Negative scores are valid for the Romanian
   profile; formatting must not hide the sign.
4. The AI verdict has two dimensions: legality and measured performance. The
   paired sample, seed, seat, opponents, and scores must be readable. A better
   score in 32 pairs does not mean globally optimal AI.
5. The offline report and Android report have distinct acceptance. Do not
   automatically fill a missing Android run with PASS based on green components.

J4 consumes only the J11 JAR delivered by J0. Any generic change to layout,
counts, comparison, or navigation remains to be implemented, verified, and
packaged in J0. The current run and HTML verification are retained under
`../../../../whist_back/rules-components/20261102-final/`.

### Concrete reproduction: observation.fields is preview only

- Strict consumer verification failed in
  `../../../../whist_back/rules-components/20261102-acceptance/extent-publisher.log`:
  `Report omitted complete component evidence`, hand 5 deal.
- In HTML, `INPUTS / HAND / LEGAL CHOICES` ends with an ellipsis after 240
  characters; the full value was absent because the consumer used only
  ReportField.
- The J4 correction uses `ReportObservation.Body(..., Format.TEXT)` for full
  input/expected/actual and keeps the field as preview.
- We do not raise the global limit or copy the renderer.
- J0: document this field semantics explicitly and add a binary test for a
  TEXT body over 240 characters, with negative signs, Unicode cards, and the
  final visible element.

- A configuration correction was confirmed by the J11 binary test: TEXT Body
  also has a separate default limit of 100 characters.
- The consumer must request
  `new ReportingSettings(240, ReportingSettings.DISABLE_BODY_DISPLAY_LIMIT)`
  for complete evidence.
- J4 did this only in its offline publisher; preview remains 240.
- Verification of all values passed:
  `../../../../whist_back/rules-components/20261102-complete/component-report-verification.properties`,
  184 cases, 88 model matches, 40,304 moves, HTML 4,604,237 bytes.
- The final benchmark has 144 pairs at 3–6 seats, not only the initial 32-case test.
- J0 should document this configuration clearly for consumers with long
  evidence; a private renderer or JAR change in J4 is not needed.
</details>

<details>
<summary><strong>New J4 handoff — J12 volume and domain test</strong></summary>

## 2026-11-02 — real J4 volume test on J12 JAR: OOM during publication

The full original match and Android Whist Lab replay passed: 19 hands, 109
tricks, 436 entries per application; final totals [89,126,145,119]. The native
J12 publisher receives 321 checkpoints with inline PNGs, 77,114,418 PNG bytes,
6,634 assertions, and 577 ACTION/OBSERVE records.

With a 1 GB heap, Extent close fails with OutOfMemoryError before HTML:
`StringUTF16.replace -> String.replace -> ExtentReportAssets.inlineFontAsset:195
-> inlineSparkAssets:94 -> ExtentReportPublicationPipeline.publish:66`.
The full diagnostic is in
`whist_back/deep-app-test/runs/20261101-231857-full-match/diagnostics/publisher-1g-oom/`.

With a dedicated 3 GB heap, the renderer produces HTML of about 105 MB. This is
consumer-side mitigation, not proof that publication uses an acceptable amount
of memory. J0: add a permanent JAR gate with this volume class and measure
allocations from asset replacement across the full UTF-16 HTML. Reduce full
copies; retain all original Base64 PNGs. Do not solve this by removing images,
truncating assertions, or moving them to external links.

Consumer verification also found JSON normalization: native output shows
`[14,24,23,15]` for input `[14, 24, 23, 15]`. Data is preserved; the J4
comparator accepts only valid normalized JSON with the same values and order.
Checks for a missing final element, changed order, extra element, and truncated
text pass. This does not require a formatter change.

Current game components: 187 PASS, 88 model matches / 40,304 moves, GameRules
100% line/branch coverage, game-core 97.22% lines / 91.07% branches. The AI
benchmark has 144 pairs at 3–6 seats: Expert 14,604 vs Hard 7,689 points. The
component report passed the J12 publisher. Visual audit of local HTML through
MCP remains undone: browser policy rejects file://. We do not present it as a
completed browser audit.

</details>

<details>
<summary><strong>New J4 handoff — complete ACTION/OBSERVE payloads</strong></summary>

### J12: long ACTION/OBSERVE payloads are lost after preview

In `ExtentUiActionRenderer`, expected/observed directly use
`ExtentReportNodeSupport.display(..., settings)`. At 240 characters, the rest
of the state is absent from HTML even with bodyMaxDisplayCharacters=0.
Reproduction: `full-match-extent-publisher-j12-final.log`, original state of
hand 18, 33 cards played. This is data loss, distinct from compact JSON.

J4 uses the existing API: it retains the short table and publishes complete
payloads correlated by checkpoint, source, and action number as native
ReportObservation.Body(TEXT) in the existing metadata phase. It does not invent
PASS assertions to carry observations. Verification of all 6,634 assertions
and 577 ACTION/OBSERVE records passed after this addition.

J0: the generic requirement is full expected/observed expansion while keeping
the 240-character preview. Add checks that remove only the final scalar from a
long payload and require inspection to FAIL. UiPhase would also benefit from
the neutral observation seam already present on AtfReportPorts.Phase, while
retaining phase ownership. Only J0 changes and packages these extensions; J4
retains consumer composition and real evidence.

</details>

## J14 — gaps in individual J4 publication

J1 retains historical coordination. Current criteria are in [J4 Reporting feedback](../../../atf_j4/docs/feedback/ATF_Reporting.md).

| Priority | Consumer criterion |
|---|---|
| 1 | false PASS resume: a menu with a disabled button passes the pixel threshold |
| 2 | missing comparisons and 209 ms of publication instead of the 37.476 s execution |
| 3 | the individual bid-grid trace does not close an HTML report |

The original J14 audit remains historical. The [inline audit contract](../../../atf_j0/docs/ATF_Inline_Audit_Contract.md) defines current publication boundaries.

### J4 test coordination on a shared device — 2026-11-02, 15:33

Live consumer tests in `ATF_REPORTING_J14_ACTIVE_DO_NOT_CLEAN_9ptTeB` and the J4
AI training run simultaneously use `127.0.0.1:5555` through the ADB server
`tcp:192.168.56.1:5038`. An external test changed Instructions in the main
menu during J4 capture; strict validation rejected the incomplete evidence.
This is execution contention, not a Reporting rendering defect.

- Do not overlap new Android journeys with an active J4 run. Let the current
  process finish without force-stopping it.
- J4 is waiting for exclusive access for the full read and AI duel. The lease
  is in `../../../../whist_back/android-ui-lease.json`.
- The diagnostic is in
  `../../../../whist_back/ai-training/20261102-143525/device-contention.json`.
- Generic Reporting gates do not use ADB. A capture from another journey does
  not become valid evidence merely by appearing in HTML.

## J4 composition without external artifacts — J15

The [J4 contract](../../../atf_j4/docs/reporting/ATF_Android.md) owns case
identity, semantic projection, captured comparisons, and PNGs per checkpoint.
[J4 Reporting feedback](../../../atf_j4/docs/feedback/ATF_Reporting.md) tracks
the standalone deliverable and acceptor qualification. Generic TEXT correction
and neutral regressions are owned by J0.

## J4 — new component and viewer audit

The [J4 audit](../../../atf_j4/docs/reporting/ATF_Audit.md) describes checks for
selection, identity, and evidence. J4 owns receipts, SKIP, acceptors, metrics,
and timing. The [J0 audit contract](../../../atf_j0/docs/ATF_Inline_Audit_Contract.md#acceptanță-permanentă)
retains the regression that returns ownership to the correct test when a PNG
is reused.

## J4 — standalone artifact and full Android screen

The [J4 criteria](../../../atf_j4/docs/feedback/ATF_Reporting.md#rpt-android-html-only---audit-delivery-without-auxiliary-copies)
require per-invocation composition and typed receipts. The migration retains
captured expected and actual values, while gaps in historical material remain
explicit.

Full-screen capture belongs to J4. The [J0 viewer contract](../../../atf_j0/docs/ATF_Inline_Audit_Contract.md#full-image-viewer-target)
defines full display, aspect-ratio preservation, and explicit access to the
original dimensions.

## J16 — shared viewer and separate J4 inputs

The [inline audit contract](../../../atf_j0/docs/ATF_Inline_Audit_Contract.md)
retains the full frame, local caption, and context on close, with neutral
source/JAR regressions. J4 owns the [handoff contract](../../../atf_j4/docs/reporting/ATF_Handoff.md#the-contract-of-the-descriptor)
and [adaptive presentation](../../../atf_j4/docs/reporting/ATF_Presentation.md#modes-of-presentation),
including purpose, configuration, inventory, and budgets.

A JAR copy does not certify these criteria or turn an archived replay into a
new device execution. J0 owns only shared rendering and Reporting tests, while
J4 retains application captures and oracles.
