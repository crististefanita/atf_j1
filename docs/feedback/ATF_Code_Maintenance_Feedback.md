

## GOV-UI-NATIVE-LAYER — pre-existing classification disagreement

Priority: 78/100. The unchanged PlaywrightPagePreparationTest is annotated AtfUnitTest and imports Playwright interfaces. The unit-layer rule rejects those imports independently of Test Kit binary adoption.

Keep this failure visible; library CodeSource, dependency, integrity and source-shadow checks have their own acceptance.

- Preserve its controlled-clock, no-browser and negative deadline oracles; do not delete the test or weaken its assertions to green the gate.
- Reconcile the declared unit/component ownership with the documented policy and keep a permanent architecture regression.
- Re-run the complete layer guard and the native UI contracts. This extraction does not claim that the pre-existing whole-J1 layer gate passes.
