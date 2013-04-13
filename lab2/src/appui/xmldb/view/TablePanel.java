package appui.xmldb.view;

import appui.xmldb.EntryPoint;
import appui.xmldb.controller.AppController;
import appui.xmldb.model.STableModel;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;

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
        exportToFile("1.xml");
    }

    public void exportToFile(String fileName) {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = f.newDocumentBuilder();
            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            Document doc = builder.newDocument();
            Element tableEl = doc.createElement("student info");
            doc.appendChild(tableEl);

            TableModel model = table.getModel();
            TableColumnModel columns = table.getColumnModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                Element rowEl = doc.createElement("student");
                tableEl.appendChild(rowEl);

                for (int j = 0; j < columns.getColumnCount(); j++) {
                    TableColumn col = columns.getColumn(j);
                    String header = col.getHeaderValue().toString();
                    String value = model.getValueAt(i, j).toString();
                    Element cellEl = doc.createElement("cell");
                    Attr colAttr = doc.createAttribute("colName");
                    cellEl.setAttributeNode(colAttr);
                    rowEl.appendChild(cellEl);
                    colAttr.appendChild(doc.createTextNode(header));
                    cellEl.appendChild(doc.createTextNode(value));
                }
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);
        } catch (Exception exception) {
            AppController.APP_LOGGER.error(exception);
        }
    }
    public void importFromXML(String fileName) {

    }
}

class TableConstants {
    public static final int TABLE_HEIGHT = 600;
    public static final int TABLE_WIDTH = 650;

    private TableConstants() {

    }
}