package appui.util;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: lgsfe_000
 * Date: 26.03.13
 * Time: 21:39
 * To change this template use File | Settings | File Templates.
 */
public class FontPair {
    private FontMetrics fontMetrics;
    private Font font;

    FontPair() {}
    FontPair(FontMetrics fM, Font f) {
        fontMetrics = fM;
        font = f;
    }
    public FontMetrics getFontMetrics() {
        return fontMetrics;
    }
    public Font getFont() {
        return font;
    }
}
