package no24_optional.practice.optional_monadic_chain;

import java.util.Optional;

/**
 * Reference solution for OptionalMonadicChainSolution.
 * 
 * Optional.or() (Java 9+):
 * - Takes a `Supplier<? extends Optional<? extends T>>` and evaluates it lazily.
 * - If the primary Optional is empty, it returns the Optional produced by the supplier.
 * - This allows clean functional fallback chains without premature evaluation or manual nested lookups.
 */
public class OptionalMonadicChainSolution {

    public static String findConfig(String key, ConfigSource cache, ConfigSource db, ConfigSource api) {
        if (key == null) {
            return "default-config";
        }

        // Lazy chaining of Optionals:
        return cache.find(key)
            .or(() -> db.find(key))
            .or(() -> api.find(key))
            .orElse("default-config");
    }
}

interface ConfigSource {
    Optional<String> find(String key);
}
