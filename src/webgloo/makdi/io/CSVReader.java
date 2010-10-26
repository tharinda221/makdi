
package webgloo.makdi.io;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import webgloo.makdi.util.MyUtils;

/**
 *
 * @author rajeevj
 */
public class CSVReader extends MyFileReader{

     public CSVReader(String fileName) throws Exception {
        super(fileName);
     }

      public List<String[]> getLines() throws Exception {


        String word;
        List<String[]> lines = new ArrayList<String[]>();
        
        while ((word = readLine()) != null) {

            if (word.charAt(0) == '#') {
                //ignore comments
                continue;
            }

            word = word.trim();
            if (!StringUtils.isBlank(word)) {
                word = MyUtils.squeeze(word);
                //explode this word on commas
                String[] tokens = word.split(",");
                lines.add(tokens);
            }

        }

        return lines;
        
    }


    public static void main(String[] args) throws Exception{
        CSVReader reader = new CSVReader("keywords-2.csv");
        
        String orgId = "1178" ;
        int count = 1 ;
        int modulo ;
        
        for(String[] tokens : reader.getLines()) {
            int divisor = 20 ;
            String SQL = " insert ignore into gloo_auto_keyword (org_id, token, seo_key, created_on)";
            SQL +=  " values (" ;
            SQL += "'" + orgId +"', '" + tokens[0] + "', '" ;
            SQL += MyUtils.convertPageNameToId(tokens[0]) + "', '" ;
            //for same token keep adding the delays
            modulo = count % divisor ;
            
            SQL += tokens[1] + " " + MyUtils.now(modulo) + "' );";
            
            System.out.println(SQL + "\n");
            count++ ;
        }

    }



}
