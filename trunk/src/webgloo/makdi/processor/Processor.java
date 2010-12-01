package webgloo.makdi.processor;

import java.util.List;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.data.IData;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.html.HtmlPage;
import webgloo.makdi.logging.MyTrace;
import webgloo.makdi.profile.IContentProfile;

/**
 *
 * @author rajeevj
 */
public abstract class Processor {
    
    public void invoke(IContentProfile profileBean) throws Exception {
        if (profileBean.getAction().equals(IContentProfile.ACTION_STORE)) {
            this.storeContent(profileBean);

        } else if (profileBean.getAction().equals(IContentProfile.ACTION_TEST)) {
            this.createTestPage(profileBean);
        } else {
            throw new Exception(" Processor :: Unknown Action :: " + profileBean.getAction());
        }
    }

    public void createTestPage(IContentProfile profileBean) throws Exception {
        
        List<String> keywords = profileBean.getKeywords();

        for (String keyword : keywords) {
            String token = WordUtils.capitalizeFully(keyword);
            StringBuilder content = new StringBuilder();
            this.getPageContent(profileBean, token, content);

            //create an HTML page
            HtmlPage.createPage(
                    profileBean.getSiteName(),
                    profileBean.getSiteDomain(),
                    token, content.toString());
            
             MyTrace.info("Test :: processed  keyword :: " + keyword);
        }

    }
    
    public void getPageContent(
            IContentProfile profileBean,
            String token,
            StringBuilder content) {

        List<IDriver> drivers = profileBean.getDrivers();

        for (IDriver driver : drivers) {
            try {
                List<IData> items = driver.run(token);

                for (IData item : items) {
                    content.append(item.toHtml());

                } //driver processed right
                //wait for appropriate time
                Thread.sleep(driver.getDelay());

            } catch (Exception ex) {
                //some error from driver
                MyTrace.error("Error in driver :: " + driver.getName());
                MyTrace.error(ex.getMessage());

            }

        } //loop: drivers
    }

    public abstract void storeContent(IContentProfile profileBean) throws Exception ;
    
}
