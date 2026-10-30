package atf.autoex.tests.component.business;

import atf.autoex.locator.AutoExSelectors;
import atf.autoex.tests.extension.AutoExComponentTest;
import atf.autoex.tests.support.business.AutoExBusinessFault;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static atf.testkit.assertions.AuditedAssertions.*;

/** Local fixture proof. The public-site negative journeys are explicit live acceptance probes. */
@AutoExComponentTest
class AutoExBusinessFaultComponentTest {
    @ParameterizedTest
    @org.junit.jupiter.params.provider.CsvSource({
            "http://foreign.local/font?families=a|b, 0",
            "http://store.local/delete_account?diagnostic=a|b, 1"
    })
    void browserQueriesCannotAbortTheScopedDeletionListener(String url, int expectedEvents) {
        try (var fixture = new AutoExBusinessFault.LocalFixture()) {
            var actual = fixture.deletionTrafficAfter(url);
            assertEquals(expectedEvents, actual.observedRequests(), "Foreign traffic is ignored and an owned request remains fully observed");
            assertEquals(0, actual.navigationCount(), "A controlled fetch never invents a document navigation or deletion verdict");
            assertFalse(actual.singleDeletionNavigation(), "Noise and non-navigation traffic cannot satisfy the account deletion gate");
        }
    }

    @org.junit.jupiter.api.Test
    void redirectDiagnosticsDropTheBrowserQueryWithoutLosingThePath() {
        var actual = new atf.autoex.support.account.AutoExAccountDeletionAttempt.ObservedRequest(
                "GET", "document", true, "http://store.local/login?families=a|b#diagnostic");
        assertEquals("/login", actual.redirectSourcePath(), "Diagnostics retain the observed path without query values or URI parsing failures");
    }

    @ParameterizedTest @EnumSource(AutoExBusinessFault.Point.class)
    void corruptsOnlyTheChosenObservedValueAndRestoresItWithoutNavigation(AutoExBusinessFault.Point point) {
        try (var fixture = new AutoExBusinessFault.LocalFixture()) {
            String selector = switch (point) {
                case ACCOUNT -> AutoExSelectors.LOGGED_IN_NAME;
                case CART -> AutoExSelectors.CART_QUANTITY;
                case PURCHASE -> AutoExSelectors.ORDER_SUCCESS;
            };
            String original = fixture.text(selector);
            var fault = fixture.fault(point);
            fault.beforeRead(".unrelated");
            assertFalse(fault.injected(), "An unrelated read cannot trigger the business fault");
            fault.beforeRead(selector);
            assertTrue(fault.injected(), "The exact real business read is intercepted");
            String rejected = switch (point) {
                case ACCOUNT -> "ATF controlled ACCOUNT mismatch";
                case CART -> "99";
                case PURCHASE -> "ATF controlled PURCHASE mismatch";
            };
            assertEquals(rejected, fixture.text(selector), "The exact intended observed value is corrupted, without changing the independent expected value");
            assertEquals(1, fixture.markers(), "One visible marker identifies original failure evidence");
            fault.beforeRead(selector);
            assertEquals(1, fixture.markedNodes(), "Repeated reads do not replay the fault");
            fault.restore();
            fault.restore();
            assertEquals(original, fixture.text(selector), "Owned cleanup sees the restored original identity");
            assertEquals(0, fixture.markers(), "The fixture removes only its marker");
            assertEquals("about:blank", fixture.url(), "Corruption and restoration never navigate or dispatch a mutation");
        }
    }
}
