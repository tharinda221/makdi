package webgloo.makdi.drivers;

import com.google.gdata.util.common.base.StringUtil;
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
import webgloo.makdi.data.Movie;
import webgloo.makdi.util.MyWriter;
import webgloo.makdi.io.URLReader;

/**
 *
 * @author rajeevj
 * driver for The moviedatabase.org (api.themoviedb.org)
 *
 */
public class TmdbDriver implements IDriver {

    public static final String TMDB_SEARCH_URI = "http://api.themoviedb.org/2.1/Movie.search/en/xml/{APIKEY}/{TOKEN}";
    public static final String TMDB_API_KEY = "e74d3bcf14aa8637d0c489701efa4ab0";
    public final static int REQUEST_DELAY = 3000;
    private Transformer transformer;

    public TmdbDriver(Transformer transformer) {
        this.transformer = transformer;
    }

    @Override
    public String getName() {
        return IDriver.TMDB_DRIVER;
    }

    @Override
    public List<IData> run(String tag) throws Exception {

        tag = this.transformer.transform(tag);
        //Urlencode the tag
        tag = java.net.URLEncoder.encode(tag, "UTF-8");
        
        List<IData> items = new ArrayList<IData>();
        String address = TMDB_SEARCH_URI.replace("{TOKEN}", tag);
        address = address.replace("{APIKEY}", TMDB_API_KEY);
        String response = URLReader.read(address);
        MyWriter.toConsole(" sending request to :: " + address);
        Movie movie = tryGetMovie(response);

        if (movie != null) {
            items.add(movie);
            //MyWriter.toConsole(movie.toString());
        }

        //wait between results
        Thread.sleep(REQUEST_DELAY);
        return items;


    }

    private Movie tryGetMovie(String response) throws Exception {

        Movie movie = null;

        XPath xpath = XPathFactory.newInstance().newXPath();
        InputStream is = new ByteArrayInputStream(response.getBytes("UTF-8"));
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        //domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        //Document doc = builder.parse("sample.xml");
        Document doc = builder.parse(is);

        XPathExpression totalExpression = xpath.compile("//OpenSearchDescription/totalResults");
        XPathExpression movieExpression = xpath.compile("//OpenSearchDescription/movies/movie");

        String totalString = (String) totalExpression.evaluate(doc, XPathConstants.STRING);
        int total = 0;

        if (totalString != null) {
            total = Integer.parseInt(totalString);
        }

        if (total > 0) {
            //parse movieId
            NodeList nodes = (NodeList) movieExpression.evaluate(doc, XPathConstants.NODESET);
            if (nodes.getLength() > 0) {
                //only interested in first movie
                Node node = nodes.item(0);
                String overView = (String) xpath.evaluate("overview", node, XPathConstants.STRING);
                String rating = (String) xpath.evaluate("rating", node, XPathConstants.STRING);
                String tmdbId = (String) xpath.evaluate("id", node, XPathConstants.STRING);
                String imdbId = (String) xpath.evaluate("imdb_id", node, XPathConstants.STRING);
                String name = (String) xpath.evaluate("name", node, XPathConstants.STRING);

                movie = new Movie(tmdbId, imdbId);
                movie.setDescription(overView);
                movie.setRating(rating);
                movie.setTitle(name);

                //find poster image
                // we do not expect more than one mode with size mid
                Node imageNode = (Node) xpath.evaluate("images/image[@size='mid']", node, XPathConstants.NODE);
                if (imageNode != null) {
                    String imageUrl = (String) xpath.evaluate("@url", imageNode, XPathConstants.STRING);
                    String imageWidth = (String) xpath.evaluate("@width", imageNode,XPathConstants.STRING);
                    String imageHeight = (String) xpath.evaluate("@height", imageNode,XPathConstants.STRING);
                    movie.setImageLink(imageUrl);

                    int[] xy = getScaledDimensions(imageWidth,imageHeight,400.0f);
                    movie.setImageWidth(xy[0]);
                    movie.setImageHeight( xy[1]);
                   
                }


            }
        }

        return movie;
    }

    /**
     *
     * @param width
     * @param height
     * @param boxHeight (maximum height of target box)
     * @return
     * @throws Exception
     */
    private int[] getScaledDimensions(String width, String height, float boxHeight) throws Exception {

        if (StringUtil.isEmpty(width) || StringUtil.isEmpty(height)) {
            throw new Exception("Bad image width and height supplied for scaling");
        }

        double originalx = Integer.parseInt(width) * 1.0f;
        double originaly = Integer.parseInt(height) * 1.0f;

        double boxWidth = (originalx / originaly) * boxHeight;

        //first try original dimensions
        double newHeight = originaly;
        double newWidth = originalx;

        if (newHeight > boxHeight) {
            double ratio = (boxHeight / newHeight);
            newHeight = ratio * newHeight;
            newWidth = ratio * newWidth;
        }

        if (newWidth > boxWidth) {
            double ratio = boxWidth / newWidth;
            newHeight = ratio * newHeight;
            newWidth = ratio * newWidth;
        }

        //Round up to nearest integer
        newWidth = Math.floor(newWidth);
        newHeight = Math.floor(newHeight);


        int[] xy = new int[2];
        xy[0] = (int) newWidth;
        xy[1] = (int) newHeight;
        return xy;

    }

    public static void main(String[] args) throws Exception {
        IDriver driver = new TmdbDriver(new Transformer());
        driver.run("the godfather");
        //TmdbDriver driver = new TmdbDriver(new BaseTransformer());
        //int[] xy = driver.getScaledDimensions("500", "750", 400);
        //System.out.println(" x=>" + xy[0] + " y=>" + xy[1]);
    }
}
