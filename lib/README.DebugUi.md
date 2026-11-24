# Debug UI — local report inspection

## At a glance

`atf-debug-ui` serves an existing HTML artifact over loopback so a local browser or TAB MCP can inspect it. It does not create reports, add assertions, or qualify a tested journey.

| Concern | J1 contract |
|---|---|
| Producer and feedback owner | The producer owns the source module, native tests, package, API contract, and shared feedback. |
| Selected artifact | `atf.api:atf-debug-ui:1.0.0-d1`; the root POM, adjacent POM, provenance, and hashes must agree. |
| J1 scope | `atf-tests` test classpath only; the application modules do not depend on the inspector. |
| Runtime dependency | The published POM brings in the Console projection; Core is transitive through Console. |
| Report ownership | Shop and AutoEx keep their own report contracts, assertions, and acceptance. |

## Inspect an existing artifact

Run `atf.debugui.AtfDebugUiLauncher` with the J1 test classpath and an explicit path to a generated report:

```text
--html <configured-report-path>
--port 0
```

The launcher prints the effective loopback URL; keep the server alive while checking the actual report in the approved browser, including expected tabs, disclosure state, links, accessibility tree, console, and screenshots. This is an inspection aid, not a reporting gate or E2E suite.

## Ownership and evidence

The producer tests and packages the implementation; J1 verifies the physical JAR/POM, embedded POM, checksum, producer inputs, ownership metadata, and loaded `CodeSource`. Governance rejects missing or altered pairs, source shadows, wrong versions, and misplaced dependencies. Package checks do not replace Shop or AutoEx report acceptance.

J2–J7 are not included in this delivery. Their local library catalogs remain unchanged until each project accepts a separately reviewed package.

<a id="shared-library-source"></a>

> **Library source:** [Debug UI · ATF J0](https://github.com/crististefanita/atf_j0/blob/main/lib/README.DebugUi.md).
> The producer owns the source and shared contract; this project owns only its selected dependency and local acceptance.
