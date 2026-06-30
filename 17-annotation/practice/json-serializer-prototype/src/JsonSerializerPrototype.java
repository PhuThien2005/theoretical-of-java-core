import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Starter template for a reflection-based JSON serializer prototype.
 */
public class JsonSerializerPrototype {

    /**
     * Serializes an object to its JSON string representation.
     * 
     * Requirements:
     * 1. Check if the object's class has the @JsonSerializable annotation.
     *    If not, throw an IllegalArgumentException.
     * 2. Iterate through all declared fields of the object's class.
     * 3. Ensure the fields are accessible (field.setAccessible(true)).
     * 4. If a field has the @JsonField annotation:
     *    - Determine the JSON key: Use the annotation's value() if non-empty,
     *      otherwise default to the field's name.
     *    - Retrieve the field's value.
     *    - Format values:
     *      - Strings are surrounded by double quotes (e.g. "name":"Alice").
     *      - Numbers and booleans are printed raw without quotes (e.g. "age":30).
     * 5. Combine key-value pairs with commas, wrapped in curly braces {}.
     *
     * @param obj the object to serialize
     * @return the JSON string representation
     * @throws Exception if reflection access fails
     */
    public static String serialize(Object obj) throws Exception {
        // TODO: Implement reflection serialization
        return null;
    }
}

/**
 * Annotation to mark a class as serializable to JSON.
 */
// TODO: Add target (TYPE) and retention (RUNTIME) annotations
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface JsonSerializable {
}

/**
 * Annotation to mark a field to be included in the JSON output.
 */
// TODO: Add target (FIELD) and retention (RUNTIME) annotations
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface JsonField {
    // TODO: Add an optional value() property defaulting to empty string
    String value() default "";
}
