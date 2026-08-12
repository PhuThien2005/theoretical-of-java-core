package practice.e_commerce;

import java.math.BigDecimal;

public class FixedAmountDiscount implements DiscountStrategy {
    // vietnamese vnd
    private BigDecimal discountAmount;

    public FixedAmountDiscount(double discountAmount) {
        this.discountAmount = new BigDecimal(String.valueOf(discountAmount));
    }

    public BigDecimal applyDiscount(BigDecimal originalAmount) {
        return (discountAmount.compareTo(originalAmount) == 1) ? new BigDecimal(0)
                : originalAmount.subtract(discountAmount);
    }
}
