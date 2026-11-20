# Primul cadru UI — pregătire, identitate runtime și input nativ

## Domeniu

J1 deține helper-ul UI și integrarea fixture-urilor AutoEx. Core C3 și Reporting
J18 își păstrează perechile fizice existente; acest increment nu schimbă
acțiunile business, asset-urile de raportare sau preview-ul implicit de 240 de
caractere.

| Observație | Semnificație și limită |
|---|---|
| Loguri UI-FIRST-PAINT-144 păstrate | Fill/Click nativ au expirat la 700 ms înaintea postcondiției; planificarea istorică exactă host/GPU nu poate fi reconstituită |
| Lucru controlat al rendererului după LOAD | O inițializare de 1200 ms, numai în fixture, reproduce ambele refuzuri fără evenimente trusted |
| Comparație cu viewport pregătit | Aceeași limită de input; click/fill native se termină în 187/57 ms după pregătirea explicită a viewport-ului |
| Oracol PNG nativ | Pixeli lime în documentul curent după inițializarea controlată, dimensiunea viewport-ului așteptată la 70/100% și înlocuiri noi roșii/albastre |
| Oracol de acționabilitate | Țintele dezactivate, acoperite, în mișcare și readonly rămân refuzate; pregătirea nu schimbă ținta și nu expediază input înainte de acțiune |
| Discrepanță în runnerul de validare | Runnerul extern a forțat în tăcere Codex Node 24.22.1; driverul Playwright Java 1.49.0 publicat conține Node 22.11.0 |
| Runtime A/B, același candidat și heap de 384 MiB | Node-ul agentului forțat: 34/39; implicitul SDK: 39/39; fără schimbare de deadline, aserțiune sau sursă între execuții |
| Interpretare | Restabilește mediul real al SDK-ului pentru acceptanță; comparația nu afirmă universal că orice instalare Node 24 este incompatibilă |

Corecția sursei și cea a runnerului tratează limite separate. Evenimentul LOAD
al unui document nu dovedește randarea viewport-ului; suprascrierea mediului de
validare nu dovedește că driverul implicit al IDE-ului se comportă la fel.

## Contractul execuției

| Etapă | Owner | Limită |
|---|---|---|
| Încarcă sau înlocuiește documentul fixture | `PlaywrightPagePreparation` | deadline-ul de navigare existent pornește o singură dată |
| Atașează rădăcina nativă și capturează PNG-ul viewport-ului | același scope de pregătire | folosește deadline-ul rămas; animațiile rămân permise |
| Returnează o recipisă | același scope | captura întârziată sau eșuată nu autorizează inputul; octeții PNG sunt copii defensive |
| Fill/Click și observația declarată | recorderul nativ existent pentru acțiuni | limita fixture-ului rămâne 700 ms; o activare trusted, fără replay |
| Document produs de căutare | runtime-ul AutoEx | păstrează deadline-ul comun original al acțiunii; pregătirea inițială nu adaugă timp |

Pregătirea este explicită și nu inspectează o țintă business. Returnează
pixelii și URL-ul curente, fără un PASS inventat în recorder; vizibilitatea,
stabilitatea, hit testing-ul, starea readonly și postcondițiile native rămân
independente.

## Teste permanente

| Responsabil | Oracol independent |
|---|---|
| [Teste pentru bugetul temporizării pregătirii](../atf-ui/src/test/java/atf/ui/playwright/PlaywrightPagePreparationTest.java) | un singur buget pentru încărcare/atașare/captură, expirare exactă, identitatea erorii originale și octeți defensivi |
| [Teste de browser pentru pregătire](../atf-ui/src/test/java/atf/ui/playwright/PlaywrightPagePreparationComponentTest.java) | pixeli PNG reali, inițializare la rece, evenimente autentice, mod de document neschimbat și ținte refuzate |
| [Teste pentru postcondițiile native](../atf-ui/src/test/java/atf/ui/playwright/PlaywrightClickPostconditionComponentTest.java) | un singur pointer, observație falsă, țintă absentă/ambiguă și răspuns copil realmente neconfirmat |
| [Teste existente pentru temporizarea acțiunii](../atf-ui/src/test/java/atf/ui/playwright/ObservationBudgetComponentTest.java) | pointerul și confirmările succesive nu reînnoiesc termenul-limită măsurat |
| [Pregătire AutoEx](../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/pages/AutoExSearchReadinessComponentTest.java) | URL/PNG fixture, produs întârziat, rută/produs greșit și înlocuirea repetată a documentului |
| [Contracte de răspuns AutoEx](../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/pages/AutoExSearchResponseComponentTest.java) | răspuns deținut, rezultate terminale, răspuns absent, o cerere GET și un click autentic |

Rulează numai într-un [staging TMP deținut](ATF_Build_And_Execution_Safety.md):

```powershell
mvn -B -pl atf-ui -am "-Dtest=PlaywrightPagePreparationTest,PlaywrightPagePreparationComponentTest,PlaywrightClickPostconditionComponentTest,ObservationBudgetComponentTest" "-Dsurefire.failIfNoSpecifiedTests=false" test
```

## Starea verificării

| Poartă | Rezultat observat |
|---|---|
| Gate UI nativ individual inițial | 24 trecute, zero eșecuri/erori/teste sărite |
| Poartă AutoEx individuală inițială cu Node-ul agentului impus | 12 trecute, trei eșecuri la activare/observarea transportului; păstrată ca dovadă despre mediu |
| Suite limitate cu aceeași sursă și Node-ul agentului forțat | 34/39; cinci eșecuri, fără teste sărite sau erori de container |
| Suite limitate cu aceeași sursă și Node-ul SDK-ului | 39/39; fără teste sărite sau erori de container, limitele de 700 ms/5 s și heap-ul neschimbate |
| Rulări individuale finale, cleanup consolidat pentru evenimente/cadru trusted | UI 24/24 și AutoEx 15/15; fără eșecuri/erori/teste sărite |
| Suita finală, ultima versiune a sursei | 39/39, cincisprezece containere reușite; fără eșecuri/teste sărite |
| Guvernanță adiacentă | 42/42: schimbări de teste, lizibilitate, arhitectura consumerului și suprafețele fizice Core/Reporting |
| Urmărirea controlului verificat | Guvernanța curentă a schimbărilor de teste trece după mutarea UI-FIRST-PAINT-144 în baseline |
| Auditul artifactelor Chromium | Patru artefacte noi: UI controlat, ambele clase AutoEx individuale și raportul comun al suitei; fără erori JavaScript |
| Acceptare vizuală | Capturi reale din browser inspectate: tabelele de pregătire/acțiuni, PNG-ul refuzului nativ și comparațiile individuale/de suită |

Suita cu setările implicite ale SDK-ului a produs un artefact AutoEx comun nou
pentru cele cincisprezece cazuri consumatoare. Testele unitare rămân o categorie
separată; contractelor native ale bibliotecii UI nu li se atribuie semantica de
raportare E2E a consumatorului.

<details>
<summary><strong>Proveniența compilării și a diagnosticului păstrată</strong></summary>

Workspace TMP deținut: `ATF_UI_FIRST_PAINT_ACTIVE_DO_NOT_CLEAN_3182723c`.
Baza de revizuire J1 capturată: `f2aaa41e1f0939b93ccdab509492f862c09d18cc`.

| Dovadă | Element păstrat |
|---|---|
| Observații native la pornire | `observations/all.json` și fișierele JSON/PNG native pentru fiecare caz |
| Porți inițiale pentru sursă | `ui-preparation-first-atf_j1.log`, `ui-preparation-individual-atf_j1.log` |
| Suită cu runtime-ul agentului | `suites.log`, `suite-codex-node24-metrics.json`, `suite-gc.log` |
| Suită cu runtime-ul SDK-ului | `suites-sdk.log`, `suite-sdk-metrics.json`, `suite-sdk-gc.log` |
| Poartă individuală finală | `ui-preparation-sdk-individual-atf_j1.log` |
| Suită finală | `suites-final.log`, `suite-final-metrics.json`, `suite-final-gc.log` |
| Guvernanță | `ui-preparation-governance-atf_j1.log`; urmărirea controlului verificat este păstrată separat |
| Randare | `render-audit.log` și `render-captures`; căi precise pentru extinderea secțiunilor, fără extinderea globală a tuturor |
| UI controlat | [un caz sănătos și un refuz nativ intenționat](http://127.0.0.1:52278/first-frame-ui.html#test-view) |
| AutoEx individual | [readiness](http://127.0.0.1:52278/first-frame-readiness.html#test-view), [răspunsuri deținute](http://127.0.0.1:52278/first-frame-response.html#test-view) |
| Suită AutoEx | [un artifact comun pentru cincisprezece cazuri](http://127.0.0.1:52278/first-frame-suite.html#test-view) |

URL-urile de revizuire necesită serverul local păstrat; HTML-ul cu marcaj temporal
rămâne în spațiul de lucru TMP deținut. Chromium și capturile inspectate folosesc
alternativa aprobată de utilizator la TAB MCP, care nu este disponibilă.

Capturile browserului și jurnalele complete ale eșecurilor rămân în afara
spațiului de lucru activ. O execuție reușită ulterioară nu rescrie artefactul
anterior sau cauza sa inițială.

</details>

Contractul UI al responsabilului din [README-ul UI](../atf-ui/README.md#pregătirea-explicită-a-fixture-ului)
definește limita execuției.

## Urmărirea capturilor cu context complet

Capturile păstrează viewport-ul complet al paginii și contextul înconjurător la
scara configurată; journey-urile Shop/AutoEx folosesc 70%. Android păstrează
întregul ecran, iar vizualizatorul raportului încadrează imaginea-sursă completă,
cu toate marginile și proporțiile originale.

| Poartă | Stare |
|---|---|
| Auditul căii de captură | Shop/AutoEx folosesc captură de pagină fără decupaj regional; Android folosește ADB pentru capturarea întregului ecran |
| Oracol permanent de pixeli | Ambele cazuri 70/100% trec, cu fiecare colț și context suplimentar din partea de jos a paginii la 70% |
| Contractul existent pentru întregul document | 1/1 trece; rezultatul explicit al întregii pagini păstrează înălțimea documentului |
| Verificare pentru consumatori | AutoEx 2/2 și Shop 2/2 trec; configurație, geometrie nativă și dimensiuni PNG complete |
| Guvernanță | 8/8 trec; urmărirea controlului verificat este înregistrată separat |
| Randare Chromium | Trei artefacte noi trec, fără erori JavaScript; comparațiile, filele, timpii, legătura către eșec și încadrarea imaginii complete sunt verificate |
| Revizuire vizuală | Capturile reale ale vizualizatorului în starea reușită/refuzată și verificările Shop sunt inspectate; toate cele patru margini ale cadrului rămân vizibile |
| Livrare | Increment numai pentru teste și documentație; octeții Core C3 și Reporting J18 își păstrează proveniența existentă |

<details>
<summary><strong>Proveniența urmăririi</strong></summary>

Workspace TMP deținut: `ATF_SCREEN_CONTEXT_ACTIVE_DO_NOT_CLEAN_838c4418`.
Baza Git J1 capturată rămâne `f2aaa41e1f0939b93ccdab509492f862c09d18cc`;
dovezile anterioare UI-FIRST-PAINT-144 de mai sus se păstrează neschimbate.

| Dovadă | Element păstrat |
|---|---|
| Componente și guvernanță | `screen-components.log`, `screen-governance.log`, `screen-context-atf_j1.status.json` |
| Artifacte cu pixeli și vizualizator | `controlled-report-final`, `render-audit.log`, `render-captures` |
| Raport UI cu tot contextul | [Viewport complet la 70% și refuz nativ intenționat](http://127.0.0.1:64792/screen-context.html#test-view) |
| Artifacte consumer | [Geometrie AutoEx](http://127.0.0.1:64792/screen-context-autoex.html#test-view), [geometrie Shop](http://127.0.0.1:64792/screen-context-shop.html#test-view) |
| Corecția păstrată a probei TMP | Proba externă inițială compara DPR-ul nativ `0.699999988079071` ca șir zecimal; proba finală folosește precizia independentă existentă a geometriei `1e-6`, fără schimbarea pragului din sursă sau a deadline-ului |

Linkurile de revizuire necesită serverul local păstrat. Chromium și capturile
inspectate folosesc alternativa aprobată la TAB MCP, indisponibil; captura
Android a fost inspectată în sursă, fără a pretinde o execuție nouă pe dispozitiv.

</details>
