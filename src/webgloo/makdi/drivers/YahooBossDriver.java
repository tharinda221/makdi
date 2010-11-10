package webgloo.makdi.drivers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Post;
import webgloo.makdi.io.URLReader;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 *
 */
public class YahooBossDriver implements IDriver {

    public final static String BOSS_APPLICATION_ID = "dDGV1CbV34FpBSGBFMZXtX4x1I5GYdcJskAxZzjND3O3ZeYN4j4CPzfSROgc";
    public final static String BOSS_WEB_URI = "http://boss.yahooapis.com/ysearch/web/v1/";
    public final static String BOSS_ARGUMENT = "\"{token}\"{site}?appid={applicationId}&count={count}&format=xml";
    //BOSS is no limits API right now
    // but again lets play it safe!

    public final static int MAX_RESULTS = 10;
    private int maxResults;
    private String[] siteNames = null;
    private Transformer transformer ;
    
    public YahooBossDriver(Transformer transformer ,String[] siteNames) {
        this.transformer = transformer;
        this.maxResults = MAX_RESULTS;
        this.siteNames = siteNames;
    }
        
    public YahooBossDriver(Transformer transformer,String[] siteNames, int maxResults) {
        this.transformer = transformer ;
        this.maxResults = maxResults;
        this.siteNames = siteNames;
    }

    @Override
    public String getName() {
        return IDriver.YAHOO_BOSS_DRIVER;
    }
    
    @Override
    public long getDelay() {
        return 3000 ;
    }
    
    @Override
    public List<IData> run(String tag) throws Exception {
        MyTrace.entry("YahooBossDriver", "run()");

        if(this.siteNames == null ) {
            throw new Exception("No site names have been supplied to Yahoo BOSS");
        }
        
        tag = this.transformer.transform(tag);
        tag = java.net.URLEncoder.encode(tag, "UTF-8");
        List<IData> items = new ArrayList<IData>();

        for(String siteName : siteNames){
            List<IData> posts = this.getPosts(siteName,tag);
            for (IData post : posts) {
                items.add(post);
            }
        }

        MyTrace.exit("YahooBossDriver", "run()");
        return items;

    }

    public List<IData> getPosts(String siteName,String tag) throws Exception {

        MyTrace.entry("YahooBossDriver", "run()");
        List<IData> items = new ArrayList<IData>();

        //create address
        String address = createAddress(siteName,tag);
        MyTrace.debug("sending request to :: " + address);
        
        String response = URLReader.read(address);
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        //domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        //Document doc = builder.parse("sample.xml");
        Document doc = builder.parse(is);
        XPathExpression expr = xpath.compile("//ysearchresponse/resultset_web/result");
        
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);

            String title = (String) xpath.evaluate("title", node, XPathConstants.STRING);
            String link = (String) xpath.evaluate("url", node, XPathConstants.STRING);
            String description = (String) xpath.evaluate("abstract", node, XPathConstants.STRING);
            String size = (String) xpath.evaluate("size", node, XPathConstants.STRING);

            //create SearchResult
            Post item = new Post();
            item.setDescription(description);
            item.setLink(link);
            item.setSize(size);
            item.setTitle(title);

            items.add(item);

        }

        is.close();
        MyTrace.exit("YahooBossDriver", "run()");
        return items;
    }

    private String createAddress(String siteName, String tag) {

        //substitute token
        String args = YahooBossDriver.BOSS_ARGUMENT.replace("{token}", tag);
        //substitute application id
        args = args.replace("{applicationId}", YahooBossDriver.BOSS_APPLICATION_ID);
        //substitute site and count
        String site =  "+site:" + siteName ;
        int count = (this.maxResults <= 0) ? MAX_RESULTS : this.maxResults;
        args = args.replace("{site}", site);
        args = args.replace("{count}", "" + count);

        String address = YahooBossDriver.BOSS_WEB_URI + args;
        return address;

    }
}
