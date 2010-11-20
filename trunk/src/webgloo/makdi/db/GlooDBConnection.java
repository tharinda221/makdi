
package webgloo.makdi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 */
public class GlooDBConnection {

    //do not want to publicize db + password
    // let his remain hidden in code 
    public static Connection getConnection() throws Exception{
        MyTrace.entry("DBConnection", "getConnection()");
        Class.forName("com.mysql.jdbc.Driver");
        
        String url =  "jdbc:mysql://localhost:3306/webgloodb";
        Connection connection =  DriverManager.getConnection(url,"gloo", "osje8L");
        MyTrace.exit("DBConnection", "getConnection()");
        return connection;
    }
    
}
