package appui.xmldb.view;

import appui.xmldb.utils.AppConstants;
import appui.xmldb.utils.GUIDesigner;
import appui.xmldb.view.dialogs.AddItemDialog;
import appui.xmldb.view.dialogs.FindItemDialog;
import appui.xmldb.view.dialogs.RemoveItemDialog;

import javax.swing.*;
import java.awt.*;

public class WndFrame extends JFrame{
    private TablePanel panel;
    private AddItemDialog addItemDialog;
    private RemoveItemDialog removeItemDialog;
    private FindItemDialog findItemDialog;
    public WndFrame() {
        init();
        initNavigationPanel();
        initPanel();
        initDialogs();
    }
    private void init() {
        setBounds(AppConstants.MAIN_WINDOW_INDENT_X, AppConstants.MAIN_WINDOW_INDENT_Y,
                  AppConstants.MAIN_WINDOW_WIDTH,    AppConstants.MAIN_WINDOW_HEIGHT);
        setLayout(new BorderLayout());
        setVisible(true);
    }
    private void initPanel() {
        panel = new TablePanel();
        JScrollPane sp = new JScrollPane(panel);
        add(sp, BorderLayout.CENTER);
    }
    private void initNavigationPanel() {
        JPanel navPanel = new JPanel(new BorderLayout());
        JMenuBar menuBar = GUIDesigner.initMenu(this);
        JToolBar toolBar = GUIDesigner.initToolBar(this);
        navPanel.add(menuBar, BorderLayout.PAGE_START);
        navPanel.add(toolBar, BorderLayout.CENTER);
        add(navPanel, BorderLayout.PAGE_START);
    }
    private void initDialogs() {
        addItemDialog = new AddItemDialog(panel);
        removeItemDialog = new RemoveItemDialog();
        findItemDialog = new FindItemDialog();
    }
    public void callAddItemDialog() {
        addItemDialog.setVisible(true);
    }
    public void callRemoveItemDialog() {
        removeItemDialog.setVisible(true);
    }
    public void callFindItemDialog() {
        findItemDialog.setVisible(true);
    }
}
