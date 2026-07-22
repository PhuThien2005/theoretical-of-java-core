package no22_functional_interface.practice.functional_transformer;

import java.util.function.Function;

/**
 * Starter template for data transformation pipelines using Function composition.
 */
public class FunctionalTransformer {

    /**
     * Returns a function that trims leading and trailing whitespace from a String.
     * If the string is null, return an empty string "".
     */
    public static Function<String, String> trim() {
        // TODO: Implement trimming function
        return null;
    }

    /**
     * Returns a function that converts a String to lower case.
     * If the string is null, return an empty string "".
     */
    public static Function<String, String> toLowerCase() {
        // TODO: Implement lowercase function
        return null;
    }

    /**
     * Returns a function that replaces all occurrences of a space " " with the replacement string.
     * If the string is null, return an empty string "".
     */
    public static Function<String, String> replaceSpaces(String replacement) {
        // TODO: Implement space replacement function
        return null;
    }

    /**
     * Composes the three functions above into a sanitizer pipeline using .andThen():
     * 1. Trim the input.
     * 2. Convert to lower case.
     * 3. Replace spaces with the specified replacement string (slug separator).
     *
     * @param replacement the string to replace spaces with
     * @return the composed Function slug sanitizer
     */
    public static Function<String, String> buildSanitizerPipeline(String replacement) {
        // TODO: Combine using .andThen()
        return null;
    }
}
