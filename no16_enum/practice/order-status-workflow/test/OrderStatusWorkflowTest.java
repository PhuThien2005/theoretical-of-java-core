package no16_enum.practice.order_status_workflow;

/**
 * Test runner for OrderStatusWorkflow.
 */
public class OrderStatusWorkflowTest {

    public static void main(String[] args) {
        try {
            testPendingTransitions();
            testPaidTransitions();
            testShippedTransitions();
            testFinalTransitions();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testPendingTransitions() {
        OrderStatus status = OrderStatus.PENDING;
        assertTrue(status.canTransitionTo(OrderStatus.PAID), "PENDING -> PAID should be allowed");
        assertTrue(status.canTransitionTo(OrderStatus.CANCELLED), "PENDING -> CANCELLED should be allowed");
        assertFalse(status.canTransitionTo(OrderStatus.SHIPPED), "PENDING -> SHIPPED should NOT be allowed");
        assertFalse(status.canTransitionTo(OrderStatus.DELIVERED), "PENDING -> DELIVERED should NOT be allowed");
    }

    private static void testPaidTransitions() {
        OrderStatus status = OrderStatus.PAID;
        assertTrue(status.canTransitionTo(OrderStatus.SHIPPED), "PAID -> SHIPPED should be allowed");
        assertTrue(status.canTransitionTo(OrderStatus.CANCELLED), "PAID -> CANCELLED should be allowed");
        assertFalse(status.canTransitionTo(OrderStatus.DELIVERED), "PAID -> DELIVERED should NOT be allowed");
        assertFalse(status.canTransitionTo(OrderStatus.PENDING), "PAID -> PENDING should NOT be allowed");
    }

    private static void testShippedTransitions() {
        OrderStatus status = OrderStatus.SHIPPED;
        assertTrue(status.canTransitionTo(OrderStatus.DELIVERED), "SHIPPED -> DELIVERED should be allowed");
        assertFalse(status.canTransitionTo(OrderStatus.CANCELLED), "SHIPPED -> CANCELLED should NOT be allowed");
        assertFalse(status.canTransitionTo(OrderStatus.PAID), "SHIPPED -> PAID should NOT be allowed");
    }

    private static void testFinalTransitions() {
        OrderStatus delivered = OrderStatus.DELIVERED;
        assertFalse(delivered.canTransitionTo(OrderStatus.PENDING), "DELIVERED cannot transition");
        assertFalse(delivered.canTransitionTo(OrderStatus.CANCELLED), "DELIVERED cannot transition");

        OrderStatus cancelled = OrderStatus.CANCELLED;
        assertFalse(cancelled.canTransitionTo(OrderStatus.PENDING), "CANCELLED cannot transition");
        assertFalse(cancelled.canTransitionTo(OrderStatus.PAID), "CANCELLED cannot transition");
    }
}
