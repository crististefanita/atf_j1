# Biblioteci ATF

## Pe scurt

Java 21 și Maven; cinci JAR-uri subțiri, cu dependențele tranzitive declarate
în fiecare POM. Aceste ghiduri descriu artifactele și contractele observabile
fără a cere sursa bibliotecilor.

| Ghid | Artifact, grupul `atf.api` | Utilizare |
|---|---|---|
| [Core](README.Core.md) | `atf-core` | HTTP, configurație, JSON și lifecycle. |
| [Reporting](README.Reporting.md) | `atf-reporting` | Extent/fallback și dovezi auditabile. |
| [Console](README.Console.md) | `atf-console` | Diagnostic Maven și IntelliJ. |
| [Mock](README.Mock.md) | `atf-mock` | Componente și contracte HTTP offline. |
| [Test Kit](README.TestKit.md) | `atf-test-kit` | Clasificare JUnit, comparații și suport pentru teste. |

## Integrare

1. Într-o copie TMP a unui consumer ATF, `mvn -B -ntp -N validate` instalează
   perechile din `lib` în repository-ul Maven selectat.
2. Declară dependența în modulul apelant; coordonata și versiunea sunt în POM-ul
   alăturat JAR-ului.
3. IntelliJ folosește același JDK, aceleași setări Maven și repository local;
   reîncarcă POM-ul rădăcină după selectarea lor.

<details>
<summary>Consumer nou fără reactorul ATF</summary>

Copiază perechile JAR/POM în `lib` și instalează-le din proiectul nou, în TMP,
cu Maven Install Plugin, indicând fiecare JAR și POM-ul alăturat.

De exemplu, declară `atf.api:atf-reporting:<versiunea din POM>` ca dependență.
Folosește scope-ul `compile` pentru suport în `src/main` și `test` numai în
`src/test`; Maven rezolvă dependențele tranzitive.

</details>

## Identitate și verificare

- O singură pereche JAR/POM per bibliotecă, indexul și cinci ghiduri, direct
  în `lib`.
- POM-ul rădăcină `pom.xml` selectează versiunile; `library-provenance.json`
  înregistrează coordonatele, hash-urile și proveniența pachetelor.
- Guvernanța și acceptanța rulează în TMP; fiecare consumer își verifică profilul
  de dependențe și classpath-ul propriu.

<a id="maintenance-and-feedback"></a>

## Mentenanță și feedback

Înregistrează o constatare despre bibliotecă după coordonata Maven, versiune și
valorile SHA-256 relevante ale JAR/POM. Include un repro minim și comportamentul
așteptat/observat. Păstrează constatările despre adaptor lângă modulul consumer
afectat; descrie comportamentul bibliotecii comune prin contractul publicat
relevant, fără a duplica în README-ul consumerului criterii nerezolvate despre
bibliotecă.

<a id="shared-library-source"></a>

> **Sursa bibliotecilor:** [Catalog · ATF J0](https://github.com/crististefanita/atf_j0/blob/HEAD/lib/README.md).
> POM-ul, proveniența și testele acestui proiect stabilesc versiunea consumată și acceptanța locală.
