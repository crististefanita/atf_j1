# ATF J1 — registrul feedback-ului activ

## Pe scurt

Registrul urmărește lacune reproductibile în codul, documentația și acceptanța
consumerului J1. J1 consumă bibliotecile versionate din [`lib`](../../lib/README.md);
notează observațiile despre biblioteci prin coordonată, versiune, hash și
reproducere locală.

## Constatări active

| Constatare | Responsabil local | Dovada următoare |
|---|---|---|
| [J1-RECOVERY-RUNNER-TRACE-2026-11-21](ATF_Reporting_Feedback.md#j1-recovery-runner-trace-2026-11-21) | Proiecția raportului component Shop/AutoEx | verifică paritatea directorului de output și randarea trace-ului colapsat după IntelliJ; rapoartele Maven trec în Playwright |
| [J1-RECOVERY-TRACE-TYPING-2026-11-21](ATF_Code_Maintenance_Feedback.md#j1-recovery-trace-typing-2026-11-21) | Proiecția raportului recovery Shop/AutoEx | evaluează o limită tipizată trace–raport care elimină parsarea duplicată a textului, păstrând artifactele actuale |
| [J1-LAUNCH-CONTEXT-146](ATF_Reporting_Feedback.md#j1-launch-context-146) | Compoziția metadatelor Reporting | capturează rădăcina JUnit și selecția efectivă în metadatele de audit |
| [AUTOEX-UI-CART-CONFIRMATION-2026-11-19](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-ui-cart-confirmation-2026-11-19) | Journey-ul de cumpărare AutoEx | reproduce confirmarea coșului public cu un browser nou |

## Dovezi consumer livrate

| Contract | Înregistrare curentă |
|---|---|
| Claritatea scenariilor recovery și proprietatea selectorilor raportului | [Închis la 2026-11-21](ATF_Code_Maintenance_Feedback.md#oop-recovery-scenarios-2026-11-21); scenariile numite și tipizate, cu selectorii raportului limitați la teste, au trecut verificările component: Shop 6/6, AutoEx 5/5 și guvernanța Whole-ATF 191/191 în ambele ediții |
| Dovezi recovery în probele Extent locale ale consumerilor | [Închis la 2026-11-21](ATF_Reporting_Feedback.md#ui-recovery-audit-01--inspectable-attempts-in-extent); ambii consumatori verifică starea colapsată, expand-all, rândurile ordonate și redactarea în ambele ediții; fiecare probă construiește un pas sintetic cu rezultatul în titlu și nu demonstrează raportul generat de suită |
| Trace recovery în rapoartele JUnit reale ale componentelor | Acceptanță Maven trecută pentru Shop 6/6 și AutoEx 5/5 în ambele ediții; Playwright a verificat toate cele patru rapoarte reale, disclosure-urile independente, rândurile integrale ale încercărilor și redactarea. Paritatea output-ului IntelliJ rămâne activă mai sus. |
| Timpul de stabilizare al lookup-ului poștal Shop | [Închis la 2026-11-21](ATF_Consumer_Business_Test_Focus_Feedback.md#shop-billing-sanity); trei rulări cu câte 13 cazuri trec, iar race se măsoară la 494–524 ms față de bugetul de 1.100 ms, cu pornirea/navigarea browserului măsurată separat |
| Comparația catalogului AutoEx și dovezi Extent extensibile | [Închis la 2026-11-20](ATF_Consumer_Business_Test_Focus_Feedback.md#autoex-catalog-evidence-146); probele component și browser ale raportului controlat au trecut |
| Faptele documentației J1 și perimetrul bibliotecilor consumate | [Închis la 2026-11-20](ATF_Documentation_Feedback.md#doc-library-contracts-147); guvernanța curentă Whole-ATF este 191/191 în EN și RO; structurile documentelor păstrate și destinațiile linkurilor coincid |
| Overview root local-first și inventarul modulelor | [Închis la 2026-11-20](ATF_Documentation_Feedback.md#doc-root-overview-20261120); guvernanța curentă este 191/191 în EN și RO; structura edițiilor Markdown rămâne aliniată |
| Hărți de proiect și rapoarte independente de verificare | [Închis la 2026-11-21](ATF_Documentation_Feedback.md#doc-verification-record-scope-20261120); contractele durabile au rămas la owneri, iar guvernanța respinge rapoarte datate și nedatate |
| Revizia edițiilor documentației și comparația workspace-urilor | [Verificat la 2026-11-21](ATF_Documentation_Feedback.md#doc-edition-comparator-20261120); 50 de documente trec verificarea fixată prin commit și comparația worktree-urilor live, testele Node 11/11 acoperă normalizarea CRLF/LF și diferențele semantice, iar POM-urile bibliotecilor rămân identice octet cu octet |
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
