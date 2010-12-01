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
         super.setIsSummaryInContent(false);
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

            String content = "";
                        
            //found wikipedia entry
            for (IData item : wikiDriver.run(token)) {
                Post post = (Post) item;
                //use wikipedia description and title
                title.append(item.getTitle());
                content = post.getDescription();
                flag = true ;
                break ;
            }
            
            for (IData item : youtubeDriver.run(token)) {
                Video video = (Video) item ;
                
                if(!flag) {
                    //use video title and description
                    // use alignment center
                    title.append(item.getTitle());
                    content = HtmlGenerator.generateYoutubeCode(video);
                    flag = true ;
                } else {
                    //Use wikipedia title and description
                    video.setTitle(title.toString());
                    video.setDescription(content);
                    video.setAlignment(Video.ALIGN_LEFT);
                    //generate new content
                    content = HtmlGenerator.generateYoutubeCode(video);
                }
                               
                break;

            }

            //right content has been set
            summary.append(content);
            
        }
        
        return flag ;
    }

    
}
