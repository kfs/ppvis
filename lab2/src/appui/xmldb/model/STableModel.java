package appui.xmldb.model;

import javax.swing.table.AbstractTableModel;

public class STableModel extends AbstractTableModel {
    private String[] columnNames = {"Stud's Name",
            "Father's Name",
            "Father's Earnings",
            "Mother's Name",
            "Mother's Earnings",
            "Count of Brothers",
            "Count of Sisters"
    };
    private Object[][] data = {
            { "addins", "02.11.2006 19:15", "Folder", "", "1", "2", "3"},
            { "AppPatch", "03.10.2006 14:10", "Folder", "", "1", "2", "3" },
            { "assembly", "02.11.2006 14:20", "Folder", "", "1", "2", "3" },
            { "Boot", "13.10.2007 10:46", "Folder", "", "1", "2", "3" },
            { "Branding", "13.10.2007 12:10", "Folder", "", "1", "2", "3" },
            { "Cursors", "23.09.2006 16:34", "Folder", "", "1", "2", "3" },
            { "Debug", "07.12.2006 17:45", "Folder", "", "1", "2", "3" },
            { "Fonts", "03.10.2006 14:08", "Folder", "", "1", "2", "3" },
            { "AppPatch", "03.10.2006 14:10", "Folder", "", "1", "2", "3" },
            { "assembly", "02.11.2006 14:20", "Folder", "", "1", "2", "3" },
            { "Boot", "13.10.2007 10:46", "Folder", "", "1", "2", "3" },
            { "Branding", "13.10.2007 12:10", "Folder", "", "1", "2", "3" },
            { "Cursors", "23.09.2006 16:34", "Folder", "", "1", "2", "3" },
            { "Debug", "07.12.2006 17:45", "Folder", "", "1", "2", "3" },
            { "Fonts", "03.10.2006 14:08", "Folder", "", "1", "2", "3" },
            { "AppPatch", "03.10.2006 14:10", "Folder", "", "1", "2", "3" },
            { "assembly", "02.11.2006 14:20", "Folder", "", "1", "2", "3" },
            { "Boot", "13.10.2007 10:46", "Folder", "", "1", "2", "3" },
            { "Branding", "13.10.2007 12:10", "Folder", "", "1", "2", "3" },
            { "Cursors", "23.09.2006 16:34", "Folder", "", "1", "2", "3" },
            { "Debug", "07.12.2006 17:45", "Folder", "", "1", "2", "3" },
            { "Fonts", "03.10.2006 14:08", "Folder", "", "1", "2", "3" },
            { "AppPatch", "03.10.2006 14:10", "Folder", "", "1", "2", "3" },
            { "assembly", "02.11.2006 14:20", "Folder", "", "1", "2", "3" },
            { "Boot", "13.10.2007 10:46", "Folder", "", "1", "2", "3" },
            { "Branding", "13.10.2007 12:10", "Folder", "", "1", "2", "3" },
            { "Cursors", "23.09.2006 16:34", "Folder", "", "1", "2", "3" },
            { "Debug", "07.12.2006 17:45", "Folder", "", "1", "2", "3" },
            { "Fonts", "03.10.2006 14:08", "Folder", "", "1", "2", "3" },
            { "Help", "08.11.2006 18:23", "Folder", "", "1", "2", "3" },
            { "explorer.exe", "18.10.2006 14:13", "File", "2,93MB", "1", "2", "3" },
            { "helppane.exe", "22.08.2006 11:39", "File", "4,58MB", "1", "2", "3" },
            { "twunk.exe", "19.08.2007 10:37", "File", "1,08MB", "1", "2", "3" },
            { "nsreg.exe", "07.08.2007 11:14", "File", "2,10MB", "1", "2", "3" },
            { "avisp.exe", "17.12.2007 16:58", "File", "12,67MB", "1", "2", "3" },
    };
    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        if (col < 2) {
            return false;
        } else {
            return true;
        }
    }
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }
}