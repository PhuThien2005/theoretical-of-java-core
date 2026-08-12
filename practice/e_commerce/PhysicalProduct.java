package practice.e_commerce;

import java.math.BigDecimal;

import practice.e_commerce.Product;

public class PhysicalProduct extends Product {
    // unit: kilogram
    private double weight;
    private BigDecimal shippingFeePerKg;

    public PhysicalProduct(String name, double price, double weight, double shippingFeePerKg) {
        super(name, price);
        setWeight(weight);
        this.shippingFeePerKg = new BigDecimal(String.valueOf(shippingFeePerKg));
    }

    public void setWeight(double weight) {
        try {
            if (weight <= 0) {
                throw new IllegalArgumentException("Weight don't allowed to be <= 0!");
            } else {
                this.weight = weight;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public double getWeight() {
        return weight;
    }

    public BigDecimal getShippingFeePerKg() {
        return shippingFeePerKg;
    }

    public BigDecimal calculateFinalPrice() {
        return this.getPrice().add(shippingFeePerKg.multiply(new BigDecimal(String.valueOf(weight))));
    }
}
