# ATF Core — library artifact and consumer acceptance

## At a glance

J1 consumes Core through the versioned JAR/POM pair selected by the root POM.
The reactor contains no Core source module; local tests inspect the physical
artifact and exercise the J1 integrations that use it.

| Contract | Current J1 verification |
|---|---|
| Runtime | Java 21; dependency versions are fixed in the published POM |
| Artifact | `atf.api:atf-core:1.0.0-c4`, stored in `lib` |
| Distribution | One flat JAR/POM pair; no reactor parent or shadow source |
| Provenance | `coreLibrary` records coordinates, hashes, inputs, and package evidence |
| Consumer scope | HTTP, configuration, JSON, and lifecycle used by local modules |

## Maven and IntelliJ

Run from the root of an owned TMP copy. Use the same settings and Maven
repository for command-line and IntelliJ resolution:

~~~powershell
mvn -B -ntp -N validate
mvn -B -ntp -pl atf-tests -am "-Dtest=WholeAtfGovernanceSuiteTest" "-Dsurefire.failIfNoSpecifiedTests=false" test
~~~

The first command installs the five library pairs into the selected Maven
repository. Reload the root POM in IntelliJ after selecting the same JDK and
repository. Do not use `systemPath`, a hierarchical file repository, or a
manually added IDE JAR.

## Artifact integrity and acceptance

1. Select the Core version through `atf.core.version` and keep its matching
   JAR/POM pair directly in `lib`.
2. Verify the published coordinate, embedded POM, SHA-256 values, bytecode
   namespace, and the `CodeSource` actually loaded.
3. Reject shadow source, missing coordinates, extra distribution files, and
   prohibited production dependencies.
4. Run affected local contracts and consumer scenarios against the physical
   artifact; keep reports and execution evidence in TMP.
5. Update provenance only when package bytes or recorded verification evidence
   change.

Governance reads provenance describing package inputs and native library checks.
Those records do not claim those checks ran in J1.

## Core artifact flow

~~~mermaid
flowchart LR
    Pin["Root POM<br/>Core coordinate"] --> Pair["lib/<br/>Core JAR + POM"]
    Pair -->|installs| Repo["Maven repository"]
    Repo -->|resolves for| Modules["J1 modules"]
    Modules -->|local acceptance| Evidence["Consumer evidence"]
    Governance["Artifact governance"] -. verifies identity and boundary .-> Pair
~~~

<details>
<summary><strong>Legend — artifact resolution and local evidence</strong></summary>

- The root POM pins the Core coordinate used by J1.
- Maven and IntelliJ resolve the physical pair from the selected local
  repository.
- Consumer acceptance covers only Core behavior exercised through J1 modules;
  Reporting has separate coordinates and provenance.
- The diagram shows dependency resolution, not a dependency on a source
  workspace.

</details>

## Reporting acceptance boundary

Reporting controls report identity, captured and published timing, assertions,
and disclosures. Core integration and application acceptance are separate from
the Reporting contract. The [Reporting library atlas](ATF_Reporting_Library_Atlas.md)
describes the observable contract.

## Complete JSON documents and faithful evidence

| Boundary | Contract used by the consumer |
|---|---|
| JsonDocuments | One complete document, no trailing content or duplicate keys, including nested keys; immutable reader and a fresh tree per call |
| Syntax and writing | Scalars and literal null are JSON; the POST/PUT JSON factory rejects null before sending |
| Explicit request | Form/text paths remain available; the executor does not infer JSON from the HTTP method |
| Mock equality | BigDecimal without narrowing; number/string and integer/decimal distinctions remain; raw body is preserved |
| Core redaction | Numeric tokens and duplicate public fields remain; every sensitive value is fully masked |
| Reporting display | Independent projection preserves numbers and duplicates before configured truncation |
| Assertions | Expected/actual are redacted independently; caller verdict is independent of the 240-character preview |

- The strict parser rejects ambiguous documents used for decisions; evidence
  projection preserves every field occurrence.
- Mock accepts literal null as syntax independently of the write-factory rule.
- Invalid diagnostic JSON does not become a valid payload.

Consumer tests and artifact governance verify the selected binaries without
adding library source to the reactor.

## Core, Console, and upstream cancellation

The HTTP future keeps a best-effort cancellation link to the upstream
operation. Concurrent execution has a single terminal owner, and cleanup
errors remain suppressed.

- URLConnection owns the body, virtual worker, and connection; body collection,
  connection, and reads consume the same deadline. A body delivered after
  termination cannot start the request.
- Cancellation does not confirm server rollback or authorize replay of an
  uncertain mutation. A controlled local GET and a physical-JAR test remain
  separate from consumer E2E.

## Shared platform libraries

[Platform library consumption](ATF_Platform_Libraries.md) documents the five
artifact contracts, flat distribution, Maven/IntelliJ bootstrap, and local
acceptance. Adapter and catalog behavior stays in J1 modules.
