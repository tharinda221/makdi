package webgloo.makdi.db;

import java.util.List;
import webgloo.makdi.util.MyStringUtil;

/**
 *
 * @author rajeevj
 */
public class DBHelper {

    public static void printPageKeys(String appId) throws Exception{
        java.sql.Connection connection = DBConnection.getConnection();
        List<String> pageIds = DBManager.getAllPageIds(connection, appId);
        for(String pageId : pageIds) {
            System.out.println(MyStringUtil.convertPageIdToName(pageId));
        }

    }

    public static void main(String[] args)throws Exception {
        DBHelper.printPageKeys("7dc53df5-703e-49b3-8670-b1c468f47f1f");
    }
    
}
