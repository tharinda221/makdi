package webgloo.makdi.rss;

/**
 *
 * @author rajeevj
 */
public class GoogleNewsRssSource implements IRssSource {

    public final static String GOOGLE_NEWS_URI = "http://news.google.co.in/news?pz=1&cf=all&ned=us&hl=en&cf=all&output=rss";

    public GoogleNewsRssSource() {
    }

    public String invoke(int maxResults, String query) throws Exception {
        query = java.net.URLEncoder.encode(query, "UTF-8");
        return GoogleNewsRssSource.GOOGLE_NEWS_URI + "&num=" + maxResults + "&q=" + query;
    }
}
