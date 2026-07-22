package no22_functional_interface.practice.data_validation_pipeline;

import java.util.function.Predicate;

/**
 * Reference solution for DataValidationPipelineSolution.
 * 
 * Predicate features:
 * - A Predicate is a functional interface representing a condition.
 * - `.and(Predicate)` chains two predicates together requiring both to be true.
 * - `.or(Predicate)` chains two predicates requiring either to be true.
 * - `.negate()` returns a predicate that represents the logical negation.
 */
public class DataValidationPipelineSolution {

    public static Predicate<String> minLength(int length) {
        return s -> s != null && s.length() >= length;
    }

    public static Predicate<String> containsDigit() {
        return s -> s != null && s.matches(".*\\d.*");
    }

    public static Predicate<String> containsSpecialChar() {
        return s -> s != null && s.matches(".*[!@#$%^&*()].*");
    }

    public static Predicate<String> buildPasswordPolicy() {
        // Composes: length >= 8 AND contains digit AND contains special character
        return minLength(8)
            .and(containsDigit())
            .and(containsSpecialChar());
    }
}
