# ATF — prevenirea regresiei afirmațiilor factuale

## Pe scurt

Descrierile geometriei disting acum configurația publicată, viewport-ul CSS,
DPR și pixelii capturați. Contractul este la [UI](../../atf-ui/README.md#geometria-nativă-a-contextului),
iar [regulile editoriale](../README_Atf_DocumentationBestPractices.md) rămân normative.

## DOC-REPORTING-FACTUAL-36 — guard pentru afirmații selectate

**Prioritate: 38/100** = impact 14 + lacună 8 + lucrabil 11 + documentare 5.
**Owner:** ATF Tests pentru guard neutru; Reporting Tests pentru declarația și
proba pe care documentul său le numește.

Guard-ul factual curent compară modulele și matricea Kit cu POM-urile reale.
Corectarea manuală a prozei despre geometrie nu îl extinde automat la acea
afirmație; snapshot-ul metadata rămâne separat de măsurătoarea nativă.

- [ ] Selectați numai afirmații stabile, cu sursă executabilă independentă:
      configurație publicată versus geometrie măsurată, default versus exemplu configurat.
- [ ] Mutantul schimbă numai documentul și trebuie refuzat cu document,
      fragment, valoare declarată/observată și sursă; fără registru paralel de adevăr.
- [ ] Pentru ambiguități fără reprezentare typed păstrați review-ul manual;
      ancorele, regex-urile și un guard semantic restrâns nu certifică sensul întregii proze.

**Închidere:** contradicțiile selectate sunt refuzate independent, iar limita
automatizării este explicită. Nu cereți un parser universal și nu relaxați
guvernanța structurală pentru a accepta un document fals.
