package org.jeppers.swing.spreadsheet;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class JSpreadsheet extends JTable implements SpanModelListener, AttributeModelListener{
    /** Span model of spreadsheet */
    protected SpanModel spanModel;
    
    /** Attribute model of spreadsheet */
    protected AttributeModel attributeModel;    
    
    protected CellRange selectionRange; /* keeps track of selection range. This is required since
                                           the ListSelectionModel used by JTable does not work 
                                           correctly with cell spans */
    
    public JSpreadsheet(TableModel model, SpanModel spanModel, AttributeModel attributeModel) {
        // Customise JTable
        super(model);
        getTableHeader().setReorderingAllowed(false);
        setCellSelectionEnabled(true);
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        setDefaultRenderer(Object.class, new SpreadsheetCellRenderer());
        setRowMargin(1);
        getColumnModel().setColumnMargin(1);
        setRowHeight(20);
        setUI(new SpreadsheetUI());
        
        // Initialise JSpreadsheet
        setSpanModel(spanModel);
        setAttributeModel(attributeModel);
        
        // Add listeners to models
        spanModel.addSpanModelListener(this);
        attributeModel.addAttributeModelListener(this);
    }
    
    /** Get the span model of this spreadsheet */
    public SpanModel getSpanModel(){
        return spanModel;
    }
    
    /** Set the span model for this spreadsheet */
    public void setSpanModel(SpanModel model){
        spanModel = model;
    }
    
    /** Get the attribute model of this spreadsheet */
    public AttributeModel getAttributeModel(){
        return attributeModel;
    }
    
    /** Set the span model of this spreadsheet */
    public void setAttributeModel(AttributeModel model){
        attributeModel = model;
    }    
    
    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
        Rectangle cellBounds = null;
        
        if ((row <0) || (column<0) || (getRowCount() <= row) || (getColumnCount() <= column) || (spanModel == null)) {
            cellBounds = super.getCellRect(row,column,includeSpacing);
        }else{
            // Get the visible cell position
            CellSpan span = spanModel.getSpanAt(row, column);
            row = span.getRow();
            column = span.getColumn();
            
            cellBounds = super.getCellRect(row,column,includeSpacing);
            
            // Include spanned cells
            if(span.getRowCount() > 1 || span.getColumnCount() > 1){
                Rectangle rect = super.getCellRect(span.getRow() + span.getRowCount() - 1, span.getColumn() + span.getColumnCount() - 1, includeSpacing);
                cellBounds.height = (rect.y - cellBounds.y) + rect.height;
                cellBounds.width = (rect.x - cellBounds.x) + rect.width;
            }
            
        }
        
        return cellBounds;
    }
    
    public int rowAtPoint(Point point) {
        int row = super.rowAtPoint(point);
        int col = super.columnAtPoint(point);
        
        if ((row < 0)||(getRowCount() <= row)){
            // out of bounds
            return -1;
        }
        
        if((col < 0) || (getColumnCount() <= col)){
            // out of bounds
            return -1;
        }
        
        CellSpan span = spanModel.getSpanAt(row, col);
        
        if(! isVisible(row, col)){
            row = span.getRow();
        }
        
        return row;
    }
    
    public int columnAtPoint(Point point) {
        int row = super.rowAtPoint(point);
        int col = super.columnAtPoint(point);
        
        if ((row < 0)||(getRowCount() <= row)){
            // out of bounds
            return -1;
        }
        
        if((col < 0) || (getColumnCount() <= col)){
            // out of bounds
            return -1;
        }
        
        CellSpan span = spanModel.getSpanAt(row, col);
        
        if(!isVisible(row, col)){
            col = span.getColumn();
        }
        
        return col;
    }
    
    /** Repaint cell range */
    public void repaint(CellRange region){
        Rectangle dirtyRegion = new Rectangle();
        
        Rectangle topLeft = getCellRect(region.getFirstRow(), region.getFirstColumn(), true);
        dirtyRegion.x = topLeft.x;
        dirtyRegion.y = topLeft.y;
        Rectangle bottomRight = getCellRect(region.getLastRow(), region.getLastColumn(), true);
        dirtyRegion.width = (bottomRight.x - topLeft.x) + bottomRight.width;
        dirtyRegion.height = (bottomRight.y - topLeft.y) + bottomRight.height;
        
        repaint(dirtyRegion.x, dirtyRegion.y, dirtyRegion.width, dirtyRegion.height);
    }
    
    /** Return the current selection range */
    public CellRange getSelectionRange(){
        return selectionRange;
    } //end getSelectionRange           
    
    /** Returns true if the cell at the specified position is selected. */
    public boolean isCellSelected(int row, int column){
        if(selectionRange == null || spanModel == null){
            return super.isCellSelected(row, column);
        }
        
        if( row >= selectionRange.getFirstRow() && row <= selectionRange.getLastRow() &&
        column >= selectionRange.getFirstColumn() && column <= selectionRange.getLastColumn()){
            return true;
        }
        
        return false;
    }
    
    /** Updates the selection models of the table, depending on the state of the two flags: toggle and extend. */
    public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend){
        ListSelectionModel rsm = getSelectionModel();
        ListSelectionModel csm = getColumnModel().getSelectionModel();
        
        int oldLeadRow = rsm.getLeadSelectionIndex();
        int oldLeadCol = csm.getLeadSelectionIndex();
        
        super.changeSelection(rowIndex, columnIndex, toggle, extend);
        
        int newLeadRow = rsm.getLeadSelectionIndex();
        int newLeadCol = csm.getLeadSelectionIndex();
        
        if(extend && !toggle && (spanModel != null) && (selectionRange != null)){
            int rowMin = rsm.getMinSelectionIndex();
            int colMin = csm.getMinSelectionIndex();
            int rowMax = rsm.getMaxSelectionIndex();
            int colMax = csm.getMaxSelectionIndex();
            
            // Add intersecting spans to selectionRange
            CellSpan cellSpan = null;
            int leftCol = colMin;
            int rightCol = colMax;
            for(int row = rowMin; row <= rowMax; row++){ // scan left and right columns
                cellSpan = spanModel.getSpanAt(row, leftCol);
                colMin = Math.min(colMin, cellSpan.getColumn());
                cellSpan = spanModel.getSpanAt(row, rightCol);
                colMax = Math.max(colMax, (cellSpan.getColumn() + cellSpan.getColumnCount() - 1));
            } // end for
            
            int topRow = rowMin;
            int bottomRow = rowMax;
            for(int col = leftCol; col <= rightCol; col++){ // scan top and bottom rows
                cellSpan = spanModel.getSpanAt(topRow, col);
                rowMin = Math.min(rowMin, cellSpan.getRow());
                cellSpan = spanModel.getSpanAt(bottomRow, col);
                rowMax = Math.max(rowMax, (cellSpan.getRow() + cellSpan.getRowCount() - 1));
            } // end for
            
            repaint(selectionRange); // clear old selection
            
            // set lead
            if(newLeadRow >= oldLeadRow){ // moving downwards
             //   rsm.setLeadSelectionIndex(rowMax);
            }
            
            if(newLeadCol >= oldLeadCol){ // moving right
             //   csm.setLeadSelectionIndex(colMax);
            }
            
            selectionRange = new CellRange(rowMin, colMin, rowMax, colMax);
            
            repaint(selectionRange); // paint new selection
        }else{
            if(rowIndex < 0 || rowIndex >= getRowCount() || columnIndex < 0 || columnIndex >= getColumnCount()){
                selectionRange = null;
            }else{
                selectionRange = new CellRange(rowIndex, columnIndex, rowIndex, columnIndex);
            }
        }// end if
    } // end changeSelection
    
    /** Return true if the cell is visible */
    public boolean isVisible(int row, int column){
        CellSpan span = spanModel.getSpanAt(row, column);
        return (row == span.getRow() && column == span.getColumn());
    }
    
    //
    // SpanModelListener
    //
    /** Called when a span is modified, added or removed. */
    public void spanDataChanged(SpanModelEvent e){
        repaint();
    }
    
    /** Called whenever the span model changes globally. */
    public void spanModelChanged(SpanModelEvent e){
        resizeAndRepaint();
    }
    
    /** Called when the shape of the underlying data model is modified
     * (rows or columns inserted or removed). */
    public void spanStructureChanged(SpanModelEvent e){
        resizeAndRepaint();
    }
    
    //
    // AttributeModelListener
    //
    
    /** Called when an atttribute is modified */
    public void attributeDataChanged(AttributeModelEvent e){
        repaint();
    }
    
    /** Called whenever the attibute model changes globally. */
    public void attributeModelChanged(AttributeModelEvent e){
        resizeAndRepaint();
    }
    
    /** Called when the shape of the underlying data model is modified
     * (rows or columns inserted or removed). */
    public void attributeStructureChanged(AttributeModelEvent e){
        resizeAndRepaint();
    }
    
} //end JSpreadsheet
