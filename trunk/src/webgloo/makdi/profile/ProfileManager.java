package webgloo.makdi.profile;

import webgloo.makdi.io.ProfileXmlObject;
import webgloo.makdi.io.ObjectXmlBridge;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.processor.GoogleHotTrendProcessor;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class ProfileManager {

    public static IContentProfile getProfileBean(String profileBeanFile) throws Exception {
        ObjectXmlBridge xmlobj = new ObjectXmlBridge();
        ProfileXmlObject bean = xmlobj.decodeXStreamXmlFile(profileBeanFile);
        
        if(MyUtils.isNullOrEmpty(bean.getSiteGuid())) {
            throw new Exception("Null or empty Profile site GUID");
        }
        if(MyUtils.isNullOrEmpty(bean.getAction())) {
            throw new Exception("Null or empty Action");
        }
        
        return bean;
    }
    
    public static void process(String profileBeanFile) throws Exception {
        MyTrace.entry("ProfileManager", "process(bean file)");
        process(getProfileBean(profileBeanFile));
        MyTrace.exit("ProfileManager", "process(bean file)");
    }
    
    private static void process(IContentProfile profileBean) throws Exception {
        if(profileBean.getName().equals(IContentProfile.GOOGLE_HOT_TRENDS)){
            new GoogleHotTrendProcessor().invoke(profileBean);
        } else {
            throw new Exception(profileBean.getName() + " processor not found ");
        }
        
    }
}
