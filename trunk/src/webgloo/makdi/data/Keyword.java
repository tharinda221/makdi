
package webgloo.makdi.data;

/**
 *
 * @author rajeevj
 */
public class Keyword {
    private String token ;
    private String date ;
    private String createdOn;
    private String seoKey ;

    public Keyword() { }
    
    /**
     * 
     * @param token
     * @param date in format 2010-11-29 or YYYY-MM-DD
     * MySQL date format is also YYYY-MM-DD
     * 
     */
    
    public Keyword(String token, String date) {
        this.token = token ;
        this.date = date ;

    }
    public String getSeoKey() {
        return seoKey;
    }

    public void setSeoKey(String seoKey) {
        this.seoKey = seoKey;
    }

    
    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
