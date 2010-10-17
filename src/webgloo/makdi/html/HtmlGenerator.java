package webgloo.makdi.html;

import java.util.List;
import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.StringTemplateGroup;
import webgloo.makdi.data.Answer;
import webgloo.makdi.data.Book;
import webgloo.makdi.data.GoogleCse;
import webgloo.makdi.data.GoogleSearchControl;
import webgloo.makdi.data.VanillaList;
import webgloo.makdi.data.Link;
import webgloo.makdi.data.Movie;
import webgloo.makdi.data.News;
import webgloo.makdi.data.Photo;
import webgloo.makdi.data.Video;
import webgloo.makdi.data.Post;

/**
 *
 * @author rajeevj
 */
public class HtmlGenerator {

    private HtmlGenerator() {
    }

    public static String generatePageCode(
            String siteName,
            String menuHtml,
            String pageName,
            String pageContent) throws Exception {


        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/page");
        st.setAttribute("pageContent", pageContent);
        st.setAttribute("pageName", pageName);
        st.setAttribute("siteName", siteName);
        st.setAttribute("menuHtml", menuHtml);

        return st.toString();
    }

    public static String generateGoogleSearchControlCode(GoogleSearchControl control) {
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/googlesearchcontrol");
        st.setAttribute("keyword", control.getKeyword());
        return st.toString();
    }

    public static String generateGoogleCseCode(GoogleCse cse) {
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/googlecse");
        st.setAttribute("searchId", cse.getSearchId());
        return st.toString();
    }

    public static String generatePostCode(Post post) {
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/post");
        st.setAttribute("post", post);
        return st.toString();
    }

    public static String generateAnswerCode(Answer answer) {
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/answer");
        st.setAttribute("post", answer);
        return st.toString();
    }

    public static String generateVanillaListCode(VanillaList list) {
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



        return st.toString();
    }

    public static String generateYoutubeCode(Video video) {
        // Look for templates in CLASSPATH as resources
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/youtube");
        st.setAttribute("title", video.getTitle());
        st.setAttribute("videoId", video.getVideoId());
        st.setAttribute("description", video.getDescription());
        return st.toString();

    }

    public static String generateNewsCode(News news) {
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/news");
        st.setAttribute("title", news.getTitle());
        st.setAttribute("description", news.getDescription());
        st.setAttribute("link", news.getLink());
        return st.toString();


    }

    public static String generatePhotoCode(Photo photo) {
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/photo");
        st.setAttribute("photo", photo);
        return st.toString();

    }

    public static String generateMovieCode(Movie movie) {
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/movie");
        st.setAttribute("movie", movie);
        return st.toString();

    }

    public static String generateBookCode(Book book) {
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/book");
        st.setAttribute("book", book);
        return st.toString();

    }

    public static String generateVerticalMenuCode(List<Link> links) {
        StringTemplateGroup group = new StringTemplateGroup("mygroup");
        StringTemplate st = group.getInstanceOf("webgloo/makdi/data/templates/vmenu");
        st.setAttribute("items", links);
        return st.toString();

    }
}
