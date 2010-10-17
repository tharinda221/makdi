
package webgloo.makdi.data;

/**
 *
 * @author rajeevj
 */
public class HtmlFragment {
    
    private String html ;
    private String driver ;
    private String key ;

    public HtmlFragment(String key, String driver ,String html) {
        this.html = html ;
        this.key = key ;
        this.driver = driver ;
    }

    public String getDriver() {
        return driver;
    }
    
    public String getKey() {
        return key;
    }

    public String getHtml() throws Exception {
        return this.html ;
    }
    
}
