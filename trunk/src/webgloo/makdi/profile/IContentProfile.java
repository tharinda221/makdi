package webgloo.makdi.profile;

import java.util.List;
import webgloo.makdi.drivers.IDriver;

/**
 *
 * @author rajeevj
 */
public interface IContentProfile {
    String ACTION_TEST = "TEST";
    String ACTION_STORE = "STORE";

    String GOOGLE_HOT_TRENDS = "GOOGLE_HOT_TRENDS";
    String ARCADE_GAMES = "ARCADE_GAMES";
    
    String getSiteGuid() ;
    List<IDriver> getDrivers();
    String getSiteDomain();
    String getSiteName();
    List<IDriver> getFrontPageDrivers ();
    String getAction();
    List<String> getKeywords();
    String getName();
}
