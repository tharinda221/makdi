package webgloo.makdi.db;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.Keyword;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 *
 */
public class AutoPostManager {

    public static void storeKeywords(
            java.sql.Connection connection,
            String orgId,
            List<Keyword> keywords) throws Exception {

        //store keyword if not there already
        //mySQL timestamps are in form 2010-10-22 21:14:45
        for (Keyword keyword : keywords) {
            String createdOn = keyword.getDate() + " " + MyUtils.now();
            addGlooAutoPostKeyword(connection, orgId, keyword.getToken(), createdOn);

        }

    }

    private static void addGlooAutoPostKeyword(java.sql.Connection connection,
            String orgId,
            String token,
            String createdOn) throws Exception {

        //with ignore we do not insert the duplicate tokens
        String INSERT_SQL =
                " insert ignore into gloo_auto_keyword(org_id,token,seo_key,created_on) "
                + " values (?,?,?,?) ";

        java.sql.PreparedStatement pstmt = connection.prepareStatement(INSERT_SQL);
        pstmt.setString(1, orgId);
        pstmt.setString(2, token);
        pstmt.setString(3, MyUtils.convertPageNameToId(token));
        pstmt.setString(4, createdOn);

        pstmt.executeUpdate();
        pstmt.close();


    }

    public static List<Keyword> loadKeywords(
            java.sql.Connection connection,
            String orgId) throws Exception {

        //store keyword is not there already
        List<Keyword> keywords = new ArrayList<Keyword>();

        String SQL =
                " select token,seo_key,created_on from gloo_auto_keyword where org_id = "
                + orgId + " and is_processed = 0  order by created_on DESC";

        java.sql.Statement stmt = connection.createStatement();
        java.sql.ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()) {
            Keyword keyword = new Keyword();
            keyword.setToken(rs.getString("token"));
            keyword.setCreatedOn(rs.getString("created_on"));
            keyword.setSeoKey(rs.getString("seo_key"));

            keywords.add(keyword);

        }

        stmt.close();
        rs.close();

        return keywords;

    }

    public static void storePostContent(java.sql.Connection connection,
            String orgId,
            Keyword keyword,
            String title,
            String summary,
            String content) throws Exception {

        //1. create a new page called gloo_page
        String pageIdentKey = MyUtils.getUUID();
        String pageName = keyword.getToken();
        //mySQL timestamps are in form 2010-10-22 21:14:45
        String createdOn = keyword.getCreatedOn();
        
        createGlooPage(connection, orgId, pageIdentKey, pageName);
        //2. store content in newly created page
        addGlooPageContent(connection, orgId, pageIdentKey, title, content);
        //3. store summary in gloo_auto_post table
        // get right date string

        addGlooAutoPost(connection, orgId, pageName, title, summary, createdOn);
               

    }

    private static void addGlooAutoPost(java.sql.Connection connection,
            String orgId,
            String pageName,
            String title,
            String summary,
            String createdOn) throws Exception {

        
        String GLOO_AUTO_POST_INSERT_SQL =
                "INSERT  INTO gloo_auto_post(org_id,ident_key,seo_key,title,content,created_on,updated_on)"
                + " VALUES(?,?,?,?,?,?,now()) ";

        String identKey = MyUtils.getUUID();
        java.sql.PreparedStatement pstmt = connection.prepareStatement(GLOO_AUTO_POST_INSERT_SQL);

        pstmt.setString(1, orgId);
        pstmt.setString(2, identKey);
        pstmt.setString(3, MyUtils.convertPageNameToId(pageName));

        pstmt.setString(4, title);
        pstmt.setString(5, summary);
        pstmt.setString(6, createdOn);

        pstmt.executeUpdate();
        pstmt.close();

    }

    private static void addGlooPageContent(java.sql.Connection connection,
            String orgId,
            String pageIdentKey,
            String title,
            String content) throws Exception {

        String identKey = MyUtils.getUUID();
        String typeOfWidget = "AUTO_POST";
        String widgetXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <widget> <type>AUTO_POST</type></widget>";
        //Magic block of Type II
        int typeofBlock = 2;
        int blockNumber = 100;

        String GLOO_BLOCK_INSERT_SQL =
                " INSERT  INTO gloo_block_data(ident_key,org_id,page_key, "
                + " widget_type, title,widget_xml,widget_html, "
                + " created_on,block_no,block_type) "
                + " VALUES(?,?,?,?,?,?,?,now(),?,?) ";

        java.sql.PreparedStatement pstmt = connection.prepareStatement(GLOO_BLOCK_INSERT_SQL);
        pstmt.setString(1, identKey);
        pstmt.setString(2, orgId);
        pstmt.setString(3, pageIdentKey);
        pstmt.setString(4, typeOfWidget);

        pstmt.setString(5, title);
        pstmt.setString(6, widgetXml);
        pstmt.setString(7, content);

        pstmt.setInt(8, blockNumber);
        pstmt.setInt(9, typeofBlock);

        pstmt.executeUpdate();
        pstmt.close();

    }

    private static void createGlooPage(java.sql.Connection connection,
            String orgId,
            String pageIdentKey,
            String pageName) throws Exception {

        String seoKey = MyUtils.convertPageNameToId(pageName);
        //delete existing page on this SEO key
        // page delete - should cascade content delete via triggers

        String GLOO_PAGE_DELETE_SQL = "delete from gloo_page where org_id = "
                + orgId + " and seo_key ='" + seoKey + "' ";


        //fire delete
        java.sql.Statement stmt = connection.createStatement();
        stmt.executeUpdate(GLOO_PAGE_DELETE_SQL);
        stmt.close();
        
        String GLOO_PAGE_INSERT_SQL =
                "INSERT  INTO gloo_page(org_id,ident_key,seo_key,page_name,created_on,updated_on)"
                + " VALUES(?,?,?,?,now(),now()) ";


        java.sql.PreparedStatement pstmt = connection.prepareStatement(GLOO_PAGE_INSERT_SQL);
        pstmt.setString(1, orgId);
        pstmt.setString(2, pageIdentKey);
        pstmt.setString(3, seoKey);
        pstmt.setString(4, pageName);

        pstmt.executeUpdate();
        pstmt.close();

    }

    
}
