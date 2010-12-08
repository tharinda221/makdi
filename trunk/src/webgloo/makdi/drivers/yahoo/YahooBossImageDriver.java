package webgloo.makdi.drivers.yahoo;

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
import webgloo.makdi.data.Photo;
import webgloo.makdi.data.Photo2;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.io.URLReader;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 *
 */
public class YahooBossImageDriver extends YahooBossDriver implements IDriver {

    public final static String ENDPOINT_URI = "http://boss.yahooapis.com/ysearch/images/v1/";
    public final static String API_ARGUMENTS = "\"{token}\"{site}?appid={applicationId}&start={start}&count={count}&dimensions=medium&format=xml";

    public YahooBossImageDriver(String[] siteNames, int startIndex, int maxResults) {
        super(siteNames, startIndex, maxResults);
    }

    public YahooBossImageDriver(Transformer transformer, String[] siteNames, int startIndex, int maxResults) {
        super(transformer, siteNames, startIndex, maxResults);
    }

    @Override
    public String getName() {
        return IDriver.YAHOO_BOSS_WEB_DRIVER;
    }

    @Override
    public long getDelay() {
        return super.getDelay();
    }
    
    @Override
    public List<IData> run(String tag) throws Exception {
        MyTrace.entry("YahooBossImageDriver", "run()");
        List<IData> items = super.run(
                YahooBossImageDriver.ENDPOINT_URI,
                YahooBossImageDriver.API_ARGUMENTS,
                tag);

        MyTrace.exit("YahooBossImageDriver", "run()");
        return items;

    }

    public List<IData> getResults(String address) throws Exception {

        MyTrace.entry("YahooBossImageDriver", "getResults()");
        List<IData> items = new ArrayList<IData>();

        MyTrace.debug("sending request to :: " + address);

        String response = URLReader.read(address);

        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(is);
        XPathExpression expr = xpath.compile("//ysearchresponse/resultset_images/result");

        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);

            String title = (String) xpath.evaluate("title", node, XPathConstants.STRING);
            String link = (String) xpath.evaluate("url", node, XPathConstants.STRING);
            String height = (String) xpath.evaluate("height", node, XPathConstants.STRING);
            String width = (String) xpath.evaluate("width", node, XPathConstants.STRING);
            String description = (String) xpath.evaluate("abstract", node, XPathConstants.STRING);

            //add only the valid links
            if (URLReader.isValidURI(link)) {
                Photo2 item = new Photo2();
                item.setDescription(description);
                item.setLink(link);
                item.setTitle(title);
                item.setImageLink(link);
                item.setAlignment(Photo.ALIGN_LEFT);
                
                //@todo box height should be supplied from outside
                int[] xy = MyUtils.getScaledDimensions(width, height, 300.0f);
                item.setWidth(xy[0]);
                item.setHeight(xy[1]);

                items.add(item);
            }

        }

        is.close();
        MyTrace.exit("YahooBossImageDriver", "getResults()");
        return items;
    }

    public static void main(String[] args) throws Exception {

        YahooBossImageDriver driver = new YahooBossImageDriver(
                new Transformer(),
                null,
                0,
                5);
        String tag = "Ginger jar lamps";
        List<IData> items = driver.run(tag);
        for (IData item : items) {

            System.out.println(item.toHtml());
        }

    }
}
