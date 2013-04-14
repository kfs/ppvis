package appui.xmldb.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class STableModel extends AbstractTableModel {
    private String[] columnNames = {"Stud's Name",
            "Father's Name",
            "Father's Earnings",
            "Mother's Name",
            "Mother's Earnings",
            "Count of Brothers",
            "Count of Sisters"
    };
    private List<List<Object>> data = new ArrayList<List<Object>>();

    public STableModel() {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add("Stud's Name");
        arrayList.add("Stud's Name");
        arrayList.add("Stud's Name");
        arrayList.add("Stud's Name");
        arrayList.add("Stud's Name");
        arrayList.add("Stud's Name");
        arrayList.add("Stud's Name");
        data.add(arrayList);
        ArrayList<Object> arrayList2 = new ArrayList<Object>();
        arrayList2.add("Stud's Name");
        arrayList2.add("Stud's Name");
        arrayList2.add("Stud's Name");
        arrayList2.add("Stud's Name");
        arrayList2.add("Stud's Name");
        arrayList2.add("Stud's Name");
        arrayList2.add("Stud's Name");
        data.add(arrayList2);
        data.add(arrayList2);
        ArrayList<Object> arrayList3 = new ArrayList<Object>();
        arrayList3.add("Stud's Name");
        arrayList3.add("Stud's Name");
        arrayList3.add("Stud's Name");
        arrayList3.add("Stud's Name");
        arrayList3.add("Stud's Name");
        arrayList3.add("Stud's Name");
        arrayList3.add("Stud's Name");
        data.add(arrayList3);

    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public Object getValueAt(int row, int col) {
        return data.get(row).get(col);
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int col) {
        return col >= 2;
    }
    public void setValueAt(Object value, int row, int col) {
        data.get(row).set(col, value);
        fireTableCellUpdated(row, col);
    }
    public void replaceData(String[] data, int row) {
        for(int pos = 0; pos < columnNames.length; pos++) {
            setValueAt(data[pos], row, pos);
        }
    }
    public void insertData(String[] data) {
        List<Object> list = new ArrayList<Object>();
        for (String str : data) {
            list.add(str);
        }
        this.data.add(list);
        fireTableDataChanged();
    }
    public int deleteData(String[] data) {
        boolean delFlag;
        int countOfDeleted = 0;
        for (int row = 0; row < this.data.size(); row++) {
            delFlag = true;
            List<Object> list = this.data.get(row);
            for (int col = 0; col < list.size(); col++) {
                if(!"".equals(data[col]) && !list.get(col).equals(data[col])) {
                    delFlag = false;
                    break;
                }
            }
            if (delFlag) {
                countOfDeleted++;
                this.data.remove(row);
                row--;
            }
        }
        return countOfDeleted;
    }
    public List<List<Object>> findData(String[] data) {
        List<List<Object>> results = new ArrayList<List<Object>>();
        boolean checkFlag;
        for (int row = 0; row < this.data.size(); row++) {
            checkFlag = true;
            List<Object> list = this.data.get(row);
            for (int col = 0; col < list.size(); col++) {
                if(!"".equals(data[col]) && !list.get(col).equals(data[col])) {
                    checkFlag = false;
                    break;
                }
            }
            if (checkFlag) {
                results.add(list);
            }
        }
        return results;
    }
    public void merge(List<List<Object>> mergeList) {
        data.addAll(mergeList);
    }
}