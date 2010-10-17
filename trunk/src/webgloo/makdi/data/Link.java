
package webgloo.makdi.data;

/**
 *
 * @author rajeevj
 */
public class Link implements IData{

    private String title ;
    private String link ;
    
    public Link(String title, String link) {
      
        this.title = title ;
        this.link = link ;

    }
    
    public String getLink() {
        return link;
    }

    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("title = " + this.title + "\n");
        buffer.append("link = " + this.link + "\n");
        return buffer.toString();

    }

    @Override
    public String toHtml() throws Exception {
        return "<a href=\"" + this.link + "\">" +  this.title + " </a> " ;
    }
    
    
}
