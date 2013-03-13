/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 13.03.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */

package appui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.Date;

import javax.swing.*;

public class AppMain {
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }
        WndFrame frame = new WndFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

@SuppressWarnings("serial")
class WndFrame extends JFrame {
    public WndFrame() {
        setTitle("Text Editor, v0.1 alpha.");
        setSize(DEF_WIDTH, DEF_HEIGHT);


        MPanel panel = new MPanel();

        add(panel);

    }
    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 500;
};



class MPanel extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
    }

    Font f = new Font("Serif", Font.BOLD, 14);

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        String message = "" + new Date();

        g2.setFont(f);
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = f.getStringBounds(message, context);

        double x = (getWidth() - bounds.getWidth()) / 2;
        double y = (getHeight() - bounds.getHeight()) / 2;

        g2.drawString(message, (float)x, (float)y);
        g2.setPaint(Color.GRAY);






        //button1.setSize(100, 20);
        //button2.setSize(100, 20);
		/*JDialog jf = new JDialog();
		jf.setVisible(true);
		jf.setSize(300,	100);*/
        add(button1);
        add(button2);

        BtnClickAction action1 = new BtnClickAction(Color.YELLOW);
        BtnClickAction action2 = new BtnClickAction(Color.BLACK);

        button1.addActionListener(action1);
        button2.addActionListener(action2);
    }
};
