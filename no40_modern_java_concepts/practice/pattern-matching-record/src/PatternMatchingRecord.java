package patternmatchingrecord;

import java.util.List;

public class PatternMatchingRecord {

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
        // TODO: Validate items and discounts.
        // TODO: Sum up all item prices.
        // TODO: Apply the discount using pattern matching (instanceof or switch).
        // Return 0.0 if the discount exceeds the total price.
        return 0.0;
    }

    /**
     * Calculates loyalty points for the order based on nested customer and discount patterns.
     * Uses switch expressions and record patterns where applicable.
     */
    public static int calculateLoyaltyPoints(Order order) {
        // TODO: Implement the loyalty points rules:
        // - VIP customer and PercentageDiscount(rate): points = (int)(totalPrice * rate * 100)
        // - ELITE customer: points = (int)(totalPrice * 2.0)
        // - VIP customer (other discounts/no discount): points = (int)(totalPrice * 0.5)
        // - Other customers: points = (int)(totalPrice * 0.1)
        return 0;
    }
}
