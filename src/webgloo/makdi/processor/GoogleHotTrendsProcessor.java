package webgloo.makdi.processor;

import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.data.News;
import webgloo.makdi.data.Video;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.profile.IContentProfile;

/**
 *
 * @author rajeevj
 */
public class GoogleHotTrendsProcessor extends AutoPostProcessor {

    public GoogleHotTrendsProcessor() {
        super.setIsSummaryInContent(true);
    }

    //methods specific to google hot trends keywords
    public List<Keyword> loadNewKeywords() throws Exception {
        return GoogleHotTrendsKeywords.loadNewKeywords();
        //List<Keyword> list = new ArrayList<Keyword>();
        //list.add(new Keyword("alessandra ambrosio", "2010-11-26"));
        //return list;
    }
    
    public boolean getPageSummary(
            IContentProfile profileBean,
            String token,
            StringBuilder title,
            StringBuilder summary) throws Exception {

        boolean flag = false;
        //get summary using front page drivers
        // for google hot trends we have only one front page driver
        IDriver newsDriver = profileBean.getFrontPageDrivers().get(0);
        IDriver youtubeDriver = profileBean.getFrontPageDrivers().get(1);

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
            content = HtmlGenerator.wrapInDiv(biggestNews.getDescription());
            title.append(biggestNews.getTitle());
            flag = true ;
            Thread.sleep(newsDriver.getDelay());
        }
        
        if(flag) {
            Thread.sleep(newsDriver.getDelay());
            for (IData item : youtubeDriver.run(token)) {
                
                Video video = (Video) item ;
                video.setTitle("");
                video.setAlignment(Video.ALIGN_LEFT);
                video.setDescription(content + video.getDescription());
                content = HtmlGenerator.generateYoutubeCode(video);
                break;
            }
            
            Thread.sleep(youtubeDriver.getDelay());
        }
        
        //right content is set!
        summary.append(content);

        return flag;
    }
}
