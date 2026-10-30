# ATF Reporting — artifactul binar consumat

## Pe scurt

Acest folder este repository-ul Maven local al bibliotecii Reporting.
JAR-ul și POM-ul se păstrează împreună; Maven rezolvă dependențele runtime
din POM, iar consumerii compilează împotriva versiunii declarate la rădăcină.
[Atlasul integrării](../docs/ATF_Reporting_Library_Atlas.md) oferă contextul
API/lifecycle și probele locale fără acces la sursa producătorului.

| Suprafață | Contract |
|---|---|
| artifact | `atf.api:atf-reporting`, JAR thin; versiunea este `atf.reporting.version` |
| layout | `atf/api/atf-reporting/<version>/`, conform repository-ului Maven |
| integritate | SHA-1 și SHA-256 pentru JAR și POM; [proveniență](../library-provenance.json) |
| sursă | întreținută în producătorul `demo2`; [manifestul intrărilor](../reporting-source-manifest.json) conține hash-uri |
| verificare | Reporting Tests consumă binarul; testele native și auditul sursei rulează la producător |

Manifestul intrărilor identifică sursele prin hash, fără să le livreze.
Absența unui sources JAR nu autorizează căutarea unui director vecin la runtime;
o problemă de implementare se reproduce și se repară în `demo2`, apoi se livrează versionat.

## Actualizare

- Construiți și verificați biblioteca într-un staging TMP al producătorului.
- Publicați o versiune nouă cu POM și checksum-uri; nu înlocuiți bytes pentru
  coordonate deja folosite de un consumer.
- Actualizați proprietatea versiunii și verificați CodeSource-ul, hash-ul și
  regresiile de randare înaintea sincronizării proiectului live.

[Ghidul consumerului J1](../docs/ATF_J1_Consumer.md) descrie importul IntelliJ,
gate-urile și relația dintre cele două proiecte.
