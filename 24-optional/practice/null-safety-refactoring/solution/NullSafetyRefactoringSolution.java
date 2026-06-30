import java.util.Optional;

/**
 * Reference solution for NullSafetyRefactoringSolution.
 * 
 * Optional pipelines:
 * - `Optional.ofNullable(val)` wraps a nullable reference.
 * - `.map(Function)` evaluates if value is present; if so, applies function,
 *   wrapping output in a new Optional. If empty, returns empty directly.
 * - This eliminates explicit `!= null` nested conditionals.
 */
public class NullSafetyRefactoringSolution {

    public static String getEmailOrDefault(User user) {
        // Refactored clean mapping pipeline:
        return Optional.ofNullable(user)
            .map(User::getProfile)
            .map(Profile::getContactInfo)
            .map(ContactInfo::getEmail)
            .orElse("default@example.com");
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
