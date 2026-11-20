# Code maintenance feedback

<a id="gov-package-cohesion-2026-11-19"></a>
## GOV-PACKAGE-COHESION-2026-11-19 — reconcile existing cohesion hotspots

**Status: Verified on 2026-11-20; owners:** `atf-tests` governance and `atf-ui` package owners.

The 2026-11-19 suite run recorded 163 tests: 161 passed and two package-cohesion checks failed. The fixes moved the documentation checker to its responsibility package and retained a reviewed exception for the stable Playwright boundary.

On 2026-11-20, earlier reruns passed 177/177 and 179/179. The latest passed 186/186 in both editions after adding root-README governance and an explicit guard for mandatory code-quality gates, with no failures, errors, or skipped tests.

| Historical hotspot | Resolution | Current control |
|---|---|---|
| `atf.api.tests.governance.documentation` had 9 test classes against the limit of 8 | Moved `DocumentationReadmeStructureGovernanceTest` into `atf.api.tests.governance.documentation.structure` | The general eight-class limit and negative mutants remain active |
| `atf.ui.playwright` had 9 production and 10 test classes against the limit of 8 | Kept the public Playwright entry points, action owners, session, and evidence recorder together under a reviewed exception | Revisit if unrelated types enter or a boundary becomes independently replaceable; remove the exception when a split needs no new cross-package API |

The exception documents the actual behavioral boundary; it does not waive package cohesion globally. The E2E Extent report check remains classified in the consumer E2E package.

The 2026-11-20 verification ran `WholeAtfGovernanceSuiteTest` from the Git-backed canonical TMP staging layout against each edition's current review base:

- `main` (`4ec3e14b357e0968776399477592fd13c3713631`): `mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=4ec3e14b357e0968776399477592fd13c3713631" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test`
- `main_RO` (`754eb315045e565c150ca819498cf63154bee9b4`): `mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=754eb315045e565c150ca819498cf63154bee9b4" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test`

The latest executions passed 186/186 in each edition using the commands above.

The full `AutoExE2eSuite` was not part of this governance proof and remains tracked separately where suite-scoped artifact evidence is required.
