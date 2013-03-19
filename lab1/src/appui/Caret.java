package appui;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 17.03.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class Caret {
    private static Caret _instance = new Caret();
    private int pos;
    private int line;
    private char caretSymbol = '|';

    static final int PLUS_ONE_CHAR = 1;
    static final int MINUS_ONE_CHAR = -1;

    private Caret() {
        //Instance
    }
    public static Caret Instance() {
        return _instance;
    }

    // Interface

    public void setPos(int p) {
        pos = p;
    }
    public void setLine(int l) {
        line = l;
    }
    public int getPos() {
        return pos;
    }
    public int getLine() {
        return  line;
    }
    public Caret changePos(int count) {
        pos += count;
        return this;
    }
    public Caret changeLine(int count) {
        line += count;
        return this;
    }
    public char getCaretSymbol() {
        return caretSymbol;
    }
}
