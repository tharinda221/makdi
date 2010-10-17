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
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import webgloo.makdi.data.Answer;
import webgloo.makdi.data.IData;
import webgloo.makdi.util.MyWriter;
import webgloo.makdi.io.URLReader;

/**
 *
 * @author rajeevj
 *
 */
public class YahooAnswerDriver implements IDriver {

    public final static int MAX_RESULTS = 10;
    public final static String YAHOO_YQL_URI = "http://query.yahooapis.com/v1/public/yql?q=";
    public final static String YAHOO_ANSWER_YQL = "select * from answers.search(0,{endIndex}) where query=\"{token}\" and search_in=\"question\" ";
    public final static int REQUEST_DELAY = 3000;

    private int maxResults;
    private Transformer transformer ;

    public YahooAnswerDriver(Transformer transformer) {
        this.maxResults = MAX_RESULTS;
        this.transformer = transformer ;
    }

    public YahooAnswerDriver(Transformer transformer, int maxResults) {
        this.maxResults = maxResults;
        this.transformer = transformer ;
    }

    @Override
    public String getName() {
        return IDriver.YAHOO_ANSWER_DRIVER;
    }

    @Override
    public List<IData> run(String tag) throws Exception {
        tag = this.transformer.transform(tag);
        
        String query = YAHOO_ANSWER_YQL.replace("{token}", tag);
        query = query.replace("{endIndex}", "" + this.maxResults);
        //url encode the full query
        query = java.net.URLEncoder.encode(query, "UTF-8");
        
        String address = YAHOO_YQL_URI + query;

        //fetch response
        MyWriter.toConsole("sending request to :: " + address);
        String response = URLReader.read(address);
        //MyWriter.toConsole("response :: " + response);
        
        List<IData> items = new ArrayList<IData>();
        items = parseResponse(response);
        //wait between results
        Thread.sleep(REQUEST_DELAY);
        return items;

    }

    private List<IData> parseResponse(String response) throws Exception{
        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(is);
        XPathExpression expr = xpath.compile("//query/results/Question");
        NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

        List<IData> items = new ArrayList<IData>();
        
        //Go Over questions
        for (int i = 0; i < nodes.getLength() ; i++) {
            Node node = nodes.item(i);
            String numAnswers = (String) xpath.evaluate("NumAnswers", node, XPathConstants.STRING);
            if(Integer.parseInt(numAnswers) <= 0 ) {
                //ignore questions w/o answers
                continue ;
            } else {
                Answer item = new Answer();
                String title = (String) xpath.evaluate("Subject", node, XPathConstants.STRING);
                String link = (String) xpath.evaluate("Link", node, XPathConstants.STRING);
                String question = (String) xpath.evaluate("Content", node, XPathConstants.STRING);
                String answer = (String) xpath.evaluate("ChosenAnswer", node, XPathConstants.STRING);
                //prune answer now
                answer = StringUtils.abbreviate(answer, 300);
                item.setTitle(title);
                item.setLink(link);
                item.setQuestion(question);
                item.setAnswer(answer);

                items.add(item);
                
            }

        }//loop: answers

        return items;
    }

    public static void main(String[] args) throws Exception {
        YahooAnswerDriver driver = new YahooAnswerDriver(new Transformer(null, " + movie + book"),4);
        String tag = "a clockwork orange ";
        List<IData> items = driver.run(tag);
        for(IData item : items) {
            System.out.println(item);
        }

    }
}
