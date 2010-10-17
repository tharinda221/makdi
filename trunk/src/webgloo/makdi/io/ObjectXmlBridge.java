package webgloo.makdi.io;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import webgloo.makdi.profile.IProfile;
import webgloo.makdi.profile.MovieBookProfile;

/**
 *
 * @author rajeevj
 */
public class ObjectXmlBridge {

    public String encodeJAXBXml(ProfileXmlObject profile) throws Exception {
        JAXBContext context = JAXBContext.newInstance(profile.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter sw = new StringWriter();
        marshaller.marshal(profile, sw);
        return sw.toString();
    }

    public void encodeJavaBeanXml(Object object) throws Exception {
        FileOutputStream fos = new FileOutputStream("bean.xml");
        XMLEncoder xenc = new XMLEncoder(fos);
        // Write object.
        xenc.writeObject(object);
        xenc.close();
    }

    public String encodeXStreamXml(IProfile profile) throws Exception {
        ProfileXmlObject bean = new ProfileXmlObject();
        bean.setDrivers(profile.getDrivers());
        bean.setSiteDomain(profile.getSiteDomain());
        bean.setSiteName(profile.getSiteName());
        bean.setSiteGuid(profile.getSiteGuid());

        XStream xstream = new XStream(new DomDriver());
        xstream.alias("profile", ProfileXmlObject.class);
        String xml = xstream.toXML(bean);

        File f = new File("profile-bean.xml");
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(xml.getBytes());
        fos.close();

        return xml;
    }
    
    public ProfileXmlObject decodeXStreamXmlFile(String profileFile) throws Exception{
        
        MyFileReader fr = new MyFileReader(profileFile);
        String xml = fr.getAsString();
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
        //String xml = objxml.encodeXStreamXml(new MovieBookProfile());
        String xml = objxml.encodeXStreamXml(new MovieBookProfile());
        System.out.println(xml);
        
        
    }
}
