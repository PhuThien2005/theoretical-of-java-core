package patternmatchingrecord;

import java.util.List;

public class PatternMatchingRecordSolution {

    public enum CustomerType {
        REGULAR, VIP, ELITE
    }

    public record Customer(String name, CustomerType type) {}

    public record Item(String name, double price) {}

    public interface Discount {}

    public record NoDiscount() implements Discount {}

    public record FlatDiscount(double amount) implements Discount {}

    public record PercentageDiscount(double rate) implements Discount {}

    public record Order(String id, Customer customer, List<Item> items, Discount discount) {}

    /**
     * Calculates the final price of the order after applying discounts.
     * Throws IllegalArgumentException if any prices or discount parameters are negative,
     * or if percentage discount rate is not between 0.0 and 1.0 (inclusive).
     */
    public static double calculateFinalPrice(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        // Validate items and calculate pre-discount sum
        double sum = 0.0;
        if (order.items() != null) {
            for (Item item : order.items()) {
                if (item == null) {
                    throw new IllegalArgumentException("Item cannot be null");
                }
                if (item.price() < 0) {
                    throw new IllegalArgumentException("Item price cannot be negative: " + item.name());
                }
                sum += item.price();
            }
        }

        // Validate discount and apply using pattern matching (switch expression on Record patterns)
        Discount discount = order.discount();
        if (discount == null) {
            throw new IllegalArgumentException("Discount cannot be null");
        }

        double finalPrice = switch (discount) {
            case NoDiscount() -> sum;
            case FlatDiscount(double amount) -> {
                if (amount < 0) {
                    throw new IllegalArgumentException("Flat discount amount cannot be negative");
                }
                yield Math.max(0.0, sum - amount);
            }
            case PercentageDiscount(double rate) -> {
                if (rate < 0.0 || rate > 1.0) {
                    throw new IllegalArgumentException("Percentage discount rate must be in [0.0, 1.0]");
                }
                yield sum * (1.0 - rate);
            }
            default -> throw new IllegalArgumentException("Unsupported discount type: " + discount.getClass());
        };

        return finalPrice;
    }

    /**
     * Calculates loyalty points for the order based on nested customer and discount patterns.
     * Uses switch expressions and record patterns where applicable.
     */
    public static int calculateLoyaltyPoints(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        double finalPrice = calculateFinalPrice(order);
        if (finalPrice <= 0.0) {
            return 0;
        }

        // We use nested pattern matching in a switch expression on the Order record.
        // The cases are ordered from most specific to most general to satisfy dominance rules.
        return switch (order) {
            // Case 1: VIP customer getting a percentage discount (most specific match)
            case Order(var id, Customer(var name, var type), var items, PercentageDiscount(double rate)) when type == CustomerType.VIP -> 
                (int) (finalPrice * rate * 100);

            // Case 2: ELITE customer getting any discount
            case Order(var id, Customer(var name, var type), var items, var discount) when type == CustomerType.ELITE -> 
                (int) (finalPrice * 2.0);

            // Case 3: VIP customer getting any other discount / no discount
            case Order(var id, Customer(var name, var type), var items, var discount) when type == CustomerType.VIP -> 
                (int) (finalPrice * 0.5);

            // Case 4: General case for all other customer/discount combinations
            default -> 
                (int) (finalPrice * 0.1);
        };
    }
}
