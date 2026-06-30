import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Reference solution for DependencyInjectionContainerSolution.
 * 
 * Injection mechanics:
 * - `Class.getDeclaredFields()` retrieves all fields, private included.
 * - `Field.isAnnotationPresent(Inject.class)` checks if the annotation is loaded on the field.
 * - `Field.setAccessible(true)` turns off compiler visibility security checks.
 * - `Field.set(target, instance)` assigns values dynamically at runtime.
 */
public class DependencyInjectionContainerSolution {

    private final Map<Class<?>, Object> registry = new HashMap<>();

    public void register(Class<?> type, Object instance) {
        if (type == null || instance == null) {
            throw new IllegalArgumentException("Type and instance cannot be null");
        }
        registry.put(type, instance);
    }

    public <T> void injectDependencies(T target) throws Exception {
        if (target == null) {
            return;
        }

        Class<?> clazz = target.getClass();

        // Retrieve all fields (including private)
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isSynthetic()) continue;

            // Check if field is annotated with @Inject
            if (field.isAnnotationPresent(Inject.class)) {
                Class<?> fieldType = field.getType();
                Object dependency = registry.get(fieldType);

                // Enforce that dependency is registered
                if (dependency == null) {
                    throw new IllegalStateException("No registered dependency found for class: " + fieldType.getName());
                }

                // Enable access to private fields and set the resolved dependency
                field.setAccessible(true);
                field.set(target, dependency);
            }
        }
    }
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Inject {
}
