package no17_annotation.practice.validation_annotation_processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * Reference solution for ValidationAnnotationProcessorSolution.
 */
public class ValidationAnnotationProcessorSolution {

    public static void validate(Object obj) throws Exception {
        if (obj == null) {
            return;
        }

        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isSynthetic()) continue;

            field.setAccessible(true); // Enable access to private fields

            // 1. Process @NotNull constraint
            if (field.isAnnotationPresent(NotNull.class)) {
                Object value = field.get(obj);
                if (value == null) {
                    throw new IllegalArgumentException(field.getName() + " cannot be null");
                }
            }

            // 2. Process @Min constraint
            if (field.isAnnotationPresent(Min.class)) {
                Min minAnnotation = field.getAnnotation(Min.class);
                int minVal = minAnnotation.value();
                Object value = field.get(obj);

                if (value instanceof Number) {
                    long num = ((Number) value).longValue();
                    if (num < minVal) {
                        throw new IllegalArgumentException(field.getName() + " must be at least " + minVal);
                    }
                }
            }
        }
    }
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface NotNull {
}

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Min {
    int value();
}
