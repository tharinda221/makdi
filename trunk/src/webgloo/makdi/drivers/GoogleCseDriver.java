package webgloo.makdi.drivers;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.GoogleCse;
import webgloo.makdi.data.IData;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 *
 */
public class GoogleCseDriver implements IDriver {

    public final static int REQUEST_DELAY = 1000;
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
    public List<IData> run(String tag) throws Exception {

        tag = this.transformer.transform(tag);
        //Urlencode the tag
        //tag = java.net.URLEncoder.encode(tag, "UTF-8");
        
        List<IData> items = new ArrayList<IData>();
        MyWriter.toConsole("creating google search control :: keyword :: " + tag);
        items.add(new GoogleCse(this.searchId));
        //wait between results
        Thread.sleep(REQUEST_DELAY);
        return items;

    }
}
