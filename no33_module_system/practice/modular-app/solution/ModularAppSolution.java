package my.module;

import java.util.logging.Logger;

/**
 * Reference solution for ModularAppSolution.
 */
public class ModularAppSolution {

    private static final Logger logger = Logger.getLogger(ModularAppSolution.class.getName());

    public static void runLogMessage(String msg) {
        if (msg != null) {
            logger.info(msg);
        }
    }
}
