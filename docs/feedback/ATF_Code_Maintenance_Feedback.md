# Feedback pentru mentenanța codului

## Pe scurt

Constatările de mai jos sunt închise. Regulile durabile rămân la ownerii modulelor
și în guvernanța executabilă; dovezile istorice de execuție rămân în istoricul Git.

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
