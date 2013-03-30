package TextBox;

import TextBox.dom.*;
import TextBox.util.FontInfo;
import TextBox.util.FontPair;

import javax.swing.*;
import java.awt.*;
import java.lang.Character;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 18.03.13
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
public class TextPanel extends JPanel implements UIComponent {
    /*
     * Vars
     */
    protected Document document = new Document();

    protected Caret caret = Caret.Instance();

    protected Map<String, Font> fontMap = new HashMap<String, Font>();

    protected Font currentFont = new Font("Serif", Font.PLAIN, 15);

    //private Font caretFont = new Font()

    protected static Map<Integer, TextBox.dom.Character> _chars = new HashMap<Integer, TextBox.dom.Character>();
   // this.key
    /*
     * Overloaded Mehods
     */

    public void resize() {

    }
    public void draw() {

    }

    /*
     * Other methods
     */

    public TextPanel() {
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


        FontMetrics metrics = g.getFontMetrics();
        Font caretFont = TextBoxConstants.DEFAULT_FONT;
        Color selectionColor = new Color(137, 189, 245);

        caretIndentY = metrics.getHeight();
        g2.setFont(currentFont);
        int lineHeight;
        for (int lineNo = 0; lineNo < document.getCountOfLines(); lineNo++) {                    ///!!!!!! lineHeight
            indentX = 0;

            Line line = document.getLineAt(lineNo);
            lineHeight = line.getMaxHeight();
            indentY += lineHeight;

            for(int charNo = 0; charNo < line.getCountOfChars(); charNo++) {
                //buf += line.getCharAt(charNo).getCH();

                TextBox.dom.Character currentCharacter = line.getCharAt(charNo);
                char currentChar = currentCharacter.getCH();
                charForDraw = Character.toString(currentChar);
                g2.setFont(line.getFont(charNo));
                FontPair fontPair = FontInfo.findFont(line.getFont(charNo));
                g2.setFont(fontPair.getFont());
                metrics = fontPair.getFontMetrics();
                //g2.setFont(new Font("Serif", 0, 14));
                //bounds = g2.getFont().getStringBounds(buf, context);
                //FontRenderContext context = g2.getFontRenderContext();
                //Rectangle2D bounds = f.getStringBounds(buf, context);
                //Rectangle rectangle = new Rectangle(TextBoxConstants.HORIZONTAL_TEXT_INDENT + indentX, /*Document.DEFAULT_INDENT_Y + indentY*/0, metrics.charWidth(line.getCharAt(charNo).getCH()), 15);
                //  Rectangle2D backgroundColRect = new Rectangle(TextBoxConstants.HORIZONTAL_TEXT_INDENT + indentX, Document.DEFAULT_INDENT_Y + indentY, metrics.charWidth(line.getCharAt(charNo).getCH()), caret.getLine()*15);
                if(isSelectedChar(charNo, lineNo)) {
                    Color two = g2.getColor();
                    g2.setColor(selectionColor);
                    g2.fillRect(TextBoxConstants.HORIZONTAL_TEXT_INDENT + indentX,
                                indentY - metrics.getHeight() + metrics.getDescent(),
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

                if(lineNo == caret.getLine() && charNo+1 == caret.getLogicalPosition()) {
                    caretIndentX = indentX/* - 2*margin;0*/;
                    caretFont = fontPair.getFont();
                }
            }
            if(lineNo == caret.getLine()) {
                caretIndentY = indentY;
            }
        }
        Color defaultFC = g2.getColor();
        Font defaultFont = g2.getFont();
        g2.setFont(caretFont);

        g2.setColor(Color.RED);
        g2.drawString(Character.toString(caret.getCaretSymbol()), TextBoxConstants.HORIZONTAL_TEXT_INDENT + caretIndentX - TextBoxConstants.HORIZONTAL_TEXT_MARGIN, caretIndentY);
        g2.setColor(defaultFC);
        g2.setFont(defaultFont);



        /*
        Graphics2D g2 = (Graphics2D) g;
        String message = "" + new Date();

        g2.setFont(f);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = f.getStringBounds(message, context);
        float space = (float) bounds.getHeight();
        setBackground(Color.YELLOW);
        double x = (getWidth() - bounds.getWidth()) / 2;
        double y = (getHeight() - bounds.getHeight()) / 2;
        g2.setPaint(Color.RED);
        g2.drawString(message, (float)x, (float)y);
        g2.drawString(message, (float)x+10, (float)y + space);
        //button1.setSize(100, 20);
        //button2.setSize(100, 20);
		/*JDialog jf = new JDialog();
		jf.setVisible(true);
		jf.setSize(300,	100);// */
        // add(button1);
        // add(button2);

        // BtnClickAction action1 = new BtnClickAction(Color.YELLOW);
        //BtnClickAction action2 = new BtnClickAction(Color.BLACK);

        //button1.addActionListener(action1);
        //button2.addActionListener(action2);
    }

    public void keyPressedWithValue(TextBox.dom.Character character) {
        document.insert(caret.getLine(), caret.getPos(), character, currentFont);
        caret.changePos(TextBoxConstants.NEXT_POS_MARKER);
    }
    public void charDelete() {
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
        repaint();
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
        repaint();
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

        repaint();
    }
    public void changeCaretLine(int count) {
        if(caret.isSetSingleOut()) caret.setSingleOutFlag(false);
        //caret.changeLine(count);

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
        repaint();
    }
    public void singleOutPos(int count) {
        if(!caret.isSetSingleOut()) {
            caret.setSingleOutFlag(true);
            caret.setSingleOutStartPos(caret.getPos());
            caret.setSingleOutStartLine(caret.getLine());
            caret.setSingleOutEndLine(caret.getLine());
        }
        caret.changePos(count);
        caret.setSingleOutEndPos(caret.getPos());
        caret.setSingleOutFlag(caret.isSelectionActual());
        repaint();
    }
    public void singleOutLine(int count) {
        if(!caret.isSetSingleOut()) {
            caret.setSingleOutFlag(true);
            caret.setSingleOutStartPos(caret.getPos());
            caret.setSingleOutStartLine(caret.getLine());
            caret.setSingleOutEndPos(caret.getPos());
        }
        caret.changeLine(count);
        caret.setSingleOutEndLine(caret.getLine());
        repaint();
    }
    private boolean isSelectedChar(int pos, int line) {
        if(!caret.isSetSingleOut()) return false;

        if(line > caret.getSelectionStartLine() && line < caret.getSelectionEndLine())
            return true;
        //else if()
        else if(line == caret.getSelectionStartLine() && pos >= caret.getSelectionStartPos() && caret.getSelectionEndLine()>caret.getSelectionStartLine())
            return true;
        else if(line == caret.getSelectionEndLine() && pos < caret.getSelectionEndPos() && caret.getSelectionStartLine()<caret.getSelectionEndLine())
            return true;
        else if(caret.getSelectionStartLine() == caret.getSelectionEndLine() && line == caret.getSelectionStartLine() && pos >= caret.getSelectionStartPos() && pos < caret.getSelectionEndPos())
            return true;
        else
        return false;
    }
    public void updateFont(Font font) {
        currentFont = font;
    }
}
