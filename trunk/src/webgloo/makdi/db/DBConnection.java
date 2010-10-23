
package webgloo.makdi.db;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author rajeevj
 */
public class DBConnection {

    
    public static Connection getConnection() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        String url =  "jdbc:mysql://localhost:3306/makdidb";
        Connection connection =  DriverManager.getConnection(url,"root", "root");
        return connection;
    }
    
}
