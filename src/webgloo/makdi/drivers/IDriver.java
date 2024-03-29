
package webgloo.makdi.drivers;
import java.util.List;
import webgloo.makdi.data.IData;

/**
 *
 * @author rajeevj
 */
public interface IDriver {
    
    List<IData> run(String tag) throws Exception;
    String getName() ;
    long getDelay();

    //list all the known drivers in our system
    String GOOGLE_BASE_DRIVER  = "GOOGLE_BASE_DRIVER";
    String GOOGLE_BOOK_DRIVER  = "GOOGLE_BOOK_DRIVER";
    String GOOGLE_CSE_DRIVER = "GOOGLE_CSE_DRIVER" ;
    String GOOGLE_AJAX_CONTROL_DRIVER = "GOOGLE_AJAX_CONTROL_DRIVER";

    String RSS_DRIVER  = "RSS_DRIVER" ;
    String TWITTER_DRIVER = "TWITTER_DRIVER" ;
    String YAHOO_BOSS_WEB_DRIVER = "YAHOO_BOSS_WEB_DRIVER" ;
    String YAHOO_BOSS_IMAGE_DRIVER = "YAHOO_BOSS_IMAGE_DRIVER" ;

    String WIKIPEDIA_DRIVER = "WIKIPEDIA_DRIVER" ;
    String YOUTUBE_DRIVER = "YOUTUBE_DRIVER" ;
    String EBAY_DRIVER = "EBAY_DRIVER";
    String YAHOO_ANSWER_DRIVER="YAHOO_ANSWER_DRIVER";

    String TMDB_DRIVER = "TMDB_DRIVER" ;
    String AMAZON_WIDGET_DRIVER = "AMAZON_WIDGET_DRIVER" ;
    String ONLINE_GAME_CODE_DRIVER = "ONLINE_GAME_CODE_DRIVER" ;

    String DELICIOUS_DRIVER = "DELICIOUS_DRIVER" ;

    
}
