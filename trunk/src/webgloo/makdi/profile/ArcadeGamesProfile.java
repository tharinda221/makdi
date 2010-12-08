package webgloo.makdi.profile;

import webgloo.makdi.drivers.yahoo.YahooAnswerDriver;
import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 */

public class ArcadeGamesProfile implements IContentProfile {

    public ArcadeGamesProfile () {

    }
    
    public String getName() {
        return IContentProfile.ARCADE_GAMES;
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
        MyTrace.entry("ArcadeGameProfile", "getDrivers()");
        //Decide on what drivers to load
        List<IDriver> drivers = new ArrayList<IDriver>();
        
        drivers.add(new OnlineGameCodeDriver());
        drivers.add(new WikipediaDriver(new Transformer(null, "game"),1));
        drivers.add(new YahooAnswerDriver(new Transformer(null, " game"),1,6));
        
        MyTrace.exit("ArcadeGameProfile", "getDrivers()");
        
        return drivers;
    }

    @Override
    public List<IDriver> getFrontPageDrivers() {
        List<IDriver> drivers = new ArrayList<IDriver>();
        
        drivers.add(new WikipediaDriver(new Transformer(null, "game"),1));
        drivers.add(new YoutubeDriver(new Transformer(null, "game"),1,1));
        return drivers;
    }

    @Override
    public String getSiteGuid() {
        return "1019";

    }
    
}
