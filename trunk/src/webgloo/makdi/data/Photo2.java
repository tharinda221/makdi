
package webgloo.makdi.data;

import com.google.gdata.util.common.base.StringUtil;
import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class Photo2 extends Photo implements IData{

   

    public Photo2() {
        super();
    }


    @Override
    public String toHtml() throws Exception {
         //check imageLink
        if (StringUtil.isEmpty(super.getImageLink())) {
            this.setImageLink(IData.IMAGE_404_URI);
        }
        
        return HtmlGenerator.generatePhoto2Code(this);

    }
    
}
