package appui.xmldb.controller;

import appui.xmldb.utils.NavigationItem;
import appui.xmldb.view.WndFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventController {
    private EventController() {

    }
    public static ActionListener getListener(NavigationItem item, WndFrame frame) {
        String itemName = item.getItemName();
        if (EventConstants.OPEN_FILE.equals(itemName)) {
            return new OpenAction();
        }
        else if (EventConstants.SAVE_FILE.equals(itemName)) {
            return new SaveAction();
        }
        else if (EventConstants.EXIT.equals(itemName)) {
            return new ExitAction();
        }
        else if (EventConstants.ADD.equals(itemName)) {
            return new AddAction(frame);
        }
        else if (EventConstants.DELETE.equals(itemName)) {
            return new DeleteAction();
        }
        else if (EventConstants.FIND.equals(itemName)) {
            return new FindAction();
        }
        else {
            return  null;
        }
    }
    private static class OpenAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    }
    private static class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    }
    private static class ExitAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    }
    private static class AddAction implements ActionListener {
        private WndFrame frame;
        public AddAction(WndFrame frame) {
            this.frame = frame;
        }
        @Override
        public void actionPerformed(ActionEvent event) {
            frame.callAddItemDialog();
        }
    }
    private static class DeleteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    }
    private static class FindAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

        }
    }
}
