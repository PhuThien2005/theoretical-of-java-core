import java.util.function.Predicate;

/**
 * Starter template for building validation pipelines using Predicate composition.
 */
public class DataValidationPipeline {

    /**
     * Returns a predicate that checks if a string's length is at least the specified minimum.
     * Return false if the string is null.
     */
    public static Predicate<String> minLength(int length) {
        // TODO: Implement predicate
        return null;
    }

    /**
     * Returns a predicate that checks if a string contains at least one digit (0-9).
     * Return false if the string is null.
     */
    public static Predicate<String> containsDigit() {
        // TODO: Implement predicate (hint: matches(".*\\d.*") or check characters)
        return null;
    }

    /**
     * Returns a predicate that checks if a string contains at least one special character 
     * from the set: !@#$%^&*()
     * Return false if the string is null.
     */
    public static Predicate<String> containsSpecialChar() {
        // TODO: Implement predicate
        return null;
    }

    /**
     * Composes the three predicates above into a single password policy pipeline:
     * Must be at least 8 characters long AND contain a digit AND contain a special character.
     */
    public static Predicate<String> buildPasswordPolicy() {
        // TODO: Combine using .and()
        return null;
    }
}
