# ATF — consumul bibliotecilor platformei

Acest ghid descrie cele cinci artefacte de bibliotecă versionate consumate de
reactorul J1 și verificările care le protejează utilizarea.

## Pe scurt

J1 rezolvă Core, Reporting, Console, Mock și Test Kit din perechile JAR/POM
plate din `lib`. POM-ul rădăcină selectează versiunile; adaptoarele locale,
configurația, scenariile business și testele de acceptanță rămân în modulele J1
care le dețin. Detaliile bibliotecilor sunt rezumate în
[catalogul artefactelor](ATF_Workspace_Synchronization.md) și
[indexul local al bibliotecilor](../lib/README.md).

## Contractele bibliotecilor

| Bibliotecă | Contractul principal folosit aici |
|---|---|
| Core | Execuție HTTP, configurație, JSON și primitive de lifecycle |
| Reporting | Randare Extent/fallback și dovezi de raport auditabile |
| Console | Formatarea neutră a evenimentelor și diagnostice configurate |
| Mock | Înlocuitor determinist al portului HTTP și potrivire strictă a cererilor |
| Test Kit | Clasificare JUnit, comparații și suport pentru lifecycle-ul testelor |

- Bridge-ul JUnit din Console este opțional. Modulele consumer care îl folosesc
  declară direct Test Kit, deoarece dependențele Maven opționale nu sunt
  tranzitive.
- Console și Mock nu depind de Reporting, Validation sau de modulele aplicațiilor.
- Fixture-urile business și cataloagele YAML rămân în modulele consumerilor lor.

## Rezolvare și acceptanță

~~~mermaid
flowchart LR
    Coordinates["POM rădăcină<br/>versiunile bibliotecilor"] --> Pair["lib/<br/>cinci perechi JAR/POM"]
    Pair -->|validate instalează| Maven["Repository Maven local"]
    Maven -->|rezolvarea dependențelor| Consumer["Module J1"]
    Consumer -->|scenarii locale| Acceptance["Dovezi component și integrare"]
    Provenance["library-provenance.json"] -. hash-uri și origine .-> Pair
    Governance["Guvernanța J1"] -. verifică .-> Pair
~~~

<details>
<summary><strong>Legendă — dependențe binare și dovezi locale</strong></summary>

- Săgețile descriu rezolvarea dependențelor și acceptanța consumerului.
- Testele J1 rulează cu JAR-urile fizice selectate în POM-ul rădăcină.
- Guvernanța verifică coordonatele, coerența POM/JAR, hash-urile, limitele
  dependențelor și inventarul plat din `lib`.
- Sursa și testele native ale bibliotecilor nu sunt incluse în acest reactor.
  Proveniența poate înregistra dovezi despre pachet, dar o rulare J1 nu implică
  executarea acelor teste aici.

</details>

## Maven și IntelliJ

Rulează dintr-o copie TMP temporară:

~~~powershell
mvn -B -ntp -N validate
mvn -B -ntp -DskipTests test-compile
~~~

Faza `validate` a POM-ului rădăcină instalează cele cinci perechi POM/JAR reale
în repository-ul Maven selectat. Folosește același JDK, setări Maven și
repository în IntelliJ, apoi reîncarcă POM-ul rădăcină. Versiunile dependențelor
sunt selectate prin `atf.core.version`, `atf.reporting.version`,
`atf.console.version`, `atf.mock.version` și `atf.testkit.version`.

Nu selecta direct clasele fixture JUnit imbricate: eșecurile lor intenționate
sunt verificate de testele exterioare. Verifică `CodeSource`-ul JAR-ului fizic
când investighezi conflicte de classpath.

## Integritate și mentenanță

- `lib` conține cinci perechi JAR/POM curente, un index și câte un ghid per
  bibliotecă. Nu conține repository imbricat, arbore de surse sau perechi vechi.
- `library-provenance.json` înregistrează coordonate, hash-urile binar/POM și
  dovezi despre inputul pachetului. Intrările istorice rămân disponibile pentru audit.
- POM-ul Maven încorporat trebuie să fie identic octet cu octet cu POM-ul
  publicat alăturat. Guvernanța verifică artifactele fizice, refuzul sursei
  shadow, limitele dependențelor și inventarul plat.
- Build-urile, cache-urile, rapoartele generate, copiile de recuperare și notele
  temporare ale pachetului rămân în staging-ul TMP deținut.
- Păstrează preview-ul implicit de 240 de caractere și aserțiunile
  component/E2E auditabile și colapsabile. Verificările offline nu implică
  acceptanță pe dispozitiv, broker extern sau ASR.

Vezi [sincronizarea artefactelor](ATF_Workspace_Synchronization.md) și
[execuția izolată](ATF_Build_And_Execution_Safety.md), precum și ghidurile
individuale pentru [Console](../lib/README.Console.md) și [Mock](../lib/README.Mock.md).
