package webgloo.makdi.main;

import jargs.gnu.CmdLineParser;
import org.apache.commons.lang.StringUtils;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.plugin.PluginManager;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 *
 */
public class Main {

    public static void main(String[] args) {

        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option profileOption = parser.addStringOption('p', "plugin");
      
        try {
            parser.parse(args);
            String pluginValue = (String) parser.getOptionValue(profileOption);
            checkForEmpty("plugin", pluginValue);
            showMessage();
            PluginManager.process(pluginValue);

        } catch (Exception ex) {
            MyTrace.error("Error during processing : check logs for details");
            MyTrace.error("Unexpected error ", ex);
        }
        
        System.out.println("Makdi spider completed @ " + MyUtils.now());

    }

    private static void checkForEmpty(String key, String value) {
        if (StringUtils.isEmpty(value)) {
            MyTrace.error("Error: missing  required parameter " + key);
            printUsage();
            System.exit(1);

        }

    }

    private static void showMessage() {
        MyTrace.info("\n\n>> starting Makdi API spider @ " + MyUtils.now());
        MyTrace.info("copyright - indigloo.com \n ");
        MyTrace.info("started makdi spider @" + MyUtils.now());
    }

    private static void printUsage() {
        MyTrace.error(
                "\nUsage: java -jar makdi.jar [options] ... \n"
                + " where options are \n"
                + " -p, --plugin \t  plugin.xml containg drivers and meta information \n\n");


    }
}
