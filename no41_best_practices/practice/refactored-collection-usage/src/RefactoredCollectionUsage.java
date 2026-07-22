package refactoredcollectionusage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefactoredCollectionUsage {

    /**
     * Returns an immutable list of countries: "USA", "Canada", "Mexico", "Japan", "Germany".
     * Legacy code:
     * <code>
     * ArrayList<String> list = new ArrayList<>();
     * list.add("USA");
     * list.add("Canada");
     * list.add("Mexico");
     * list.add("Japan");
     * list.add("Germany");
     * return list;
     * </code>
     */
    public static List<String> getImmutableCountries() {
        // TODO: Refactor using interface type declarations and modern immutable factories.
        return null;
    }

    /**
     * Returns an immutable map of item to price: "Apple" -> 1, "Banana" -> 2, "Cherry" -> 3.
     * Legacy code:
     * <code>
     * HashMap<String, Integer> map = new HashMap<>();
     * map.put("Apple", 1);
     * map.put("Banana", 2);
     * map.put("Cherry", 3);
     * return map;
     * </code>
     */
    public static Map<String, Integer> getImmutablePopulations() {
        // TODO: Refactor using interface declarations and modern immutable map factories.
        return null;
    }

    /**
     * Instantiates and returns a HashMap of the specified expected size.
     * The map must be initialized with the optimal initial capacity to prevent resizing.
     * Formula: capacity = (expectedSize / 0.75) + 1.
     */
    public static Map<Integer, String> createOptimalHashMap(int expectedSize) {
        // TODO: Allocate the HashMap with optimal initial capacity.
        return null;
    }

    /**
     * Converts a List of Strings to a String Array.
     * Legacy code used slow sizing:
     * <code>
     * return list.toArray(new String[list.size()]);
     * </code>
     */
    public static String[] convertListToArray(List<String> list) {
        // TODO: Refactor using modern optimal array conversion (toArray(new String[0]) or using array constructor reference).
        return null;
    }
}
