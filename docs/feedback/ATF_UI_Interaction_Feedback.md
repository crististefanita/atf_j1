# UI interaction — current findings

## Verified contract — UI-FIRST-PAINT-144

UI-FIRST-PAINT-144 is resolved for the bounded J1 fixtures. The
[verification record](../ATF_UI_First_Frame_Verification.md) retains the original
failures, controlled reproduction, runtime comparison and final acceptance.

| Boundary | Delivered evidence |
|---|---|
| Preparation | Explicit load, native document attachment and viewport PNG share the existing 5 s navigation deadline |
| Native actions | Original 700 ms cap, one trusted activation, no retry or target mutation; disabled, covered, moving and readonly targets remain refused |
| Driver runtime | Validation inherits the SDK environment; the runner's silent agent-Node override was removed |
| Regression | 39 individual and 39 suite cases pass; 42 adjacent governance cases pass |
| Rendering | Four artifacts checked in Chromium; collapsed comparisons, dynamic tables, phase timing, failure deep link and full native PNG verified |
| Authorization | Verified UI control in the [technical baseline](../ATF_Test_Changes_Baseline.md); existing AutoEx control remains separate |

No unresolved criterion remains here. The exact historic host/GPU scheduling
cannot be recovered, and the Node comparison does not establish universal
Node 24 incompatibility; those limits remain explicit in the verification record.

## Verified screenshot context

The [full-context capture contract](../../atf-ui/README.md#full-context-screenshots)
preserves the entire browser viewport at the configured scale and the whole
Android display. Region close-ups supplement the original context frame.

| Gate | Result |
|---|---|
| Native pixels | Every corner at 70/100%, additional lower-page context at 70%, and existing full-document height: 3/3 |
| Consumer wiring | AutoEx 2/2 and Shop 2/2, with configured 70% and auditable assertions |
| Source governance | 8/8; the verified test-change control is retained in the baseline |
| Rendering | Three artifacts; native PNG, full-screen fit, aspect ratio, four edges, phase timing, disclosures and failure navigation verified |

No unresolved screenshot-context criterion remains here. The
[verification follow-up](../ATF_UI_First_Frame_Verification.md#full-context-screenshot-follow-up)
retains execution locations and review links; production capture and JAR bytes are unchanged.
