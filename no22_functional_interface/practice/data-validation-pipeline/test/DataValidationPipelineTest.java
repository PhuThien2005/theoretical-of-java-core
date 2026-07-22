package no22_functional_interface.practice.data_validation_pipeline;

import java.util.function.Predicate;

/**
 * Test runner for DataValidationPipeline.
 */
public class DataValidationPipelineTest {

    public static void main(String[] args) {
        try {
            testMinLengthPredicate();
            testContainsDigitPredicate();
            testContainsSpecialCharPredicate();
            testPasswordPolicyPipeline();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testMinLengthPredicate() {
        Predicate<String> pred = DataValidationPipeline.minLength(5);
        assertTrue(pred.test("hello"), "hello has length 5");
        assertTrue(pred.test("longerstring"), "longer has length > 5");
        assertFalse(pred.test("tiny"), "tiny has length < 5");
        assertFalse(pred.test(null), "Null should fail minLength");
    }

    private static void testContainsDigitPredicate() {
        Predicate<String> pred = DataValidationPipeline.containsDigit();
        assertTrue(pred.test("pass1"), "pass1 contains digit");
        assertTrue(pred.test("123"), "123 contains digit");
        assertFalse(pred.test("password"), "password has no digits");
        assertFalse(pred.test(null), "Null should fail containsDigit");
    }

    private static void testContainsSpecialCharPredicate() {
        Predicate<String> pred = DataValidationPipeline.containsSpecialChar();
        assertTrue(pred.test("hello!"), "hello! has special char");
        assertTrue(pred.test("pass#word"), "pass#word has special char");
        assertFalse(pred.test("pass123"), "pass123 has no special char");
        assertFalse(pred.test(null), "Null should fail containsSpecialChar");
    }

    private static void testPasswordPolicyPipeline() {
        Predicate<String> policy = DataValidationPipeline.buildPasswordPolicy();

        // Valid passwords
        assertTrue(policy.test("StrongP@ss1"), "StrongP@ss1 matches policy");
        assertTrue(policy.test("abc!12345"), "abc!12345 matches policy");

        // Invalid passwords
        assertFalse(policy.test("Short1!"), "Short1! is too short (7 chars)");
        assertFalse(policy.test("NoDigits!"), "NoDigits! contains no digits");
        assertFalse(policy.test("NoSpecialChar1"), "NoSpecialChar1 contains no special char");
        assertFalse(policy.test(null), "Null password fails");
    }
}
