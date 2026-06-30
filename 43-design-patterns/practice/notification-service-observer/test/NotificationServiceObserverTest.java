package notificationserviceobserver;

import java.util.ArrayList;
import java.util.List;

public class NotificationServiceObserverTest {

    public static void main(String[] args) {
        try {
            testPlainTextFormatting();
            testMarkdownAndHtmlFormatting();
            testRegisterUnregisterBehavior();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: [" + expected + "], Actual: [" + actual + "]");
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static class MockListener implements NotificationServiceObserver.NotificationListener {
        List<String> received = new ArrayList<>();

        @Override
        public void onNotification(String formattedMessage) {
            received.add(formattedMessage);
        }
    }

    private static void testPlainTextFormatting() {
        var strategy = new NotificationServiceObserver.PlainTextStrategy();
        var service = new NotificationServiceObserver.NotificationService(strategy);
        var listener = new MockListener();

        service.register(listener);
        service.sendNotification("Hello", "Alice");

        assertEquals(1, listener.received.size(), "Listener should receive 1 notification");
        assertEquals("From Alice: Hello", listener.received.get(0), "Plain text format mismatch");
    }

    private static void testMarkdownAndHtmlFormatting() {
        var service = new NotificationServiceObserver.NotificationService(new NotificationServiceObserver.MarkdownStrategy());
        var listener = new MockListener();
        service.register(listener);

        // Test Markdown
        service.sendNotification("Markdown", "Bob");
        assertEquals("**From Bob**: *Markdown*", listener.received.get(0), "Markdown format mismatch");

        // Switch to HTML
        service.setStrategy(new NotificationServiceObserver.HtmlStrategy());
        service.sendNotification("Html", "Charlie");
        assertEquals("<b>From Charlie</b>: <i>Html</i>", listener.received.get(1), "HTML format mismatch");
    }

    private static void testRegisterUnregisterBehavior() {
        var service = new NotificationServiceObserver.NotificationService(new NotificationServiceObserver.PlainTextStrategy());
        var listener1 = new MockListener();
        var listener2 = new MockListener();

        service.register(listener1);
        service.register(listener2);

        service.sendNotification("First Alert", "System");
        assertEquals(1, listener1.received.size(), "Listener1 should receive First Alert");
        assertEquals(1, listener2.received.size(), "Listener2 should receive First Alert");

        service.unregister(listener1);
        service.sendNotification("Second Alert", "System");

        assertEquals(1, listener1.received.size(), "Listener1 should not receive Second Alert after unregistering");
        assertEquals(2, listener2.received.size(), "Listener2 should receive Second Alert");
        assertEquals("From System: Second Alert", listener2.received.get(1), "Mismatch in second notification content");
    }
}
