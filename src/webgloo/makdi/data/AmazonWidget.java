
package webgloo.makdi.data;

import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class AmazonWidget implements IData{

    private String amazonId ;

    public AmazonWidget(String amazonId) {
        this.amazonId = amazonId;
    }

    @Override
    public String getTitle() {
        return "amazon id - " + amazonId ;
    }

    public String getAmazonId() {
        return amazonId;
    }
    
    @Override
    public String toHtml() throws Exception {
        return HtmlGenerator.generateAmazonWidgetCode(this);
    }
    
    
}