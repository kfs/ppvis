package TextBox.util;

import TextBox.dom.TextBoxConstants;

public class Caret {
    private static Caret _instance = new Caret();
    private int pos;
    private int visiblePos = TextBoxConstants.VISIBLE_POS_NOT_USED;
    private int line;
    private char caretSymbol = '|';
    private int singleOutStartPos;
    private int singleOutEndPos;
    private int singleOutStartLine;
    private int singleOutEndLine;
    private boolean singleOutFlag;

    private Caret() {
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
        return pos;
    }
    public int getLogicalPosition() {
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
        visiblePos = TextBoxConstants.VISIBLE_POS_NOT_USED;
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
    public void setPosAtSelectionLogicalStart() {
        if(isSetSingleOut()) {
            if(singleOutStartLine != singleOutEndLine) {
                setLine(singleOutStartLine < singleOutEndLine ? singleOutStartLine : singleOutEndLine);
                setPos(singleOutStartLine < singleOutEndLine ? singleOutStartPos : singleOutEndPos);
            }
            else {
                setLine(singleOutStartLine);
                setPos(singleOutStartPos < singleOutEndPos ? singleOutStartPos : singleOutEndPos);
            }

        }
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
