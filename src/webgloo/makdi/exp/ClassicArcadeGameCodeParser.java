
package webgloo.makdi.exp;

import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Post;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.drivers.yahoo.YahooBossWebDriver;
import webgloo.makdi.io.URLReader;

/**
 *
 * @author rajeevj
 *
 * Class to parse embed code from a page on classic arcade game
 * The magic is that we first locate this page using our Yahoo Boss driver
 * and then pull embed code out of that page. So all you need to know if the
 * games name! However it would me much easier to just make the Game - SWF link
 * tables instead of doing all this parsing...
 * 
 */
public class ClassicArcadeGameCodeParser {
    public long getDelay() {
        return 2000 ;
    }

    public void run(String tag) throws Exception {
        String[] siteNames = new String[] {"classicgamesarcade.com"} ;
        IDriver bossDriver = new YahooBossWebDriver(
                new Transformer(null,null),
                siteNames,
                0,
                1);
        List<IData> hits = bossDriver.run(tag);
        Post result ;
        
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
            
        }
        
    }

     public static void main(String[] args) throws Exception {

        ClassicArcadeGameCodeParser driver = new ClassicArcadeGameCodeParser();
        String tag = "super mario brothers";
        driver.run(tag);
        
    }
}
