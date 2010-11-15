package webgloo.makdi.drivers;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;

/**
 *
 * @author rajeevj
 *
 */
public class OnlineGameCodeDriver implements IDriver {

    public OnlineGameCodeDriver() {
                
    }

    @Override
    public String getName() {
        return "TEST_DRIVER";
    }

    @Override
    public long getDelay() {
        return 2000 ;
    }
    
    @Override
    public List<IData> run(String tag) throws Exception {

        List<IData> items = new ArrayList<IData>();
        return items;

    }

}
