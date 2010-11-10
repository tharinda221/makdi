package webgloo.makdi.main;

import jargs.gnu.CmdLineParser;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import webgloo.makdi.io.MyFileReader;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.profile.ProfileManager;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 *
 */
public class Main {

    public static void main(String[] args) {

        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option profileOption = parser.addStringOption('p', "profile");
      
        try {
            parser.parse(args);
            String profileValue = (String) parser.getOptionValue(profileOption);
            checkForEmpty("profile", profileValue);
            showMessage();
            ProfileManager.process(profileValue);

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
                + " -p, --profile \t profile.xml containg drivers and meta information \n\n");


    }
}
