
package webgloo.makdi.io;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.profile.IProfileBean;


/**
 *
 * @author rajeevj
 */

public class ProfileXmlObject implements IProfileBean{

    private String siteDomain;
    private String siteName ;
    private String siteGuid ;
    
    private List<IDriver> drivers ;

    private IDriver frontPageDriver ;
    
    //default constrcutor
    public ProfileXmlObject() {
        this.drivers = new ArrayList<IDriver>();
    }

    public IDriver getFrontPageDriver() {
        return this.frontPageDriver;
    }
    
    public void  setFrontPageDriver(IDriver fdriver) {
        this.frontPageDriver = fdriver ;
    }

    public List<IDriver> getDrivers() {
        return drivers;
    }
    
    
    public void setDrivers(List<IDriver> drivers) {
        this.drivers = drivers;
    }
    
    public String getSiteDomain() {
        return siteDomain;
    }

    public void setSiteDomain(String siteDomain) {
        this.siteDomain = siteDomain;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteGuid() {
        return siteGuid;
    }

    public void setSiteGuid(String siteGuid) {
        this.siteGuid = siteGuid;
    }
    
    public void addDriver(IDriver driver) {
        this.drivers.add(driver);
    }


}
