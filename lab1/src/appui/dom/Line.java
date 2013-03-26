package appui.dom;

import java.awt.*;
import java.util.Collections;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 18.03.13
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class Line extends Glyph {
    private Vector<CharFontPair> charFontLine = new Vector<CharFontPair>();
    private int pos;
    private int maxHeight;
    private int posY;

    public Line() {/*pos = _pos;*/}
    public Line(int posY) {
        this.posY = posY;
    }
    //public int getPos() {return pos;}
    public int getCountOfChars() {
        return charFontLine.size();
    }
    public Character getCharAt(int pos) {
        CharFontPair l = charFontLine.get(pos);
        return l.getChar();
    }
    public void insertCharAt(int pos, Character character, Font font) {
        charFontLine.add(pos, new CharFontPair(character, font));
    }
    public void deleteCharAt(int pos) {
        charFontLine.remove(pos);
    }
    public void moveCharsFromTo(int from, int to, Line toLine) {
        //Line temp = new Line();
        //Collections.copy(this.line, toLine.line);
        toLine.charFontLine = (Vector<CharFontPair>)charFontLine.clone();
        toLine.charFontLine.subList(0, from).clear();
        charFontLine.subList(from, to).clear();
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
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public int getPosY() {
        return posY;
    }
    public Font getFont(int pos) {
        CharFontPair l = charFontLine.get(pos);
        return l.getCharFont();
    }
}

class CharFontPair {
    CharFontPair() {}
    CharFontPair(Character c, Font f) {
        character = c;
        charFont = f;
    }
    private appui.dom.Character character;
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
