package appui.xmldb.utils;

import javax.swing.*;

public enum NavigationItem {
    OPEN("open", new ImageIcon("appui/xmldb/imgs/open.png")),
    SAVE("save", new ImageIcon("")),

    ;
    private String text;
    private Icon icon;
    NavigationItem(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }
    public Icon getIcon() {
        return icon;
    }
    public String getText() {
        return text;
    }
}