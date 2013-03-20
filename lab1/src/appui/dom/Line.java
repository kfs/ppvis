package appui.dom;

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
    private Vector<Character> line = new Vector<Character>();
    private int pos;
    private int maxHeight;
    private int posY;

    public Line() {/*pos = _pos;*/}
    public Line(int posY) {
        this.posY = posY;
    }
    //public int getPos() {return pos;}
    public int getCountOfChars() {
        return line.size();
    }
    public Character getCharAt(int pos) {
        return line.elementAt(pos);
    }
    public void insertCharAt(int pos, Character character) {
        line.add(pos, character);

    }
    public void deleteCharAt(int pos) {
        line.remove(pos);
    }
    public void moveCharsFromTo(int from, int to, Line toLine) {
        //Line temp = new Line();
        //Collections.copy(this.line, toLine.line);
        toLine.line = (Vector<Character>)line.clone();
        toLine.line.subList(0, from).clear();
        line.subList(from, to).clear();
    }
    public int getMaxHeight() {
        // ... calculate maxHeight -  height(H) of each char in the line - maxH = H > maxH ? H : maxH; ...
        return maxHeight;
    }
    public void setPosY(int posY) {
        this.posY = posY;
    }
    public int getPosY() {
        return posY;
    }
}
