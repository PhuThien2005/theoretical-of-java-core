package no17_annotation.practice.validation_annotation_processor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Starter template for a validation annotation processor.
 */
public class ValidationAnnotationProcessor {

    /**
     * Validates the fields of the given object against constraints.
     * 
     * Requirements:
     * 1. Iterate over all declared fields of the object's class.
     * 2. Ensure they are accessible (field.setAccessible(true)).
     * 3. If a field has the @NotNull annotation:
     *    - Retrieve the field's value.
     *    - If it is null, throw an IllegalArgumentException with message:
     *      "[fieldName] cannot be null"
     * 4. If a field has the @Min annotation:
     *    - Retrieve the field's value (must be an integer/number).
     *    - If it is less than the annotation's configured min value,
     *      throw an IllegalArgumentException with message:
     *      "[fieldName] must be at least [minVal]"
     *
     * @param obj the object to validate
     * @throws Exception if reflection access fails
     */
    public static void validate(Object obj) throws Exception {
        // TODO: Implement reflection validation based on annotations
    }
}

/**
 * Field annotation to enforce non-null values.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface NotNull {
}

/**
 * Field annotation to enforce a minimum integer value.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface Min {
    // TODO: Define an int value() property
    int value();
}
