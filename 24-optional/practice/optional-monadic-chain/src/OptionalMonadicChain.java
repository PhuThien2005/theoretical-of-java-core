import java.util.Optional;

/**
 * Starter template for building fallback chains using Optional.or().
 */
public class OptionalMonadicChain {

    /**
     * Resolves a configuration value by checking multiple sources in order of preference:
     * 1. Check local cache.
     * 2. Check database.
     * 3. Check remote API.
     * 
     * If all sources are empty, return "default-config".
     * 
     * Requirements:
     * - Chain the fallbacks lazily using Optional.or() or flatMap() pipelines.
     * - Do NOT check states manually (e.g. do not call optional.isPresent() / if-statements).
     *
     * @param key the configuration key to search
     * @param cache local cache source
     * @param db database source
     * @param api remote API source
     * @return the resolved config value or "default-config"
     */
    public static String findConfig(String key, ConfigSource cache, ConfigSource db, ConfigSource api) {
        // TODO: Implement Optional fallback chain using .or()
        return null;
    }
}

/**
 * Represents a source of configuration values.
 */
interface ConfigSource {
    Optional<String> find(String key);
}
