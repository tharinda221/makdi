package webgloo.makdi.processor;

import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.data.News;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.profile.IContentProfile;

/**
 *
 * @author rajeevj
 */
public class GoogleHotTrendsProcessor extends AutoPostProcessor{


    public GoogleHotTrendsProcessor() {

    }

    //methods specific to google hot trends keywords
    public List<Keyword> loadNewKeywords() throws Exception {
        return GoogleHotTrendKeywords.loadNewKeywords();
    }
    
    public  boolean getPageSummary(
            IContentProfile profileBean,
            String token,
            StringBuilder title,
            StringBuilder summary) throws Exception {

        boolean flag = false ;
        //get summary using front page drivers
        // for google hot trends we have only one front page driver
        IDriver frontPageDriver = profileBean.getFrontPageDrivers().get(0);
        List<IData> items = frontPageDriver.run(token);

        for (IData item : items) {
            summary.append(HtmlGenerator.generateAutoPostNews((News) item));
            title.append(item.getTitle());

        }
        
        flag = items.size() > 0 ? true : false ;
        return flag ;
    }

    
}
