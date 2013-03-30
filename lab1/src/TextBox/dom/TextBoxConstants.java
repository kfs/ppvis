package TextBox.dom;

import java.awt.Font;

public class TextBoxConstants {
    public static final int DEFAULT_LINE_SIZE = 20;
    public static final int DEFAULT_FONT_SIZE = 15;
    public static final int NEXT_POS_MARKER = 1;
    public static final int PREV_POS_MARKER = -1;
    public static final int HORIZONTAL_TEXT_MARGIN = 1;
    public static final int HORIZONTAL_TEXT_INDENT = 10;
    public static final int FIRST_SYMBOL_POS = 0;
    public static final int FIRST_LINE_POS = 0;
    public static final int VISIBLE_POS_NOT_USED = -1;
    public static final int LAST_POS_ERROR = -1;

    //command constants
    public static final char CONTROL_C_COPY_SEQUENCE = '\u0003';
    public static final char CONTROL_V_PASTE_SEQUENCE = '\u0016';
    public static final char CONTROL_X_CUT_SEQUENCE = '\u0018';
    public static final char NEW_LINE_SYMBOL = '\n';
    public static final char DELETE_SYMBOL = '\u007F';
    public static final char BACK_SPACE_SYMBOL = '\b';

    public static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, DEFAULT_FONT_SIZE);

    private TextBoxConstants() {}
}
