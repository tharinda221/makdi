
package webgloo.makdi.processor;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.profile.IProfileBean;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 */
public class GoogleHotTrendProcessor {

    public static void invoke(IProfileBean profileBean) throws Exception{
         //first refresh the keywords
        List<Keyword> newKeywords = GoogleHotTrendKeywords.loadNewKeywords();
        //java.sql.Connection connection = DBConnection.getConnection();
        //DBManager.storeKeywords(connection, GoogleHotTrendKeywords.loadNewKeywords());

        //Now find unprocessed keywords
        //List<String> keywords = DBManager.loadKeywords(connection, profile.getSiteGuid());
        List<String> keywords = new ArrayList<String>();
        for(Keyword k : newKeywords) {
            keywords.add(k.getToken());

        }

        List<IDriver> drivers = profileBean.getDrivers();

        for (String keyword : keywords) {

            MyWriter.toConsole("\n** start process :: " + keyword);
            keyword = WordUtils.capitalizeFully(keyword);
            //String pageId = MyStringUtil.convertPageNameToId(keyword);
            StringBuilder content = new StringBuilder();
            StringBuilder summary = new StringBuilder();

            //get content
            for (IDriver driver : drivers) {

                List<IData> items = driver.run(keyword);
                for (IData item : items) {
                    content.append(item.toHtml());
                }
            }
            
            //get summary
            List<IData> items = profileBean.getFrontPageDriver().run(keyword);
            for (IData item : items) {
                summary.append(item.toHtml());
            }

            //store summary and  content
            MyWriter.toConsole("\n** summary :: " + summary.toString());
            MyWriter.toConsole("\n** content :: " + content.toString());
            MyWriter.toConsole("\n** end process :: " + keyword);
            break ;

        } //loop: keywords


        //connection.close();

    }
}
