package org.jeppers.swing.spreadsheet;
/*
 * SpanModel.java
 *
 * Created on 4 July 2002, 12:40
 */

/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public interface SpanModel {
    /** Return the cellspan for (row, column) */
    public CellSpan getSpanAt(int row, int column);
    
    /** Add span model listener */
    public void addSpanModelListener(SpanModelListener listener);
    
    /** Remove span model listener */
    public void removeSpanModelListener(SpanModelListener listener);
}

