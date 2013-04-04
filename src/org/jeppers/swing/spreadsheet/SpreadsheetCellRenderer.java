package org.jeppers.swing.spreadsheet;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.border.*;

public class SpreadsheetCellRenderer extends JLabel implements TableCellRenderer {
    protected Border noFocusBorder;
    
    public SpreadsheetCellRenderer() {
        noFocusBorder = new EmptyBorder(1, 2, 1, 2);
        setOpaque(true);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {        
        Color foreground = table.getForeground();
        Color background = table.getBackground();
        Font  font       = table.getFont();
        int   horizontalAlignment   = SwingConstants.LEFT;
        int   verticalAlignment     = SwingConstants.BOTTOM;
        
        if(table instanceof JSpreadsheet){
            AttributeModel attributes = ((JSpreadsheet)table).getAttributeModel();
            foreground = attributes.getForeground(row, column);
            background = attributes.getBackground(row, column);
            font       = attributes.getFont(row, column);
            horizontalAlignment = attributes.getHorizontalAlignment(row, column);
            verticalAlignment   = attributes.getVerticalAlignment(row, column);
        }
        
        if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
        } else {
            setForeground(foreground);
            setBackground(background);
        }
        setFont(font);
        setBorder(noFocusBorder);
        if (hasFocus) {
            setForeground(foreground);
            setBackground(background);
        }
        setVerticalAlignment(verticalAlignment);
        setHorizontalAlignment(horizontalAlignment);
        setValue(value);
        return this;
    }
    
    protected void setValue(Object value) {
        setText((value == null) ? "" : value.toString());
    }
}


