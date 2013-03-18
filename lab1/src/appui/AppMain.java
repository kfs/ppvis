/**
 * Created with IntelliJ IDEA.
 * User: lgsferry
 * Date: 13.03.13
 * Time: 20:32
 * To change this template use File | Settings | File Templates.
 */

package appui;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

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
        add(toolBar);
        //

        /*
         * Panel with text editor
         */
        final TextPanel panel = new TextPanel();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println("1");
                panel.requestFocusInWindow();
               /* for (Map.Entry<Integer, appui.dom.Character> entry: panel._chars.entrySet()) {
                    if(entry.getKey() == e.getKeyCode()) {
                        panel.keyPressedWithValue(entry.getValue());
                        file.setName("1");
                        return;
                    }
                }
                appui.dom.Character c = new appui.dom.Character(e.getKeyChar());
                panel._chars.put(e.getKeyCode(), c);
                panel.keyPressedWithValue(c);
                file.setName("2");*/
            }
        });

        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                System.out.println("2-1");
                for (Map.Entry<Integer, appui.dom.Character> entry: panel._chars.entrySet()) {
                    if(entry.getKey() == e.getKeyCode()) {
                        panel.keyPressedWithValue(entry.getValue());
                        file.setName("1");
                        return;
                    }
                }
                System.out.println("2-2");
                appui.dom.Character c = new appui.dom.Character(e.getKeyChar());
                panel._chars.put(e.getKeyCode(), c);
                panel.keyPressedWithValue(c);
                file.setName("2");
            }
        });
        panel.requestFocusInWindow();
        add(panel);

    }
    public static final int DEF_WIDTH = 600;
    public static final int DEF_HEIGHT = 500;
}
