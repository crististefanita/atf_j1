# Console — execution diagnostics

## At a glance

`atf-console` projects ATF events to stdout/stderr: lifecycle events, durations, and configurable details. It is useful for monitoring a Maven or IntelliJ run; the HTML artifact is produced by [Reporting](README.Reporting.md).

## Minimal example

Snippet inside a method; the policy is explicit, and the stream remains in the adapter:

```java
import atf.console.AtfConsoleReporter;
import atf.console.ConsoleSettings;

var console = AtfConsoleReporter.configuredStdout(
        new ConsoleSettings(true, false));
console.info("Catalogul de scenarii a fost încărcat");
```

`configuredStdout()` reads `atf.report.config` → `ATF_REPORT_CONFIG` → `atf.yaml/yml`; the options are:

```yaml
reporting:
  console:
    enabled: true
    detailsEnabled: false
```

## Integration and options

| Intent | Choice |
|---|---|
| Message independent of details | `info(...)` |
| Typed events with lifecycle and duration | `publish(AtfConsoleEvent)` through the Core `AtfConsoleSink` port |
| Console fully disabled | `enabled: false` or `ConsoleSettings.disabled()` |
| Final report path in the console | Constructor with `reportPathEnabled: true` and the `REPORT_PUBLISHED` event |
| Per-class JUnit policy | JUnit Console adapter and Test Kit annotations; declare Kit explicitly |

The path requires opt-in, an enabled console, and an event with a confirmed artifact; `detailsEnabled` controls details only.

Console depends on Core; Kit is optional for JUnit integration. The projection is diagnostic: the comparison and verdict belong to the test.

---

Maintenance of Console code and tests belongs to the separate `atf_j0/atf-console` project; [feedback and synchronization](README.md#maintenance-and-feedback) start from J0.
