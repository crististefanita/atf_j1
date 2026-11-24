# Test Kit — JUnit classification and audit

## At a glance

Kit provides annotations, evaluated comparisons, and a neutral lifecycle. The JAR does not start a browser, broker, or ADB, and does not publish Extent on its own.

| Need | API and limit |
|---|---|
| Test level | `@AtfUnitTest`, `@AtfComponentTest`, `@AtfIntegrationTest`, `@AtfE2eTest`; classification only, with no automatic launch. |
| Auditable comparisons | `RecordedAssertions`; the JUnit verdict precedes the 240-character preview, which preserves complete Unicode pairs. |
| Existing JUnit assertions | `AuditedAssertions` within the adapter scope; lazy diagnostics, original identity and cause. Direct JUnit calls do not become auditable automatically. |
| Full values | `AssertionValueDisplay` can receive a `Projection` observer with the expected/actual role and redacted text before preview; the consumer owns retention. |
| Phases and cleanup | `ComponentAuditExtension` / `ComponentAuditAdapter`, `TestRunInitialization`, `FailureAwareCleanup`. |
| Policies and resources | `Atf*Policy` resolvers; `readResourceUtf8` for classpath/JAR, `requiredPath` for a real file. |
| Source-language governance | `AtfSourceLanguageScanner` in the selected Test Kit artifact; deterministic Java/MJS comments, identifiers, and recognized diagnostic strings with path/line/construct findings; no file inventory or project-specific exceptions. |

## Minimal example

The adapter owns the recorder and publishes the result; a mismatch preserves the original JUnit exception:

```java
import atf.testkit.assertions.RecordedAssertions;

public static void verifyState(RecordedAssertions audit, String actual) {
    audit.equals("State", "ready", actual);
}
```

<a id="scope-and-integration"></a>

## Scope and integration

- Dependency: `atf.api:atf-test-kit` with the `atf.testkit.version` from the project POM. Use test scope in application modules; compile is justified only for main-scope support in a test module. Do not include Kit in an APK/application.
- JUnit Jupiter API and Jackson are transitive. The runner declares the `junit-jupiter-engine` and its selection; the level annotation does not replace `@Test` or the consumer extension.
- For Extent: the adapter sends checks to `AssertionAuditReport`; `AssertionAuditRun` owns the shared session. Kit does not copy the renderer. [Reporting integration](README.Reporting.md#scope-and-integration).
- `RecordedAssertions.observe(ComponentAuditObservation)` stores immutable facts with the current phase, separately from checks and assertion coverage. The consumer adapter decides whether and how to map them to Reporting; Kit has no Reporting dependency and publishes no renderer.
- Inject the sanitizer through `AssertionValueDisplay` before preview. The comparison runs once; stored values are bounded and are not a complete archive. Pure units keep their own policy.
- Scopes and resources are per invocation; close them and preserve the original cause. A local lock does not isolate global policies from external readers.
- The source-language scanner is used by governance tests only; it is not an application dependency. Each consumer owns its source inventory, localized-data exceptions, and adoption test.

---

Record Test Kit findings against its Maven coordinate, version, and JAR/POM hashes. Keep adoption behavior and value-retention checks with the affected J1 module.

## Discovered launch plan

Test Kit provides `TestLaunchContext.capture(context)` and the immutable
`TestLaunchSnapshot`. The `LauncherSessionListener` observer is discovered through
SPI; the JUnit launcher is an optional dependency supplied by the runner.

- Selectors are associated with the plan configuration; an unrelated request is never reused.
- Inventory and count come from the filtered `TestPlan` at execution start.
- Disabled tests are included; later dynamic registrations are counted separately.
- Scopes restore across nested launches and remain isolated across concurrent launches.
- A plan from another session retains its inventory but declares missing request provenance; without the observer, capture is absent.

Kit depends on neither Reporting nor UI and launches no tests. The consumer owns
snapshot publication and verifies the selected binary through its own report.

<a id="shared-library-source"></a>

> **Library source:** [Test Kit · ATF J0](https://github.com/crististefanita/atf_j0/blob/main/lib/README.TestKit.md).
> This project’s POM, provenance and tests define the consumed version and local acceptance.
