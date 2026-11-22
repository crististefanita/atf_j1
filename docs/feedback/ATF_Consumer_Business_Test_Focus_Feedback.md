# Verificare business în consumeri — lacune curente

## Pe scurt

Observația coșului public AutoEx rămâne deschisă. Constatările închise de mai jos
păstrează legături stabile către contractele ownerilor și probele permanente.

## Căutarea AutoEx — contract livrat

[README-ul AutoEx](../../atf-test-auto-ex/README.md) deține corelarea GET,
comparația cu DOM proaspăt și comportamentul terminal pentru coada plină.

<a id="autoex-catalog-evidence-146"></a>
## AUTOEX-CATALOG-EVIDENCE-146 — ownership-ul schimbului observat

**Stare: Verificat și închis.** Componentele controlate și rapoartele generate
prin suită, inspectate în browser, verifică o singură comparație sub GET-ul
selectat, refuză capturile lipsă sau ambigue și păstrează dovezile expandabile.
Preview-ul implicit rămâne de 240 de caractere.

Probe permanente: AutoExRuntimeBoundaryComponentTest,
AutoExCatalogReportPresentationE2eTest și AutoExE2eOverviewNarrativeComponentTest.
Fixture-urile lor locale nu certifică traseul site-ului public.

<a id="shop-billing-sanity"></a>
## SHOP-BILLING-SANITY — stabilizarea măsurată a lookup-ului

**Stare: Verificat și închis pentru criteriul măsurat.**
ShopBillingSynchronizationComponentTest măsoară stabilizarea lookup-ului separat
de pornirea/navigarea browserului și păstrează termenul de 1.100 ms, ownership-ul
request-ului, valorile finale și verificările fără reintroducerea datelor.

Evenimentul istoric de 122 de secunde rămâne neexplicat. Reverificările controlate
certifică stabilizarea în bugetul fixture-ului, fără acceptanță pentru suita largă
sau site-ul public.

<a id="autoex-ui-cart-confirmation-2026-11-19"></a>
## AUTOEX-UI-CART-CONFIRMATION-2026-11-19 — confirmarea coșului public

**Stare: Deschis; owner:** traseul de cumpărare AutoEx. O rulare publică a apăsat
butonul coșului și a depășit termenul așteptând modalul ascuns, înaintea
înregistrării sau checkout-ului. Componentele locale cu modal imediat/întârziat nu o reproduc.

- [ ] Reproduceți într-un browser nou și capturați starea coșului și schimbul observat după click.
- [ ] Distingeți comportamentul site-ului de defectele de locator/readiness înaintea schimbării traseului.
- [ ] Păstrați raportul de eșec inspectabil și verificați cleanup-ul dacă se ajunge la înregistrare.
