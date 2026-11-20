# Căutarea AutoEx — răspuns deținut și DOM nou

## Domeniu și cauză

J1 deține această corecție a consumatorului. Core C3 și Reporting J18 rămân
neschimbate; responsabilitățile producătorilor de sursă/teste și ale
consumatorilor independenți își păstrează limitele existente.

| Observație | Dovadă și limită |
|---|---|
| Eșec UI inițial păstrat | PNG-ul final arată `This website is under heavy load (queue full)` la `/products?search=Blue%20Top` |
| Starea HTTP inițială | Nu a fost capturată în acea dovadă exclusiv UI; starea nu este dedusă din apeluri reușite ulterioare |
| Handlerul real de căutare | `#submit_search` setează `window.location='/products?search='+search`; rezultatul este un document HTML GET în cadrul principal |
| Verificare HTML ulterioară | HTTP 200; 12.976 de caractere; primul produs este Blue Top |
| Verificare ulterioară a catalogului API | `/api/productsList`: HTTP 200, responseCode 200, 34 de produse; Blue Top id 1, Rs. 500 |
| Verificare ulterioară a API-ului de căutare directă | POST `/api/searchProduct`, `search_product=Blue+Top`: HTTP 200, responseCode 200, un rezultat Blue Top |
| Separarea cauzelor | Aceste verificări reușite sunt observații distincte și nu pot certifica documentul de căutare eșuat anterior |

Implementarea anterioară aștepta produsul după confirmarea URL-ului, astfel că
pagina terminală „queue full” consuma tot bugetul locatorului. Fixture-uri locale
independente au mai expus rezultate PASS false pentru HTTP 525 în prezența unui
produs, schimbarea URL-ului numai în istoric cu DOM învechit și un indicator
vizibil de încărcare simultan cu produsul.

## Contractul de execuție păstrat

| Limită | Comportament curent |
|---|---|
| Activare | Un singur pointer nativ; fără reîncercare de test, repetarea pointerului sau cerere GET de recuperare |
| Răspuns | Identitatea cererii din cadrul principal deține metoda, URL-ul și rezultatul HTTP; XHR și cadrele secundare sunt excluse |
| Destinație | Aceeași origine/cale configurată și exact un parametru de căutare configurat, decodificat |
| DOM | `performance.timeOrigin` nou, document pars-at și primul produs configurat vizibil |
| Rezultat terminal | Un răspuns non-2xx sau pagina vizibilă „queue full” rămâne FAIL, cu o cauză explicită |
| Buget | Bugetul existent de 700 ms pentru acțiunea componentei și 20 s pentru acțiunea live; pragurile nu sunt relaxate |
| Dovezi | Dovada răspunsului INFO, verdictul temporizat al clickului, lanțul original al excepțiilor și politica PNG configurată |
| Răspuns absent | `HTTP neobservat` explicit; URL-ul cererii este identificat ca atare, fără a inventa metadate despre răspuns |
| Responsabilitate | `AutoExSearchDocument` gestionează listener-ele pasive prin `UiListenerScope` UI existent; fără modificări în bibliotecile generice |
| Raportare | Aserțiunile colapsabile existente și previzualizarea implicită de 240 de caractere; dovezile complete rămân disponibile |

Corecția „queue full” îmbunătățește clasificarea și auditabilitatea; nu poate
face disponibil un server public indisponibil. Un răspuns respins oprește
parcursul înaintea operațiilor ulterioare de coș, cont sau plată.

## Regresii permanente

| Cazuri | Responsabil |
|---|---|
| produs inițial întârziat, document înlocuit, produs/interogare greșite, un singur click | [contractele existente de pregătire](../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/pages/AutoExSearchReadinessComponentTest.java) |
| HTTP 200/503 cu „queue full”; HTTP 525 cu produsul așteptat | [contractele de răspuns deținute](../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/pages/AutoExSearchResponseComponentTest.java) |
| DOM învechit după schimbare doar în istoric; prioritatea indicatorului vizibil de încărcare; indicator ascuns; GET reușit | aceleași contracte de răspuns |
| API independent sănătos cu document absent/eșuat; eroare de transport | aceleași contracte de răspuns |

Rulează într-o [etapă TMP deținută](ATF_Build_And_Execution_Safety.md), niciodată în mediul live:

```powershell
mvn -B -pl atf-test-auto-ex -am "-Dtest=AutoExSearchReadinessComponentTest,AutoExSearchResponseComponentTest" "-Dsurefire.failIfNoSpecifiedTests=false" test
```

## Registrul verificărilor

| Poartă | Rezultat |
|---|---|
| Implementarea inițială și primele șapte cazuri de răspuns | Cinci eșecuri au expus clasificarea lipsă și rezultatele PASS false; cele trei cazuri inițiale de pregătire trec |
| Prima verificare corectată a răspunsului/DOM-ului | 10 trecute, zero eșecuri/erori/teste sărite, cu bugetele originale |
| Prima verificare extinsă a testelor adiacente | 52 AutoEx și 5 UI trecute; 13 teste în suita de căutare limitată; un parcurs real de coș trecut |
| Observație ulterioară cu fixture UI neutru | Unul dintre cele trei cazuri neschimbate cu pointer a eșuat înainte de activare, în așteptarea stabilității la 698.884001 ms; păstrat separat în [UI-FIRST-PAINT-144](ATF_UI_First_Frame_Verification.md) |
| Suita finală limitată de căutare | 15 trecute, zero eșecuri/erori/teste sărite; cele trei cazuri existente de pregătire și încă douăsprezece cazuri de răspuns, cu un singur raport comun |
| Parcursul final real de coș prin UI | 1 trecut, zero eșecuri/erori/teste sărite; căutare, recalcularea cantității și eliminarea produsului din magazinul public, acțiune 20 s/navigare 45 s neschimbate |
| Diagnosticele finale pentru răspuns absent | 3 trecute: numai istoric, API independent și transport întrerupt; HTTP neobservat explicit, un pointer și numărători independente ale cererilor |
| Artefactul cu runtime controlat | Două scenarii: GET-ul deținut sănătos este PASS; HTTP 200 cu „queue full” este FAIL, cu cauza/PNG-ul original; un singur GET pentru fiecare, fără repetare |
| Randare Chromium | Patru artefacte verificate offline: controlat, UI real, componentă și suită; comparațiile inițial colapsate, aspectul secțiunilor/tabelelor, patru file, legătura directă spre eșec și vizualizatorul PNG trec fără erori JavaScript |
| Revizuire vizuală | Capturi reale din browser inspectate: Click eșuat, dovada INFO HTTP/DOM, PNG-ul „queue full” și timpi separați pentru execuția/publicarea etapelor |
| Guvernanța finală | 42 trecute, zero eșecuri/erori/teste sărite: controale pentru schimbările de teste, lizibilitate, arhitectura consumatorului și suprafețele fizice Core/Reporting |
| Documentația curentă | Toate cele șapte fișiere Markdown modificate trec validatorii actuali ai sursei pentru linkuri/ancore, proză, structură, diagrame și căi dinamice |
| Artefacte randate finale | Patru selecții noi de artefacte trec inspecția Chromium; capturile păstrate în `render-final-captures`, rezultatele în `render-final.log` |
| Reconcilierea mediului live | Unsprezece fișiere protejate au fost promovate; toate cele 372 de intrări relevante sursă/resursă/POM/bibliotecă corespund etapei verificate după commitul live concurent |
| Compilare nouă după sincronizare | Patru cazuri de componentă în browser și guvernanța schimbărilor de teste pentru HEAD-ul curent au trecut din noul instantaneu live, fără eșecuri/erori/teste sărite |

- **Domeniul suitei:** selectorul limitat există numai în TMP și nu este livrat.
  `AutoExComponentSuite` canonică selectează deja ambele clase permanente prin
  pachetul său de componente existent; acceptanța limitată exercită același domeniu.
- **Eșecuri individuale păstrate:** verificarea intermediară cu 54 de cazuri
  adiacente a păstrat o eroare la completarea inputului și un eșec al clickului
  nativ înainte de activare, anterior postcondiției căutării.
  [UI-FIRST-PAINT-144](ATF_UI_First_Frame_Verification.md) le păstrează împreună
  cu observația UI neutră; nu se afirmă un rezultat agregat individual complet
  reușit și nici vreo reîncercare ascunsă a acțiunii/testului.
- **Etichete diagnostice finale:** trei cazuri concentrate verifică formularea
  `HTTP neobservat` și URL-ul cererii după suita de 15 cazuri și execuția live;
  acțiunea, ruta, DOM-ul și termenele-limită rămân neschimbate.

## Continuare — pregătirea inițială a viewport-ului

Verificarea ulterioară [UI-FIRST-PAINT-144](ATF_UI_First_Frame_Verification.md)
rezolvă limita adiacentă a fixture-ului inițial și corectează suprascrierea
tăcută a versiunii Node de către executorul de validare. În mediul implicit al
SDK-ului, toate cele cincisprezece cazuri AutoEx trec individual și în suită, cu
limitele originale.

Documentul inițial folosește acum un singur termen-limită de pregătire de 5 s
pentru încărcare, atașarea nativă și captura PNG. Observarea răspunsului/DOM-ului
deținută de căutare păstrează termenul-limită existent al acțiunii, numărul
cererilor și verificările negative independente; această continuare nu rescrie
rezultatele istorice de mai sus.

<details>
<summary><strong>Locația execuției și rezultatele păstrate</strong></summary>

Toate compilările și artefactele aparțin spațiului de lucru TMP deținut
`ATF_AUTOEX_SEARCH_ACTIVE_DO_NOT_CLEAN_WYOHyf`; the captured J1 review base is
`94b88a3e4d3f9aa8ba300e16e99409d5b0da28ac`.

Mediul live a avansat ulterior la `f2aaa41e1f0939b93ccdab509492f862c09d18cc`.
Un instantaneu nou `post-sync/atf_j1` păstrează acea stare Git independentă;
baseline-ul tehnic adaugă un control separat, fără rebazarea înregistrărilor vechi.

| Dovadă | Element păstrat |
|---|---|
| Eșecuri inițiale independente | `search-component-baseline-atf_j1.log` |
| Prima verificare completă, cu teste adiacente, reușită | `search-regression-fixed-oracle-atf_j1.log` |
| Suita finală / parcurs public | `search-suite-owner-final-atf_j1.log` / `search-live-owner-final-atf_j1.log` |
| Etichetă pentru răspuns absent | `search-unobserved-atf_j1.log` |
| Eșecuri de pregătire păstrate vizibile | `search-regression-final-atf_j1.log` / `search-regression-owner-final-atf_j1.log` |
| Audit real în browser | `render.log` și `render-captures` |
| Artefact controlat | [un scenariu sănătos și unul „queue full”](http://127.0.0.1:55726/autoex-search-controlled.html#test-view) |
| Artefact UI real | [parcurs public de coș](http://127.0.0.1:55726/autoex-search-live.html#test-view) |
| Promovare | `delivery-plan.json` și manifestul de protecție Core; numai lista permisă de surse/documente, biblioteci neschimbate |
| Instantaneu live nou | `post-sync-snapshot.json` și `search-post-sync-atf_j1.log`; controlul Git curent este separat de revizuirea inițială |

URL-urile locale de revizuire necesită serverul păstrat; indisponibilitatea lor
ulterioară nu șterge HTML-ul cu marcaj temporal și nu îl transformă într-o execuție nouă.

</details>

Artefactul eșecului inițial rămâne imuabil. Rezultatele, jurnalele și capturile
noi se păstrează în afara mediului live; un parcurs reușit ulterior nu îl rescrie.

[Owner contract](../atf-test-auto-ex/README.md#confirmarea-căutării-în-catalog)
and [current feedback](feedback/ATF_Consumer_Business_Test_Focus_Feedback.md)
track this consumer increment separately from library acceptance.
