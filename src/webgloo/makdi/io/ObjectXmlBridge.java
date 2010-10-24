package webgloo.makdi.io;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.File;
import java.io.FileOutputStream;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.profile.GoogleHotTrendProfile;
import webgloo.makdi.profile.IProfileBean;

/**
 *
 * @author rajeevj
 */
public class ObjectXmlBridge {
    
   
    public String encodeXStreamXml(IProfileBean profile) throws Exception {
        MyTrace.entry("ObjectXmlBridge", "encodeXStreamXml(profile)");

        ProfileXmlObject bean = new ProfileXmlObject();
        bean.setDrivers(profile.getDrivers());
        bean.setSiteDomain(profile.getSiteDomain());
        bean.setSiteName(profile.getSiteName());
        bean.setSiteGuid(profile.getSiteGuid());
        bean.setFrontPageDriver(profile.getFrontPageDriver());
        
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("profile", ProfileXmlObject.class);
        String xml = xstream.toXML(bean);

        File f = new File("profile-bean.xml");
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(xml.getBytes());
        fos.close();

        MyTrace.exit("ObjectXmlBridge", "encodeXStreamXml(profile)");

        return xml;
    }
    
    public ProfileXmlObject decodeXStreamXmlFile(String profileFile) throws Exception{
        MyTrace.entry("ObjectXmlBridge", "decodeXStreamXmlFile(file)");
        MyFileReader fr = new MyFileReader(profileFile);
        String xml = fr.getAsString();
        MyTrace.exit("ObjectXmlBridge", "decodeXStreamXmlFile(file)");

        return decodeXStreamXml(xml);
        
    }

    public ProfileXmlObject decodeXStreamXml(String xml) throws Exception{
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("profile", ProfileXmlObject.class);
        ProfileXmlObject bean = (ProfileXmlObject) xstream.fromXML(xml);
        return bean ;

    }
    
    public static void main(String[] args) throws Exception {

        ObjectXmlBridge objxml = new ObjectXmlBridge();
        String xml = objxml.encodeXStreamXml(new GoogleHotTrendProfile());
        System.out.println(xml);
                
    }
}
