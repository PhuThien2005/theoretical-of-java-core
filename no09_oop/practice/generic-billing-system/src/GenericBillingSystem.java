package no09_oop.practice.generic_billing_system;

/**
 * Starter template for a billing system comparing interfaces and abstract classes.
 */
public class GenericBillingSystem {

    /**
     * Computes the total price of an array of Billable items.
     * 
     * @param items array of Billable items
     * @return the total summed price
     */
    public static double calculateTotal(Billable[] items) {
        // TODO: Sum the prices of all items in the array (handle null elements safely)
        return 0.0;
    }
}

/**
 * Interface defining the capability of being billed.
 */
interface Billable {
    double getPrice();
    String getDescription();
}

/**
 * Abstract class representing an item that can receive a percentage discount.
 * This implements Billable because all discounted items are billable.
 */
abstract class DiscountedItem implements Billable {
    private double discountPercentage; // e.g. 10.0 for 10%

    public DiscountedItem(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    /**
     * Helper method to compute price after applying the discount percentage.
     */
    protected double getDiscountedPrice(double originalPrice) {
        // TODO: Calculate and return discounted price: originalPrice * (1 - discountPercentage / 100)
        return 0.0;
    }
}

/**
 * Represents a physical product that has a shipping cost and receives a discount.
 * Extends DiscountedItem.
 */
class PhysicalProduct extends DiscountedItem {
    private String name;
    private double basePrice;
    private double shippingCost;

    public PhysicalProduct(String name, double basePrice, double discountPercentage, double shippingCost) {
        super(discountPercentage);
        this.name = name;
        this.basePrice = basePrice;
        this.shippingCost = shippingCost;
    }

    // TODO: Implement getPrice() (basePrice after discount + shippingCost)
    @Override
    public double getPrice() {
        return 0.0;
    }

    // TODO: Implement getDescription() (returns name)
    @Override
    public String getDescription() {
        return null;
    }
}

/**
 * Represents a subscription service with a flat monthly rate.
 * Implements Billable directly (no discount, no shipping).
 */
class ServiceSubscription implements Billable {
    private String serviceName;
    private double monthlyFee;

    public ServiceSubscription(String serviceName, double monthlyFee) {
        this.serviceName = serviceName;
        this.monthlyFee = monthlyFee;
    }

    // TODO: Implement getPrice() (returns monthlyFee)
    @Override
    public double getPrice() {
        return 0.0;
    }

    // TODO: Implement getDescription() (returns serviceName)
    @Override
    public String getDescription() {
        return null;
    }
}
