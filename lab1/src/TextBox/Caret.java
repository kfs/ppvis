package TextBox;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 17.03.13
 * Time: 11:56
 * To change this template use File | Settings | File Templates.
 */
public class Caret {
    protected static Caret _instance = new Caret();
    protected int pos;
    protected int visiblePos = -1;
    protected int line;
    protected char caretSymbol = '|';
    protected int singleOutStartPos;
    protected int singleOutEndPos;
    protected int singleOutStartLine;
    protected int singleOutEndLine;
    protected boolean singleOutFlag;

    static final int PLUS_ONE_CHAR = 1;
    static final int MINUS_ONE_CHAR = -1;
    static final int POS_AT_THE_BEGIN_OF_LINE = 0;

    protected Caret() {
        //Instance
    }
    public static Caret Instance() {
        return _instance;
    }

    // Interface
    public char getCaretSymbol() {
        return caretSymbol;
    }
    public void setPos(int p) {
        pos = p;
    }
    public void setLine(int l) {
        line = l;
    }
    public int getPos() {
        if(visiblePos < 0)
            return pos;
        else
            return visiblePos;
    }
    public int getLine() {
        return  line;
    }
    // navigation
    public Caret changePos(int count) {
        pos += count;
        return this;
    }
    public Caret changeLine(int count) {
        line += count;
        return this;
    }
    public void updateVisiblePos() {
        pos = visiblePos;
        visiblePos = -1;
    }
    public int getVisiblePos() {
        return visiblePos;
    }
    public void setVisiblePos(int vPos) {
        visiblePos = vPos;
    }

    // text selection
    public boolean isSetSingleOut() {
        return singleOutFlag;
    }
    public void setSingleOutFlag(boolean state) {
        singleOutFlag = state;
    }
    public void setSingleOutStartPos(int soPos) {
        singleOutStartPos = soPos;
    }
    public void setSingleOutEndPos(int soPos) {
        singleOutEndPos = soPos;
    }
    public void setSingleOutStartLine(int soLine) {
        singleOutStartLine = soLine;
    }
    public void setSingleOutEndLine(int soLine) {
        singleOutEndLine= soLine;
    }
    public int getSelectionStartPos() {
        if(singleOutStartLine==singleOutEndLine) {
            if(isSetSingleOut())
                return singleOutStartPos < singleOutEndPos ? singleOutStartPos : singleOutEndPos;
            else return singleOutStartPos;
        }
        else return singleOutEndLine > singleOutStartLine ? singleOutStartPos : singleOutEndPos;
    }
    public int getSelectionStartLine() {
        return singleOutEndLine < singleOutStartLine ? singleOutEndLine : singleOutStartLine;
    }
    public int getSelectionEndPos() {
        if(singleOutStartLine==singleOutEndLine) {
            return singleOutStartPos > singleOutEndPos ? singleOutStartPos : singleOutEndPos;
        }
        else return singleOutEndLine > singleOutStartLine ? singleOutEndPos : singleOutStartPos;
    }
    public int getSelectionEndLine() {
        return singleOutEndLine > singleOutStartLine ? singleOutEndLine : singleOutStartLine;
    }
    public boolean isSelectionActual() {
        return !(singleOutStartPos == singleOutEndPos && singleOutStartLine == singleOutEndLine);
    }
}
