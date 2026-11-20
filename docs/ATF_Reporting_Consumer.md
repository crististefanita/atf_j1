# ATF J1 — consumul bibliotecii Reporting

<a id="at-a-glance"></a>

## Pe scurt

J1 selectează Reporting prin POM-ul rădăcină și consumă perechea JAR/POM
versionată din `lib`. Ghidul descrie contractul publicat, integrarea locală și
dovezile pe care J1 le poate verifica față de octeții selectați.

**Legături rapide:** [ghidul bibliotecii Reporting](../lib/README.Reporting.md) ·
[catalogul artefactelor](ATF_Workspace_Synchronization.md#consumed-library-catalog) ·
[verificări component ale raportului](#component-report-acceptance).

| Aspect | Regula J1 |
|---|---|
| Selectarea bibliotecii | POM-ul rădăcină fixează o coordonată Reporting; perechea JAR/POM corespunzătoare este păstrată în `lib`. |
| Comportament local | Adaptoarele Shop/AutoEx, configurația, așteptările business și testele consumerului rămân în modulele J1. |
| Dovezi de raport | Testele consumerului verifică raportul generat față de biblioteca selectată și scenariile locale. |
| Execuție | Testele, rapoartele generate și copiile de revizuire rămân în staging TMP deținut. |

<a id="consumed-libraries"></a>

## Biblioteci consumate

| Bibliotecă | Coordonata selectată de POM-ul rădăcină | Contract folosit în J1 |
|---|---|---|
| Core | `atf.api:atf-core:1.0.0-c4` | Primitive HTTP, configurație, JSON și lifecycle |
| Reporting | `atf.api:atf-reporting:1.0.0-j25` | Publicare Extent/fallback și dovezi auditabile de raport |
| Console | `atf.api:atf-console:1.0.0-c2` | Formatare neutră a evenimentelor și diagnostice |
| Mock | `atf.api:atf-mock:1.0.0-m2` | Înlocuitor determinist al portului HTTP |
| Test Kit | `atf.api:atf-test-kit:1.0.0-k2` | Clasificare JUnit, comparații și suport lifecycle |

Acestea sunt dependențe binare ale reactorului J1, nu module sursă din el.
Descrie o dependență prin coordonată, versiune și contract observabil. Păstrează
locale adaptoarele aplicației, fixture-urile și valorile business așteptate.

<a id="resolve-and-accept"></a>

## Rezolvare și acceptanță

~~~mermaid
flowchart LR
    Pin["POM rădăcină<br/>coordonată selectată"] -->|selectează| Pair["lib/<br/>JAR + POM versionate"]
    Pair -->|validate instalează| Repo["Repository Maven"]
    Repo -->|rezolvă pentru| Modules["Module J1"]
    Modules -->|exercită| Contract["adaptor local + scenariu"]
    Contract -->|produce| Evidence["aserțiuni + raport Extent"]
~~~

<details>
<summary><strong>Legendă — dependență și dovezi ale consumerului</strong></summary>

- POM-ul rădăcină selectează coordonata și versiunea folosite de reactorul local.
- `mvn validate` instalează perechea JAR/POM alăturată în repository-ul Maven
  selectat; consumerul rezolvă acei octeți.
- Acceptanța consumerului acoperă doar modulele și scenariile alese de acea
  rulare. Nu implică executarea unor teste din afara acestui reactor.

</details>

<a id="component-report-acceptance"></a>

## Acceptanța raportului component

Pentru o verificare component de reporting, inspectează atât rezultatul testului,
cât și HTML-ul generat. Acceptanța Extent locală trebuie să afișeze scenariul,
pașii încheiați și dovezile aserțiunilor în secțiuni extensibile separat.

Confirmă că **Expand all** afișează fiecare pas și aserțiune imbricată, că
restrângerea unui element-soră păstrează celelalte ramuri deschise și că raportul
randat păstrează statusul, valorile și legăturile de navigare așteptate. Notează
coordonata exactă a bibliotecii și calea raportului la orice nepotrivire
reproductibilă.

<a id="change-a-library-version"></a>

## Schimbarea versiunii unei biblioteci

1. Consultă coordonata selectată în POM-ul rădăcină și perechea corespunzătoare
   din `lib`.
2. Rulează cel mai mic test component sau de integrare J1 afectat dintr-o copie
   TMP deținută, cu setările și repository-ul Maven vizate.
3. Pentru Reporting, deschide HTML-ul generat și verifică nodurile extensibile
   ale testului, pașilor și aserțiunilor relevante în vizualizator.
4. Înregistrează scenariul, comportamentul așteptat și observat, coordonata și
   hash-urile JAR/POM relevante în feedback-ul local.

Folosește [ghidul bibliotecii Reporting](../lib/README.Reporting.md) pentru
suprafața publicată și [sincronizarea artefactelor](ATF_Workspace_Synchronization.md)
pentru regulile locale de instalare și proveniență.
