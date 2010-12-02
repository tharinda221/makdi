package webgloo.makdi.drivers;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.News;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.rss.GoogleNewsRssSource;
import webgloo.makdi.rss.IRssSource;
import webgloo.makdi.util.HtmlToText;

/**
 *
 * @author rajeevj
 */
public class RssDriver implements IDriver {

    private int maxResults ;
    private Transformer transformer ;
    private IRssSource source ;

    public RssDriver(Transformer transformer,int maxResults,IRssSource command) {
        this.maxResults = maxResults ;
        this.transformer = transformer ;
        this.source = command ;
        
    }

    public RssDriver(int maxResults,IRssSource command) {
        this.maxResults = maxResults ;
        this.transformer = new Transformer() ;
        this.source  = command ;
    }
    
    @Override
    public String getName() {
        return IDriver.RSS_DRIVER;
    }

    @Override
    public long getDelay() {
        return 3000;
    }

    @Override
    public List<IData> run(String tag) throws Exception {
        MyTrace.entry("RssDriver", "run()");

        tag = this.transformer.transform(tag);
        String feedSource = this.source.invoke(this.maxResults, tag);
        
        //For every tag and every source fetch required feeds
        List<IData> feeds = new ArrayList<IData>();
        MyTrace.debug("using feed source :: " + feedSource);

        URL feedUrl = new URL(feedSource);
        URLConnection uc = feedUrl.openConnection();
        uc.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8");

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(uc));
        List<SyndEntry> entries = feed.getEntries();

        for (SyndEntry entry : entries) {
            feeds.add(createNews(entry));

        }

        MyTrace.exit("RssDriver", "run()");

        return feeds;

    }
    
    private News createNews(SyndEntry entry) {
        News news = new News();
        news.setAuthor(entry.getAuthor());
        news.setLink(entry.getLink());
        news.setTitle(entry.getTitle());
        if (entry.getDescription() != null) {
            news.setDescription(entry.getDescription().getValue());
        }

        return news;

    }

    public static void main(String[] args) throws Exception{
        IDriver driver = new RssDriver(2,new GoogleNewsRssSource());
        String tag = "alessandra ambrosio";
        List<IData> items = driver.run(tag);
        for(IData item : items) {
            News news = (News) item ;
            System.out.println(news.getTitle());
            System.out.println(news.getDescription());
            System.out.println("\n\n");
            System.out.println(HtmlToText.extractUsingSwingKit(news.getDescription()));

            
        }
    }
}
