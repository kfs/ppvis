package TextBox;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;

import TextBox.dom.TextBoxConstants;
import TextBox.util.Caret;
import TextBox.util.CharacterFactory;
import TextBox.util.fonts.FontConstants;
import TextBox.util.fonts.SFontFrame;
import org.apache.log4j.Logger;

//i18n -\ResourceBundle -\\ascii - \u1212
//log4j
public class AppMain {
    public static String textName;
    public static String docName;
    public static final Logger LOGGER = Logger.getLogger(AppMain.class);
    public static void main(String[] args) {
        setSystemLookAndFeel();
        WndFrame wndFrame = addTextPanel();
        setLocale(args, wndFrame);
    }
    private static void setLocale(String[] args, WndFrame frame) {
        String language;
        String country;
        if(args.length != 2) {
            language = new String("en");
            country = new String("GB");
        } else {
            language = new String(args[0]);
            country = new String(args[1]);
        }

        Locale currentLocale;
        ResourceBundle messages;
        currentLocale = new Locale(language, country);
        messages = ResourceBundle.getBundle("TextBox/locale/MessagesBundle", currentLocale);
        textName = messages.getString(TextBoxConstants.LOCALE_EDITOR_TITLE);
        docName = messages.getString(TextBoxConstants.LOCALE_DOC_TITLE);
        frame.setTitle(docName + " - " + textName);
    }
    private static void setSystemLookAndFeel() {
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            LOGGER.error(e);
        }
        catch (ClassNotFoundException e) {
            LOGGER.error(e);
        }
        catch (InstantiationException e) {
            LOGGER.error(e);
        }
        catch (IllegalAccessException e) {
            LOGGER.error(e);
        }
    }
    static private WndFrame addTextPanel() {
        WndFrame frame = new WndFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.initPanelUI();
        return frame;
    }
}

@SuppressWarnings("serial")
class WndFrame extends JFrame {
    public static final Logger LOGGER = Logger.getLogger(OpenFileAdapter.class);
    TextPanel textPanel;

    SFontFrame fontChooser = new SFontFrame();

    class OpenFileAdapter extends MouseAdapter {
        TextPanel panel;
        OpenFileAdapter(TextPanel panel) {
            this.panel = panel;
        }
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
                    }
                    panel.pasteString(TextBoxConstants.FIRST_SYMBOL_POS, TextBoxConstants.FIRST_LINE_POS, document);
                    //panel.document.calculateMaxWidthOfLines();
                    panel.updateScrollPane();
                    panel.repaint();
                }
                catch (IOException exception) {
                    LOGGER.error(exception);
                }
            }
        }
    }
    class SaveFileAdapter extends MouseAdapter {
        TextPanel panel;
        SaveFileAdapter(TextPanel panel) {
            this.panel = panel;
        }
        @Override
        public void mousePressed(MouseEvent e) {
            ///
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showSaveDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION)
                try{
                    File saveFile = fileChooser.getSelectedFile();
                    String fileName = fileChooser.getName(saveFile);
                    String filePath = saveFile.getAbsolutePath();

                    if(!saveFile.exists()){
                        BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
                        int countOfLines = panel.getCountOfLines();
                        for (int currLine = 0; currLine < countOfLines; currLine++) {
                            out.write(panel.getLine(currLine));
                            out.newLine();
                        }
                        panel.setFileName(fileName);
                        out.close();
                    }
                    else{
                        String message = "File  \'" + fileName + "\' already exist in \n" + filePath + ":\n" + "Do you want to overwrite?";
                        String title = "Warning";
                        int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
                        if(reply == JOptionPane.YES_OPTION){
                            BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
                            int countOfLines = panel.getCountOfLines();
                            for (int currLine = 0; currLine < countOfLines; currLine++) {
                                out.write(panel.getLine(currLine));
                                out.newLine();
                            }
                            out.close();
                            JOptionPane.showMessageDialog(null, "File \'" + fileName + "\' overwritten successfully in \n" + filePath);

                        }
                    }
                }
                catch(IOException exception){
                    LOGGER.error(exception);
                }
        }
    }
    class FontMouseEvent extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            fontChooser.chooseFont(textPanel);
        }
    }
    class NewDocEvent extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            textPanel.newDocument();
        }
    }
    class KeyEventAdapter implements KeyListener {
        TextPanel panel;
        KeyEventAdapter(TextPanel panel) {
            this.panel = panel;
        }
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
        @Override
        public void keyReleased(KeyEvent e) {
        }
    }

    public WndFrame() {
        initTextPanel();
        initMenu();
        initToolBar();
        initScrollPane();
        initDefaultSettings();
    }
    public void initPanelUI() {
        textPanel.initFontInfo();
    }
    private void initTextPanel() {
        textPanel = new TextPanel();
        textPanel.setFocusable(true);
        textPanel.setBackground(Color.WHITE);
        textPanel.requestFocusInWindow();
        //textPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        textPanel.addKeyListener(new KeyEventAdapter(textPanel));
        //textPanel.add(toolBar, BorderLayout.PAGE_START);

    }
    class CutEvent extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            textPanel.cutToClipboard();
        }
    }
    class CopyEvent extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            textPanel.copyToClipboard();
        }
    }
    class PasteEvent extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            textPanel.pasteFromClipboard();
        }
    }
    private void initMenu() {
        JMenuBar menu = new JMenuBar();
        setJMenuBar(menu);

        // Menu item FILE

        final JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        menu.add(file);
        JMenuItem mCreate = new JMenuItem("New...", new ImageIcon("imgs/new.png"));
        mCreate.addMouseListener(new NewDocEvent());
        JMenuItem mOpen = new JMenuItem("Open...", new ImageIcon("imgs/open.png"));
        mOpen.addMouseListener(new OpenFileAdapter(textPanel));
        JMenuItem mSave = new JMenuItem("Save", new ImageIcon("imgs/save.png"));
        mSave.addMouseListener(new SaveFileAdapter(textPanel));
        mSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        JMenuItem mSaveAs = new JMenuItem("Save As...", new ImageIcon("imgs/saveas.png"));
        mSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
        mSaveAs.addMouseListener(new SaveFileAdapter(textPanel));
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
        mNewFont.addMouseListener(new FontMouseEvent());
        mNewFont.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
//        JMenuItem mRedo = new JMenuItem("Redo");
//        mRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
        JMenuItem mCut = new JMenuItem("Cut", new ImageIcon("imgs/cut.png"));
        mCut.addMouseListener(new CutEvent());
        mCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
        JMenuItem mCopy = new JMenuItem("Copy", new ImageIcon("imgs/copy.png"));
        mCopy.addMouseListener(new CopyEvent());
        mCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        JMenuItem mPaste = new JMenuItem("Paste", new ImageIcon("imgs/paste.png"));
        mPaste.addMouseListener(new PasteEvent());
        mPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
        edit.add(mNewFont);
//        edit.add(mRedo);
        edit.addSeparator();
        edit.add(mCut);
        edit.add(mCopy);
        edit.add(mPaste);

    }
    private void initToolBar() {
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
                fontChooser.chooseFont(textPanel);
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

        JButton newFile = new JButton(new ImageIcon("imgs/new.png"));
        newFile.addMouseListener(new NewDocEvent());
        newFile.setFocusable(false);
        toolBar.add(newFile);
        JButton openFile = new JButton(new ImageIcon("imgs/open.png"));
        openFile.addMouseListener(new OpenFileAdapter(textPanel));
        openFile.setFocusable(false);
        toolBar.add(openFile);

        JButton saveFile = new JButton(new ImageIcon("imgs/save.png"));
        saveFile.setFocusable(false);
        saveFile.addMouseListener(new SaveFileAdapter(textPanel));
        toolBar.add(saveFile);

        JButton saveFileAs = new JButton(new ImageIcon("imgs/saveas.png"));
        saveFileAs.setFocusable(false);
        toolBar.add(saveFileAs);
        toolBar.addSeparator();

        JButton fontBold = new JButton(new ImageIcon("imgs/bold.png"));
        fontBold.setFocusable(false);
        fontBold.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                textPanel.setBold();
            }
        });
        toolBar.add(fontBold);

        JButton fontItalic = new JButton(new ImageIcon("imgs/italic.png"));
        fontItalic.setFocusable(false);
        fontItalic.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                textPanel.setItalic();
            }
        });
        toolBar.add(fontItalic);

        final JComboBox fontName = new JComboBox(FontConstants.fontNames);
        fontName.setFocusable(false);
        fontName.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    textPanel.setFontName((String) item);
                }
            }
        });
        toolBar.add(fontName);


        final JComboBox fontSize = new JComboBox(FontConstants.fontSizes);
        fontSize.setSelectedIndex(9);
        fontSize.setFocusable(false);
        fontSize.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Object item = e.getItem();
                    textPanel.setFontSize(Integer.parseInt((String) item));
                }
            }
        });
        toolBar.add(fontSize);

        add(toolBar,BorderLayout.PAGE_START);
    }
    private void initScrollPane() {
        JScrollPane sp = new JScrollPane(textPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setBorder(null);
        sp.createHorizontalScrollBar();
        add(sp);
    }
    private void initDefaultSettings() {
//        setTitle(textPanel.getFileName() + " - Text Editor, v0.6 rc.");
        setSize(TextPanel.DEFAULT_TEXT_PANEL_WIDTH, TextPanel.DEFAULT_TEXT_PANEL_HEIGHT);
    }
}
