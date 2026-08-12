package practice.e_commerce;

import java.math.BigDecimal;

public interface DiscountStrategy {
    public BigDecimal applyDiscount(BigDecimal originalAmount);
}
