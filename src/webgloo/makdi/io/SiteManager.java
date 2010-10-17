package webgloo.makdi.io;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.data.IData;
import webgloo.makdi.drivers.IDriver;
import webgloo.makdi.html.HtmlPage;
import webgloo.makdi.profile.IProfile;
import webgloo.makdi.util.MyStringUtil;
import webgloo.makdi.util.MyWriter;

/**
 *
 * @author rajeevj
 */
public class SiteManager {

    
    public SiteManager() {

    }


    public void run(IProfile profile, List<String> keywords) throws Exception {
        List<IDriver> drivers = profile.getDrivers();
        
        
        //run this profile and store data in DB
        for (String keyword : keywords) {
            MyWriter.toConsole("\n** start process :: " + keyword);
            List<IData> bucket = new ArrayList<IData>();
            //raw keyword
            keyword = WordUtils.capitalizeFully(keyword);

            for (IDriver driver : drivers) {
                //use keyword + driver
                //Reader gives us trimmed + squeezed keywords
                //driver knows how to transform the keyword
                List<IData> items = driver.run(keyword);
                bucket.addAll(items);
            }

            //store items in a page
            //create pageId from pageName
            String pageId = MyStringUtil.convertPageNameToId(keyword);

            //generate page
            HtmlPage.createPage("test", "test.com", "test menu", pageId, bucket);
            MyWriter.toConsole("\n** end process :: " + keyword);
        } //loop: keywords


    }

    public void store(IProfile profile) throws Exception {

        KeywordsManager.loadNewKeywords(profile);
        
        /*
        List<IDriver> drivers = profile.getDrivers();
        java.sql.Connection connection = DBConnection.getConnection();

        
        List<String> keywords = DBManager.loadKeywords(profile);
        
        //run this profile and store data in DB
        for (String keyword : keywords) {
            MyWriter.toConsole("\n** start process :: " + keyword);
            //list to keep track of deleted driver content
            //profile can have multiple instance of this driver
            // so it is important that we clear the DB rows only
            //one time. Failing to do so would mean we are erasing data
            //created by  driver instance1 when driver instance2 runs
            //@IMP
            //ignore already used driver
            List<String> usedDrivers = new ArrayList<String>();
            //raw keyword
            for (IDriver driver : drivers) {
                //use keyword + driver
                //Reader gives us trimmed + squeezed keywords
                keyword = WordUtils.capitalizeFully(keyword);
                List<IData> items = driver.run(keyword);
                //store the items in DB
                String pageId = MyStringUtil.convertPageNameToId(keyword);

                if (usedDrivers.contains(driver.getName())) {
                    //already seen this driver for this keyword
                    DBManager.storeItems(
                            connection,
                            profile.getSiteGuid(),
                            pageId,
                            driver.getName(),
                            items, false);
                } else {
                    //not seen this driver for this keyword
                    //delete old rows - from previous run
                    DBManager.storeItems(
                            connection,
                            profile.getSiteGuid(),
                            pageId,
                            driver.getName(),
                            items, true);
                    usedDrivers.add(driver.getName());

                }


            } //loop:drivers
            
            MyWriter.toConsole("\n** end process :: " + keyword);
        } //loop:keywords

        connection.close(); */
        
    }

    /*
    public void createPageFromDB(
            java.sql.Connection connection,
            String appId,
            String siteDomain,
            String siteName,
            String menuHtml,
            String pageId,
            List<IDriver> drivers) throws Exception {

        StringBuilder sb = new StringBuilder();
        //we need to ignore multiple instances of same driver
        //for page generation purposes
        List<String> usedDrivers = new ArrayList<String>();

        for (IDriver driver : drivers) {
            //@IMP
            //ignore already used driver
            if (!usedDrivers.contains(driver.getName())) {
                sb.append(DBManager.getPageContent(connection, appId, pageId, driver.getName()));
                usedDrivers.add(driver.getName());
            }

        }
        //generate page
        HtmlPage.createPage(siteName, siteDomain, menuHtml, pageId, sb.toString());

    }

    public void createSiteFromDB(IProfile profile,List<String> keywords) throws Exception {
        
        MyWriter.toConsole("\nGenerating site from DB :: appId => " + profile.getSiteGuid());
                
        //create menus html first
        //DB connection for site
        java.sql.Connection connection = DBConnection.getConnection();
        
        //load all pageId from DB for this appId?
        //List<String> pageIds = DBManager.getAllPageIds(connection, profile.getSiteGuid());
        //Use supplied keywords as Ids
        List<String> pageIds = new ArrayList<String>();
        
        for(String keyword : keywords) {
            pageIds.add(MyStringUtil.convertPageNameToId(keyword));
        }
        
        MenuManager menum = new MenuManager(profile.getSiteDomain());
        String menuHtml = menum.createMenusFromDB(pageIds);

        //now create a list of drivers (in the order we want!)
        List<IDriver> drivers = profile.getDrivers();

        //Create an Html page
        //foreach pageId + driver - fetch content
        for (String pageId : pageIds) {

            this.createPageFromDB(
                    connection,
                    profile.getSiteGuid(),
                    profile.getSiteDomain(),
                    profile.getSiteName(),
                    menuHtml,
                    pageId,
                    drivers);
        }

        //close DB connection for site
        connection.close();
        MyWriter.toConsole("\n*** created site *** " + profile.getSiteDomain());
    } */
    
}
