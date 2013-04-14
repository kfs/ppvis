package appui.xmldb.view.dialogs.events;

import appui.xmldb.view.dialogs.AddItemDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ApplyAddEvent implements ActionListener {
    private AddItemDialog dialog;
    public ApplyAddEvent(AddItemDialog dialog) {
        this.dialog = dialog;
    }
    @Override
    public void actionPerformed(ActionEvent event) {
        dialog.addItem();
    }
}
