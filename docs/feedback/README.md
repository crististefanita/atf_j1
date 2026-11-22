# ATF J1 — registrul feedback-ului activ

## Pe scurt

Registrul urmărește lacune reproductibile în codul, documentația și acceptanța
consumerului J1. J1 consumă bibliotecile versionate din [`lib`](../../lib/README.md);
notează observațiile despre biblioteci prin coordonată, versiune, hash și
reproducere locală.

## Constatări active

| Constatare | Responsabil local | Dovada următoare |
|---|---|---|
| [AUTOEX-UI-CART-CONFIRMATION-2026-11-19](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-ui-cart-confirmation-2026-11-19) | Journey-ul de cumpărare AutoEx | reproduce confirmarea coșului public cu un browser nou |
| [J1-RECOVERY-IDE-REPORT-PARITY-2026-11-22](ATF_Reporting_Feedback.md#j1-recovery-ide-report-parity-2026-11-22) | Configurarea lansării consumerilor | rulează componentele recovery din IntelliJ și validează artefactele emise cu acceptanța browser existentă |

## Backlog de proiectare AI amânat

[Lacunele evaluatorului și workflow-ului AI](README_AI_Evaluation.md) rămân
criterii de proiectare deschise, în afara ordinii curente de mentenanță. Comparatorul
livrat este separat de registrul, lifecycle-ul și proiecția în raport propuse.

## Responsabilii contractelor curente

| Contract | Responsabil |
|---|---|
| Identitatea bibliotecilor și acceptanța locală | [Catalogul bibliotecilor](../../lib/README.md) și testele modulelor |
| Reporting și dovezile lansării | [Ghidul testării](../ATF_Testing_Guide.md) și README-urile modulelor consumer |
| Dovezi screenshot complete | [Contractul de captură](../ATF_Evidence_Capture_Contract.md) |
| Documentația și edițiile de limbă | [Standardul documentar](../README_Atf_DocumentationBestPractices.md) |

## Înregistrează o observație despre bibliotecă

1. Reprodu-o în cel mai mic test J1 afectat, folosind JAR-ul fizic selectat.
2. Notează coordonata Maven, versiunea, hash-urile JAR/POM, comportamentul
   așteptat și observat și calea raportului, dacă este relevantă.
3. Păstrează politica aplicației și constatările adaptorului lângă modulul local.
   Descrie comportamentul bibliotecii prin ghidul publicat și dovezile inspectabile
   de J1.

## Închidere și istoric

Mută regula stabilă în README-ul relevant și elimină o constatare numai după ce
trece dovada de închidere. Controalele verificate ale testelor și bazele lor Git
rămân în [baseline](../ATF_Test_Changes_Baseline.md); nu deduce o rulare nouă din
copierea unui director sau redenumirea unui artefact.
