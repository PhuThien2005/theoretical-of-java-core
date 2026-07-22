package no06_control_flow.practice.fizzbuzz_extended;

import java.util.Arrays;
import java.util.List;

/**
 * Test runner for FizzBuzzExtended.
 */
public class FizzBuzzExtendedTest {

    public static void main(String[] args) {
        try {
            testStandardFizzBuzz();
            testSkipCondition();
            testStopCondition();
            testCombinedSkipAndStop();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(List<String> expected, List<String> actual, String message) {
        if (expected == null && actual == null) return;
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + "\nExpected: " + expected + "\nActual:   " + actual);
        }
    }

    private static void testStandardFizzBuzz() {
        // No skip divisor (set to 0 or large number) and no stop number (set to large number)
        List<String> expected = Arrays.asList("1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz");
        List<String> actual = FizzBuzzExtended.fizzBuzz(1, 10, 0, 999);
        assertEquals(expected, actual, "Standard FizzBuzz 1-10");
    }

    private static void testSkipCondition() {
        // Skip numbers divisible by 4 (e.g. 4 and 8 should be missing)
        // Range 1 to 10
        List<String> expected = Arrays.asList("1", "2", "Fizz", "Buzz", "Fizz", "7", "Fizz", "Buzz");
        List<String> actual = FizzBuzzExtended.fizzBuzz(1, 10, 4, 999);
        assertEquals(expected, actual, "FizzBuzz skipping multiples of 4");
    }

    private static void testStopCondition() {
        // Stop at 6 (so 6, 7, 8, 9, 10 should not be evaluated or added)
        List<String> expected = Arrays.asList("1", "2", "Fizz", "4", "Buzz");
        List<String> actual = FizzBuzzExtended.fizzBuzz(1, 10, 0, 6);
        assertEquals(expected, actual, "FizzBuzz stopping at 6");
    }

    private static void testCombinedSkipAndStop() {
        // Range 1 to 15, skip multiples of 3, stop at 10
        // Numbers: 1, 2, 3 (skip), 4, 5, 6 (skip), 7, 8, 9 (skip), 10 (stop) -> stops before adding 10
        // Expected: "1", "2", "4", "Buzz" (5), "7", "8"
        List<String> expected = Arrays.asList("1", "2", "4", "Buzz", "7", "8");
        List<String> actual = FizzBuzzExtended.fizzBuzz(1, 15, 3, 10);
        assertEquals(expected, actual, "FizzBuzz skip multiples of 3, stop at 10");
    }
}
