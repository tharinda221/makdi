
package webgloo.makdi.test;

import java.io.FileInputStream;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author rajeevj
 */
public class ProfileParser {

    public static void parseProfile() throws Exception {
        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new FileInputStream("profile.xml");
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();


        Document doc = builder.parse(is);
        XPathExpression expr = xpath.compile("//MakdiProfile/drivers/driver");

        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < nodes.getLength(); i++) {

            Node node = nodes.item(i);
            String prefix = (String) xpath.evaluate("transformer/prefix", node, XPathConstants.STRING);
            String suffix = (String) xpath.evaluate("transformer/suffix", node, XPathConstants.STRING);
            System.out.println("prefix=" + prefix + "suffix=" + suffix);
            NodeList attributes = (NodeList) xpath.evaluate("attribute", node, XPathConstants.NODESET);

            for (int j = 0; j < attributes.getLength(); j++) {
                Node attribute = attributes.item(j);
                String name = (String) xpath.evaluate("@name", attribute, XPathConstants.STRING);
                String value = (String) xpath.evaluate("@value", attribute, XPathConstants.STRING);
                System.out.println("name=" + name + "value=" + value);
            }
        }

    }

}
