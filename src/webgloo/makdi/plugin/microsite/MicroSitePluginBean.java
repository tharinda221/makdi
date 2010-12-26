package webgloo.makdi.plugin.microsite;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.plugin.IPlugin;

/**
 *
 * @author rajeevj
 */

public class MicroSitePluginBean implements IPlugin {

    public MicroSitePluginBean () {

    }
    
    public String getName() {
        return IPlugin.MICRO_SITE;
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
        List<IDriver> drivers = new ArrayList<IDriver>();
        return drivers;
    }

    @Override
    public List<IDriver> getFrontPageDrivers() {
        List<IDriver> drivers = new ArrayList<IDriver>();
        return drivers;
    }

    @Override
    public String getSiteGuid() {
        return "1019";

    }
    
}
