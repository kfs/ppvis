package appui.xmldb.view;

import appui.xmldb.controller.AppController;
import appui.xmldb.controller.EventConstants;
import appui.xmldb.controller.EventController;
import appui.xmldb.utils.AppConstants;
import appui.xmldb.utils.GUIDesigner;
import appui.xmldb.utils.Navigation;
import appui.xmldb.utils.NavigationItem;
import appui.xmldb.view.dialogs.AddItemDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

import static javax.swing.BorderFactory.createEmptyBorder;

public class WndFrame extends JFrame{
    private TablePanel panel;
    public WndFrame() {
        init();
        initNavigationPanel();
        initPanel();
    }
    private void init() {
        setBounds(AppConstants.MAIN_WINDOW_INDENT_X, AppConstants.MAIN_WINDOW_INDENT_Y,
                  AppConstants.MAIN_WINDOW_WIDTH,    AppConstants.MAIN_WINDOW_HEIGHT);
        setLayout(new BorderLayout());
        setVisible(true);
        new AddItemDialog();
    }
    private void initPanel() {
        panel = new TablePanel();
        JScrollPane sp = new JScrollPane(panel);
        //sp.setBorder(createEmptyBorder());
        add(sp, BorderLayout.CENTER);
        //add(panel, BorderLayout.CENTER);
    }
    private void initNavigationPanel() {
        JPanel navPanel = new JPanel(new BorderLayout());

        JMenuBar menuBar = GUIDesigner.initMenu();
        JToolBar toolBar = GUIDesigner.initToolBar();
        navPanel.add(menuBar, BorderLayout.PAGE_START);
        navPanel.add(toolBar, BorderLayout.CENTER);

        add(navPanel, BorderLayout.PAGE_START);
    }
}
