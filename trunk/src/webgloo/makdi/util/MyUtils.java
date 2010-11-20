package webgloo.makdi.util;

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
        //replace incoming dashes with space
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

    public static void main(String[] args) {
        String s = "   Meditation Chairs: -  The Benefits Of Having One   ";
        System.out.println(MyUtils.removeNonAlphaNumeric(s));
        System.out.println(MyUtils.convertPageNameToId(s));
        
    }
}

