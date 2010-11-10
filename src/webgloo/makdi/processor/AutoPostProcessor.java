package webgloo.makdi.processor;

import java.util.List;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.db.AutoPostManager;
import webgloo.makdi.db.DBConnection;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.profile.IContentProfile;

/**
 *
 * @author rajeevj
 */
public abstract class AutoPostProcessor extends Processor {


    public abstract List<Keyword> loadNewKeywords() throws Exception ;
    public abstract boolean getPageSummary(
            IContentProfile profileBean,
            String token,
            StringBuilder title,
            StringBuilder summary) throws Exception ;

    
    public void storeContent(IContentProfile profileBean) throws Exception  {
        
        MyTrace.entry("AutoPostProcessor", "store(profile bean)");
        java.sql.Connection connection = DBConnection.getConnection();
        //fetch keywords for this hour
        List<Keyword> newKeywords = this.loadNewKeywords();
        AutoPostManager.storeKeywords(connection, profileBean.getSiteGuid(), newKeywords);

        Thread.sleep(1000);

        //Now load back unprocessed keys
        List<Keyword> unprocessedKeywords = AutoPostManager.loadKeywords(connection, profileBean.getSiteGuid());
        MyTrace.info("total keywords to process :: " + unprocessedKeywords.size());

        for (Keyword keyword : unprocessedKeywords) {
            MyTrace.info("\n process keyword :: " + keyword.getToken());
            String token = WordUtils.capitalizeFully(keyword.getToken());

            StringBuilder content = new StringBuilder();
            StringBuilder title = new StringBuilder();
            StringBuilder summary = new StringBuilder();
            
            if(this.getPageSummary(profileBean,token,title, summary)) {
                this.getPageContent(profileBean, token, content);
                AutoPostManager.storePostContent(connection,
                        profileBean.getSiteGuid(),
                        keyword,
                        title.toString(),
                        summary.toString(),
                        content.toString());

                MyTrace.info("\n stored info for keyword :: " + keyword.getToken());
            }else {
                MyTrace.info("\n No summary for :: " + token);
                AutoPostManager.updateKeyword(connection, profileBean.getSiteGuid(), keyword);
            }

            Thread.sleep(1000);

        }//loop: keywords

        connection.close();
        MyTrace.exit("GoogleHotTrendProcessor", "store(profile bean)");

    }
    
}
