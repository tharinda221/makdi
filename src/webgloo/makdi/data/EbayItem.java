package webgloo.makdi.data;

/**
 *
 * @author rajeevj
 */
public class EbayItem implements IData {

    private String title;
    private String link;
    private String imageLink;
    private String itemId;
    private String postalCode;
    private String location;
    private String country;
    private String currentPrice;

    public EbayItem(String itemId) {
        this.itemId = itemId;

    }

    public String getItemId() {
        return this.itemId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("title => " + this.title + "\n");
        buffer.append("link => " + this.link + "\n");
        buffer.append("image link => " + this.imageLink + "\n");
        buffer.append("current price => " + this.currentPrice + "\n");
        return buffer.toString();

    }

    @Override
    public String toHtml() throws Exception {
        
        //use a photo IData object to create html
        Photo photo = new Photo();
        photo.setImageLink(this.imageLink);
        photo.setTitle(this.title);
        photo.setLink(this.link);
        String description = "&nbsp;price&nbsp;" + this.currentPrice + "&nbsp;country&nbsp;" + country;
        description += "&nbsp;location&nbsp;" + this.location;
        photo.setDescription(description);
        return photo.toHtml();

    }
}
