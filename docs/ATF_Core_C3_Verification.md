# Core C3 — J1 Consumer Verification 2026-11-02

## At a glance

J1 consumed the Core C3 pair while Reporting remained at J12; no new release
was needed to use an existing API. This dated record keeps package identity and
historical verification results separate from current library versions.

| Artifact | Identity |
|---|---|
| Core | atf.api:atf-core:1.0.0-c3 |
| Core JAR SHA-256 | 605407B9D40E67C9C352EE6E94DA758E50BD3CFB14CC0D7A0DE3359C71088447 |
| Core POM SHA-256 | 7853373300E588B2EA25F6A07C927ECAC1FB06044073AD75FDA8D6B7CFF275E0 |
| Reporting unchanged | atf.api:atf-reporting:1.0.0-j12 |
| Reporting JAR SHA-256 | 1F1B7B5502FEFEA02F500C2FC80494859D4E57E38261306B57FA7AD2D5AB38B1 |

## Proven contracts

- Core redacts decoded public START/COMPLETE names and JSON strings according to
  the configured policy; the original request and response remain intact.
- `HttpClientTransport` makes a best-effort cancellation call upstream, once for
  the caller, while preserving the interrupt flag; concurrent completion does
  not replace the terminal verdict.
- Cleanup failure is suppressed on the primary cancellation. A blocked local
  GET retains one dispatch, `CANCELLED`, and no invented response.

## Executed matrix

| Proof | Result / boundary |
|---|---|
| Core source verification | 286 native tests, zero failures/errors/skips |
| Parentless Core package | two independent fresh builds, 286 native tests each, byte-identical JAR |
| Physical Core JAR | the same 286 native tests plus one CodeSource control; no source in the consumer |
| API C2 → C3 | zero public/protected symbols removed |
| Reporting on Core C3 | 16 source cases, 43 JAR cases, and one identity control; zero failures/errors/skips in accepted gates |
| Browser | Extent suite/FAIL, combined Mock and message/SQL fallback; mouse, Enter/Space, final row, and exact backlink |

## Limits and documentation

The consumer acceptance recorded here verifies the published library contract
for J1. Cancelling a future does not prove server-side rollback; these proofs do
not certify behavior beyond the listed local and binary checks.

- Provenance and package-verification records describe the historical inputs;
  they do not claim those source checks ran in J1.
- Read current consumer contracts in the local library guides. This dated record
  does not establish the behavior of later library versions.

TMP evidence: ATF_ADOPTION_CORE_C3_ACTIVE_DO_NOT_CLEAN_HjeEfO — gates, XML,
core-binary-api.json, C3 matrices, inventory, and guarded promotion. The dated
receipt records package verification separately from J1 consumer acceptance.


<details>
<summary><strong>Excluded runs and acceptance limits</strong></summary>

- A source replay hit the 5 s DOM limit in `JsonEvidenceFidelityComponentTest`
  under concurrent load. An isolated repeat passed 16/16 with the same threshold;
  the original log is retained.
- Two documentation gates targeted another staging area and are excluded in
  `invalid-documentation-gates.json`. Correct gates verify project identity
  explicitly; the runner rejects a name/workdir mismatch.
- The intermediate Core publisher was replaced before delivery. Only
  publisher-3/4 produce the final byte-identical candidate with all 149 inputs pinned.
- This historical consumer verification does not close requirements for
  high-volume memory or complete ACTION/OBSERVE evidence in later versions.

</details>
