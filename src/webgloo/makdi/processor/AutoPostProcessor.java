package webgloo.makdi.processor;

import java.util.List;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.db.GlooDBConnection;
import webgloo.makdi.db.GlooDBManager;

import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.profile.IContentProfile;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public abstract class AutoPostProcessor extends Processor {

    public abstract List<Keyword> loadNewKeywords() throws Exception;

    public abstract boolean getPageSummary(
            IContentProfile profileBean,
            String token,
            StringBuilder title,
            StringBuilder summary) throws Exception;

    public void storeContent(IContentProfile profileBean) throws Exception {

        MyTrace.entry("AutoPostProcessor", "store(profile bean)");
        java.sql.Connection connection = GlooDBConnection.getConnection();

        //load any new keywords
        List<Keyword> newKeywords = this.loadNewKeywords();

        if (newKeywords != null && newKeywords.size() > 0) {

            for (Keyword keyword : newKeywords) {

                //artificial createdOn values
                String createdOn = keyword.getDate() + " " + MyUtils.now();

                GlooDBManager.addAutoPostKeyword(
                        connection,
                        profileBean.getSiteGuid(),
                        keyword.getToken(),
                        createdOn);

                //wait 1 second before pushing in new keyword
                // This will esnure that keywords are spaced right
                Thread.sleep(1000);

            }

        }


        //Now load back unprocessed keys from database
        List<Keyword> unprocessedKeywords = GlooDBManager.getUnprocessedAutoKeywords(connection, profileBean.getSiteGuid());
        MyTrace.info("total keywords to process :: " + unprocessedKeywords.size());

        String typeOfWidget = "AUTO_POST";
        String widgetXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <widget> <type>AUTO_POST</type></widget>";
        
        for (Keyword keyword : unprocessedKeywords) {
            MyTrace.info("\n process keyword :: " + keyword.getToken());
            String token = WordUtils.capitalizeFully(keyword.getToken());

            //buffers to hold data
            StringBuilder content = new StringBuilder();
            StringBuilder title = new StringBuilder();
            StringBuilder summary = new StringBuilder();


            if (this.getPageSummary(profileBean, token, title, summary)) {
                this.getPageContent(profileBean, token, content);

                //1. create a new gloo_page
                String pageIdentKey = MyUtils.getUUID();
                String pageName = keyword.getToken();
                //mySQL timestamps are in form 2010-10-22 21:14:45
                String createdOn = keyword.getCreatedOn();


                GlooDBManager.addPage(
                        connection,
                        profileBean.getSiteGuid(),
                        pageIdentKey,
                        pageName);

                //2. store content in newly created page
                GlooDBManager.addPageContent(connection,
                        profileBean.getSiteGuid(),
                        pageIdentKey,
                        typeOfWidget,
                        widgetXml,
                        title,
                        content);

                //3. store summary in gloo_auto_post table
                // get right date string

                GlooDBManager.addAutoPost(connection,
                        profileBean.getSiteGuid(),
                        pageName,
                        title,
                        summary,
                        createdOn);


                MyTrace.info("\n stored info for keyword :: " + keyword.getToken());

            } else {
                MyTrace.info("\n No summary for :: " + token);
                GlooDBManager.updateAutoKeyword(connection, profileBean.getSiteGuid(), keyword);
            }

            Thread.sleep(1000);

        }//loop: keywords

        connection.close();
        MyTrace.exit("GoogleHotTrendProcessor", "store(profile bean)");

    }
}
