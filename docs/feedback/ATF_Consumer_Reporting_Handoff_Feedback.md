# Dovezi despre consumul Reporting — reproducere locală

## Pe scurt

J1 înregistrează ceea ce observă propriile teste folosind un JAR Reporting
selectat. Pagina păstrează local dovezile reproducerii și identifică biblioteca
prin coordonată, versiune și hash.

## Traseul dovezilor

```mermaid
flowchart LR
    Test["Test component J1"] -->|folosește| Jar["JAR Reporting selectat"]
    Jar -->|randează| Html["raport Extent local"]
    Html -->|inspectat pentru| Finding["scenariu, pas, aserțiune"]
    Finding -->|înregistrat cu| Identity["coordonată + versiune + hash"]
    Identity -->|susține| Feedback["intrare feedback J1"]
```

<details>
<summary><strong>Legendă — dovezi reproductibile ale consumerului</strong></summary>

- Testul component deține scenariul local și valorile așteptate.
- Coordonata și hash-ul JAR identifică octeții bibliotecii folosiți de rulare.
- Raportul HTML arată dacă pașii și aserțiunile se extind și navighează corect.
- Constatarea rămâne atașată codului J1 sau unei intrări din feedback-ul J1.

</details>
