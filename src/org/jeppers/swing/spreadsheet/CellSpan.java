package org.jeppers.swing.spreadsheet;
/*
 * CellSpan.java
 *
 * Created on 4 July 2002, 12:51
 */

/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public interface CellSpan {
    public int getRow();
    
    public int getColumn();
    
    public int getRowCount();
    
    public int getColumnCount();
}
