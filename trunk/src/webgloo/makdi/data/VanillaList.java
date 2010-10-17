
package webgloo.makdi.data;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class VanillaList implements IData{

    private List<String> items ;
    private String title ;

    public VanillaList(String title) {
        this.title = title ;
        this.items = new ArrayList<String>();
    }

    public VanillaList(String title,List<String> items) {
        this.title = title ;
        this.items = items;
    }

    public List<String> getItems() {
        return this.items;
    }

    @Override
    public String getTitle() {
        return this.title;
    }
    
    public void add(String item){
        this.items.add(item);
    }
    
    @Override
    public String toString() {
        StringBuilder st = new StringBuilder();
        
        int count = 1 ;
        for(String item : items){
             st.append(count + "::" + item + "\n"); count++ ;
        }
        return st.toString();
    }

    @Override
    public String toHtml() throws Exception {
        return HtmlGenerator.generateVanillaListCode(this);
    }


}
