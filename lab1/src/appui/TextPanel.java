package appui;

import appui.dom.*;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.Date;
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

    private Font currentFont = new Font("Serif", Font.BOLD, 14);

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
        Graphics2D g2 = (Graphics2D) g;
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds;
        FontMetrics metrics = g.getFontMetrics();
        //g2.setFont(currentFont);
        if(document.getCountOfLines() != 0 && document.getLineAt(caret.getLine()).getCountOfChars() != 0) {
            Line line = document.getLineAt(caret.getLine());
            //Graphics2D g2 = (Graphics2D) g;
            //g2.drawString(new String("|"), Document.DEFAULT_INDENT_X + indentX, Document.DEFAULT_INDENT_Y);
            for(int i = 0; i < line.getCountOfChars(); i++) {
                //buf += line.getCharAt(i).getCH();
                buf = "" + line.getCharAt(i).getCH();
                bounds = g2.getFont().getStringBounds(buf, context);


                g2.drawString(buf, Document.DEFAULT_INDENT_X+indentX, Document.DEFAULT_INDENT_Y);
                indentX += metrics.charWidth(line.getCharAt(i).getCH());
            }
            g2.drawString("|", Document.DEFAULT_INDENT_X + indentX, Document.DEFAULT_INDENT_Y);


        }


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
}
