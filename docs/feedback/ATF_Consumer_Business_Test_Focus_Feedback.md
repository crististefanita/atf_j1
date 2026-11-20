# Verificarea fluxurilor funcționale ale consumatorului — stare curentă

## Căutarea AutoEx — contract livrat

Documentul de [verificare a căutării AutoEx](../ATF_AutoEx_Search_Verification.md) păstrează contractul GET/DOM proaspăt, incidentul inițial și dovezile de regresie.
[Dovada achiziției live](../ATF_J1_Live_Screenshot_Verification.md) înregistrează
verificările separate de achiziție și randare pentru AutoEx și Shop.

<a id="autoex-catalog-evidence-146"></a>
## AUTOEX-CATALOG-EVIDENCE-146 — asocierea dovezii comparației cu schimbul observat

**Prioritate: 48/100.**

Modificarea sursei și verificările țintite pentru raportul de componentă și
browserul local trec.

- Raportul valid atribuie comparația o singură dată GET-ului selectat, deși există
  un POST cu aceeași cale și mai multe GET-uri.
- Dovezile absente sau cu secvență duplicată produc o aserțiune API în FAIL, fără
  să atașeze comparația business.
- Verificarea în browser confirmă expandarea grupului de aserțiuni și a detaliilor
  cererii în raportul Extent randat.
- Dovezile de componentă și browser local sunt înregistrate mai jos; artefactul
  la nivel de suită rămâne restant.

- [x] Rulați captura controlată și inspectați raportul generat pentru a demonstra
      că comparația apare numai sub schimbul observat și selectat.
- [x] Rulați regresia de corelare după metodă, cale și secvența capturată;
      confirmați că o captură absentă sau ambiguă eșuează și că verificările nu se
      dublează între potrivirile de cale.
- [x] Păstrați comportamentul în suportul principal AutoEx și în testele
      consumerului; regresiile generice de renderer și ordinal rămân în grija
      Reporting Tests din J0.
- [x] Rulați verificările de componentă și de prezentare în browserul local;
      inspectați raportul generat pentru un singur responsabil al comparației,
      previzualizarea de 240, niciun schimb sintetic și dovezi API/cerere
      expandabile.
- [ ] Inspectați artefactul Extent al suitei dintr-o rulare controlată și
      confirmați că păstrează același responsabil al comparației și aceeași ierarhie
      expandabilă.

**Dovezi curente**

- La 2026-11-20, cele două cazuri selectate de corelare din `AutoExRuntimeBoundaryComponentTest` au trecut 2/2, fără eșecuri sau erori, în ambele ediții TMP canonice.
- Rularea a generat rapoarte HTML pentru captură validă, lipsă și ambiguă în `atf-test-auto-ex\artifacts\reports\j1-extent-api-20261120`, în fiecare checkout de staging.
- `AutoExCatalogReportPresentationE2eTest` a trecut 1/1 în fiecare ediție; `PlaywrightPagePreparationTest` a trecut 7/7.
- Testul din browser a expandat scenariul, faza testului, pasul API al catalogului, validarea API, GET-ul selectat și grupul de aserțiuni API; a verificat că aserțiunea pornește colapsată, devine vizibilă după expandare și că detaliile requestului se deschid.
- Aserțiunile componente au confirmat un singur owner al comparației pe GET-ul selectat, patru schimburi capturate, lipsa unui al cincilea schimb sintetic și limita de preview de 240 de caractere.
- Suita completă `AutoExE2eSuite` nu a rulat; artefactul ei cu scope de suită rămâne deschis.
<a id="shop-billing-sanity"></a>
## SHOP-BILLING-SANITY — izolarea eșecurilor UI și de căutare existente

Prioritate: 88/100. În timpul adoptării binarelor Console/Mock, testul
ShopBillingSynchronizationComponentTest, rămas neschimbat, a avut două eșecuri
de aserțiune și o eroare în treisprezece invocări. Invocarea cu cursa a durat
122 de secunde, deși bugetele configurate erau de 1,1 secunde pentru acțiune și
3 secunde pentru configurare; o urmă de thread arată apeluri imbricate
verificări Playwright de disponibilitate/`isEnabled` în timpul selectării căutării poștale.

- Reproduceți separat cazurile de cursă, așteptare și duplicare pe configurații
  de test DOM/API controlate; comparați consumul din sursă și din binar înainte de a
  atribui cauza.
- Păstrați oracolele independente originale pentru adresă, trimitere/finalizare
  și lipsa reintroducerii textului. Urmăriți bugetul rămas prin verificările
  native imbricate de disponibilitate, în loc să adăugați încercări repetate sau
  durate de așteptare mai mari.
- După remedierea ownerului real, verificați cazurile negative adiacente
  căutării poștale și randarea Extent pentru rularea individuală și pentru suită.

Suita Shop extinsă a fost oprită intenționat după păstrarea eșecurilor și a
urmei de thread; aceasta nu este o acceptanță trecută. Verificarea concentrată
a adoptării bibliotecii rămâne separată de această limită UI nerezolvată.
Dovezile rămân lângă verificarea pachetului în TMP-ul deținut.

**Rerulare concentrată, 2026-11-19.** Aceeași clasă de componentă a rulat
într-un staging TMP deținut: 13 teste, 0 eșecuri, 0 erori, 0 skip-uri,
37,045 secunde. Eșecul anterior nu s-a reprodus. Aceasta nu închide criteriul:
suita extinsă și comparația sursă-versus-JAR sunt încă restante.

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

- [ ] Reproduceți cu un browser nou și capturați starea coșului și schimbul de
      rețea relevant imediat după apăsare.
- [ ] Înainte de a modifica fluxul, distingeți o schimbare de comportament a
      site-ului public de un defect de locator sau de readiness.
- [ ] Păstrați raportul de eșec inspectabil și verificați curățarea dacă o
  reproducere ulterioară ajunge la crearea contului.
