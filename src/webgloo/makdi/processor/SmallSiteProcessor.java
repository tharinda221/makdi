package webgloo.makdi.processor;

import java.util.List;
import webgloo.makdi.db.GlooDBConnection;
import webgloo.makdi.db.GlooDBManager;
import webgloo.makdi.scraper.ArticleBaseLinks;
import webgloo.makdi.scraper.ArticleBaseScraper;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class SmallSiteProcessor {

    public void createSite(String orgId, String keyword) throws Exception {

        java.sql.Connection connection = GlooDBConnection.getConnection();
        String typeOfWidget = "TEXT_ONLY";
        String widgetXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <widget> <type>AUTO_POST</type> <markdown>NO</markdown></widget>";

        //first fetch a list of articlebase.com links
        List<String> links = new ArticleBaseLinks().getRecords(keyword);
        ArticleBaseScraper scraper;
        int count = 0;

        for (String link : links) {
            scraper = new ArticleBaseScraper();
            scraper.run(link);

            if (scraper.getTitle() != null) {
                count++;
                //create a new gloo page
                String pageIdentKey = (count == 1) ? "home" : MyUtils.getUUID();
                
                if (!pageIdentKey.equals("home")) {
                    //Home is already created when an organization is created
                    // so do not add a gloo page for home
                    GlooDBManager.addPage(
                            connection,
                            orgId,
                            pageIdentKey,
                            scraper.getTitle().toString());
                }
                
                //Add content for all pages
                GlooDBManager.addPageContent(connection,
                        orgId,
                        pageIdentKey,
                        typeOfWidget,
                        widgetXml,
                        scraper.getTitle(),
                        scraper.getContent());


            }

            //sleep for some time
            Thread.sleep(5000);

        }

    }

    public static void main(String[] args) throws Exception {
        new SmallSiteProcessor().createSite("1173", "meditation chairs");
    }
}
