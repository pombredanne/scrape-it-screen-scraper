package org.jeppers.swing.spreadsheet;
/*
 * SpanModelEvent.java
 *
 * Created on 4 July 2002, 13:15
 */

/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public class SpanModelEvent extends java.util.EventObject{
    //
    // Event types
    //
    
    /** A continuous set of columns was deleted from the model */
    public final static int COLUMNS_DELETED  = 1;
    /** A continuous set of columns was added to the model */
    public final static int COLUMNS_INSERTED = 2;
    /** A continuous set of rows was deleted from the model */
    public final static int ROWS_DELETED     = 3;
    /** A continuous set of rows was added to the model */
    public final static int ROWS_INSERTED    = 4;
    /** A span was added */
    public final static int SPAN_ADDED       = 5;
    /** A span was removed */
    public final static int SPAN_REMOVED     = 6;
    /** A span was modified */
    public final static int SPAN_UPDATED     = 7;
    /** The entire model was changed */
    public final static int MODEL_CHANGED    = 8;

    //
    // Instance variables
    //
    private SpanModel source;
    private int       type;
    private int       anchorRow;
    private int       anchorColumn;
    private int       oldRowCount;
    private int       oldColumnCount;
    private int       newRowCount;
    private int       newColumnCount;
    
    /** Creates new SpanModelEvent */
    public SpanModelEvent(SpanModel source, int type, int anchorRow, int anchorColumn, 
                        int oldRowCount, int oldColumnCount, int newRowCount, int newColumnCount) {
        super(source);                    
        this.source = source;
        this.type = type;
        this.anchorRow = anchorRow;
        this.anchorColumn = anchorColumn;
        this.oldRowCount = oldRowCount;
        this.oldColumnCount = oldColumnCount;
        this.newRowCount = newRowCount;
        this.newColumnCount = newColumnCount;
    }

    /** Returns the anchor (left) column of the span */
    public int getAnchorColumn(){
        return anchorColumn;
    }
    
    /** Returns the anchor (top) row of the span event */
    public int getAnchorRow(){
        return anchorRow;
    }
    
    /** Valid for SPAN_UPDATED, SPAN_ADDED, COLUMNS_INSERTED. */
    public int getNewColumnCount(){
        return newColumnCount;
    }
    
    /** Valid for SPAN_UPDATED, SPAN_ADDED, ROWS_INSERTED. */
    public int getNewRowCount(){
        return newRowCount;
    }
    
    /** Valid for SPAN_UPDATED, SPAN_REMOVED, COLUMNS_DELETED. */
    public int getOldColumnCount(){
        return oldColumnCount;
    }
    
    /** Valid for SPAN_UPDATED, SPAN_REMOVED, ROWS_DELETED. */
    public int getOldRowCount(){
        return oldRowCount;
    }
    
    /** 
     *   Returns the type of the event, one of: MODEL_CHANGED SPAN_UPDATED 
     *   SPAN_ADDED SPAN_REMOVED ROWS_INSERTED ROWS_DELETED COLUMNS_INSERTED COLUMNS_DELETED 
     */
    public int getType(){
        return type;
    }
}
