# ATF J1 — registrul lacunelor active

## Pe scurt

J1 deține testele AutoEx/Shop și integrarea Reporting JAR; implementarea și regresiile generice Reporting au owner în J0. Contractele livrate sunt la [ownerii documentați](../ATF_Context_Atlas.md), iar aici rămân numai lacunele și traseele de închidere.

[Workspace-urile](../ATF_Workspace_Synchronization.md) separă J0 sursă, J1 consumer web și J2 WIP pentru Kafka/mesagerie cu Setup în Podman. Un rezultat sau o prioritate nu se transferă automat între ele.

## Feedback pentru biblioteci

Constatările despre Core, Reporting, Console, Mock și Test Kit merg la ownerul J0. J1 păstrează problemele domeniului și acceptanța locală. [Ghidul bibliotecilor](../../lib/README.md)


## Top feedback-uri active

Scorurile sunt priorități, nu note de calitate sau procente de acoperire. Formula este impact /40 + lacună /30 + lucrabil /20 + datorie documentară /10; definiția și dovada rămân la owner.

| Criteriu J1 | Owner local | Probă rămasă |
|---|---|---|
| Identitatea lansării | [J1-LAUNCH-CONTEXT-146](ATF_Reporting_Feedback.md#j1-launch-context-146--record-the-actual-launch-scope) | suita reală în metadatele de audit |
| Dovada catalogului | [AUTOEX-CATALOG-EVIDENCE-146](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-catalog-evidence-146--bind-comparison-evidence-to-its-observed-exchange) | comparația atașată exclusiv schimbului HTTP observat |
| Lookup Shop | [SHOP-BILLING-SANITY](ATF_Consumer_Business_Test_Focus_Feedback.md#shop-billing-sanity--isolate-the-existing-uilookup-failures) | bugetul de readiness și oracolele curselor de lookup |
| Clasificarea Playwright | [GOV-UI-NATIVE-LAYER](ATF_Code_Maintenance_Feedback.md#gov-ui-native-layer--pre-existing-classification-disagreement) | clasificarea probei native și verdictul guvernanței |

[Reporting](ATF_Reporting_Feedback.md) și [Reporting Tests](ATF_Reporting_Tests_Feedback.md) trimit la backlog-ul canonic J0, cu prioritățile sale actuale. J1 păstrează reproducerea și acceptanța propriului JAR, fără o copie concurentă a criteriilor.

## Contracte și controale verificate

| Contract livrat | Autoritatea verificării |
|---|---|
| Pregătirea UI și capturile de context | [UI-FIRST-PAINT-144](../ATF_UI_First_Frame_Verification.md) |
| Căutare AutoEx cu GET și DOM proaspăt | [Verificarea AutoEx](../ATF_AutoEx_Search_Verification.md) |
| JSON strict și potrivire numerică | [Core J1](../ATF_Core_Consumer.md#document-json-complet-și-evidence-fidel) · [Mock J0](../../../atf_j0/atf-mock/README.md#json-strict-și-potrivire-numerică-exactă) |

[Contractul Core](../ATF_Core_Consumer.md) fixează sursa și probele native în J0,
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

<a id="cerințe-comune-preluate-din-j0--2026-11-02"></a>

## Frontiere de coordonare cu J0

| Owner în J1 | Criterii păstrate | Limită |
|---|---|---|
| [Test Kit](ATF_Common_Test_Support_Feedback.md) | Diagnosticul adoptării în Validation | codul, API-ul și contractul preview Unicode rămân la J0 |
| [Guvernanță documentară](ATF_Documentation_Feedback.md) | DOC-CONTEXT-DEPLOYMENT-111 (81) | guard semantic rămas; corecțiile editoriale verificate nu sunt relistate |
| [AI](README_AI_Evaluation.md) | ID-urile AI-EVAL/AI-WORKFLOW existente | design separat, fără funcționalitate nouă autorizată prin mutare |
| [Coordonare Android](ATF_Consumer_Reporting_Handoff_Feedback.md) | responsabilități device/business și dovezi J4 | oracolele și execuția Android rămân în J4; rendererul generic rămâne J0 |

Contractul preview Unicode se citește în [ghidul Test Kit](../../lib/README.TestKit.md). Dovezile Validation rămân specifice J1.
