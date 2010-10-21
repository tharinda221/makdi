package webgloo.makdi.main;

import jargs.gnu.CmdLineParser;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import webgloo.makdi.io.MyFileReader;
import webgloo.makdi.profile.ProfileManager;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 *
 */
public class Main {

    public static void main(String[] args) throws Exception {

        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option actionOption = parser.addStringOption('a', "action");
        CmdLineParser.Option profileOption = parser.addStringOption('p', "profile");
        CmdLineParser.Option keywordsOption = parser.addStringOption('k', "keywords");
        
        parser.parse(args);

        String actionValue = (String) parser.getOptionValue(actionOption);
        String profileValue = (String) parser.getOptionValue(profileOption);
        String keywordsValue = (String) parser.getOptionValue(keywordsOption);
        
        checkForEmpty("action" , actionValue);
        checkForEmpty("profile" ,profileValue);
        showMessage();
        
        ProfileManager.process(profileValue);
        
        
    }

    private static void checkForEmpty(String key, String value) {
        if (StringUtils.isEmpty(value)) {
           System.err.println("Error: missing  required parameter " + key);
            printUsage();
            System.exit(1);

        }

    }
    
    private static List<String> getLinesInFile(String fileName) throws Exception {
        MyFileReader myreader = new MyFileReader(fileName);
        List<String> words = myreader.getAllLines() ;
        return words ;

    }
    
    private static void showMessage() {
        MyWriter.toConsole("starting Makdi API spider ....");
        MyWriter.toConsole("copyright - indigloo.com \n ");
    }

    private static void printUsage() {
        System.err.println(
                "\nUsage: java -jar makdi.jar [options] ... \n"
                + " where options are \n"
                + " -k, --keywords \t keywords file \n"
                + " -a, --action \t select action, one of [test|store|generate] \n"
                + " \t action: store \t store results in db \n"
                + " \t action: test \t generate test html pages \n"
                + " -p, --profile \t profile.xml containg drivers and meta information \n\n") ;
                

    }
}
