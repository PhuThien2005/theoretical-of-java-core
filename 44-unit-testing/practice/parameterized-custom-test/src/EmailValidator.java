package parameterizedcustomtest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class EmailValidator {

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
        // TODO: Implement the email validation rules.
        return false;
    }
}
