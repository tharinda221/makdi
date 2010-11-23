package webgloo.makdi.drivers;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.TestData;

/**
 *
 * @author rajeevj
 *
 */
public class TestDriver implements IDriver {

    private int maxResults ;
    
    public TestDriver(int maxResults){
        this.maxResults = maxResults ;
    }

    @Override
    public String getName() {
        return "TEST_DRIVER";
    }


    @Override
    public long getDelay() {
        return 1000 ;
    }


    @Override
    public List<IData> run(String tag) throws Exception {

        List<IData> items = new ArrayList<IData>();

        //Fetch TestData for each tag
        for(int i = 0 ; i < this.maxResults ;i ++) {
            TestData t = new TestData("Test :: " + i);
            items.add(t);
        }
        
        return items;

    }

}
