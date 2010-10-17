
package webgloo.makdi.html;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.data.Link;
import webgloo.makdi.db.DBConnection;
import webgloo.makdi.db.DBManager;
import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.util.MyStringUtil;

/**
 *
 * @author rajeevj
 */
public class MenuManager {

    
    private String siteAddress ;
   

    public MenuManager(String siteDomain) {
        this.siteAddress = "http://" + siteDomain ;
    }
    
    public String createMenusFromDB(List<String> pageIds) throws Exception {

        List<Link> links = new ArrayList<Link>();

        //first menu is home menu
        Link home = new Link("Home",this.siteAddress + "/index.html");
        links.add(home);
        
        for(String pageId : pageIds) {
            String nameOnDisk = pageId ;
            String letter = "" +pageId.charAt(0);
            String pageName = MyStringUtil.convertPageIdToName(pageId);
            String addressOfPage = this.siteAddress + "/" + letter + "/" + nameOnDisk + ".html" ;
            Link link = new Link (pageName,addressOfPage);
            links.add(link);
            
        }
        
        //push all links inside menu template
        return HtmlGenerator.generateVerticalMenuCode(links);
        
    }
}
