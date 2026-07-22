package no18_generics.practice.wildcard_utility;

import java.util.List;
import java.util.ArrayList;

/**
 * Reference solution for WildcardUtilitySolution.
 * 
 * PECS Rule (Producer Extends, Consumer Super):
 * - If you only read from a collection, it is a Producer. Use `? extends T`.
 * - If you only write to a collection, it is a Consumer. Use `? super T`.
 */
public class WildcardUtilitySolution {

    /**
     * Copies all elements from source to destination.
     * Source `List<? extends T>` produces T (we can read T from it).
     * Destination `List<? super T>` consumes T (we can write T to it).
     */
    public static <T> void copy(List<? extends T> source, List<? super T> destination) {
        if (source == null || destination == null) {
            return;
        }
        for (T item : source) {
            destination.add(item);
        }
    }

    /**
     * Filters elements strictly greater than threshold.
     * The input list produces elements of type extending T: `List<? extends T>`.
     * `T extends Comparable<? super T>` allows comparing type T against itself or parent classes (e.g. comparing Integer against Number).
     */
    public static <T extends Comparable<? super T>> List<T> findGreaterThan(List<? extends T> list, T threshold) {
        List<T> result = new ArrayList<>();
        if (list == null || threshold == null) {
            return result;
        }
        for (T item : list) {
            // item.compareTo(threshold) > 0 means item > threshold
            if (item.compareTo(threshold) > 0) {
                result.add(item);
            }
        }
        return result;
    }
}
