package webgloo.makdi.io;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileOutputStream;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.plugin.IPlugin;
import webgloo.makdi.plugin.microsite.MicroSitePluginBean;

/**
 *
 * @author rajeevj
 */
public class ObjectXmlBridge {
    
   
    public String encodeXStreamXml(IPlugin plugin) throws Exception {
        MyTrace.entry("ObjectXmlBridge", "encodeXStreamXml(plugin)");

        PluginXmlObject bean = new PluginXmlObject();
        bean.setDrivers(plugin.getDrivers());
        bean.setSiteDomain(plugin.getSiteDomain());
        bean.setSiteName(plugin.getSiteName());
        bean.setSiteGuid(plugin.getSiteGuid());
        bean.setFrontPageDrivers(plugin.getFrontPageDrivers());

        bean.setAction(plugin.getAction());
        bean.setName(plugin.getName());
        bean.setKeywords(plugin.getKeywords());

        XStream xstream = new XStream(new DomDriver());
        xstream.alias("plugin", PluginXmlObject.class);
        String xml = xstream.toXML(bean);

        File f = new File("plugin-bean.xml");
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(xml.getBytes());
        fos.close();

        MyTrace.exit("ObjectXmlBridge", "encodeXStreamXml(profile)");

        return xml;
    }
    
    public PluginXmlObject decodeXStreamXmlFile(String pluginFile) throws Exception{
        MyTrace.entry("ObjectXmlBridge", "decodeXStreamXmlFile(file)");
        MyFileReader fr = new MyFileReader(pluginFile);
        String xml = fr.getAsString();
        MyTrace.exit("ObjectXmlBridge", "decodeXStreamXmlFile(file)");

        return decodeXStreamXml(xml);
        
    }

    public PluginXmlObject decodeXStreamXml(String xml) throws Exception{
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("plugin", PluginXmlObject.class);
        PluginXmlObject bean = (PluginXmlObject) xstream.fromXML(xml);
        return bean ;

    }
    
    public static void main(String[] args) throws Exception {

        ObjectXmlBridge objxml = new ObjectXmlBridge();
        String xml = objxml.encodeXStreamXml(new MicroSitePluginBean());
        System.out.println(xml);
                
    }
}
