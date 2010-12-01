package webgloo.makdi.processor;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.data.News;
import webgloo.makdi.data.Video;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.profile.IContentProfile;
import webgloo.makdi.util.HtmlToText;

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
        //return GoogleHotTrendKeywords.loadNewKeywords();
        List<Keyword> list = new ArrayList<Keyword>();
        list.add(new Keyword("alessandra ambrosio", "2010-11-26"));
        list.add(new Keyword("mox", "2010-11-26"));
        
        return list;
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
        
        String content = "";
        

        for (IData item : newsDriver.run(token)) {
            
            Thread.sleep(newsDriver.getDelay());
            
            News news = (News) item;
            String text = HtmlToText.extractUsingSwingKit(news.getDescription());
            content = HtmlGenerator.wrapInDiv(text);
            title.append(news.getTitle());
            flag = true ;
            
            break;
        }
        
        if(flag) {
            for (IData item : youtubeDriver.run(token)) {

                Thread.sleep(youtubeDriver.getDelay());
                
                Video video = (Video) item ;
                video.setTitle("");
                video.setAlignment(Video.ALIGN_LEFT);
                video.setDescription(content + video.getDescription());
                content = HtmlGenerator.generateYoutubeCode(video);
                break;
            }
        }
        
        //right content is set!
        summary.append(content);

        return flag;
    }
}
