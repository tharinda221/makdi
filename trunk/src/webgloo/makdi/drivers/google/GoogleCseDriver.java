package webgloo.makdi.drivers.google;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.GoogleCse;
import webgloo.makdi.data.IData;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 *
 */
public class GoogleCseDriver implements IDriver {


    private String searchId ;

    private Transformer transformer ;

    public GoogleCseDriver(Transformer transformer,String searchId) {
       this.searchId = searchId ;
       this.transformer = transformer ;
    }

    @Override
    public String getName() {
       return IDriver.GOOGLE_CSE_DRIVER ;
    }


    @Override
    public long getDelay() {
        return  1 ;
    }
    
    @Override
    public List<IData> run(String tag) throws Exception {
        MyTrace.entry("GoogleCseDriver", "run()");

        tag = this.transformer.transform(tag);
        //Urlencode the tag
        //tag = java.net.URLEncoder.encode(tag, "UTF-8");
        
        List<IData> items = new ArrayList<IData>();
        MyTrace.debug("creating google search control :: keyword :: " + tag);
        items.add(new GoogleCse(this.searchId));
        
        MyTrace.exit("GoogleCseDriver", "run()");
        
        return items;

    }
}
