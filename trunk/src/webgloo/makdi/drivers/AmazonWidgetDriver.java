package webgloo.makdi.drivers;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.AmazonWidget;
import webgloo.makdi.data.IData;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 *
 */
public class AmazonWidgetDriver implements IDriver {


    private String amazonId ;
    
    public AmazonWidgetDriver(String amazonId) {
       this.amazonId = amazonId ;
       
    }

    @Override
    public String getName() {
       return IDriver.AMAZON_WIDGET_DRIVER ;
    }
    
    @Override
    public long getDelay() {
        return  1 ;
    }
    
    @Override
    public List<IData> run(String tag) throws Exception {
        MyTrace.entry("AmazonWidgetDriver", "run()");
        //contextual widget - just ignore the tag
        
        List<IData> items = new ArrayList<IData>();
        MyTrace.debug("creating google search control :: keyword :: " + tag);
        items.add(new AmazonWidget(this.amazonId));
        MyTrace.exit("AmazonWidgetDriver", "run()");
        
        return items;

    }
}
