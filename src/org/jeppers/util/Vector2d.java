package org.jeppers.util;

import java.util.Vector;

public class Vector2d{
    //Instance variables
    private Vector dataVector; // holds row vectors    
       
    /**
     * Initialise a Vector2d of size rows x cols
     */
    public Vector2d(int rows, int cols){
        dataVector = new Vector();
        dataVector.setSize(rows);
        // add a vector for each row
        for(int i=0; i<rows; i++){
            Vector rowVector = new Vector();
            rowVector.setSize(cols);
            dataVector.setElementAt(rowVector, i);
        }
    }
    
    /**
     * Add an element to the Vector2d at row, col
     */
    public void setElementAt(int row, int col, Object element){
        Vector rowVector = (Vector) dataVector.get(row);
        rowVector.setElementAt(element, col);
    }
    
    /**
     * Get the element in the Vector2d at row, col
     */
    public Object getElementAt(int row, int col){
        Vector rowVector = (Vector) dataVector.get(row);
        return rowVector.get(col);
    }
    
    /**
     * Return the number of rows
     */
    public int getRowCount(){
        return dataVector.size();
    }
    
    /**
     * Return the number of columns
     */
    public int getColumnCount(){
        Vector rowVector = (Vector) dataVector.get(0);
        return rowVector.size();
    }
}