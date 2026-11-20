# Arhitectura ATF J1

Acest document descrie reactorul Maven ATF J1 din acest checkout. Cele șase module locale și testele lor sunt subiectul arhitecturii. Cinci biblioteci ATF versionate sunt consumate ca dependențe binare selectate de POM-ul rădăcină.

Reactorul păstrează exact cele 6 module existente.

## Pe scurt

- Reactorul conține șase module: Validation, UI, Debug UI, Practice Shop, AutoEx și teste de guvernanță.
- Core, Reporting, Console, Mock și Test Kit sunt consumate din perechile JAR/POM versionate din lib.
- POM-ul rădăcină și POM-ul fiecărui modul sunt sursele normative pentru module, versiuni și scope-urile dependențelor.
- [Sincronizarea bibliotecilor](ATF_Workspace_Synchronization.md#consumed-library-catalog) înregistrează artefactele selectate; [ghidurile bibliotecilor](../lib/README.md) descriu contractele folosite de consumatori.

## Modulele locale ale reactorului

| Modul | Responsabilitate în J1 | Verificare principală |
|---|---|---|
| atf-validation | Contracte de răspuns, reguli de validare și publicarea verdictului prin portul local de validare | Teste unitare și de componentă |
| atf-ui | Adaptor Playwright comun și politici de browser folosite de consumatorii locali | Teste unitare și de componentă |
| atf-debug-ui | Server local doar pentru citirea și inspectarea artefactelor HTML generate | Verificări de componentă și browser |
| atf-test-practice-shop | Configurația Practice Shop, pagini, fluxuri API/UI și dovezi specifice aplicației | Suite de componentă, API, UI și E2E |
| atf-test-auto-ex | Configurația AutoEx, fluxuri API/UI și dovezi specifice aplicației | Suite de componentă, API, UI și E2E opționale |
| atf-tests | Guvernanța arhitecturii, documentației, dependențelor și împachetării acestui reactor | Suite de guvernanță |

Cele două module consumer de aplicație declară Test Kit numai pentru codul de test:

| Consumator | `compile` | `test` |
|---|---|---|
| Shop | Core | Test Kit |
| AutoEx | Core | Test Kit |

## Biblioteci consumate

| Bibliotecă | Coordonată selectată | Utilizare în J1 |
|---|---|---|
| Core | atf.api:atf-core:1.0.0-c4 | Contracte API pentru cereri, răspunsuri, execuție și configurare |
| Reporting | atf.api:atf-reporting:1.0.0-j25 | Modelul Extent și publicarea rapoartelor HTML consumate de testele locale |
| Console | atf.api:atf-console:1.0.0-c2 | Ieșire diagnostică structurată |
| Mock | atf.api:atf-mock:1.0.0-m2 | Transport fals determinist în guvernanță și scenarii de test |
| Test Kit | atf.api:atf-test-kit:1.0.0-k2 | Adnotări pentru nivelul testelor, resolvere și suport de testare |

J1 verifică exact octeții JAR/POM selectați și integrările exercitate de propriile teste. Ghidurile bibliotecilor descriu contractele disponibile; acceptanța locală nu pretinde că rulează suita independentă de teste a unei biblioteci.

## Diagrama dependențelor

Săgețile continue indică dependențe Maven directe și scope-urile declarate. Săgețile punctate indică verificări de guvernanță, nu dependențe Maven.

~~~mermaid
flowchart LR
  subgraph Libraries[Biblioteci versionate din lib]
    Core[Core]
    Reporting[Reporting]
    Console[Console]
    Mock[Mock]
    Kit[Test Kit]
  end

  subgraph Reactor[Cele șase module locale J1]
    Validation[atf-validation]
    UI[atf-ui]
    Debug[atf-debug-ui]
    Shop[atf-test-practice-shop]
    AutoEx[atf-test-auto-ex]
    Governance[atf-tests]
  end

  Core -->|compile| Validation
  Kit -->|test| Validation
  Core -->|compile| UI
  Kit -->|test| UI
  Console -->|compile| Debug
  Kit -->|test| Debug
  Core -->|compile| Shop
  Console -->|compile| Shop
  Reporting -->|compile| Shop
  UI -->|compile| Shop
  Kit -->|test| Shop
  Core -->|compile| AutoEx
  Reporting -->|compile| AutoEx
  UI -->|compile| AutoEx
  Console -->|test| AutoEx
  Kit -->|test| AutoEx
  Core -.->|test| Governance
  Reporting -.->|test| Governance
  Console -.->|test| Governance
  UI -.->|test| Governance
  Kit -.->|test| Governance
  Mock -.->|test| Governance
~~~


Guvernanța verifică și graful modulelor locale, precum și documentația. Nu schimbă scope-urile dependențelor și nu înlocuiește acceptanța la nivel de modul.

<a id="ui-traffic-capture-and-selection"></a>

## Capturarea și selecția traficului UI

Modulele aplicațiilor dețin selecția scenariilor, baseline-urile de setup, valorile așteptate și verdicturile. Modulul comun `atf-ui` execută acțiuni în browser și returnează observații neutre; adaptoarele locale mapează aceste observații în dovezile scenariului. Biblioteca Reporting selectată publică modelul raportului ca HTML Extent.

~~~mermaid
flowchart LR
  subgraph Consumers[Modulele consumer locale J1]
    Shop[Scenarii și așteptări Shop]
    ShopAdapter[Adaptor de dovezi și aserțiuni Shop]
    AutoEx[Scenarii și așteptări AutoEx]
    AutoExAdapter[Adaptor de dovezi și aserțiuni AutoEx]
  end
  UI[atf-ui<br/>acțiuni de browser și observații neutre]
  Reporting[JAR Reporting selectat]
  HTML[HTML Extent]
  Checks[Verificări locale de componentă și UI]

  Shop -->|execută prin| UI
  AutoEx -->|execută prin| UI
  UI -->|returnează observații| ShopAdapter
  UI -->|returnează observații| AutoExAdapter
  Shop -->|deține| ShopAdapter
  AutoEx -->|deține| AutoExAdapter
  ShopAdapter -->|publică modelul local de raport| Reporting
  AutoExAdapter -->|publică modelul local de raport| Reporting
  Reporting -->|randează| HTML
  HTML -->|este inspectat când este selectat| Checks
~~~

- Shop și AutoEx păstrează fluxurile aplicațiilor, baseline-urile de setup, așteptările business și verdicturile în modulele locale.
- `atf-ui` deține execuția generică a browserului și observațiile neutre; nu selectează aserțiuni business și nu publică HTML.
- Reporting este o dependență binară consumată. Deține rendererul generic de rapoarte; J1 verifică integrările folosite de testele locale.

## Acceptanța consumatorilor și rapoartele Extent

Practice Shop și AutoEx dețin scenariile aplicațiilor lor. Testele locale de componentă și UI care generează rapoarte verifică adaptoarele J1, dovezile scenariilor, ieșirea Extent generată și comportamentul vizibil în browser selectat de suitele respective. Testele de componentă păstrează detaliile aserțiunilor în pași expandabili; verificările de browser inspectează raportul randat când scenariul o cere.

Un test verde al unui consumator J1 este dovadă pentru fluxul local executat și versiunea de bibliotecă selectată. Nu dovedește că au fost exercitate toate contractele bibliotecilor sau toate fluxurile aplicației. Consultă [ghidul de testare](ATF_Testing_Guide.md), [ghidul consumatorului Reporting](ATF_Reporting_Consumer.md) și [feedback-ul activ](feedback/README.md).

## Controlul modificărilor

Când se schimbă un modul local, o dependență sau o aserțiune din raport, actualizează README-ul modulului responsabil și diagrama sau tabelul de mai sus dacă se schimbă arhitectura. Guvernanța edițiilor documentației compară structura și linkurile în engleză și română; guvernanța documentației verifică faptul că versiunile bibliotecilor corespund POM-ului rădăcină și perechilor locale de artefacte.
