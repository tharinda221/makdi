
package webgloo.makdi.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.util.MyStringUtil;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 */
public class HtmlPage {


    public static void createPage(
            String siteName,
            String siteDomain,
            String menuHtml,
            String pageId,
            List<IData> items) throws Exception{

        StringBuilder sb = new StringBuilder();
        for(IData item: items) {
            sb.append(item.toHtml());

        }
        HtmlPage.createPage(siteName, siteDomain, menuHtml, pageId, sb.toString());
        
    }

    public static void createPage(
            String siteName,
            String siteDomain,
            String menuHtml,
            String pageId,
            String pageContent) throws Exception{


        String executionPath = System.getProperty("user.dir");
        
         //get first letter from tag
        String letter = pageId.charAt(0) + "";
        String pageName = MyStringUtil.convertPageIdToName(pageId);
        

        String sitePath = executionPath
                + File.separator
                + "makdi-out"
                + File.separator
                + siteDomain
                + File.separator
                + letter ;

        File siteDirectory = new File(sitePath);
        if (!siteDirectory.exists()) {
            siteDirectory.mkdirs();
            MyWriter.toConsole("created new site directory :: " + siteDirectory);
        }

        String nameOnDisk = pageId;
      
        String content = HtmlGenerator.generatePageCode(siteName,menuHtml,pageName,pageContent);
        
        File outf = new File(siteDirectory, nameOnDisk + ".html");
        FileWriter fstream = new FileWriter(outf);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(content);
        out.close();
        MyWriter.toConsole("\n *** Wrote file => " + outf.getAbsolutePath() + "\n\n ");

    }
    
}
