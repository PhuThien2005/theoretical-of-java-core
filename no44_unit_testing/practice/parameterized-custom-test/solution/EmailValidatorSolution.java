package parameterizedcustomtest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class EmailValidatorSolution {

    // Custom annotations to simulate JUnit's Parameterized tests
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MyParameterizedTest {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MyValueSource {
        String[] strings() default {};
    }

    /**
     * Validates an email address.
     * Rules:
     * 1. Must not be null or empty.
     * 2. Must contain exactly one '@'.
     * 3. Must contain at least one '.' after the '@'.
     * 4. Must not start or end with '@' or '.'.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        int atIndex = email.indexOf('@');
        // Must contain '@' and it cannot be the first or last character
        if (atIndex <= 0 || atIndex == email.length() - 1) {
            return false;
        }

        // Must not contain multiple '@' symbols
        if (email.indexOf('@', atIndex + 1) != -1) {
            return false;
        }

        // Must contain '.' after '@' and it cannot be immediately after '@' or at the very end
        int dotIndex = email.indexOf('.', atIndex + 1);
        if (dotIndex <= atIndex + 1 || dotIndex == email.length() - 1) {
            return false;
        }

        // Must not start or end with '.'
        if (email.startsWith(".") || email.endsWith(".")) {
            return false;
        }

        return true;
    }
}
