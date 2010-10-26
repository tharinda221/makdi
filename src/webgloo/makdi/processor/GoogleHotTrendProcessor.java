package webgloo.makdi.processor;

import java.util.List;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.data.News;
import webgloo.makdi.db.DBConnection;
import webgloo.makdi.db.AutoPostManager;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.profile.IProfileBean;

/**
 *
 * @author rajeevj
 */
public class GoogleHotTrendProcessor {

    //@todo check lock - if lock in place then dont process
    public static void invoke(IProfileBean profileBean) throws Exception {

        MyTrace.entry("GoogleHotTrendProcessor", "invoke(profile bean)");
        java.sql.Connection connection = DBConnection.getConnection();
        //fetch keywords for this hour
        List<Keyword> newKeywords = GoogleHotTrendKeywords.loadNewKeywords();
        AutoPostManager.storeKeywords(connection, profileBean.getSiteGuid(), newKeywords);

        Thread.sleep(1000);

        //Now load back unprocessed keys
        List<Keyword> unprocessedKeywords = AutoPostManager.loadKeywords(connection, profileBean.getSiteGuid());
        MyTrace.info("Total keywords to process = " + unprocessedKeywords.size());
        List<IDriver> drivers = profileBean.getDrivers();


        for (Keyword keyword : unprocessedKeywords) {
            MyTrace.info("\n process keyword :: " + keyword.getToken());

            String token = WordUtils.capitalizeFully(keyword.getToken());
            StringBuilder content = new StringBuilder();
            StringBuilder summary = new StringBuilder();
            String title = null;

            int count = 0;
            //get summary
            List<IData> summaryItems = profileBean.getFrontPageDriver().run(token);
            for (IData sumamryItem : summaryItems) {
                summary.append(HtmlGenerator.generateAutoPostNews((News) sumamryItem));
                title = sumamryItem.getTitle();
                count++;
            }

            if (count <= 0) {
                MyTrace.info("\n No summary for :: " + keyword.getToken());
                AutoPostManager.updateKeyword(connection,  profileBean.getSiteGuid(),keyword);
                
            } else {

                //get content only if we have summary
                for (IDriver driver : drivers) {
                    try {
                        List<IData> items = driver.run(token);
                        for (IData item : items) {
                            content.append(item.toHtml());
                        }
                        //driver processed right
                        //wait for appropriate time
                        Thread.sleep(driver.getDelay());


                    } catch (Exception ex) {
                        //some error from driver
                        MyTrace.error("Error in driver :: " + driver.getName());
                        MyTrace.error(ex.getMessage());
                    }

                } //loop

                AutoPostManager.storePostContent(connection,
                        profileBean.getSiteGuid(),
                        keyword,
                        title,
                        summary.toString(),
                        content.toString());

                MyTrace.info("\n stored info for keyword :: " + keyword.getToken());

            }

            MyTrace.info("\n processed keyword :: " + keyword.getToken());
            //sleep for 1s
            Thread.sleep(10000);
        } //loop: keywords

        connection.close();

        MyTrace.exit("GoogleHotTrendProcessor", "invoke(profile bean)");
    }
}
