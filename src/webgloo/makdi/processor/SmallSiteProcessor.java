package webgloo.makdi.processor;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.db.GlooDBConnection;
import webgloo.makdi.db.GlooDBManager;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.drivers.YahooBossImageDriver;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.profile.IContentProfile;
import webgloo.makdi.scraper.ArticleBaseLinks;
import webgloo.makdi.scraper.ArticleBaseScraper;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class SmallSiteProcessor {

    public void invoke(IContentProfile profileBean) throws Exception {
            //get keywords from profileBean
            List<String> keywords = profileBean.getKeywords();
            String orgId = profileBean.getSiteGuid();
            
            for(String keyword: keywords) {
                this.createSite(orgId, keyword,10);
            }
            
    }
    
    public void createSite(String orgId, String keyword, int numberOfPages) throws Exception {

        java.sql.Connection connection = GlooDBConnection.getConnection();
        String typeOfWidget = "AUTO_POST";
        String widgetXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <widget> <type>AUTO_POST</type> </widget>";
        
        //first fetch a list of articlebase.com links
        List<String> links = new ArticleBaseLinks(numberOfPages).getRecords(keyword);
        
        ArticleBaseScraper scraper;
        int index = 0;
        String title ;
        
        List<IData> images = new ArrayList<IData>();

        //fetch images for a page
        if (links.size() > 0) {
            MyTrace.debug("fetch images :" + links.size());
            IDriver driver = new YahooBossImageDriver(null, 0, links.size());
            images = driver.run(keyword);

        }

        for (String link : links) {
            scraper = new ArticleBaseScraper();
            scraper.run(link);

            if (scraper.getTitle() != null) {

                //create a new gloo page
                String pageIdentKey = (index == 0) ? "home" : MyUtils.getUUID();

                if (!pageIdentKey.equals("home")) {
                    //Home is already created when an organization is created
                    // so do not add a gloo page for home
                    GlooDBManager.addPage(
                            connection,
                            orgId,
                            pageIdentKey,
                            scraper.getTitle().toString());
                }

                //Add article base content
                GlooDBManager.addPageContent(connection,
                        orgId,
                        pageIdentKey,
                        typeOfWidget,
                        widgetXml,
                        scraper.getTitle(),
                        scraper.getContent());
                
                //Add image
                // second widget will get ui_order = 2 and hence
                // will come out on top of other widget
                
                if (index < images.size()) {
                    //Add image data
                    GlooDBManager.addPageContent(connection,
                            orgId,
                            pageIdentKey,
                            typeOfWidget,
                            widgetXml,
                            new StringBuilder(images.get(index).getTitle()),
                            new StringBuilder(images.get(index).toHtml()));
                }

                MyTrace.info("created page for site :: " +scraper.getTitle().toString() );
            }
                       
            index++;
            //sleep for some time after processing one link
            Thread.sleep(5000);
            
        } //loop:links

    }

    public static void main(String[] args) throws Exception {
        new SmallSiteProcessor().createSite("1174", "meditation chairs",20);
    }
}
