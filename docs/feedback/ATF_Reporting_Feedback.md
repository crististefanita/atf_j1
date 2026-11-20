# Reporting — J0 owner and criteria

## At a glance

This consumer uses the Reporting JAR. The [canonical J0 register](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md) owns the current criteria and priorities; we do not keep a competing copy of the backlog.

Local reproductions are tied to the JAR version/hash and the exact scenario. Implementation and permanent mock/contract regression are verified in J0; the consumer then accepts the delivered binary according to the [protocol](../ATF_Reporting_Consumer.md).

<details>
<summary><strong>Stable links to owner criteria</strong></summary>

## Criteria remaining for consolidation

[Current content and criterion in J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#criterii-rămase-pentru-consolidare).

## Preparation order

[Current content and criterion in J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#ordinea-pregătirii).

## REPORT-PUBLIC-API-43 — supported surface and ownership

[Current content and criterion in J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#report-public-api-43--suprafață-susținută-și-ownership).

## REPORT-RELEASE-CONTRACT-45 — package contents and promises

[Current content and criterion in J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#report-release-contract-45--ce-conține-și-ce-promite-pachetul).

## REPORT-PORTABLE-DOCS-46 — external adoption and portable documentation

[J0 library distribution and consumption guide](../../../atf_j0/docs/ATF_Platform_Libraries.md).

## REPORT-FLOW-PROJECTION-36 — evidence plan separate from publication

[Current content and criterion in J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#report-flow-projection-36--plan-de-dovezi-separat-de-publicare).

## UI-RECOVERY-AUDIT-01 — inspectable attempts in Extent

[Current content and criterion in J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#ui-recovery-audit-01--încercări-inspectabile-în-extent).

## Index fallback — J11 delivered contract

The [display contract](../../../atf_j0/docs/ATF_Reporting_Display_Contract.md) preserves the neutral index, separate status, and full names; generic criterion 66 is no longer open.

## Existing contracts

[Current content and criterion in J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#contracte-existente).

</details>

## Current library adoption

See [consumer provenance](../../library-provenance.json) and the
[large report navigation contract](../../../atf_j0/docs/ATF_Large_Report_Navigation.md).

## Verified J1 live acceptance

The [2026-11-03 purchase and rendering receipt](../ATF_J1_Live_Screenshot_Verification.md)
owns the historical evidence for the launch-context finding below.

<a id="j1-launch-context-146"></a>
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
