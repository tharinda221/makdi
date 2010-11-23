package webgloo.makdi.data;

/**
 *
 * @author rajeevj
 */
public class Movie extends Photo2 implements IData {

   
    private String tmdbId;
    private String imdbId;
    private String rating;
  
    
    public static final String IMDB_PREFIX = "http://www.imdb.com/title/";

    public Movie(String tmdbId, String imdbId) {
        super();
        this.tmdbId = tmdbId;
        this.imdbId = imdbId;
        String address = IMDB_PREFIX + imdbId;
        super.setLink(address);
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTmdbId() {
        return tmdbId;
    }
    
}
