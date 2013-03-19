package appui.dom;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 18.03.13
 * Time: 17:47
 * To change this template use File | Settings | File Templates.
 */
public class Document {
    private Vector<Line> _lines = new Vector<Line>();
    public static final int DEFAULT_INDENT_X = 10;
    public static final int DEFAULT_INDENT_Y = 15;

    public Document() {
        _lines.add(new Line(DEFAULT_INDENT_Y));
    }
    public void keyPressed(char _char) {

    }

    public void insert(int line, int pos, Character character) {
        _lines.elementAt(line).insertCharAt(pos, character);
    }
    public int getCountOfLines() {
        return _lines.size();
    }
    public Line getLineAt(int pos) {
        return _lines.elementAt(pos);
    }

}
