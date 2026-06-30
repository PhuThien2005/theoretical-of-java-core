/**
 * A class demonstrating the Builder pattern using a static nested class.
 */
public class BuilderPatternInner {
    // Wrapper class
}

/**
 * Represents a UserAccount. This class is immutable (no setters, all fields final).
 */
class UserAccount {
    private final String username; // required
    private final String email;    // required
    private final String firstName; // optional
    private final String lastName;  // optional
    private final boolean active;   // optional

    // TODO: Implement a private constructor taking a Builder instance.
    public UserAccount(String username, String email, String firstName, String lastName, boolean active) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.active = active;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isActive() {
        return active;
    }

    /**
     * Public static nested class representing the Builder.
     */
    public static class Builder {
        // TODO: Declare the same fields as UserAccount (required and optional)
        String username;
        String email;
        String firstName = "";
        String lastName = "";
        boolean active = false;
        
        /**
         * Constructor enforcing the required fields.
         */
        public Builder(String username, String email) {
            // TODO: Initialize required fields. Throw IllegalArgumentException if either is null.
            this.username = username;
            this.email = email;
        }

        // TODO: Implement fluent optional setters returning `this`:
        public Builder firstName(String firstName) {
            return this;
        }

        public Builder lastName(String lastName) {
            return this;
        }

        public Builder active(boolean active) {
            return this;
        }

        /**
         * Builds and returns a new UserAccount instance.
         */
        public UserAccount build() {
            // TODO: Call private constructor of UserAccount passing this builder
            return new UserAccount(username, email, firstName, lastName, active);
        }
    }
}
