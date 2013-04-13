package appui.xmldb.utils;

import java.util.ArrayList;
import java.util.List;

public enum Navigation {
    FILE("file", NavigationItem.OPEN, NavigationItem.SAVE, NavigationItem.SEPARATOR, NavigationItem.CLOSE),
    EDIT("edit", NavigationItem.ADD, NavigationItem.DELETE, NavigationItem.FIND),
    ;
    private String itemName;
    private List<NavigationItem> subItems = new ArrayList<NavigationItem>();

    Navigation(String itemName, NavigationItem ... navItems) {
        this.itemName = itemName;
        for(NavigationItem navigationItem : navItems) {
            subItems.add(navigationItem);
        }
    }
    public String getItemName() {
        return itemName;
    }
    public List<NavigationItem> getSubItems() {
        return subItems;
    }
}
