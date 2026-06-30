/**
 * Reference solution for GenericBillingSystemSolution.
 */
public class GenericBillingSystemSolution {

    public static double calculateTotal(Billable[] items) {
        if (items == null) {
            return 0.0;
        }
        double total = 0.0;
        for (Billable item : items) {
            if (item != null) {
                total += item.getPrice();
            }
        }
        return total;
    }
}

/**
 * Interface establishing a common type contract for pricing and descriptions.
 * Any class implementing Billable can be processed by the billing engine.
 */
interface Billable {
    double getPrice();
    String getDescription();
}

/**
 * Abstract class sharing code for discount computations.
 * It is abstract because "DiscountedItem" is a concept, not a concrete entity.
 */
abstract class DiscountedItem implements Billable {
    private double discountPercentage;

    public DiscountedItem(double discountPercentage) {
        if (discountPercentage < 0.0 || discountPercentage > 100.0) {
            throw new IllegalArgumentException("Discount percentage must be between 0 and 100");
        }
        this.discountPercentage = discountPercentage;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    protected double getDiscountedPrice(double originalPrice) {
        return originalPrice * (1.0 - (discountPercentage / 100.0));
    }
}

/**
 * PhysicalProduct extends the abstract DiscountedItem.
 * It reuses the discount logic and adds shipping cost calculation.
 */
class PhysicalProduct extends DiscountedItem {
    private String name;
    private double basePrice;
    private double shippingCost;

    public PhysicalProduct(String name, double basePrice, double discountPercentage, double shippingCost) {
        super(discountPercentage);
        if (name == null || basePrice < 0.0 || shippingCost < 0.0) {
            throw new IllegalArgumentException("Invalid product parameters");
        }
        this.name = name;
        this.basePrice = basePrice;
        this.shippingCost = shippingCost;
    }

    @Override
    public double getPrice() {
        return getDiscountedPrice(basePrice) + shippingCost;
    }

    @Override
    public String getDescription() {
        return name;
    }
}

/**
 * ServiceSubscription implements Billable directly because it does not share
 * any hierarchical behavior or attributes with DiscountedItem.
 */
class ServiceSubscription implements Billable {
    private String serviceName;
    private double monthlyFee;

    public ServiceSubscription(String serviceName, double monthlyFee) {
        if (serviceName == null || monthlyFee < 0.0) {
            throw new IllegalArgumentException("Invalid subscription parameters");
        }
        this.serviceName = serviceName;
        this.monthlyFee = monthlyFee;
    }

    @Override
    public double getPrice() {
        return monthlyFee;
    }

    @Override
    public String getDescription() {
        return serviceName;
    }
}
