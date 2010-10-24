package webgloo.makdi.logging;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author rajeevj
 */
public class MyTrace {

    private static Logger logger = Logger.getLogger("webgloo.makdi");

    static {
        //initialization code
        // It has the same effect of the parameter
        // -Djava.util.logging.config.file
        try {
            Properties properties = System.getProperties();
            properties.setProperty("java.util.logging.config.file", "log.properties");
            LogManager.getLogManager().readConfiguration();

        } catch (Exception ex) {
            logger.severe("Problems loading logging configuration" + ex.toString());
        }


    }

    public static void debug(String message) {
        //use convenience method for logging
        if (logger.isLoggable(Level.FINEST)) {
            logger.finest(message);
        }

    }

    public static void info(String message) {
        if (logger.isLoggable(Level.INFO)) {
            logger.info(message);
        }

    }

    public static void error(String message) {
        logger.severe(message);
    }

    public static void error(String message, Exception ex) {
        logger.log(Level.SEVERE,message, ex);
    }

    public static void entry(String className, String methodName) {
        logger.entering(className, methodName);
    }
    
    public static void exit(String className, String methodName) {
        logger.exiting(className, methodName);
    }
}
