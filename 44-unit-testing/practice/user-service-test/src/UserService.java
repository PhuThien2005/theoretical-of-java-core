package userservicetest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class UserService {

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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Registers a new user.
     * Throws IllegalArgumentException if name or email is null/empty, or email lacks '@'.
     * Throws IllegalStateException if the email is already registered.
     */
    public User registerUser(String name, String email) {
        // TODO: Validate name and email.
        // TODO: Check if user already exists using repository.
        // TODO: Save user and return it.
        return null;
    }
}
