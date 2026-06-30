import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test runner for WildcardUtility.
 */
public class WildcardUtilityTest {

    public static void main(String[] args) {
        try {
            testCopyWildcard();
            testFindGreaterThan();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + " (Expected: " + expected + ", Actual: " + actual + ")");
        }
    }

    private static void testCopyWildcard() {
        // Source is List of Integers (subclass of Number)
        List<Integer> source = Arrays.asList(1, 2, 3);
        // Destination is List of Numbers (superclass of Integer)
        List<Number> destination = new ArrayList<>();

        // Copy: copies Integer elements into Number list
        WildcardUtility.copy(source, destination);

        assertEquals(3, destination.size(), "Destination size should be 3");
        assertEquals(1, destination.get(0), "First copied element");
        assertEquals(2, destination.get(1), "Second copied element");
        assertEquals(3, destination.get(2), "Third copied element");
    }

    private static void testFindGreaterThan() {
        // Test with Integers
        List<Integer> numbers = Arrays.asList(10, 5, 20, 15, 30);
        List<Integer> resultNums = WildcardUtility.findGreaterThan(numbers, 15);
        assertEquals(Arrays.asList(20, 30), resultNums, "Numbers greater than 15");

        // Test with Strings (lexicographical comparison)
        List<String> words = Arrays.asList("apple", "orange", "banana", "pear", "grape");
        List<String> resultWords = WildcardUtility.findGreaterThan(words, "grape");
        
        // words greater than "grape": "orange", "pear" (alphabetically sorted after "grape")
        // E.g. "grape".compareTo("orange") < 0, "grape".compareTo("pear") < 0.
        // Let's assert that it contains them.
        assertEquals(2, resultWords.size(), "Words size matches");
        assertEquals(true, resultWords.contains("orange"), "Contains orange");
        assertEquals(true, resultWords.contains("pear"), "Contains pear");
    }
}
