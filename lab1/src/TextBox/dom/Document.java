package TextBox.dom;

import TextBox.util.CharacterFactory;

import java.awt.*;
import java.util.Vector;

public class Document {
    private Vector<Line> _lines = new Vector<Line>();

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
        if(startLine == endLine && startPos == endPos) return temp;

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
        if(startLine == endLine && startPos == endPos) return temp;

        for(int currentLine = startLine; currentLine <= endLine; ) {
            Line strLine = _lines.get(currentLine);
            int countOfChars = strLine.getCountOfChars();
            int endLineSymbol = (currentLine == endLine ? endPos : countOfChars);
            for(int currentPos = currentLine == startLine ? startPos : 0 ;
                currentPos < endLineSymbol ;
                endLineSymbol--) {
                char currentChar = strLine.getCharAt(currentPos).getCH();
                strLine.deleteCharAt(currentPos);
                temp += java.lang.Character.toString(currentChar);
            }
            if(currentLine != endLine) temp += '\n';
            if(currentLine == endLine && startLine != endLine) {
                _lines.get(currentLine + TextBoxConstants.PREV_POS_MARKER).concatLines(_lines.get(currentLine));
                deleteLineAt(currentLine);
            }
            if(currentLine != startLine && currentLine != endLine) {
                deleteLineAt(currentLine);
                endLine--;
            }
            else
                currentLine++;
        }

        return temp;
    }
    public int[] pasteSelectedString(int pos, int line, String string) {
        int[] state = new int[2];
        int stringPos;
        int stringLength = string.length();
        char symbol;

        for (stringPos = 0; stringPos < stringLength; stringPos++) {
            symbol = string.charAt(stringPos);
            if (symbol == '\n') {
                insertLineAt(line);
                pos = TextBoxConstants.FIRST_SYMBOL_POS;
                line++;
            }
            else {
                Character character = CharacterFactory.newChar(symbol);
                insert(line, pos, character, TextBoxConstants.DEFAULT_FONT);
                pos++;
            }
        }
        calculateMaxWidthOfLines();
        calculateMaxHeightOfLines();
        state[0] = line;
        state[1] = pos;
        return state;
    }
    public void calculateLineWidth(int line) {
        _lines.get(line).calculateLineWidth();
    }
    public int calculateMaxWidthOfLines() {
        int maxLineWidth = 0;
        int currLineWidth;
        for (Line line : _lines) {
            line.calculateLineWidth();
            currLineWidth = line.getWidth();
            if (maxLineWidth < currLineWidth)
                maxLineWidth = currLineWidth;
        }
        return maxLineWidth;
    }
    public int calculateMaxHeightOfLines() {
        int maxLinesHeight = 0;
        int currLineHeight;
        for (Line line : _lines) {
            currLineHeight = line.getMaxHeight();
            maxLinesHeight += currLineHeight;
        }
        return maxLinesHeight;
    }
}