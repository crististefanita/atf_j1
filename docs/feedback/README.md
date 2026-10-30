# ATF Java — registrul cerințelor rămase

Pentru acest deployment, [feedback-ul J1](ATF_J1_Consumer_Feedback.md)
deține gate-urile consumerului și retragerea propusă a Reporting Tests.
Reporting și testele sale generice au owner în `demo2`; topul lor se consultă separat de lucrările comune pornite în J1.

Registrul indexează criteriile neîndeplinite. Contractele stabile aparțin
README-ului ownerului; controalele verificate aparțin baseline-ului testelor.

## Pe scurt

- UI livrează politica BE read-only, continuitatea selectului, observerii și geometria iframe; sesiunea browser păstrează un criteriu distinct de cauză/rollback.
- Reporting verifică sursa izolată, JAR-ul fizic și refuzul constructorului; [pregătirea pentru release](ATF_Reporting_Feedback.md) păstrează API-ul, distribuția și portabilitatea. [Reporting Tests](ATF_Reporting_Tests_Feedback.md) deține oracolele per scenariu și containment-ul fallback.
- Consumerii corelează lookup-ul Shop cu răspunsul și DOM-ul curente; sincronizează autofill-ul și confirmă produsul după click; Account/Cart/Purchase au negative UI/UI_API individual/prin suită; ramurile Cart și eroarea generică după PASS cer acoperire distinctă înaintea reducerii selecției.
- Core prevalidează sursele și părinții destinației fără scriere; [atomicitatea per fișier](../../atf-core/README.md#manifest-și-promovare-guarded) rămâne explicită.
- Documentația verifică linkurile și fragmentele în README-uri și docs; [afirmațiile factuale critice](ATF_Documentation_Feedback.md) cer comparație independentă cu sursa executabilă.
- Validation, Console, Mock și Debug UI au contractele de boundary și probele la owner; registrul păstrează numai lacunele deschise.

În J1 există 11 module sursă și Reporting ca bibliotecă externă.
[Ownership-ul și direcțiile sync](../ATF_J1_Consumer.md) sunt explicite;
îmbunătățim codul comun din J1:
responsabilități, stare, erori, duplicări și documentație. Flow-urile E2E Shop și
AutoEx pot fi completate în modulele lor până la rezultatul business declarat.

Propunerile de funcționalități noi și workflow-ul AI rămân în backlog;
scorul lor nu extinde scope-ul incrementului. Politica BE read-only livrată
explicit are contract la ownerii UI, Kit și consumeri.

## Top feedback-uri active

Scorul este prioritate de intervenție, nu notă a codului sau procent de acoperire.
Formula comună este impact /40 + lacună /30 + lucrabil /20 + datorie documentară /10.
Un document cu mai multe gap-uri primește scorul celui mai urgent, nu suma lor.

| Ordine | Feedback / owner | Scor /100 | Criteriul care decide ordinea |
|---:|---|---:|---|
| 1 | [J1 — guvernanța consumerului](ATF_J1_Consumer_Feedback.md#report-j1-validation-01--gate-uri-pentru-consumerul-binar) | **84** = 32 + 25 + 18 + 9 | inventar sursă/binar, ținte documentare reale, probe negative și baseline Git |
| 2 | [Consumer — oracole de acceptanță](ATF_Consumer_Business_Test_Focus_Feedback.md) | **74** = 30 + 21 + 17 + 6 | verdict și captură asociate fiecărei invocări; ramurile condiționale rămân separate (39) |
| 3 | [UI — lifecycle browser](ATF_UI_Backend_Guided_Recovery_Feedback.md) | **72** = 30 + 20 + 16 + 6 | păstrarea cauzei la achiziție și erori simultane de close |
| 4 | [Documentație — guard factual selectiv](ATF_Documentation_Feedback.md) | **38** = 14 + 8 + 11 + 5 | prevenirea automată a contradicțiilor selectate; corecția prozei nu extinde guard-ul |

- Reporting are backlog separat, întreținut în `demo2`: [API/release](ATF_Reporting_Feedback.md)
  cu prioritate 81 și [oracolele testelor](ATF_Reporting_Tests_Feedback.md) cu
  prioritate 80. J1 trimite reproducerea și verifică livrarea, fără owner concurent.
- [Retragerea Reporting Tests](ATF_J1_Consumer_Feedback.md#j1-reporting-tests-02--retragere-cu-dovadă-păstrată)
  are criteriu de migrare separat, cu prioritate 72. Este o propunere de reactor,
  nu un incident și nici o eliminare executată.

O componentă verde confirmă criteriul ei; închiderea unui gap cere întreaga
probă declarată la owner. Evaluarea calității folosește separat
[rubrica documentației](../README_Atf_DocumentationBestPractices.md#rubrica-reviziei-editoriale).

În perimetrul fără Reporting rămân [oracolele consumerilor](ATF_Consumer_Business_Test_Focus_Feedback.md),
ramurile business condiționale și [lifecycle-ul UI](ATF_UI_Backend_Guided_Recovery_Feedback.md). Compoziția readiness, anularea deterministă,
achiziția parțială și comparația document/POM au contracte și probe la owneri.

- Proiecția typed `UI-RECOVERY-AUDIT-01` are owner Reporting.
- Prevenirea factuală selectivă are ownerul neutru ATF Tests și declarații la ownerul documentului.

## Traiectoria modulelor peste 91

Ținta privește codul livrat, conform [rubricii de cod](../README_Atf_CodeBestPractices.md#rubrica-reevaluării-modulelor).
O notă editorială mare nu compensează un false PASS, o cauză pierdută sau un
scope contaminat; reevaluarea urmează probei și sincronizării live.

| Modul | Contract demonstrat sau criteriu rămas | Owner |
|---|---|---|
| Core | sursele și părinții sunt prevalidate fără scriere; execuția rămâne atomică per fișier | [promovarea guarded](../../atf-core/README.md#manifest-și-promovare-guarded) |
| Mock | query-ul și constrângerile distincte nu se reasociază printr-o etichetă ambiguă | [asocierea exactă în adaptor](../../atf-reporting-tests/README.md#fidelitatea-query-ului-și-asocierea-mock) |
| Validation | cauza inițială supraviețuiește close-ului; refuzul one-shot precede callback-ul | [cauza și protocolul](../../atf-validation/README.md#cauza-primară-și-refuzul-înaintea-evaluării) |
| Console | copilul restaurează contextul/sink-ul părintelui la succes, FAIL și SKIP | [scope imbricat](../../atf-console/README.md#restaurarea-contextelor-imbricate) |
| Reporting | source/JAR gate, constructor cu rollback, lifecycle declarat, query privat și ordinalul dovezii originale; API-ul susținut și release păstrează criterii distincte | [criteriile Reporting](ATF_Reporting_Feedback.md) |
| UI | pointerul iframe folosește context nativ; inspectorul, selectul și observerii își păstrează identitatea, activarea unică și cleanup-ul complet; proiecția typed rămâne la Reporting | [sincronizare](ATF_UI_Backend_Guided_Recovery_Feedback.md) și [geometria livrată](../../atf-ui/README.md#geometria-nativă-a-contextului) |
| Debug UI | close se termină bounded și cu request incomplet; rămâne read-only loopback | [shutdown](../../atf-debug-ui/README.md#închiderea-conexiunilor-deținute) |
| Shop | marcaje numeric/typed, Confirm și billing sincronizat; rollback-ul achiziției parțiale păstrează cauza și închide contextul nativ o dată | [criterii consumer](ATF_Consumer_Business_Test_Focus_Feedback.md) |
| AutoEx | identitate, produs și factură confirmate bounded; negativele Account/Cart/Purchase păstrează cauza, captura și cleanup-ul; acoperirea ramurilor rămase condiționează reducerea selecției | [gate și readiness](../../atf-test-auto-ex/README.md#gate-ul-coșului-și-ownerul-contului) și [criterii consumer](ATF_Consumer_Business_Test_Focus_Feedback.md) |
| Reporting Tests | oracole independente pentru query, randare, backlink, achiziție parțială și cleanup direct/delegat | [contractele și probele](../../atf-reporting-tests/README.md#erori-simultane-la-publicare-și-close) |
| Test Kit | revalidarea protocolului JUnit și a binding-ului; reutilizare neutră numai după două utilizări reale | [limita reutilizării](../../atf-test-kit/README.md) |
| ATF Tests | păstrarea gate-urilor și a probelor lor negative; nicio relaxare pentru lucrările de mai sus | [guvernanța ownerului](../../atf-tests/README.md) |

Kit și guvernanța nu primesc defecte inventate pentru a completa tabelul.
Fiecare modul se revede pe propriul boundary; fără module noi, clase auxiliare
fără motiv semantic sau funcționalități suplimentare pentru punctaj.

## Contracte și controale verificate

Regulile demonstrate sunt la README-ul ownerului și în
[ghidul de testare](../ATF_Testing_Guide.md); registrul de feedback nu dublează
inventarul lucrărilor închise. Controalele Git verificate sunt în
[baseline](../ATF_Test_Changes_Baseline.md#controale-tehnice-verificate),
iar cele `ACTIVE` rămân lângă owner până la proba lor completă.

## Design AI — evaluare separată

[Feedback-ul AI](README_AI_Evaluation.md) separă comparatorul implementat de
publication path, run ledger și workflow-ul propus. Readiness-ul său nu este
prioritatea operațională de mai sus și nu se recalibrează editorial.

## Păstrare, promovare și verificare

| Moment | Actualizarea cerută |
|---|---|
| înaintea refactorizării | confruntați feedback-ul cu sursa live; fixați triggerul, ownerul, observația și proba de închidere |
| în timpul incrementului | reconciliați imediat constatarea nouă, limita probei ori schimbarea de scope; separați analiza statică de reproducere |
| înaintea promovării | revalidați sursele live, probele și linkurile; un criteriu parțial rămâne deschis, cu restanța exactă |
| după acceptare | promovați contractul stabil la owner și eliminați numai criteriul demonstrat, cu controalele și ancorele reconciliate |

- Regula stabilă se promovează la owner înainte de eliminarea criteriului închis.
- Fișierul fără gap-uri se elimină după reconcilierea controalelor și linkurilor;
  țintele `@DocumentationTarget` rămân protejate.
- README-urile de modul rămân documentație de utilizare chiar fără ToDo-uri.

Protocolul este în [bunele practici documentare](../README_Atf_DocumentationBestPractices.md#păstrarea-și-ștergerea-feedback-ului).
Verificările respectă [staging-ul și sync-ul sigur](../ATF_Build_And_Execution_Safety.md).

## Cum verifici un criteriu

| Pas | Sursă | Rezultat cerut |
|---|---|---|
| Delimitează problema | secțiunea feedback a ownerului | trigger, observație, expected independent și boundary |
| Verifică implementarea | README owner → cod/POM → test nominal și negativ | criteriul este demonstrat, nu doar descris |
| Reconciliază controlul tehnic | [baseline testChanges](../ATF_Test_Changes_Baseline.md) și [gate-ul Git](../../atf-tests/src/test/java/atf/api/tests/governance/changes/TestChangeGovernanceTest.java) | control eligibil pentru diferența și baza de review curente |
| Închide | [protocolul documentar](../README_Atf_DocumentationBestPractices.md#păstrarea-și-ștergerea-feedback-ului) | regula stabilă este la owner, iar gap-ul închis nu rămâne backlog |

Scorul de prioritate nu este nota README-ului și nu certifică implementarea.
Designurile AI păstrează [evaluarea lor separată](README_AI_Evaluation.md),
fără a extinde incrementul de mentenanță al codului existent.
