package webgloo.makdi.plugin.microsite;

import java.util.List;
import webgloo.makdi.db.GlooDBConnection;
import webgloo.makdi.db.GlooDBManager;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.plugin.IPlugin;
import webgloo.makdi.scraper.ArticleBaseLinks;
import webgloo.makdi.scraper.ArticleBaseScraper;
import webgloo.makdi.util.HtmlToText;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class MicroSitePlugin {

    public void invoke(IPlugin profileBean) throws Exception {
        //get keywords from profileBean
        List<String> keywords = profileBean.getKeywords();
        String orgId = profileBean.getSiteGuid();

        for (String keyword : keywords) {
            MyTrace.info("creating pages for keyword :: " + keyword);
            this.createSitePages(orgId, keyword, 20);
        }

    }
    
    public void createSitePages(String orgId, String keyword, int numberOfPages) throws Exception {

        java.sql.Connection connection = GlooDBConnection.getConnection();
        String typeOfWidget = "TEXT_ONLY";
        String widgetXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <widget> <type>TEXT_ONLY</type> <markdown>NO</markdown></widget>";

        //first fetch a list of articlebase.com links
        ArticleBaseLinks linksObj = new ArticleBaseLinks(numberOfPages);
        List<String> links = linksObj.getRecords(keyword);


        ArticleBaseScraper scraper;


        for (String link : links) {
            //is this link already in Database?
            //create MD5 digest of incoming link and compare with the
            // md5 digest stored in DB
            String digest = MyUtils.MD5(link);

            if (GlooDBManager.isExistingPage(connection, orgId, digest)) {
                //page exists - try next link
                MyTrace.info(link + " :: with digest :: " + digest + " already exists");
                continue;
            }

            //scrape the link and store as a page!
            scraper = new ArticleBaseScraper();
            scraper.run(link);

            if (scraper.getTitle() != null) {

                //Create summary for Autopost
                // Run HTML through a kit to convert to text otherwise
                // we will chop html tags in middle and all alignments will
                // go for a toss
                String content = HtmlToText.extractUsingSwingKit(scraper.getContent().toString());
                //150 chars should be about 30 words..
                StringBuilder summary = new StringBuilder(content.substring(0, 150));
                summary.append("....");
                
                //Add a new page
                GlooDBManager.addPage(
                        connection,
                        orgId,
                        digest,
                        scraper.getTitle().toString());

                //Add articles base content
                GlooDBManager.addPageContent(connection,
                        orgId,
                        digest,
                        typeOfWidget,
                        widgetXml,
                        scraper.getTitle(),
                        scraper.getContent());

                
                //Add auto post
                GlooDBManager.addAutoPost(connection,
                    orgId,
                    scraper.getTitle().toString(),
                    scraper.getTitle(),
                    summary);

            }
            
            Thread.sleep(5000);

        } //loop:links

    }
    
}
