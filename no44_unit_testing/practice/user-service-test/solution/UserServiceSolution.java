package userservicetest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class UserServiceSolution {

    // Custom lifecycle annotations for our micro-testing framework
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MyTest {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MyBeforeEach {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface MyAfterEach {}

    public record User(String name, String email) {}

    public interface UserRepository {
        boolean existsByEmail(String email);
        User save(User user);
    }

    private final UserRepository userRepository;

    public UserServiceSolution(UserRepository userRepository) {
        if (userRepository == null) {
            throw new IllegalArgumentException("UserRepository cannot be null");
        }
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user.
     * Throws IllegalArgumentException if name or email is null/empty, or email lacks '@'.
     * Throws IllegalStateException if the email is already registered.
     */
    public User registerUser(String name, String email) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email must be valid and contain '@'");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("User with email already exists: " + email);
        }

        User newUser = new User(name, email);
        return userRepository.save(newUser);
    }
}
