
module translation.module {
    // Declares service interface consumption
    uses my.service.ServiceLoaderProvider.TranslationService;

    // Declares service provider implementation using nested class path
    provides my.service.ServiceLoaderProvider.TranslationService 
        with my.service.ServiceLoaderProvider.SpanishTranslationService;
}