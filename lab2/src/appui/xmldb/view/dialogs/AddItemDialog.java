package appui.xmldb.view.dialogs;

import javax.swing.*;

public class AddItemDialog extends JDialog {
    private LabeledField studName;
    private LabeledField fatherName;
    private LabeledField fatherEarn;
    private LabeledField motherName;
    private LabeledField motherEarn;
    private LabeledField broCount;
    private LabeledField sisCount;
    private java.util.List<LabeledField> asf;

    public AddItemDialog() {
        setTitle("Add Dialog");
        setSize(300, 310);
        setResizable(false);
        initDialog();
    }
    private void initDialog() {
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        studName = addPanelLine("Student's Name:");
        fatherName = addPanelLine("Father's Name:");
        fatherEarn = addPanelLine("Father's Earnings:");
        motherName = addPanelLine("Mother's Name:");
        motherEarn = addPanelLine("Mother's Earnings:");
        broCount = addPanelLine("Count of Brothers:");
        sisCount = addPanelLine("Count of Sisters:");

        JPanel navPanel = addNavigation();
        add(navPanel);

        setVisible(true);
    }
    private JPanel addNavigation() {
        JPanel btnPanel = new JPanel();
        JButton applyBtn = new JButton("Apply");
        applyBtn.addActionListener(new ApplyAddEvent());
        btnPanel.add(applyBtn);
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(new CancelEvent());
        btnPanel.add(cancelBtn);
        return btnPanel;
    }
    private LabeledField addPanelLine(String labelName) {
        LabeledField labeledField = new LabeledField(labelName);
        add(labeledField);
        return labeledField;
    }
}

class LabeledField extends JPanel {
    private JTextField textField;

    LabeledField(String labelName) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setSize(50, 250);
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

}

