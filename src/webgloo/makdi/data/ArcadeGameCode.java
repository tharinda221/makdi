
package webgloo.makdi.data;

import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class ArcadeGameCode implements IData{

    private String code ;
    private String title ;
    
    public ArcadeGameCode(String title ,String code) {
        this.code = code;
        this.title = title ;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public String getCode() {
        return this.code;
    }
    
    @Override
    public String toHtml() throws Exception {
        return HtmlGenerator.generateArcadeGameCode(this);
    }
    
    
}