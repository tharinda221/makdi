package webgloo.makdi.profile;

import webgloo.makdi.io.ProfileXmlObject;
import webgloo.makdi.io.ObjectXmlBridge;
import webgloo.makdi.processor.GoogleHotTrendProcessor;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class ProfileManager {

    public static IProfileBean getProfileBean(String profileBeanFile) throws Exception {
        ObjectXmlBridge xmlobj = new ObjectXmlBridge();
        ProfileXmlObject bean = xmlobj.decodeXStreamXmlFile(profileBeanFile);
        
        if(MyUtils.isNullOrEmpty(bean.getSiteGuid())) {
            throw new Exception("Null or empty Profile site GUID");
        }

        return bean;
    }

    public static void process(String profileBeanFile) throws Exception {
        process(getProfileBean(profileBeanFile));

    }

    public static void process(IProfileBean profileBean) throws Exception {
        //only one process right now
        GoogleHotTrendProcessor.invoke(profileBean);

    }
}
