package no17_annotation.practice.json_serializer_prototype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Reference solution for JsonSerializerPrototypeSolution.
 * 
 * Custom Annotations & Reflection:
 * - `@Target` specifies where the annotation can be applied (type, field, method, etc.).
 * - `@Retention(RetentionPolicy.RUNTIME)` ensures the annotation metadata is preserved in the compiled class file and is readable by the JVM at runtime.
 * - Reflection APIs like `Class.isAnnotationPresent` and `Field.getAnnotation` are used to inspect the metadata and extract runtime configurations.
 */
public class JsonSerializerPrototypeSolution {

    public static String serialize(Object obj) throws Exception {
        if (obj == null) {
            return "null";
        }

        Class<?> clazz = obj.getClass();
        
        // Step 1: Enforce @JsonSerializable check
        if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
            throw new IllegalArgumentException("Class " + clazz.getSimpleName() + " is not annotated with @JsonSerializable");
        }

        List<String> jsonParts = new ArrayList<>();
        
        // Step 2: Traverse declared fields
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isSynthetic()) continue; // Skip compiler-added fields

            // Step 3: Check for @JsonField annotation
            if (field.isAnnotationPresent(JsonField.class)) {
                field.setAccessible(true); // Bypass private access modifier

                JsonField jsonField = field.getAnnotation(JsonField.class);
                
                // Step 4: Determine the key
                String key = jsonField.value().isEmpty() ? field.getName() : jsonField.value();
                Object val = field.get(obj);

                // Step 5: Format the value based on type
                String formattedValue;
                if (val == null) {
                    formattedValue = "null";
                } else if (val instanceof Number || val instanceof Boolean) {
                    formattedValue = val.toString();
                } else {
                    // Treat as standard String/Object: surround in double quotes
                    formattedValue = "\"" + val.toString() + "\"";
                }

                jsonParts.add("\"" + key + "\":" + formattedValue);
            }
        }

        // Step 6: Construct JSON string
        return "{" + String.join(",", jsonParts) + "}";
    }
}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface JsonSerializable {
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface JsonField {
    String value() default "";
}
