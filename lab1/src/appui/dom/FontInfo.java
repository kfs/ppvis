package appui.dom;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 23.03.13
 * Time: 10:41
 * To change this template use File | Settings | File Templates.
 */
public class FontInfo {
    private static Map<String, Font> fontMap = new HashMap<String, Font>();
    FontInfo() {

    }
    public static Font checkForFontInMapOrAdd(String font, int style, int size) {
        Font searchResult = fontMap.get(font);
        if(searchResult == null) {
            Font newFont = new Font(font, style, size);
            fontMap.put(font, newFont);
            return newFont;
        }
        else return searchResult;
    }
}
