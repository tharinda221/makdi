package webgloo.makdi.drivers;

import webgloo.makdi.drivers.yahoo.YahooBossWebDriver;
import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Post;

import webgloo.makdi.io.URLReader;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 *
 */
public class WikipediaDriver implements IDriver {

    public static final String WIKIPEDIA_PRINT_URI = "http://en.wikipedia.org/w/index.php?printable=yes&title=";
    private Transformer transformer;
    private int numberOfParagraphs ;

    public WikipediaDriver(Transformer transformer, int numberOfParagraphs) {
        this.transformer = transformer;
        this.numberOfParagraphs = numberOfParagraphs ;
    }

    public WikipediaDriver(int numberOfParagraphs) {
        this.transformer = new Transformer();
        this.numberOfParagraphs = numberOfParagraphs ;
    }
    
    @Override
    public String getName() {
        return IDriver.WIKIPEDIA_DRIVER;
    }

    @Override
    public long getDelay() {
        return 3000;
    }

    @Override
    public List<IData> run(String tag) throws Exception {
        MyTrace.entry("WikipediaDriver", "run()");

        //Transformer is for YAHOO BOSS driver
        //YAHOO BOSS will do its own encoding 
        YahooBossWebDriver boss = new YahooBossWebDriver(
                this.transformer,
                new String[]{"wikipedia.org"},
                0,
                2);
        List<IData> results = boss.run(tag);
        
        Post result = null;

        if (results.size() > 0) {
            result = (Post) results.get(0);

        }
        
        List<IData> items = new ArrayList<IData>();

        //No search result case
        if (result != null) {
            //get the original URL
            String originalUrl = result.getLink();
            
            //create printable url from original Url
            String token = getWikipediaToken(originalUrl);
            token = java.net.URLEncoder.encode(token, "UTF-8");
            String address = WikipediaDriver.WIKIPEDIA_PRINT_URI + token;
            //Now fetch content from printUrl
            MyTrace.debug("sending request to :: " + address);
            

            //Read response back from WIKIpedia
            String htmlResponse = URLReader.read(address);

            int lastParagraphIndex = 1 ;
            StringBuilder sb = new StringBuilder();

            //try to extract the first paragraph from wikipedia entry
            // this is strange but so far this heuristics is the only effective
            // method. we simply look for <p> and </p> tags
            
            for(int i = 1 ; i <= this.numberOfParagraphs ; i++ ) {
                int pos1  = htmlResponse.indexOf("<p>",lastParagraphIndex);
                int pos2 = htmlResponse.indexOf("</p>",pos1);
                String paragraph = htmlResponse.substring(pos1 +3, pos2);
                paragraph = paragraph.replaceAll("a href=\"/wiki","a href=\"http://en.wikipedia.org/wiki");
                sb.append(paragraph).append("\n <br> <br>");

                //set for next iteration
                lastParagraphIndex = pos2 + 4 ;


            }
                        
            //create a new post
            Post wikipost = new Post();
            wikipost.setTitle(tag);
            wikipost.setDescription(sb.toString());
            wikipost.setLink(originalUrl);
            items.add(wikipost);

        }

        MyTrace.exit("WikipediaDriver", "run()");

        return items;

    }

    private static String getWikipediaToken(String originalUrl) throws Exception {
        //get the token
        int pos = originalUrl.lastIndexOf('/');
        if (pos == -1) {
            throw new Exception("Invalid wikipedia original Url");
        }
        String token = originalUrl.substring(pos + 1);
        return token;

    }

    public static void main(String[] args) throws Exception {
        WikipediaDriver driver = new WikipediaDriver(new Transformer(null, "movie"),3);
        String tag = "a clockwork orange";
        List<IData> items = driver.run(tag);
        for (IData item : items) {
            System.out.println(item.toHtml());
        }

    }
}
