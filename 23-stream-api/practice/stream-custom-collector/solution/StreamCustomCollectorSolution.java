import java.util.stream.Collector;

/**
 * Reference solution for StreamCustomCollectorSolution.
 * 
 * Custom Collectors:
 * - A custom collector is defined by:
 *   1. Supplier: creates a new mutable accumulator container.
 *   2. Accumulator: folds a new element into the container.
 *   3. Combiner: merges two container results (for parallel streams).
 *   4. Finisher: converts the container to the final result type.
 */
public class StreamCustomCollectorSolution {

    public static Collector<Double, ?, Double> toStandardDeviation() {
        return Collector.of(
            StatsAccumulator::new,         // Supplier
            StatsAccumulator::accept,      // Accumulator
            StatsAccumulator::combine,     // Combiner
            StatsAccumulator::getStdDev    // Finisher
        );
    }

    /**
     * Mutable accumulator to track stream statistics.
     */
    private static class StatsAccumulator {
        private long count = 0;
        private double sum = 0.0;
        private double sumOfSquares = 0.0;

        public void accept(double value) {
            count++;
            sum += value;
            sumOfSquares += value * value;
        }

        public StatsAccumulator combine(StatsAccumulator other) {
            this.count += other.count;
            this.sum += other.sum;
            this.sumOfSquares += other.sumOfSquares;
            return this;
        }

        public double getStdDev() {
            if (count <= 1) {
                return 0.0;
            }
            double mean = sum / count;
            double variance = (sumOfSquares / count) - (mean * mean);
            // Ensure variance is not negative due to floating-point rounding errors
            return Math.sqrt(Math.max(0.0, variance));
        }
    }
}
