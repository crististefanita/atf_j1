# Test Kit — JUnit classification and audit

## At a glance

Kit provides annotations, evaluated comparisons, and a neutral lifecycle. The JAR does not start a browser, broker, or ADB, and does not publish Extent on its own.

| Need | API and limit |
|---|---|
| Test level | @AtfUnitTest, @AtfComponentTest, @AtfIntegrationTest, @AtfE2eTest; classification only, with no automatic launch. |
| Auditable comparisons | RecordedAssertions; the JUnit verdict precedes the 240-character preview, which preserves complete Unicode pairs. |
| Existing JUnit assertions | AuditedAssertions within the adapter scope; lazy diagnostics, original identity and cause. Direct JUnit calls do not become auditable automatically. |
| Full values | AssertionValueDisplay can receive a Projection observer with the expected/actual role and redacted text before preview; the consumer owns retention. |
| Phases and cleanup | ComponentAuditExtension / ComponentAuditAdapter, TestRunInitialization, FailureAwareCleanup. |
| Policies and resources | Atf*Policy resolvers; readResourceUtf8 for classpath/JAR, requiredPath for a real file. |

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

- Dependency: atf.api:atf-test-kit with the atf.testkit.version from the project POM. Use test scope in production; compile is justified only for main-scope support in a test module. Do not include Kit in an APK/application.
- JUnit Jupiter API and Jackson are transitive. The runner declares the junit-jupiter-engine and its selection; the level annotation does not replace @Test or the consumer extension.
- For Extent: the adapter sends checks to AssertionAuditReport; AssertionAuditRun owns the shared session. Kit does not copy the renderer. [Reporting integration](README.Reporting.md#scope-and-integration).
- Inject the sanitizer through AssertionValueDisplay before preview. The comparison runs once; stored values are bounded and are not a complete archive. Pure units keep their own policy.
- Scopes and resources are per invocation; close them and preserve the original cause. A local lock does not isolate global policies from external readers.

---

Record Test Kit findings against its Maven coordinate, version, and JAR/POM hashes. Keep adoption behavior and value-retention checks with the affected J1 module.
