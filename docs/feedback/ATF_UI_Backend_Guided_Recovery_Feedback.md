# ATF UI — lifecycle-ul sesiunii browser

## Pe scurt

Sincronizarea nativă, geometria iframe și observerii au contracte și probe la
[UI](../../atf-ui/README.md). Proiecția recovery are [owner Reporting](ATF_Reporting_Feedback.md#ui-recovery-audit-01--încercări-inspectabile-în-extent).
Restanța de aici privește sesiunea browser, distinctă de lifecycle-ul listener-elor.

## UI-SESSION-LIFECYCLE-59 — cauza primară la achiziție și close

**Prioritate: 72/100** = impact 30 + lacună 20 + lucrabil 16 + documentare 6.
**Owner:** UI pentru `PlaywrightUiSession`; consumerii păstrează ordinea resurselor
lor, iar fixture-urile test-only nu extind API-ul public al runtime-ului.

| Traseu static live | Risc delimitat |
|---|---|
| `open()` prinde `RuntimeException`, apoi apelează direct `playwright.close()` | eroarea închiderii poate înlocui eroarea inițială; ramura `Error` nu are rollback echivalent |
| `close()` imbrică inspector/context/browser/driver în `finally` | toate sunt încercate, dar o eroare ulterioară poate masca prima cauză |
| probele listener-elor și achiziției Shop | au alt owner de resursă; nu demonstrează implicit protocolul sesiunii UI |

Constatarea este de analiză statică a căilor de failure; nu declară un leak
nativ nou reprodus. O probă focalizată trebuie să distingă dobândirea resursei,
efectul operației și eroarea callback-ului de cleanup.

- [ ] Probați refuz înainte/după fiecare resursă dobândită, cu `RuntimeException`
      și `Error`; păstrați aceeași cauză și fiecare eroare secundară fără self-suppression.
- [ ] La close, executați toate închiderile deținute o singură dată, în ordinea
      declarată; două erori simultane nu se înlocuiesc prin ultimul `finally`.
- [ ] Folosiți seam-uri de test locale/package-private; fără hook-uri publice
      de fault, timeout mărit, retry de mutații sau o dependență UI → Test Kit.
- [ ] Revalidați o componentă browser și o invocare consumer individual/prin
      suită; cauza, raportul și vecinul sănătos își păstrează identitatea.

**Închidere:** achiziția și închiderea au probe negative independente, iar
README-ul UI descrie exact protocolul demonstrat. Primitivele deja livrate
nu se redeschid și nu se relaxează pentru acest increment.

Procesul de lucru este în [staging și sync sigur](../ATF_Build_And_Execution_Safety.md).
