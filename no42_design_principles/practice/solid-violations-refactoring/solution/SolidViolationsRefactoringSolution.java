package solidviolationsrefactoring;

public class SolidViolationsRefactoringSolution {

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
            if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");
            return weight * 1.5;
        }
    }

    public static class ExpressShipping implements ShippingStrategy {
        @Override
        public double calculateCost(double weight) {
            if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");
            return weight * 3.5 + 10.0;
        }
    }

    public static class OvernightShipping implements ShippingStrategy {
        @Override
        public double calculateCost(double weight) {
            if (weight < 0) throw new IllegalArgumentException("Weight cannot be negative");
            return weight * 7.0 + 25.0;
        }
    }

    /**
     * Refactored OrderProcessor.
     * It now:
     * 1. Depends on abstractions (Database and NotificationService) injected via constructor (DIP).
     * 2. Delegates shipping cost calculations to a dynamically passed ShippingStrategy (OCP).
     * 3. Coordinates order processing (saving to database, calculating total, notifying) 
     *    without doing the detailed work itself (SRP).
     */
    public static class OrderProcessor {
        
        private final Database database;
        private final NotificationService notificationService;

        public OrderProcessor(Database database, NotificationService notificationService) {
            if (database == null) {
                throw new IllegalArgumentException("Database cannot be null");
            }
            if (notificationService == null) {
                throw new IllegalArgumentException("NotificationService cannot be null");
            }
            this.database = database;
            this.notificationService = notificationService;
        }

        public double processOrder(Order order, ShippingStrategy strategy) {
            if (order == null) {
                throw new IllegalArgumentException("Order cannot be null");
            }
            if (strategy == null) {
                throw new IllegalArgumentException("ShippingStrategy cannot be null");
            }

            // Calculate shipping cost using polymorphism (OCP)
            double shippingCost = strategy.calculateCost(order.weight());
            
            // Save the order to the database (SRP - delegates persistence)
            database.save(order);
            
            // Send confirmation (SRP - delegates notifications)
            notificationService.sendConfirmation(order);
            
            // Return total cost
            return order.amount() + shippingCost;
        }
    }
}
