
package webgloo.makdi.profile;

import webgloo.makdi.io.ProfileXmlObject;
import webgloo.makdi.io.ObjectXmlBridge;

/**
 *
 * @author rajeevj
 */
public class ProfileManager {

    public static IProfile getProfile(String profileFile) throws Exception{
        ObjectXmlBridge xmlobj = new ObjectXmlBridge();
        ProfileXmlObject bean = xmlobj.decodeXStreamXmlFile(profileFile);
        return bean;
    }
    
}
