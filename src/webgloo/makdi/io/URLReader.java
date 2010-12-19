package webgloo.makdi.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import webgloo.makdi.logging.MyTrace;

/**
 *
 * @author rajeev jha(jha.rajeev@gmail.com)
 * 
 */
public class URLReader {

    public static String read(String address) throws Exception {

        MyTrace.entry("URLReader", "read(address)");

        URL url = new URL(address);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.8) Gecko/20100722 Firefox/3.6.8");

        String line;
        String response;
        //long totalBytes = 0  ;

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            //totalBytes += line.getBytes("UTF-8").length ;
            //System.out.println("Total bytes read ::  " + totalBytes);
        }

        response = builder.toString();

        MyTrace.exit("URLReader", "read(address)");

        return response;
    }

    public static int getCode(String address) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(address).openConnection();
        int code = connection.getResponseCode();
        return code;
    }

    public static boolean isValidURI(String address) throws Exception {
        boolean flag = false;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(address).openConnection();
            int code = connection.getResponseCode();
            flag = (code == 200) ? true : false;
        } catch (Exception ex) {
            flag = false;
        }
        
        return flag;

    }

    public static void main(String[] args) throws Exception {
        String address = "http://www.moomoogames.com/rajeev";
        System.out.println(URLReader.isValidURI(address));
    }
}
