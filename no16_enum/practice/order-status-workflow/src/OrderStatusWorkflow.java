package no16_enum.practice.order_status_workflow;

/**
 * Starter template for a state-machine workflow using enums.
 */
public class OrderStatusWorkflow {
    // Wrapper class
}

/**
 * Represents the status of an order.
 */
enum OrderStatus {
    PENDING,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    /**
     * Checks if the order is allowed to transition from the current status (this)
     * to the next status.
     * 
     * Transition rules:
     * - PENDING -> PAID or CANCELLED
     * - PAID -> SHIPPED or CANCELLED
     * - SHIPPED -> DELIVERED
     * - DELIVERED -> no transitions (final state)
     * - CANCELLED -> no transitions (final state)
     *
     * @param next the target status
     * @return true if transition is valid, false otherwise
     */
    public boolean canTransitionTo(OrderStatus next) {
        // TODO: Implement transition rules
        return false;
    }
}
