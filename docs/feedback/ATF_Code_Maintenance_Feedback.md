# Feedback pentru mentenanța codului

<a id="gov-package-cohesion-2026-11-19"></a>
## GOV-PACKAGE-COHESION-2026-11-19 — remedierea zonelor cu probleme de coeziune

**Stare: Verificat la 2026-11-20. Prioritate: 74/100. Responsabili:** guvernanța `atf-tests`, împreună cu responsabilii pachetelor `atf-ui`.

Rularea `WholeAtfGovernanceSuiteTest` din 2026-11-19 a avut 163 de teste: 161 au trecut, iar două verificări de coeziune a pachetelor au eșuat. Constatarea este rezolvată prin mutarea verificării documentației într-un pachet responsabil și printr-o excepție revizuită pentru limita stabilă Playwright. Rularea completă ATF din 2026-11-20 a trecut cu 171/171 în ambele ediții documentare.

| Zonă istorică | Rezolvare | Control curent |
|---|---|---|
| `atf.api.tests.governance.documentation` avea 9 clase de test față de limita 8 | `DocumentationReadmeStructureGovernanceTest` a fost mutat în `atf.api.tests.governance.documentation.structure` | Limita generală de opt clase și mutanții negativi rămân activi |
| `atf.ui.playwright` avea 9 clase de producție și 10 clase de test față de limita 8 | Punctele publice de intrare Playwright, responsabilii acțiunilor, sesiunea și înregistratorul de dovezi au rămas împreună printr-o excepție revizuită | Reevaluați dacă apar tipuri fără legătură sau limita devine înlocuibilă independent; eliminați excepția dacă separarea nu necesită API nou între pachete |

Excepția descrie limita comportamentală reală; nu suspendă guvernanța coeziunii pentru alte pachete. Verificarea raportului Extent în browser rămâne clasificată în pachetul E2E al consumerului.

```text
mvn -B -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=5773a1567e14b570b17608b25058158bb2b1c114" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test
```

Suita a rulat din layout-ul canonic de staging TMP, versionat cu Git. Suita completă `AutoExE2eSuite` nu a făcut parte din această dovadă de guvernanță și rămâne urmărită separat acolo unde este necesară dovada artifactelor la nivel de suită.