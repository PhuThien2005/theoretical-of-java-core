package practice.e_commerce;

import java.math.BigDecimal;

public class DigitalProduct extends Product {
    private String downloadLink;

    public DigitalProduct(String name, double price, String downloadLink) {
        super(name, price);
        this.downloadLink = downloadLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public BigDecimal calculateFinalPrice() {
        return this.getPrice();
    }
}
