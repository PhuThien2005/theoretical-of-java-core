package no16_enum.practice.order_status_workflow;

/**
 * Reference solution for OrderStatusWorkflowSolution.
 */
public class OrderStatusWorkflowSolution {
    // Wrapper class
}

enum OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    /**
     * Checks if transitioning from 'this' status to 'next' is allowed.
     */
    public boolean canTransitionTo(OrderStatus next) {
        if (next == null) {
            return false;
        }

        return switch (this) {
            case PENDING -> next == PAID || next == CANCELLED;
            case PAID -> next == SHIPPED || next == CANCELLED;
            case SHIPPED -> next == DELIVERED;
            case DELIVERED, CANCELLED -> false; // Final states, no transitions allowed
        };
    }
}
