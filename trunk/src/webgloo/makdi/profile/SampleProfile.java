package webgloo.makdi.profile;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.rss.GoogleNewsRssSource;
import webgloo.makdi.rss.VanillaRssSource;

/**
 *
 * @author rajeevj
 */
public class SampleProfile implements IContentProfile {

    public String getName() {
        return "sample";
    }

    public String getAction() {
        return IContentProfile.ACTION_TEST;
    }

    public List<String> getKeywords() {
        List<String> keywords = new ArrayList<String>();
        keywords.add("keyword1");
        keywords.add("keyword2");

        return keywords;
    }

    @Override
    public String getSiteDomain() {
        return "sample.indigloo.net";
    }

    @Override
    public String getSiteName() {
        return "sample.indigloo.net - all profiles";
    }

    @Override
    public List<IDriver> getDrivers() {
        //Decide on what drivers to load
        List<IDriver> drivers = new ArrayList<IDriver>();

        drivers.add(new EbayDriver(new Transformer(), 5));
        drivers.add(new GoogleBaseDriver(1, 4));
        drivers.add(new GoogleBookDriver(new Transformer(null, "+book"), 1, 2));


        drivers.add(new GoogleSearchControlDriver(new Transformer()));
        drivers.add(new RssDriver(10, new VanillaRssSource("http://www.nytimes.com/services/xml/rss/nyt/HomePage.xml")));
        drivers.add(new RssDriver(5, new GoogleNewsRssSource()));

        drivers.add(new TmdbDriver(new Transformer()));
        drivers.add(new TwitterDriver(new Transformer("watched", null), 8));
        drivers.add(new WikipediaDriver(1));
        drivers.add(new YoutubeDriver(new Transformer(null, "trailer"), 1, 1));
        drivers.add(new YahooAnswerDriver(new Transformer("movie", null), 1, 2));


        String[] siteNames = new String[]{"rottentomatoes.com", "flickr.com"};
        drivers.add(
                new YahooBossWebDriver(
                new Transformer("intitle:", "+book"), siteNames, 1, 2));
        drivers.add(new YahooBossNewsDriver(null, 0, 5));
        drivers.add(new YahooBossImageDriver(null, 0, 5));

        return drivers;
    }

    @Override
    public List<IDriver> getFrontPageDrivers() {
        List<IDriver> drivers = new ArrayList<IDriver>();
        //return google news driver
        IDriver driver1 = new RssDriver(1, new GoogleNewsRssSource());
        drivers.add(driver1);
        return drivers;
    }

    @Override
    public String getSiteGuid() {
        return "7dc53df5-703e-49b3-8670-b1c468f47f1f";

    }
}
