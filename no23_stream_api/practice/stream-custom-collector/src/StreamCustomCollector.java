package no23_stream_api.practice.stream_custom_collector;

import java.util.stream.Collector;

/**
 * Starter template for creating a custom Stream Collector.
 */
public class StreamCustomCollector {

    /**
     * Returns a Collector that calculates the standard deviation of a stream of Double values.
     * 
     * Formula for Standard Deviation (population):
     * Mean = Sum / Count
     * Variance = (SumOfSquares / Count) - (Mean^2)
     * Standard Deviation = Math.sqrt(Variance)
     * 
     * If there are no elements or only 1 element, standard deviation should be 0.0.
     *
     * @return the Collector computing the standard deviation
     */
    public static Collector<Double, ?, Double> toStandardDeviation() {
        // TODO: Implement custom collector using Collector.of()
        // Hint: Create a helper accumulator class to track count, sum, and sumOfSquares.
        return null;
    }
}
