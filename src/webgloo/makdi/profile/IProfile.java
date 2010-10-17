package webgloo.makdi.profile;

import java.util.List;
import webgloo.makdi.drivers.IDriver;

/**
 *
 * @author rajeevj
 */
public interface IProfile {
    String getSiteGuid() ;
    List<IDriver> getDrivers();
    
    String getSiteDomain();
    String getSiteName();
    
}
