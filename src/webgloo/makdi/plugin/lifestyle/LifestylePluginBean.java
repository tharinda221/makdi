package webgloo.makdi.plugin.lifestyle;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.plugin.IPlugin;

/**
 *
 * @author rajeevj
 */

public class LifestylePluginBean implements IPlugin {

    public LifestylePluginBean () {

    }
    
    public String getName() {
        return IPlugin.LIFE_STYLE ;
    }
    
    public String getAction() {
        return IPlugin.ACTION_TEST ;
    }
    
    public List<String> getKeywords() {
        List<String> keywords = new ArrayList<String>();
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
