# Reporting — responsabilul și criteriile din J0

## Pe scurt

Acest consumator folosește biblioteca Reporting din JAR. [Registrul canonic J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md) deține criteriile și prioritățile curente; nu păstrăm o copie concurentă a restanțelor.

Reproducerea locală se leagă de versiunea/hash-ul JAR-ului și de scenariul exact. Implementarea și regresia permanentă cu mock/contract se verifică în J0, apoi consumatorul acceptă binarul livrat, conform [protocolului](../ATF_Reporting_Consumer.md).

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

## Adoptarea curentă a bibliotecii

Consultați [proveniența consumerului](../../library-provenance.json) și
[contractul de navigare a rapoartelor mari](../../../atf_j0/docs/ATF_Large_Report_Navigation.md).

## Acceptanța live verificată în J1

Documentul de verificare pentru [achiziție și randare din 2026-11-03](../ATF_J1_Live_Screenshot_Verification.md)
păstrează dovezile istorice pentru constatarea despre contextul lansării de mai jos.

<a id="j1-launch-context-146"></a>
<a id="j1-launch-context-146--record-the-actual-launch-scope"></a>
## J1-LAUNCH-CONTEXT-146 — înregistrarea domeniului real al lansării

**Prioritate: P2. Responsabil:** metadatele lansării și compoziția consumerului
în J1; capturarea reutilizabilă a contextului JUnit aparține Kit-ului. Este o
lacună de context de audit, iar toate cele patru porți de business și randare au trecut.

| Verificare | Fapt observat |
|---|---|
| Lansarea efectivă | Planul de descoperire păstrat execută o achiziție prin AutoExE2eSuite sau ShopE2eSuite |
| Identitatea publicată | Niciun HTML al suitei nu conține numele canonic real al suitei; metadatele secundare de execuție nu îl furnizează |
| Nume de fișier ambiguu | AutoEx folosește purchase-suite atât pentru raportul individual, cât și pentru cel al suitei |
| Limita existentă | ReportEnvironmentField.SUITE este ascuns intenționat din System operațional; faptele despre lansare aparțin metadatelor secundare Tests/trace |

- [ ] Capturați la limita JUnit domeniul imuabil al lansării, rădăcina reală a suitei când este cunoscută, clasa/metoda selectată și numărul efectiv de selecții; păstrați explicit starea necunoscută când valoarea nu este disponibilă.
- [ ] Furnizați aceste fapte sigure prin API-urile neutre existente pentru metadate/observații; păstrați titlurile pașilor de business, câte un raport per suită și preview-ul de 240 de caractere.
- [ ] Adăugați contracte permanente de componentă pentru lansări directe și filtrate ale suitei canonice, inclusiv clase cu același titlu și un nume de fișier înșelător; nu deduceți domeniul din textul artifactului.
- [ ] Nu includeți achiziția browserului sau comportamentul aplicației în această schimbare de metadate; regresiile generice de randare aparțin J0 numai dacă se reproduce un defect real de publicare.

**Închidere:** un cititor poate identifica contextul real al lansării direct
din fiecare HTML, fără checkout-ul sursă sau logul launcherului extern.
Artifactele live care au trecut sunt baseline-ul, nu înlocuitorul oracolului
negativ permanent.
