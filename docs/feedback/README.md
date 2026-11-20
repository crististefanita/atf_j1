# ATF J1 — registrul feedback-ului activ

## Pe scurt

Registrul urmărește lacune reproductibile în codul, documentația și acceptanța
consumerului J1. J1 consumă bibliotecile versionate din [`lib`](../../lib/README.md);
notează observațiile despre biblioteci prin coordonată, versiune, hash și
reproducere locală.

## Constatări active

| Constatare | Responsabil local | Dovada următoare |
|---|---|---|
| [UI-RECOVERY-AUDIT-01](ATF_Reporting_Feedback.md#ui-recovery-audit-01--inspectable-attempts-in-extent) | Dovezile recovery J1 și acceptanța raportului | randează cazurile recovered și terminale; verifică un singur grup restrâns care se extinde complet și rămâne redactat |
| [J1-LAUNCH-CONTEXT-146](ATF_Reporting_Feedback.md#j1-launch-context-146) | Compoziția metadatelor Reporting | capturează rădăcina JUnit și selecția efectivă în metadatele de audit |
| [DOC-VERIFICATION-RECORD-SCOPE-20261120](ATF_Documentation_Feedback.md#doc-verification-record-scope-20261120) | Guvernanța artefactelor documentației | stabilește dacă înregistrările independente de verificare rămase sunt contracte durabile ale modulelor; altfel, mută dovezile rulărilor în afara documentației întreținute și respinge fixture-urile fără dată |
| [SHOP-BILLING-SANITY](ATF_Consumer_Business_Test_Focus_Feedback.md#shop-billing-sanity) | Journey-ul de billing Shop | separă pornirea browserului de timpul de stabilizare a lookup-ului |
| [AUTOEX-UI-CART-CONFIRMATION-2026-11-19](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-ui-cart-confirmation-2026-11-19) | Journey-ul de cumpărare AutoEx | reproduce confirmarea coșului public cu un browser nou |

## Dovezi consumer livrate

| Contract | Înregistrare curentă |
|---|---|
| Comparația catalogului AutoEx și dovezi Extent extensibile | [Închis la 2026-11-20](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-catalog-evidence-146); probele component și browser ale raportului controlat au trecut |
| Faptele documentației J1 și perimetrul bibliotecilor consumate | [Închis la 2026-11-20](ATF_Documentation_Feedback.md#doc-library-contracts-147); ultima guvernanță Whole-ATF: 190/190 în EN și RO; structura și destinațiile linkurilor coincid în toate cele 52 de documente Markdown |
| Overview root local-first și inventarul modulelor | [Închis la 2026-11-20](ATF_Documentation_Feedback.md#doc-root-overview-20261120); ultima guvernanță: 190/190 în EN și RO; structura edițiilor Markdown rămâne aliniată |
| Hărți de proiect și instantanee de verificare datate sau versionate după bibliotecă | [Implementat la 2026-11-20](ATF_Documentation_Feedback.md#doc-context-artifact-governance-20261120); întrebarea despre înregistrările nedatate rămase este urmărită separat mai sus |
| Revizia edițiilor documentației și comparația workspace-urilor | [Verificat la 2026-11-21](ATF_Documentation_Feedback.md#doc-edition-comparator-20261120); guvernanță focusată 18/18 în EN și RO, testele Node 10/10 și comparație curată în TMP pentru 52 de documente |
| Politica dovezilor screenshot | [Contractul de captură](../ATF_Evidence_Capture_Contract.md), ghidul modulului UI și testele native de geometrie browser definesc comportamentul curent |
| Contractele consumerului | README-urile modulelor și testele executabile definesc comportamentul curent |

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
