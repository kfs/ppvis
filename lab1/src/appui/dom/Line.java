package appui.dom;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 18.03.13
 * Time: 17:38
 * To change this template use File | Settings | File Templates.
 */
public class Line extends Glyph {
    private Vector<Glyph> line;
    private int pos;

    public Line(int _pos) {pos = _pos;}
    //public int getPos() {return pos;}
    public int getCountOfChars() {
        return line.size();
    }
}
