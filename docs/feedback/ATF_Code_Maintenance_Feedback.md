# Code maintenance feedback

## At a glance

All code-maintenance findings on this page are verified and closed. Durable
rules remain with module owners and executable governance; historical execution
evidence remains in Git history.

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

## Verified change control — Debug UI binary adoption

The Debug UI implementation and native tests now have one canonical owner and
a versioned consumer artifact. The former J1 source module was removed after
whole-reactor governance passed; the binary contract, integrity, ownership, and
documentation controls remain in the
[verified change baseline](../ATF_Test_Changes_Baseline.md).

<a id="source-language-en-2026-11-24"></a>
## SOURCE-LANGUAGE-EN-2026-11-24 — English source and comments

**Status: Verified and closed.** The selected Test Kit k5 artifact supplies the canonical scanner; J1 owns the Java/MJS inventory, localized-data exceptions, and suite enrollment. Source remains byte-identical between `main` and `main_RO`.

- Negative Romanian and positive localized-data fixtures pass, including recognized diagnostic-message contexts.
- The gate reports file, line, construct, and token and is selected by both the code governance gate and whole-ATF suite.
- Documentation, artifact coordinate, JAR/POM identity, integrity, and exact-path change governance passed in staged verification.

The verified test-change control is retained in the [baseline](../ATF_Test_Changes_Baseline.md). Future consumers remain with their own owners and the shared rollout feedback in the producer workspace.
