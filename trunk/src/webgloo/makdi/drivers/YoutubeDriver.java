package webgloo.makdi.drivers;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import webgloo.makdi.data.IData;
import webgloo.makdi.data.Video;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 *
 */


public class YoutubeDriver implements IDriver {

    //public final static String YOUTUBE_DEVELOPER_KEY = "AI39si4lE6Zol7L6lMp8ZbmMGLZPnpmGj0JBMvWqYBHE8CYKUS_hoqZXxgv4QqK4WKyIwAWdV8giymWfXe2Ne5Gyy3Xs2f5FeA";
    public final static int MAX_RESULTS = 10;
    public final static String YOUTUBE_VIDEO_URI = "http://gdata.youtube.com/feeds/api/videos";
    
    
    private int maxResults;
    private Transformer transformer;
    
    public YoutubeDriver(Transformer transformer) {
        this.maxResults = MAX_RESULTS;
        this.transformer = transformer;

    }

    public YoutubeDriver(Transformer transformer, int maxResults) {
        this.maxResults = maxResults;
        this.transformer = transformer;
    }

    @Override
    public String getName() {
        return IDriver.YOUTUBE_DRIVER;
    }

    @Override
    public long getDelay() {
        return 3000 ;
    }
    
    @Override
    public List<IData> run(String tag) throws Exception {
        MyTrace.entry("YoutubeDriver", "run()");

        tag = this.transformer.transform(tag);
        //Urlencode the tag
        //tag = java.net.URLEncoder.encode(tag, "UTF-8");

        YouTubeService service = new YouTubeService("indigloo-makdi-1.0");
        YouTubeQuery query = new YouTubeQuery(new URL(YoutubeDriver.YOUTUBE_VIDEO_URI));
        // use default ordering
        // query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
        query.setMaxResults(this.maxResults);
        query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);

        List<IData> items = new ArrayList<IData>();

        //Fetch videos for each tag

        MyTrace.debug("sending request to :: " + query.getUrl());
        //include restricted content in the search results
        query.setFullTextQuery(tag);
        VideoFeed videoFeed = service.query(query, VideoFeed.class);

        List<VideoEntry> videoEntries = videoFeed.getEntries();
        for (VideoEntry entry : videoEntries) {
            items.add(createVideo(entry));

        }
        
        MyTrace.exit("YoutubeDriver", "run()");

        return items;


    }

    private Video createVideo(VideoEntry entry) {
        Video video = new Video();

        if (entry.getTitle() != null) {
            video.setTitle(entry.getTitle().getPlainText());
        }

        if (entry.getHtmlLink() != null) {
            video.setUrl(entry.getHtmlLink().getHref());
        }

        String videoId = entry.getId();
        String[] parts = videoId.split(":");
        video.setVideoId(parts[parts.length - 1]);

        video.setDescription(entry.getMediaGroup().getDescription().getContent().getPlainText());
        video.setSource(IDriver.YOUTUBE_DRIVER);
        return video;

    }



    public static void main(String[] args) throws Exception {

        YoutubeDriver driver = new YoutubeDriver(new Transformer(null, "trailer"), 2);
        String tag = "inception";
        List<IData> items = driver.run(tag);
        for (IData item : items) {
            MyTrace.debug(item.toHtml());
        }

    }
}
