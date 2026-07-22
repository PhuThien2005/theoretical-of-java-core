package no19_collections_framework.practice.bracket_matching_stack;

import java.util.Deque;
import java.util.ArrayDeque;

/**
 * Reference solution for BracketMatchingStackSolution.
 * 
 * Best Practice:
 * - Use `java.util.Deque` (ArrayDeque implementation) instead of legacy `Stack`
 *   to avoid synchronization overhead and improve stack processing speed.
 */
public class BracketMatchingStackSolution {

    public static boolean isValid(String expression) {
        if (expression == null) {
            return false;
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // Push opening brackets to stack
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }
            // Check closing brackets
            else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) {
                    return false; // Closing bracket with no matching opening bracket
                }
                
                char open = stack.pop();
                if (!matches(open, c)) {
                    return false; // Mismatched brackets
                }
            }
        }

        // If stack is empty, all brackets were correctly matched
        return stack.isEmpty();
    }

    private static boolean matches(char open, char close) {
        return (open == '(' && close == ')') ||
               (open == '{' && close == '}') ||
               (open == '[' && close == ']');
    }
}
