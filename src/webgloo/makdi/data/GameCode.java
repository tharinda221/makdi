package webgloo.makdi.data;

import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class GameCode implements IData {

    private String title ;
    private String swfMovieName ;

    public GameCode(String title) {
        this.title = title ;
        this.swfMovieName = MyUtils.convertPageNameToId(title);

    }

    public String getSwfMovieName() {
        return swfMovieName;
    }

    public void setSwfMovieName(String swfMovieName) {
        this.swfMovieName = swfMovieName;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
    
    public String toHtml() throws Exception {
        return HtmlGenerator.generateGameCode(this);
    }

}
