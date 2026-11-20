# ATF Java — evaluarea AI față de codul curent

## Pe scurt

**Legături rapide:** [livrat versus propus](../../atf-validation/README.md#comparatorul-ai--contract-implementat) ·
[lacune](#gap-uri-rămase-pentru-evaluator) ·
[pregătire](#gradul-de-pregătire-față-de-codul-curent) · [verificări](#verificarea-comparatorului-livrat)

Acest document separă lacunele evaluatorului AI de gradul de pregătire al implementării.
Designul țintă și tiparele extrase din ATF1/.NET sunt în
[designul AI al proiectului](../ATF_AI_Evaluation_Design.md).

Restanțele AI nu intră în ordinea priorităților active din registrul comun.
Implementarea propusă păstrează limitele existente ale J1; absența unui modul nou nu
constituie o lacună sau o penalizare.

| Stare | Responsabil / limită |
|---|---|
| Implementat și testat | [Comparatorul AI din ATF Validation](../../atf-validation/README.md#comparatorul-ai--contract-implementat): valorile expected separate, comparații tipizate, eșec obligatoriu, dovezi ale apelului, fundamentare limitată la allowlist și instantaneu imuabil |
| Parțial | Guvernanța întregului ATF și domeniile `testChanges`; încă nu există o propunere/predare completă |
| Deschis | Ambiguitatea fundamentării, registrul rulărilor, ciclul de viață, revizuirea/starea, rubrica de business, raportarea și regresiile |
| Doar proiectat | compoziție agentică în modulele existente, controale și YAML conceptual; nu sunt opțiuni livrate |

O implementare se închide numai cu probă pozitivă și negativă la limita
potrivită. Mutarea unui document, adăugarea unei clase sau producerea unui raport
frumos nu ridică scorul implementării. Modificarea testelor de guvernanță urmează regula responsabilului din
[README-ul ATF Tests](../../atf-tests/README.md#guvernanța-schimbării-testelor).

<a id="gap-uri-rămase-pentru-evaluator"></a>
## Lacune rămase pentru evaluator

| ID | Cerință lipsă | Criteriu de închidere |
|---|---|---|
| `AI-EVAL-04` | clasificarea fundamentării ambigue | evaluatorul produce `AMBIGUOUS` cu motiv și referință; un caz negativ verifică verdictul și faptul că nu este promovat la `SUPPORTED` |
| `AI-EVAL-05` | revizuire/stare | acțiunea și tranzițiile sunt evaluate separat, inclusiv lipsa revizuirii și tranzițiile interzise |
| `AI-EVAL-06` | integrarea apelului/rezervei în ciclul de viață | rularea corelează modul real/mock/fallback, rezultatul și dovezile; lipsa dovezii ori clasificarea greșită eșuează explicit |
| `AI-EVAL-07` | catalog AI pentru business și rubrică per caz | reguli versionate și cazuri negative pentru intenție/revizuire/stare; eșecul obligatoriu prevalează indiferent de scor |
| `AI-EVAL-08` | registru de dovezi la nivel de rulare | indexul/corelarea/urma leagă apelul, fundamentarea și instantaneul; referințele lipsă sau străine sunt respinse |
| `AI-EVAL-09` | publicare și paritate de raportare | instantaneul AI este publicat fidel în Extent/fallback; testele compară aceeași semantică și diagnosticele diferențelor |
| `AI-EVAL-10` | diferența de regresie | comparator între instantanee versionate, cu cazuri noi/rezolvate/modificate și baseline incompatibil |
| `AI-EVAL-11` | guvernanța acoperirii AI | fiecare contract public nou are probe pozitive și negative; controlul identifică o probă eliminată |
| `AI-EVAL-12` | probă UI | browserul verifică revizuirea/starea/dovezile vizibile, corelate cu instantaneul și fără enunțuri duplicate |

<a id="gap-uri-de-design-pentru-workflow-ul-agentic"></a>
## Lacune de proiectare pentru fluxul de lucru agentic

| ID | Lacună | Prioritate | Dovadă necesară |
|---|---|---:|---|
| `AI-WORKFLOW-01` | lipsește o propunere de schimbare delimitată pentru agent | P1 | propunere validă/invalidă și diagnostice |
| `AI-WORKFLOW-02` | predarea nu poate fi citită de mașină | P1 | predare completă cu diff/gates/evidence |
| `AI-WORKFLOW-03` | lipsește validarea responsabilului/stratului/planului de probe pentru propunerea agentică | P1 | teste de guvernanță fail-closed, distincte de inventarul general al modulelor |
| `AI-WORKFLOW-04` | agentul nu are feedback compilat standard | P1 | feedback cu regula, faptul, remedierea și următoarea probă |
| `AI-WORKFLOW-05` | impactul documentar al agent-delta nu are un control executabil | P2 | probă de derivă documentară |
| `AI-WORKFLOW-06` | separarea scorurilor este proiectată, dar nu există două instantanee runtime | P1 | scorul fluxului de lucru și calitatea AI au verdicte distincte, fără calcul din textul agentului |
| `AI-WORKFLOW-07` | pretraining-ul este o îndrumare, nu un contract | P2 | fișă de baseline la rece cu limite |
| `AI-EVAL-13` | testul generat nu este verificat față de faptele observate | P1 | verificarea verdictului literal și asocierea dovezii |
| `AI-EVAL-15` | feedback-ul diferenței de regresie nu este o predare către agent | P2 | diferență semantică → următoarea probă |

Aceste cerințe descriu un design viitor, nu un modul sau o opțiune livrată.
Orice implementare rămâne o schimbare separată, cu responsabil, limită și probe
pozitive/negative proprii.

## Gradul de pregătire față de codul curent

Evaluarea de mai jos privește gradul de pregătire al implementării, nu scorul de
calitate al unui model și nici prioritățile registrului central. Contractul
implementat rămâne la responsabilul său; designul propus nu este dovadă de livrare.

<a id="scorecard-executiv"></a>
### Fișă executivă de evaluare

Implementarea rămâne **50/100**, conform evaluării existente: comparatorul,
proveniența apelului și instantaneul tipizat sunt livrate; clasificarea
`AMBIGUOUS`, registrul rulărilor, ciclul de viață AI și proiecția raportului
lipsesc. Această curățare editorială nu recalculează gradul de pregătire și nu
închide cerințe.

Contractul executabil este în [Validation](../../atf-validation/README.md#comparatorul-ai--contract-implementat);
infrastructura generică de [raportare](../ATF_Reporting_Contract.md),
[observare UI](../../atf-ui/README.md) și
[guvernanță](../../atf-tests/README.md) nu dovedește singură o integrare AI.
Tabelele de lacune de mai sus păstrează ID-ul, limita și proba lipsă; designul
și opțiunile conceptuale rămân în documentul de proiect.

### Plan de adoptare în ordinea corectă

1. AMBIGUOUS produs de evaluator + probă negativă
2. registru de dovezi la nivel de rulare + corelarea ciclului de viață
3. catalog de business versionat: intenție, revizuire/stare și eșec obligatoriu
4. checkpoint semantic UI/API/AI + publicare și paritate Extent/fallback
5. diferența de regresie + controale pentru propuneri/predări

Primul pilot folosește comparatorul existent, un caz cu intrare controlată și
două rezultate negative. Dovada minimă este la nivel de componentă/integrare;
browserul verifică ulterior efectul observabil. Furnizorul real are nevoie de
propria probă de integrare și nu înlocuiește matricea deterministă.

## Criteriu de închidere și recalibrare

- `AMBIGUOUS` trebuie produs de evaluator și probat negativ, nu doar declarat în enum.
- Registrul rulărilor leagă apelul, rezultatul, fundamentarea și instantaneul; referințele lipsă sau străine eșuează explicit.
- Publicarea proiectează instantaneul AI fără să recalculeze verdictul și păstrează paritatea Extent/fallback.
- Cazurile versionate verifică intenția, revizuirea/starea și eșecul obligatoriu; furnizorul real are propria probă de integrare.
- Fluxul de lucru agentic are propuneri/predări valide și invalide; nu își modifică singur porțile de verificare.
- La închidere, contractul demonstrat se promovează în README-ul responsabilului și dispare din acest registru de restanțe.

Ordinea domeniilor este în [registrul de constatări](README.md), iar contractele și
opțiunile proiectate sunt în [designul AI](../ATF_AI_Evaluation_Design.md).

## Verificarea comparatorului livrat

| Întrebare | Sursă și probă | Limită |
|---|---|---|
| Valorile expected și verdictul sunt independente de renderer? | [Evaluatorul](../../atf-validation/src/main/java/atf/validation/ai/AiEvaluationEvaluator.java) și [AiEvaluationEvaluatorTest](../../atf-validation/src/test/java/atf/validation/ai/AiEvaluationEvaluatorTest.java) | comparație locală; nu apelează un furnizor AI |
| Ce publică modulul? | [Contractul Validation](../../atf-validation/README.md#comparatorul-ai--contract-implementat) și [verificarea locală](../../atf-validation/README.md#verificare-rapidă) | instantaneul nu este încă o proiecție AI în Extent |
| Ce rămâne doar proiectat? | [Designul AI](../ATF_AI_Evaluation_Design.md) și criteriile din acest feedback | documentarea YAML-ului conceptual nu îl transformă într-o opțiune runtime |

Gradul de pregătire existent nu se recalculează printr-o îmbunătățire editorială.
Calitatea acestui README și completitudinea funcționalității propuse sunt
evaluări distincte, conform [rubricii documentare](../README_Atf_DocumentationBestPractices.md#rubrica-reviziei-editoriale).
