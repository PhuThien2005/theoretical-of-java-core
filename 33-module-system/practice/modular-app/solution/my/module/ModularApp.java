package my.module;

import java.util.logging.Logger;

/**
 * Reference solution for ModularApp.
 */
public class ModularApp {

    private static final Logger logger = Logger.getLogger(ModularApp.class.getName());

    public static void runLogMessage(String msg) {
        if (msg != null) {
            logger.info(msg);
        }
    }
}
