# Debug UI — inspectarea locală a rapoartelor

## Pe scurt

`atf-debug-ui` servește prin loopback un artifact HTML existent, astfel încât browserul local sau TAB MCP să îl poată inspecta. Nu generează rapoarte, nu adaugă aserțiuni și nu califică journey-ul testat.

| Aspect | Contractul J1 |
|---|---|
| Ownerul producerului și feedback-ului | Producerul deține modulul sursă, testele native, pachetul, contractul API și feedback-ul comun. |
| Artifact selectat | `atf.api:atf-debug-ui:1.0.0-d1`; POM-ul rădăcină, POM-ul alăturat, proveniența și hash-urile trebuie să coincidă. |
| Scope J1 | Numai classpath-ul de test `atf-tests`; modulele aplicației nu depind de inspector. |
| Dependență runtime | POM-ul publicat aduce proiecția Console; Core este tranzitiv prin Console. |
| Ownership-ul raportului | Shop și AutoEx își păstrează contractele, aserțiunile și acceptanța rapoartelor proprii. |

## Inspectarea unui artifact existent

Rulează `atf.debugui.AtfDebugUiLauncher` cu classpath-ul de test J1 și o cale explicită spre raportul generat:

```text
--html <configured-report-path>
--port 0
```

Launcherul afișează URL-ul efectiv loopback. Păstrează serverul activ cât timp verifici raportul real în browserul aprobat: tab-uri, starea expandărilor, linkuri, arborele de accesibilitate, consola și capturile așteptate. Este un ajutor pentru inspecție, nu o poartă de raportare sau o suită E2E.

## Ownership și dovezi

Producerul testează și împachetează implementarea; J1 verifică JAR/POM-ul fizic, POM-ul încorporat, checksum-ul, inputurile producerului, metadatele de ownership și `CodeSource`-ul încărcat. Guvernanța respinge perechi lipsă sau alterate, surse shadow, versiuni greșite și dependențe în scope nepotrivit. Verificarea pachetului nu înlocuiește acceptanța rapoartelor Shop sau AutoEx.

Această livrare nu modifică alte cataloage locale. Fiecare adopție ulterioară necesită pachet revizuit și acceptanță proprie.

<a id="shared-library-source"></a>

> **Sursa bibliotecii:** [Debug UI · ATF J0](https://github.com/crististefanita/atf_j0/blob/main_RO/lib/README.DebugUi.md).
> Producerul deține sursa și contractul comun; acest proiect deține doar dependența selectată și acceptanța locală.
