package org.jeppers.swing.spreadsheet;

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.*;
import javax.swing.border.*;

public class SpreadsheetUI extends BasicTableUI {
    private final static int HORIZONTAL = 0;
    private final static int VERTICAL   = 1;
        
    protected AttributeModel attributeModel = null;    
    
    /** Paint a representation of the <code>table</code> instance
     * that was set in installUI().
     */
    public void paint(Graphics g, JComponent c) {
        if (table.getRowCount() <= 0 || table.getColumnCount() <= 0) {
            return;
        }
        attributeModel = ((JSpreadsheet)table).getAttributeModel();
        
        Rectangle clip = g.getClipBounds();
        Point minLocation = clip.getLocation();
        Point maxLocation = new Point(clip.x + clip.width - 1, clip.y + clip.height - 1);
        int rMin = table.rowAtPoint(minLocation);
        int rMax = table.rowAtPoint(maxLocation);
        // This should never happen.
        if (rMin == -1) {
            rMin = 0;
        }
        // If the table does not have enough rows to fill the view we'll get -1.
        // Replace this with the index of the last row.
        if (rMax == -1) {
            rMax = table.getRowCount()-1;
        }
        int cMin = table.columnAtPoint(minLocation);
        int cMax = table.columnAtPoint(maxLocation);
        // This should never happen.
        if (cMin == -1) {
            cMin = 0;
        }
        // If the table does not have enough columns to fill the view we'll get -1.
        // Replace this with the index of the last column.
        if (cMax == -1) {
            cMax = table.getColumnCount()-1;
        }
        
        // Paint the grid lines
        paintGrid(g, rMin, rMax, cMin, cMax);
        
        // Paint the cells.
        paintCells(g, rMin, rMax, cMin, cMax);
        
        // Paint cell border
        paintBorders(g, rMin, rMax, cMin, cMax);
    }
    
    private void paintCells(Graphics g, int rMin, int rMax, int cMin, int cMax) {
        JSpreadsheet spreadsheet = (JSpreadsheet) table;
        
        for(int row = rMin; row <= rMax; row++){
            for(int column = cMin; column <= cMax; column++){
                if(spreadsheet.isVisible(row, column)){
                    Rectangle cellRect = table.getCellRect(row, column, false);
                    paintCell(g, cellRect, row, column);
                }
            }
        }
        
        // Remove any renderers that may be left in the rendererPane.
        rendererPane.removeAll();
    }
    
    private void paintCell(Graphics g, Rectangle cellRect, int row, int column) {
        if (table.isEditing() && table.getEditingRow()==row && table.getEditingColumn()==column) {
            Component component = table.getEditorComponent();
            component.setBounds(cellRect);
            component.validate();
        }
        else {
            // Get render component
            TableCellRenderer renderer = table.getCellRenderer(row, column);
            Component component = table.prepareRenderer(renderer, row, column);
            
            // Paint component
            rendererPane.paintComponent(g, component, table, cellRect.x, cellRect.y,
            cellRect.width, cellRect.height, true);
        }
    }
    
    private void paintGrid(Graphics g, int rMin, int rMax, int cMin, int cMax){
        // Only paint if painting both horizontal lines and verticallines
        if(!(table.getShowHorizontalLines() && table.getShowVerticalLines())){
            return;
        }
        
        g.setColor(table.getGridColor());
        for(int row = rMin; row <= rMax; row++){
            for(int column = cMin; column <= cMax; column++){
                Rectangle cellRect = table.getCellRect(row, column, true);
                g.drawRect(cellRect.x-1, cellRect.y-1, cellRect.width, cellRect.height);
            }
        }
    }
    
    private void paintBorders(Graphics g, int rMin, int rMax, int cMin, int cMax){
        for(int row = rMin; row <= rMax; row++){
            for(int column = cMin; column <= cMax; column++){
                Rectangle cellRect = table.getCellRect(row, column, false);
                paintBorder(g, cellRect, row, column);
            }
        }
    }
    
    private void paintBorder(Graphics g, Rectangle cellRect, int row, int column){
        // Paint border
        Border border = attributeModel.getBorder(row, column);
        Insets borderInsets = border.getBorderInsets(null);
        Rectangle borderRect = new Rectangle(cellRect);
        int topOffset = (borderInsets.top >> 1);
        int leftOffset = (borderInsets.left >> 1);
        int bottomOffset = (borderInsets.bottom/3) + topOffset;
        int rightOffset = (borderInsets.right/3) + leftOffset;
        int colMargin = table.getColumnModel().getColumnMargin();
        int rowMargin = table.getRowMargin();
        borderRect.x -= (leftOffset + colMargin);
        borderRect.y -= (topOffset + rowMargin);
        borderRect.width += (rightOffset + (colMargin << 1));
        borderRect.height += (bottomOffset + (rowMargin << 1));
        border.paintBorder(null, g, borderRect.x, borderRect.y, borderRect.width, borderRect.height);
    }
}
