package components;

import javafx.scene.text.Text;
import model.Data;
import model.Item;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class TableViewProduct extends DefaultTableModel {

    private Data data;
    private char rowHead = 'A';

    @Override
    public int getRowCount() {
        int count = data != null ? data.getConfig().getRows() : 0;
        return count;
    }

    @Override
    public int getColumnCount() {
        int count = data != null ? Integer.parseInt(data.getConfig().getColumns()) + 1 : 0;
        return count;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0)
            return "Row/Column";
        return String.valueOf(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (data != null) {
            int index = rowIndex * Integer.parseInt(data.getConfig().getColumns()) + (columnIndex - 1);
            if (columnIndex != 0 &&
                    data.getItems().size() > 0 &&
                    index < data.getItems().size()) {

                Item item = data.getItems().get(index);
                if (item.getAmount() <= 0) {
                    return "Empty";
                }
                String s = item.toString();
                s = s.replace("Item{name='", "")
                        .replace("', amount=", " ")
                        .replace(", price='", " ")
                        .replace("'", "")
                        .replace("}", "");
                return s;
            } else if (columnIndex == 0) {
                return (char) (rowHead + rowIndex);
            }
        }
        return "Empty";
    }

    public void replaceData(Data data) {
        this.data = new Data();
        this.data.setConfig(data.getConfig());
        this.data.setItems(data.getItems());

        this.setColumnCount(Integer.parseInt(data.getConfig().getColumns()));
        this.setRowCount(data.getConfig().getRows());
        this.fireTableDataChanged();
    }
}
