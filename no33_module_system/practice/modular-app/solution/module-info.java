
module my.module {
    // Requires JDK platform logging module
    requires java.logging;

    // Exports its package so other modules or reflection can access it
    exports my.module;
}