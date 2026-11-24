# ATF — guvernanța artifactelor și limitelor modulelor J1

## Pe scurt

Acest pachet verifică reactorul J1 declarat de POM-ul rădăcină și cele șase
artifacte de bibliotecă versionate consumate din `lib`. Citește numai POM-ul,
fișierele artifactelor și proveniența din acest checkout.

## Catalogul local de biblioteci

| Bibliotecă | Coordonată | Pereche locală |
|---|---|---|
| Core | `atf.api:atf-core:1.0.0-c4` | `lib/atf-core-1.0.0-c4.jar` + POM |
| Reporting | `atf.api:atf-reporting:1.0.0-j26` | `lib/atf-reporting-1.0.0-j26.jar` + POM |
| Console | `atf.api:atf-console:1.0.0-c2` | `lib/atf-console-1.0.0-c2.jar` + POM |
| Mock | `atf.api:atf-mock:1.0.0-m2` | `lib/atf-mock-1.0.0-m2.jar` + POM |
| Test Kit | `atf.api:atf-test-kit:1.0.0-k4` | `lib/atf-test-kit-1.0.0-k4.jar` + POM |
| Debug UI | `atf.api:atf-debug-ui:1.0.0-d1` | `lib/atf-debug-ui-1.0.0-d1.jar` + POM; inspector doar în scope-ul `test`, nu produce rapoarte |

POM-ul rădăcină selectează versiunile. `library-provenance.json` identifică
hash-urile binar/POM și dovezile păstrate despre împachetare.

## Suprafața verificărilor

| Punct de intrare | Responsabilitate | Dovadă |
|---|---|---|
| [GovernanceDeployment](GovernanceDeployment.java) | catalog Maven local strict și deployment declarat | [verificări de module și ținte](GovernanceDeploymentTest.java) |
| [CoreGovernanceSurface](core/CoreGovernanceSurface.java) | identitatea artifactului Core, limita bytecode și clasa încărcată | [mutații Core](core/CoreGovernanceSurfaceTest.java) |
| [ReportingGovernanceSurface](ReportingGovernanceSurface.java) | POM Reporting, proveniență și limita sursă/JAR | [proveniență și mutații](ReportingGovernanceSurfaceTest.java) |
| [DeploymentFixture](DeploymentFixture.java) / [BinaryReportingFixture](BinaryReportingFixture.java) | mutații izolate ale deployment-ului local | reactorul live și artifactele publicate rămân neschimbate |

## Fluxul dovezilor

~~~mermaid
%%{init: {"themeVariables":{"fontSize":"15px"},"themeCSS":".edgeLabel, .edgeLabel p { font-size: 12px !important; } .node rect { rx: 6px; ry: 6px; }","flowchart":{"curve":"linear","nodeSpacing":24,"rankSpacing":22,"padding":6},"sequence":{"mirrorActors":false,"messageMargin":24,"wrap":true}}}%%
flowchart LR
    POM["POM rădăcină J1"] -->|selectează| Pair["Perechi de biblioteci"]
    Pair -->|rezolvate de| Reactor["Module J1"]
    Reactor -->|exercitate prin| Tests["Verificări consumer"]
    Tests -->|înregistrează| Evidence["Proveniență<br/>și rapoarte"]
    Guard["guvernanța<br/>artifactelor"] -. verifică .-> Pair
~~~

<details>
<summary><strong>Legendă — dovezi pentru artifactele locale</strong></summary>

- POM-ul definește modulele locale și cele șase versiuni de bibliotecă selectate.
- Guvernanța verifică coordonatele artifactelor, octeții, limitele dependențelor
  și identitatea clasei încărcate fără să citească un workspace alăturat.
- Catalogul testelor native din proveniență înregistrează dovezi despre pachet;
  nu pretinde că acele teste au rulat în J1.
- Acceptanța consumerului acoperă doar scenariile J1 selectate și executate.

</details>

## Invariante și limite

- POM-ul J1 definește inventarul modulelor locale; lipsa unui modul declarat
  provoacă eșecul verificării.
- Distribuția binară citește perechile JAR/POM corespondente din `lib`.
- Guvernanța respinge sursele shadow, fișierele lipsă, coordonatele duplicate și
  modulele nedeclarate.
- POM-ul încorporat trebuie să coincidă cu POM-ul distribuit; verificările
  CodeSource și JDK `jdeps` detectează un binar încărcat diferit sau dependențe
  bytecode interzise.
- Faptele documentate se compară cu POM-ul rădăcină, perechile fizice și indexul
  local al bibliotecilor.
- Guvernanța schimbărilor de teste folosește baza de revizuire a acestui checkout.

## Mentenanță

O schimbare de versiune a bibliotecii actualizează într-un singur increment
revizuit proprietatea rădăcină, perechea JAR/POM corespondentă, proveniența,
catalogul documentației și dovada consumerului afectat. Un fișier lipsă sau
redenumit nu înseamnă că dependența a fost eliminată.
