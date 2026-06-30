package refactoredcollectionusage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefactoredCollectionUsageSolution {

    /**
     * Returns an immutable list of countries.
     * Refactored: Programmed to interface 'List' and initialized cleanly with List.of().
     */
    public static List<String> getImmutableCountries() {
        return List.of("USA", "Canada", "Mexico", "Japan", "Germany");
    }

    /**
     * Returns an immutable map.
     * Refactored: Programmed to interface 'Map' and initialized cleanly with Map.of().
     */
    public static Map<String, Integer> getImmutablePopulations() {
        return Map.of("Apple", 1, "Banana", 2, "Cherry", 3);
    }

    /**
     * Instantiates and returns a HashMap of the specified expected size.
     * The map is initialized with the optimal initial capacity to prevent resizing.
     * Formula: capacity = (expectedSize / 0.75) + 1.
     */
    public static Map<Integer, String> createOptimalHashMap(int expectedSize) {
        if (expectedSize < 0) {
            throw new IllegalArgumentException("Expected size cannot be negative");
        }
        // Calculate the threshold-safe capacity to prevent the Map from resizing when filled.
        int initialCapacity = (int) (expectedSize / 0.75f) + 1;
        return new HashMap<>(initialCapacity);
    }

    /**
     * Converts a List of Strings to a String Array.
     * Refactored: Uses type-safe modern conversion.
     */
    public static String[] convertListToArray(List<String> list) {
        if (list == null) {
            return new String[0];
        }
        // Java 11+ optimal list-to-array conversion method.
        // It performs better than passing a pre-sized array like new String[list.size()] 
        // due to JVM internal optimizations that avoid zero-filling redundant arrays.
        return list.toArray(String[]::new);
    }
}
