package components;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TextTableRenderer extends JTextArea implements TableCellRenderer {
    public TextTableRenderer() {
        setOpaque(true);
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row,
                                                   int column) {

        setText((value == null)
                ? ""
                : value.toString());
        return this;
    }
}