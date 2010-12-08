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
import webgloo.makdi.data.News;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.io.URLReader;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 *
 */
public class YahooBossNewsDriver extends YahooBossDriver implements IDriver {
    
    public final static String ENDPOINT_URI = "http://boss.yahooapis.com/ysearch/news/v1/";
    public final static String API_ARGUMENTS = "\"{token}\"{site}?appid={applicationId}&start={start}&count={count}&format=xml";

    public YahooBossNewsDriver(String[] siteNames, int startIndex, int maxResults) {
        super(siteNames, startIndex, maxResults);
    }

    public YahooBossNewsDriver(Transformer transformer, String[] siteNames, int startIndex, int maxResults) {
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
        MyTrace.entry("YahooBossNewsDriver", "run()");
        List<IData> items = super.run(
                YahooBossNewsDriver.ENDPOINT_URI,
                YahooBossNewsDriver.API_ARGUMENTS,
                tag);

        MyTrace.exit("YahooBossNewsDriver", "run()");
        return items;

    }

    public List<IData> getResults(String address) throws Exception {

        MyTrace.entry("YahooBossNewsDriver", "getResults()");
        List<IData> items = new ArrayList<IData>();

        MyTrace.debug("sending request to :: " + address);

        String response = URLReader.read(address);

        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(is);
        XPathExpression expr = xpath.compile("//ysearchresponse/resultset_news/result");

        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);

            String title = (String) xpath.evaluate("title", node, XPathConstants.STRING);
            String description = (String) xpath.evaluate("abstract", node, XPathConstants.STRING);
            String source = (String) xpath.evaluate("source", node, XPathConstants.STRING);

            News news = new News();
            news.setSource(source);
            news.setTitle(title);
            news.setDescription(description);

            items.add(news);
            
        }

        is.close();
        MyTrace.exit("YahooBossNewsDriver", "getResults()");
        return items;
    }

    public static void main(String[] args) throws Exception {

        YahooBossNewsDriver driver = new YahooBossNewsDriver(
                null,
                0,
                5);
        String tag = "austin rivers";
        List<IData> items = driver.run(tag);
        for (IData item : items) {

            System.out.println(item.toHtml());
        }

    }
}
