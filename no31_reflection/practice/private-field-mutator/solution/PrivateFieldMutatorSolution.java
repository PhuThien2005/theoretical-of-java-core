package no31_reflection.practice.private_field_mutator;

import java.lang.reflect.Field;

/**
 * Reference solution for PrivateFieldMutatorSolution.
 * 
 * Breaking Encapsulation:
 * - `Class.getDeclaredField(name)` retrieves fields defined directly in the class, regardless of access modifier.
 * - `Field.setAccessible(true)` turns off checks so we can read and write private fields.
 */
public class PrivateFieldMutatorSolution {

    public static Object getPrivateField(Object obj, String fieldName) throws Exception {
        if (obj == null || fieldName == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        
        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        
        // Turn off visibility checks
        field.setAccessible(true);
        
        return field.get(obj);
    }

    public static void setPrivateField(Object obj, String fieldName, Object value) throws Exception {
        if (obj == null || fieldName == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }

        Class<?> clazz = obj.getClass();
        Field field = clazz.getDeclaredField(fieldName);
        
        // Turn off visibility checks
        field.setAccessible(true);
        
        field.set(obj, value);
    }
}
