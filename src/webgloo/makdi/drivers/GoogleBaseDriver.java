package webgloo.makdi.drivers;

import com.google.api.gbase.client.GoogleBaseEntry;
import com.google.api.gbase.client.GoogleBaseFeed;
import com.google.api.gbase.client.GoogleBaseQuery;
import com.google.api.gbase.client.GoogleBaseService;
import com.google.gdata.data.TextContent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Photo;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 */
public class GoogleBaseDriver implements IDriver {

    //public snippets feed
    public static final String SNIPPETS_FEED = "http://base.google.com/base/feeds/snippets";
    public static final int MAX_RESULTS = 10;
    public static final long REQUEST_DELAY = 10000;

    private int maxResults;
    private Transformer transformer;

    public GoogleBaseDriver(Transformer transformer) {
        this.maxResults = MAX_RESULTS;
        this.transformer = transformer;
    }

    public GoogleBaseDriver(Transformer transformer,int maxResults) {
        this.maxResults = maxResults;
        this.transformer = transformer;
    }

    @Override
    public String getName() {
        return IDriver.GOOGLE_BASE_DRIVER ;
    }

    @Override
    public List<IData> run(String tag) throws Exception {
        tag = this.transformer.transform(tag);
        //Urlencode the tag
        tag = java.net.URLEncoder.encode(tag, "UTF-8");
        List<IData> items = new ArrayList<IData>();

        GoogleBaseService service = new GoogleBaseService("indigloo-makdi-1.0");
        URL feedUrl = new URL(SNIPPETS_FEED);
        // Create a query URL from the given arguments
        GoogleBaseQuery query = new GoogleBaseQuery(feedUrl);

        //strict query
        //query.setGoogleBaseQuery(QUERY);

        //Query like search box on google base page!
        query.setFullTextQuery(tag);
        // Display the URL generated by the API
        MyWriter.toConsole("Sending request to: " + query.getUrl());
        query.setResultFormat(GoogleBaseQuery.ResultFormat.ATOM);
        query.setMaxResults(this.maxResults);

        GoogleBaseFeed feed = service.query(query, GoogleBaseFeed.class);

        for (GoogleBaseEntry entry : feed.getEntries()) {
            items.add(createPhoto(entry));
        }
        
        Thread.sleep(REQUEST_DELAY);

        return items;

    }

    //create a photo out of this google base entry
    private IData createPhoto(GoogleBaseEntry entry) throws Exception{
        Photo photo = new Photo();

        photo.setTitle(entry.getTitle().getPlainText());
        photo.setImageLink(entry.getGoogleBaseAttributes().getImageLink());
        photo.setLink(entry.getHtmlLink().getHref());

        TextContent content = (TextContent) entry.getContent();
        photo.setDescription(content.getContent().getPlainText());
        return photo;
    }
}

