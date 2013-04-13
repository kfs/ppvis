package appui.xmldb.view;

import appui.xmldb.utils.AppConstants;

import javax.swing.JFrame;

public class WndFrame extends JFrame{
    private TablePanel panel;
    public WndFrame() {
        init();
        initMenu();
        initPanel();
    }
    private void init() {
        setSize(AppConstants.MAIN_WINDOW_WIDTH, AppConstants.MAIN_WINDOW_HEIGHT);
        setVisible(true);
    }
    private void initPanel() {
        panel = new TablePanel();
        add(panel);
    }
    private void initMenu() {

    }
}
