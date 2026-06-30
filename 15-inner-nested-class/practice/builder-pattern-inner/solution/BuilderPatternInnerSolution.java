/**
 * Reference solution for BuilderPatternInnerSolution.
 * 
 * Static nested class concept:
 * - `Builder` is a public static nested class of `UserAccount`.
 * - It does not have an implicit reference to any instance of `UserAccount`.
 * - It acts as a helper template to compile values, and finally instantiate
 *   `UserAccount` via a private constructor (which the nested class can access!).
 */
public class BuilderPatternInnerSolution {
    // Wrapper class
}

class UserAccount {
    private final String username; // required
    private final String email;    // required
    private final String firstName; // optional
    private final String lastName;  // optional
    private final boolean active;   // optional

    // Private constructor: only accessible within UserAccount (which includes its nested classes!)
    private UserAccount(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.active = builder.active;
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
     * Static nested class representing the Builder.
     */
    public static class Builder {
        private final String username;
        private final String email;
        private String firstName = "";
        private String lastName = "";
        private boolean active = true; // default true

        public Builder(String username, String email) {
            if (username == null || email == null) {
                throw new IllegalArgumentException("Username and Email are required");
            }
            this.username = username;
            this.email = email;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this; // Return this to allow method chaining
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public UserAccount build() {
            // Instantiate target class using private constructor
            return new UserAccount(this);
        }
    }
}
