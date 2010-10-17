
package webgloo.makdi.data;


/**
 *
 * @author rajeevj
 */
public class TestData implements IData{

    private String message;

    public TestData(String message) {
        this.message = message ;
    }

    @Override
    public String getTitle() {
        return "test data " ;
    }

    @Override
    public String toHtml() throws Exception {
        return " <p> <b> HTML :: " + this.message + " </b> </p> <br>" ;
    }
    
    @Override
    public String toString() {
        return this.message ;
    }
    
}