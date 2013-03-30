package TextBox.dom;

import java.awt.*;
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
    public static final int DEFAULT_INDENT_X = 7;
    public static final int DEFAULT_INDENT_Y = 15;

    public Document() {
        Line line = new Line();
        line.setMaxHeight(TextBoxConstants.DEFAULT_LINE_SIZE);
        _lines.add(line);
    }
    public void insert(int line, int pos, Character character, Font font) {
        _lines.elementAt(line).insertCharAt(pos, character, font);
    }
    public int getCountOfLines() {
        return _lines.size();
    }
    public Line getLineAt(int pos) {
        return _lines.elementAt(pos);
    }
    public void insertLineAt(int pos) {
        Line line = new Line();
        line.setMaxHeight(TextBoxConstants.DEFAULT_LINE_SIZE);
        _lines.add(pos + TextBoxConstants.NEXT_POS_MARKER, line);
    }
    public void deleteLineAt(int pos) {
        _lines.remove(pos);
    }
    public String copySelectedString(int startPos, int startLine, int endPos, int endLine) {
        String temp = "";
        for(int currentLine = startLine; currentLine <= endLine; currentLine++ ) {
            Line strLine = _lines.get(currentLine);
            int countOfChars = strLine.getCountOfChars();
            for(int currentPos = currentLine == startLine ? startPos : 0 ;
                        currentPos < (currentLine == endLine ? endPos : countOfChars) ;
                        currentPos++) {
                char currentChar = strLine.getCharAt(currentPos).getCH();
                temp += java.lang.Character.toString(currentChar);
            }
            if(currentLine != endLine) temp += '\n';
        }

        return temp;
    }
    public String cutSelectedString(int startPos, int startLine, int endPos, int endLine) {
        String temp = "";


        return temp;
    }
    public void pasteSelectedString(int pos, int line, String string) {

    }
}