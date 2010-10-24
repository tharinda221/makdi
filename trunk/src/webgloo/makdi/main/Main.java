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
        CmdLineParser.Option actionOption = parser.addStringOption('a', "action");
        CmdLineParser.Option profileOption = parser.addStringOption('p', "profile");
        CmdLineParser.Option keywordsOption = parser.addStringOption('k', "keywords");

        try {
            parser.parse(args);

            String actionValue = (String) parser.getOptionValue(actionOption);
            String profileValue = (String) parser.getOptionValue(profileOption);
            String keywordsValue = (String) parser.getOptionValue(keywordsOption);

            checkForEmpty("action", actionValue);
            checkForEmpty("profile", profileValue);
            showMessage();

            ProfileManager.process(profileValue);

        } catch (Exception ex) {
            System.out.println("Error during processing : check logs for details");
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

    private static List<String> getLinesInFile(String fileName) throws Exception {
        MyFileReader myreader = new MyFileReader(fileName);
        List<String> words = myreader.getAllLines();
        return words;

    }

    private static void showMessage() {
        System.out.println("\n\n>> starting Makdi API spider @ " + MyUtils.now());
        System.out.println("copyright - indigloo.com \n ");
        MyTrace.info("started makdi spider @" + MyUtils.now());
    }

    private static void printUsage() {
        MyTrace.error(
                "\nUsage: java -jar makdi.jar [options] ... \n"
                + " where options are \n"
                + " -k, --keywords \t keywords file \n"
                + " -a, --action \t select action, one of [test|store|generate] \n"
                + " \t action: store \t store results in db \n"
                + " \t action: test \t generate test html pages \n"
                + " -p, --profile \t profile.xml containg drivers and meta information \n\n");


    }
}
