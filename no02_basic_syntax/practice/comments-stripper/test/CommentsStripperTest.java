package no02_basic_syntax.practice.comments_stripper;

/**
 * Test runner for CommentsStripper.
 * Verifies that single-line and multi-line comments are correctly removed,
 * while literals and structures are preserved.
 */
public class CommentsStripperTest {

    public static void main(String[] args) {
        try {
            testSingleLineComment();
            testMultiLineComment();
            testCommentsInStringLiterals();
            testCommentsInCharLiterals();
            testMixedComments();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertEquals(String expected, String actual, String message) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected == null || !expected.equals(actual)) {
            throw new AssertionError(message + "\nExpected: [" + expected + "]\nActual:   [" + actual + "]");
        }
    }

    private static void testSingleLineComment() {
        String source = "int x = 10; // This is a comment\nint y = 20;";
        String expected = "int x = 10; \nint y = 20;";
        assertEquals(expected, CommentsStripper.stripComments(source), "Should strip single line comment");
    }

    private static void testMultiLineComment() {
        String source = "int x = 10; /* This is a \n multi-line comment */\nint y = 20;";
        String expected = "int x = 10; \nint y = 20;";
        assertEquals(expected, CommentsStripper.stripComments(source), "Should strip multi-line comment");
    }

    private static void testCommentsInStringLiterals() {
        String source = "String url = \"https://google.com\"; /* comment */\nString s = \"nested /*comment*/ and //comment here\";";
        String expected = "String url = \"https://google.com\"; \nString s = \"nested /*comment*/ and //comment here\";";
        assertEquals(expected, CommentsStripper.stripComments(source), "Should not strip comments inside string literals");
    }

    private static void testCommentsInCharLiterals() {
        String source = "char c1 = '/';\nchar c2 = '*';\n// comment\nchar c3 = '\\'';";
        String expected = "char c1 = '/';\nchar c2 = '*';\n\nchar c3 = '\\'';";
        assertEquals(expected, CommentsStripper.stripComments(source), "Should not strip comment markers inside character literals");
    }

    private static void testMixedComments() {
        String source = "// Start of class\n" +
                        "public class Demo {\n" +
                        "    /*\n" +
                        "     * main method\n" +
                        "     */\n" +
                        "    public static void main(String[] args) {\n" +
                        "        System.out.println(\"Hello // world\"); // print statement\n" +
                        "    }\n" +
                        "}";
        String expected = "\n" +
                          "public class Demo {\n" +
                          "    \n" +
                          "    public static void main(String[] args) {\n" +
                          "        System.out.println(\"Hello // world\"); \n" +
                          "    }\n" +
                          "}";
        assertEquals(expected, CommentsStripper.stripComments(source), "Should correctly handle mixed comments and strings");
    }
}
