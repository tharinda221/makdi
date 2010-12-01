package webgloo.makdi.rss;

/**
 *
 * @author rajeevj
 */
public interface IRssSource {

    public String invoke(int maxResults, String query) throws Exception;
}
