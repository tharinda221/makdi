
package webgloo.makdi.data;

/**
 *
 * @author rajeevj
 */
public interface IData {

    String toHtml() throws Exception;
    String getTitle() ;
    
    //@todo move to a common location
    String IMAGE_404_URI = "/art/common/images/image-404.jpg" ;
    
}
