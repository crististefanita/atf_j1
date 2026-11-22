# Reporting — feedback despre consumul J1

## Pe scurt

Registrul păstrează lacunele de integrare Reporting specifice J1. Reprodu
fiecare constatare folosind coordonata Reporting selectată și păstrează raportul
generat ca dovadă.


<a id="ui-recovery-audit-01--inspectable-attempts-in-extent"></a>

## UI-RECOVERY-AUDIT-01 — toate observațiile de recovery trebuie inspectabile în Extent

**Stare: verificată și închisă la 2026-11-21.** Testele component creează probe
Extent locale din înregistrările recovery capturate de fiecare consumer.
Fiecare probă construiește un scenariu sintetic și un pas cu rezultatul în
titlu; înregistrările J1 ordonate sunt randate într-un singur disclosure
`Audit observations`, colapsat inițial și expus ca
`details.atf-ui-observations`. Proba verifică proiecția locală a consumerului
și randarea, nu un pas agregat părinte generat de suită sau un raport E2E de pe
site-ul public.

| Dovadă în cod | Ce se înregistrează |
|---|---|
| `UiBackendRecovery.recording` | Emite o acțiune INFO pentru fiecare trace: ACTION este `BE observation #n`, iar TARGET este operația semantică `catalog-readiness` |
| Proba sintetică a consumerului | Afișează separat evenimentul și ținta în coloanele tabelului de acțiuni și verifică ținta semantică |
| Proiecția raportului JUnit real | Observația consumer tipizată afișează `Operation = catalog-readiness` și separă `Event = Backend observation #n`; vezi [rapoartele actuale de recovery](#j1-recovery-runner-trace-2026-11-21) |
| UiRecoveryTrace.description | Decizia, motivul, sumarul semnalului capturat când există, duratele probe/backoff/total și sursa politicii |
| README-ul curent al consumerului | Descrie probe locale de raportare componente și diferențiază pasul sintetic cu rezultatul în titlu de raportul produs de o suită |
| Verificări ale raportului 2026-11-21 | AutoExBackendRecoveryComponentTest a trecut 5/5, iar ShopBackendRecoveryComponentTest 6/6, în ambele ediții; fiecare test component deschide în Playwright propria probă Extent locală sintetică, separat de raportul JUnit al suitei |

- [x] Rulează scenarii deterministe recuperate și terminal epuizate în ambii consumatori.
- [x] Verifică titlul pasului cu rezultatul și încercările ordonate într-un singur disclosure de observații, colapsat inițial.
- [x] Extinde disclosure-ul și verifică toate rândurile, deciziile, metadatele sigure ale semnalelor, duratele, sursa politicii și redactarea body-urilor și URL-urilor locale.
- [x] Verifică starea inițial colapsată, comportamentul expand-all, vizibilitatea tuturor rândurilor și redactarea în testele locale component/browser.

**Dovezi de închidere:** ambele teste consumer trec în EN și RO.

- Playwright confirmă că disclosure-ul pornește închis și rămâne închis după
  `Expand all nodes`; deschiderea lui afișează fiecare încercare în ordine.
- Testul component construiește un scenariu sintetic și un pas cu rezultatul în
  titlu; rândurile capturate de J1 trec prin rendererul Reporting selectat.
- Proba verifică proiecția locală a consumerului și randarea disclosure-ului.
  Nu verifică un pas părinte generat de suite sau un raport E2E public.
- HTML-ul generat exclude URL-ul fixture-ului și body-ul privat al răspunsului.

Controlul verificat rămâne în
[baseline](../ATF_Test_Changes_Baseline.md#recovery-extent-report-rendering).

<a id="j1-recovery-runner-trace-2026-11-21"></a>

## J1-RECOVERY-RUNNER-TRACE-2026-11-21 — urmele recovery în rapoartele reale ale componentelor

**Stare: Verificată și închisă pentru rapoartele componente generate de Maven la
2026-11-22.** Proiecțiile ambilor consumatori publică datele recovery tipizate
în rezultatul real JUnit. Faza randată `TEST BODY` conține pași expandabili
separați pentru `Component assertions` și trace-ul recovery INFO.

| Dovada artifactului | Comportament observat |
|---|---|
| Raportul real Shop component | `Operation` afișează `catalog-readiness`; `Event` afișează `Backend observation #N`. Încercările rămân ordonate și INFO, cu politica, decizia, motivul, semnalul și timpii probe/backoff/total tipizați. |
| Raportul real AutoEx component | Aceleași câmpuri deținute de consumer și rezultate terminale apar prin `AutoExExtentTestExtension`; aserțiunile, verdictul și numărul de teste rămân intacte. |
| Rulări consumer Maven | Shop a trecut 6/6, iar AutoEx 5/5 cu perechea Reporting J26/Test Kit K3 fixată. |
| Randare Playwright | Testele opt-in ale consumatorilor deschid HTML-ul real al suitei pentru `RECOVERED` și `EXHAUSTED`. Extind rezultatul component, `TEST BODY` și pasul standard de aserțiuni, apoi verifică independent disclosure-ul recovery colapsat după `Expand all nodes`; verifică toate câmpurile încercărilor, timpii ISO-8601, rezultatul INFO, redactarea body/URL și o captură de viewport. Testele rendererului sintetic verifică separat țintele din tabelul de acțiuni. |
| Siguranță și rezultat | Acceptanța citește calea artifactului furnizată explicit și nu suprascrie intrarea. Capturile rămân sub `target` în staging; body-ul răspunsului și adresa fixture-ului local nu apar în raport. |

- [x] Păstrați operația semantică, eticheta evenimentului și duratele tipizate
  drept câmpuri distincte deținute de fiecare consumer.
- [x] Verificați urmele recovered și exhausted în rapoartele Maven reale din
  ambele module, inclusiv disclosure-ul, completitudinea rândurilor, redactarea
  și verdictul.
- [x] Păstrați selectorii și proiecția locale fiecărui consumer, sub guvernanța
  arhitecturii.

Paritatea directorului de output IntelliJ este urmărită separat în
[J1 recovery IDE report parity](#j1-recovery-ide-report-parity-2026-11-22).

<a id="j1-recovery-ide-report-parity-2026-11-22"></a>

## J1-RECOVERY-IDE-REPORT-PARITY-2026-11-22 — verificarea căii de raport IntelliJ

**Stare: Deschisă; responsabil:** configurarea lansării testelor componente J1.

Noua acceptanță Playwright deschide un raport real după o cale explicită și
verifică artifactele Shop și AutoEx generate de Maven. Nu a fost încă verificat
un raport lansat din IntelliJ cu același oracle.

- Rulați fiecare test component recovery din IntelliJ cu configurația sa
  obișnuită și păstrați calea raportului generat.
- Deschideți fiecare artifact produs de IDE cu proprietatea corespunzătoare
  `ShopRecoveryActualReportAcceptanceTest` sau
  `AutoExRecoveryActualReportAcceptanceTest`.
- Confirmați același owner și faza `TEST BODY` extinsă, cu pași separați pentru
  aserțiuni și trace, trace colapsat, câmpuri ordonate operație semantică/
  eveniment/timpi, redactare și verdict.

Păstrați constatarea deschisă până când ambele artefacte IntelliJ trec aceeași
acceptanță în browser ca rapoartele Maven.

<a id="j1-launch-context-146"></a>

## J1-LAUNCH-CONTEXT-146 — înregistrarea domeniului real al lansării

**Stare: Rămâne deschisă după reverificarea din 2026-11-21. Responsabil:**
metadatele lansării J1 și compoziția consumerului. Verificările business și de
randare trec; metadatele de audit nu conțin încă rădăcina JUnit și selecția
efectivă a testelor.

| Martor | Fapt observat |
|---|---|
| Compoziția AutoEx | `AutoExRunFactory` partajează o resursă de raport din store-ul rădăcină JUnit, dar denumește raportul după metoda de test și fiecare scenariu după clasa/display name al copilului; nu publică suita rădăcină sau selecția efectivă |
| Compoziția Shop | `ShopE2eTestSupport` partajează un raport din store-ul rădăcină; `ShopRunReportFactory` primește configurația, profilul de dovezi și mediul, nu identitatea lansării JUnit |
| Nume de fișier ambiguu | `AutoExReportSession.fileName` derivă numele raportului din metodă, astfel o rulare directă a metodei și o rulare de suită pot avea același prefix |
| Limită existentă | `ReportEnvironmentField.SUITE` este ascuns intenționat din System operațional; informația lansării aparține metadatelor secundare Tests/trace |
| Verificare controlată 2026-11-20 | `AutoExE2eOverviewNarrativeComponentTest` a trecut două scenarii fixture UI/UI_API în scope de suită și a verificat expandarea raportului; nu verifică suita canonică rădăcină sau selecția efectivă |

Verificările de recovery și guvernanța întregului reactor din 2026-11-21 nu
afirmă identitatea lansării. Ele lasă deschisă această constatare despre metadate.

- [ ] Capturează scope-ul imuabil al lansării, rădăcina canonică a suitei când
  este cunoscută, clasa/metoda selectată și numărul selecției efective la
  limita JUnit; păstrează o valoare unknown explicită când informația lipsește.
- [ ] Publică aceste fapte sigure prin limita neutră existentă pentru metadate,
  în afara System operațional; păstrează titlurile pașilor business, un raport
  per suită și preview-ul de 240 de caractere.
- [ ] Adaugă teste J1 pentru lansări directe și filtrate ale suitei canonice,
  inclusiv clase cu același titlu și nume de fișier înșelătoare. Nu deduce
  scope-ul din numele artefactelor sau textul afișat al pașilor.
- [ ] Dacă contractul Test Kit selectat nu expune contextul necesar, notează
  coordonata exactă a bibliotecii și o reproducere J1 minimală; păstrează
  achiziția browserului și comportamentul aplicației în afara acestui item.

**Închidere:** cititorul identifică suita-rădăcină JUnit, selecția efectivă și
testele selectate din fiecare raport HTML fără checkout de surse sau loguri ale
launcherului. Lansările directe și filtrate trec testele pozitive și negative;
testele business sau de randare singure nu închid această lacună de metadate.
