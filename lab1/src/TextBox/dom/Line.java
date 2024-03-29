package TextBox.dom;

import TextBox.util.FontInfo;
import TextBox.util.FontPair;

import java.awt.*;
import java.util.Vector;

public class Line {
    private Vector<CharFontPair> charFontLine = new Vector<CharFontPair>();
    private int maxHeight;
    private int width;

    public Line() {}
    public int getCountOfChars() {
        return charFontLine.size();
    }
    public Character getCharAt(int pos) {
        CharFontPair l = charFontLine.get(pos);
        return l.getChar();
    }
    public void insertCharAt(int pos, Character character, Font font) {
        FontPair fontPair = FontInfo.findFont(font);
        int height = fontPair.getFontMetrics().getHeight();
        if (height>maxHeight) maxHeight = height;
        charFontLine.add(pos, new CharFontPair(character, font));
    }
    public void deleteCharAt(int pos) {
        CharFontPair charFontPair = charFontLine.get(pos);
        FontPair fontPair = FontInfo.findFont(charFontPair.getCharFont());
        int height = fontPair.getFontMetrics().getHeight();
        charFontLine.remove(pos);
        if(height==maxHeight) {
            if(getCountOfChars() != 0)
                calculateLineHeight();
            else
                setMaxHeight(TextBoxConstants.DEFAULT_LINE_SIZE);
        }
    }
    public void calculateLineHeight() {
        maxHeight = 0;
        int height;
        for(CharFontPair charFontPair : charFontLine) {
            FontPair font = FontInfo.findFont(charFontPair.getCharFont());
            height = font.getFontMetrics().getHeight();
            if(height > maxHeight) maxHeight = height;
        }
        if (maxHeight == 0)
            maxHeight = TextBoxConstants.DEFAULT_LINE_SIZE;
    }
    public void moveCharsFromTo(int from, int to, Line toLine) {
        //Line temp = new Line();
        //Collections.copy(this.line, toLine.line);
        toLine.charFontLine = (Vector<CharFontPair>)charFontLine.clone();
        toLine.charFontLine.subList(0, from).clear();
        charFontLine.subList(from, to).clear();
        if(getCountOfChars() != 0)
            calculateLineHeight();
        else
            setMaxHeight(TextBoxConstants.DEFAULT_LINE_SIZE);
        toLine.calculateLineHeight();
    }
    public void concatLines(Line concatLine) {
        charFontLine.addAll(concatLine.charFontLine);
    }
    public void setMaxHeight(int maxH) {
        maxHeight = maxH;
    }
    public int getMaxHeight() {
        return maxHeight;
    }
    public void setWidth(int width1) {
        width = width1;
    }
    public int getWidth() {
        return width;
    }
    public int calculateLineWidth() {
        int lineWidth = 0;
        for (CharFontPair charFontPair : charFontLine) {
            char currChar = charFontPair.getChar().getCH();
            Font currFont = charFontPair.getCharFont();
            FontPair fontPair = FontInfo.findFont(currFont);
            int charWidth = fontPair.getFontMetrics().charWidth(currChar);
            lineWidth += charWidth;
        }
        width = lineWidth;
        return width;
    }
    public Font getFont(int pos) {
        CharFontPair l = charFontLine.get(pos);
        return l.getCharFont();
    }
    public void setFont(int pos, Font font) {
        CharFontPair l = charFontLine.get(pos);
        l.setCharFont(font);
    }
}

class CharFontPair {
    CharFontPair(Character c, Font f) {
        character = c;
        charFont = f;
    }
    private TextBox.dom.Character character;
    private Font charFont;
    void setChar(Character c) {
        character = c;
    }
    Character getChar() {
        return character;
    }
    void setCharFont(Font f) {
        charFont = f;
    }
    Font getCharFont() {
        return charFont;
    }
}
