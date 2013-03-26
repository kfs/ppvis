/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 13.03.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */

package appui;

import appui.util.SFontFrame;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;

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
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        SFontFrame WnDFONT = new SFontFrame();
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

        ImageIcon iNew = new ImageIcon("cut.png");

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);

        // Menu item FILE

        final JMenu file = new JMenu("File");
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
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.add(new JButton("asf"));

        //

        /*
         * Panel with text editor
         */
        final TextPanel panel = new TextPanel();
        panel.setFocusable(true);
        panel.setBackground(Color.WHITE);
        panel.requestFocusInWindow();
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Caret caret = Caret.Instance();
                switch (e.getKeyChar()) {
                    case '\b':
                        panel.charDelete(); //handle caret selection
                        if(caret.isSetSingleOut())
                            caret.setSingleOutFlag(false);
                        break;
                    case '\u007F':
                        //just for test
                        //panel.changeCaretPos(3);
                        //just for test
                        break;
                    case '\n':
                        panel.newLine(); //handle caret selection
                        if(caret.isSetSingleOut())
                            caret.setSingleOutFlag(false);
                        break;
                    default:
                        /*for (Map.Entry<Integer, appui.dom.Character> entry: panel._chars.entrySet()) {
                            if(entry.getKey() == (int) e.getKeyChar()) {
                                panel.keyPressedWithValue(entry.getValue());
                                //panel.paint(panel.getGraphics());
                                panel.repaint();
                                return;
                            }
                        }*/
                        appui.dom.Character character = panel._chars.get((int) e.getKeyChar());
                        if(character == null) {appui.dom.Character c = new appui.dom.Character(e.getKeyChar()); panel._chars.put((int)e.getKeyChar(), c); character=c;}

                       // panel._chars.put( (int)e.getKeyChar(), c);
                        panel.keyPressedWithValue(character);
                        panel.repaint();
                }
            }
            @Override
            public void keyPressed(KeyEvent e) {
                // VK_RIGHT, VK_LEFT, VK_TOP, VK_DOWN events HERE.
                if(e.isShiftDown()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            panel.singleOutLine(Caret.MINUS_ONE_CHAR);
                            break;
                        case KeyEvent.VK_DOWN:
                            panel.singleOutLine(Caret.PLUS_ONE_CHAR);
                            break;
                        case KeyEvent.VK_LEFT:
                            panel.singleOutPos(Caret.MINUS_ONE_CHAR);
                            break;
                        case KeyEvent.VK_RIGHT:
                            panel.singleOutPos(Caret.PLUS_ONE_CHAR);
                            break;
                    }
                }
                else {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            panel.changeCaretLine(Caret.MINUS_ONE_CHAR);
                            break;
                        case KeyEvent.VK_DOWN:
                            panel.changeCaretLine(Caret.PLUS_ONE_CHAR);
                            break;
                        case KeyEvent.VK_LEFT:
                            panel.changeCaretPos(Caret.MINUS_ONE_CHAR);
                            break;
                        case KeyEvent.VK_RIGHT:
                            panel.changeCaretPos(Caret.PLUS_ONE_CHAR);
                            break;
                    }
                }
            }
        });
        panel.add(toolBar, BorderLayout.PAGE_START);
        add(panel);

    }
    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 500;
}
