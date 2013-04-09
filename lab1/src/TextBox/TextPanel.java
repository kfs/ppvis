package TextBox;

import TextBox.dom.*;
import TextBox.util.Caret;
import TextBox.util.FontInfo;
import TextBox.util.FontPair;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.lang.Character;
import org.apache.log4j.Logger;

public class TextPanel extends JPanel {

    public static final Logger LOGGER = Logger.getLogger(TextPanel.class);
    /*
     * Constants
     */

    public static int DEFAULT_TEXT_PANEL_HEIGHT = 450;
    public static int DEFAULT_TEXT_PANEL_WIDTH = 550;

    /*
     * Vars
     */
    private Document document = new Document();

    private Caret caret = Caret.Instance();

    private Font currentFont = new Font("Serif", Font.PLAIN, 15);

    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    private Rectangle caretRectangle;

    private boolean scrollNeedUpdate;

    private String fileName = "Document";

    /*
     * Other methods
     */

    public TextPanel() {
        //setAutoscrolls(true);
        //setTitle(fileName + " - Text Editor, v0.4 beta.");

        //initFontInfo();
//        getGraphics().setFont(currentFont);
    }

    public void initFontInfo() {
        setFont(currentFont);
        FontInfo.setGraphics(getGraphics());
        FontInfo.findFont(currentFont);
    }

    /*private static final long serialVersionUID = 1L;
    JButton button1 = new JButton("Yellow");
    JButton button2 = new JButton("Black");

    class BtnClickAction implements ActionListener {
        public BtnClickAction(Color c) {
            bgColor = c;
        }
        public void actionPerformed(ActionEvent Event) {
            setBackground(bgColor);
        }
        public Color bgColor;
    }*/

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        String charForDraw;
        int indentX;
        int indentY = 0;
        int caretIndentX = 0;
        int caretIndentY;

        g2.setFont(currentFont);
        FontMetrics metrics = g2.getFontMetrics();
        Font caretFont = TextBoxConstants.DEFAULT_FONT;
        Color selectionColor = new Color(137, 189, 245);

        caretIndentY = metrics.getHeight();

        int lineHeight;
        int selectionIndent = 0;
        int caretHeight = caretIndentY;
        for (int lineNo = 0; lineNo < document.getCountOfLines(); lineNo++) {
            indentX = 0;

            Line line = document.getLineAt(lineNo);
            lineHeight = line.getMaxHeight();
            indentY += lineHeight;

            for(int charNo = 0; charNo < line.getCountOfChars(); charNo++) {
                FontPair fontPair = FontInfo.findFont(line.getFont(charNo));
                g2.setFont(fontPair.getFont());
                metrics = fontPair.getFontMetrics();
                int baselineIndent = metrics.getLeading() + metrics.getDescent();

                if (selectionIndent < baselineIndent) {
                    selectionIndent = baselineIndent;
                }
            }

            for(int charNo = 0; charNo < line.getCountOfChars(); charNo++) {
                //buf += line.getCharAt(charNo).getCH();

                TextBox.dom.Character currentCharacter = line.getCharAt(charNo);
                char currentChar = currentCharacter.getCH();
                charForDraw = Character.toString(currentChar);
                //g2.setFont(line.getFont(charNo));
                FontPair fontPair = FontInfo.findFont(line.getFont(charNo));
                g2.setFont(fontPair.getFont());
                metrics = fontPair.getFontMetrics();
                //g2.setFont(new Font("Serif", 0, 14));
                //bounds = g2.getFont().getStringBounds(buf, context);
                //FontRenderContext context = g2.getFontRenderContext();
                //Rectangle2D bounds = f.getStringBounds(buf, context);
                //Rectangle rectangle = new Rectangle(TextBoxConstants.HORIZONTAL_TEXT_INDENT + indentX, /*Document.DEFAULT_INDENT_Y + indentY*/0, metrics.charWidth(line.getCharAt(charNo).getCH()), 15);
                //Rectangle2D backgroundColRect = new Rectangle(TextBoxConstants.HORIZONTAL_TEXT_INDENT + indentX, Document.DEFAULT_INDENT_Y + indentY, metrics.charWidth(line.getCharAt(charNo).getCH()), caret.getLine()*15);
                if(isCharSelected(charNo, lineNo)) {
                    Color two = g2.getColor();
                    g2.setColor(selectionColor);
                    g2.fillRect(TextBoxConstants.HORIZONTAL_TEXT_INDENT + indentX,
                                indentY - lineHeight + selectionIndent,
                                metrics.charWidth(currentChar),
                                lineHeight
                                );
                    g2.setColor(two);
                }
                //metrics.str//
                g2.drawString(charForDraw, TextBoxConstants.HORIZONTAL_TEXT_INDENT + indentX, indentY);
                //g2.drawString("the most", TextBoxConstants.HORIZONTAL_TEXT_INDENT, Document.DEFAULT_INDENT_Y+15);
                //indentX += metrics.charWidth(currentChar)+margin/2;
                indentX += metrics.charWidth(currentChar);

                if(lineNo == caret.getLine() && charNo + TextBoxConstants.NEXT_POS_MARKER == caret.getLogicalPosition()) {
                    caretIndentX = indentX/* - 2*margin;0*/;
                    caretFont = fontPair.getFont();
                    caretHeight = metrics.getHeight();
                }
            }
            if(lineNo == caret.getLine()) {
                caretIndentY = indentY;
            }
        }
        Color defaultFC = g2.getColor();
        Font defaultFont = g2.getFont();
        g2.setFont(caretFont);
        caretRectangle = new Rectangle( TextBoxConstants.HORIZONTAL_TEXT_INDENT + caretIndentX - 15 - TextBoxConstants.HORIZONTAL_TEXT_MARGIN, caretIndentY - caretHeight, TextBoxConstants.DEFAULT_HORIZONTAL_SCROLL_INDENT, caretHeight * 2);
        //g2.drawRect( TextBoxConstants.HORIZONTAL_TEXT_INDENT + caretIndentX - 15 - TextBoxConstants.HORIZONTAL_TEXT_MARGIN, caretIndentY - caretHeight, TextBoxConstants.DEFAULT_HORIZONTAL_SCROLL_INDENT, caretHeight * 2);
        g2.setColor(Color.RED);
        g2.drawString(Character.toString(caret.getCaretSymbol()), TextBoxConstants.HORIZONTAL_TEXT_INDENT + caretIndentX - TextBoxConstants.HORIZONTAL_TEXT_MARGIN, caretIndentY);

        g2.setColor(defaultFC);
        g2.setFont(defaultFont);
       // updateScrollPane(caretRectangle);
        checkScrollUpdate();
    }

    public void keyPressedWithValue(TextBox.dom.Character character) {
        if(caret.getVisiblePos() != TextBoxConstants.VISIBLE_POS_NOT_USED)
            caret.updateVisiblePos();
        if(caret.isSetSingleOut()) {
            document.cutSelectedString(caret.getSelectionStartPos(),
                    caret.getSelectionStartLine(),
                    caret.getSelectionEndPos(),
                    caret.getSelectionEndLine()
            );
            caret.setPosAtSelectionLogicalStart();
            caret.setSingleOutFlag(false);
        }
        document.insert(caret.getLine(), caret.getPos(), character, currentFont);
        caret.changePos(TextBoxConstants.NEXT_POS_MARKER);
        document.calculateLineWidth(caret.getLine());

        //updateScrollPane(caretRectangle);
        //checkScrollUpdate();
        scrollNeedUpdate = true;
    }
    public void charDelete() {
        if(caret.isSetSingleOut()) {
            document.cutSelectedString(caret.getSelectionStartPos(),
                    caret.getSelectionStartLine(),
                    caret.getSelectionEndPos(),
                    caret.getSelectionEndLine()
            );
            caret.setPosAtSelectionLogicalStart();
            caret.setSingleOutFlag(false);
            scrollNeedUpdate = true;
            repaint();
            return;
        }
        //isCaretAtMiddleOrEndOfLine
        if(caret.getLine() != TextBoxConstants.FIRST_LINE_POS && caret.getPos() == TextBoxConstants.FIRST_SYMBOL_POS && document.getLineAt(caret.getLine()).getCountOfChars() != 0) {
            /// concat prev and current lines here
            caret.setPos(document.getLineAt(caret.getLine() + TextBoxConstants.PREV_POS_MARKER).getCountOfChars());
            document.getLineAt(caret.getLine()+ TextBoxConstants.PREV_POS_MARKER).concatLines(document.getLineAt(caret.getLine()));
            document.deleteLineAt(caret.getLine());
            caret.changeLine(TextBoxConstants.PREV_POS_MARKER);
        }
        //isCaretAtBeginOfNotFirstLine
        else if(caret.getPos() == TextBoxConstants.FIRST_SYMBOL_POS && caret.getLine() != TextBoxConstants.FIRST_LINE_POS) {
            document.deleteLineAt(caret.getLine());
            caret.changeLine(TextBoxConstants.PREV_POS_MARKER);
            caret.setPos(document.getLineAt(caret.getLine()).getCountOfChars());
        }
        else if(caret.getLine() == TextBoxConstants.FIRST_LINE_POS && caret.getPos() == TextBoxConstants.FIRST_SYMBOL_POS) {
            return;
        }
        else {
            document.getLineAt(caret.getLine()).deleteCharAt(caret.getPos() + TextBoxConstants.PREV_POS_MARKER);
            caret.changePos(TextBoxConstants.PREV_POS_MARKER);
        }
        document.calculateLineWidth(caret.getLine());

        scrollNeedUpdate = true;
        repaint();
        //updateScrollPane(caretRectangle);
    }
    public void newLine() {
        document.insertLineAt(caret.getLine());
        if(caret.getPos() == document.getLineAt(caret.getLine()).getCountOfChars()) {
            caret.changeLine(TextBoxConstants.NEXT_POS_MARKER);
            caret.setPos(TextBoxConstants.FIRST_SYMBOL_POS);
        }
        else {
            /// situation where caret isn't situated at the end of line
            document.getLineAt(caret.getLine()).moveCharsFromTo(caret.getPos(), document.getLineAt(caret.getLine()).getCountOfChars(), document.getLineAt(caret.getLine() + 1));
            caret.changeLine(TextBoxConstants.NEXT_POS_MARKER);
            caret.setPos(TextBoxConstants.FIRST_SYMBOL_POS);
        }

        scrollNeedUpdate = true;
        repaint();
        //updateScrollPane(caretRectangle);
    }
    public void changeCaretPos(int count) {
        if(caret.getVisiblePos() != TextBoxConstants.VISIBLE_POS_NOT_USED)
            caret.updateVisiblePos();

        if(caret.isSetSingleOut()) {
            caret.setSingleOutFlag(false);
            if((caret.getSelectionStartPos() > caret.getPos() && count > 0) || (caret.getSelectionStartPos() < caret.getPos() && count < 0))
                caret.setPos(caret.getSelectionStartPos());
            repaint();
            return;
        }

        changeCaretPosBorders(count);

        scrollNeedUpdate = true;
        repaint();
        //updateScrollPane(caretRectangle);
    }
    private void changeCaretPosBorders(int count) {
        // left

        if(caret.getPos() + count >= TextBoxConstants.FIRST_SYMBOL_POS && caret.getPos() + count <= document.getLineAt(caret.getLine()).getCountOfChars())
            // right
            caret.changePos(count);
        else if(caret.getPos() + count < TextBoxConstants.FIRST_SYMBOL_POS && caret.getLine() > TextBoxConstants.FIRST_LINE_POS) {
            caret.changeLine(TextBoxConstants.PREV_POS_MARKER);
            caret.setPos(document.getLineAt(caret.getLine()).getCountOfChars());
        }
        else if(caret.getPos() + count > document.getLineAt(caret.getLine()).getCountOfChars() && caret.getLine() + TextBoxConstants.NEXT_POS_MARKER < document.getCountOfLines()) {
            caret.changeLine(TextBoxConstants.NEXT_POS_MARKER);
            caret.setPos(TextBoxConstants.FIRST_SYMBOL_POS);
        }
    }
    public void changeCaretLine(int count) {
        if(caret.isSetSingleOut()) caret.setSingleOutFlag(false);
        //caret.changeLine(count);
        changeCaretLineBorders(count);

        scrollNeedUpdate = true;
        repaint();
//        updateScrollPane(caretRectangle);
    }
    private void changeCaretLineBorders(int count) {
        //up
        //up when 1st(0) line
        if(count < 0 && caret.getLine() == TextBoxConstants.FIRST_LINE_POS)
            return;
            //down when last line
        else if(count > 0 && caret.getLine() == document.getCountOfLines() + TextBoxConstants.LAST_POS_ERROR)
            return;
            //caret at the middle of lines
        else {
            // all good
            if(caret.getLine() + count <= document.getCountOfLines() + TextBoxConstants.LAST_POS_ERROR && caret.getLine() + count >= TextBoxConstants.FIRST_LINE_POS) {
                caret.changeLine(count);
            } // up -  more than exists
            else if(count < 0) {
                caret.setLine(TextBoxConstants.FIRST_LINE_POS);
            } // down - more than exists
            else {
                caret.setLine(document.getCountOfLines() + TextBoxConstants.LAST_POS_ERROR);
            }
        }
        int currentLine = caret.getLine();
        int countOfChars = document.getLineAt(currentLine).getCountOfChars();
        if(caret.getPos() > countOfChars)
            caret.setVisiblePos(countOfChars);
        else
            caret.setVisiblePos(TextBoxConstants.VISIBLE_POS_NOT_USED);
        //down
    }
    public void singleOutPos(int count) {
        if(!caret.isSetSingleOut()) {
            caret.setSingleOutFlag(true);
            caret.setSingleOutStartPos(caret.getPos());
            caret.setSingleOutStartLine(caret.getLine());
            caret.setSingleOutEndLine(caret.getLine());
        }
        //caret.changePos(count);
        if(caret.getVisiblePos() != TextBoxConstants.VISIBLE_POS_NOT_USED)
            caret.updateVisiblePos();
        changeCaretPosBorders(count);
        caret.setSingleOutEndPos(caret.getPos());
        caret.setSingleOutEndLine(caret.getLine());
        caret.setSingleOutFlag(caret.isSelectionActual());
        scrollNeedUpdate = true;
        repaint();
    }
    public void singleOutLine(int count) {
        if(!caret.isSetSingleOut()) {
            caret.setSingleOutFlag(true);
            caret.setSingleOutStartPos(caret.getPos());
            caret.setSingleOutStartLine(caret.getLine());
            caret.setSingleOutEndPos(caret.getPos());
        }
        //caret.changeLine(count);
        changeCaretLineBorders(count);

        caret.setSingleOutEndLine(caret.getLine());
        scrollNeedUpdate = true;
        repaint();
    }
    private boolean isCharSelected(int pos, int line) {
        if(!caret.isSetSingleOut()) return false;

        /*if(isLineFullySelected(line))
            return true;
        else if(isCharAtFirstLineOfSelection(pos, line))
            return true;
        else if(isCharAtLastLineOfSelection(pos, line))
            return true;
        else if(isCharInOneLineSelection(pos, line))
            return true;
        else
        return false;*/

        /*if(isLineFullySelected(line)
            || isCharAtFirstLineOfSelection(pos, line)
            || isCharAtLastLineOfSelection(pos, line)
            || isCharInOneLineSelection(pos, line))
            return true;
        else
            return false;*/

        return isLineFullySelected(line)
                || isCharAtFirstLineOfSelection(pos, line)
                || isCharAtLastLineOfSelection(pos, line)
                || isCharInOneLineSelection(pos, line);
    }
    private boolean isLineFullySelected(int line) {
        return line > caret.getSelectionStartLine()
               && line < caret.getSelectionEndLine();
    }
    private boolean isCharAtFirstLineOfSelection(int pos, int line) {
        int selectionStartPos = caret.getSelectionStartPos();
        int selectionStartLine = caret.getSelectionStartLine();
        int selectionEndLine = caret.getSelectionEndLine();
        return line == selectionStartLine
                    && pos >= selectionStartPos
                    && selectionEndLine > selectionStartLine;
    }
    private boolean isCharAtLastLineOfSelection(int pos, int line) {
        int selectionStartLine = caret.getSelectionStartLine();
        int selectionEndPos = caret.getSelectionEndPos();
        int selectionEndLine = caret.getSelectionEndLine();
        return line == selectionEndLine
                    && pos < selectionEndPos
                    && selectionStartLine < selectionEndLine;
    }
    private boolean isCharInOneLineSelection(int pos, int line) {
        int selectionStartPos = caret.getSelectionStartPos();
        int selectionStartLine = caret.getSelectionStartLine();
        int selectionEndPos = caret.getSelectionEndPos();
        int selectionEndLine = caret.getSelectionEndLine();
        return  selectionStartLine == selectionEndLine
                && line == selectionStartLine
                && pos >= selectionStartPos
                && pos < selectionEndPos;
    }
    //font commands
    public void updateFont(Font font) {
        if(caret.isSetSingleOut()) {
            int startLineNum = caret.getSelectionStartLine();
            int startPosNum = caret.getSelectionStartPos();
            int endLineNum = caret.getSelectionEndLine();
            int endPosNum = caret.getSelectionEndPos();
            Line currLine;

            if(startLineNum == endLineNum && startPosNum == endPosNum) {
                caret.setSingleOutFlag(false);
                currentFont = font;
                return;
            }

            for(int currLineNum = startLineNum; currLineNum <= endLineNum; currLineNum++ ) {
                currLine = document.getLineAt(currLineNum);
                int countOfChars = currLine.getCountOfChars();
                for(int currPosNum = currLineNum == startLineNum ? startPosNum : 0 ;
                    currPosNum < (currLineNum == endLineNum ? endPosNum : countOfChars) ;
                    currPosNum++) {
                    currLine.setFont(currPosNum, font);
                }
                currLine.calculateLineWidth();
                currLine.calculateLineHeight();
            }
            scrollNeedUpdate = true;
            repaint();
        }
        else
            currentFont = font;
    }
    //clipboard commands
    public void copyToClipboard() {
        String copyStr = document.copySelectedString(caret.getSelectionStartPos(),
                                                     caret.getSelectionStartLine(),
                                                     caret.getSelectionEndPos(),
                                                     caret.getSelectionEndLine());
        StringSelection copySelection = new StringSelection(copyStr);
        clipboard.setContents(copySelection, copySelection);
    }
    public void cutToClipboard() {
        String copyStr = document.cutSelectedString(caret.getSelectionStartPos(),
                caret.getSelectionStartLine(),
                caret.getSelectionEndPos(),
                caret.getSelectionEndLine());
        caret.setPos(caret.getSelectionStartPos());
        caret.setSingleOutFlag(false);
        scrollNeedUpdate = true;
        repaint();
        StringSelection copySelection = new StringSelection(copyStr);
        clipboard.setContents(copySelection, copySelection);
    }
    public void pasteFromClipboard() {
        String pasteStr = "";
        Transferable contents = clipboard.getContents(clipboard);
        boolean hasTransferableText = (contents != null)
                                    && contents.isDataFlavorSupported(DataFlavor.stringFlavor);
        if(hasTransferableText) {
            try {
                pasteStr = (String) contents.getTransferData(DataFlavor.stringFlavor);
            }
            catch (UnsupportedFlavorException exception) {
                LOGGER.error(exception);
            }
            catch (IOException exception) {
                LOGGER.error(exception);
            }
            if(!"".equals(pasteStr)) {
                if(caret.isSetSingleOut()) {
                    document.cutSelectedString( caret.getSelectionStartPos(),
                                                caret.getSelectionStartLine(),
                                                caret.getSelectionEndPos(),
                                                caret.getSelectionEndLine()
                                              );
                    caret.setSingleOutFlag(false);
                }
                int[] state = document.pasteSelectedString(caret.getLogicalPosition(), caret.getLine(), pasteStr);
                caret.updateVisiblePos();
                caret.setLine(state[TextBoxConstants.AFTER_PASTE_CARET_LINE]);
                caret.setPos(state[TextBoxConstants.AFTER_PASTE_CARET_POS]);
                scrollNeedUpdate = true;
                repaint();
            }
        }
    }
    public void pasteString(int pos, int line, String string) {
        document.pasteSelectedString(pos, line, string);
    }
    //scroll commands
    public void updateScrollPane() {
        int panelWidth = document.calculateMaxWidthOfLines() + TextBoxConstants.DEFAULT_HORIZONTAL_SCROLL_INDENT;
        int panelHeight = document.calculateMaxHeightOfLines() + TextBoxConstants.DEFAULT_VERTICAL_SCROLL_INDENT;
        Rectangle panelVisibleRect = getVisibleRect();
        int panelVisibleWidth = (int) panelVisibleRect.getWidth() - 10;
        int panelVisibleHeight = (int) panelVisibleRect.getHeight();

        if(isPanelNeedScrollUpdate(panelWidth, panelHeight, panelVisibleWidth, panelVisibleHeight)) {
            int width = panelWidth > panelVisibleWidth ? panelWidth : panelVisibleWidth;

            int height = panelHeight > panelVisibleHeight ? panelHeight : panelVisibleHeight;
            setSize(width, height);
            setPreferredSize(new Dimension(width, height));
        }
        scrollRectToVisible(caretRectangle);
    }
    private boolean isPanelNeedScrollUpdate(int panelWidth, int panelHeight, int panelVisibleWidth, int panelVisibleHeight) {
        return panelWidth > panelVisibleWidth
                //|| (panelWidth > panelVisibleWidth && getWidth() > panelWidth)
                || (panelWidth < getWidth()-10 && panelVisibleWidth < getWidth()-10)
                || panelHeight > getHeight()
                //|| (panelHeight > panelVisibleHeight && panelHeight > getWidth())
                || (panelHeight < getHeight() && panelVisibleHeight < getHeight());
    }
    private void checkScrollUpdate() {
        if(scrollNeedUpdate) {
            updateScrollPane();
            scrollNeedUpdate = false;
            repaint();
        }
    }
    public void newDocument() {
        document = new Document();
        caret.setPos(TextBoxConstants.FIRST_SYMBOL_POS);
        caret.setLine(TextBoxConstants.FIRST_LINE_POS);
        repaint();
    }
    public String getText() {
        int startPos = TextBoxConstants.FIRST_SYMBOL_POS;
        int startLine = TextBoxConstants.FIRST_LINE_POS;
        int lastLine = document.getCountOfLines() + TextBoxConstants.LAST_POS_ERROR;
        int lastPos = document.getLineAt(lastLine).getCountOfChars() + TextBoxConstants.LAST_POS_ERROR;

        return document.copySelectedString(startPos, startLine, lastPos, lastLine);
    }
    public String getLine(int line) {
        int startPos = TextBoxConstants.FIRST_SYMBOL_POS;
        int lastPos = document.getLineAt(line).getCountOfChars();

        return document.copySelectedString(startPos, line, lastPos, line);
    }
    public int getCountOfLines() {
        return document.getCountOfLines();
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setBold() {
        Font font;
        int style;
        if(currentFont.isBold() && currentFont.isItalic())
            style = Font.ITALIC;
        else if(currentFont.isBold())
            style = Font.PLAIN;
        else if(currentFont.isItalic())
            style = Font.ITALIC + Font.BOLD;
        else
            style = Font.BOLD;
        font = new Font(currentFont.getName(), style, currentFont.getSize());
        FontPair fontPair = FontInfo.findFont(font);
        currentFont = fontPair.getFont();
        if(caret.isSetSingleOut()) {
            updateFont(currentFont);
            repaint();
        }
    }
    public void setItalic() {
        Font font;
        int style;
        if(currentFont.isBold() && currentFont.isItalic())
            style = Font.BOLD;
        else if(currentFont.isBold())
            style = Font.ITALIC + Font.BOLD;
        else if(currentFont.isItalic())
            style = Font.PLAIN;
        else
            style = Font.ITALIC;
        font = new Font(currentFont.getName(), style, currentFont.getSize());
        FontPair fontPair = FontInfo.findFont(font);
        currentFont = fontPair.getFont();
        if(caret.isSetSingleOut()) {
            updateFont(currentFont);
            repaint();
        }
    }
    public void setFontName(String fontName) {
        Font font;
        font = new Font(fontName, currentFont.getStyle(), currentFont.getSize());
        FontPair fontPair = FontInfo.findFont(font);
        currentFont = fontPair.getFont();
        if(caret.isSetSingleOut()) {
            updateFont(currentFont);
            repaint();
        }
    }
    public void setFontSize(int fontSize) {
        Font font;
        font = new Font(currentFont.getName(), currentFont.getStyle(), fontSize);
        FontPair fontPair = FontInfo.findFont(font);
        currentFont = fontPair.getFont();
        if(caret.isSetSingleOut()) {
            updateFont(currentFont);
            repaint();
        }
    }
}
