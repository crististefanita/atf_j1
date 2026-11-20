# Verificarea fluxurilor funcționale ale consumatorului — stare curentă

## Căutarea AutoEx — contract livrat

Documentul de [verificare a căutării AutoEx](../ATF_AutoEx_Search_Verification.md) păstrează contractul GET/DOM proaspăt, incidentul inițial și dovezile de regresie.

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

**Stare: deschisă.** Eșecul inițial nu s-a reprodus, dar latența traseului race nu are încă o cauză atribuită.

- În timpul adoptării Console/Mock, `ShopBillingSynchronizationComponentTest` a avut două eșecuri de aserțiune și o eroare în treisprezece invocări.
- Invocarea inițială cu cursă a durat 122 de secunde. O urmă de thread a arătat verificări Playwright imbricate de disponibilitate/`isEnabled` în timpul selectării lookup-ului poștal; acesta este un indiciu, nu o cauză rădăcină confirmată.

**Rerulări focalizate**

- La 2026-11-19, toate cele 13 invocări au trecut în 37,045 secunde.
- La 2026-11-20, toate cele 13 au trecut în 59,19 secunde. Invocarea `race,true,true` a durat 16,986 secunde; celelalte invocări au durat între 2,075 și 7,124 secunde. Hash-ul sursei testului selectat corespunde checkout-ului live.
- Durata JUnit include pornirea browserului/sesiunii și navigarea la fixture. Nu dovedește că lookup-ul poștal a consumat 16,986 secunde. Testul verifică încheierea cererilor, valorile finale ale adresei și lipsa reintroducerii textului, dar nu are oracol pentru durată. Fixture-ul local folosește debounce de 300 ms și injectează o aplicare întârziată cu 500 ms în modul `race`.

**Dovezi rămase**

- Măsurați separat pornirea/navigarea browserului față de intervalul dintre `inputsApplied`, terminarea cererii, aplicarea în DOM și încheierea observării din `ShopPostalLookup`. Comparați intervalul de la input la încheiere cu bugetul de observare existent.
- Păstrați oracolele pentru adresă, ownership-ul cererilor, completare și lipsa reintroducerii textului. Nu măriți timeout-urile și nu adăugați încercări repetate pentru a ascunde latența.
- Dacă se justifică o schimbare de cod, rerulați matricea de 13 cazuri și inspectați dovada Extent a componentei.

Rularea Shop extinsă anterioară a fost oprită după păstrarea eșecurilor și a urmei de thread; niciuna dintre trecerile focalizate nu dovedește acceptanța suitei extinse. Închideți criteriul numai după ce rulări repetate ale cursei arată o încheiere limitată a lookup-ului, iar timpul de pornire rece al browserului este raportat separat.

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

- [ ] Reproduceți cu un browser nou și capturați starea coșului și schimbul de
      rețea relevant imediat după apăsare.
- [ ] Înainte de a modifica fluxul, distingeți o schimbare de comportament a
      site-ului public de un defect de locator sau de readiness.
- [ ] Păstrați raportul de eșec inspectabil și verificați curățarea dacă o
  reproducere ulterioară ajunge la crearea contului.
