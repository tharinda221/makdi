package webgloo.makdi.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class MyFileReader {

        
    private BufferedReader bufferin = null;

    public MyFileReader(String fileName) throws Exception {
        String executionPath = System.getProperty("user.dir");
        //try full path first
        File f = new File(fileName);
        if (!f.exists()) {
            //should be relative path to execution dir now
            f = new File(executionPath + File.separator + fileName);
            if (!f.exists()) {
                throw new Exception("Unable to locate file ::" + fileName);
            }
        }

        this.bufferin = new BufferedReader(new FileReader(f));
    }

    public List<String> getAllLines() throws Exception {
        List<String> list = new ArrayList<String>();
        String word;
        while ((word = readLine()) != null) {

            if (word.charAt(0) == '#') {
                //ignore comments
                continue;
            }

            word = word.trim();
            if (!StringUtils.isBlank(word)) {
                word = MyUtils.squeeze(word);
                list.add(word);

            }
            
        }

        return list;

    }
    
    public String getAsString() throws Exception {
        StringBuilder sb = new StringBuilder();
        String word;
        while ((word = readLine()) != null) {
            sb.append(word);
        }

        return sb.toString();
    }

    public String readLine() throws Exception {
        if (this.bufferin == null) {
            throw new Exception("Buffered in not initialized yet");
        }
        return this.bufferin.readLine();

    }

    public static void main(String[] args) throws Exception{
        MyFileReader reader = new MyFileReader("book1.keywords");
        for(String s : reader.getAllLines()) {
            System.out.println(s);
        }

    }
}
