package webgloo.makdi.processor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.io.URLReader;

/**
 *
 * @author rajeevj
 *
 */
public class GoogleHotTrendKeywords {

    public static List<Keyword> loadNewKeywords() throws Exception {
        //hard code for now
        String address = "http://www.google.com/trends/hottrends/atom/hourly";
        String response = URLReader.read(address);

        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(is);
        XPathExpression expr = xpath.compile("//feed/entry");

        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
        String content = null ;

        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            content = (String) xpath.evaluate("content", node, XPathConstants.STRING);
            
        }
        //@see 
        Pattern p = Pattern.compile("<a href=\"(.*?)\">");
        Matcher m = p.matcher(content);

        List<Keyword> keywords = new ArrayList<Keyword>();
        
        while(m.find()) {
            String link = m.group(1) ;
            int pos1 = link.indexOf("?q=");
            link = link.substring(pos1+3);
            int pos2 = link.indexOf("&date=");
            Keyword keyword = new Keyword();

            keyword.setToken(link.substring(0, pos2));
            keyword.setDate(link.substring(pos2+6, pos2+16));
            keywords.add(keyword);
        }

        return keywords ;

    }
}
