package webgloo.makdi.profile;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 */

public class GoogleHotTrendProfile implements IContentProfile {

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
        
        //Google news is 1 
        drivers.add(new RssDriver(new Transformer(),1,4));
        drivers.add(new TwitterDriver(new Transformer(),10));
        drivers.add(new YoutubeDriver(new Transformer(),4));
        drivers.add(new YahooAnswerDriver(new Transformer(),4));
        
        MyTrace.exit("GoogleHotTrendProfile", "getDrivers()");
        
        return drivers;
    }

    @Override
    public List<IDriver> getFrontPageDrivers() {
        List<IDriver> drivers = new ArrayList<IDriver>();
        //return google news driver
        IDriver driver1 =  new RssDriver(new Transformer(),1,2);
        drivers.add(driver1);
        return drivers;
    }

    @Override
    public String getSiteGuid() {
        return "1019";

    }
    
}
