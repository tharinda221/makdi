package webgloo.makdi.profile;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.rss.GoogleNewsRssSource;

/**
 *
 * @author rajeevj
 */

public class GoogleHotTrendsProfile implements IContentProfile {

    public String getName() {
        return IContentProfile.GOOGLE_HOT_TRENDS;
    }
    
    public String getAction() {
        return IContentProfile.ACTION_TEST ;
    }
    
    public List<String> getKeywords() {
        List<String> keywords = new ArrayList<String>();
        keywords.add("keyword1");
        keywords.add("keyword2");
        
        return keywords ;
    }

    @Override
    public String getSiteDomain() {
        return "www.abcd.com" ;
    }

    @Override
    public String getSiteName() {
        return "ABCD site";
    }
    
    @Override
    public List<IDriver> getDrivers() {
        MyTrace.entry("GoogleHotTrendProfile", "getDrivers()");
        //Decide on what drivers to load
        List<IDriver> drivers = new ArrayList<IDriver>();
        //first video is already added - so start from 2
        // pull one more video 
        drivers.add(new YoutubeDriver(2,1));
        drivers.add(new YahooAnswerDriver(1,4));
                
        MyTrace.exit("GoogleHotTrendProfile", "getDrivers()");
        
        return drivers;
    }

    @Override
    public List<IDriver> getFrontPageDrivers() {
        List<IDriver> drivers = new ArrayList<IDriver>();
        //return google news driver
        IDriver driver1 =  new YahooBossNewsDriver(null,0,10);
        drivers.add(driver1);
        drivers.add(new YoutubeDriver(1,1));

        return drivers;
    }

    @Override
    public String getSiteGuid() {
        return "1019";

    }
    
}
