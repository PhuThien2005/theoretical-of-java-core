
module translation.module {
    // Declares service interface consumption
    uses my.service.ServiceLoaderProviderSolution.TranslationService;

    // Declares service provider implementation using nested class path
    provides my.service.ServiceLoaderProviderSolution.TranslationService 
        with my.service.ServiceLoaderProviderSolution.SpanishTranslationService;
}