# Core C3 and J2 Consumer Profiles — Verification 2026-11-02

## At a glance

J0 retains Core and Reporting source and their permanent tests. J1–J4 consume
the same Core C3 pair; Reporting remains at J12, with no artificial release to
adopt an existing API.

| Artifact | Identitate |
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
- J2 profiles are declared by the owner before reading HTML; identity, business
  effect, typed SQL, and complete-value oracles remain independent.

## Executed matrix

| Proof | Result / boundary |
|---|---|
| Core source | 286 native tests, zero failures/errors/skips |
| Parentless Core | two independent fresh builds, 286 native tests each, byte-identical JAR |
| Physical Core JAR | the same 286 native tests plus one CodeSource control; no Core source |
| API C2 → C3 | zero public/protected symbols removed |
| Reporting on Core C3 | 16 source cases, 43 JAR cases, and one identity control; zero failures/errors/skips in accepted gates |
| J2 Eventuate | 14 reports, 18 invocations, six controlled JUnit FAIL results |
| J2 Mock | 14 reports, comparisons and verdicts from independent proofs; no fake HTTP |
| J2 SQL | 12 reports, 36 invocations, four controlled JDBC exceptions |
| J2 profiles | 40/40 conform; every ACTUAL mutant rejected; five additional unit contracts |
| Browser | Extent suite/FAIL, combined Mock and message/SQL fallback; mouse, Enter/Space, final row, and exact backlink |

## Limits and documentation

SQL has no runtime Core or Reporting dependency. Eventuate loads Core C3, and
both modules consume the physical Reporting J12 artifact. Cancelling a future
does not prove server-side rollback; these proofs do not certify the Kafka, ASR,
or Android applications.

- Active J0 feedback retains only Core/Reporting. Test Kit, shared governance,
  AI, and consumer coordination are tracked in the J1 register.
- The audit history remains a dated document; read proven contracts at their
  owner. The transfer does not close criteria without implementation and proof.

TMP evidence: ATF_ADOPTION_CORE_C3_ACTIVE_DO_NOT_CLEAN_HjeEfO — gates, XML,
core-binary-api.json, C3 matrices, inventory, and guarded promotion. Reporting
source/JAR regressions and final governance are recorded in the increment receipts.


<details>
<summary><strong>Excluded runs and acceptance limits</strong></summary>

- A source replay hit the 5 s DOM limit in `JsonEvidenceFidelityComponentTest`
  under concurrent load. An isolated repeat passed 16/16 with the same threshold;
  the original log is retained.
- Two documentation gates targeted another staging area and are excluded in
  `invalid-documentation-gates.json`. Correct gates verify project identity
  explicitly; the runner rejects a name/workdir mismatch.
- The copied J4 TMP cache contained invalid POM/JAR files; accepted verification
  uses a fresh cache and two passing Core/Reporting contracts.
- The intermediate Core publisher was replaced before delivery. Only
  publisher-3/4 produce the final byte-identical candidate with all 149 inputs pinned.
- New Reporting requirements for high-volume memory and full ACTION/OBSERVE
  remain open in J0; J4 mitigations and Core C3 do not close them.

</details>
