package webgloo.makdi.plugin;

import java.util.List;
import webgloo.makdi.drivers.IDriver;

/**
 *
 * @author rajeevj
 */
public interface IPlugin {
    String ACTION_TEST = "TEST";
    String ACTION_STORE = "STORE";

    String GOOGLE_HOT_TRENDS = "GOOGLE_HOT_TRENDS";
    String ARCADE_GAMES = "ARCADE_GAMES";
    String MICRO_SITE = "MICRO_SITE";
    
    String getSiteGuid() ;
    List<IDriver> getDrivers();
    String getSiteDomain();
    String getSiteName();
    List<IDriver> getFrontPageDrivers ();
    String getAction();
    List<String> getKeywords();
    String getName();
}
