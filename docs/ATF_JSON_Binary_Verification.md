# JSON și biblioteci Core C2 / Reporting J9 — acceptanță

## Pe scurt

Înregistrare istorică a dovezilor C2/J9. Contractul curent de disclosure și
guvernanță este descris în [atlasul bibliotecii Reporting](ATF_Reporting_Library_Atlas.md).
Aceste rezultate C2/J9 rămân istorice.

Acest checkout J1 consemnează acceptanța istorică a octeților C2/J9. Perechile
JAR/POM sunt păstrate direct în `lib`, câte o versiune selectată per bibliotecă;
reactorul nu include un modul-sursă Core.

| Bibliotecă | SHA-256 JAR |
|---|---|
| atf.api:atf-core:1.0.0-c2 | 91080FE996CCB358B7E42B8F147322B3D4468D84C364DFD259371EEEF9AFD5DB |
| atf.api:atf-reporting:1.0.0-j9 | 2B58E5B871CCB78AF53C5BF77F1A21B6F6EAE92D18EF5F66CAC6FF9CCA154322 |

## Contract și dovadă

| Suprafață | Rezultat și limită |
|---|---|
| verificare sursă Core și JAR C2 | 276 teste native PASS pentru fiecare; document complet, duplicate, null/write, redactare exactă și factory înainte de trimitere |
| control Core C1 | 37 cazuri noi, 20 FAIL așteptate; aceeași regresie pe sursa veche și JAR fizic |
| Mock și Validation | suite pe sursă și suite consumer J1; rută fallback independentă, scalar YAML exact, tipuri și corp brut păstrate |
| Reporting nativ | 64 PASS per build; redactor și formatter probate separat, cu numere și duplicate |
| control Reporting J8 fizic | 16 cazuri, 15 FAIL așteptate; eroarea numerică și pierderea multiplicității sunt reproduse |
| Reporting J9 fizic | 39 PASS: teste native JSON, browser JSON, disclosure API și CodeSource; fără implementare Reporting în harness |
| teste unitare Reporting pe sursă | 486 PASS; distincte de cele opt componente JSON și cele 22 verificări API disclosure |
| Kit | 133 PASS separat în fiecare staging; cele două verificări ale cerințelor sunt distincte semantic, fără prag relaxat |
| J1 | guvernanță completă și câte un component Shop/AutoEx; nu declară o nouă execuție de journey extern |
| publicare | două build-uri curate identice pentru fiecare JAR; comparația API nu elimină simboluri publice |

## Randare și limite rămase

- Browserul automat verifică Extent/fallback, individual/agregat, ownerul activ, tokenurile numerice și toate aparițiile duplicate.
- Mouse, Enter și Space păstrează disclosure-ul auditat; ultimul rând și verdicturile FAIL/FAIL/PASS sunt accesibile.
- Assertions păstrează default 240; fixture-ul numeric declară body 240, fără schimbarea default-ului standalone 100.
- Oracolul refuză rotunjirea ori eliminarea unui duplicat chiar când statusurile rămân identice.
- TAB MCP confirmă valorile și fallback keyboard; închiderea unui vecin după Expand all expune separat REPORT-SIBLING-DISCLOSURE-114.

Patru criterii Reporting anterioare de coeziune/importuri rămân separate de
această acceptanță JSON. Timing-ul capturării/publicării, profilul inspectorului,
contrastul și lățimea fallback au criterii distincte; fidelitatea JSON nu le
închide implicit.

## Reproducere și sincronizare

[Core Consumer](ATF_Core_Consumer.md) descrie contractul, bootstrap-ul Maven/IDE
și păstrarea istoricului. [Sincronizarea](ATF_Workspace_Synchronization.md)
cere manifest explicit, verificarea drift-ului și promovare selectivă.

Dovezile acestui increment sunt în staging-ul extern
ATF_JSON_C2_J9_ACTIVE_DO_NOT_CLEAN_f8f75e12: acceptance-evidence.json, logurile,
manifestele publicațiilor și cele patru HTML-uri din publication. Sursa retrasă
și perechile vechi se arhivează recuperabil; output-urile nu intră în live.
