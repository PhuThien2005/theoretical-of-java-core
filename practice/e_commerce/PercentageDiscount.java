package practice.e_commerce;

import java.math.BigDecimal;

import practice.e_commerce.DiscountStrategy;

public class PercentageDiscount implements DiscountStrategy {
    // 10 % , 10
    private BigDecimal percent;

    private BigDecimal maxDiscountAmount;

    public PercentageDiscount(double percent, double maxDiscountAmount) {
        this.percent = new BigDecimal(String.valueOf(percent));
        this.maxDiscountAmount = new BigDecimal(String.valueOf(maxDiscountAmount));
    }

    public BigDecimal getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public BigDecimal getPercent() {
        return percent;
    }

    public BigDecimal applyDiscount(BigDecimal originalAmount) {
        return originalAmount
                .subtract(((originalAmount.multiply(percent.divide(new BigDecimal(100)))
                        .compareTo(maxDiscountAmount) == 1)
                                ? maxDiscountAmount
                                : originalAmount.multiply(percent).divide(new BigDecimal(100))));
    }
}
