package no02_basic_syntax.practice.comments_stripper;

/**
 * Reference solution for CommentsStripperSolution.
 * 
 * This class uses a finite state machine (FSM) to scan the source code character by character.
 * An FSM is highly suitable here because regular expressions are notoriously complex and prone
 * to errors when attempting to parse nested syntaxes (like comments inside strings/char literals,
 * and escaped quotes).
 */
public class CommentsStripperSolution {

    // Finite State Machine States
    private static final int STATE_NORMAL = 0;
    private static final int STATE_IN_STRING = 1;
    private static final int STATE_IN_CHAR = 2;
    private static final int STATE_IN_SINGLE_LINE_COMMENT = 3;
    private static final int STATE_IN_MULTI_LINE_COMMENT = 4;

    /**
     * Strips all single-line (//) and multi-line (/* ... *\/) comments from the given 
     * Java source code string, keeping comments inside string or character literals intact.
     */
    public static String stripComments(String source) {
        if (source == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int length = source.length();
        int state = STATE_NORMAL;
        int i = 0;

        while (i < length) {
            char c = source.charAt(i);

            switch (state) {
                case STATE_NORMAL:
                    // Check for start of single-line comment "//"
                    if (c == '/' && i + 1 < length && source.charAt(i + 1) == '/') {
                        state = STATE_IN_SINGLE_LINE_COMMENT;
                        i++; // Skip the second '/'
                    }
                    // Check for start of multi-line comment "/*"
                    else if (c == '/' && i + 1 < length && source.charAt(i + 1) == '*') {
                        state = STATE_IN_MULTI_LINE_COMMENT;
                        i++; // Skip the '*'
                    }
                    // Check for start of string literal "\""
                    else if (c == '"') {
                        state = STATE_IN_STRING;
                        sb.append(c);
                    }
                    // Check for start of character literal "'"
                    else if (c == '\'') {
                        state = STATE_IN_CHAR;
                        sb.append(c);
                    }
                    // Standard code character, append it
                    else {
                        sb.append(c);
                    }
                    break;

                case STATE_IN_STRING:
                    // If we encounter a backslash, the next character is escaped (e.g. \" or \\).
                    // We must append both and skip checking the next character for string termination.
                    if (c == '\\' && i + 1 < length) {
                        sb.append(c);
                        sb.append(source.charAt(i + 1));
                        i++;
                    }
                    // End of string literal
                    else if (c == '"') {
                        state = STATE_NORMAL;
                        sb.append(c);
                    }
                    // Append characters inside the string literal
                    else {
                        sb.append(c);
                    }
                    break;

                case STATE_IN_CHAR:
                    // Handle escape sequences in character literals (e.g. '\'' or '\\')
                    if (c == '\\' && i + 1 < length) {
                        sb.append(c);
                        sb.append(source.charAt(i + 1));
                        i++;
                    }
                    // End of character literal
                    else if (c == '\'') {
                        state = STATE_NORMAL;
                        sb.append(c);
                    }
                    // Append characters inside character literal
                    else {
                        sb.append(c);
                    }
                    break;

                case STATE_IN_SINGLE_LINE_COMMENT:
                    // A single-line comment ends at a line break (newline or carriage return).
                    // We must retain the line break in the output to preserve line numbering.
                    if (c == '\n' || c == '\r') {
                        state = STATE_NORMAL;
                        sb.append(c);
                    }
                    // All other characters inside the single-line comment are discarded.
                    break;

                case STATE_IN_MULTI_LINE_COMMENT:
                    // A multi-line comment ends when we encounter the close marker "*/"
                    if (c == '*' && i + 1 < length && source.charAt(i + 1) == '/') {
                        state = STATE_NORMAL;
                        i++; // Skip the '/' character
                    }
                    // All other characters inside the multi-line comment are discarded.
                    break;
            }
            i++;
        }

        return sb.toString();
    }
}
