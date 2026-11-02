# Coordonare consumer Android — J1 către J4

## Pe scurt

J1 păstrează coordonarea cerințelor nonbibliotecă transferate din J0 la 2026-11-02.
J4 deține ADB, modelul, oracolele și execuția device; [J0 Reporting](../../../atf_j0/docs/feedback/ATF_Android_UI_Reporting_Feedback.md) păstrează numai contractele generice de renderer și proba sursă/JAR.

Înregistrările de mai jos sunt handoff-uri datate, nu afirmații despre release-ul curent.
Detaliile deja demonstrate sunt context pentru acceptanța consumerului; nu se recalculează verdictul sau numărul testelor dintr-un checkpoint.

<details>
<summary><strong>Handoff-uri consumer păstrate integral</strong></summary>

## 2026-11-01 — partidă completă și dovezi de stare, consumator J11

- J4 a primit perechea fizică J11.
- Proba producerului cu PNG sintetic nu închide acceptanța Android a consumatorului.
- Ținta live este 19 mâini, 109 levate și 436 cărți, apoi aceleași distribuții/oferte/mutări în Whist Lab cu rezultate calculate de motorul local.
- Rularea este în curs: prima mână are 40 de cărți contabilizate și scoruri observate `[10,15,2,3]`, reconciliate independent.
- Scenariul complet nu este încă acceptat.

**Cerințe ferme pentru contract și probă, fără logică Whist în Reporting:**

1. **Proveniență per afirmație.** OBSERVE poate descrie starea produsului și
   calculul independent, pe lângă captura UI. XML UI vechi, pixel schimbat și
   stare salvată proaspătă sunt dovezi diferite. Nu transformați OBSERVE în
   ACTION sau interpretarea într-un fapt citit automat din PNG. Parsarea stării
   și oracolul rămân în J4; modelul Reporting rămâne neutru.
2. **Reproducere din raport.** Inputul, condiția regulii, expected/actual,
   verdictul, captura inline exactă și artifact-ul sursă cu SHA-256 rămân
   corelate cu pasul/scenariul lor. Preview-ul rămâne 240 de caractere;
   comparația integrală trebuie să poată fi deschisă și citită. Listele de
   valori legale nu pot dispărea din HTML.
3. **Numărători stabile.** Testele JUnit, scenariile live, checkpoint-urile,
   assertions, mutările și capturile au populații diferite. Sute de assertions
   nu devin sute de teste în Overview. Un FAIL de regulă/scor rămâne FAIL și
   când acceptanța vizuală este WARNING.
4. **Probă de volum.** Cele două aplicații produc sute de checkpoint-uri cu
   PNG 1080×1920 inline Base64. Măsurați publish/close, dimensiunea HTML,
   memoria și navigarea în browser cu o probă sintetică echivalentă. Verificați
   media și assertions din ultimul pas. Dacă imaginile ascunse sunt decodate
   toate la deschidere, propuneți încărcare la nevoie păstrând HTML-ul autonom
   și payload-urile complete.
5. **Timing neinstrumentat.** Diferențiați observația/calculul fără timing
   instrumentat de o acțiune măsurată ca 0 ms. Dacă suprafața actuală permite
   metadata/observations, documentați folosirea ei înainte de a extinde API-ul.
6. **Continuare auditabilă.** Eșecul harness-ului păstrează trace-ul și cauza.
   Continuarea revalidează checkpoint-urile reținute și păstrează logul inițial.
   Nu asociați capturi după simplul nume de fișier și nu ascundeți proveniența
   fazelor la publicarea verdictului final.

- J4 folosește API-ul existent J11.
- Dacă cerințele sunt acoperite, livrați proba pe JAR și instrucțiuni concrete de consum.
- Dacă apare un gol, numai J0 modifică, testează și împachetează biblioteca.
- Backup-urile `.ab` brute pot conține preferințe ale aplicației; J4 atașează în raport numai starea jocului și dovezile necesare, fără arhivele brute.

## 2026-11-02 — J4: reguli românești, componente și AI înainte de Android

Consumerul J4 adaugă raportul nativ `RuleComponentExtentReportTest`, fără
renderer HTML privat. Profilul românesc este separat de Oh Hell; testele JVM
produc intrări, stări și expected/actual la fiecare carte, fără emulator.
Contractele Reporting necesare pentru acest consum sunt concrete:

1. Overview trebuie să distingă cazuri JUnit, partide model, comparații AI,
   mutări și ramuri JaCoCo. 6.375 configurații dintr-un test nu sunt 6.375
   cazuri JUnit. Nici un pas al unei partide nu devine automat un test nou.
2. Un flow de componentă are SETUP/TEST BODY/TEARDOWN și stări de domeniu, fără
   screenshot. Lipsa unei capturi UI aici este normală, nu warning de produs.
   Când revenim la Android, pozele rămân PNG reale inline Base64.
3. Expected/Actual trebuie să păstreze integral mâna, lista cărților legale,
   atuul, actorul, câștigătorul, levatele și totalurile. Pentru profilul românesc
   scorurile negative sunt valide; formatarea nu trebuie să ascundă semnul.
4. Verdictul AI are două dimensiuni: legalitate și performanță măsurată.
   Eșantionul pereche, seed-ul, locul, adversarii și scorurile trebuie să poată
   fi citite. Un scor mai bun în 32 de perechi nu înseamnă AI optim global.
5. Raportul offline și raportul Android au acceptanțe distincte. Nu completați
   automat cu PASS o execuție Android lipsă pe baza componentelor verzi.

J4 consumă numai JAR-ul J11 livrat de J0. Orice schimbare generică de layout,
numărători, comparație sau navigare rămâne de implementat, verificat și
împachetat în J0. Rularea curentă și verificarea HTML sunt păstrate sub
`../../../../whist_back/rules-components/20261102-final/`.

### Reproducere concretă: observation.fields este doar preview

- Verificarea strictă a consumerului a eșuat în `../../../../whist_back/rules-components/20261102-acceptance/extent-publisher.log`: `Report omitted complete component evidence`, distribuția mâinii 5.
- În HTML, `INPUTS / HAND / LEGAL CHOICES` se termină cu elipsă după 240 caractere; valoarea integrală nu era în raport deoarece consumerul folosise numai ReportField.
- Corecția J4 folosește `ReportObservation.Body(..., Format.TEXT)` pentru intrări/expected/actual complete și păstrează câmpul ca preview.
- Nu ridicăm plafonul global și nu copiem rendererul.
- J0: documentați explicit această semnificație a câmpurilor și adăugați o probă binară pentru corp TEXT de peste 240 caractere, cu semne negative, cărți Unicode și ultimul element vizibil.

- Corecție de configurare confirmată prin proba binară J11: și Body TEXT are un plafon implicit separat de 100 de caractere.
- Consumerul trebuie să ceară `new ReportingSettings(240, ReportingSettings.DISABLE_BODY_DISPLAY_LIMIT)` pentru dovezi complete.
- J4 a făcut asta numai în publisherul său offline; preview-ul rămâne 240.
- Verificarea tuturor valorilor a trecut: `../../../../whist_back/rules-components/20261102-complete/component-report-verification.properties`, 184 cazuri, 88 partide model, 40.304 mutări, HTML 4.604.237 bytes.
- Benchmarkul final are 144 perechi la 3–6 locuri, nu doar proba inițială de 32.
- Această configurare trebuie documentată clar de J0 pentru consumerii cu dovezi lungi; nu este nevoie de un renderer privat sau de modificarea JAR-ului în J4.
</details>

<details>
<summary><strong>Handoff J4 nou — volum J12 și proba de domeniu</strong></summary>


## 2026-11-02 — probă reală J4 de volum pe JAR J12: OOM la publicare

Toată partida originală și replay-ul Android Whist Lab au trecut: 19 mâini,
109 levate, 436 intrări per aplicație; totaluri finale [89,126,145,119].
Publisherul native J12 primește 321 checkpoint-uri cu PNG inline, 77.114.418
bytes PNG, 6.634 assertions și 577 înregistrări ACTION/OBSERVE.

La heap de 1 GB, închiderea Extent eșuează cu OutOfMemoryError înainte de HTML:
`StringUTF16.replace -> String.replace -> ExtentReportAssets.inlineFontAsset:195
-> inlineSparkAssets:94 -> ExtentReportPublicationPipeline.publish:66`.
Diagnosticul complet este în
`whist_back/deep-app-test/runs/20261101-231857-full-match/diagnostics/publisher-1g-oom/`.

Cu heap dedicat de 3 GB, rendererul produce HTML de circa 105 MB. Aceasta este
mitigare în consumer, nu dovada că publicarea are consum acceptabil. J0: adăugați
un gate permanent pe JAR cu această clasă de volum și măsurați alocările din
înlocuirile asset-urilor pe întregul HTML UTF-16. Reduceți copiile integrale;
păstrați toate PNG-urile originale Base64. Nu rezolvați prin eliminarea pozelor,
trunchierea assertions sau mutarea lor în linkuri externe.

Verificarea consumerului a mai identificat normalizarea JSON: native afișează
`[14,24,23,15]` pentru inputul `[14, 24, 23, 15]`. Datele sunt păstrate;
comparatorul J4 acceptă numai JSON valid normalizat, cu aceleași valori și
ordine. Controalele pentru ultimul element lipsă, ordine schimbată, element
suplimentar și text trunchiat trec. Aceasta nu cere schimbarea formatterului.

Componentele curente ale jocului: 187 PASS, 88 partide model / 40.304 mutări,
GameRules 100% linii/ramuri, game-core 97,22% linii / 91,07% ramuri. Benchmarkul
AI are 144 perechi la 3–6 locuri: Expert 14.604 vs Hard 7.689 puncte.
Raportul componentelor a trecut publisherul pe J12. Auditul vizual al HTML-ului
local prin MCP rămâne neefectuat: politica browserului refuză file://. Nu îl
prezentăm ca audit browser închis.

</details>

<details>
<summary><strong>Handoff J4 nou — ACTION/OBSERVE integral</strong></summary>


### J12: payload-urile ACTION/OBSERVE lungi se pierd după preview

În `ExtentUiActionRenderer` expected/observed folosesc direct
`ExtentReportNodeSupport.display(..., settings)`. La 240 caractere, restul
stării nu există în HTML, chiar cu bodyMaxDisplayCharacters=0. Reproducere:
`full-match-extent-publisher-j12-final.log`, starea originală a mâinii 18,
33 de cărți jucate. Aceasta este pierdere de date, diferită de JSON compact.

J4 consumă API-ul existent: păstrează tabelul scurt și publică payload-urile
complete, corelate prin checkpoint, sursă și număr de acțiune, ca native
ReportObservation.Body(TEXT) în faza deja existentă de metadata. Nu inventează
assertions PASS pentru a transporta observații. Verificarea tuturor celor
6.634 assertions și 577 ACTION/OBSERVE a trecut după această completare.

J0: cerința generică este expansiune expected/observed integrală, păstrând
preview-ul de 240. Adăugați controale care elimină numai ultimul scalar dintr-un
payload lung și cer FAIL al inspecției. UiPhase ar beneficia și de seam-ul
neutru observation deja existent pe AtfReportPorts.Phase, cu owner-ul fazei
păstrat. Numai J0 modifică și împachetează aceste extensii; J4 păstrează
compoziția de consumer și probele reale.

</details>

## J14 — lacune în publicarea individuală J4

[Criteriile active au owner în J4](../../../atf_j4/docs/feedback/ATF_Android_Alignment_Feedback.md#j14--criterii-active-demonstrate); J1 păstrează coordonarea.

| Prioritate | Criteriu consumer |
|---|---|
| 1 | false PASS resume: meniul cu buton disabled trece pragul de pixeli |
| 2 | comparații lipsă și 209 ms de publicare în locul execuției de 37,476 s |
| 3 | trace-ul bid-grid individual nu închide un HTML |

[Auditul J14](../ATF_Reporting_J14_Verification.md) fixează probele și limitele.

### Coordonarea probei J4 pe dispozitiv partajat — 2026-11-02, 15:33

Probele live de consumer din `ATF_REPORTING_J14_ACTIVE_DO_NOT_CLEAN_9ptTeB` și
rularea J4 AI training folosesc simultan `127.0.0.1:5555` prin serverul ADB
`tcp:192.168.56.1:5038`. O probă externă a schimbat Instructions în meniul
principal în timpul capturării J4; validarea strictă a refuzat dovada incompletă.
Aceasta este contendență de execuție, nu un defect de randare Reporting.

- Nu suprapuneți noi journeys Android cu o rulare J4 activă. Finalizați procesul curent fără oprire forțată.
- J4 așteaptă acces exclusiv pentru lectura completă și duelul AI. Avizul este în `../../../../whist_back/android-ui-lease.json`.
- Diagnosticul este în `../../../../whist_back/ai-training/20261102-143525/device-contention.json`.
- Gate-urile generice Reporting nu folosesc ADB. Captura altui journey nu devine dovadă validă prin simpla prezență în HTML.

## Compoziție J4 fără artifacte externe — J15

[Contractul J4](../../../atf_j4/docs/ATF_Android_Report_Contract.md) deține identitatea cazurilor, proiecția semantică, comparațiile capturate și PNG-urile per checkpoint. [Criteriile active](../../../atf_j4/docs/feedback/ATF_Android_Alignment_Feedback.md) includ raw dumps și acceptori globali; J1 coordonează, fără duplicarea backlog-ului. Corecția generică TEXT și regresiile neutre au owner în J0.

## J4 — audit nou al componentelor și viewer-ului

[Auditul J4](../../../atf_j4/docs/ATF_J4_Report_Audit.md) leagă selecția proaspătă, reconstrucția E2E arhivată și negativele. J4 deține receipt-uri goale, SKIP, acceptorul CR, metricile și timing-ul; [Reporting 137](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#report-screenshot-return-137--png-reutilizat-schimbă-ownerul-și-pierde-expandările) deține pierderea contextului la PNG duplicat, cu regresie neutră în J0.

## J4 — artifact autonom și ecran Android integral

[Criteriile J4](../../../atf_j4/docs/feedback/ATF_Android_Alignment_Feedback.md#un-singur-artifact--prototip-verificabil-migrare-încă-deschisă) cer compoziție per invocare și recepte tipizate, fără dependențe externe ori scenarii PASS de rezumat. Prototipul diagnostic nu este migrarea live și nu închide detaliile de assertions lipsă din captura legacy.

Captura integrală aparține J4; [viewer-ul 138](../../../atf_j0/docs/feedback/ATF_Reporting_Feedback.md#report-screenshot-viewport-138--imagine-integrală-în-spațiul-disponibil) și regresiile neutre au owner în J0. Criteriile generice 137/138 au acum [livrarea J16](../ATF_Reporting_J16_Verification.md); constatările din incrementul documentar anterior rămân istorice.

## J16 — viewer comun și input-uri J4 separate

[J16](../ATF_Reporting_J16_Verification.md) păstrează cadrul integral, caption-ul local și contextul la close, cu regresii neutre sursă/JAR. [J4-RPT-07–13](../../../atf_j4/docs/feedback/ATF_Android_Alignment_Feedback.md#reader-autonomy--input-uri-pentru-implementarea-j4) și [detaliul adaptiv](../../../atf_j4/docs/feedback/ATF_Android_Alignment_Feedback.md#adaptive-game-detail--configurație-consumer-fără-pierderea-verdictului) rămân implementare consumer: scop/setup/input/oracol, grupare lizibilă și bugete configurabile, fără bypass nelimitat sau assertions pierdute.

O copie a JAR-ului nu certifică aceste criterii și nu transformă replay-ul arhivat într-o execuție device nouă. J0 deține numai randarea comună și testele Reporting, iar J4 păstrează capturile și oracolele aplicației.
