# Verificarea fluxurilor funcționale ale consumatorului — stare curentă

## Căutarea AutoEx — contract livrat

Contractul de căutare și regresiile permanente sunt în [README-ul consumerului AutoEx](../../atf-test-auto-ex/README.md).

<a id="autoex-catalog-evidence-146"></a>
## AUTOEX-CATALOG-EVIDENCE-146 — asocierea dovezii comparației cu schimbul observat

**Stare: verificat și închis la 2026-11-20.** Artefactul suitei controlate
dovedește acum ownership-ul comparației și expandarea în browser în același raport.

Modificarea sursei și verificările țintite pentru raportul de componentă și
browserul local trec.

- Raportul valid atribuie comparația o singură dată GET-ului selectat, deși există
  un POST cu aceeași cale și mai multe GET-uri.
- Dovezile absente sau cu secvență duplicată produc o aserțiune API în FAIL, fără
  să atașeze comparația business.
- Verificarea în browser confirmă expandarea grupului de aserțiuni și a detaliilor
  cererii în raportul Extent randat.
- Dovezile de componentă și browser local sunt înregistrate mai jos; artefactul
  controlat cu scope de suită închide criteriul rămas.

- [x] Rulați captura controlată și inspectați raportul generat pentru a demonstra
      că comparația apare numai sub schimbul observat și selectat.
- [x] Rulați regresia de corelare după metodă, cale și secvența capturată;
      confirmați că o captură absentă sau ambiguă eșuează și că verificările nu se
      dublează între potrivirile de cale.
- [x] Păstrați comportamentul în suportul principal AutoEx și în testele
      consumerului; contractele generice de renderer și ordinal aparțin bibliotecii
      Reporting, iar J1 le verifică prin fixture-uri locale și rapoarte randate.
- [x] Rulați verificările de componentă și de prezentare în browserul local;
      inspectați raportul generat pentru un singur responsabil al comparației,
      previzualizarea de 240, niciun schimb sintetic și dovezi API/cerere
      expandabile.
- [x] Inspectați artefactul Extent al suitei dintr-o rulare controlată și
      confirmați că păstrează același responsabil al comparației și aceeași ierarhie
      expandabilă.

**Dovezi curente**

- La 2026-11-20, cele două cazuri selectate de corelare din `AutoExRuntimeBoundaryComponentTest` au trecut 2/2, fără eșecuri sau erori, în ambele ediții TMP canonice.
- Rularea a generat rapoarte HTML pentru captură validă, lipsă și ambiguă în `atf-test-auto-ex\artifacts\reports\j1-extent-api-20261120`, în fiecare checkout de staging.
- `AutoExCatalogReportPresentationE2eTest` a trecut 1/1 în fiecare ediție; `PlaywrightPagePreparationTest` a trecut 7/7.
- Testul din browser a expandat scenariul, faza testului, pasul API al catalogului, validarea API, GET-ul selectat și grupul de aserțiuni API; a verificat că aserțiunea pornește colapsată, devine vizibilă după expandare și că detaliile requestului se deschid.
- Aserțiunile componente au confirmat un singur owner al comparației pe GET-ul selectat, patru schimburi capturate, lipsa unui al cincilea schimb sintetic și limita de preview de 240 de caractere.
- La 2026-11-20, `AutoExE2eOverviewNarrativeComponentTest` a trecut 1/1, fără
  eșecuri, erori sau skip-uri, în ambele staging-uri TMP `main` și `main_RO`.
  Lansarea JUnit cu scope explicit de suită a rulat un fixture UI și unul UI_API
  împotriva unui magazin loopback și a generat un singur artefact Extent pentru suită.
- Playwright a deschis artefactul generat și a expandat scenariul UI/API,
  `TEST BODY`, checkpoint-ul terminal, `API validation`, unicul
  `GET /api/productsList` observat și grupul său `API assertions`. A verificat că
  comparația pornește colapsată, devine vizibilă după expandare și rămâne sub
  GET-ul selectat; GET-ul separat al documentului nu primește comparația copiată,
  iar detaliile requestului se expandează.
- Această probă de suită controlată închide criteriul înregistrat. Suita canonică
  `AutoExE2eSuite` cu site-ul public nu a rulat, iar testul offline nu este
  prezentat drept acceptanță pe site-ul live.

<a id="shop-billing-sanity"></a>
## SHOP-BILLING-SANITY — atribuirea latenței cursei de lookup

**Stare: verificată și închisă la 2026-11-21.** Intervalul de stabilizare al lookup-ului este acum măsurat separat de pornirea browserului și navigarea fixture-ului; rulările locale repetate limitează traseul race în deadline-ul existent pentru observare.

- În timpul adoptării Console/Mock, `ShopBillingSynchronizationComponentTest` a avut două eșecuri de aserțiune și o eroare în treisprezece invocări.
- Invocarea inițială cu cursă a durat 122 de secunde. O urmă de thread a arătat verificări Playwright imbricate de disponibilitate/`isEnabled` în timpul selectării lookup-ului poștal; acesta este un indiciu, nu o cauză rădăcină confirmată.

**Rerulări focalizate**

- Trei execuții focalizate ale `ShopBillingSynchronizationComponentTest` au trecut fiecare 13/13 (39 de invocări), fără eșecuri sau erori.
- Reverificarea din 2026-11-21 a trecut, de asemenea, 13/13 în ambele worktree-uri EN și RO proaspete, fără eșecuri sau erori.
- Un oracle nou măsoară acțiunea `Await visible` după `inputsApplied`; stabilizarea cu succes trebuie să rămână în bugetul configurat de 1.100 ms. Refuzurile așteptate prin timeout permit cel mult 25 ms pentru încheierea Playwright/înregistrării, fără modificarea timeout-ului configurat.
- Două rulări măsurate `race,true,true` s-au stabilizat în 524 ms și 494 ms. Pornirea browserului și navigarea fixture-ului au măsurat separat 11.904 ms și 9.233 ms. **Inferență:** setup-ul domină invocarea race recentă, care a durat mai mult; măsurătorile acestea nu stabilesc cauza evenimentului istoric de 122 de secunde.
- Fixture-ul local păstrează debounce-ul de 300 ms și aplicarea întârziată de 500 ms pentru `race`. Verificările ownership-ului requestului, completării, valorilor finale și lipsei reintroducerii textului rămân active.

**Protecție de regresie păstrată**

- Păstrați aserțiunea temporală împreună cu oracolele pentru ownership-ul requesturilor, completare, adresă și lipsa reintroducerii textului. Nu măriți timeout-urile și nu adăugați încercări repetate pentru a ascunde latența.

Rularea Shop extinsă anterioară și evenimentul istoric de 122 de secunde nu s-au reprodus și nu sunt prezentate ca explicate. Dovezile actuale închid numai criteriul de stabilizare măsurată; nu stabilesc acceptanța suitei extinse.

<a id="autoex-ui-cart-confirmation-2026-11-19"></a>

## AUTOEX-UI-CART-CONFIRMATION-2026-11-19 — reverificarea confirmării publice de adăugare în coș

**Stare: observat o singură dată; cauza rădăcină și prioritatea rămân neevaluate până la reproducere.**

- Testul opt-in `AutoExPurchaseUiE2eTest` a ajuns la Automation Exercise, a
  căutat `Blue Top` și a apăsat `button.cart`.
- Așteptarea pentru `#cartModal` a expirat după 20 de secunde, cât timp fereastra
  modală a rămas ascunsă; raportul conține secvența de acțiuni și diagnosticele.
- Testul s-a oprit înainte de înregistrarea contului și checkout. Această
  rulare nu stabilește dacă s-a schimbat site-ul, dacă apăsarea nu a modificat
  coșul sau dacă locatorul de confirmare este perimat.
- [`AutoExPurchaseBusinessComponentTest`](../../atf-test-auto-ex/src/test/java/atf/autoex/tests/component/journey/AutoExPurchaseBusinessComponentTest.java) verifică dovezile pentru modalul imediat și întârziat pe fixture-uri locale; nu reproduce incidentul de pe site-ul public.
- Revizia din 2026-11-21 nu a rerulat journey-ul public; constatarea rămâne activă.

- [ ] Reproduceți cu un browser nou și capturați starea coșului și schimbul de
      rețea relevant imediat după apăsare.
- [ ] Înainte de a modifica fluxul, distingeți o schimbare de comportament a
      site-ului public de un defect de locator sau de readiness.
- [ ] Păstrați raportul de eșec inspectabil și verificați curățarea dacă o
  reproducere ulterioară ajunge la crearea contului.
