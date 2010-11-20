package webgloo.makdi.scraper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 * @author rajeevj
 */
public class ArticleBaseScraper {

    private StringBuilder buffer1;
    private StringBuilder buffer2;

    private StringBuilder title ;
    private StringBuilder content ;
    
    private boolean isSyndicate;
    private boolean isContainer;
    private boolean isContent;
    private boolean isKonaBody;
  

    public ArticleBaseScraper() {
        this.buffer1 = new StringBuilder();
        this.buffer2 = new StringBuilder();

        this.title = null;
        this.content = null;
        
        this.isSyndicate = false;
        this.isContainer = false;
        this.isContent = false;
        this.isKonaBody = false;
    }

    public StringBuilder getContent() {
        return content;
    }

    public StringBuilder getTitle() {
        return title;
    }
    
    public void run(String address) throws Exception {
        //go to this address and fetch syndicated content
        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8");

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        //@todo parse tags w/o dependency on spaces
        //@todo we need SAX parsing on bad html
        String SYNDICATE_DIV = "<div id=\"syndicate\"";
        String END_DIV = "</div>";
        String CONTAINER_DIV = "<div class=\"container\">";
        String KONA_BODY = "<div class=\"KonaBody\">";

        while ((line = reader.readLine()) != null) {

            //look for syndicated DIV
            if (line.contains(SYNDICATE_DIV)) {
                handleSyndicateDivTag();
            }

            if (line.contains(CONTAINER_DIV)) {
                handleContainerDivTag();
            }

            if (line.contains(END_DIV)) {
                handleEndDivTag(line);
            }

            if (line.contains(KONA_BODY)) {
                handleKonaBodyDivTag();
            }

            handleContent(line);

        }

        //strip unwanted tags from buffer 1
        int pos1 = this.buffer1.indexOf("<h1>");
        int pos2 = this.buffer1.indexOf("</h1>", pos1);
        int pos3 = this.buffer1.lastIndexOf("</textarea>");
        
        if ((pos1 != -1) && (pos2 != -1) && (pos3 != -1)) {
            this.title = new StringBuilder(this.buffer1.subSequence(pos1 + 4, pos2));
            this.content = new StringBuilder(this.buffer1.subSequence(pos1, pos3));
            //replace <!--articlecontent--> with buffer2
            String finalContent = this.content.toString().replace("<!--articlecontent-->", this.buffer2.toString());
            this.content = new StringBuilder(finalContent);

        }

               
    }

    private void handleSyndicateDivTag() {
        //System.out.println("handleSyndicateDivTag");
        this.isSyndicate = true;
    }

    private void handleKonaBodyDivTag() {
        this.isKonaBody = true;
    }

    private void handleContainerDivTag() {
        //System.out.println("handleContainerDivTag");
        if (this.isSyndicate) {
            this.isContainer = true;
            this.isContent = true;
        }

    }

    private void handleEndDivTag(String line) {
        //System.out.println("handleEndDivTag");
        if (this.isContainer) {
            this.isContainer = false;
            this.isContent = false;
            this.isSyndicate = false;

            this.buffer1.append(line);
        }

        if (this.isKonaBody) {
            this.isKonaBody = false;
            this.buffer2.append(line);
        }

    }

    private void handleContent(String line) {

        if (this.isContainer && this.isContent) {
            this.buffer1.append(line);
        }

        if (this.isKonaBody) {
            this.buffer2.append(line);
        }

    }

    public static void main(String[] args) throws Exception {
        String address = "http://www.articlesbase.com/ecommerce-articles/how-to-choose-a-quality-animal-print-rug-26786.html";
        ArticleBaseScraper scrapper = new ArticleBaseScraper();
        scrapper.run(address);
        //scrapper.getContent("http://www.articlesbase.com/marketing-articles/bulk-sms-api-2791138.html");
        System.out.println(scrapper.getContent());
        System.out.println("\n\n **** \n ");
        System.out.println(scrapper.getTitle());
    }
}
