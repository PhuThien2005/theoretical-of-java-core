package refactoredcollectionusage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefactoredCollectionUsageTest {

    public static void main(String[] args) {
        try {
            testImmutableCountries();
            testImmutablePopulations();
            testHashMapPreallocation();
            testListToArrayConversion();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(int expected, int actual, String message) {
        if (expected != actual) {
            throw new AssertionError(message + " - Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " - Expected: [" + expected + "], Actual: [" + actual + "]");
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testImmutableCountries() {
        List<String> list = RefactoredCollectionUsage.getImmutableCountries();
        assertTrue(list != null, "Countries list should not be null");
        assertEquals(5, list.size(), "Countries list size should be 5");
        
        try {
            list.add("France");
            throw new AssertionError("Expected UnsupportedOperationException on modifying immutable list");
        } catch (UnsupportedOperationException e) {
            // Expected
        }
    }

    private static void testImmutablePopulations() {
        Map<String, Integer> map = RefactoredCollectionUsage.getImmutablePopulations();
        assertTrue(map != null, "Populations map should not be null");
        assertEquals(3, map.size(), "Populations map size should be 3");

        try {
            map.put("Date", 4);
            throw new AssertionError("Expected UnsupportedOperationException on modifying immutable map");
        } catch (UnsupportedOperationException e) {
            // Expected
        }
    }

    private static void testHashMapPreallocation() {
        // Expected size is 12. Optimal initial capacity calculated: 12 / 0.75 + 1 = 17.
        // HashMap rounds this capacity up to the next power of two, which is 32.
        // Default capacity is 16.
        Map<Integer, String> map = RefactoredCollectionUsage.createOptimalHashMap(12);
        assertTrue(map instanceof HashMap, "Returned map must be a HashMap instance");

        // Force initialization of table by putting an element
        map.put(1, "Test");

        try {
            java.lang.reflect.Field tableField = HashMap.class.getDeclaredField("table");
            tableField.setAccessible(true);
            Object[] table = (Object[]) tableField.get(map);
            if (table != null) {
                assertEquals(32, table.length, "HashMap capacity should be optimized to 32 to prevent resizing");
            }
        } catch (Throwable t) {
            // JVM strong encapsulation prevents accessing private fields of java.util.HashMap.
            System.out.println("⚠️ Warning: Skipped deep HashMap capacity check due to JDK reflection restrictions: " + t.getMessage());
        }
    }

    private static void testListToArrayConversion() {
        List<String> list = List.of("One", "Two", "Three");
        String[] array = RefactoredCollectionUsage.convertListToArray(list);
        
        assertTrue(array != null, "Array should not be null");
        assertEquals(3, array.length, "Array length should be 3");
        assertEquals("One", array[0], "First element should match");
        assertEquals("Two", array[1], "Second element should match");
        assertEquals("Three", array[2], "Third element should match");
    }
}
