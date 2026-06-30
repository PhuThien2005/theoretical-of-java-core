import java.util.Optional;

/**
 * Starter template for refactoring nested null-checks into Optional pipelines.
 */
public class NullSafetyRefactoring {

    /**
     * Retrieves the email address of the user, defaulting to "default@example.com"
     * if the user, profile, contactInfo, or email is null.
     * 
     * Requirements:
     * - Refactor this to use Optional.ofNullable() and map() pipelines.
     * - Do NOT use standard if-statements for null checking!
     *
     * @param user the user instance
     * @return the user's email, or "default@example.com" if not found
     */
    public static String getEmailOrDefault(User user) {
        // TODO: Refactor using Optional mapping pipeline.
        return null;
    }
}

class User {
    private final Profile profile;

    public User(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
}

class Profile {
    private final ContactInfo contactInfo;

    public Profile(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }
}

class ContactInfo {
    private final String email;

    public ContactInfo(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
