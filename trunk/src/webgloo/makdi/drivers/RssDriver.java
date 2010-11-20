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

/**
 *
 * @author rajeevj
 */
public class RssDriver implements IDriver {

    
    public final static String GOOGLE_NEWS_URI = "http://news.google.co.in/news?pz=1&cf=all&ned=us&hl=en&cf=all&output=rss";
    public static final String GOOGLE_BASE_URI = "http://base.google.com/base/feeds/snippets?alt=atom";
    
    //known sources
    
    public static final int GOOGLE_NEWS = 1 ;
    public static final int GOOGLE_BASE = 2;

    public static final int MAX_RESULTS = 10;


    private String feedURI;
    private int maxResults;
    private int source ;
    private Transformer transformer;
    private boolean isExternal = false ;
    
    /**
     *
     * @param feedURI is complete feedURI including start-index and max-results etc.
     * The automatic appending of query etc only works for known URL
     * 
     */

    public RssDriver(String feedURI,int maxResults) {
        this.feedURI = feedURI;
        this.maxResults = MAX_RESULTS ;
        this.transformer = new Transformer();
        this.isExternal = true ;
        
    }

    public RssDriver(Transformer transformer,String feedURI, int maxResults) {
        this.feedURI = feedURI;
        this.maxResults = maxResults ;
        this.transformer = transformer;
        this.isExternal = true ;

    }
    
    public RssDriver(int source,int maxResults) {
        this.feedURI = null ;
        this.source = source ;
        this.maxResults = maxResults ;
        this.transformer = new Transformer();
    }
    
    public RssDriver(Transformer transformer,int source , int maxResults) {
        this.feedURI = null ;
        this.source = source ;
        this.maxResults = maxResults;
        this.transformer = transformer;
    }

    @Override
    public String getName() {
        return IDriver.RSS_DRIVER ;
    }


    @Override
    public long getDelay() {
        return 3000 ;
    }

    @Override
    public List<IData> run(String tag) throws Exception {
        MyTrace.entry("RssDriver", "run()");

        tag = this.transformer.transform(tag);
        
        String feedSource = this.feedURI ;
        // create feed URI for internal sources
        if(!this.isExternal) {
            //create final feed URI based on source, tag and max-results
            //Urlencode the tag
            tag = java.net.URLEncoder.encode(tag, "UTF-8");
            feedSource = createRssFeedURI(this.source,this.maxResults,tag);
        }
        
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

    private String createRssFeedURI(int source, int maxResults, String tag) throws Exception{
        String feedAddress = null ;

        switch(source) {
            case GOOGLE_NEWS :
                feedAddress = GOOGLE_NEWS_URI + "&num=" + maxResults + "&q=" + tag;
                break;
            case GOOGLE_BASE :
                feedAddress = GOOGLE_BASE_URI + "&max-results=" + maxResults + "&q=" + tag;
                break;
            default :
                throw new Exception ("Unknown source for RSS Driver");
        }

        return feedAddress ;
    }
    
    private News createNews(SyndEntry entry) {
        News news = new News();
        news.setAuthor(entry.getAuthor());
        news.setLink(entry.getLink());
        news.setTitle(entry.getTitle());
        if (entry.getDescription() != null) {
            news.setDescription(entry.getDescription().getValue());
        }
        /*
        MyWriter.toConsole(news.toString());
        MyWriter.toConsole(news.getDescription());
        MyWriter.toConsole("\n\n");
         */
        return news;

    }
}
