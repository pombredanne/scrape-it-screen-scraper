package org.jeppers.swing.spreadsheet;

/*
 * AttributeModelEvent.java
 *
 * Created on 8 July 2002, 18:18
 */

/**
 * @author  Cameron Zemek
 * @version 1.0
 */
public class AttributeModelEvent extends java.util.EventObject{
    //
    // Event types
    //
    
    /** The model shape was not modified, but some cells were updated */
    public final static int CELLS_UPDATED       = 0;    
    
    /** A continuous set of columns was deleted from the model */
    public final static int COLUMNS_DELETED     = 1;
    
    /** A continuous set of columns was added to the model */
    public final static int COLUMNS_INSERTED    = 2;
    
    /** The model shape was not modified, but some columns were updated */
    public final static int COLUMNS_UPDATED     = 3;
    
    /** The entire model was changed */
    public final static int MODEL_CHANGED       = 4;
    
    /** A continuous set of rows was deleted from the model */
    public final static int ROWS_DELETED        = 5;
    
    /** A continuous set of rows was added to the model */
    public final static int ROWS_INSERTED       = 6;
    
    /** The model shape was not modified, but some rows were updated */
    public final static int ROWS_UPDATED        = 7;

    //
    // Instance variables
    // 
    private AttributeModel  source;
    private int             type;
    private int             firstRow;
    private int             firstColumn;
    private int             rowCount;
    private int             columnCount;
    
    //
    // Methods
    //
    
    /** Creates new AttributeModelEvent */
    public AttributeModelEvent(AttributeModel source, int type, int firstRow, int firstColumn, int rowCount, int columnCount) {
        super(source);
        this.source      = source;
        this.type        = type;
        this.firstRow    = firstRow;
        this.firstColumn = firstColumn;
        this.rowCount    = rowCount;
        this.columnCount = columnCount;
    }

    public int getColumnCount(){
        return columnCount;
    }
    
    public int getFirstColumn(){
        return firstColumn;
    }
    
    public int getFirstRow(){
        return firstRow;
    }
    
    public int getLastColumn(){
        return (firstColumn + columnCount - 1);
    }
    
    public int getLastRow(){
        return (firstRow + rowCount - 1);
    }
    
    public int getRowCount(){
        return rowCount;
    }
    
    public int getType(){
        return type;
    }
}
