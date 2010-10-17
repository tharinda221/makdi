
package webgloo.makdi.data;

import com.google.gdata.util.common.base.StringUtil;
import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.html.HtmlGenerator;

/**
 *
 * @author rajeevj
 */
public class Book implements IData{
    
    private String title ;
    private List<String> authors ;
    private String link ;
    private String imageLink ;
    private List<String> isbns ;
    private String previewLink;
    private String description;
    private boolean isEmbeddable ;
    private String bookId ;


    public Book() {
        this.authors = new ArrayList<String>();
        this.isbns = new ArrayList<String>();
        this.isEmbeddable = false ;
        //this.bookId = bookId ;
        
    }

    //bookId is not known apriori
    //we actually parse the identifiers to get bookId
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }


    public String getBookId() {
        return bookId;
    }
    
    public boolean isIsEmbeddable() {
        return isEmbeddable;
    }

    public void setIsEmbeddable(boolean isEmbeddable) {
        this.isEmbeddable = isEmbeddable;
    }
    
    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<String> getIsbns() {
        return isbns;
    }

    public void setIsbns(List<String> isbns) {
        this.isbns = isbns;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addIsbn(String isbn){
        this.isbns.add(isbn);
    }

    public void addAuthor(String author){
        this.authors.add(author);
    }

    
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("title = " + this.title + "\n");
        buffer.append("link = " + this.link + "\n");
        for(String author : authors){
            buffer.append("author=" + author + "\n");
        }
        
        for(String isbn : isbns){
            buffer.append("isbn=" + isbn + "\n");
        }
        
        return buffer.toString();

    }

    @Override
    public String toHtml() throws Exception {
         //check imageLink
         if(StringUtil.isEmpty(this.imageLink)) {
             this.setImageLink(IData.IMAGE_404_URI);
         }
         
         return HtmlGenerator.generateBookCode(this);
    }

 }
