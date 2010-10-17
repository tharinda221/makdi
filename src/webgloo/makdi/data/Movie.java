package webgloo.makdi.data;

import com.google.gdata.util.common.base.StringUtil;
import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class Movie implements IData {

    private String title;
    private String tmdbId;
    private String imdbId;
    private String description;
    private String link;
    private String rating;
    private String imageLink;

    private int imageWidth ;
    private int imageHeight ;
    
    public static final String IMDB_PREFIX = "http://www.imdb.com/title/";

    public Movie(String tmdbId, String imdbId) {
        this.tmdbId = tmdbId;
        this.imdbId = imdbId;
        this.link = IMDB_PREFIX + imdbId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getLink() {
        return link;
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

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }
    

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("title = " + this.title + "\n");
        buffer.append("tmdbId = " + this.tmdbId + "\n");
        buffer.append("link= " + this.link + "\n");
        buffer.append("rating= " + this.link + "\n");
        buffer.append("imageLink= " + this.imageLink + "\n");

        return buffer.toString();

    }

    @Override
    public String toHtml() throws Exception {
        //check imageLink
        if (StringUtil.isEmpty(this.imageLink)) {
            this.setImageLink(IData.IMAGE_404_URI);
        }
        
        return HtmlGenerator.generateMovieCode(this);
    }
}
