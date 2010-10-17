
package webgloo.makdi.data;

import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class GoogleCse implements IData{

    private String searchId ;

    public GoogleCse(String searchId) {
        this.searchId = searchId;
    }

    @Override
    public String getTitle() {
        return "google cse " + searchId ;
    }

    public String getSearchId() {
        return searchId;
    }
    
    @Override
    public String toHtml() throws Exception {
        return HtmlGenerator.generateGoogleCseCode(this);
    }
    
    
}