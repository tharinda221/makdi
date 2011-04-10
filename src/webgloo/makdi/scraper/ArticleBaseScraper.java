package webgloo.makdi.scraper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import webgloo.makdi.io.URLReader;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 */
public class ArticleBaseScraper {

    private StringBuilder buffer1;
    private StringBuilder title ;
    private StringBuilder content ;
    //private String summaryInText ;

    private boolean isContent;

  

    public ArticleBaseScraper() {
        this.buffer1 = new StringBuilder();
        this.title = null;
        this.content = null;
        this.isContent = false;
    
    }

    //@todo - summary and name Needs to be fixed!
    public String getName() {
        return this.title.toString();
    }

    public String getSummaryInText() {
        return this.title.toString();
    }

    /*
    public void setName(String name) {
        this.name = name;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    */
   

    


    public StringBuilder getContent() {
        return content;
    }

    public StringBuilder getTitle() {
        return title;
    }

    private String getEzineLink(String link) {
        //create syndicated content ezine link now
        String ezineCode = null ;
        String ezineLink = null ;

        if(link != null) {
            int pos1 = link.lastIndexOf('-');
            int pos2 = link.lastIndexOf('.');
            if(pos1 != -1 && pos2 != -1) {
                ezineCode = link.substring(pos1+1, pos2);
                ezineLink = "http://www.articlesbase.com/ezine/" + ezineCode ;
            }

        }

        return   ezineLink;

    }

    public void run(String address) throws Exception {
        address = this.getEzineLink(address);
        //get out if address is null
        // get out if address is not valid
        if(address == null || !URLReader.isValidURI(address)) {
            return ;
        }
        
        MyTrace.info(" scraping information from address :: " + address);
        
        //go to this address and fetch syndicated content
        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8");

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        //@todo parse tags w/o dependency on spaces
        //@todo we need SAX parsing on bad html
        String START_CONTENT = "<textarea id=\"ezine_html\"";
        String END_CONTENT = "</textarea>";
        

        while ((line = reader.readLine()) != null) {

            //look for syndicated DIV
            if (line.contains(START_CONTENT)) {
                handleStartContent(line);
            }

            if (line.contains(END_CONTENT)) {
                handleEndContent(line);
            }
            
            handleContent(line);

        }

        //strip unwanted tags from buffer 1
        int pos1 = this.buffer1.indexOf("<h1>");
        int pos2 = this.buffer1.indexOf("</h1>", pos1);
        //break at first </textarea..
        int pos3 = this.buffer1.indexOf("</textarea>");
        
        if ((pos1 != -1) && (pos2 != -1) && (pos3 != -1)) {
            this.title = new StringBuilder(this.buffer1.subSequence(pos1 + 4, pos2));
            this.content = new StringBuilder(this.buffer1.subSequence(pos1, pos3));
            
        }

               
    }

    private void handleStartContent(String line) {
        this.isContent = true;
        //@todo - the collection should start from <textarea ..
        this.buffer1.append(line);
        
    }

    private void handleEndContent(String line) {
       //@todo the collection should stop @ </textarea ...
       this.buffer1.append(line);
       this.isContent = false;
    }
    
    private void handleContent(String line) {

        if (this.isContent) {
            this.buffer1.append(line);
        }
        
    }
    
    public static void main(String[] args) throws Exception {
        String address = "http://www.articlesbase.com/nutrition-articles/liquid-protein-diet-the-pros-and-cons-3726418.html";
        ArticleBaseScraper scrapper = new ArticleBaseScraper();
        scrapper.run(address);
        //scrapper.getContent("http://www.articlesbase.com/marketing-articles/bulk-sms-api-2791138.html");
        System.out.println(scrapper.getContent());
        System.out.println("\n\n **** \n ");
        System.out.println(scrapper.getTitle());
    }
}
