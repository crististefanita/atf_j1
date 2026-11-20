# Reporting — J1 consumer evidence

## At a glance

J1 has no separate Reporting source-test module. Its consumer tests verify the
selected library through local adapters and scenarios; report presentation
checks inspect the generated J1 artifact.

## Record a reproducible report gap

Attach the test name, Maven coordinate, JAR/POM hashes, report path, and the
expected and observed tree. State which scenario, step, or assertion fails to
expand, and whether keyboard navigation reproduces the issue.

Use the [Reporting consumer guide](../ATF_Reporting_Consumer.md) for local
acceptance and the [library usage guide](../../lib/README.Reporting.md) for the
published API contract. Keep application assertions with the affected J1 test.
