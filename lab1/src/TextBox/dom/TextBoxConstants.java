package TextBox.dom;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 30.03.13
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
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

    public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, TextBoxConstants.DEFAULT_FONT_SIZE);

    private TextBoxConstants() {}
}
