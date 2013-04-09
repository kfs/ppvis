package TextBox.dom;

import java.awt.Font;

public class TextBoxConstants {
    //document constants
    //enum
//    enum CarrentConstant{
//        ;
//    }

    public static final int DEFAULT_LINE_SIZE = 20;
    public static final int DEFAULT_FONT_SIZE = 15;
    //caret constants
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

    //scrollpane constants
    public static final int DEFAULT_HORIZONTAL_SCROLL_INDENT = 25;
    public static final int DEFAULT_VERTICAL_SCROLL_INDENT = 20;

    //copy-paste consts
    public static final int AFTER_PASTE_CARET_LINE = 0;
    public static final int AFTER_PASTE_CARET_POS = 1;

    public static final Font DEFAULT_FONT = new Font("Serif", Font.PLAIN, DEFAULT_FONT_SIZE);

    //locale constants
    public static final String LOCALE_EDITOR_TITLE = "editorTitle";
    public static final String LOCALE_DOC_TITLE = "defaultDocumentTitle";

    private TextBoxConstants() {}
}
