package TextBox;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import TextBox.dom.TextBoxConstants;
import TextBox.util.Caret;
import TextBox.util.CharacterFactory;
import TextBox.util.SFontFrame;

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
        frame.initPanelUI();
    }
}

@SuppressWarnings("serial")
class WndFrame extends JFrame {
    TextPanel textPanel;
    SFontFrame fontChooser = new SFontFrame();
    public WndFrame() {
        /*
         * Set default props
         */



        /*
         * Menu
         */

        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);

        // Menu item FILE

        final JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        menu.add(file);
        JMenuItem mCreate = new JMenuItem("New...", new ImageIcon("imgs/new.png"));
        JMenuItem mOpen = new JMenuItem("Open...", new ImageIcon("imgs/open.png"));
        JMenuItem mSave = new JMenuItem("Save", new ImageIcon("imgs/save.png"));
        mSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        JMenuItem mSaveAs = new JMenuItem("Save As...", new ImageIcon("imgs/saveas.png"));
        mSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
        JMenuItem mExit = new JMenuItem("Exit", new ImageIcon("imgs/exit.png"));
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
        JMenuItem mNewFont = new JMenuItem("Change font");
        mNewFont.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
//        JMenuItem mRedo = new JMenuItem("Redo");
//        mRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        JMenuItem mCut = new JMenuItem("Cut", new ImageIcon("imgs/cut.png"));
        mCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        JMenuItem mCopy = new JMenuItem("Copy", new ImageIcon("imgs/copy.png"));
        mCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        JMenuItem mPaste = new JMenuItem("Paste", new ImageIcon("imgs/paste.png"));
        mPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        edit.add(mNewFont);
//        edit.add(mRedo);
        edit.addSeparator();
        edit.add(mCut);
        edit.add(mCopy);
        edit.add(mPaste);


        final TextPanel panel = new TextPanel();

        /*
         * Toolbar
         */

        JToolBar toolBar = new JToolBar("Toolbar");
        //toolBar.setBackground(Color.cyan);
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.setOrientation(SwingConstants.HORIZONTAL);
        JButton jbtn = new JButton("Choose Font...");
        jbtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                fontChooser.chooseFont(panel);
            }
        });
        jbtn.setFocusable(false);
        toolBar.add(jbtn);

        JButton cutBtn = new JButton(new ImageIcon("imgs/cut.png"));
        cutBtn.setFocusable(false);
        toolBar.add(cutBtn);
        JButton copyBtn = new JButton(new ImageIcon("imgs/copy.png"));
        copyBtn.setFocusable(false);
        toolBar.add(copyBtn);
        JButton pasteBtn = new JButton(new ImageIcon("imgs/paste.png"));
        pasteBtn.setFocusable(false);
        toolBar.add(pasteBtn);
        toolBar.addSeparator();

        JButton openFile = new JButton(new ImageIcon("imgs/open.png"));
        openFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                panel.newDocument();
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileReader fileReader = new FileReader(fileChooser.getSelectedFile());
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        String document = "";
                        String line;
                        while((line = bufferedReader.readLine()) != null) {
                            document += line + '\n';
                            System.out.println(line);
                        }
                        panel.document.pasteSelectedString(TextBoxConstants.FIRST_SYMBOL_POS, TextBoxConstants.FIRST_LINE_POS, document);
                        panel.document.calculateMaxWidthOfLines();
                        panel.updateScrollPane();
                        panel.repaint();
    //                    System.out.println(openFile.toString());
    //                    if(openFile.canRead()) {

                        //}
                    }
                    catch (IOException exception) {
                        System.out.println(exception);
                    }
                }
            }
        });
        openFile.setFocusable(false);
        toolBar.add(openFile);

        JButton saveFile = new JButton(new ImageIcon("imgs/save.png"));
        saveFile.setFocusable(false);
        saveFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ///
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showSaveDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION)
                    try{
                        File saveFile = fileChooser.getSelectedFile();
                        String fileName = fileChooser.getName();
                        String filePath = saveFile.getAbsolutePath();

                        if(!saveFile.exists()){
                            //saveFile.createNewFile();
                            BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
                            out.write("sfasf"/*panel.getText()*/);
                            out.close();
                        }
                        else{
                            String message = "File • " + fileName + " • already exist in \n" + filePath + ":\n" + "Do you want to overwrite?";
                            String title = "Warning";
                            int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                            if(reply == JOptionPane.YES_OPTION){
                                //saveFile.delete();
                                //saveFile.createNewFile();
                                BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
                                out.write("dsdg"/*panel.getText()*/);
                                out.close();
                                JOptionPane.showMessageDialog(null, "File • " + fileName + " • overwritten succesfully in \n" + filePath);

                            }
                        }

                    }
                    catch(IOException exception){
                        System.out.println(exception);
                    }
            }
        });
        toolBar.add(saveFile);

        JButton saveFileAs = new JButton(new ImageIcon("imgs/saveas.png"));
        saveFileAs.setFocusable(false);
        toolBar.add(saveFileAs);
        toolBar.addSeparator();

        JButton fontBold = new JButton(new ImageIcon("imgs/bold.png"));
        fontBold.setFocusable(false);

        toolBar.add(fontBold);

        JButton fontItalic = new JButton(new ImageIcon("imgs/italic.png"));
        fontItalic.setFocusable(false);

        toolBar.add(fontItalic);

        JComboBox fontName = new JComboBox(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
        fontName.setFocusable(false);
        toolBar.add(fontName);

        String fontSizes[] = {"4", "6", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
                "20", "24", "30", "36", "40", "48", "60", "72"};
        JComboBox fontSize = new JComboBox(fontSizes);
        fontSize.setSelectedIndex(9);
        fontSize.setFocusable(false);
        toolBar.add(fontSize);

        //

        /*
         * Panel with text editor
         */


        textPanel = panel;
        panel.setFocusable(true);
        panel.setBackground(Color.WHITE);
        panel.requestFocusInWindow();
        //panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Caret caret = Caret.Instance();
                ///if(!e.isControlDown()) {
                    switch (e.getKeyChar()) {
                        case TextBoxConstants.BACK_SPACE_SYMBOL:
                            panel.charDelete(); //handle caret selection
                            if (caret.isSetSingleOut())
                                caret.setSingleOutFlag(false);
                            break;
                        case TextBoxConstants.DELETE_SYMBOL:
                            //just for test
                            //panel.changeCaretPos(3);
                            //just for test
                            break;
                        case TextBoxConstants.NEW_LINE_SYMBOL:
                            panel.newLine(); //handle caret selection
                            if (caret.isSetSingleOut())
                                caret.setSingleOutFlag(false);
                            break;
                        case TextBoxConstants.CONTROL_X_CUT_SEQUENCE:
                            panel.cutToClipboard();
                            break;
                        case TextBoxConstants.CONTROL_V_PASTE_SEQUENCE:
                            panel.pasteFromClipboard();
                            break;
                        case TextBoxConstants.CONTROL_C_COPY_SEQUENCE:
                            panel.copyToClipboard();
                            break;
                        default:
                            TextBox.dom.Character character = CharacterFactory.newChar(e.getKeyChar());
                            panel.keyPressedWithValue(character);
                            panel.repaint();
                    }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // VK_RIGHT, VK_LEFT, VK_TOP, VK_DOWN events HERE.
                if (e.isShiftDown()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            panel.singleOutLine(TextBoxConstants.PREV_POS_MARKER);
                            break;
                        case KeyEvent.VK_DOWN:
                            panel.singleOutLine(TextBoxConstants.NEXT_POS_MARKER);
                            break;
                        case KeyEvent.VK_LEFT:
                            panel.singleOutPos(TextBoxConstants.PREV_POS_MARKER);
                            break;
                        case KeyEvent.VK_RIGHT:
                            panel.singleOutPos(TextBoxConstants.NEXT_POS_MARKER);
                            break;
                    }
                } else {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            panel.changeCaretLine(TextBoxConstants.PREV_POS_MARKER);
                            break;
                        case KeyEvent.VK_DOWN:
                            panel.changeCaretLine(TextBoxConstants.NEXT_POS_MARKER);
                            break;
                        case KeyEvent.VK_LEFT:
                            panel.changeCaretPos(TextBoxConstants.PREV_POS_MARKER);
                            break;
                        case KeyEvent.VK_RIGHT:
                            panel.changeCaretPos(TextBoxConstants.NEXT_POS_MARKER);
                            break;
                    }
                }
            }
        });
        //panel.add(toolBar, BorderLayout.PAGE_START);
        add(toolBar,BorderLayout.PAGE_START);
        JScrollPane sp = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setBorder(null);
        sp.createHorizontalScrollBar();
        add(sp);
        panel.requestFocusInWindow();
        setTitle(textPanel.fileName + " - Text Editor, v0.5 beta.");
        setSize(TextPanel.DEFAULT_TEXT_PANEL_WIDTH, TextPanel.DEFAULT_TEXT_PANEL_HEIGHT);

    }
    public void initPanelUI() {
        textPanel.initFontInfo();
    }
}
