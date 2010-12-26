package webgloo.makdi.util;

import com.google.gdata.util.common.base.StringUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.lang.WordUtils;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeevj
 */
public class MyUtils {

    public static String squeeze(String s) {
        MyTrace.entry("MyUtils", "squeeze()");
        s = s.trim();
        char[] a = s.toCharArray();
        int N = 1;
        for (int i = 1; i < a.length; i++) {
            //Add next char if non-space or
            //last one was non-space
            //N is total of such chars
            a[N] = a[i];
            if (a[N] != ' ') {
                N++;
            } else if (a[N - 1] != ' ') {
                N++;
            }
        }

        MyTrace.exit("MyUtils", "squeeze()");

        return new String(a, 0, N);
    }
    
    public static String getFirstLetterAndLower(String tag) {
        char[] letters = new char[1];
        letters[0] = tag.charAt(0);
        String letter = new String(letters).toLowerCase();
        return letter;

    }

    public static boolean isNullOrEmpty(String s) {
        if (s == null || s.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String convertPageIdToName(String pageId) {
        //convert pageId into pageName
        String tag = pageId.replace("-", " ");
        //capitalize
        tag = WordUtils.capitalizeFully(tag);
        return tag;
    }

    public static String convertPageNameToId(String pageName) {
        //remove non alphanumeric
        pageName = removeNonAlphaNumeric(pageName);
        //squeeze spaces
        pageName = squeeze(pageName);
        //convert single spaces to dashes
        pageName = pageName.replace(" ", "-");
        return pageName.toLowerCase();

    }

    public static String removeNonAlphaNumeric(String s) {
        s = s.replaceAll("[^a-zA-Z0-9]", " ");
        return s ;

    }

    
    public static String getUUID() {
        java.util.UUID uuid = java.util.UUID.randomUUID();
        return uuid.toString();
    }

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());
        
    }

     public static String now(int delay) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, delay);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(cal.getTime());

    }

       /**
     *
     * @param width
     * @param height
     * @param boxHeight (maximum height of target box)
     * @return
     * @throws Exception
     */
    public static int[] getScaledDimensions(String width, String height, float boxHeight) throws Exception {

        if (StringUtil.isEmpty(width) || StringUtil.isEmpty(height)) {
            throw new Exception("Bad image width and height supplied for scaling");
        }
        
        double originalx = Integer.parseInt(width) * 1.0f;
        double originaly = Integer.parseInt(height) * 1.0f;

        double boxWidth = (originalx / originaly) * boxHeight;

        //first try original dimensions
        double newHeight = originaly;
        double newWidth = originalx;

        if (newHeight > boxHeight) {
            double ratio = (boxHeight / newHeight);
            newHeight = ratio * newHeight;
            newWidth = ratio * newWidth;
        }

        if (newWidth > boxWidth) {
            double ratio = boxWidth / newWidth;
            newHeight = ratio * newHeight;
            newWidth = ratio * newWidth;
        }

        //Round up to nearest integer
        newWidth = Math.floor(newWidth);
        newHeight = Math.floor(newHeight);


        int[] xy = new int[2];
        xy[0] = (int) newWidth;
        xy[1] = (int) newHeight;
        return xy;

    }

    public static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String MD5(String text)
        throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        MessageDigest md;
        md = MessageDigest.getInstance("MD5");
        byte[] md5hash = new byte[32];
        md.update(text.getBytes("UTF-8"), 0, text.length());
        md5hash = md.digest();
        return convertToHex(md5hash);
    }

    public static void main(String[] args) throws Exception{
        String s = "Meditation Chairs - The Benefits Of Having One";
        System.out.println(MyUtils.MD5(s));
            
    }
}

