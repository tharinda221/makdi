package webgloo.makdi.profile;

import webgloo.makdi.io.ProfileXmlObject;
import webgloo.makdi.io.ObjectXmlBridge;
import webgloo.makdi.processor.GoogleHotTrendProcessor;

/**
 *
 * @author rajeevj
 */
public class ProfileManager {

    public static IProfileBean getProfileBean(String profileBeanFile) throws Exception {
        ObjectXmlBridge xmlobj = new ObjectXmlBridge();
        ProfileXmlObject bean = xmlobj.decodeXStreamXmlFile(profileBeanFile);
        return bean;
    }

    public static void process(String profileBeanFile) throws Exception {
        process(getProfileBean(profileBeanFile));

    }

    public static void process(IProfileBean profileBean) throws Exception {
        System.out.println("inside process ..");
        //if (profile instanceof GoogleHotTrendProfile) {
            GoogleHotTrendProcessor.invoke(profileBean);

        //}

    }
}
