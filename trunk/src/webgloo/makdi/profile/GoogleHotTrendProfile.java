package webgloo.makdi.profile;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.drivers.Transformer;

/**
 *
 * @author rajeevj
 */

public class GoogleHotTrendProfile implements IProfileBean {

    @Override
    public String getSiteDomain() {
        return null ;
    }

    @Override
    public String getSiteName() {
        return null;
    }
    
    @Override
    public List<IDriver> getDrivers() {
        //Decide on what drivers to load
        List<IDriver> drivers = new ArrayList<IDriver>();
        
        //Google news is 1 
        drivers.add(new RssDriver(new Transformer(),1,2));
        drivers.add(new YoutubeDriver(new Transformer(null, "trailer"), 1));
        drivers.add(new YahooAnswerDriver(new Transformer(),4));
        return drivers;
    }

    @Override
    public IDriver getFrontPageDriver() {
        //return google news driver
        return new RssDriver(new Transformer(),1,2);
    }

    @Override
    public String getSiteGuid() {
        return null;

    }
        

}
