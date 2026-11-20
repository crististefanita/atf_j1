# Acceptanța capturilor live J1 — 2026-11-03

## Domeniu și rezultat

O achiziție UI reală AutoEx și o achiziție UI/API reală Shop ca vizitator au
rulat individual și prin suitele canonice existente. Toate cele patru invocări
trec; fiecare execuție produce un artifact Extent nou.

| Aplicație și lansare | Caz selectat | Rezultat | Aserțiuni | Referințe PNG | Revizuire |
|---|---|---|---:|---:|---|
| AutoEx individual | AutoExPurchaseUiE2eTest | 1 PASS | 49 | 13 | [raport individual](http://127.0.0.1:53827/autoex-individual.html#test-view) |
| AutoExE2eSuite, filtrată | aceeași clasă | 1 PASS | 49 | 13 | [raport de suită](http://127.0.0.1:53827/autoex-suite.html#test-view) |
| Shop individual | ShopPurchaseE2eTest | 1 PASS | 150 | 10 | [raport individual](http://127.0.0.1:53827/shop-individual.html#test-view) |
| ShopE2eSuite, filtrată | aceeași clasă | 1 PASS | 150 | 10 | [raport de suită](http://127.0.0.1:53827/shop-suite.html#test-view) |

AutoEx se înregistrează la checkout, finalizează comanda demo și șterge contul
temporar. Shop finalizează o achiziție ca vizitator cu dovezi UI/API observate.
Numărătorile disting invocările JUnit, rândurile comparațiilor și referințele
la capturi.

## Selectarea exactă a suitei

Launcherul extern selectează AutoExE2eSuite sau ShopE2eSuite reală și folosește
un filtru post-discovery pentru clasa de achiziție cerută. Verifică existența
exact a unui caz descoperit înainte de execuție și cere un succes, zero teste
sărite/anulate și nicio eroare de container după rulare.

| Limită | Comportament păstrat |
|---|---|
| Cod canonic | Clasele suitei, adnotările și selectorii de producție existenți rămân neschimbați |
| Selector extern | Rămâne numai în TMP; ID-urile discovery păstrează motorul suitei și clasa canonică reală |
| Execuție | Planul descoperit și filtrat se execută serial; cazurile fără legătură din suită sunt excluse |
| Dovezi | Aceeași clasă business și aceeași populație de aserțiuni, individual și în scope-ul suitei |
| Limită | Aceste patru rulări nu certifică toate cazurile din niciuna dintre suitele complete |

## Context complet la zoom de 70 la sută

Toate cele 46 de referințe la capturi duc la fișiere PNG native valide, cu
geometria completă a viewport-ului de 1440 × 1000, în marja de rotunjire de un
pixel deja existentă în motor. Overview înregistrează layout-ul CSS de
2057 × 1429 și DPR 0.70; nu se folosește captură regională sau decupare după
captură.

| Inspectare în browser | Rezultat observat |
|---|---|
| Aserțiuni | Aceleași 49/150 rânduri per aplicație în ambele moduri; comparațiile sunt inițial colapsate, iar căile vizate se deschid corect |
| Acțiuni UI | Tabelele native cu acțiuni ordonate se extind folosind layout-ul dinamic existent al coloanelor |
| Capturi | Toate referințele se decodează; selecțiile inițială/intermediară/finală din vizualizator păstrează exact PNG-ul original și fiecare margine |
| Contextul vizualizatorului | Încadrarea folosește ecranul disponibil și proporțiile originale; Escape păstrează ownerul, disclosure-urile și focusul tastaturii |
| Navigare | Tests, Category, Fails și Overview funcționează fără erori JavaScript |
| Revizuire vizuală | Capturi inspectate din toate cele patru artifacte: coșul/magazinul AutoEx și contextul checkout/comandă Shop |
| Preview | Valoarea implicită rămâne 240; nicio aserțiune sau limită de timp a acțiunii nu se schimbă |

## Timpii fazelor și limitele execuției

| Corpul testului | Execuție ms | Captură ms | Publicare ms |
|---|---:|---:|---:|
| AutoEx individual | 31766 | 19038 | 375 |
| AutoEx suite | 32994 | 6399 | 269 |
| Shop individual | 15073 | 1560 | 860 |
| Shop suite | 10303 | 1397 | 505 |

Aceștia sunt timpi capturați pe faze, fără flush-ul HTML final. Durata JUnit
include și achiziția resurselor și lucrul lifecycle-ului; nu o folosim în locul
duratei corpului testului. Limitele pentru acțiunile/navigarea UI rămân 20 s/45 s.

Shop folosește o copie YAML temporară în care doar `headless=false` devine
`true`. Ambele module păstrează zoom-ul configurat de 70%, inputurile demo și
comportamentul nativ al browserului; runtime-ul Node selectat de SDK este
moștenit fără suprascriere din partea agentului.

## Îmbunătățire rămasă

Acceptanța business și de randare de mai sus trece. HTML-ul nu păstrează
identitatea canonică reală a suitei pentru aceste rulări filtrate; numele
fișierului AutoEx conține și `purchase-suite` la o lansare individuală.
[Criteriul J1 pentru contextul lansării](feedback/ATF_Reporting_Feedback.md#j1-launch-context-146--înregistrarea-domeniului-real-al-lansării)
izolează această îmbunătățire de auditabilitate de defectele generice ale
rendererului.

<details>
<summary><strong>Proveniența execuției, binarului și curățării</strong></summary>

| Element | Dovadă păstrată |
|---|---|
| Rădăcina TMP deținută | ATF_LIVE_SCREEN_AUDIT_ACTIVE_DO_NOT_CLEAN_85211735 |
| Baza capturată J1 | f2aaa41e1f0939b93ccdab509492f862c09d18cc; lucrul local existent este păstrat |
| Baza documentației biblioteca consumată | 6bef40515954acd121c93a66e1bf544db14e97b5 |
| Rulări individuale | live-autoex-individual-atf_j1.log și live-shop-individual-atf_j1.log; XML-urile Surefire sunt păstrate |
| Rulări din suite canonice | suite-autoex.log, suite-shop.log și recipisele JSON pentru discovery/metrici |
| Verificare browser | render-audit.log, render-captures/audit.json și capturile PNG inspectate |
| Contextul lansării runtime | run-context-probe.log; numele canonic real al suitei lipsește din ambele artifacte de suită |
| Reporting | SHA-256 pentru JAR-ul fizic J18: 63655536DE4EBA02A8AF6CA765D445BBCE7D89967979FF5BF9247FDC0F79AD02 |
| Core | SHA-256 pentru JAR-ul fizic C3: 605407B9D40E67C9C352EE6E94DA758E50BD3CFB14CC0D7A0DE3359C71088447 |
| Modificări documentare | Octeții sursei/runtime/JAR rămân neschimbați; se promovează numai documentația revizuită |

URL-urile de revizuire necesită serverul loopback păstrat. Build-urile,
rapoartele și capturile rămân în TMP-ul deținut; Chromium și capturile inspectate
folosesc alternativa aprobată anterior la TAB MCP, indisponibil.

</details>
