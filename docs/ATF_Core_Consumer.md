# ATF Core — artefactul bibliotecii și acceptanța consumerului

## Pe scurt

J1 consumă Core prin perechea JAR/POM versionată selectată de POM-ul rădăcină.
Reactorul nu conține modulul-sursă Core; testele locale inspectează artefactul
fizic și exercită integrările J1 care îl folosesc.

| Contract | Verificare J1 curentă |
|---|---|
| Runtime | Java 21; versiunile dependențelor sunt fixate în POM-ul publicat |
| Artefact | `atf.api:atf-core:1.0.0-c4`, păstrat în `lib` |
| Distribuție | O pereche JAR/POM plată; fără părinte de reactor sau sursă shadow |
| Proveniență | `coreLibrary` înregistrează coordonate, hash-uri, inputuri și dovezi despre pachet |
| Scope consumer | HTTP, configurație, JSON și lifecycle folosite de modulele locale |

## Maven și IntelliJ

Rulează din rădăcina unei copii TMP deținute. Folosește aceleași setări și
repository Maven pentru rezolvarea din linia de comandă și IntelliJ:

~~~powershell
mvn -B -ntp -N validate
mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Dsurefire.failIfNoSpecifiedTests=false" test
~~~

Prima comandă instalează cele cinci perechi de biblioteci în repository-ul
Maven selectat. Reîncarcă POM-ul rădăcină în IntelliJ după selectarea aceluiași
JDK și repository. Nu folosi `systemPath`, repository ierarhic de fișiere sau
JAR adăugat manual în IDE.

## Integritatea artefactului și acceptanță

1. Selectează versiunea Core prin `atf.core.version` și păstrează perechea
   JAR/POM corespondentă direct în `lib`.
2. Verifică coordonata publicată, POM-ul încorporat, valorile SHA-256,
   namespace-ul bytecode-ului și `CodeSource`-ul încărcat efectiv.
3. Respinge sursele shadow, coordonatele lipsă, fișierele suplimentare în
   distribuție și dependențele de producție interzise.
4. Rulează contractele locale afectate și scenariile consumerului pe artefactul
   fizic; păstrează raportul și dovezile execuției în TMP.
5. Actualizează proveniența numai când se schimbă octeții pachetului sau
   dovezile de verificare înregistrate.

Guvernanța citește proveniența care descrie inputurile pachetului și verificările
native ale bibliotecii. Aceste înregistrări nu afirmă că verificările au rulat
în J1.

## Fluxul artefactului Core

~~~mermaid
flowchart LR
    Pin["POM rădăcină<br/>coordonata Core"] --> Pair["lib/<br/>JAR + POM Core"]
    Pair -->|instalează| Repo["Repository Maven"]
    Repo -->|rezolvă pentru| Modules["Module J1"]
    Modules -->|acceptanță locală| Evidence["Dovezi consumer"]
    Governance["Guvernanța artefactelor"] -. verifică identitatea și limita .-> Pair
~~~

<details>
<summary><strong>Legendă — rezolvarea artefactului și dovada locală</strong></summary>

- POM-ul rădăcină fixează coordonata Core folosită de J1.
- Maven și IntelliJ rezolvă perechea fizică din repository-ul local selectat.
- Acceptanța consumerului acoperă numai comportamentul Core exercitat prin
  modulele J1; Reporting are coordonate și proveniență separate.
- Diagrama arată rezolvarea dependenței, nu o dependență de un workspace cu surse.

</details>

## Limita acceptanței Reporting

Reporting controlează identitatea raportului, timpii capturați și publicați,
aserțiunile și disclosures. Integrarea Core și acceptanța aplicației sunt
distincte de contractul Reporting. [Atlasul bibliotecii Reporting](ATF_Reporting_Library_Atlas.md)
descrie contractul observabil.

## Documente JSON complete și dovezi fidele

| Limită | Contractul folosit de consumer |
|---|---|
| JsonDocuments | Un document complet, fără conținut final suplimentar sau chei duplicate, inclusiv imbricate; reader imuabil și arbore nou la fiecare apel |
| Sintaxă și scriere | Scalarile și literalul null sunt JSON; factory-ul POST/PUT JSON respinge null înainte de trimitere |
| Cerere explicită | Căile form/text rămân disponibile; executorul nu deduce JSON doar din metoda HTTP |
| Egalitate Mock | BigDecimal fără îngustare; number/string și integer/decimal își păstrează distincțiile; corpul brut rămâne intact |
| Redactare Core | Tokenurile numerice și câmpurile publice duplicate se păstrează; fiecare valoare sensibilă este mascată complet |
| Afișare Reporting | Proiecția independentă păstrează numerele și duplicatele înainte de trunchierea configurată |
| Aserțiuni | Expected/actual sunt redactate separat; verdictul callerului e independent de preview-ul de 240 de caractere |

- Parserul strict respinge documentele ambigue folosite pentru decizii; proiecția
  de dovezi păstrează fiecare apariție a câmpului.
- Mock acceptă literalul null ca sintaxă, independent de restricția factory-ului
  de scriere.
- JSON-ul diagnostic invalid nu devine payload valid.

Testele consumerului și guvernanța artefactelor verifică binarele selectate fără
a adăuga sursa bibliotecii în reactor.

## Core, Console și anulare upstream

Future-ul HTTP păstrează o legătură best-effort de anulare către operația
upstream. Execuția concurentă are un singur proprietar terminal, iar erorile de
cleanup rămân suprimate.

- URLConnection deține corpul, worker-ul virtual și conexiunea; colectarea
  corpului, conectarea și citirile consumă același deadline. Un corp livrat după
  terminare nu poate porni cererea.
- Anularea nu confirmă rollback pe server și nu autorizează replay-ul unei
  mutații incerte. Un GET local controlat și testul pe JAR fizic rămân distincte
  de E2E-ul consumerului.

## Bibliotecile comune ale platformei

[Consumul bibliotecilor platformei](ATF_Platform_Libraries.md) documentează
contractele celor cinci artefacte, distribuția plată, bootstrap-ul
Maven/IntelliJ și acceptanța locală. Comportamentul adaptorului și catalogului
rămâne în modulele J1.
