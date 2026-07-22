package no18_generics.practice.wildcard_utility;

import java.util.List;
import java.util.ArrayList;

/**
 * A utility class to practice Generics Wildcards (PECS rule).
 */
public class WildcardUtility {

    /**
     * Copies all elements from the source list to the destination list.
     * 
     * PECS Rule:
     * - Source list is a Producer (extends): List<? extends T>
     * - Destination list is a Consumer (super): List<? super T>
     *
     * @param source the source list containing elements of type extending T
     * @param destination the destination list consuming elements of type T or its superclasses
     * @param <T> the base type
     */
    public static <T> void copy(List<? extends T> source, List<? super T> destination) {
        // TODO: Implement copying from source to destination
    }

    /**
     * Filters a list to find all elements strictly greater than the given threshold.
     * 
     * Bounds:
     * - T must be comparable to itself or its superclasses: T extends Comparable<? super T>
     * - The list parameter produces elements of T: List<? extends T>
     *
     * @param list the list to filter
     * @param threshold the comparison threshold
     * @param <T> the comparable base type
     * @return a new list containing elements greater than the threshold
     */
    public static <T extends Comparable<? super T>> List<T> findGreaterThan(List<? extends T> list, T threshold) {
        // TODO: Implement filtering
        return null;
    }
}
