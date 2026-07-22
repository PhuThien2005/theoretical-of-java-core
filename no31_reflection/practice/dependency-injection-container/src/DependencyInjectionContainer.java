package no31_reflection.practice.dependency_injection_container;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

/**
 * Starter template for a simple Dependency Injection (DI) Container using Reflection.
 */
public class DependencyInjectionContainer {

    private final Map<Class<?>, Object> registry = new HashMap<>();

    /**
     * Registers an instance for a given Class/Interface type.
     */
    public void register(Class<?> type, Object instance) {
        registry.put(type, instance);
    }

    /**
     * Scans the target object for fields annotated with @Inject, resolves their instances
     * from the container's registry, and injects them.
     * 
     * Requirements:
     * - Retrieve all declared fields of the target's class.
     * - Check if a field is annotated with @Inject.
     * - If annotated, resolve the instance from `registry` by class type.
     * - If not registered, throw an IllegalStateException.
     * - Bypass visibility modifiers using field.setAccessible(true) and set the value.
     */
    public <T> void injectDependencies(T target) throws Exception {
        // TODO: Implement dependency injection scans using reflection
    }
}

/**
 * Annotation marking fields that should be populated by Dependency Injection.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Inject {
}
