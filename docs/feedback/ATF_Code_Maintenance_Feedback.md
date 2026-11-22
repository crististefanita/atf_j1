# Code maintenance feedback

## At a glance

The findings below are closed. Durable rules remain with the module owners and
executable governance; historical execution evidence remains in Git history.

<a id="gov-package-cohesion-2026-11-19"></a>
## GOV-PACKAGE-COHESION-2026-11-19 — package cohesion

**Status: Verified and closed.** Documentation structure checks have their own
responsibility package. The reviewed Playwright exception preserves the public
boundary; the general package limit and negative fixtures remain active.

[Code rules](../README_Atf_CodeBestPractices.md) and
[governance](../../atf-tests/README.md) own the retained controls.

<a id="oop-recovery-scenarios-2026-11-21"></a>
## OOP-RECOVERY-SCENARIOS-2026-11-21 — typed scenarios and selector ownership

**Status: Verified and closed.** Both recovery components use named scenario
records, outcome enums and typed validation inputs. Report selectors belong to
each consumer's test-scoped probe, enforced by
[consumer architecture governance](../../atf-tests/src/test/java/atf/api/tests/governance/consumers/ConsumerModuleArchitectureGovernanceTest.java).

The [Shop](../../atf-test-practice-shop/README.md) and
[AutoEx](../../atf-test-auto-ex/README.md) READMEs own the behavior and checks.

<a id="j1-recovery-trace-typing-2026-11-21"></a>
## J1-RECOVERY-TRACE-TYPING-2026-11-21 — typed recovery projection

**Status: Verified and closed.** Consumer projections retain semantic operation,
event order, decisions, reasons, signals and measured durations from the typed
trace. Real Maven report checks verify recovered/exhausted outcomes, separate
assertions and trace disclosures, expansion, redaction and stable verdicts.

[Reporting feedback](ATF_Reporting_Feedback.md#j1-recovery-ide-report-parity-2026-11-22)
retains the separate IntelliJ check. Verified change controls remain in the
[baseline](../ATF_Test_Changes_Baseline.md).
