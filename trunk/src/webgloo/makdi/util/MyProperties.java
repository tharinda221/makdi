package webgloo.makdi.util;

import java.io.FileInputStream;
import java.util.Properties;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 */
public class MyProperties {

    // Read properties file.
    private Properties properties;
    private static MyProperties _instance = new MyProperties();

    private MyProperties() {
        try {
            this.properties = new Properties();
            properties.load(new FileInputStream("makdi.properties"));
        } catch (Exception ex) {
            MyTrace.error("Problems loading makdi properties", ex);
        }
    }

    public static MyProperties getInstance() {
        return _instance;
    }

    public String getValue(String key) {
        return this.properties.getProperty(key);
    }
}
