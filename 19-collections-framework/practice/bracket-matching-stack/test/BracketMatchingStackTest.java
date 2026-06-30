/**
 * Test runner for BracketMatchingStack.
 */
public class BracketMatchingStackTest {

    public static void main(String[] args) {
        try {
            testBalancedExpressions();
            testUnbalancedExpressions();
            testNonBracketContent();
            testEdgeCases();
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

    private static void testBalancedExpressions() {
        assertTrue(BracketMatchingStack.isValid("()"), "()");
        assertTrue(BracketMatchingStack.isValid("[]"), "[]");
        assertTrue(BracketMatchingStack.isValid("{}"), "{}");
        assertTrue(BracketMatchingStack.isValid("{[()]}"), "{[()]}");
        assertTrue(BracketMatchingStack.isValid("()[]{}"), "()[]{}");
    }

    private static void testUnbalancedExpressions() {
        assertFalse(BracketMatchingStack.isValid("("), "(");
        assertFalse(BracketMatchingStack.isValid(")"), ")");
        assertFalse(BracketMatchingStack.isValid("([)"), "([)");
        assertFalse(BracketMatchingStack.isValid("([)]"), "([)]");
        assertFalse(BracketMatchingStack.isValid("{[()]}("), "{[()]}(\nUnopened tail");
    }

    private static void testNonBracketContent() {
        assertTrue(BracketMatchingStack.isValid("((a + b) * c)"), "Text contents with balanced brackets");
        assertTrue(BracketMatchingStack.isValid("public static void main(String[] args) { System.out.println(); }"), "Java code snippet");
        assertFalse(BracketMatchingStack.isValid("public static void main(String[] args) { System.out.println(); "), "Snippet with missing ending brace");
    }

    private static void testEdgeCases() {
        assertTrue(BracketMatchingStack.isValid(""), "Empty string is balanced");
        assertFalse(BracketMatchingStack.isValid(null), "Null expression is not valid");
    }
}
