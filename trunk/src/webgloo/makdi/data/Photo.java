
package webgloo.makdi.data;

import com.google.gdata.util.common.base.StringUtil;
import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class Photo implements IData{

    private String title ;
    private String link ;
    private String description ;
    private String imageLink ;
    
    private int height ;
    private int width ;
    private String alignment ;

    public static String ALIGN_CENTER = "aligncenter";
    public static String ALIGN_LEFT = "alignleft";
    public static String ALIGN_RIGHT = "alignright";

    public Photo() {
        this.alignment = Photo.ALIGN_CENTER ;
    }
    
    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }
    
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("title = " + this.title + "\n");
        buffer.append("link = " + this.link + "\n");
        buffer.append("image link = " + this.imageLink + "\n");
        return buffer.toString();

    }

    @Override
    public String toHtml() throws Exception {
         //check imageLink
        if (StringUtil.isEmpty(this.imageLink)) {
            this.setImageLink(IData.IMAGE_404_URI);
        }
        
        return HtmlGenerator.generatePhotoCode(this);

    }
    
}
