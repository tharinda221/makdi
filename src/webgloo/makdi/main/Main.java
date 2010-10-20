package webgloo.makdi.main;

import jargs.gnu.CmdLineParser;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import webgloo.makdi.io.MyFileReader;
import webgloo.makdi.io.SiteManager;
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
        

        if (StringUtils.isEmpty(actionValue) 
                || StringUtils.isEmpty(profileValue)) {
           System.err.println(
                    "Error: missing one of the required parameters \n "
                    + " 1.action or 2.profile  or 3. keywords \n");
            printUsage();
            System.exit(1);

        }
        
        showMessage();
        
        if (actionValue.equals("test")) {
            List<String> keywords = getLinesInFile(keywordsValue);
            new SiteManager().run(ProfileManager.getProfile(profileValue), keywords);
            
        }else if (actionValue.equals("store")) {
            new SiteManager().store(ProfileManager.getProfile(profileValue));
        } else {
            System.err.println(
                    "Error: Unknown action :: " + actionValue
                    + "\n Makdi can process test | store");
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
