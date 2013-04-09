package appui.userInterfaceDialogs;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;

public class TablePanel extends JPanel{
    private JTable table;
    public TablePanel() {
        initPanelUI();
    }
    public void paintComponent(Graphics g) {

    }
    private void initPanelUI() {
        //super(new GridLayout(1, 0));
        setLayout(new GridLayout(1, 0));
        table = new JTable(new STableModel());
       table.setPreferredScrollableViewportSize(new Dimension(TableConstants.TABLE_WIDTH, TableConstants.TABLE_HEIGHT));
        table.setFillsViewportHeight(true);
        JScrollPane sp = new JScrollPane(table);
        add(sp);
    }
    class STableModel extends AbstractTableModel {
        private String[] columnNames = {"Stud's Name",
                "Father's Name",
                "Father's Earnings",
                "Mother's Name",
                "Mother's Earnings",
                "Count of Brothers",
                "Count of Sisters"
        };
        private Object[][] data = {};
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
}

class TableConstants {
    public static final int TABLE_HEIGHT = 600;
    public static final int TABLE_WIDTH = 650;

    private TableConstants() {

    }
}