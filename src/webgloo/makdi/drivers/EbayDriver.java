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
import webgloo.makdi.data.EbayItem;
import webgloo.makdi.data.IData;

import webgloo.makdi.io.URLReader;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeev jha (jha.rajeev@gmail.com)
 *
 */
public class EbayDriver implements IDriver{

    public final static String EBAY_APP_ID = "RajeevJh-326b-4885-9056-e82c0cfb0e82";
    public final static String EBAY_FINDING_SERVICE_URI = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME="
            + "{operation}&SERVICE-VERSION={version}&SECURITY-APPNAME="
            + "{applicationId}&GLOBAL-ID={globalId}&keywords={keywords}"
            + "&paginationInput.entriesPerPage={maxresults}";
    public static final String SERVICE_VERSION = "1.0.0";
    public static final String OPERATION_NAME = "findItemsByKeywords";
    public static final String GLOBAL_ID = "EBAY-US";
    
    public final static int MAX_RESULTS = 10;

    private int maxResults;
    private Transformer transformer;

    public EbayDriver(Transformer transformer) {
        this.maxResults = MAX_RESULTS;
        this.transformer = transformer ;

    }

    public EbayDriver(Transformer transformer,int maxResults) {
        this.maxResults = maxResults;
        this.transformer = transformer;
    }

    @Override
    public long getDelay() {
        return 3000 ;
    }

    @Override
    public String getName() {
        return IDriver.EBAY_DRIVER;
    }

    @Override
    public List<IData> run(String tag)  throws Exception {
        MyTrace.entry("EbayDriver", "run()");

        tag = this.transformer.transform(tag);
        tag = java.net.URLEncoder.encode(tag, "UTF-8");

        String address = createAddress(tag);
        MyTrace.info("sending request to :: " + address);

        String response = URLReader.read(address);
        //MyWriter.toConsole("response :: " + response);
        //process xml dump returned from EBAY
        List<IData> items = processResponse(response);
        
        MyTrace.exit("EbayDriver", "run()");
        
        return items;

    }

    private String createAddress(String tag) {

        
        //substitute token
        String address = EbayDriver.EBAY_FINDING_SERVICE_URI;
        address = address.replace("{version}", EbayDriver.SERVICE_VERSION);
        address = address.replace("{operation}", EbayDriver.OPERATION_NAME);
        address = address.replace("{globalId}", EbayDriver.GLOBAL_ID);
        address = address.replace("{applicationId}", EbayDriver.EBAY_APP_ID);
        address = address.replace("{keywords}", tag);
        address = address.replace("{maxresults}", "" + this.maxResults);

        return address;

    }

    private List<IData> processResponse(String response) throws Exception {

        List<IData> items = new ArrayList<IData>();

        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();


        Document doc = builder.parse(is);
        XPathExpression ackExpression = xpath.compile("//findItemsByKeywordsResponse/ack");
        XPathExpression itemExpression = xpath.compile("//findItemsByKeywordsResponse/searchResult/item");

        String ackToken = (String) ackExpression.evaluate(doc, XPathConstants.STRING);
        MyTrace.info("ACK from ebay API :: " + ackToken);

        if (!ackToken.equals("Success")) {
            throw new Exception(" service returned an error");
        }

        NodeList nodes = (NodeList) itemExpression.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            String itemId = (String) xpath.evaluate("itemId", node, XPathConstants.STRING);
            String title = (String) xpath.evaluate("title", node, XPathConstants.STRING);
            String itemUrl = (String) xpath.evaluate("viewItemURL", node, XPathConstants.STRING);
            String galleryUrl = (String) xpath.evaluate("galleryURL", node, XPathConstants.STRING);
            String currentPrice = (String) xpath.evaluate("sellingStatus/currentPrice", node, XPathConstants.STRING);

            String country = (String) xpath.evaluate("country", node, XPathConstants.STRING);
            String location = (String) xpath.evaluate("location", node, XPathConstants.STRING);
            String postalCode = (String) xpath.evaluate("postalCode", node, XPathConstants.STRING);
            
            EbayItem item = new EbayItem(itemId);

            
            item.setTitle(title);
            item.setLink(itemUrl);
            item.setImageLink(galleryUrl);
            
            item.setCountry(country);
            item.setLocation(location);
            item.setPostalCode(postalCode);
            item.setCurrentPrice(currentPrice);
            items.add(item);
        }

        is.close();

        return items ;
    }

   
    public static void main(String[] args) throws Exception {
        EbayDriver driver = new EbayDriver(new Transformer(),2);
        String tag = "ipod";
        driver.run(tag);

    }
}
