package webgloo.makdi.scraper;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Post;
import webgloo.makdi.drivers.YahooBossWebDriver;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 */
public class ArticleBaseLinks {

    public List<String> getRecords(String keyword) throws Exception {
        
        String[] siteNames = new String[]{"www.articlesbase.com"};
        YahooBossWebDriver driver = new YahooBossWebDriver(siteNames,0,10);

        List<IData> items = driver.run(keyword);
        List<String> links = new ArrayList<String>();
        
        for (IData item : items) {
            Post post = (Post) item;
            String link = post.getLink();
            if(link.contains("article-tags")) {
                MyTrace.info("Ignored article link " + link);
            } else {
                links.add(link);
            }

        }
        
        return links;
        
    }
}
