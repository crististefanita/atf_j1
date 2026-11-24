# ATF J1 workspace rules

- Treat this checkout as the source of truth for its reactor, configuration,
  Shop/AutoEx scenarios, consumer adapters, and acceptance criteria.
- Read `docs/ATF_Workspace_Synchronization.md` and
  `docs/ATF_Build_And_Execution_Safety.md` before changing or delivering code.
- Consume Core, Reporting, Console, Mock, and Test Kit through the versioned
  JAR/POM pairs in `lib`. Use the root POM for selected versions and
  `library-provenance.json` for coordinates, hashes, and recorded package origin.
  Do not copy library source into this reactor.
- Keep each J1 consumer adapter, domain expectation, configuration, and
  integration test local. Describe shared dependencies by library name,
  Maven coordinate, version, and observable contract.
- Preserve local reactor POMs, configurations, and Git base. Build and test only
  in owned TMP staging; keep generated reports, cache, and output there.
- Preserve the 240-character default reporting preview and auditable,
  collapsible component/E2E assertions.
- Playwright validation must inherit the SDK-selected Node runtime by default.
  Do not silently overwrite `PLAYWRIGHT_NODEJS_PATH` with another runtime.
  Record an explicit runtime experiment separately from SDK-default acceptance.
- Keep project READMEs and docs aligned with the current code and tests. Record
  feedback against a reproducible local scenario and the exact library
  coordinate/hash when relevant. Preserve historical build/test provenance;
  do not infer a new version or test run from a directory rename.
- Screenshot evidence preserves page context: capture the entire browser
  viewport at the configured zoom (70% for Shop/AutoEx journeys), or the whole
  Android display. Region crops may supplement the original frame, never replace
  it. The report viewer fits the complete image to the screen with its aspect
  ratio and all edges intact. Explicit geometry tests retain their independent
  scales.
- Keep the root README local-first and concise: explain the J1 modules and
  evidence flow before any dependency note. If the library catalog is needed,
  link to lib/README.md once in the final section; do not add sibling
  workspace identities, run scores, or dated results.
- Keep current contract documentation concise: put any package verification
  note beside its artifact in owned TMP and record it in provenance when
  required. Do not commit project-wide orientation maps or dated/library-
  versioned verification and acceptance reports in docs or `lib`; maintain
  durable contracts at their owners.

- Library guides in `lib` may end with one marked canonical source note.
  Keep the consumed version and acceptance local; this navigation exception
  does not allow workspace catalogs or other project references elsewhere.
- Developer-authored Java and JavaScript/MJS identifiers, comments, test names,
  and technical diagnostics use English. Use the scanner shipped in the selected
  versioned Test Kit JAR; do not copy or fork its implementation.
  Localized product strings and deliberate fixture/payload values remain data.
