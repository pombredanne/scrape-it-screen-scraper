package org.jeppers.swing.spreadsheet;
/*
 * DefaultSpanModel.java
 *
 * Created on 4 July 2002, 13:42
 */
import org.jeppers.util.Vector2d;
/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public class DefaultSpanModel extends AbstractSpanModel{
    protected Vector2d spans; // spans
    
    /** Creates new DefaultSpanModel */
    public DefaultSpanModel(int rows, int columns) {
        spans = new Vector2d(rows, columns);
        
        // initalise spans
        for(int row=0; row < rows; row++){
            for(int col=0; col < columns; col++){
                spans.setElementAt(row, col, new AtomicCell(row, col));
            }
        }
    }
    
    public CellSpan getSpanAt(int row, int column){
        return (CellSpan)spans.getElementAt(row, column);
    }
    
    public void addSpan(int row, int column, int rowCount, int columnCount){
        CellSpan oldSpan = getSpanAt(row, column);
        int oldRowCount = oldSpan.getRowCount();
        int oldColumnCount = oldSpan.getColumnCount();
        
        int startRow = row;
        int startCol = column;
        
        for(int i=0; i < rowCount; i++){
            row = startRow + i;
            for(int j=0; j < columnCount; j++){
                column = startCol + j;
                spans.setElementAt(row, column, new ExtendCell(startRow, startCol, rowCount, columnCount));
            }
        }
        // notify listeners
        SpanModelEvent e = new SpanModelEvent(this, SpanModelEvent.SPAN_ADDED, row, column, oldRowCount, oldColumnCount, rowCount, columnCount);
        fireDataChanged(e);
    }

    class ExtendCell implements CellSpan{
        protected int row;
        protected int column;
        protected int rowCount;
        protected int columnCount;
        
        public ExtendCell(int row, int column, int rowCount, int columnCount){
            this.row = row;
            this.column = column;
            this.rowCount = rowCount;
            this.columnCount = columnCount;
        }
        
        public int getRow() {
            return row;
        }
        
        public int getColumn() {
            return column;
        }
        
        public int getRowCount() {
            return rowCount;
        }
        
        public int getColumnCount() {
            return columnCount;
        }
        
    }
}
