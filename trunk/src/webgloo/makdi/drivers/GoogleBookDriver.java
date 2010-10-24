package webgloo.makdi.drivers;

import com.google.gdata.client.books.BooksService;
import com.google.gdata.client.books.VolumeQuery;
import com.google.gdata.data.books.VolumeEntry;
import com.google.gdata.data.books.VolumeFeed;
import com.google.gdata.data.dublincore.Creator;
import com.google.gdata.data.dublincore.Description;
import com.google.gdata.data.dublincore.Identifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.Book;
import webgloo.makdi.data.IData;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 *
 */
public class GoogleBookDriver implements IDriver {

    
    public static final int MAX_RESULTS = 5;
    public static final String FEED_URL = "http://www.google.com/books/feeds/volumes";

    private int maxResults;
    private Transformer transformer ;


    public GoogleBookDriver(Transformer transformer) {
        this.maxResults = MAX_RESULTS;
        this.transformer = transformer ;
    }

    public GoogleBookDriver(Transformer transformer,int maxResults) {
        this.maxResults = maxResults;
        this.transformer = transformer ;
    }

    @Override
    public String getName() {
        return IDriver.GOOGLE_BOOK_DRIVER ;
    }


    @Override
    public long getDelay() {
        return 3000 ;
    }

    @Override
    public List<IData> run(String tag) throws Exception {

        MyTrace.entry("GoogleBookDriver", "run()");

        tag = this.transformer.transform(tag);
        tag = java.net.URLEncoder.encode(tag, "UTF-8");

        List<IData> items = new ArrayList<IData>();

        BooksService service = new BooksService("indigloo-makdi-1.0");
        VolumeQuery query = new VolumeQuery(new URL(FEED_URL));
        query.setMaxResults(this.maxResults);
        query.setFullTextQuery(tag);
        query.setMinViewability(VolumeQuery.MinViewability.NONE);
        //query.setMinViewability(VolumeQuery.MinViewability.PARTIAL);
        MyTrace.info("Sending request to: " + query.getUrl());
        VolumeFeed volumeFeed = service.query(query, VolumeFeed.class);

        List<VolumeEntry> entries = volumeFeed.getEntries();

        //plug in some intelligence to match titles
        if (entries.size() > 0) {
            items.add(createBook(entries.get(0)));
        }
        
        MyTrace.exit("GoogleBookDriver", "run()");
        
        return items;

    }

    private static Book createBook(VolumeEntry entry) {

        Book book = new Book();
        //For a google book there are 2 identifiers
        // isbn numbers start with ISBN, like 
        //Identifiers:4wY4jaxQuWAC,ISBN:1411683056

        for (Identifier identifier : entry.getIdentifiers()) {
            String value = identifier.getValue();
            if (value.startsWith("ISBN")) {
                book.addIsbn(value);
            } else {
                book.setBookId(value);
            }

        }

        //description
        if (entry.hasDescriptions()) {

            List<Description> descriptions = entry.getDescriptions();
            if (descriptions.size() > 0) {
                book.setDescription(descriptions.get(0).getValue());
            }

        }

        //preview, details and thumbnail link
        if (entry.hasEmbeddability()) {
            book.setIsEmbeddable(true);
            book.setPreviewLink(entry.getPreviewLink().getHref());
        }

        if(entry.getInfoLink() != null )
            book.setLink(entry.getInfoLink().getHref());
        if(entry.getThumbnailLink() != null)
            book.setImageLink(entry.getThumbnailLink().getHref());

        //authors
        if (entry.getCreators() != null) {
            for (Creator c : entry.getCreators()) {
                book.addAuthor(c.getValue());
            }
        }
        
        //title
        book.setTitle(entry.getTitle().getPlainText());

        return book;

    }
}

