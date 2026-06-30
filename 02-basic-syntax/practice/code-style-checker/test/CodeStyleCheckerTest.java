/**
 * Test runner for CodeStyleChecker.
 * Verifies that the naming convention checkers work correctly under various conditions.
 */
public class CodeStyleCheckerTest {

    public static void main(String[] args) {
        try {
            testClassName();
            testMethodName();
            testVariableName();
            testConstantName();
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
            throw new AssertionError("Assertion failed: Expected TRUE but got FALSE. " + message);
        }
    }

    private static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError("Assertion failed: Expected FALSE but got TRUE. " + message);
        }
    }

    private static void testClassName() {
        // Valid class names (PascalCase)
        assertTrue(CodeStyleChecker.isValidClassName("StudentService"), "StudentService should be valid");
        assertTrue(CodeStyleChecker.isValidClassName("OrderService123"), "OrderService123 should be valid");
        assertTrue(CodeStyleChecker.isValidClassName("A"), "A should be valid");

        // Invalid class names
        assertFalse(CodeStyleChecker.isValidClassName("studentService"), "studentService should be invalid (starts lowercase)");
        assertFalse(CodeStyleChecker.isValidClassName("Student_Service"), "Student_Service should be invalid (contains underscore)");
        assertFalse(CodeStyleChecker.isValidClassName("123Service"), "123Service should be invalid (starts with digit)");
        assertFalse(CodeStyleChecker.isValidClassName(""), "Empty string should be invalid");
        assertFalse(CodeStyleChecker.isValidClassName(null), "Null should be invalid");
    }

    private static void testMethodName() {
        // Valid method names (camelCase)
        assertTrue(CodeStyleChecker.isValidMethodName("calculateTotal"), "calculateTotal should be valid");
        assertTrue(CodeStyleChecker.isValidMethodName("getName"), "getName should be valid");
        assertTrue(CodeStyleChecker.isValidMethodName("run123"), "run123 should be valid");
        assertTrue(CodeStyleChecker.isValidMethodName("x"), "x should be valid");

        // Invalid method names
        assertFalse(CodeStyleChecker.isValidMethodName("CalculateTotal"), "CalculateTotal should be invalid (starts uppercase)");
        assertFalse(CodeStyleChecker.isValidMethodName("calculate_total"), "calculate_total should be invalid (contains underscore)");
        assertFalse(CodeStyleChecker.isValidMethodName("123run"), "123run should be invalid (starts with digit)");
        assertFalse(CodeStyleChecker.isValidMethodName(""), "Empty string should be invalid");
        assertFalse(CodeStyleChecker.isValidMethodName(null), "Null should be invalid");
    }

    private static void testVariableName() {
        // Valid variable names (camelCase)
        assertTrue(CodeStyleChecker.isValidVariableName("studentName"), "studentName should be valid");
        assertTrue(CodeStyleChecker.isValidVariableName("age"), "age should be valid");
        assertTrue(CodeStyleChecker.isValidVariableName("x"), "x should be valid");

        // Invalid variable names
        assertFalse(CodeStyleChecker.isValidVariableName("StudentName"), "StudentName should be invalid (starts uppercase)");
        assertFalse(CodeStyleChecker.isValidVariableName("student_name"), "student_name should be invalid (contains underscore)");
        assertFalse(CodeStyleChecker.isValidVariableName(""), "Empty string should be invalid");
        assertFalse(CodeStyleChecker.isValidVariableName(null), "Null should be invalid");
    }

    private static void testConstantName() {
        // Valid constant names (UPPER_SNAKE_CASE)
        assertTrue(CodeStyleChecker.isValidConstantName("MAX_RETRY_COUNT"), "MAX_RETRY_COUNT should be valid");
        assertTrue(CodeStyleChecker.isValidConstantName("PI"), "PI should be valid");
        assertTrue(CodeStyleChecker.isValidConstantName("DEFAULT_TIMEOUT"), "DEFAULT_TIMEOUT should be valid");
        assertTrue(CodeStyleChecker.isValidConstantName("MAX_2"), "MAX_2 should be valid");

        // Invalid constant names
        assertFalse(CodeStyleChecker.isValidConstantName("max_retry_count"), "max_retry_count should be invalid (lowercase)");
        assertFalse(CodeStyleChecker.isValidConstantName("MaxRetryCount"), "MaxRetryCount should be invalid (mixed case)");
        assertFalse(CodeStyleChecker.isValidConstantName("MAX_RETRY_COUNT_"), "MAX_RETRY_COUNT_ should be invalid (trailing underscore)");
        assertFalse(CodeStyleChecker.isValidConstantName("_MAX_RETRY"), "_MAX_RETRY should be invalid (leading underscore)");
        assertFalse(CodeStyleChecker.isValidConstantName("MAX__RETRY"), "MAX__RETRY should be invalid (consecutive underscores)");
        assertFalse(CodeStyleChecker.isValidConstantName(""), "Empty string should be invalid");
        assertFalse(CodeStyleChecker.isValidConstantName(null), "Null should be invalid");
    }
}
