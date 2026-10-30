# ATF J1 — gate-uri și boundary-uri ale consumului binar

## Pe scurt

J1 deține schimbările comune non-Reporting; biblioteca și regresiile ei
generice au owner în `demo2`. [Procesul J1](../ATF_J1_Consumer.md) fixează
sincronizarea, iar [atlasul JAR](../ATF_Reporting_Library_Atlas.md) permite integrarea fără sursă.

Linkurile locale către Reporting și inventarul documentar au fost portate.
O corecție editorială nu adaptează însă gate-urile executabile și nu autorizează
retragerea unui modul; mai jos rămân criteriile tehnice neînchise.

## REPORT-J1-VALIDATION-01 — gate-uri pentru consumerul binar

**Prioritate: 84/100** = impact 32 + lacună 25 + lucrabil 18 + documentare 9.
**Owner:** guvernanța J1; regulile și probele native Reporting rămân la producător.

| Gap delimitat | Criteriu de închidere |
|---|---|
| inventarul producătorului este presupus local | definiți explicit modulele sursă J1 și biblioteca externă din declarațiile Maven; nu creați un modul Reporting gol |
| ținta `@DocumentationTarget("atf-reporting/README.md")` lipsește | declarați ținte reale ale consumerului și păstrați gate-ul producătorului cu ținta sa proprie |
| mutantul factual caută literal 12 → 13 | parametrizați proba după declarația proiectului; numărul greșit trebuie refuzat și la 11 module, apoi la 10 dacă retragerea este acceptată |
| Git J1 nu are încă `HEAD` | stabiliți baseline-ul real și istoria de review; controalele copiate din producător nu devin automat eligibile |

- [ ] Inventariați toate guard-urile care presupun sursa Reporting, 12 module
      ori istoricul producătorului; clasificați-le pe perimetrul protejat.
- [ ] Probați consumerul valid și controale negative independente: modul lipsă,
      binar/versiune greșite, țintă documentară absentă și scope Kit alterat.
- [ ] Păstrați regulile producătorului și toate oracolele; fără skip, exceptare
      globală, directoare fictive sau commit TMP care ascunde diferența de review.
- [ ] Rulați gate-ul J1 în staging cu istoric valid și includeți defectele
      native la gate-ul producătorului, fără importarea sursei Reporting în consumer.

**Închidere:** fiecare perimetru acceptă numai configurația sa și refuză
mutanții focalizați. Un gate parțial verde ori documentația corectată nu
certifică whole-ATF sau controlul Git.

## J1-REPORTING-TESTS-02 — retragere cu dovadă păstrată

**Prioritate: 72/100** = impact 27 + lacună 20 + lucrabil 17 + documentare 8.
**Owner:** decizia de reactor J1; regresiile generice sunt canonice în `demo2`.

Modulul există încă în POM. Primele căutări nu arată importuri ale claselor
sale în Shop/AutoEx, dar guvernanța, selecțiile și documentația îl referă;
această observație statică nu este acceptanță de eliminare.

- [ ] Stabiliți destinația fiecărei categorii: native și regresii generice
      Reporting la producător; integrarea adaptorului în Shop/AutoEx; reguli
      comune cross-module în guvernanță. Nu mutați teste numai după numele clasei.
- [ ] Verificați regresiile canonice în `demo2` pe versiunea destinată J1 și
      păstrați probele negative, fallback, navigare, tabele și tastatură.
- [ ] Păstrați în J1 acceptanța binară și integrarea component/E2E a ambilor
      consumeri, individual și prin suită; o probă a JAR-ului nu verifică mapperul aplicației.
- [ ] După decizia explicită de retragere, reconciliați POM, descoperire,
      guvernanță și linkuri. Dovediți cele 10 module sursă rezultate, fără a
      distribui testele producătorului sau a crea un modul nou compensator.

**Închidere:** J1 funcționează fără modul, acceptanța păstrată refuză defectele
relevante, iar producătorul execută întregul inventar canonic al bibliotecii.
Până atunci, documentația descrie 11 module și o retragere propusă.

## Pachetul documentar și compatibilitatea bibliotecii

Atlasul local explică integrarea, nu înlocuiește livrarea documentației unei
versiuni susținute. [Reporting](ATF_Reporting_Feedback.md) deține documentele
de adopție, suprafața API și compatibilitatea; [Reporting Tests](ATF_Reporting_Tests_Feedback.md) deține oracolele neutre.

Un upgrade cere JAR/POM/checksum-uri/proveniență coerente, exemplu compilat
fără sursă, CodeSource verificat și acceptanța adaptoarelor. Un hash al surselor
ori compatibilitatea unor simboluri nu certifică întregul API sau comportamentul.
