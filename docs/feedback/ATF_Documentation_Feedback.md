# Documentație ATF — fidelitatea deployment-urilor

## Pe scurt

Atlasul este orientare către cod și POM, iar guvernanța comună aparține ATF Tests.
Nota editorială și trecerea linkurilor nu certifică fidelitatea tuturor afirmațiilor.

## DOC-CONTEXT-DEPLOYMENT-111 — prioritate 81

**Prioritate: 81/100** = impact 31 + lacună 24 + lucrabil 18 + documentare 8.
**Owner:** guvernanța comună coordonată în J1; J1/J2/J3 păstrează contractele propriului deployment.

Reproducerea inițială avea J1 cu 10 module, J2 cu 8 și Reporting J6 în atlas,
deși reactorul avea 9/7 module și binarul era J8. Acesta este contextul istoric al reproducerii;
guvernanța structurală nu dovedește încă respingerea contradicției semantice.

- Confruntați selectiv afirmațiile curente din atlas/index cu reactorul explicit, proprietatea Reporting și proveniența țintei; versiunea așteptată se citește din deployment, fără constantă istorică în test.
- Proba negativă schimbă numai 9 în 10, 7 în 8 sau versiunea curentă cu una istorică; fiecare contradicție trebuie refuzată la afirmația exactă.
- Acceptați în continuare istoricul legitim J6/J7/J8/J9 și controalele la bazele originale; existența unei versiuni vechi în jurnal nu este singură un defect.
- Distingeți ownerul sursei Core J0 de consumul Core C2 prin JAR în J1/J2/J3; catalogul nu se micșorează implicit după foldere absente.

**Închidere:** documentele nominale și istorice trec, iar mutanții curenți
sunt refuzați fără relaxarea perimetrului. [Criteriul local J2](../../../atf_j2/docs/feedback/ATF_Documentation_Feedback.md)
are acceptanță proprie; nu închide implicit guvernanța comună.

### Reconcilierea afirmațiilor curente

Inventarul curent este 12 module sursă J0, 9 J1, 8 J2 și câte 7 în J3/J4.
Declarațiile de versiune se verifică din POM și proveniență; probele istorice își păstrează coordonatele originale.

- Contradicțiile de catalog/versiune din auditul 2026-11-01 au fost reconciliate editorial în incrementul J12.
- Gap-ul rămas este guard-ul semantic cu mutanți, nu simpla prezență a versiunilor istorice.
- J1 coordonează guvernanța comună; fiecare deployment are acceptanță proprie, fără mirror al documentelor.
- Documentația specifică API-ului Core/Reporting rămâne la ownerul J0.

