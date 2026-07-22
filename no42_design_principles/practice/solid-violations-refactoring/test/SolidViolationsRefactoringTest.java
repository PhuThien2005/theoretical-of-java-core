package solidviolationsrefactoring;

public class SolidViolationsRefactoringTest {

    public static void main(String[] args) {
        try {
            testOrderProcessingStandard();
            testOrderProcessingExpress();
            testOrderProcessingOvernight();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(double expected, double actual, double delta, String message) {
        if (Math.abs(expected - actual) > delta) {
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

    // Mock Implementations for testing DIP components

    private static class MockDatabase implements SolidViolationsRefactoring.Database {
        boolean saveCalled = false;
        SolidViolationsRefactoring.Order savedOrder = null;

        @Override
        public void save(SolidViolationsRefactoring.Order order) {
            saveCalled = true;
            savedOrder = order;
        }
    }

    private static class MockNotificationService implements SolidViolationsRefactoring.NotificationService {
        boolean sendCalled = false;
        SolidViolationsRefactoring.Order notifiedOrder = null;

        @Override
        public void sendConfirmation(SolidViolationsRefactoring.Order order) {
            sendCalled = true;
            notifiedOrder = order;
        }
    }

    private static void testOrderProcessingStandard() {
        var db = new MockDatabase();
        var notifier = new MockNotificationService();
        var processor = new SolidViolationsRefactoring.OrderProcessor(db, notifier);

        var order = new SolidViolationsRefactoring.Order("ORD-001", 100.0, 10.0, "STANDARD");
        var strategy = new SolidViolationsRefactoring.StandardShipping();

        // 100.0 + (10.0 * 1.5) = 115.0
        double total = processor.processOrder(order, strategy);

        assertEquals(115.0, total, 0.001, "Standard shipping order total mismatch");
        assertTrue(db.saveCalled, "Database save should be called");
        assertEquals("ORD-001", db.savedOrder.id(), "Database should save the correct order");
        assertTrue(notifier.sendCalled, "Notification should be sent");
        assertEquals("ORD-001", notifier.notifiedOrder.id(), "Notification should reference the correct order");
    }

    private static void testOrderProcessingExpress() {
        var db = new MockDatabase();
        var notifier = new MockNotificationService();
        var processor = new SolidViolationsRefactoring.OrderProcessor(db, notifier);

        var order = new SolidViolationsRefactoring.Order("ORD-002", 200.0, 5.0, "EXPRESS");
        var strategy = new SolidViolationsRefactoring.ExpressShipping();

        // 200.0 + (5.0 * 3.5 + 10.0) = 227.5
        double total = processor.processOrder(order, strategy);

        assertEquals(227.5, total, 0.001, "Express shipping order total mismatch");
        assertTrue(db.saveCalled, "Database save should be called");
        assertTrue(notifier.sendCalled, "Notification should be sent");
    }

    private static void testOrderProcessingOvernight() {
        var db = new MockDatabase();
        var notifier = new MockNotificationService();
        var processor = new SolidViolationsRefactoring.OrderProcessor(db, notifier);

        var order = new SolidViolationsRefactoring.Order("ORD-003", 50.0, 2.0, "OVERNIGHT");
        var strategy = new SolidViolationsRefactoring.OvernightShipping();

        // 50.0 + (2.0 * 7.0 + 25.0) = 89.0
        double total = processor.processOrder(order, strategy);

        assertEquals(89.0, total, 0.001, "Overnight shipping order total mismatch");
        assertTrue(db.saveCalled, "Database save should be called");
        assertTrue(notifier.sendCalled, "Notification should be sent");
    }
}
