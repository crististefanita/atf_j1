# ATF J1 — registrul lacunelor active

## Pe scurt

J1 deține testele AutoEx/Shop și integrarea Reporting JAR; implementarea și regresiile generice Reporting au owner în J0. Contractele livrate sunt la [ownerii documentați](../ATF_Context_Atlas.md), iar aici rămân numai lacunele și traseele de închidere.

[Workspace-urile](../ATF_Workspace_Synchronization.md) separă J0 sursă, J1 consumer web și J2 WIP pentru Kafka/mesagerie cu Setup în Podman. Un rezultat sau o prioritate nu se transferă automat între ele.

## Top feedback-uri active

Scorurile sunt priorități, nu note de calitate sau procente de acoperire. Formula este impact /40 + lacună /30 + lucrabil /20 + datorie documentară /10; definiția și dovada rămân la owner.

| Boundary consumat | Owner canonic | Acceptanță locală |
|---|---|---|
| Core C1 | [Core J0](../ATF_Core_Consumer.md#document-json-complet-și-evidence-fidel) | adopția bibliotecii nu închide documentul JSON complet sau fidelitatea numerică |
| Reporting J8 | [Reporting J0](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md) | verificarea hash-ului și adaptorului rămâne distinctă de defectele generice ale rendererului |
| Mock comun | [Mock J0](../../../atf_j0/docs/feedback/ATF_Mock_Feedback.md) | guard-ul candidatului J2 și exactitatea numerică cer reconciliere și probe J1 proprii |

[Reporting](ATF_Reporting_Feedback.md) și [Reporting Tests](ATF_Reporting_Tests_Feedback.md) trimit la backlog-ul canonic J0, cu prioritățile sale actuale. J1 păstrează reproducerea și acceptanța propriului JAR, fără o copie concurentă a criteriilor.

## Contracte și controale verificate

[Contractul Core C1](../ATF_Core_Consumer.md) fixează sursa și nativele în J0,
consumul prin JAR în J1/J2 și acceptanțele distincte. Criteriile rezolvate ale
distribuției și probele deterministe de timeout sunt în documentația ownerului
și în baseline; nu rămân defecte active în feedback.

[Atlasul](../ATF_Context_Atlas.md), [arhitectura](../ATF_Architecture.md) și [ghidul de testare](../ATF_Testing_Guide.md) dețin contractele stabile și probele de pornire. Controalele tehnice sunt în [baseline](../ATF_Test_Changes_Baseline.md#controale-tehnice-verificate), cu bazele reale ale proiectului.

## Design AI — evaluare separată

[Evaluarea AI](README_AI_Evaluation.md) separă comparatorul existent de designul agentic propus. Backlog-ul de design nu extinde incrementul de mentenanță, iar [nota editorială](../README_Atf_DocumentationBestPractices.md#rubrica-reviziei-editoriale) nu certifică readiness-ul implementării.

## Păstrare, promovare și verificare

| Situație | Acțiune și autoritate |
|---|---|
| contract deja implementat | păstrează regula la README-ul ownerului; indexul trimite la ea prin [atlas](../ATF_Context_Atlas.md) |
| lacună reală | trigger, observație, owner, prioritate și criteriu de închidere în feedback-ul domeniului |
| rezultat parțial | precizează limita și restanța; un guard verde nu închide alt scenariu |
| control Git verificat | [baseline](../ATF_Test_Changes_Baseline.md); nu se rebazează ori prelungește implicit |

[Protocolul documentar](../README_Atf_DocumentationBestPractices.md#păstrarea-și-ștergerea-feedback-ului) și [build-ul în TMP](../ATF_Build_And_Execution_Safety.md) guvernează fiecare increment. Criteriul se elimină numai după probă și promovarea contractului stabil.
