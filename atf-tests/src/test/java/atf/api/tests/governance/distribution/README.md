# Guvernanța ATF — artifacte J1 și limitele modulelor

## Pe scurt

Acest pachet verifică reactorul J1 declarat în POM-ul rădăcină și cele cinci
artefacte de bibliotecă versionate consumate din `lib`. Citește numai POM-ul,
fișierele artifactelor și proveniența din acest checkout.

## Catalogul local al bibliotecilor

| Bibliotecă | Coordonată | Pereche locală |
|---|---|---|
| Core | `atf.api:atf-core:1.0.0-c4` | `lib/atf-core-1.0.0-c4.jar` + POM |
| Reporting | `atf.api:atf-reporting:1.0.0-j26` | `lib/atf-reporting-1.0.0-j26.jar` + POM |
| Console | `atf.api:atf-console:1.0.0-c2` | `lib/atf-console-1.0.0-c2.jar` + POM |
| Mock | `atf.api:atf-mock:1.0.0-m2` | `lib/atf-mock-1.0.0-m2.jar` + POM |
| Test Kit | `atf.api:atf-test-kit:1.0.0-k3` | `lib/atf-test-kit-1.0.0-k3.jar` + POM |

POM-ul rădăcină selectează versiunile. `library-provenance.json` identifică
hash-urile binar/POM și dovezile păstrate despre pachete.

## Suprafețe de verificare

| Punct de intrare | Responsabilitate | Dovadă |
|---|---|---|
| [GovernanceDeployment](GovernanceDeployment.java) | catalog Maven local strict și deployment declarat | [verificări de module și ținte](GovernanceDeploymentTest.java) |
| [CoreGovernanceSurface](core/CoreGovernanceSurface.java) | identitatea artifactului Core, limita bytecode și clasa încărcată | [mutanți Core](core/CoreGovernanceSurfaceTest.java) |
| [ReportingGovernanceSurface](ReportingGovernanceSurface.java) | POM Reporting, proveniență și limita sursă/JAR | [proveniență și mutanți](ReportingGovernanceSurfaceTest.java) |
| [DeploymentFixture](DeploymentFixture.java) / [BinaryReportingFixture](BinaryReportingFixture.java) | mutații izolate ale deployment-ului local | reactorul live și artifactele publicate rămân neschimbate |

## Fluxul dovezilor

~~~mermaid
flowchart LR
    POM["POM rădăcină J1"] -->|selectează| Pair["cinci perechi JAR/POM în lib"]
    Pair -->|rezolvate de| Reactor["Module J1"]
    Reactor -->|exercitate de| Tests["teste consumer și guvernanță"]
    Tests -->|înregistrează| Evidence["proveniență și rapoarte randate"]
    Guard["guvernanța artifactelor"] -. verifică .-> Pair
~~~

<details>
<summary><strong>Legendă — dovezi despre artifactele locale</strong></summary>

- POM-ul definește modulele locale și cele cinci versiuni de biblioteci selectate.
- Guvernanța verifică coordonatele, octeții, limitele dependențelor și
  identitatea încărcată fără să citească un workspace alăturat.
- Catalogul testelor native din proveniență înregistrează dovezi despre pachet;
  nu afirmă că testele respective au rulat în J1.
- Acceptanța consumerului acoperă numai scenariile J1 selectate și executate.

</details>

## Invariante și limite

- POM-ul J1 definește inventarul modulelor locale; lipsa unui modul declarat eșuează.
- Distribuția binară citește perechile JAR/POM corespondente din `lib`.
- Guvernanța respinge surse shadow, fișiere lipsă, coordonate duplicate și
  module nedeclarate.
- POM-ul încorporat trebuie să corespundă POM-ului distribuit; CodeSource și
  verificările JDK `jdeps` identifică alt binar încărcat sau dependențe bytecode interzise.
- Faptele documentației se compară cu POM-ul rădăcină, perechile fizice și
  indexul local al bibliotecilor.
- Guvernanța schimbărilor de test folosește baza de review a acestui checkout.

## Mentenanță

Schimbarea unei versiuni de bibliotecă actualizează proprietatea rădăcină,
perechea JAR/POM corespondentă, proveniența, catalogul documentației și dovada
consumerului afectat într-un singur increment revizuit. Lipsa sau redenumirea
unui fișier nu implică eliminarea dependenței.
