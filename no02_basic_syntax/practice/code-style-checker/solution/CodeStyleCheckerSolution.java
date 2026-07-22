package no02_basic_syntax.practice.code_style_checker;

/**
 * Reference solution for CodeStyleCheckerSolution.
 * 
 * This utility uses regular expressions to validate standard Java naming conventions.
 */
public class CodeStyleCheckerSolution {

    /**
     * Validates if a class name follows PascalCase.
     * Rule: Must start with an uppercase letter, followed by letters or digits.
     * 
     * Regex explanation:
     * - `^[A-Z]`   : Must start with a capital letter from A to Z.
     * - `[a-zA-Z0-9]*$` : Can be followed by any number of uppercase/lowercase letters or digits.
     */
    public static boolean isValidClassName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.matches("^[A-Z][a-zA-Z0-9]*$");
    }

    /**
     * Validates if a method name follows camelCase.
     * Rule: Must start with a lowercase letter, followed by letters or digits.
     * 
     * Regex explanation:
     * - `^[a-z]`   : Must start with a lowercase letter from a to z.
     * - `[a-zA-Z0-9]*$` : Can be followed by any number of uppercase/lowercase letters or digits.
     */
    public static boolean isValidMethodName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.matches("^[a-z][a-zA-Z0-9]*$");
    }

    /**
     * Validates if a variable name follows camelCase.
     * Rule: Must start with a lowercase letter, followed by letters or digits.
     * 
     * Since standard Java variables and methods share the same camelCase convention,
     * the implementation details and regex match the method validation.
     */
    public static boolean isValidVariableName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.matches("^[a-z][a-zA-Z0-9]*$");
    }

    /**
     * Validates if a constant name follows UPPER_SNAKE_CASE.
     * Rule: Must start with an uppercase letter, followed by uppercase letters, digits, or underscores.
     * No consecutive underscores, no leading or trailing underscores.
     * 
     * Regex explanation:
     * - `^[A-Z][A-Z0-9]*` : Starts with an uppercase letter, followed by any uppercase letters or digits.
     * - `(_[A-Z0-9]+)*$`  : Can be followed by groups of: an underscore followed by one or more uppercase letters or digits.
     *                       This ensures no trailing underscore (since a group must end with characters, not `_`)
     *                       and no consecutive underscores (since the separator is a single `_`).
     */
    public static boolean isValidConstantName(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        return name.matches("^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$");
    }
}
