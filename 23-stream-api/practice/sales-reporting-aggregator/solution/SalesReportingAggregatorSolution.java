import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Reference solution for SalesReportingAggregatorSolution.
 * 
 * Grouping and Partitioning:
 * - `Collectors.groupingBy(classifier, downstream)` partitions elements into keys according to classifier,
 *   and applies downstream collector to values.
 * - `Collectors.partitioningBy(predicate)` separates elements into true/false keys.
 */
public class SalesReportingAggregatorSolution {

    public static Map<String, Double> sumByCategory(List<Sale> sales) {
        if (sales == null || sales.isEmpty()) {
            return new HashMap<>();
        }

        return sales.stream()
            .collect(Collectors.groupingBy(
                Sale::getCategory,
                Collectors.summingDouble(Sale::getAmount)
            ));
    }

    public static Map<Boolean, List<Sale>> partitionHighValueSales(List<Sale> sales, double threshold) {
        if (sales == null || sales.isEmpty()) {
            Map<Boolean, List<Sale>> empty = new HashMap<>();
            empty.put(true, Collections.emptyList());
            empty.put(false, Collections.emptyList());
            return empty;
        }

        return sales.stream()
            .collect(Collectors.partitioningBy(
                s -> s.getAmount() >= threshold
            ));
    }
}

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
