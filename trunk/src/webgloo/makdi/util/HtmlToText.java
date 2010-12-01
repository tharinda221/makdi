package webgloo.makdi.util;

import java.io.StringReader;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML.Tag;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

/**
 *
 * @author rajeevj
 */

public class HtmlToText {

    public HtmlToText() {

    }

    public static String extractUsingGdataKit(String s) {
        String text = com.google.gdata.util.common.html.HtmlToText.htmlToPlainText(s);
        return text ;
    }
    
    public static String extractUsingSwingKit(String s) throws Exception {
        final StringBuilder sb = new StringBuilder();

        StringReader reader = new StringReader(s);
        ParserDelegator parserDelegator = new ParserDelegator();
        ParserCallback parserCallback = new ParserCallback() {

            @Override
            public void handleText(final char[] data, final int pos) {
                sb.append(data);
            }

            @Override
            public void handleStartTag(Tag tag, MutableAttributeSet attribute, int pos) {
            }

            @Override
            public void handleEndTag(Tag t, final int pos) {
            }

            @Override
            public void handleSimpleTag(Tag t, MutableAttributeSet a, final int pos) {
            }

            @Override
            public void handleComment(final char[] data, final int pos) {
            }

            @Override
            public void handleError(final java.lang.String errMsg, final int pos) {
            }
        };
        parserDelegator.parse(reader, parserCallback, true);

        return sb.toString();

    }

    
}
