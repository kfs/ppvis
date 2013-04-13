package appui.xmldb.view;

import appui.xmldb.controller.AppController;
import appui.xmldb.model.STableModel;
import org.w3c.dom.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static javax.swing.BorderFactory.createEmptyBorder;

public class TablePanel extends JPanel{
    private JTable table;
    private int countOfRows = 10;
    private JTextField rowsField;
    private STableModel tableModel;

    public TablePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initPanelUI();
    }

    private void initPanelUI() {
        //JPanel tablePar = new JPanel();
        JScrollPane sp = createTable();
       //tablePar.add(sp);
        add(sp);
        JPanel navigation = createNavigation(sp);
        add(navigation);
        exportToFile("1.xml");
        String[] art = { "1", "2", "3", "4", "5", "6", "7"};
        tableModel.insertData(art);
        String[] ololol = {"Stud's Name", "", "", "Stud's Name", "", "Stud's Name", ""};
        tableModel.findData(ololol);
        //tableModel.deleteData(ololol);
        importFromXML("1.xml");
    }
    private JScrollPane createTable() {
        tableModel = new STableModel();
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(TableConstants.TABLE_WIDTH, table.getRowHeight()*countOfRows));
        table.setFillsViewportHeight(true);
       /* table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableAdjuster = new TableColumnAdjuster(table);
        tableAdjuster.adjustColumns();*/
        final JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {
            @Override
            public Dimension getMaximumSize() {
                double height = 0;

                double width = super.getMaximumSize().getWidth();
                height += columnHeader.getViewSize().getHeight();
                height += viewport.getView().getPreferredSize().getHeight();
                height += (getViewportBorderBounds().getHeight() * -1);
                Dimension returnValue = new Dimension ((int) width, (int) height);
                return returnValue;
            }
        };
        sp.setBorder(createEmptyBorder());
        return sp;
    }
    private JPanel createNavigation(final JScrollPane sp) {
        JPanel navigation = new JPanel(
                new FlowLayout(FlowLayout.CENTER));
        JButton next = new JButton("Next");
        next.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                int height = table.getRowHeight()*countOfRows;
                JScrollBar bar = sp.getVerticalScrollBar();
                bar.setValue( bar.getValue()+height );
            }
        } );
        JButton previous = new JButton("Prev");
        previous.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent ae) {
                int height = table.getRowHeight()*countOfRows;
                JScrollBar bar = sp.getVerticalScrollBar();
                bar.setValue( bar.getValue()-height );
            }
        } );
        final JTextField rowsField = new JTextField(Integer.toString(countOfRows));
        rowsField.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                        countOfRows = Integer.parseInt(rowsField.getText());
                        }
                        catch (Exception exception) {
                            AppController.APP_LOGGER.error(exception);
                            return;
                        }
                        int rowCount = table.getRowCount();
                        if(countOfRows > rowCount) {
                            countOfRows = rowCount;
                            rowsField.setText(Integer.toString(rowCount));
                        }
                        else if(countOfRows <= 0) {
                            countOfRows = rowCount == 0 ? 1 : rowCount;
                        }
                        table.setPreferredScrollableViewportSize(
                                new Dimension(TableConstants.TABLE_WIDTH,
                                        table.getRowHeight()*countOfRows)
                        );
                        sp.getParent().revalidate();
                        //sp.revalidate();
                        table.revalidate();
                    }
                }
        );
        navigation.add(previous);
        navigation.add(rowsField);
        navigation.add(next);
        return navigation;
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
            Element tableEl = doc.createElement("studentInfo");
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
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(fileName);

            Element root = doc.getDocumentElement();

            NodeList nodeList = root.getChildNodes();

            String[] st = new String[7];

            for (int i = 0; i < nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);
                if (node.getNodeType() == node.ENTITY_NODE) {
                    st[0] = node.getChildNodes().item(1).getTextContent();
                    st[1] = node.getChildNodes().item(3).getTextContent();
                    st[2] = node.getChildNodes().item(5).getTextContent();
                    st[3] = node.getChildNodes().item(7).getTextContent();
                    st[1] = node.getChildNodes().item(9).getTextContent();
                    st[2] = node.getChildNodes().item(11).getTextContent();
                    st[3] = node.getChildNodes().item(13).getTextContent();
                    ((DefaultTableModel) table.getModel()).addRow(st);

                }

            }
        }
        catch (Exception exception) {
            AppController.APP_LOGGER.error(exception);
        }
    }
}

class TableConstants {
    public static final int TABLE_HEIGHT = 450;
    public static final int TABLE_WIDTH = 650;

    private TableConstants() {

    }
}