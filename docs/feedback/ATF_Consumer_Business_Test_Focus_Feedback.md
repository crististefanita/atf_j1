# ATF — acoperirea ramurilor business ale consumerilor

## Pe scurt

Account/Cart/Purchase au probe negative UI/UI_API prin extensiile E2E reale,
individual și în suită fixture, cu vecin sănătos în profil opus.
Contractul stabil este în [ghidul de testare](../ATF_Testing_Guide.md#acceptanța-business-individual-și-prin-suită),
cu intrări la [Shop](../../atf-test-practice-shop/README.md#acceptanța-failure-urilor-business)
și [AutoEx](../../atf-test-auto-ex/README.md#acceptanța-failure-urilor-business).

Selecțiile canonice rămân complete. Restanțele de mai jos condiționează o
reducere ulterioară a repetării live; nu declară un nou incident al site-ului.

## CONSUMER-ACCEPTANCE-ORACLES-58 — dovadă pe fiecare invocare

**Prioritate: 74/100** = impact 30 + lacună 21 + lucrabil 17 + documentare 6.
**Owner:** Shop și AutoEx pentru CLI-ul de acceptanță; contractul neutru de
artifact are [probe distincte](ATF_Reporting_Tests_Feedback.md#report-test-audit-60--o-dovadă-identificabilă-pentru-fiecare-scenariu).

| Control curent în `launch()` | Ce poate rata |
|---|---|
| markerul FAIL în markup, după eliminarea scripturilor/stilurilor | verificarea globală nu identifică invocarea căreia îi aparține verdictul |
| un singur marker de captură în întregul HTML | dovada unui sibling poate acoperi lipsa capturii altui caz negativ |

CLI-urile exclud acum scripturile și stilurile înaintea verificării markerilor.
Confuzia cu selectorul runtime este corectată; criteriul deschis privește
asocierea fiecărei dovezi, conform [regulii probelor](../ATF_Testing_Guide.md#auditul-componentelor-consumerilor).

Controlul în memorie elimină toate capturile în afară de una și păstrează
adevărate verificările HTML globale. Aceasta arată o lacună a oracolului;
nu declară lipsa capturilor în artifactele business deja auditate.

- [ ] Verificați per invocare scenariul/faza, verdictul, checkpoint-ul, cauza
      și captura originală; nici scriptul, nici dovada unui sibling nu pot satisface cazul.
- [ ] Pentru vecinul sănătos verificați și verdictul raportului, lipsa failure-ului
      moștenit și totalurile coerente în Tests/Fails/Overview.
- [ ] Oracolul refuză eliminarea unei singure capturi, reasocierea ei și
      schimbarea verdictului unui singur caz; păstrează JUnit, mutațiile numărate și un HTML per lansare.
- [ ] Unificați numai lansarea/colectarea neutră după caracterizarea celor două
      utilizări; politica contului și fixture-ul browser rămân la consumer.
      Launcherul JUnit este acum test-scoped în Kit; o mutare nu extinde implicit runtime-ul lui.

**Închidere:** fiecare degradare are refuz focalizat, individual și în suită,
cu UI/UI_API și vecin sănătos. Numărul corect de excepții JUnit nu certifică
singur fidelitatea HTML-ului publicat.

## CONSUMER-E2E-PROFILES-01 — variantele de evidence și acoperirea business

**Prioritate: 39/100** = impact 18 + lacună 6 + lucrabil 10 + documentare 5.
**Owner:** consumerii pentru ramurile business și captura originală; Reporting
Tests numai pentru contractele neutre de evidence.

| Boundary demonstrat | Limita probei |
|---|---|
| Shop Account/Cart/Purchase: checkpoint real respins, UI/UI_API, individual și suită; cont scoped eliberat și context închis o dată | povestea Cart folosește journey-ul de achiziție autentificat; nu certifică toate combinațiile de recalculare/removal |
| AutoEx Account: identitate DOM greșită după înregistrarea reală; cleanup owned, captură inițială și profil opus sănătos | nu substituie proba locală a identității străine și nici fiecare eroare posibilă după setup |
| AutoEx Cart: adăugare publică, coș guest, login și restaurarea sesiunii; cantitate greșită respinsă înaintea continuării | `recalculatesAndRemovesProduct` este altă ramură; failure-ul ei nu este injectat de această matrice |
| AutoEx Purchase: confirmarea comenzii respinsă după o plată unică; cauza și captura preced cleanup-ul owned | nu reprezintă toate defectele facturii sau toate refuzurile serverului |
| fiecare consumer: șase negative și șase succese în resursa comună de raport; aceleași cazuri lansate individual | o execuție a matricei nu măsoară stabilitatea statistică și nu autorizează reducerea selecției |
| `AutoExAccountCleanupRecoveryComponentTest`: înregistrare reală pe documente locale, eroare după checkpoint PASS, identitate owned/străină, diagnostic secundar și captura originală | integrarea remote a acestei erori generice după PASS, prin extensie și suită, rămâne distinctă de failure-ul de validare demonstrat |

- [ ] Înaintea reducerii selecției Cart, selectați explicit ramura de
      recalculare/removal și probați mismatch-ul, oprirea continuării, expected-ul
      independent și zero replay; adăugarea publică și restaurarea sesiunii au
      deja proba E2E de mai sus și nu se redeschid generic.
- [ ] Dacă optimizarea elimină o poveste care poate eșua după un checkpoint
      PASS, completați proba locală cu eroarea generică prin extensia/suita E2E:
      diagnostic FAIL distinct, aceeași cauză, captura inițială și vecin sănătos.

**Închidere:** ramurile propuse pentru eliminare au dovada negativă declarată,
individual și în suită, iar raportul păstrează comparația ori diagnosticul
efectiv eșuat. Matricea implementată nu devine o afirmație despre toate ramurile.

## Contract și proces

Acceptanța, regula reducerii selecției și limitele probelor sunt în
[ghidul de testare](../ATF_Testing_Guide.md#acceptanța-business-individual-și-prin-suită).
Controalele verificate rămân în [baseline](../ATF_Test_Changes_Baseline.md);
build-ul și promovarea respectă [procesul izolat](../ATF_Build_And_Execution_Safety.md).
