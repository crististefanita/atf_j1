# Documentație ATF — fidelitatea deployment-urilor

## Pe scurt

Atlasul este orientare către cod și POM, iar guvernanța comună aparține ATF Tests.
Nota editorială și trecerea linkurilor nu certifică fidelitatea tuturor afirmațiilor.

## DOC-CONTEXT-DEPLOYMENT-111 — prioritate 81

**Prioritate: 81/100** = impact 31 + lacună 24 + lucrabil 18 + documentare 8.
**Owner:** guvernanța comună coordonată în J1; fiecare deployment păstrează contractele și acceptanța proprii.

Reproducerea inițială avea J1 cu 10 module, J2 cu 8 și Reporting J6 în atlas,
deși reactorul avea 9/7 module și binarul era J8. Acesta este contextul istoric al reproducerii;
guvernanța structurală nu dovedește încă respingerea contradicției semantice.

- Confruntați selectiv afirmațiile curente din atlas/index cu reactorul explicit, proprietatea Reporting și proveniența țintei; versiunea așteptată se citește din deployment, fără constantă istorică în test.
- Proba negativă schimbă un singur fapt: numărul modulelor cu o unitate față de POM sau versiunea curentă cu una istorică. Guard-ul indică afirmația respinsă.
- Acceptați în continuare istoricul legitim J6/J7/J8/J9 și controalele la bazele originale; existența unei versiuni vechi în jurnal nu este singură un defect.
- Distingeți ownerul sursei Core J0 de consumul Core prin JAR în J1–J6; catalogul nu se micșorează implicit după foldere absente.

**Închidere:** documentele nominale și istorice trec, iar mutanții curenți
sunt refuzați fără relaxarea perimetrului. [Criteriul local J2](../../../atf_j2/docs/feedback/ATF_Documentation_Feedback.md)
are acceptanță proprie; nu închide implicit guvernanța comună.

### Reconcilierea afirmațiilor curente

Inventarul curent din POM-uri este J0: 12, J1: 6, J2: 5, J3: 4, J4: 3,
J5: 2 și J6: 5 module. Declarațiile de versiune se verifică din POM și
proveniență; probele istorice își păstrează coordonatele originale.

- Contradicțiile de catalog/versiune din auditul 2026-11-01 au fost reconciliate editorial în incrementul J12.
- Gap-ul rămas este guard-ul semantic cu mutanți, nu simpla prezență a versiunilor istorice.
- J1 coordonează guvernanța comună; fiecare deployment are acceptanță proprie, fără mirror al documentelor.
- Documentația specifică API-ului Core/Reporting rămâne la ownerul J0.

## DOC-LIBRARY-CONTRACTS-147 — keep release chronology outside official contracts

**Priority: 82/100.** J1 owns common documentation governance; J0 owns the Core/Reporting contracts and fixes their factual content. Official README, atlas and contract pages must describe current behavior rather than successive library improvements.

- [ ] Add a permanent producer documentation control for library release narratives and release-specific verification/scorecard pages; mutation controls insert a historical improvement paragraph and a retired-release link into an otherwise valid official document.
- [ ] Preserve legitimate workspace names, semantic configuration examples, current provenance, approved test-change controls and domain implementation journals; do not reject every number or the word incremental.
- [ ] Keep one current verification note per library beside its package and retain original build facts in provenance; official pages link to stable owner contracts.
- [ ] Reconcile current claims with actual POM/source boundaries; a valid link or a high editorial score does not prove the meaning of its surrounding claim.
- [ ] Derive J0–J6 module counts and library-consumer roles from each live POM;
      reject one-count and one-role mutants in the factual documentation gate.
- [ ] Keep the Core ownership/deployment contract canonical in
      `ATF_Core_Consumer.md`; reduce repeated explanatory blocks in the J0
      architecture, atlas and index to short links so those facts cannot drift.
- [ ] Keep Romanian prose consistent within J0 README/atlas/architecture and
      library guides; retain English API identifiers and code examples, but
      remove mid-document language switches in explanatory paragraphs.
