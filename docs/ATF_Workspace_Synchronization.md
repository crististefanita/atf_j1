# ATF J1 — sincronizarea artefactelor bibliotecilor

Acest ghid descrie cum reactorul J1 selectează, instalează și verifică cele cinci
biblioteci consumate. Reactorul și criteriile sale de acceptanță rămân locale;
dependențele sunt identificate prin coordonate Maven și octeții artifactelor.

## Pe scurt

POM-ul rădăcină fixează versiunea fiecărei biblioteci, iar `lib` păstrează
perechea JAR/POM corespunzătoare. `mvn validate` instalează acele fișiere în
repository-ul Maven selectat, iar modulele consumer compilează și rulează cu
ele. `library-provenance.json` înregistrează hash-urile artefactelor și
proveniența pachetelor.

<a id="consumed-library-catalog"></a>

## Catalogul bibliotecilor consumate

| Bibliotecă | Coordonată Maven | JAR | POM |
|---|---|---|---|
| Core | `atf.api:atf-core:1.0.0-c4` | `lib/atf-core-1.0.0-c4.jar` | `lib/atf-core-1.0.0-c4.pom` |
| Reporting | `atf.api:atf-reporting:1.0.0-j26` | `lib/atf-reporting-1.0.0-j26.jar` | `lib/atf-reporting-1.0.0-j26.pom` |
| Console | `atf.api:atf-console:1.0.0-c2` | `lib/atf-console-1.0.0-c2.jar` | `lib/atf-console-1.0.0-c2.pom` |
| Mock | `atf.api:atf-mock:1.0.0-m2` | `lib/atf-mock-1.0.0-m2.jar` | `lib/atf-mock-1.0.0-m2.pom` |
| Test Kit | `atf.api:atf-test-kit:1.0.0-k3` | `lib/atf-test-kit-1.0.0-k3.jar` | `lib/atf-test-kit-1.0.0-k3.pom` |

Versiunile de mai sus trebuie să corespundă proprietăților din POM-ul rădăcină
și numelor de fișier din `lib`. Utilizarea bibliotecilor și limitele
dependențelor sunt descrise în [indexul local al bibliotecilor](../lib/README.md).

## Fluxul artefactelor

~~~mermaid
flowchart LR
    POM["POM rădăcină<br/>coordonate selectate"] -->|fixează| Pair["lib/<br/>JAR + POM versionate"]
    Pair -->|mvn validate instalează| Repo["Repository Maven selectat"]
    Repo -->|rezolvă pentru| Modules["Module J1"]
    Modules -->|verificări component și integrare| Evidence["Acceptanță consumer"]
    Pair -. hash-uri și origine .-> Provenance["library-provenance.json"]
    Governance["Guvernanță artefact"] -. verifică octeți și limite .-> Pair
~~~

<details>
<summary><strong>Legendă — coordonate, octeți și dovada consumerului</strong></summary>

- POM-ul rădăcină selectează versiunile bibliotecilor folosite de acest reactor.
- Fișierele JAR/POM alăturate sunt artifactele rezolvate; testele consumerului
  folosesc acești octeți publicați, nu sursă de bibliotecă copiată.
- Proveniența identifică fiecare artifact prin coordonate și hash-uri și
  păstrează dovezile originii pachetului.
- Acceptanța consumerului dovedește numai integrările și scenariile locale care
  au fost executate. Nu afirmă că aici s-au rulat teste separate ale bibliotecii.

</details>

## Instalare și verificare

Rulează dintr-o copie TMP temporară, cu aceleași setări Maven și același
repository local ca în IntelliJ:

~~~powershell
mvn -B -ntp -N validate
mvn -B -ntp -DskipTests test-compile
~~~

Faza `validate` a POM-ului rădăcină instalează cele cinci perechi JAR/POM
alăturate. Reîncarcă POM-ul rădăcină în IntelliJ după selectarea aceluiași JDK,
fișier de setări și repository local. Nu adăuga modulele-sursă ale bibliotecilor
în reactor pentru a satisface importurile.

## Modificări și păstrarea dovezilor

1. Identifică dependența după grup, artifact și versiunea din POM-ul rădăcină.
2. Reprodu comportamentul în cel mai mic test afectat al consumerului J1.
3. Înregistrează comportamentul așteptat și observat, scenariul exact și
   valorile SHA-256 relevante pentru JAR/POM.
4. Păstrează constatările despre adaptor lângă modulul local și descrie
   comportamentul artifactului prin contractul publicat al bibliotecii.
5. Păstrează istoricul provenienței; adaugă dovezi de pachet numai dacă se
   schimbă octeții artifactului sau execuția de verificare.

Build-urile, cache-urile, rapoartele generate și notele temporare de verificare
rămân în staging-ul TMP deținut. Păstrează preview-ul implicit de 240 de
caractere și grupurile de aserțiuni component/E2E colapsabile și auditabile.
