package org.jeppers.swing.spreadsheet;
/*
 * AtomicCell.java
 *
 * Created on 4 July 2002, 12:56
 */

/**
 *
 * @author  Cameron Zemek
 * @version 
 */
public class AtomicCell implements CellSpan{
    protected int row;
    protected int column;

    /** Creates new CellSpan */
    public AtomicCell(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getColumn(){
        return column;
    }
    
    public int getRowCount(){
        return 1;
    }
    
    public int getColumnCount(){
        return 1;
    }
}
