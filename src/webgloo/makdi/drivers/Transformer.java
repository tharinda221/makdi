package webgloo.makdi.drivers;

/**
 *
 * @author rajeevj
 */

public class Transformer {

    public static final String  SPACE  = " " ;

    private String prefix;
    private String suffix;
    //private boolean wrapInQuotes ;
    
    public Transformer() {
        this.prefix = null ;
        this.suffix = null ;
       
    }

    public Transformer(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;

    }
    
    public String transform(String s) throws Exception {
        s = (this.prefix == null ) ? s : this.prefix + SPACE + s ;
        s = (this.suffix == null ) ? s : s + SPACE + this.suffix ;
        return s;

    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
    
}
