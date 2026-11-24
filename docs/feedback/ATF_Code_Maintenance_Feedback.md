# Feedback pentru mentenanța codului

## Pe scurt

Registrul păstrează constatările active și închise. Regulile durabile rămân la ownerii modulelor și în
guvernanța executabilă; dovezile istorice rămân în istoricul Git.

<a id="gov-package-cohesion-2026-11-19"></a>
## GOV-PACKAGE-COHESION-2026-11-19 — coeziunea pachetelor

**Stare: Verificat și închis.** Verificările structurii documentației au propriul
pachet de responsabilitate. Excepția Playwright revizuită păstrează boundary-ul
public; limita generală a pachetelor și fixture-urile negative rămân active.

[Regulile de cod](../README_Atf_CodeBestPractices.md) și
[guvernanța](../../atf-tests/README.md) dețin controalele păstrate.

<a id="oop-recovery-scenarios-2026-11-21"></a>
## OOP-RECOVERY-SCENARIOS-2026-11-21 — scenarii tipizate și ownership-ul selectorilor

**Stare: Verificat și închis.** Ambele componente recovery folosesc înregistrări
de scenariu denumite, enum-uri pentru rezultat și intrări de validare tipizate.
Selectorii raportului aparțin probei din scope-ul de test al fiecărui consumer,
conform [guvernanței arhitecturii consumerilor](../../atf-tests/src/test/java/atf/api/tests/governance/consumers/ConsumerModuleArchitectureGovernanceTest.java).

README-urile [Shop](../../atf-test-practice-shop/README.md) și
[AutoEx](../../atf-test-auto-ex/README.md) dețin comportamentul și verificările.

<a id="j1-recovery-trace-typing-2026-11-21"></a>
## J1-RECOVERY-TRACE-TYPING-2026-11-21 — proiecție recovery tipizată

**Stare: Verificat și închis.** Proiecțiile consumerilor păstrează operația
semantică, ordinea evenimentelor, deciziile, motivele, semnalele și duratele din
urma tipizată. Verificările rapoartelor Maven reale acoperă rezultatele
recovered/exhausted, aserțiunile și disclosure-urile separate, expandarea,
redactarea și verdicturile stabile.

[Feedback-ul Reporting](ATF_Reporting_Feedback.md#j1-recovery-ide-report-parity-2026-11-22)
păstrează verificarea IntelliJ separată. Controalele verificate rămân în
[baseline](../ATF_Test_Changes_Baseline.md).

## Control verificat — adoptarea binară Debug UI

Implementarea Debug UI și testele native au acum un singur owner canonic și un
artifact versionat pentru consumer. Modulul sursă J1 a fost eliminat după ce a
trecut guvernanța întregului reactor; contractul binar, integritatea, ownership-ul
și documentația rămân protejate de controalele din
[baseline-ul verificat](../ATF_Test_Changes_Baseline.md).

<a id="source-language-en-2026-11-24"></a>
## SOURCE-LANGUAGE-EN-2026-11-24 — sursă și comentarii în engleză

**Stare: Verificat și închis pentru J0/J1.** J0 publică scannerul comun în Test Kit K5, iar suita J1 îl aplică surselor Java/MJS declarate. Testele negative resping identificatori și comentarii localizate; textele UI și payload-urile fixture localizate rămân acceptate. Diagnosticul păstrează fișierul, linia și construcția.

- Păstrează în engleză sursa J1 scrisă de dezvoltatori: identificatori Java/MJS, comentarii/docstrings, nume de teste și mesaje tehnice. Sursa rămâne identică între `main` și `main_RO`; se traduce doar documentația guvernată.
- Exclude din verdict textele localizate ale produsului și datele de test intenționat localizate. Testele negative și pozitive verifică această limită, iar diagnosticul indică fișierul, linia și construcția.
- J1 folosește scannerul Test Kit K5 deținut de J0, fără o copie locală. Gate-ul rulează în suita whole-reactor și în verificarea de inventariere a suitei de cod; adopția J2–J7 rămâne o etapă separată, urmărită de feedback-ul de rollout al ownerului J0.

Închiderea locală este susținută de verificările J0/J1 și de guvernanța documentației; rollout-ul către alți consumatori nu este implicit închis. Controlul tehnic verificat este în [baseline](../ATF_Test_Changes_Baseline.md).
