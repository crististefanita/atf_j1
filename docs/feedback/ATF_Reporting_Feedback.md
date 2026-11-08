# Reporting — owner și criterii în J0

## Pe scurt

Acest consumer folosește Reporting JAR. [Registrul canonic J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md) deține criteriile și prioritățile curente; nu păstrăm o copie concurentă a backlog-ului.

Reproducerea locală se leagă de versiunea/hash-ul JAR-ului și de scenariul exact. Implementarea și regresia permanentă cu mock/contract se verifică în J0, apoi consumerul acceptă binarul livrat, conform [protocolului](../ATF_Reporting_Consumer.md).

<details>
<summary><strong>Trimiteri stabile către criteriile ownerului</strong></summary>

## Criterii rămase pentru consolidare

[Conținut și criteriu curent în J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#criterii-rămase-pentru-consolidare).

## Ordinea pregătirii

[Conținut și criteriu curent în J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#ordinea-pregătirii).

## REPORT-PUBLIC-API-43 — suprafață susținută și ownership

[Conținut și criteriu curent în J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#report-public-api-43--suprafață-susținută-și-ownership).

## REPORT-RELEASE-CONTRACT-45 — ce conține și ce promite pachetul

[Conținut și criteriu curent în J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#report-release-contract-45--ce-conține-și-ce-promite-pachetul).

## REPORT-PORTABLE-DOCS-46 — adopție externă și documente transportabile

[Ghidul de distribuire și consum al bibliotecilor J0](../../../atf_j0/docs/ATF_Platform_Libraries.md).

## REPORT-FLOW-PROJECTION-36 — plan de dovezi separat de publicare

[Conținut și criteriu curent în J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#report-flow-projection-36--plan-de-dovezi-separat-de-publicare).

## UI-RECOVERY-AUDIT-01 — încercări inspectabile în Extent

[Conținut și criteriu curent în J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#ui-recovery-audit-01--încercări-inspectabile-în-extent).

## Index fallback — contract livrat J11

[display contract](../../../atf_j0/docs/ATF_Reporting_Display_Contract.md) păstrează indexul neutru, statusul separat și numele integrale; criteriul generic 66 nu mai este deschis.

## Contracte existente

[Conținut și criteriu curent în J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#contracte-existente).

</details>

## Current library adoption

See [consumer provenance](../../library-provenance.json) and the
[large report navigation contract](../../../atf_j0/docs/ATF_Large_Report_Navigation.md).

## Verified J1 live acceptance

The [2026-11-03 purchase and rendering receipt](../ATF_J1_Live_Screenshot_Verification.md)
owns the historical evidence for the launch-context finding below.

## J1-LAUNCH-CONTEXT-146 — record the actual launch scope

**Priority: P2. Owner:** J1 launch metadata and consumer composition; reusable
JUnit context capture belongs in Kit. This is an audit-context gap, with all
four business and rendering gates passing.

| Witness | Observed fact |
|---|---|
| Actual launch | The retained discovery plan executes one purchase through AutoExE2eSuite or ShopE2eSuite |
| Published identity | Neither suite HTML contains its actual canonical suite name; secondary execution metadata does not supply it |
| Ambiguous filename | AutoEx uses purchase-suite in both individual and suite report names |
| Existing boundary | ReportEnvironmentField.SUITE is intentionally hidden from operational System; launch facts belong in secondary Tests/trace metadata |

- [ ] Capture immutable launch scope, real suite root when known, selected class/method and effective selection count at the JUnit boundary; preserve an explicit unknown state when unavailable.
- [ ] Supply those safe facts through existing neutral metadata/observation APIs; retain the business step titles, one report per suite and preview 240.
- [ ] Add permanent component contracts for direct and filtered canonical-suite launches, including same-title classes and a misleading filename; never derive scope from artifact text.
- [ ] Keep browser acquisition and application behavior out of this metadata change; generic rendering regressions belong in J0 only if an actual publication defect is reproduced.

**Closure:** a standalone reader identifies the real launch context from each
HTML without the source checkout or external launcher log. These passing live
artifacts are the baseline, not a substitute for the permanent negative oracle.
