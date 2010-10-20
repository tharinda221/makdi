package webgloo.makdi.profile;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.drivers.Transformer;

/**
 *
 * @author rajeevj
 */

public class GoogleHotTrendProfile implements IProfile {

    @Override
    public String getSiteDomain() {
        return "sample.indigloo.net" ;
    }

    @Override
    public String getSiteName() {
        return "sample.indigloo.net - all profiles";
    }
    
    @Override
    public List<IDriver> getDrivers() {
        //Decide on what drivers to load
        List<IDriver> drivers = new ArrayList<IDriver>();
        drivers.add(new GoogleSearchControlDriver(new Transformer()));
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
        return "7dc53df5-703e-49b3-8670-b1c468f47f1f";

    }
}
