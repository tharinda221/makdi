package webgloo.makdi.drivers;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 *
 */
public abstract class YahooBossDriver {

    public final static String BOSS_APPLICATION_ID = "dDGV1CbV34FpBSGBFMZXtX4x1I5GYdcJskAxZzjND3O3ZeYN4j4CPzfSROgc";
    //BOSS is no limits API right now
    // but again lets play it safe!
    private int maxResults;
    private int startIndex;
    private String[] siteNames = null;
    private Transformer transformer;

    public YahooBossDriver(String[] siteNames,int startIndex, int maxResults) {
        this.transformer = new Transformer();
        this.maxResults = maxResults;
        this.startIndex = startIndex;
        this.siteNames = siteNames;
    }

    public YahooBossDriver(
            Transformer transformer,
            String[] siteNames,
            int startIndex,
            int maxResults) {
            
        this.transformer = transformer;
        this.maxResults = maxResults;
        this.startIndex = startIndex;
        this.siteNames = siteNames;
    }

    // concrete implementations will overwrite this method
    public abstract List<IData> getResults(String address) throws Exception;
    
    public long getDelay() {
        return 3000;
    }

    public List<IData> run(String endpointURI, String arguments, String token) throws Exception {

        MyTrace.entry("YahooBossDriver", "run()");
        token = this.transformer.transform(token);
        token = java.net.URLEncoder.encode(token, "UTF-8");
        List<IData> items;

        if (this.siteNames != null) {
            items = this.getAllSitesItems(endpointURI, arguments, token);
        } else {
            items = this.getAllItems(endpointURI, arguments, token);
        }

        MyTrace.exit("YahooBossDriver", "run()");
        return items;

    }

    private List<IData> getAllSitesItems(String endpointURI, String arguments, String token) throws Exception {

        List<IData> items = new ArrayList<IData>();
        //search specific sites
        for (String siteName : this.siteNames) {
            String address = createAddress(endpointURI, arguments, siteName, token);
            MyTrace.debug("sending BOSS request to :: " + address);
            List<IData> posts = this.getResults(address);
            for (IData post : posts) {
                items.add(post);
            }
        }

        return items;
    }

    private List<IData> getAllItems(String endpointURI, String arguments, String token) throws Exception {

        List<IData> items = new ArrayList<IData>();

        String address = createAddress(endpointURI, arguments, token);
        List<IData> posts = this.getResults(address);
        for (IData post : posts) {
            items.add(post);
        }
        
        return items;
    }

    private String createAddress(String endpointURI, String arguments, String siteName, String token) {

        //substitute token
        arguments = arguments.replace("{token}", token);
        //substitute application id
        arguments = arguments.replace("{applicationId}", YahooBossDriver.BOSS_APPLICATION_ID);
        //substitute site and count
        String site = "+site:" + siteName;
        int count = (this.maxResults <= 0) ? 10 : this.maxResults;
        arguments = arguments.replace("{site}", site);
        arguments = arguments.replace("{count}", "" + count);
        arguments = arguments.replace("{start}", "" + this.startIndex);
        String address = endpointURI + arguments;
        return address;

    }

    public String createAddress(String endpointURI, String arguments, String token) {

        //substitute token
        arguments = arguments.replace("{token}", token);
        //substitute application id
        arguments = arguments.replace("{applicationId}", YahooBossDriver.BOSS_APPLICATION_ID);
        //No site name -- replace with empty
        arguments = arguments.replace("{site}", "");
        int count = (this.maxResults <= 0) ? 10 : this.maxResults;
        arguments = arguments.replace("{count}", "" + count);
        arguments = arguments.replace("{start}", "" + this.startIndex);
        String address = endpointURI + arguments;
        return address;

    }
}
