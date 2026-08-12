package practice.e_commerce;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Product {
    private String id;
    private String name;
    private BigDecimal price;

    public Product(String name, double price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.setPrice(new BigDecimal(String.valueOf(price)));
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        try {
            if (price.doubleValue() <= 0) {
                throw new IllegalArgumentException("Price don't allowed to be <= 0!");
            } else {
                this.price = price;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public abstract BigDecimal calculateFinalPrice();
}