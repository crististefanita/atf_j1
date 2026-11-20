# Reguli pentru workspace-ul ATF J1

- Tratează acest checkout drept sursa de adevăr pentru reactor, configurație,
  scenariile Shop/AutoEx, adaptoarele consumerului și criteriile de acceptanță.
- Citește `docs/ATF_Workspace_Synchronization.md` și
  `docs/ATF_Build_And_Execution_Safety.md` înainte de modificarea sau livrarea codului.
- Consumă Core, Reporting, Console, Mock și Test Kit prin perechile JAR/POM
  versionate din `lib`. Folosește POM-ul rădăcină pentru versiunile selectate și
  `library-provenance.json` pentru coordonate, hash-uri și proveniența înregistrată
  a pachetelor. Nu copia sursa bibliotecilor în acest reactor.
- Păstrează local fiecare adaptor J1, așteptare de domeniu, configurație și test
  de integrare. Descrie dependențele comune după numele bibliotecii, coordonata
  Maven, versiune și contract observabil.
- Păstrează locale POM-urile reactorului, configurațiile și baza Git. Construiește
  și testează numai în staging TMP deținut; păstrează acolo rapoartele, cache-ul
  și output-ul generate.
- Păstrează preview-ul Reporting implicit de 240 de caractere și aserțiunile
  component/E2E auditabile și colapsabile.
- Validarea Playwright trebuie să moștenească implicit runtime-ul Node selectat
  de SDK. Nu suprascrie în tăcere `PLAYWRIGHT_NODEJS_PATH` cu alt runtime.
  Înregistrează separat experimentul explicit de runtime față de acceptanța cu
  runtime-ul implicit al SDK-ului.
- Menține README-urile și documentele proiectului aliniate cu codul și testele
  curente. Înregistrează feedback-ul pe baza unui scenariu local reproductibil și,
  când este relevant, a coordonatei/hash-ului exact al bibliotecii. Păstrează
  proveniența istorică a build-urilor și testelor; nu deduce o versiune nouă sau
  o execuție nouă din redenumirea unui director.
- Dovezile screenshot păstrează contextul paginii: capturează întregul viewport
  al browserului la zoom-ul configurat (70% pentru journey-urile Shop/AutoEx) sau
  întregul ecran Android. Decupajele regionale pot completa cadrul original, dar
  nu îl înlocuiesc. Vizualizatorul raportului adaptează imaginea completă la
  ecran, păstrând proporțiile și toate marginile. Testele explicite de geometrie
  își păstrează scările independente.
- Păstrează README-ul root local-first și concis: explică modulele J1 și fluxul
  dovezilor înaintea oricărei note despre dependențe. Dacă este necesar catalogul
  bibliotecilor, adaugă un singur link către lib/README.md în secțiunea finală;
  nu adăuga identități ale workspace-urilor surori, note de rulare sau rezultate
  datate.
- Păstrează concisă documentația contractelor curente. Reține numai nota curentă
  de verificare a pachetului lângă artifact, în TMP-ul deținut; înregistrează
  locația în proveniență când este necesar. Nu acumula în docs sau `lib` fișiere
  Markdown de verificare pentru fiecare versiune.
