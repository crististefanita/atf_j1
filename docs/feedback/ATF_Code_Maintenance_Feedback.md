# Code maintenance feedback

<a id="gov-package-cohesion-2026-11-19"></a>
## GOV-PACKAGE-COHESION-2026-11-19 — reconcile existing cohesion hotspots

**Status: Verified on 2026-11-20. Priority: 74/100. Owner:** `atf-tests` governance with `atf-ui` package owners.

The 2026-11-19 run of `WholeAtfGovernanceSuiteTest` had 163 tests: 161 passed and two package-cohesion checks failed. The finding is resolved by a responsibility-based move for the documentation checker and a reviewed exception for the stable Playwright boundary. The 2026-11-20 whole-ATF run passed 171/171 in both documentation editions.

| Historical hotspot | Resolution | Current control |
|---|---|---|
| `atf.api.tests.governance.documentation` had 9 test classes against the limit of 8 | Moved `DocumentationReadmeStructureGovernanceTest` into `atf.api.tests.governance.documentation.structure` | The general eight-class limit and negative mutants remain active |
| `atf.ui.playwright` had 9 production and 10 test classes against the limit of 8 | Kept the public Playwright entry points, action owners, session, and evidence recorder together under a reviewed exception | Revisit if unrelated types enter or a boundary becomes independently replaceable; remove the exception when a split needs no new cross-package API |

The exception documents the actual behavioral boundary; it does not waive package cohesion globally. The E2E Extent report check remains classified in the consumer E2E package.

```text
mvn -B -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Datf.governance.testChanges.baseRef=5773a1567e14b570b17608b25058158bb2b1c114" "-Datf.test.heap.max=384m" "-Dsurefire.failIfNoSpecifiedTests=false" test
```

The suite ran from the Git-backed canonical TMP staging layout. The full `AutoExE2eSuite` was not part of this governance proof and remains tracked separately where suite-scoped artifact evidence is required.