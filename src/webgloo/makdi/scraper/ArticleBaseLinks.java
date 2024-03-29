package webgloo.makdi.scraper;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Post;
import webgloo.makdi.drivers.yahoo.YahooBossWebDriver;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 */
public class ArticleBaseLinks {

    private int size ;

    public ArticleBaseLinks(int size) {
        this.size = size ;
    }

    public List<String> getRecords(String keyword) throws Exception {
        
        String[] siteNames = new String[]{"www.articlesbase.com"};
        YahooBossWebDriver driver = new YahooBossWebDriver(siteNames,0,this.size);

        List<IData> items = driver.run(keyword);
        List<String> links = new ArrayList<String>();
        
        for (IData item : items) {
            Post post = (Post) item;
            String link = post.getLink();
            if(link.contains("article-tags")) {
                MyTrace.info("Ignored article link " + link);
            } else {
                links.add(link);
                MyTrace.debug("scraper found link :: " + link);
            }

        }
        
        return links;
        
    }

    public static void main(String[] args) throws Exception{
        ArticleBaseLinks base = new ArticleBaseLinks(2);
        List<String> links = base.getRecords("liquid protein diet") ;
        for(String link: links) {
            System.out.println(link);
        }

    }
}
