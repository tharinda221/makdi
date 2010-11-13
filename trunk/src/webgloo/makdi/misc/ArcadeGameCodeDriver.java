
package webgloo.makdi.misc;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.ArcadeGameCode;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Post;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.drivers.YahooBossDriver;
import webgloo.makdi.io.URLReader;

/**
 *
 * @author rajeevj
 */
public class ArcadeGameCodeDriver implements IDriver{

    public long getDelay() {
        return 2000 ;
    }

    public String getName() {
        return IDriver.ARCADE_GAME_CODE_DRIVER ;
    }

    public List<IData> run(String tag) throws Exception {
        String[] siteNames = new String[] {"classicgamesarcade.com"} ;
        IDriver bossDriver = new YahooBossDriver(
                new Transformer(null,null),
                siteNames,
                0,
                1);
        List<IData> hits = bossDriver.run(tag);
        
        Post result ;
        List<IData> items = new ArrayList<IData>();
        
        if (hits.size() > 0) {
            result = (Post) hits.get(0);
            String address = result.getLink() ;
            String content = URLReader.read(address);
            
            String  TOKEN = "copy and paste the code below" ;
            //look for first TOKEN
            int pos = content.indexOf(TOKEN);
            int pos1 = content.indexOf("<embed", pos);
            int pos2 = content.indexOf("</embed>",pos);
            String embedCode = content.substring(pos1, pos2+8);
            ArcadeGameCode item = new ArcadeGameCode(tag,embedCode);
            items.add(item);
        }


        return items ;
        
    }

     public static void main(String[] args) throws Exception {
        
        ArcadeGameCodeDriver driver = new ArcadeGameCodeDriver();
        String tag = "super mario brothers";
        List<IData> items = driver.run(tag);
        for(IData item: items )
            System.out.println(item.toHtml());

    }

   

}
