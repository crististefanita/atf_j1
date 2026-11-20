# Feedback pentru mentenanța codului

<a id="gov-package-cohesion-2026-11-19"></a>
## GOV-PACKAGE-COHESION-2026-11-19 — remedierea zonelor cu probleme de coeziune

**Stare: Verificat la 2026-11-20; responsabili:** guvernanța `atf-tests` și responsabilii pachetelor `atf-ui`.

Rularea suitei din 2026-11-19 a consemnat 163 de teste: 161 au trecut, iar două verificări de coeziune a pachetelor au eșuat. Remedierea a mutat verificarea documentației în pachetul responsabil și a păstrat o excepție revizuită pentru limita Playwright stabilă.

La 2026-11-20, rerulările anterioare au trecut cu 177/177 și 179/179. Cea mai recentă a trecut cu 186/186 în ambele ediții după adăugarea guvernanței README-ului root și a unui control explicit pentru porțile obligatorii de calitate a codului, fără eșecuri, erori sau teste omise.

| Zonă istorică | Rezolvare | Control curent |
|---|---|---|
| `atf.api.tests.governance.documentation` avea 9 clase de test față de limita 8 | `DocumentationReadmeStructureGovernanceTest` a fost mutat în `atf.api.tests.governance.documentation.structure` | Limita generală de opt clase și mutanții negativi rămân activi |
| `atf.ui.playwright` avea 9 clase de producție și 10 clase de test față de limita 8 | Punctele publice de intrare Playwright, responsabilii acțiunilor, sesiunea și înregistratorul de dovezi au rămas împreună printr-o excepție revizuită | Reevaluați dacă apar tipuri fără legătură sau limita devine înlocuibilă independent; eliminați excepția dacă separarea nu necesită API nou între pachete |

Excepția descrie limita comportamentală reală; nu suspendă guvernanța coeziunii pentru alte pachete. Verificarea raportului Extent în browser rămâne clasificată în pachetul E2E al consumerului.

Verificarea din 2026-11-20 a rulat `WholeAtfGovernanceSuiteTest` din layout-ul canonic de staging TMP, versionat cu Git, folosind baza curentă a fiecărei ediții:

- `main` (`4ec3e14b357e0968776399477592fd13c3713631`): `mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=4ec3e14b357e0968776399477592fd13c3713631" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test`
- `main_RO` (`754eb315045e565c150ca819498cf63154bee9b4`): `mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=754eb315045e565c150ca819498cf63154bee9b4" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test`

Cea mai recentă executare a trecut cu 186/186 în fiecare ediție, folosind comenzile de mai sus.

Suita completă `AutoExE2eSuite` nu a făcut parte din această dovadă de guvernanță și rămâne urmărită separat acolo unde este necesară dovada artifactelor la nivel de suită.
