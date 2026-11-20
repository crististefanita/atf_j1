# Core C3 — verificarea consumerului J1 2026-11-02

## Pe scurt

J1 a consumat perechea Core C3, iar Reporting a rămas la J12; nu a fost nevoie
de o versiune nouă pentru folosirea unui API existent. Înregistrarea datată
separă identitatea pachetului și verificările istorice de versiunile curente.

| Artefact | Identitate |
|---|---|
| Core | atf.api:atf-core:1.0.0-c3 |
| SHA-256 JAR Core | 605407B9D40E67C9C352EE6E94DA758E50BD3CFB14CC0D7A0DE3359C71088447 |
| SHA-256 POM Core | 7853373300E588B2EA25F6A07C927ECAC1FB06044073AD75FDA8D6B7CFF275E0 |
| Reporting neschimbat | atf.api:atf-reporting:1.0.0-j12 |
| SHA-256 JAR Reporting | 1F1B7B5502FEFEA02F500C2FC80494859D4E57E38261306B57FA7AD2D5AB38B1 |

## Contracte probate

- Core redactează numele START/COMPLETE și string-urile JSON publice decodate folosind politica configurată; request-ul și response-ul originale rămân intacte.
- HttpClientTransport transmite best effort anularea către upstream, o dată pentru caller, cu interrupt flag păstrat; completarea concurentă nu înlocuiește verdictul terminal.
- Eșecul cleanup este suppressed pe anularea primară. GET-ul local blocat păstrează un singur dispatch, CANCELLED și absența unui răspuns inventat.

## Matricea executată

| Probă | Rezultat / limită |
|---|---|
| verificare sursă Core | 286 teste native, zero fail/error/skip |
| pachet Core parentless | două build-uri noi independente, 286 teste native fiecare, JAR byte-identic |
| JAR Core fizic | aceleași 286 teste native plus un control CodeSource; fără sursă în consumer |
| API C2 → C3 | zero simboluri public/protected eliminate |
| Reporting pe Core C3 | 16 cazuri sursă, 43 cazuri JAR și un control de identitate; zero fail/error/skip în gate-urile acceptate |
| Browser | Extent suită/FAIL, Mock combinat și mesaje/SQL fallback; mouse, Enter/Space, ultimul rând și backlink exact |

## Limite și documentație

Acceptanța de consumer consemnată aici verifică în J1 contractul bibliotecii
publicate. Anularea unui future nu dovedește rollback server-side; probele nu
certifică un comportament dincolo de verificările locale și binare enumerate.

- Proveniența și verificările de pachet descriu inputurile istorice; nu afirmă
  că acele verificări pe sursă au rulat în J1.
- Citește contractele curente ale consumerului în ghidurile locale ale
  bibliotecilor. Această înregistrare datată nu stabilește comportamentul
  versiunilor ulterioare.

Dovezi TMP: ATF_ADOPTION_CORE_C3_ACTIVE_DO_NOT_CLEAN_HjeEfO — gate-uri, XML,
core-binary-api.json, matricile C3, inventar și promovare guarded. Recepția
datată consemnează separat verificarea pachetului și acceptanța consumerului J1.


<details>
<summary><strong>Rulări excluse și limitele acceptanței</strong></summary>

- Un replay sursă a atins limita DOM de 5 s la JsonEvidenceFidelityComponentTest în context concurent. Repetarea izolată are 16/16 verzi, cu același prag; logul inițial este păstrat.
- Două gate-uri documentare au țintit alt staging și sunt excluse în invalid-documentation-gates.json. Gate-urile corecte verifică explicit identitatea proiectului; runnerul refuză nepotrivirea nume/workdir.
- Publisherul Core intermediar a fost înlocuit înaintea livrării. Numai publisher-3/4 produc candidatul final, byte-identic, cu toate cele 149 intrări fixate.
- Această verificare istorică a consumerului nu închide cerințele pentru memorie
  la volum mare sau dovezi ACTION/OBSERVE complete în versiuni ulterioare.

</details>
