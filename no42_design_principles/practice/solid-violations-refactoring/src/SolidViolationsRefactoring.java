package solidviolationsrefactoring;

public class SolidViolationsRefactoring {

    public record Order(String id, double amount, double weight, String shippingMethod) {}

    // Interfaces for Dependency Inversion Principle
    
    public interface Database {
        void save(Order order);
    }

    public interface NotificationService {
        void sendConfirmation(Order order);
    }

    public interface ShippingStrategy {
        double calculateCost(double weight);
    }

    // Concrete Shipping Strategies to support Open-Closed Principle
    
    public static class StandardShipping implements ShippingStrategy {
        @Override
        public double calculateCost(double weight) {
            return weight * 1.5;
        }
    }

    public static class ExpressShipping implements ShippingStrategy {
        @Override
        public double calculateCost(double weight) {
            return weight * 3.5 + 10.0;
        }
    }

    public static class OvernightShipping implements ShippingStrategy {
        @Override
        public double calculateCost(double weight) {
            return weight * 7.0 + 25.0;
        }
    }

    /**
     * Refactored OrderProcessor.
     * It should:
     * 1. Depend on abstractions (Database and NotificationService) injected via constructor (DIP).
     * 2. Delegate shipping cost calculations to a strategy lookup based on the order's shipping method (OCP).
     * 3. Coordinate work without performing DB or notification logic directly (SRP).
     */
    public static class OrderProcessor {
        
        // TODO: Declare private final fields for Database and NotificationService.

        public OrderProcessor(Database db, NotificationService notifier) {
            // TODO: Initialize fields.
        }

        public double processOrder(Order order, ShippingStrategy strategy) {
            // TODO: Calculate shipping cost using the strategy.
            // TODO: Save the order to the database.
            // TODO: Send confirmation.
            // Return the total cost (amount + shipping).
            return 0.0;
        }
    }
}
