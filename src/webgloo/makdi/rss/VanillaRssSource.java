package webgloo.makdi.rss;

/**
 *
 * @author rajeevj
 */
public class VanillaRssSource implements IRssSource {

    String feedURI;

    public VanillaRssSource(String feedURI) {
        this.feedURI = feedURI;
    }

    public String invoke(int maxResults, String query) throws Exception {
        return this.feedURI;
    }
}
