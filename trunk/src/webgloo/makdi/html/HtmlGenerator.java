package webgloo.makdi.html;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import webgloo.makdi.data.AmazonWidget;
import webgloo.makdi.data.Answer;
import webgloo.makdi.data.Book;
import webgloo.makdi.data.GoogleCse;
import webgloo.makdi.data.GoogleSearchControl;
import webgloo.makdi.data.VanillaList;
import webgloo.makdi.data.Movie;
import webgloo.makdi.data.News;
import webgloo.makdi.data.Photo;
import webgloo.makdi.data.Video;
import webgloo.makdi.data.Post;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 */
public class HtmlGenerator {

    private HtmlGenerator() {
    }

    public static String generatePageCode(
            String siteName,
            String pageName,
            String pageContent) throws Exception {

        MyTrace.entry("HtmlGenerator", "generatePageCode()");

        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/page");
        st.setAttribute("pageContent", pageContent);
        st.setAttribute("pageName", pageName);
        st.setAttribute("siteName", siteName);
        
        MyTrace.exit("HtmlGenerator", "generatePageCode()");
        return st.toString();
    }

    public static String generateGoogleSearchControlCode(GoogleSearchControl control) {

        MyTrace.entry("HtmlGenerator", "generateGoogleSearchControlCode()");
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/googlesearchcontrol");
        st.setAttribute("keyword", control.getKeyword());

        MyTrace.exit("HtmlGenerator", "generateGoogleSearchControlCode()");
        
        return st.toString();
    }

    public static String generateGoogleCseCode(GoogleCse cse) {
        MyTrace.entry("HtmlGenerator", "generateGoogleCseCode()");
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/googlecse");
        st.setAttribute("searchId", cse.getSearchId());
        MyTrace.exit("HtmlGenerator", "generateGoogleCseCode()");

        return st.toString();
    }

    public static String generateAmazonWidgetCode(AmazonWidget data) {
        MyTrace.entry("HtmlGenerator", "generateAmazonWidgetCode()");
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/amazon-widget");
        st.setAttribute("amazonId", data.getAmazonId());
        MyTrace.exit("HtmlGenerator", "generateAmazonWidgetCode()");

        return st.toString();
    }

    public static String generatePostCode(Post post) {
        MyTrace.entry("HtmlGenerator", "generatePostCode()");
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/post");
        st.setAttribute("post", post);
        MyTrace.exit("HtmlGenerator", "generatePostCode()");

        return st.toString();
    }

    public static String generateAnswerCode(Answer answer) {
        MyTrace.entry("HtmlGenerator", "generateAnswerCode()");
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/answer");
        st.setAttribute("post", answer);
        
        MyTrace.exit("HtmlGenerator", "generateAnswerCode()");

        return st.toString();
    }

    public static String generateVanillaListCode(VanillaList list) {
        MyTrace.entry("HtmlGenerator", "generateVanillaListCode()");
        //no html for empty list
        if (list.getItems().size() <= 0) {
            return "";
        }
        
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/list");

        //since we are iterating over items in the template
        //we need to set items attribute to some Java collection with an iterator
        // so we get List<String> by calling getItems() of  makdi data structure.
        st.setAttribute("items", list.getItems());
        st.setAttribute("title", list.getTitle());

        MyTrace.exit("HtmlGenerator", "generateVanillaListCode()");
        return st.toString();
    }

    public static String generateYoutubeCode(Video video) {
        MyTrace.entry("HtmlGenerator", "generateYoutubeCode()");
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/youtube");
        st.setAttribute("title", video.getTitle());
        st.setAttribute("videoId", video.getVideoId());
        st.setAttribute("description", video.getDescription());
        //set video alignment
        //alignleft, alignright or aligncenter
        st.setAttribute("alignment",video.getAlignment());
        MyTrace.exit("HtmlGenerator", "generateYoutubeCode()");
        return st.toString();

    }

    public static String generateArcadeVideoCode(Video video) {
         MyTrace.entry("HtmlGenerator", "generateArcadeVideoCode()");
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/youtube-post");
        st.setAttribute("title", video.getTitle());
        st.setAttribute("videoId", video.getVideoId());
        st.setAttribute("description", video.getDescription());
        //set video alignment
        //alignleft, alignright or aligncenter
        st.setAttribute("alignment",video.getAlignment());
        MyTrace.exit("HtmlGenerator", "generateArcadeVideoCode()");
        return st.toString();
    }

    public static String generateNewsCode(News news) {
        MyTrace.entry("HtmlGenerator", "generateNewsCode()");
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/news");
        st.setAttribute("title", news.getTitle());
        st.setAttribute("description", news.getDescription());
        st.setAttribute("link", news.getLink());
        MyTrace.exit("HtmlGenerator", "generateNewsCode()");
        return st.toString();

    }

    public static String generateAutoPostNews(News news) {
        MyTrace.entry("HtmlGenerator", "generateAutoPostNews()");
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/autopost-news");
        st.setAttribute("title", news.getTitle());
        st.setAttribute("description", news.getDescription());
        MyTrace.exit("HtmlGenerator", "generateAutoPostNews()");

        return st.toString();

    }

    public static String generatePhotoCode(Photo photo) {

        MyTrace.entry("HtmlGenerator", "generatePhotoCode()");
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/photo");
        st.setAttribute("photo", photo);

        MyTrace.exit("HtmlGenerator", "generatePhotoCode()");

        return st.toString();

    }

    public static String generateMovieCode(Movie movie) {
        MyTrace.entry("HtmlGenerator", "generateMovieCode()");
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/movie");
        st.setAttribute("movie", movie);
        MyTrace.exit("HtmlGenerator", "generateMovieCode()");

        return st.toString();

    }

    public static String generateBookCode(Book book) {
        MyTrace.entry("HtmlGenerator", "generateBookCode()");
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/book");
        st.setAttribute("book", book);
        MyTrace.exit("HtmlGenerator", "generateBookCode()");

        return st.toString();

    }

    
}
