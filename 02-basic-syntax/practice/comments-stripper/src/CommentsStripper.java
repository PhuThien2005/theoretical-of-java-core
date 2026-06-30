/**
 * A utility class to strip single-line and multi-line comments from Java source code.
 */
public class CommentsStripper {

    /**
     * Strips all single-line (//) and multi-line (/* ... *\/) comments from the given 
     * Java source code string.
     * 
     * IMPORTANT: 
     * - Do NOT strip comment markers when they appear inside double-quoted string literals 
     *   (e.g., "http://example.com" or "This is a /* comment *\/ inside a string").
     * - Do NOT strip comment markers when they appear inside single-quoted character literals
     *   (e.g., '//' or '/*').
     * - Keep newline characters from single-line comments to preserve line endings for subsequent code lines.
     *
     * @param source the Java source code string
     * @return the source code with comments stripped
     */
    public static String stripComments(String source) {
        // TODO: Implement the comments stripping utility.
        // Hint: You can use a character-by-character scan (state machine) to differentiate 
        // between standard code, comments, string literals, and character literals.
        return null;
    }
}
