package webgloo.makdi.processor;

import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.data.Post;
import webgloo.makdi.data.Video;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.profile.IContentProfile;

/**
 *
 * @author rajeevj
 */
public class ArcadeGamesProcessor extends AutoPostProcessor{


    public ArcadeGamesProcessor() {

    }

    //methods specific to arcade games
    public List<Keyword> loadNewKeywords() throws Exception {
        return null ;
    }
    
    public  boolean getPageSummary(
            IContentProfile profileBean,
            String token,
            StringBuilder title,
            StringBuilder summary) throws Exception {

        boolean flag = false ;
        //get summary using front page drivers
        // for google hot trends we have only one front page driver
        if(profileBean.getFrontPageDrivers()!= null
                && profileBean.getFrontPageDrivers().size() >= 2) {

            
            IDriver wikiDriver = profileBean.getFrontPageDrivers().get(0);
            IDriver youtubeDriver = profileBean.getFrontPageDrivers().get(1);


            for (IData item : wikiDriver.run(token)) {
                summary.append(HtmlGenerator.generatePostCode((Post) item));
                title.append(item.getTitle());
                flag = true ;
            }

            for (IData item : youtubeDriver.run(token)) {
                summary.append(HtmlGenerator.generateYoutubeCode((Video) item));
                if(!flag) {
                    title.append(item.getTitle());
                    flag = true ;
                }

            }
            
            
        }
        
        return flag ;
    }

    
}
