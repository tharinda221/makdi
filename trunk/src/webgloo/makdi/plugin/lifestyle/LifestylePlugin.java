package webgloo.makdi.plugin.lifestyle;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Photo;
import webgloo.makdi.db.GlooDBConnection;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.drivers.yahoo.YahooBossImageDriver;
import webgloo.makdi.html.HtmlGenerator;
import webgloo.makdi.plugin.IPlugin;
import webgloo.makdi.util.HtmlToText;

/**
 *
 * @author rajeevj
 */

public class LifestylePlugin {

    public void invoke(IPlugin profileBean) throws Exception {
        System.out.println("Hello from Lifestyle plugin....");
        //get a connection to DB
        // fetch list of pages
        // for every page
        java.sql.Connection connx = GlooDBConnection.getConnection();

        String orgId = "1181";

        String SQL =
                " select * from gloo_page  where org_id = " + orgId;

        java.sql.Statement stmt = connx.createStatement();
        java.sql.ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            curePage(connx, orgId, rs.getString("ident_key"), rs.getString("tags"));
            System.out.println(" Processed :: " + rs.getString("page_name"));
            //printUpdateStatement(orgId, rs.getString("seo_key"), rs.getString("tags"),rs.getString("category"));
            Thread.sleep(2000);
        }

        stmt.close();
        rs.close();
        connx.close();
    }

    /*
    private static void printUpdateStatement(String orgId, String seoKey,String tags, String category) {
        String updateSQL = " update gloo_page set tags= '" + tags + "' , category= '" + category+ "' " ;
        updateSQL += " where org_id = " + orgId + " and seo_key= '" + seoKey + "' ; \n ";
        System.out.println(updateSQL);
    }*/
    
    
    /*
    private static void checkImage(String tags, String seoKey) throws Exception {

        System.out.println(" checking page :: " + seoKey);
        IDriver imageDriver = new YahooBossImageDriver(null, 0, 1);
        List<IData> items = null;

        if (!StringUtils.isEmpty(tags)) {
            // find an image on page tags
            items = imageDriver.run(tags);

        }

        if (items != null && items.size() > 0) {
            //found a photo
            for (IData item : items) {
                Photo photo = (Photo) item;
                System.out.println("image is :: " + photo.getImageLink());
                break ;
            }
        } else {
            System.out.println(" no image");
        }
    } */

    private static void curePage(java.sql.Connection connx, String orgId, String pageIdentKey, String tags) throws Exception {

        String oldContent = null;
        String widgetId = null;

        String SQL =
                " select id,title,widget_html from gloo_block_data where org_id = "
                + orgId + " and page_key = '" + pageIdentKey + "' ";

        java.sql.Statement stmt = connx.createStatement();
        java.sql.ResultSet rs = stmt.executeQuery(SQL);

        //find current content
        // cure it and create summary

        while (rs.next()) {
            oldContent = rs.getString("widget_html");
            widgetId = rs.getString("id");

        }

        stmt.close();
        rs.close();

        List<String> paragraphs = cureHtmIntoParagraphs(oldContent);

        StringBuilder buffer = new StringBuilder();
        for (String paragraph : paragraphs) {
            buffer.append("\n <br> \n <p> ").append(paragraph).append(" </p> \n");

        }

        //new formatted content
        //System.out.println(buffer.toString());

        //page summary
        String summary = "";
        if (paragraphs.size() > 0) {
            summary = extractParagraphSummary(paragraphs.get(0));
        }
        
        IDriver imageDriver = new YahooBossImageDriver(null, 0, 1);
        List<IData> items = null;

        if (!StringUtils.isEmpty(tags)) {
            // find an image on page tags
            items = imageDriver.run(tags);
        }

        if (items != null && items.size() > 0) {
            //found a photo
            for (IData item : items) {
                Photo photo = (Photo) item;
                //we already have post title
                photo.setTitle("&nbsp;");
                //new content
                photo.setDescription(buffer.toString());
                //save this as embed code
                String widgetHtml = HtmlGenerator.generateLifeStyleCode(photo);
                //System.out.println(widgetHtml);
                updatePostHtml(connx, orgId, widgetId, widgetHtml);

                break;
            }

        } else {
            //did not find any photo
            // update content
            updatePostHtml(connx, orgId, widgetId, buffer.toString());

        }

        updatePageSummary(connx, orgId, pageIdentKey, summary);

    }

    private static void updatePostHtml(java.sql.Connection connx, String orgId, String widgetId, String content) throws Exception {
        String SQL =
                "update gloo_block_data set widget_html = ? where id = ? and org_id = ?";

        java.sql.PreparedStatement pstmt = connx.prepareStatement(SQL);
        pstmt.setString(1, content);
        pstmt.setString(2, widgetId);
        pstmt.setString(3, orgId);

        pstmt.executeUpdate();
        pstmt.close();


    }

    private static void updatePageSummary(java.sql.Connection connx, String orgId, String pageIdentKey, String summary) throws Exception {
        //update page summary
        String SQL =
                "update gloo_page set page_summary = ?, meta_tags = ? where ident_key = ? and org_id = ?";

        String metaTags = "<META NAME=\"description\" CONTENT=\" " + summary + " \" />";
        java.sql.PreparedStatement pstmt = connx.prepareStatement(SQL);
        pstmt.setString(1, summary);
        pstmt.setString(2, metaTags);
        pstmt.setString(3, pageIdentKey);
        pstmt.setString(4, orgId);

        pstmt.executeUpdate();
        pstmt.close();
    }

    private static String extractParagraphSummary(String content) throws Exception {
        StringBuilder summary = new StringBuilder();
        String[] words = StringUtils.split(content);
        int count = 0;
        for (String word : words) {
            summary.append(" ").append(word);
            count++;
            if (count > 40) {
                break;
            }
        }

        return summary.toString();

    }

    private static List<String> split(String content) throws Exception {
        List<String> paragraphs = new ArrayList<String>();

        String[] words = StringUtils.split(content);
        int count = 0;
        // one paragraph = 16 x 6 == 100 words
        StringBuilder buffer = new StringBuilder();

        boolean breakSignal = false;
        boolean breakNow = false;

        for (String word : words) {
            //System.out.println(word);
            //collect words into buffer
            buffer.append(" ").append(word);

            count++;
            if (count > 110) {
                breakSignal = true;
            }

            if (breakSignal && count > 135) {
                breakNow = true;
            }

            if (breakSignal && word.endsWith(".")) {
                breakNow = true;
            }

            if (breakNow) {
                //time to break into a paragraph
                paragraphs.add(buffer.toString());
                //reset counter and buffer
                count = 0;
                buffer = new StringBuilder();
                //reset flags
                breakSignal = false;
                breakNow = false;
            }
        }

        return paragraphs;


    }

    private static List<String> cureHtmIntoParagraphs(String oldContent) throws Exception {
        // break pararaphs on right words after right intervals
        //String newContent = HtmlToText.extractUsingGdataKit(oldContent);
        String newContent = HtmlToText.extractUsingSwingKit(oldContent);
        //split on paragraphs
        List<String> paragraphs = split(newContent);
        return paragraphs;

    }
}
