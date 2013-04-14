package appui.xmldb.utils;

import appui.xmldb.controller.AppController;
import appui.xmldb.controller.EventConstants;
import appui.xmldb.controller.EventController;
import appui.xmldb.view.WndFrame;

import javax.swing.*;
import java.util.ResourceBundle;

public class GUIDesigner {
    private GUIDesigner() {

    }
    public static JMenuBar initMenu(WndFrame frame) {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu;
        for (Navigation nav : Navigation.values()) {
            menu = createMenu(nav, frame);
            menuBar.add(menu);
        }
        return menuBar;
    }
    private static JMenu createMenu(Navigation nav, WndFrame frame) {
        ResourceBundle bundle = AppController.getBundle();
        String name = bundle.getString(nav.getItemName());
        JMenu menu = new JMenu(name);
        JMenuItem menuItem;
        for (NavigationItem navItem : nav.getSubItems()) {
            menuItem = createSubMenuItem(navItem, bundle, frame);
            if(menuItem == null) {
                menu.addSeparator();
            }
            else {
                menu.add(menuItem);
            }
        }
        return menu;
    }
    private static JMenuItem createSubMenuItem(NavigationItem nav, ResourceBundle bundle, WndFrame frame) {
        String itemName = nav.getItemName();
        String name = bundle.getString(itemName);
        if(EventConstants.SEPARATOR.equals(name)) {
            return null;
        }
        JMenuItem menuItem = new JMenuItem(name, nav.getIcon());
        menuItem.addActionListener(EventController.getListener(nav, frame));
        return menuItem;
    }

    public static JToolBar initToolBar(WndFrame frame) {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        JButton button;
        ResourceBundle bundle = AppController.getBundle();
        String name;
        for (Navigation nav : Navigation.values()) {
            for (NavigationItem item : nav.getSubItems()) {
                Icon ico = item.getIcon();
                if (ico != null) {
                    button = new JButton(ico);
                }
                else {
                    name = bundle.getString(item.getItemName());
                    if (EventConstants.SEPARATOR.equals(name)) {
                        toolBar.addSeparator();
                        continue;
                    }
                    else {
                        button = new JButton(name);
                    }
                }
                button.addActionListener(EventController.getListener(item, frame));
                toolBar.add(button);
            }
            toolBar.addSeparator();
        }
        return toolBar;
    }

}
