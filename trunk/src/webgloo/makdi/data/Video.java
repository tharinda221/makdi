
package webgloo.makdi.data;

import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class Video implements IData{

    private String title ;
    private String videoId ;
    private String source ;
    private String description ;
    private String link ;
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return link;
    }

    public void setUrl(String url) {
        this.link = url;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("title = " + this.title + "\n");
        buffer.append("link = " + this.link + "\n");
        buffer.append("id = " + this.videoId + "\n");
        return buffer.toString();

    }

    @Override
    public String toHtml() throws Exception {
        //decide template based on Video source
        return HtmlGenerator.generateYoutubeCode(this);
        
    }

}
