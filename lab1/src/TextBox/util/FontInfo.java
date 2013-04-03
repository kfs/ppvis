package TextBox.util;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
            graphics.setFont(newFont);
            FontMetrics newFM = graphics.getFontMetrics();
            FontPair fontPair = new FontPair(newFM, newFont);
            fontMap.put(keyString, fontPair);
            return fontPair;
        }
        else return searchResult;
    }
    public static FontPair findFont(String fontID) {
        return fontMap.get(fontID);
    }
    public static FontPair addFont(Font font, String fontID) {
        Font currentFont = graphics.getFont();
        graphics.setFont(font);
        FontMetrics newFM = graphics.getFontMetrics();
        FontPair fontPair = new FontPair(newFM, font);
        fontMap.put(fontID, fontPair);
        graphics.setFont(currentFont);
        return fontPair;
    }
    public static void setGraphics(Graphics g) {
        graphics = g;
    }
}