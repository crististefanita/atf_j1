# Feedback documentar — contracte păstrate

## Pe scurt

Aceste constatări sunt închise. Regulile curente aparțin
[standardului documentar](../README_Atf_DocumentationBestPractices.md), README-urilor
ownerilor și testelor executabile; cronologia execuțiilor rămâne în istoricul Git.

<a id="doc-library-contracts-147"></a>
## DOC-LIBRARY-CONTRACTS-147 — catalog factual al bibliotecilor

**Stare: Verificat și închis.**
[Catalogul](../ATF_Workspace_Synchronization.md#consumed-library-catalog) urmează
POM-ul rădăcină și perechile fizice. Guvernanța factuală și a relațiilor refuză
coordonate vechi, ghiduri lipsă și referințe în afara dependențelor directe J1.

<a id="doc-root-overview-20261120"></a>
## DOC-ROOT-OVERVIEW-20261120 — prezentare centrată pe proiect

**Stare: Verificat și închis.** [README-ul principal](../../README.md) prezintă
scopul, modulele și fluxul dovezilor acestui proiect. Guvernanța verifică ordinea
secțiunilor, inventarul modulelor, contractul raportului și linkul final către biblioteci.

<a id="doc-context-artifact-governance-20261120"></a>
## DOC-CONTEXT-ARTIFACT-GOVERNANCE-20261120 — controlul artefactelor de context

**Stare: Verificat și închis.**
[Guvernanța artefactelor de context](../../atf-tests/src/test/java/atf/api/tests/governance/documentation/structure/DocumentationContextArtifactGovernanceTest.java)
refuză hărți de proiect și înregistrări de verificare independente, inclusiv
forme fără dată, prin fixture-uri pozitive și negative în suita completă.

<a id="doc-verification-record-scope-20261120"></a>
## DOC-VERIFICATION-RECORD-SCOPE-20261120 — contracte durabile la owner

**Stare: Verificat și închis.** Regulile de căutare AutoEx și pregătire UI rămân
în README-urile [AutoEx](../../atf-test-auto-ex/README.md) și
[UI](../../atf-ui/README.md). Receipt-urile rămân lângă artefacte în staging-ul
propriu; controalele verificate rămân în [baseline](../ATF_Test_Changes_Baseline.md).

<a id="doc-edition-comparator-20261120"></a>
## DOC-EDITION-COMPARATOR-20261120 — ediții bilingve revizuite

**Stare: Verificat și închis.**
[Comparatorul edițiilor](../../atf-tests/tools/documentation-editions.mjs) compară
structura Markdown, destinațiile linkurilor și implementarea comună. Fixture-urile
negative disting terminatorii de linie de diferențele semantice și păstrează
octeții exacți ai POM-urilor bibliotecilor; inventarul revizuit este fixat pe commit în
[metadatele edițiilor](../../config/documentation-editions.json).
