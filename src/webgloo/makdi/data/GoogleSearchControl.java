
package webgloo.makdi.data;

import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class GoogleSearchControl implements IData{
    
    private String keyword ;
    
    public GoogleSearchControl(String keyword) {
      this.keyword = keyword ;
        
    }

    @Override
    public String getTitle() {
        return "google ajax search control " + keyword ;
    }

    public String getKeyword() {
        return keyword;
    }
    
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("keyword = " + this.keyword + "\n");
        return buffer.toString();

    }

    @Override
    public String toHtml() throws Exception {
        return HtmlGenerator.generateGoogleSearchControlCode(this);
    }
    
    
}