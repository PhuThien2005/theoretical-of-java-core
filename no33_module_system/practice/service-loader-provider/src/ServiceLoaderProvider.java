package my.service;

import java.util.List;
import java.util.ServiceLoader;

/**
 * Starter template for dynamic Service Discovery using modular ServiceLoader.
 */
public class ServiceLoaderProvider {

    /**
     * Uses ServiceLoader to load and return all registered TranslationService providers.
     */
    public static List<TranslationService> loadServices() {
        // TODO: Implement ServiceLoader.load and collect instances
        return null;
    }

    /**
     * Service interface.
     */
    public interface TranslationService {
        String translate(String text);
        String getLanguage();
    }

    /**
     * Service implementation.
     * Note: Must be public static and have public no-arg constructor.
     */
    public static class SpanishTranslationService implements TranslationService {
        
        public SpanishTranslationService() {
            // Public constructor
        }
        
        @Override
        public String translate(String text) {
            // TODO: Implement translation. For starter, return text.
            return text;
        }

        @Override
        public String getLanguage() {
            return "Spanish";
        }
    }
}
