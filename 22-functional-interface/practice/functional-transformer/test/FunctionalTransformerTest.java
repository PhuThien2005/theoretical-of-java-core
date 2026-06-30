import java.util.function.Function;

/**
 * Test runner for FunctionalTransformer.
 */
public class FunctionalTransformerTest {

    public static void main(String[] args) {
        try {
            testTrimFunction();
            testToLowerCaseFunction();
            testReplaceSpacesFunction();
            testSanitizerPipeline();
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
            throw new AssertionError(message + " (Expected: [" + expected + "], Actual: [" + actual + "])");
        }
    }

    private static void testTrimFunction() {
        Function<String, String> f = FunctionalTransformer.trim();
        assertEquals("hello", f.apply("   hello   "), "Trim hello");
        assertEquals("", f.apply("   "), "Trim spaces only");
        assertEquals("", f.apply(null), "Trim null");
    }

    private static void testToLowerCaseFunction() {
        Function<String, String> f = FunctionalTransformer.toLowerCase();
        assertEquals("hello world", f.apply("HELLO WORLD"), "Lower case");
        assertEquals("", f.apply(null), "Lower case null");
    }

    private static void testReplaceSpacesFunction() {
        Function<String, String> f = FunctionalTransformer.replaceSpaces("-");
        assertEquals("a-b-c", f.apply("a b c"), "Replace space with hyphen");
        assertEquals("abc", f.apply("abc"), "No spaces present");
        assertEquals("", f.apply(null), "Replace space null");
    }

    private static void testSanitizerPipeline() {
        // Build slugifier pipeline
        Function<String, String> slugifier = FunctionalTransformer.buildSanitizerPipeline("-");
        
        String input = "   Java Core Programming Exercises  ";
        String expected = "java-core-programming-exercises";
        assertEquals(expected, slugifier.apply(input), "Slugify input title");

        // Test with different separator
        Function<String, String> underscoreSlugifier = FunctionalTransformer.buildSanitizerPipeline("_");
        assertEquals("java_core", underscoreSlugifier.apply("  Java Core  "), "Slugify with underscore");

        // Test null input
        assertEquals("", slugifier.apply(null), "Null input slugify returns empty string");
    }
}
