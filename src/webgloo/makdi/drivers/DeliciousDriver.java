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
import webgloo.makdi.data.VanillaList;
import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.io.URLReader;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 *
 */
public class DeliciousDriver implements IDriver {

    public final static String YAHOO_YQL_URI = "http://query.yahooapis.com/v1/public/yql?q=";
    public final static String DELICIOUS_YQL = "select * from delicious.feeds where tag=\"{token}\" ";
    public final static String ALL_TABLES_ENV = "&env=store://datatables.org/alltableswithkeys" ;
    

    private Transformer transformer;

    public DeliciousDriver() {
        this.transformer = new Transformer();
    }

    public DeliciousDriver(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public String getName() {
        return IDriver.DELICIOUS_DRIVER;
    }

    @Override
    public long getDelay() {
        return 3000;
    }

    @Override
    public List<IData> run(String tag) throws Exception {
        MyTrace.entry("DeliciousDriver", "run()");

        tag = this.transformer.transform(tag);
        String query = DeliciousDriver.DELICIOUS_YQL.replace("{token}", tag);
        query = java.net.URLEncoder.encode(query, "UTF-8");

        String address = YAHOO_YQL_URI + query + ALL_TABLES_ENV;

        //fetch response
        MyTrace.debug("sending request to :: " + address);
        String response = URLReader.read(address);
        List<IData> items = new ArrayList<IData>();

        items = parseResponse(response,tag);
        MyTrace.exit("DeliciousDriver", "run()");

        return items;

    }

    private List<IData> parseResponse(String response,String tag) throws Exception {
        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(is);
        XPathExpression expr = xpath.compile("//query/results/item");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        List<IData> items = new ArrayList<IData>();
        VanillaList list = new VanillaList("del.icio.us links for " + tag);

        //Go Over questions
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            String title = (String) xpath.evaluate("title", node, XPathConstants.STRING);
            String link = (String) xpath.evaluate("link", node, XPathConstants.STRING);
            list.add(HtmlGenerator.generateLinkHtml(title, link));
            

        }//loop: answers

        items.add(list);
        return items;
    }
    
    public static void main(String[] args) throws Exception {
        DeliciousDriver driver = new DeliciousDriver();
        String tag = "naga viper";
        List<IData> items = driver.run(tag);
        for (IData item : items) {
            System.out.println(item.toHtml());
        }

    }
}
