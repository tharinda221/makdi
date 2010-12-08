package webgloo.makdi.plugin.trends.google;

import webgloo.makdi.drivers.yahoo.YahooBossNewsDriver;
import webgloo.makdi.drivers.yahoo.YahooAnswerDriver;
import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.drivers.yahoo.YahooBossImageDriver;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.plugin.IPlugin;

/**
 *
 * @author rajeevj
 */

public class GoogleHotTrendsPluginBean implements IPlugin {

    public String getName() {
        return IPlugin.GOOGLE_HOT_TRENDS;
    }
    
    public String getAction() {
        return IPlugin.ACTION_TEST ;
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
        MyTrace.entry("GoogleHotTrendsPluginBean", "getDrivers()");
        //Decide on what drivers to load
        List<IDriver> drivers = new ArrayList<IDriver>();
        //first video is already added - so start from 2
        // pull one more video 
        drivers.add(new YoutubeDriver(new Transformer(null, "news"),1,1));
        drivers.add(new TwitterDriver(10));
        drivers.add(new YahooAnswerDriver(1,4));
                
        MyTrace.exit("GoogleHotTrendsPluginBean", "getDrivers()");
        
        return drivers;
    }
    
    @Override
    public List<IDriver> getFrontPageDrivers() {
        List<IDriver> drivers = new ArrayList<IDriver>();
        //return yahoo boss news driver
        IDriver driver1 =  new YahooBossNewsDriver(null,0,5);
        drivers.add(driver1);
        drivers.add(new YahooBossImageDriver(new Transformer(null, "news"),null,0,1));

        return drivers;
    }

    @Override
    public String getSiteGuid() {
        return "1019";

    }
    
}
