package webgloo.makdi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author rajeevj
 */
public class MyUtils {

    public static String squeeze(String s) {
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
        return new String(a, 0, N);
    }

    public static String squeezeAndReplace(String s, char c) {
        s = s.trim();
        char[] a = s.toCharArray();
        int N = 1;
        int S = -1;
        for (int i = 1; i < a.length; i++) {
            //Add next char is non-space or last one was
            // non-space
            a[N] = a[i];
            if (a[N] != ' ') {
                N++;
            } else if (a[N - 1] != ' ' && (N - 1 != S)) {
                //a[N] is space
                //ready to gather the next char
                //we made a substitution at N
                a[N] = c;
                S = N;
                N++;

            }
        }
        return new String(a, 0, N);
    }

    public static String squeezeAndReplaceAndLower(String s, char c) {
        String result = squeezeAndReplace(s, c);
        return result.toLowerCase();
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
        //convert  pageName into pageId
        return squeezeAndReplaceAndLower(pageName, '-');

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

    public static void main(String[] args) {
        String s = MyUtils.now();
        System.out.println("[" + s + "]");

    }
}

