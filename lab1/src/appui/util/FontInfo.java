package appui.util;

import appui.util.FontPair;

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
    private static Map<String, FontPair> fontMap = new HashMap<String, FontPair>();
    private static Graphics graphics;
    public static final char FONT_KEY_SEPARATOR = '-';
    public static String getFontKey(Font f) {
        return f.getName() + FONT_KEY_SEPARATOR + f.getStyle() + FONT_KEY_SEPARATOR + f.getSize();
    }
    public static String getFontKey(String fontName, int style, int size) {
        return fontName + FONT_KEY_SEPARATOR + style + FONT_KEY_SEPARATOR + size;
    }
    public FontInfo(Graphics g) {
        graphics = g;
    }
    public static FontPair findFont(Font font) {
        String fontName = font.getName();
        int style = font.getStyle();
        int size = font.getSize();
        String keyString = FontInfo.getFontKey(font);
        FontPair searchResult = fontMap.get(keyString);
        if(searchResult == null) {
            Font newFont = new Font(fontName, style, size);
            FontMetrics newFM = graphics.getFontMetrics();
            FontPair fontPair = new FontPair(newFM, newFont);
            fontMap.put(keyString, fontPair);

            return fontPair;
        }
        else return searchResult;
    }
    public static void setGraphics(Graphics g) {
        graphics = g;
    }
}