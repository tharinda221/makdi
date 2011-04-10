package webgloo.makdi.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author rajeevj
 */
public class MyFileWriter {

    private BufferedWriter bufferedWriter = null;

    public MyFileWriter(String fileName) throws Exception {
        String executionPath = System.getProperty("user.dir");
        //try full path first
        File f = new File(fileName);
        if (!f.exists()) {
            //should be relative path to execution dir now
            f = new File(executionPath + File.separator + fileName);
            System.out.println(" Absolute path = " + f.getAbsolutePath() + "\n");
            this.bufferedWriter = new BufferedWriter(new FileWriter(f));
            
        }
        
    }

    public void write(String s) throws Exception {
        this.bufferedWriter.write(s);
    }

    public void close() {
        try {
            if (this.bufferedWriter != null) {
                this.bufferedWriter.flush();
                this.bufferedWriter.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
   
    public static void main(String[] args) throws Exception {
        MyFileWriter writer = new MyFileWriter("file.out");
        writer.write("some sample string");
        writer.close();

    }
}
