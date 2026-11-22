# Test Kit — clasificare și audit JUnit

## Pe scurt

Kit oferă adnotări, comparații evaluate și lifecycle neutru. JAR-ul nu pornește browser, broker ori ADB și nu publică singur Extent.

| Nevoie | API și limită |
|---|---|
| Nivelul testului | @AtfUnitTest, @AtfComponentTest, @AtfIntegrationTest, @AtfE2eTest; clasificare, fără lansare automată. |
| Comparații auditabile | RecordedAssertions; verdictul JUnit precede preview-ul 240, care păstrează perechile Unicode complete. |
| Aserțiuni JUnit existente | AuditedAssertions în scope-ul adaptorului; diagnostic lazy, identitate și cauză originale. Apelurile JUnit directe nu devin automat auditabile. |
| Valori integrale | AssertionValueDisplay poate primi un observer Projection cu rol expected/actual și text redactat, înainte de preview; consumerul deține retenția. |
| Faze și cleanup | ComponentAuditExtension / ComponentAuditAdapter, TestRunInitialization, FailureAwareCleanup. |
| Politici și resurse | Resolverele Atf*Policy; readResourceUtf8 pentru classpath/JAR, requiredPath pentru fișier real. |

## Exemplu minim

Adaptorul deține recorderul și publică rezultatul; o nepotrivire păstrează excepția JUnit originală:

```java
import atf.testkit.assertions.RecordedAssertions;

public static void verifyState(RecordedAssertions audit, String actual) {
    audit.equals("State", "ready", actual);
}
```

<a id="scope-and-integration"></a>

## Scope și integrare

- Dependență atf.api:atf-test-kit cu versiunea atf.testkit.version din POM-ul proiectului. Folosiți test scope în produs; compile este justificat numai pentru suportul main al unui modul de test. Nu includeți Kit în APK/aplicație.
- JUnit Jupiter API și Jackson sunt tranzitive. Runnerul declară motorul junit-jupiter-engine și selecția sa; adnotarea de nivel nu înlocuiește @Test ori extensia consumerului.
- Pentru Extent: adaptorul trimite checks către AssertionAuditReport; AssertionAuditRun deține sesiunea comună. Kit nu copiază rendererul. [Integrare Reporting](README.Reporting.md#scope-și-integrare).
- K3 `RecordedAssertions.observe(ComponentAuditObservation)` păstrează fapte imuabile pentru faza curentă, separat de checks și coverage-ul aserțiunilor. Adaptorul consumerului decide dacă și cum le mapează în Reporting; Kit nu depinde de Reporting și nu publică un renderer.
- Sanitizer-ul se injectează prin AssertionValueDisplay înainte de preview. Comparația rulează o dată; valorile stocate sunt limitate și nu constituie arhivă integrală. Unitățile pure își păstrează politica proprie.
- Scope-urile și resursele sunt per invocare; închideți-le și păstrați cauza inițială. Politicile globale nu devin izolate între cititori externi printr-un lock local.

---

Înregistrează constatările Test Kit după coordonata Maven, versiune și hash-urile JAR/POM. Păstrează comportamentul de adopție și verificările de păstrare a valorilor în modulul J1 afectat.

## Planul descoperit al lansării

Test Kit K4 oferă `TestLaunchContext.capture(context)` și snapshot-ul imuabil
`TestLaunchSnapshot`. Observerul `LauncherSessionListener` este descoperit prin
SPI; launcherul JUnit este o dependență opțională furnizată de runner.

- Selectorii sunt asociați configurației planului; o cerere străină nu este reutilizată.
- Inventarul și numărul provin din `TestPlan` după filtre, la începutul execuției.
- Testele dezactivate sunt incluse; înregistrările dinamice ulterioare se numără separat.
- Scope-urile se restaurează la lansări imbricate și se izolează la lansări concurente.
- Un plan din altă sesiune păstrează inventarul, dar declară lipsa cererii; fără observer, captura este absentă.

Kit nu depinde de Reporting ori UI și nu lansează teste. Consumerul decide cum
publică snapshot-ul și verifică binarul selectat în raportul său.

<a id="shared-library-source"></a>

> **Sursa bibliotecii:** [Test Kit · ATF J0](https://github.com/crististefanita/atf_j0/blob/HEAD/lib/README.TestKit.md).
> POM-ul, proveniența și testele acestui proiect stabilesc versiunea consumată și acceptanța locală.
