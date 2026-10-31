# ATF Reporting — distribuția binară inclusă

## Pe scurt

JAR-ul thin, POM-ul și checksum-urile stau direct în acest folder.
[POM-ul rădăcină](../pom.xml) instalează perechea în cache-ul Maven în faza
`validate`; consumerii păstrează dependențele Maven normale și tranzitivele reale.

| Suprafață | Contract |
|---|---|
| artifact | `atf.api:atf-reporting`; versiunea unică este `atf.reporting.version` |
| layout | `atf-reporting-<version>.jar` și `.pom`, direct în `lib` |
| inventar | numai ultima versiune: un JAR și un POM, cu checksum-urile lor; versiunile retrase se arhivează în TMP |
| bootstrap | `mvn -B -N validate` înaintea primului import IntelliJ; automat la build-ul reactorului |
| cache | repository-ul configurat Maven; structura coordonatelor rămâne numai acolo |
| integritate | SHA-1 și SHA-256 pentru JAR/POM; [proveniență](../library-provenance.json) |
| sursă | întreținută în `demo2`; [manifestul intrărilor](../reporting-source-manifest.json) identifică sursele build-ului |
| verificare | gate extern Reporting Tests pe JAR în TMP; toate testele sunt întreținute la producător |

## Actualizare

- Construiți și verificați biblioteca într-un staging TMP al producătorului.
- Includeți o versiune nouă cu POM și checksum-uri; coordonatele publicate păstrează
  aceiași bytes. Bootstrap-ul nu generează un POM minimal pentru a înlocui tranzitivele.
- Actualizați proprietatea versiunii, executați bootstrap-ul și Reload Maven în IDE.
- Arhivați versiunea precedentă în TMP; folderul proiectului nu păstrează versiuni paralele sau ierarhii de coordonate.
- Verificați CodeSource-ul, hash-ul și regresiile de randare înaintea promovării.

[Ghidul J1](../docs/ATF_J1_Consumer.md) descrie Maven, IntelliJ și validarea completă.
