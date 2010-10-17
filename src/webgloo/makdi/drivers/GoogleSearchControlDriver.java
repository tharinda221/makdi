package webgloo.makdi.drivers;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.GoogleSearchControl;
import webgloo.makdi.data.IData;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 *
 */
public class GoogleSearchControlDriver implements IDriver {

    
    public final static int REQUEST_DELAY = 1000 ;
    private Transformer transformer ;

    public GoogleSearchControlDriver(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public String getName() {
       return IDriver.GOOGLE_AJAX_CONTROL_DRIVER ;
    }

    @Override
    public List<IData> run(String tag) throws Exception {

        tag = this.transformer.transform(tag);
        //Urlencode the tag
        //tag = java.net.URLEncoder.encode(tag, "UTF-8");

        List<IData> items = new ArrayList<IData>();

        MyWriter.toConsole("creating google search control :: keyword :: " + tag);
        items.add(new GoogleSearchControl(tag));
        //wait between results
        Thread.sleep(REQUEST_DELAY);
        return items;

    }
  
}
