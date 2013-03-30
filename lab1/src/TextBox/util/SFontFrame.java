package TextBox.util;

import TextBox.TextPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SFontFrame extends JFrame {
    protected JList fontList;
    protected JList sizeList;
    protected JPanel panel;
    protected JButton btnApply;
    protected JButton btnCancel;
    protected JCheckBox chkBold;
    protected JCheckBox chkItalic;
    protected TextPanel textView;

    protected Font resultFont;
    protected String resultFontID;

    protected String fontSizes[] = {"4", "6", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "24", "30", "36", "40", "48", "60", "72"};
    protected String fontNames[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();


    public SFontFrame() {
        setSize(250, 300);
        setTitle("Font Chooser...");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        init();
    }

    private void  init() {
        int defaultFontIndex = 0;
        int defaultSizeIndex = 9;

        fontList = new JList(fontNames);
        for (int currFontIndex = 0; currFontIndex < fontNames.length; currFontIndex++) {
            if (fontNames[currFontIndex].equals("Serif")) {
                defaultFontIndex = currFontIndex;
                break;
            }
        }
        fontList.setSelectedIndex(defaultFontIndex);
        sizeList = new JList(fontSizes);
        sizeList.setSelectedIndex(defaultSizeIndex);

        JScrollPane fontScroll = new JScrollPane(fontList);
        fontScroll.setPreferredSize(new Dimension(150, 200));
        JScrollPane sizeScroll = new JScrollPane(sizeList);
        sizeScroll.setPreferredSize(new Dimension(60, 200));
        fontList.ensureIndexIsVisible(defaultFontIndex);
        sizeList.ensureIndexIsVisible(sizeList.getSelectedIndex());

        btnApply = new JButton("Apply");
        btnApply.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                String fontName = fontList.getSelectedValue().toString();
                int style;
                int size = Integer.parseInt(sizeList.getSelectedValue().toString());

                if(chkBold.isSelected() && chkItalic.isSelected())
                    style = Font.BOLD + Font.ITALIC;
                else if(chkBold.isSelected())
                    style = Font.BOLD;
                else if(chkItalic.isSelected())
                    style = Font.ITALIC;
                else
                    style = Font.PLAIN;
                resultFontID = fontName + '-' + style + '-' + size;
                System.out.println(resultFontID);
                FontPair fontPair = FontInfo.findFont(resultFontID);
                if(fontPair == null) {
                    resultFont = new Font(fontName, style, size);
                    FontInfo.addFont(resultFont, resultFontID);
                }
                else
                    resultFont = fontPair.getFont();

                setVisible(false);
                textView.updateFont(resultFont);
                textView.requestFocus();
            }
        });
        btnCancel = new JButton("Cancel");
        chkBold = new JCheckBox("Bold");
        chkItalic = new JCheckBox("Italic");
        panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        panel.add(fontScroll);
        panel.add(sizeScroll);
        panel.add(chkBold);
        panel.add(chkItalic);
        panel.add(btnApply);
        panel.add(btnCancel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(fontScroll)
                        .addComponent(sizeScroll))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(chkBold)
                        .addComponent(chkItalic))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(btnApply)
                        .addComponent(btnCancel))
        );
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(fontScroll)
                        .addComponent(chkBold)
                        .addComponent(btnApply))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(sizeScroll)
                        .addComponent(chkItalic)
                            .addComponent(btnCancel))

        );

        add(panel);
    }
    public void chooseFont(TextPanel textPanel) {
        textView = textPanel;
        setVisible(true);
    }
}
