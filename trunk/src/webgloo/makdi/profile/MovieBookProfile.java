package webgloo.makdi.profile;

import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.drivers.*;
import webgloo.makdi.drivers.Transformer;
import webgloo.makdi.rss.GoogleNewsRssSource;

/**
 *
 * @author rajeevj
 */
public class MovieBookProfile implements IContentProfile {

    public String getName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getAction() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<String> getKeywords() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getSiteDomain() {
        return "www.moviebook.info";
    }

    @Override
    public String getSiteName() {
        return "moviebook.info - Movies based on books";
    }

    @Override
    public List<IDriver> getDrivers() {
        //Decide on what drivers to load
        List<IDriver> drivers = new ArrayList<IDriver>();

        //You need this searchId for adding google custom search box
        drivers.add(new GoogleCseDriver(new Transformer(), "008055205579233425318:vepvl6ivmpu"));
        drivers.add(new TmdbDriver(new Transformer()));
        drivers.add(new GoogleBookDriver(1, 2));
        drivers.add(new WikipediaDriver(new Transformer(),2));
        drivers.add(new YoutubeDriver(new Transformer(null, "trailer"), 1, 1));
        drivers.add(new TwitterDriver(new Transformer("watched", null), 8));
        drivers.add(new TwitterDriver(new Transformer("saw", null), 8));
        drivers.add(new YahooAnswerDriver(new Transformer("movie", null), 1, 3));
        drivers.add(new YahooAnswerDriver(new Transformer("book", null), 1, 3));

        return drivers;
    }

    @Override
    public String getSiteGuid() {
        return "7dc53df5-703e-49b3-8670-b1c468f47f1f";

    }

    @Override
    public List<IDriver> getFrontPageDrivers() {
        List<IDriver> drivers = new ArrayList<IDriver>();
        //return google news driver
        IDriver driver1 = new RssDriver(1, new GoogleNewsRssSource());
        drivers.add(driver1);
        return drivers;
    }
}
