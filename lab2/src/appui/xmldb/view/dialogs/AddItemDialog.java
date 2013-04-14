package appui.xmldb.view.dialogs;

import appui.xmldb.controller.AppController;
import appui.xmldb.utils.AppConstants;
import appui.xmldb.view.TablePanel;
import appui.xmldb.view.dialogs.events.ApplyAddEvent;
import appui.xmldb.view.dialogs.events.CancelEvent;

import javax.swing.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddItemDialog extends JDialog {
    private LabeledField studName;
    private LabeledField fatherName;
    private LabeledField fatherEarn;
    private LabeledField motherName;
    private LabeledField motherEarn;
    private LabeledField broCount;
    private LabeledField sisCount;
    private java.util.List<LabeledField> fields = new ArrayList<LabeledField>();
    private TablePanel panel;

    public AddItemDialog(TablePanel panel) {
        this.panel = panel;
        String title = AppController.getBundle().getString(AppConstants.ADD_DIALOG);
        setTitle(title);
        setBounds(AppConstants.DIALOG_WINDOW_INDENT_X, AppConstants.DIALOG_WINDOW_INDENT_Y,
                AppConstants.DIALOG_WINDOW_HEIGHT, AppConstants.DIALOG_WINDOW_WIDTH);
        setResizable(false);
        initDialog();
    }
    private void initDialog() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        String[] fieldLabels = {"Student's Name:",
                                "Father's Name:",
                                "Father's Earnings:",
                                "Mother's Name:",
                                "Mother's Earnings:",
                                "Count of Brothers:",
                                "Count of Sisters:"
                                };
        for (String str : fieldLabels) {
            fields.add(addPanelLine(str));
        }
        JPanel navPanel = addNavigation();
        add(navPanel);
    }
    private JPanel addNavigation() {
        ResourceBundle bundle = AppController.getBundle();
        String applyText = bundle.getString(DialogConstants.APPLY_BUTTON_TEXT);
        String cancelText = bundle.getString(DialogConstants.CANCEL_BUTTON_TEXT);
        JPanel btnPanel = new JPanel();
        JButton applyBtn = new JButton(applyText);
        applyBtn.addActionListener(new ApplyAddEvent(this));
        btnPanel.add(applyBtn);
        JButton cancelBtn = new JButton(cancelText);
        cancelBtn.addActionListener(new CancelEvent());
        btnPanel.add(cancelBtn);
        return btnPanel;
    }
    private LabeledField addPanelLine(String labelName) {
        LabeledField labeledField = new LabeledField(labelName);
        add(labeledField);
        return labeledField;
    }
    public void addItem() {
        String[] item = new String[7];
        for (int currItem = 0; currItem < item.length; currItem++) {
            item[currItem] = fields.get(currItem).getText();
        }
        panel.addItem(item);
        setVisible(false);
    }
}

class LabeledField extends JPanel {
    private JTextField textField;

    LabeledField(String labelName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        addLabel(labelName);
        addField();
    }
    private void addLabel(String labelName) {
        JLabel label = new JLabel(labelName);
        add(label);
    }
    private void addField() {
        textField = new JTextField();
        add(textField);
    }
    public String getText() {
        return textField.getText();
    }
}

