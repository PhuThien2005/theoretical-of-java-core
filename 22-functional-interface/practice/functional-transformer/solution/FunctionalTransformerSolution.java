import java.util.function.Function;

/**
 * Reference solution for FunctionalTransformerSolution.
 * 
 * Function features:
 * - A Function<T, R> represents a function that converts type T to type R.
 * - `.andThen(Function)` chains two functions: first runs this function, then the next function on the result.
 * - `.compose(Function)` chains two functions in reverse order: first runs the parameter function, then this function.
 */
public class FunctionalTransformerSolution {

    public static Function<String, String> trim() {
        return s -> s == null ? "" : s.trim();
    }

    public static Function<String, String> toLowerCase() {
        return s -> s == null ? "" : s.toLowerCase();
    }

    public static Function<String, String> replaceSpaces(String replacement) {
        return s -> {
            if (s == null) {
                return "";
            }
            // If replacement is null, default to empty string or throw, let's default to empty string
            String rep = replacement == null ? "" : replacement;
            return s.replace(" ", rep);
        };
    }

    public static Function<String, String> buildSanitizerPipeline(String replacement) {
        // Compose sequence: trim -> toLowerCase -> replaceSpaces
        return trim()
            .andThen(toLowerCase())
            .andThen(replaceSpaces(replacement));
    }
}
