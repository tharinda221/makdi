
package webgloo.makdi.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class HtmlPage {
    
    public static void createPage(
            String siteName,
            String siteDomain,
            String pageName,
            String content) throws Exception{
            
        MyTrace.entry("HtmlPage", "createPage(content)");
        String executionPath = System.getProperty("user.dir");
        String pageId = MyUtils.convertPageNameToId(pageName);
        

        String sitePath = executionPath
                + File.separator
                + "makdi-out"
                + File.separator
                + siteDomain ;

        File siteDirectory = new File(sitePath);
        if (!siteDirectory.exists()) {
            siteDirectory.mkdirs();
            MyTrace.info("created new site directory :: " + siteDirectory);
        }

        String nameOnDisk = pageId;
        String pageHtml = HtmlGenerator.generatePageCode(siteName,pageName,content);
        
        File outf = new File(siteDirectory, nameOnDisk + ".html");
        FileWriter fstream = new FileWriter(outf);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(pageHtml);
        out.close();

        MyTrace.info("\n *** Wrote file => " + outf.getAbsolutePath() + "\n\n ");
        MyTrace.exit("HtmlPage", "createPage(content)");
    }
    
}
