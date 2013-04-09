package TextBox.util.fonts;

import java.awt.GraphicsEnvironment;

public final class FontConstants {
    private FontConstants() {

    }
    public static final String fontSizes[] = {"4", "6", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "24", "30", "36", "40", "48", "60", "72"};
    public static final String fontNames[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
}