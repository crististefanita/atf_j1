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

### Ultima reverificare — 2026-11-20

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
