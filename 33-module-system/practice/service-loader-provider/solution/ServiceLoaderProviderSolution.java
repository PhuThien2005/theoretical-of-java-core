package my.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Reference solution for ServiceLoaderProviderSolution.
 * 
 * ServiceLoader pattern:
 * - `ServiceLoader.load(Interface.class)` creates a loader for the service.
 * - It finds all modular providers registered with `provides ... with ...` in the module layer.
 * - This decouples client invocation code from explicit implementation references.
 */
public class ServiceLoaderProviderSolution {

    public static List<TranslationService> loadServices() {
        List<TranslationService> services = new ArrayList<>();
        
        // Loads providers registered for the service interface
        ServiceLoader<TranslationService> loader = ServiceLoader.load(TranslationService.class);
        
        for (TranslationService service : loader) {
            services.add(service);
        }
        
        return services;
    }

    public interface TranslationService {
        String translate(String text);
        String getLanguage();
    }

    public static class SpanishTranslationService implements TranslationService {
        
        public SpanishTranslationService() {
            // Public no-arg constructor is required for ServiceLoader
        }
        
        @Override
        public String translate(String text) {
            if ("Hello".equalsIgnoreCase(text)) {
                return "Hola";
            }
            return text;
        }

        @Override
        public String getLanguage() {
            return "Spanish";
        }
    }
}
