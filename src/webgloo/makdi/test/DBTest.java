
package webgloo.makdi.test;

import webgloo.makdi.db.GlooDBConnection;
import webgloo.makdi.db.GlooDBManager;
import webgloo.makdi.io.MyFileReader;

/**
 *
 * @author rajeevj
 */
public class DBTest {

    public static void main(String[] args) throws Exception {
        java.sql.Connection connection = GlooDBConnection.getConnection();
        String pageIdentKey= "81965ad5-4503-451f-9dba-f6ebf4a5f46c" ;
        String typeOfWidget = "TEXT_ONLY" ;
        String widgetXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <widget> <type> TEXT_ONLY</type> <markdown>NO</markdown> </widget>" ;
        StringBuilder title = new StringBuilder("test title x123");
        

       // MyFileReader reader = new MyFileReader("futa.txt");
       // String fileBlob = reader.getAsString();
       // StringBuilder content = new StringBuilder(fileBlob);
       String testString = "\uFFFD";
       StringBuilder content = new StringBuilder(testString);
       System.out.println("Bytes length in UTF-8 :: " + testString.getBytes("utf-8").length);


        String orgId = "1178";

        GlooDBManager.addPageContentToBlock(connection, 
                orgId,
                pageIdentKey,
                typeOfWidget,
                widgetXml,
                title,
                content);
        


    }
}
