package appui.xmldb.controller;

import appui.xmldb.view.WndFrame;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class AppController {
    private static WndFrame appFrame;
    private static Locale locale;
    private static ResourceBundle bundle;
    public static final Logger APP_LOGGER = Logger.getLogger("ApplicationLogger");

    private AppController() {

    }
    public static void initApplication(String[] args) {
        setLocale(args);
        setSystemLookAndFeel();
        addDialogPanel();
    }
    private static void setSystemLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            APP_LOGGER.error(e);
        }
        catch (ClassNotFoundException e) {
            APP_LOGGER.error(e);
        }
        catch (InstantiationException e) {
            APP_LOGGER.error(e);
        }
        catch (IllegalAccessException e) {
            APP_LOGGER.error(e);
        }
    }
    private static void setLocale(String[] args) {
        String language;
        String country;
        Locale currentLocale;
        if(args.length != 2) {
            currentLocale = Locale.ENGLISH;
        } else {
            language = new String(args[0]);
            country = new String(args[1]);
            currentLocale = new Locale(language, country);
        }
        locale = currentLocale;
//        appFrame.setLocale(locale);
        bundle = ResourceBundle.getBundle("appui/xmldb/utils/locale/MessagesBundle", currentLocale);
    }
    private static void addDialogPanel() {
        appFrame = new WndFrame();
        appFrame.setLocale(locale);
        appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        appFrame.setVisible(true);
    }
    public static Locale getLocale() {
        return locale;
    }
    public static ResourceBundle getBundle() {
        return bundle;
    }
}
