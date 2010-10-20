package webgloo.makdi.db;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.Keyword;


/**
 *
 * @author rajeevj
 *
 */

public class DBManager {

     public static void storeKeywords(java.sql.Connection connection,List<Keyword> keywords) throws Exception{
        //store keyword is not there already
        


    }

    public static List<String> loadKeywords(java.sql.Connection connection,String appId) throws Exception{
        //store keyword is not there already
        List<String> keywords = new ArrayList<String>();
        return keywords ;
        
    }

    

    /*
    public static List<String> getAllPageIds(
            java.sql.Connection connection,
            String appId) throws Exception {

        //get distinct pageId for this appId
        String sql = " select distinct page_id from page_data where app_id = '" + appId + "' order by page_id ";
        java.sql.Statement stmt = connection.createStatement();
        java.sql.ResultSet rs = stmt.executeQuery(sql);

        List<String> pageIds = new ArrayList<String>();
        String pageId;

        while (rs.next()) {
            pageId = rs.getString("page_id");
            pageIds.add(pageId);

        }

        stmt.close();
        rs.close();

        return pageIds;

    }

    public static String getPageContent(
            java.sql.Connection connection,
            String appId,
            String pageId,
            String driver) throws Exception {

        StringBuilder sb = new StringBuilder();

        String sql = " select html from page_data where app_id ='" + appId + "' ";
        sql += " and page_id ='" + pageId + "' and driver ='" + driver + "' ";

        java.sql.Statement stmt = connection.createStatement();
        java.sql.ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            sb.append(rs.getString("html"));


        }

        stmt.close();
        rs.close();

        return sb.toString();

    } 
    

    public static void storeItems(
            java.sql.Connection connection,
            String appId,
            String pageId,
            String driver,
            List<IData> items,
            boolean deleteOldRows) throws Exception {


        //for appId,pageId and driver
        //clean all items and store new ones
        String deleteSql = " delete from page_data where app_id = '" + appId + "' and ";
        deleteSql += " page_id='" + pageId + "' and driver ='" + driver + "' ";

        //insert new items
        String insertSql = " insert into page_data(app_id,page_id,driver,title,html,created_on) ";
        insertSql += " values (?,?,?,?,?,now()) ";

        if (deleteOldRows) {
            //fire delete
            java.sql.Statement stmt = connection.createStatement();
            stmt.executeUpdate(deleteSql);
            stmt.close();
        }
        
        //store new items now
        java.sql.PreparedStatement pstmt = connection.prepareStatement(insertSql);
        pstmt.setString(1, appId);
        pstmt.setString(2, pageId);
        pstmt.setString(3, driver);

        for (IData item : items) {

            //System.out.println("html = \n" + item.toHtml());
            pstmt.setString(4, item.getTitle());
            pstmt.setString(5, item.toHtml());
            pstmt.executeUpdate();

        } //loop:items
        
        pstmt.close();

    }*/

   
}
