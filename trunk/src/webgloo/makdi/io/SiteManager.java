package webgloo.makdi.io;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.html.HtmlPage;
import webgloo.makdi.profile.IProfileBean;
import webgloo.makdi.util.MyStringUtil;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 */
public class SiteManager {

    public SiteManager() {
    }

    public void run(IProfileBean profile, List<String> keywords) throws Exception {
        List<IDriver> drivers = profile.getDrivers();
        
        //run this profile and store data in DB
        for (String keyword : keywords) {
            MyWriter.toConsole("\n** start process :: " + keyword);
            List<IData> bucket = new ArrayList<IData>();
            //raw keyword
            keyword = WordUtils.capitalizeFully(keyword);

            for (IDriver driver : drivers) {
                //use keyword + driver
                //Reader gives us trimmed + squeezed keywords
                //driver knows how to transform the keyword
                List<IData> items = driver.run(keyword);
                bucket.addAll(items);
            }
            
            //create pageId from pageName
            String pageId = MyStringUtil.convertPageNameToId(keyword);

            //generate page
            HtmlPage.createPage("test", "test.com", "test menu", pageId, bucket);
            MyWriter.toConsole("\n** end process :: " + keyword);
            
        } //loop: keywords


    }

    public void store(IProfileBean profile) throws Exception {

       

    }

   
}
