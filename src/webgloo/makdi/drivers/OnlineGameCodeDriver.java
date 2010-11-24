package webgloo.makdi.drivers;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.GameCode;
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
        return IDriver.ONLINE_GAME_CODE_DRIVER;
    }

    @Override
    public long getDelay() {
        return 1000;
    }

    @Override
    public List<IData> run(String tag) throws Exception {

        List<IData> items = new ArrayList<IData>();
        //for this game generate - embed code
        GameCode code = new GameCode(tag);
        items.add(code);
        return items;
        
    }
}
