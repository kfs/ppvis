package appui.xmldb.utils;

import appui.xmldb.controller.EventConstants;

import javax.swing.*;

public enum NavigationItem {
    OPEN(EventConstants.OPEN_FILE, new ImageIcon("imgs/open.png")),
    SAVE(EventConstants.SAVE_FILE, new ImageIcon("imgs/save.png")),
    CLOSE(EventConstants.EXIT, new ImageIcon("imgs/exit.png")),
    ADD(EventConstants.ADD, new ImageIcon("imgs/new.png")),
    DELETE(EventConstants.DELETE, new ImageIcon("imgs/cut.png")),
    FIND(EventConstants.FIND, new ImageIcon("imgs/copy.png")),

    SEPARATOR(EventConstants.SEPARATOR, null);

    private String itemName;
    private Icon icon;
    NavigationItem(String itemName, Icon icon) {
        this.itemName = itemName;
        this.icon = icon;
    }
    public Icon getIcon() {
        return icon;
    }
    public String getItemName() {
        return itemName;
    }
}