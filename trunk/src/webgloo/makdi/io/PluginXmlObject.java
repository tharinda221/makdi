
package webgloo.makdi.io;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.plugin.IPlugin;


/**
 *
 * @author rajeevj
 */

public class PluginXmlObject implements IPlugin{


    private String siteDomain;
    private String siteName ;
    private String siteGuid ;
    
    private List<IDriver> drivers ;
    private List<IDriver> frontPageDrivers ;

    private String action ;
    private List<String> keywords ;
    private String name ;
    
    //default constrcutor
    public PluginXmlObject() {
        this.drivers = new ArrayList<IDriver>();
        this.frontPageDrivers = new ArrayList<IDriver>();
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
    
    public List<IDriver> getFrontPageDrivers() {
        return this.frontPageDrivers;
    }
    
    public void  setFrontPageDrivers(List<IDriver> fdrivers) {
        this.frontPageDrivers = fdrivers ;
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
