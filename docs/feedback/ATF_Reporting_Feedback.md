# Reporting — feedback al consumerilor

## Pe scurt

Paritatea rapoartelor IntelliJ rămâne deschisă și este amânată pentru revizia curentă.
Constatările închise păstrează contractele consumerilor și oracolele executabile.

<a id="ui-recovery-audit-01--inspectable-attempts-in-extent"></a>
## UI-RECOVERY-AUDIT-01 — disclosure recovery

**Stare: Verificat și închis.** Ambele probe locale ale consumerilor verifică
observațiile INFO ordonate, țintele semantice, redactarea și un disclosure colapsat
care rămâne închis după Expand all și expune toate rândurile la deschidere.

<a id="j1-recovery-runner-trace-2026-11-21"></a>
## J1-RECOVERY-RUNNER-TRACE-2026-11-21 — rapoarte Maven recovery reale

**Stare: Verificat și închis pentru Maven.** Shop și AutoEx publică operația,
evenimentul, decizia, semnalul și duratele tipizate în rapoartele componentelor.
Oracolele browser expandează rezultatul, TEST BODY și pasul de aserțiuni, apoi
inspectează separat urmele recovered/exhausted, redactarea și verdictul.

README-urile [Shop](../../atf-test-practice-shop/README.md) și
[AutoEx](../../atf-test-auto-ex/README.md) documentează acceptanța opt-in a raportului
real. Căile istorice ale artefactelor și identitatea pachetului rămân în
[proveniență](../../library-provenance.json).

<a id="j1-recovery-ide-report-parity-2026-11-22"></a>
## J1-RECOVERY-IDE-REPORT-PARITY-2026-11-22 — paritatea raportului IntelliJ

**Stare: Deschis; amânat la cererea utilizatorului. Owner:** configurația de lansare.
Rapoartele Maven au trecut oracolele browser; un raport generat efectiv de IntelliJ
nu a trecut aceleași verificări pentru ambii consumeri.

- Rulați fiecare componentă recovery din IntelliJ și păstrați calea raportului emis.
- Furnizați calea către ShopRecoveryActualReportAcceptanceTest sau AutoExRecoveryActualReportAcceptanceTest.
- Verificați separarea aserțiuni/urmă, expandarea, câmpurile tipizate ordonate, redactarea, verdictul și rădăcina output-ului.

Păstrați constatarea deschisă până trec ambele artefacte generate de IDE.

<a id="j1-launch-context-146"></a>
## J1-LAUNCH-CONTEXT-146 — selecția reală și planul de descoperire

**Stare: Verificat și închis.** Test Kit furnizează selectorii request-ului,
inventarul filtrat și numărul descoperit; consumerii îi publică în Launch context.
Testele dezactivate rămân în numărul inițial, iar înregistrările dinamice sunt separate.

Probele de clasă, metodă și suită filtrată/canonică verifică valorile exacte și
expandarea integrală în browser. Contractele native și pe JAR fizic acoperă lansări
imbricate/paralele izolate, planuri executate ulterior și lipsa observerului/provenienței.
Controalele verificate rămân în [baseline](../ATF_Test_Changes_Baseline.md).
