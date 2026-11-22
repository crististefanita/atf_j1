# Feedback pentru mentenanța codului

<a id="gov-package-cohesion-2026-11-19"></a>
## GOV-PACKAGE-COHESION-2026-11-19 — remedierea zonelor cu probleme de coeziune

**Stare: Verificat la 2026-11-20; responsabili:** guvernanța `atf-tests` și responsabilii pachetelor `atf-ui`.

Rularea suitei din 2026-11-19 a consemnat 163 de teste: 161 au trecut, iar două verificări de coeziune a pachetelor au eșuat. Remedierea a mutat verificarea documentației în pachetul responsabil și a păstrat o excepție revizuită pentru limita Playwright stabilă.

La 2026-11-20, rerulările anterioare au trecut cu 177/177 și 179/179. Rularea 186/186 a adăugat guvernanța README-ului root și un control explicit pentru porțile obligatorii de calitate a codului. Este păstrată ca dovadă istorică; reverificarea ulterioară a guvernanței artefactelor de context este consemnată mai jos.

| Zonă istorică | Rezolvare | Control curent |
|---|---|---|
| `atf.api.tests.governance.documentation` avea 9 clase de test față de limita 8 | `DocumentationReadmeStructureGovernanceTest` a fost mutat în `atf.api.tests.governance.documentation.structure` | Limita generală de opt clase și mutanții negativi rămân activi |
| `atf.ui.playwright` avea 9 clase de producție și 10 clase de test față de limita 8 | Punctele publice de intrare Playwright, responsabilii acțiunilor, sesiunea și înregistratorul de dovezi au rămas împreună printr-o excepție revizuită | Reevaluați dacă apar tipuri fără legătură sau limita devine înlocuibilă independent; eliminați excepția dacă separarea nu necesită API nou între pachete |

Excepția descrie limita comportamentală reală; nu suspendă guvernanța coeziunii pentru alte pachete. Verificarea raportului Extent în browser rămâne clasificată în pachetul E2E al consumerului.

Verificarea inițială din 2026-11-20 a rulat `WholeAtfGovernanceSuiteTest` din layout-ul canonic de staging TMP, versionat cu Git, folosind bazele de review utilizate atunci:

- `main` (`4ec3e14b357e0968776399477592fd13c3713631`): `mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=4ec3e14b357e0968776399477592fd13c3713631" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test`
- `main_RO` (`754eb315045e565c150ca819498cf63154bee9b4`): `mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=754eb315045e565c150ca819498cf63154bee9b4" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test`

Aceste execuții au trecut cu 186/186 în fiecare ediție și sunt istorice; comenzile și bazele de mai sus păstrează dovada inițială.

### Reverificarea commit-urilor anterioare — 2026-11-20

După curățarea artefactelor de context și actualizarea guvernanței, suita
completă `WholeAtfGovernanceSuiteTest` a trecut cu 190/190 în ambele ediții
live, fără eșecuri, erori sau teste omise:

- `main`: `e1504f69fdbfefe2670236024cb93f92b9f69ac5`
- `main_RO`: `3520c2cae2481d1eba6d05c54559259ebd52c4e7`

- Testele Node pentru edițiile documentației au trecut cu 10/10; comparația din
  TMP, pe worktree-uri Git curate, raportează 0 probleme în cele 52 de
  documente. Comparația worktree-urilor live a semnalat două fișiere `.mjs`, dar
  ID-urile blob Git coincid și textul normalizat este identic; vezi
  [feedback-ul documentației](ATF_Documentation_Feedback.md#doc-edition-comparator-20261120)
  pentru discrepanța de terminatoare a worktree-urilor.
- Dovada de guvernanță nu include suita completă `AutoExE2eSuite`; dovezile
  consumerului rămân urmărite separat acolo unde sunt necesare artefacte la nivel de suită.
- După editarea feedback-ului, guvernanța focusată a documentației a trecut
  18/18 în ambele ediții la 2026-11-21.

### Verificări focusate ale consumerilor — 2026-11-21

Worktree-uri EN și RO proaspete, bazate pe reviziile de control
(`72c387e98735b03fe1afd65cab1913b76b1d86b4` și
`07a1fac012bbdab02916a2ef6d6cd8a735b3d3c0`), au trecut verificările focusate:

- `ShopBillingSynchronizationComponentTest`: 13/13.
- `ShopBackendRecoveryComponentTest`: 6/6; `AutoExBackendRecoveryComponentTest`: 5/5.

### Guvernanța anterioară a întregului reactor, în commit-uri — 2026-11-21

După corectarea documentației despre raportarea recovery, `WholeAtfGovernanceSuiteTest`
a trecut cu 190/190, fără eșecuri, erori sau omisiuni, la aceste commit-uri:

- `main`: `94434f1458171ada958e83f3577f9d6a9f776203`.
- `main_RO`: `06709dde637f3a08929932d6bee8130bc1eefed8`.

Controalele schimbărilor de teste au folosit bazele de review focusat de mai sus.
Testele Node ale edițiilor au trecut apoi cu 10/10 în ambele ediții; comparația
perechii în TMP cu Git curat a găsit 0 probleme în toate cele 50 de documente
Markdown păstrate.

Aceste rezultate acoperă fixture-uri locale și probe de randare a raportului.
Constatarea coșului public AutoEx rămâne deschisă; nu se deduce o rulare E2E pe
site-ul public.

### Verificarea curentă a feedback-ului — 2026-11-21

După actualizarea feedback-ului pereche:

- `WholeAtfGovernanceSuiteTest` a trecut cu 191/191 în ambele worktree-uri de
  review, fără eșecuri, erori sau teste omise. Bazele au fost `main`
  `225d58abe5f4d9859ab02bc2935e5ae44fa03850` și `main_RO`
  `bb824283d7c5c83c3ca9fe05c94fb800df57aa8c`.
- După adăugarea linkurilor directe către cod, guvernanța focusată de proză,
  ancore, ținte și linkuri a trecut cu 10/10 în fiecare ediție.
- Testele Node ale edițiilor documentației au trecut cu 11/11; comparația Git a
  perechii a găsit 0 probleme în toate cele 50 de documente Markdown păstrate.

<a id="oop-recovery-scenarios-2026-11-21"></a>
## OOP-RECOVERY-SCENARIOS-2026-11-21 — scenarii component explicite și proprietatea raportului

**Stare: Închis la 2026-11-21; responsabili:** responsabilii testelor component AutoEx și Shop.

Testele componente de recovery transmiteau argumente primitive poziționale pentru
statusul răspunsului, politica de captură, starea de pregătire așteptată, profilul
de validare și randarea raportului. Acum transmit un record numit
`RecoveryScenario`, cu enumerări pentru rezultat și fabrici pentru fiecare
scenariu. Cazurile parametrizate de validare folosesc surse bazate pe enum, astfel
încât intrările rămân tipizate de la declarație până la execuție.

Selectorii paginii de raport AutoEx și verificările de expandare sunt acum în
`AutoExRecoveryExtentReportProbe`, cu vizibilitate limitată la teste, alături de
fațada de raport Shop existentă. `ConsumerModuleArchitectureGovernanceTest` impune
această limită de proprietate. Modificarea privește doar organizarea testelor;
comportamentul runtime al consumerilor și contractele de raportare nu s-au schimbat.

Verificarea controlată a trecut pe starea sursei înregistrată în aceste commituri:

| Ediție | Commit sursă | Test component recovery Shop | Test component recovery AutoEx | Guvernanță Whole-ATF |
|---|---|---:|---:|---:|
| EN | `9f55590530cef94999d4ca3cd34e916bbca5f440` | 6/6 | 5/5 | 191/191 |
| RO | `0716679d7f2809b21ae8d395d623fc20d1249871` | 6/6 | 5/5 | 191/191 |

- `ConsumerModuleArchitectureGovernanceTest` a trecut 14/14 în fiecare ediție,
  inclusiv noua verificare a proprietății selectorilor.
- Controlul protejat al schimbărilor de teste urmărește testul component AutoEx,
  fațada raportului și verificarea de arhitectură.
- Sursa testului component Shop rămâne în afara setului deja stabilit de căi
  protejate. Rezultatul său de 6/6 este raportat separat, fără a-l prezenta ca
  protejat de baseline.

Prima rulare de guvernanță după actualizare a semnalat paragraful de patru
propoziții din această înregistrare, peste limita pentru proza ușor de scanat.
Textul este acum împărțit în puncte. Rerularea finală a trecut 191/191 în ambele
ediții, fără eșecuri, erori sau teste omise.

<a id="j1-recovery-trace-typing-2026-11-21"></a>
## J1-RECOVERY-TRACE-TYPING-2026-11-21 — păstrarea dovezii recovery tipizate până la proiecția raportului

**Stare: Verificată și închisă la 2026-11-22; responsabili:** responsabilii
testelor component Shop și AutoEx.

- `ShopRecoveryReportObservation` și `AutoExRecoveryReportObservation`
  proiectează `UiRecoveryTrace` deținut de consumer în fiecare raport component
  JUnit real. `Operation` este `catalog-readiness`, iar `Event` este
  `Backend observation #N`.
- Proiecțiile aliniază numărul și ordinea acțiunilor cu trace-ul, ținta semantică
  și nivelul INFO. Decizia, motivul, semnalul și timpii probe/backoff/total vin
  din trace-ul tipizat; textul din `observed()` nu mai este parsată pentru timpi.
- Testele ambilor consumatori verifică rezultatele recovered și exhausted,
  câmpurile ordonate, redactarea și ținta tabelului sintetic de acțiuni. Testele
  Playwright opt-in verifică ambele rezultate în rapoartele JUnit Maven reale,
  extind rezultatul component, `TEST BODY` și aserțiunile și verifică independent
  disclosure-ul colapsat după `Expand all nodes`.
- `ConsumerModuleArchitectureGovernanceTest` păstrează proiecția și selectorii
  raportului în consumerul proprietar. `atf-ui` și API-ul Reporting publicat nu
  s-au modificat.
- Paritatea directorului de output IntelliJ este urmărită separat în
  [J1 recovery IDE report parity](ATF_Reporting_Feedback.md#j1-recovery-ide-report-parity-2026-11-22).
