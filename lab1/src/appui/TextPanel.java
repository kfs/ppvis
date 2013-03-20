package appui;

import appui.dom.*;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
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
    private Document document = new Document();

    private Caret caret = Caret.Instance();

    private Map<String, Font> fontMap = new HashMap<String, Font>();

    private Font currentFont = new Font("Arial", Font.PLAIN, 15);

    //private Font caretFont = new Font()

    static Map<Integer, appui.dom.Character> _chars = new HashMap<Integer, appui.dom.Character>();
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
        String buf = new String();


        final int horisontalIndent = 2;
        int indentX = 0;
        int indentY = 0;
        int caretIndentX = 0;
        final int margin = 1;
        Graphics2D g2 = (Graphics2D) g;
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds;
        FontMetrics metrics = g.getFontMetrics();

        g2.setFont(currentFont);
        for (int j = 0; j < document.getCountOfLines(); j++, indentY += 15) {
            indentX = 0;
                ///indentY += 15; in for loop
            Line line = document.getLineAt(j);

                //Graphics2D g2 = (Graphics2D) g;
                //g2.drawString(new String("|"), Document.DEFAULT_INDENT_X + indentX, Document.DEFAULT_INDENT_Y);
            for(int i = 0; i < line.getCountOfChars(); i++) {
                //buf += line.getCharAt(i).getCH();
                buf = Character.toString(line.getCharAt(i).getCH());
                bounds = g2.getFont().getStringBounds(buf, context);


                g2.drawString(buf, Document.DEFAULT_INDENT_X+indentX, Document.DEFAULT_INDENT_Y+indentY);
                indentX += metrics.charWidth(line.getCharAt(i).getCH()) + margin;
                if(j == caret.getLine() && i+1 == caret.getPos()) {
                    caretIndentX = indentX - margin;
                }
            }
        }
        Color defaultFC = g2.getColor();
        Font defaultFont = g2.getFont();
        g2.setFont(currentFont);

        g2.setColor(Color.LIGHT_GRAY);
        g2.drawString(Character.toString(caret.getCaretSymbol()), Document.DEFAULT_INDENT_X + caretIndentX, Document.DEFAULT_INDENT_Y + caret.getLine() * 15);
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

    public void keyPressedWithValue(appui.dom.Character character) {
        document.insert(caret.getLine(), caret.getPos(), character);
        caret.changePos(Caret.PLUS_ONE_CHAR);
    }
    public void charDelete() {
        if(caret.getLine() != 0 && caret.getPos() == 0 && document.getLineAt(caret.getLine()).getCountOfChars() != 0) {
            /// concat prev and current lines here
            caret.setPos(document.getLineAt(caret.getLine() - 1).getCountOfChars());
            document.getLineAt(caret.getLine()-1).concatLines(document.getLineAt(caret.getLine()));
            document.deleteLineAt(caret.getLine());
            caret.changeLine(Caret.MINUS_ONE_CHAR);
        }
        else if(caret.getPos() == 0 && caret.getLine() != 0) {
            document.deleteLineAt(caret.getLine());
            caret.changeLine(Caret.MINUS_ONE_CHAR);
            caret.setPos(document.getLineAt(caret.getLine()).getCountOfChars());
        }
        else if(caret.getLine() == 0 && caret.getPos() == 0) {
            return;
        }
        else {
            document.getLineAt(caret.getLine()).deleteCharAt(caret.getPos() - 1);
            caret.changePos(Caret.MINUS_ONE_CHAR);
        }
        repaint();
    }
    public void newLine() {
        document.insertLineAt(caret.getLine());
        if(caret.getPos() == document.getLineAt(caret.getLine()).getCountOfChars()) {
            caret.changeLine(Caret.PLUS_ONE_CHAR);
            caret.setPos(0);
        }
        else {
            /// situation where caret isn't situated at the end of line
            document.getLineAt(caret.getLine()).moveCharsFromTo(caret.getPos(), document.getLineAt(caret.getLine()).getCountOfChars(), document.getLineAt(caret.getLine() + 1));
            caret.changeLine(Caret.PLUS_ONE_CHAR);
            caret.setPos(0);
        }
        repaint();
    }
    public void changeCaretPos(int count) {
        if(caret.isSetSingleOut()) caret.setSingleOutFlag(false);
        caret.changePos(count);
        repaint();
    }
    public void  changeCaretLine(int count) {
        if(caret.isSetSingleOut()) caret.setSingleOutFlag(false);
        caret.changeLine(count);
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
        caret.setSingleOutEndPos(caret.getPos());
        repaint();
    }
}
