package org.jeppers.swing.spreadsheet;

public class CellRange {
    protected int firstRow;
    protected int firstColumn;
    protected int lastRow;
    protected int lastColumn;
    
    /** Construct general cell range */
    public CellRange(int firstRow, int firstColumn, int lastRow, int lastColumn){
        this.firstRow = firstRow;
        this.firstColumn = firstColumn;
        this.lastRow = lastRow;
        this.lastColumn = lastColumn;
    }    
    
    public int getRowCount() {
        return (lastRow - firstRow + 1);
    }
    
    public int getColumnCount() {
        return (lastColumn - firstColumn + 1);
    }
    
    public int getFirstRow() {
        return firstRow;
    }
    
    public int getFirstColumn() {
        return firstColumn;
    }
    
    public int getLastRow() {
        return lastRow;
    }
    
    public int getLastColumn() {
        return lastColumn;
    }
}
