package webgloo.makdi.drivers;

import java.util.ArrayList;
import java.util.List;
import twitter4j.Query;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.VanillaList;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 *
 */
public class TwitterDriver implements IDriver {

    public final static int MAX_RESULTS = 20;
    //default rate limit for twitter API is 150/hour only!
    public final static int REQUEST_DELAY = 30000;
    private int maxResults;
    private Transformer transformer;

    public TwitterDriver(Transformer transformer) {
        this.maxResults = MAX_RESULTS;
        this.transformer = transformer;
    }

    public TwitterDriver(Transformer transformer, int maxResults) {
        this.maxResults = maxResults;
        this.transformer = transformer;
    }

    @Override
    public String getName() {
        return IDriver.TWITTER_DRIVER;
    }

    @Override
    public List<IData> run(String tag) throws Exception {
        
        tag = this.transformer.transform(tag);
        //Urlencode the tag
        //tag = java.net.URLEncoder.encode(tag, "UTF-8");

        //use twitter4J library
        Twitter client = new TwitterFactory().getInstance();
        //wrap in quotes
        Query q = new Query("\"" + tag + "\"");
        q.setRpp(this.maxResults);
        MyWriter.toConsole("sending twitter request :: " + q.getQuery());
        List<Tweet> tweets = client.search(q).getTweets();

        VanillaList list = new VanillaList("Tweets");
       
        //collect data from all feeds and populate one list
        for (Tweet tweet : tweets) {
            //create a string item
            String data = tweet.getText();
            if (tweet.getLocation() != null) {
                data = data + " (" + tweet.getLocation() + ") ";
                
            }
            
            //System.out.println(data);
            list.add(data);

        }
        
        //Add list to return items
        List<IData> items = new ArrayList<IData>();
        items.add(list);
        //wait between results
        Thread.sleep(REQUEST_DELAY);
        return items;

    }

    public static void main(String[] args) throws Exception {

        //TwitterDriver driver = new TwitterDriver(new NullTransformer(),10);
        TwitterDriver driver = new TwitterDriver(new Transformer("watched", null), 10);
        String tag = "despicable me";
        List<IData> items = driver.run(tag);
        for (IData item : items) {
            MyWriter.toConsole(item.toHtml());
        }

    }
}
