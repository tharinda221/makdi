package webgloo.makdi.plugin.trends.google;

import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.data.News;
import webgloo.makdi.data.Photo2;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.plugin.AutoPostPlugin;
import webgloo.makdi.plugin.IPlugin;

/**
 *
 * @author rajeevj
 */
public class GoogleHotTrendsPlugin extends AutoPostPlugin {

    public GoogleHotTrendsPlugin() {
        super.setIncludeSummaryInPost(true);
    }

    //methods specific to google hot trends keywords
    public List<Keyword> loadNewKeywords() throws Exception {
        return GoogleHotTrendsKeywords.loadNewKeywords();
        //List<Keyword> list = new java.util.ArrayList<Keyword>();
        //list.add(new Keyword("Donna D Errico", "2010-12-09"));
        //return list;
    }
    
    public boolean getPostSummary(
            IPlugin profileBean,
            String token,
            StringBuilder title,
            StringBuilder summaryInHtml,
            StringBuilder summaryInText) throws Exception {

        boolean flag = false;
        //get summary using front page drivers
        // for google hot trends we have only one front page driver
        IDriver newsDriver = profileBean.getFrontPageDrivers().get(0);
        IDriver imageDriver = profileBean.getFrontPageDrivers().get(1);

        News biggestNews = null ;
        News news = null ;
        int size = 0 ;
        int currentItemSize = 0 ;
        String content = "" ;
        
        for (IData item : newsDriver.run(token)) {
            news  = (News) item;
            currentItemSize = news.getDescription().length();
            if(currentItemSize > size) {
                biggestNews = news ;
                size = currentItemSize;
            }
           
        }

        if(biggestNews != null) {
            title.append(biggestNews.getTitle());
            content = biggestNews.getDescription();
            summaryInText.append(biggestNews.getDescription());
            
            flag = true ;
            Thread.sleep(newsDriver.getDelay());
        }
        
        if(flag) {
            Thread.sleep(newsDriver.getDelay());
            
            for (IData item : imageDriver.run(token)) {
                
                Photo2 photo2 = (Photo2) item ;
                //No title for this photo
                photo2.setTitle("");
                photo2.setDescription(content);
                content = HtmlGenerator.generatePhoto2Code(photo2);
                break;
            }
            
            Thread.sleep(imageDriver.getDelay());
        }
        
        //right content is set!
        summaryInHtml.append(content);

        return flag;
    }
}
