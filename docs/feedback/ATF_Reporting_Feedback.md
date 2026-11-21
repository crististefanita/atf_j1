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
| UiBackendRecovery.recording | Emite o acțiune INFO pentru fiecare trace: ACTION este `BE observation #n`, TARGET este operația semantică `catalog-readiness`; auditul component proiectează acum ACTION drept câmp `Operation` și omite TARGET |
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

**Status: Activ — testele Maven trec; acceptanța browserului pe raportul real
este parțială; verificarea IntelliJ rămâne deschisă.** J1 consumă acum Reporting J26 și Test Kit K3. Adaptoarele ambelor
componente transmit observațiile recovery imuabile în faza JUnit reală
`TEST BODY`, alături de pasul standard `Component assertions`.

| Dovada artifactului | Comportament observat |
|---|---|
| Raportul real Shop component | `ShopExtentTestExtension` publică nodul INFO separat `Backend recovery trace · <outcome>`, lângă `Component assertions`; valoarea `Attempt N · Operation` este `Backend observation #N`, nu operația semantică de recovery. Verdictul și numărul testelor rămân neschimbate. |
| Raportul real AutoEx component | `AutoExExtentTestExtension` publică aceeași limită și folosește acum aceeași etichetă de eveniment în câmpul `Operation`. |
| Rulări consumer Maven | Shop a trecut 6/6 și AutoEx 5/5 în EN și RO, folosind JAR-urile fizice J26/K3. |
| Randare Playwright | Probele Playwright deținute de consumatori deschid doar HTML-ul local sintetic. O verificare separată în browser a deschis raportul JUnit real Shop și a verificat navigarea, disclosure-urile nested colapsate după `Expand all nodes`, `TEST BODY` și tabelul de aserțiuni vizibile, precum și rândurile de observație randate; apoi s-a oprit la așteptarea specifică fixture-ului `not captured`. Raportul JUnit real AutoEx nu a fost verificat în browser. Operația semantică nu este verificată în proiecția auditului component; proba cu tabel sintetic o arată doar în TARGET. |
| Siguranță și rezultat | Căutarea în sursa celor 17 rapoarte JUnit generate pentru Shop/AutoEx nu a găsit nici canary-ul body-ului privat, nici `127.0.0.1`; `EXHAUSTED` rămâne un rezultat business așteptat într-un test care trece. Acceptanța în browser a redactării în raportul real AutoEx rămâne deschisă. |

- [x] Publicați trace-ul recovery ordonat din fiecare rulare reală JUnit a
  componentelor, într-un disclosure separat, colapsat inițial, cu eticheta
  evenimentului, politica, observația, timing măsurat, rezultat INFO și valori redactate.
  Păstrați pasul standard de aserțiuni, verdictul testului și numărul testelor.
- [x] Rulați probele Playwright deținute de fiecare consumer pe HTML-ul local
  sintetic de recovery. Verificați `Expand all nodes`, dovezile colapsate
  independent, rândurile ordonate complete și redactarea în acele probe.
- [ ] Finalizați acceptanța în browser pe rapoartele JUnit reale Shop și AutoEx
  cu un oracol owner/scenariu independent de fixture. Proba Shop curentă ajunge
  la tabelul de aserțiuni și rândurile de observație, dar se oprește la markerul
  fixture hardcodat `not captured`; raportul real AutoEx nu a fost deschis.
- [ ] Deschideți artifactul real după o rulare IntelliJ. Verificați același
  director de rapoarte, ierarhia de aserțiuni, trace-ul colapsat independent,
  câmpurile ordonate ale încercărilor și redactarea în raportul IDE.

**Dovada rămasă pentru închidere:** inspectarea raportului real după lansarea
testelor componente din IntelliJ. Workspace-ul repo nu are un runner IntelliJ
disponibil în mediul acestei verificări; succesul Maven nu închide paritatea
directorului de output din IDE.

Lipsa etichetei operației semantice și proiecția tipizată a trace-ului rămân
urmărite separat în [J1-RECOVERY-TRACE-TYPING-2026-11-21](ATF_Code_Maintenance_Feedback.md#j1-recovery-trace-typing-2026-11-21).

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
