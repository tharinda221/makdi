package webgloo.makdi.processor;

import java.util.List;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.db.DBConnection;
import webgloo.makdi.db.DBManager;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.profile.IProfileBean;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 */
public class GoogleHotTrendProcessor {

    public static void invoke(IProfileBean profileBean) throws Exception {
        //first refresh the keywords
        List<Keyword> newKeywords = GoogleHotTrendKeywords.loadNewKeywords();
        java.sql.Connection connection = DBConnection.getConnection();
        //plug in logic to remove duplicate keywords 
        List<IDriver> drivers = profileBean.getDrivers();

        for (Keyword keyword : newKeywords) {

            MyWriter.toConsole("\n** start process :: " + keyword);
            String token = WordUtils.capitalizeFully(keyword.getToken());
            //String pageId = MyStringUtil.convertPageNameToId(keyword);
            StringBuilder content = new StringBuilder();
            StringBuilder summary = new StringBuilder();

            int count = 0;

            //get content
            for (IDriver driver : drivers) {

                List<IData> items = driver.run(token);
                for (IData item : items) {
                    content.append(item.toHtml());
                }
            }

            //get summary
            List<IData> items = profileBean.getFrontPageDriver().run(token);
            for (IData item : items) {
                summary.append(item.toHtml());
                count++;
            }

            //store summary and  content
            if (count > 0) {
                //store content for this keyword
                DBManager.storePageContent(connection,
                        profileBean.getSiteGuid(),
                        token,
                        " automated ocontent for " + token,
                        summary.toString(),
                        content.toString());

            }

            MyWriter.toConsole("\n** summary :: " + summary.toString());
            MyWriter.toConsole("\n** end process :: " + keyword);

        } //loop: keywords

        connection.close();

    }
}
