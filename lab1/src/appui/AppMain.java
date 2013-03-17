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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.Date;

import javax.swing.*;

public class AppMain {
    public static void main(String[] args) {
        /*try {
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
        }*/
        WndFrame frame = new WndFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

@SuppressWarnings("serial")
class WndFrame extends JFrame {
    public WndFrame() {

        /*
         * Set default props
         */

        setTitle("Text Editor, v0.1 alpha.");
        setSize(DEF_WIDTH, DEF_HEIGHT);

        /*
         * Menu
         */

        ImageIcon iNew = new ImageIcon(getClass().getResource("16796.ico"));

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);

        // Menu item FILE

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        menu.add(file);
        JMenuItem mCreate = new JMenuItem("New...", iNew);
        JMenuItem mOpen = new JMenuItem("Open...");
        JMenuItem mSave = new JMenuItem("Save");
        mSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        JMenuItem mSaveAs = new JMenuItem("Save As...");
        mSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
        JMenuItem mExit = new JMenuItem("Exit");
        file.add(mCreate);
        file.add(mOpen);
        file.addSeparator();
        file.add(mSave);
        file.add(mSaveAs);
        file.addSeparator();
        file.add(mExit);

        // Menu item EDIT

        JMenu edit = new JMenu("Edit");
        menu.add(edit);
        JMenuItem mUndo = new JMenuItem("Undo");
        mUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        JMenuItem mRedo = new JMenuItem("Redo");
        mRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        JMenuItem mCut = new JMenuItem("Cut");
        mCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        JMenuItem mCopy = new JMenuItem("Copy");
        mCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        JMenuItem mPaste = new JMenuItem("Paste");
        mPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        edit.add(mUndo);
        edit.add(mRedo);
        edit.addSeparator();
        edit.add(mCut);
        edit.add(mCopy);
        edit.add(mPaste);

        /*
         * Toolbar
         */

        JToolBar toolBar = new JToolBar("Toolbar");
        add(toolBar);
        //

        /*
         * Panel with text editor
         */

        TextPanel panel = new TextPanel();
        add(panel);
    }
    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 500;
}



class TextPanel extends JPanel implements UIComponent {
    /**
     *
     */
    private Caret caret = new Caret();

    public TextPanel() {

    }

    public void resize() {

    }
    public void draw() {

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
		// /*JDialog jf = new JDialog();
		jf.setVisible(true);
		jf.setSize(300,	100);// /*
        add(button1);
        add(button2);

        BtnClickAction action1 = new BtnClickAction(Color.YELLOW);
        BtnClickAction action2 = new BtnClickAction(Color.BLACK);

        button1.addActionListener(action1);
        button2.addActionListener(action2);
    }*/
}
