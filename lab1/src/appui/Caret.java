package appui;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 17.03.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class Caret {
    private static Caret _instance;
    private int pos;
    private int line;
    public Caret() {
        Instance();
    }
    public Caret Instance() {
        if (_instance == null)
        {
            _instance = new Caret();
            pos = 0;
            line = 0;
        }
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
}
