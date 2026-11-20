# Reguli pentru workspace-ul ATF

- Folosește workspace-ul-soră `atf_j0` (`../atf_j0`) ca referință actuală pentru
  sursele și testele Core/Reporting/Console/Mock.
  `demo2` este istoric; nu îl edita și nu îl sincroniza pentru lucrări noi.
- Citește `docs/ATF_Workspace_Synchronization.md` și
  `docs/ATF_Build_And_Execution_Safety.md` înainte de a modifica sau livra cod.
- Păstrează implementarea Reporting și testele native în `atf_j0/atf-reporting`,
  iar regresiile permanente mock, de contract și de browser în
  `atf_j0/atf-reporting-tests`.
- După schimbări relevante în J0, evaluează atât `atf_j1`, cât și `atf_j2`.
  Livrează și validează separat fiecare consumer inițializat. Inspectează
  bootstrap-ul și scope-ul de mesagerie curente din J2; nu deduce acceptanța
  acestuia din rezultatele J1.
- J2 își păstrează scope-ul Git și de mesagerie existent. Profilul Eventuate
  explicit include Setup și exclude Shop, AutoEx, UI și Debug UI; nu readăuga
  aceste module sau Playwright în J2.
- Păstrează implementarea Core și testele native numai în J0. Consumerii binari
  păstrează direct în `lib` cele mai recente perechi JAR/POM pentru Core,
  Reporting, Console, Mock și Test Kit, cu un singur fișier de proveniență la
  rădăcină. Nu copia sursa Reporting sau harness-ul său în consumerii binari
  activi.
- Reconciliază selectiv codul comun și adaptoarele consumerilor în ambele
  direcții. Păstrează lucrul local, POM-urile reactorului, configurațiile și
  bazele Git independente. Promovarea reușită a unui fișier nu este o tranzacție
  atomică între workspace-uri.
- Construiește și testează numai într-un staging TMP deținut; păstrează acolo
  rapoartele, cache-ul și output-ul generate. Păstrează preview-ul Reporting
  implicit de 240 de caractere și aserțiunile component/E2E auditabile și
  colapsabile.
- Validarea Playwright trebuie să moștenească implicit runtime-ul Node selectat
  de SDK. Nu suprascrie în tăcere `PLAYWRIGHT_NODEJS_PATH` cu runtime-ul agentului.
  Înregistrează separat experimentul explicit de runtime față de acceptanța cu
  runtime-ul implicit al SDK-ului.
- Menține documentația ownerilor și feedback-ul la zi cu codul și testele reale.
  Păstrează proveniența istorică a build-urilor; o redenumire de director, de una
  singură, nu cere o versiune Reporting nouă și nu presupune o execuție nouă a
  testelor.

- Limitează feedback-ul activ din J0 la Core/Reporting/Console/Mock și Test Kit,
  inclusiv testele Reporting generice. J1 coordonează celelalte criterii ale
  modulelor comune și handoff-urile către consumeri; păstrează ownership-ul de
  domeniu și acceptanța independentă în J2/J3/J4. Rutele de compatibilitate pot
  rămâne în J0 fără TODO-uri duplicate.

- Dovezile screenshot păstrează contextul paginii: capturează întregul viewport
  al browserului la zoom-ul configurat (70% pentru journey-urile Shop/AutoEx)
  sau întregul ecran Android. Decupajele regionale pot completa cadrul original,
  dar nu îl înlocuiesc. Vizualizatorul raportului adaptează imaginea completă la
  ecran, păstrând proporțiile și toate marginile. Testele explicite de geometrie
  își păstrează scările independente.

- Păstrează README-urile și documentele proiectului concentrate pe contractele
  curente. Reține lângă pachetul producător doar nota curentă de verificare
  Reporting, în TMP-ul deținut; înregistrează locația ei în proveniența curentă
  a consumerului. Nu acumula fișiere Markdown de verificare pentru fiecare
  versiune în docs sau în `lib`-ul consumerului. Păstrează proveniența istorică a
  build-urilor și testelor.
