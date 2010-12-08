package webgloo.makdi.plugin.arcade;

import webgloo.makdi.drivers.yahoo.YahooAnswerDriver;
import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.plugin.IPlugin;

/**
 *
 * @author rajeevj
 */

public class ArcadeGamesPluginBean implements IPlugin {

    public ArcadeGamesPluginBean () {

    }
    
    public String getName() {
        return IPlugin.ARCADE_GAMES;
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
        MyTrace.entry("ArcadeGamesPluginBean", "getDrivers()");
        //Decide on what drivers to load
        List<IDriver> drivers = new ArrayList<IDriver>();
        
        drivers.add(new OnlineGameCodeDriver());
        drivers.add(new WikipediaDriver(new Transformer(null, "game"),1));
        drivers.add(new YahooAnswerDriver(new Transformer(null, " game"),1,6));
        
        MyTrace.exit("ArcadeGamesPluginBean", "getDrivers()");
        
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
