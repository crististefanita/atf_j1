# JSON and Core C2 / Reporting J9 Libraries — Acceptance

## At a glance

Historical C2/J9 evidence. The current disclosure and governance contract is
described by the [Reporting library atlas](ATF_Reporting_Library_Atlas.md).
These C2/J9 results remain historical.

This J1 checkout records historical acceptance of the C2/J9 bytes. JAR/POM
pairs are stored directly in `lib`, one selected version per library; no Core
source module is included in this reactor.

| Library | JAR SHA-256 |
|---|---|
| atf.api:atf-core:1.0.0-c2 | 91080FE996CCB358B7E42B8F147322B3D4468D84C364DFD259371EEEF9AFD5DB |
| atf.api:atf-reporting:1.0.0-j9 | 2B58E5B871CCB78AF53C5BF77F1A21B6F6EAE92D18EF5F66CAC6FF9CCA154322 |

## Contract and evidence

| Surface | Result and boundary |
|---|---|
| Core source and C2 JAR | 276 native PASS each; complete document, duplicates, null/write, exact redaction, and factory before submission |
| Core C1 control | 37 new cases, 20 expected FAIL results; the same regression on old source and physical JAR |
| Mock and Validation | source suites and J1 consumer suites; independent route fallback, exact YAML scalar, types, and raw body retained |
| Native Reporting | 64 PASS per build; redactor and formatter proved separately, including counts and duplicates |
| Physical Reporting J8 control | 16 cases, 15 expected FAIL results; numeric precision error and multiplicity loss reproduced |
| Physical Reporting J9 | 39 PASS: native JSON, browser JSON, API disclosure, and CodeSource; no Reporting implementation in the harness |
| Reporting source unit tests | 486 PASS; separate from the eight JSON component tests and 22 API disclosure cases |
| Kit | 133 PASS separately in each workspace; the two requirement proofs are semantically distinct, with no relaxed threshold |
| J1 | full governance and one Shop/AutoEx component test each; no new external journey run is claimed |
| publication | two identical clean builds for each JAR; API comparison removes no public symbols |

## Rendering and remaining limits

- The automated browser verifies Extent/fallback, individual/aggregate views, active ownership, numeric tokens, and every duplicate occurrence.
- Mouse, Enter, and Space preserve the audited disclosure; the final row and FAIL/FAIL/PASS verdicts remain accessible.
- Assertions retain the default of 240; the numeric fixture declares a 240-character body without changing the standalone default of 100.
- The oracle rejects rounding or removing a duplicate even when statuses remain identical.
- TAB MCP confirms values and keyboard fallback; closing a neighbor after Expand all exposes REPORT-SIBLING-DISCLOSURE-114 separately.

Four earlier Reporting cohesion/import criteria remain separate from this JSON
acceptance. Capture/publication timing, inspector profile, contrast, and fallback
width keep distinct criteria; JSON fidelity does not close them implicitly.

## Reproduction and synchronization

[Core Consumer](ATF_Core_Consumer.md) describes the contract, Maven/IDE bootstrap,
and history retention. [Synchronization](ATF_Workspace_Synchronization.md)
requires an explicit manifest, drift verification, and selective promotion.

Evidence for this increment is in external staging
ATF_JSON_C2_J9_ACTIVE_DO_NOT_CLEAN_f8f75e12: `acceptance-evidence.json`, logs,
publication manifests, and the four HTML files in `publication`. Retired source
and old pairs are archived recoverably; outputs do not enter the live checkout.
