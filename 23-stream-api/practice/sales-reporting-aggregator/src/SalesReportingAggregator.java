import java.util.List;
import java.util.Map;

/**
 * Starter template for a Sales reporting tool using grouping/partitioning Stream Collectors.
 */
public class SalesReportingAggregator {

    /**
     * Groups sales by category and sums the sales amounts for each category.
     * Return an empty map if list is null/empty.
     */
    public static Map<String, Double> sumByCategory(List<Sale> sales) {
        // TODO: Implement grouping and summing using Stream Collectors.groupingBy
        return null;
    }

    /**
     * Partitions sales into high-value (amount >= threshold) and low-value.
     * Return an empty map of boolean lists if list is null/empty.
     */
    public static Map<Boolean, List<Sale>> partitionHighValueSales(List<Sale> sales, double threshold) {
        // TODO: Implement partitioning using Stream Collectors.partitioningBy
        return null;
    }
}

/**
 * Represents a Sale transaction.
 */
class Sale {
    private final String sellerName;
    private final String category;
    private final double amount;

    public Sale(String sellerName, String category, double amount) {
        this.sellerName = sellerName;
        this.category = category;
        this.amount = amount;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}
