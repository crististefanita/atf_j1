# Reporting — feedback despre consumul J1

## Pe scurt

Registrul păstrează lacunele de integrare Reporting specifice J1. Reprodu
fiecare constatare folosind coordonata Reporting selectată și păstrează raportul
generat ca dovadă.


<a id="ui-recovery-audit-01--inspectable-attempts-in-extent"></a>

## UI-RECOVERY-AUDIT-01 — toate observațiile de recovery trebuie inspectabile în Extent

**Stare:** Deschisă · **Responsabil:** dovezile recovery J1 și acceptanța raportului.
Adaptorul local de înregistrare emite un singur record informativ per
UiRecoveryTrace, iar documentația consumerilor descrie doar indicatorul agregat.
Un raport randat nu a confirmat încă afișarea trace-ului complet într-un singur
grup expandabil.

| Dovadă în cod | Ce se înregistrează |
|---|---|
| UiBackendRecovery.recording | Un record informativ pentru fiecare observație, cu titlul încercării și operația |
| UiRecoveryTrace.description | Decizia, motivul, sumarul semnalului capturat când există, duratele probe/backoff/total și sursa politicii |
| README-ul consumatorului | Descrie un indicator agregat de recovery; nu specifică ierarhia vizibilă pentru întreaga secvență |
| Verificări de componentă 2026-11-20 | AutoExBackendRecoveryComponentTest a trecut 5/5, iar ShopBackendRecoveryComponentTest 6/6; ambele verifică siguranța trace-ului și limitele dispatch-ului, dar niciuna nu generează raport Extent |

- [ ] Rulează un scenariu determinist recuperat și unul terminal refuzat/epuizat în fiecare consumator care activează recovery.
- [ ] Verifică raportul Extent generat: toate observațiile sunt grupate sub un singur pas recovery colapsat, în ordinea încercărilor, iar rezultatul agregat rămâne vizibil la părinte.
- [ ] Extinde grupul și verifică decizia, motivul, metadatele sigure ale semnalului, durata și sursa politicii pentru fiecare încercare. Confirmă absența body-urilor și URL-urilor private.
- [ ] Adaugă sau păstrează o aserțiune locală component/browser pentru starea inițial colapsată, expandarea tuturor, vizibilitatea completă a copiilor și redactare. Păstrează aserțiunile recovery separate de verdicturile business.

**Închidere:** un raport J1 generat și o verificare locală a raportului randat
dovedesc că toate încercările sunt accesibile dintr-un singur grup colapsat și
rămân complete după expandare, inclusiv expand-all. Sunt acoperite rezultatul
recuperat și cel terminal; nu se publică conținut sensibil al cererilor.

<a id="j1-launch-context-146"></a>

## J1-LAUNCH-CONTEXT-146 — înregistrarea domeniului real al lansării

**Stare: Deschisă. Responsabil:** metadatele lansării J1 și compoziția
consumerului. Verificările business și de randare trec; metadatele de audit nu
conțin încă rădăcina JUnit și selecția efectivă a testelor.

| Martor | Fapt observat |
|---|---|
| Compoziția AutoEx | `AutoExRunFactory` partajează o resursă de raport din store-ul rădăcină JUnit, dar denumește raportul după metoda de test și fiecare scenariu după clasa/display name al copilului; nu publică suita rădăcină sau selecția efectivă |
| Compoziția Shop | `ShopE2eTestSupport` partajează un raport din store-ul rădăcină; `ShopRunReportFactory` primește configurația, profilul de dovezi și mediul, nu identitatea lansării JUnit |
| Nume de fișier ambiguu | `AutoExReportSession.fileName` derivă numele raportului din metodă, astfel o rulare directă a metodei și o rulare de suită pot avea același prefix |
| Limită existentă | `ReportEnvironmentField.SUITE` este ascuns intenționat din System operațional; informația lansării aparține metadatelor secundare Tests/trace |
| Verificare controlată 2026-11-20 | `AutoExE2eOverviewNarrativeComponentTest` a trecut două scenarii fixture UI/UI_API în scope de suită și a verificat expandarea raportului; nu verifică suita canonică rădăcină sau selecția efectivă |

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
