/*
 * DefaultAttributeModel.java
 *
 * Created on 8 July 2002, 19:06
 */

package org.jeppers.swing.spreadsheet;
import org.jeppers.util.Vector2d;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.*;

/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public class DefaultAttributeModel extends AbstractAttributeModel{
    //
    // Defaults
    //
    protected final static Font   DEFAULT_FONT        = new Font("Dialog", Font.PLAIN, 12);
    protected final static Color  DEFAULT_FOREGROUND  = Color.black;
    protected final static Color  DEFAULT_BACKGROUND  = Color.white;
    protected final static int    DEFAULT_HORIZONTALALIGNMENT = AttributeModel.LEFT;
    protected final static int    DEFAULT_VERTICALALIGNMENT   = javax.swing.SwingConstants.BOTTOM;
    
    //
    // Instance variables
    //
    
    /** Fonts */
    protected Vector2d font;
    
    /** Foreground colors */
    protected Vector2d fgColor;
    
    /** Background colors */
    protected Vector2d bgColor;
    
    /** Borders */
    protected Vector2d borders;
    
    /** Horizontal alignment */
    protected Vector2d horizontalAlignment;
    
    /** Vertical alignment */
    protected Vector2d verticalAlignment;
    
    //
    // Methods
    //
    
    /** Creates new DefaultAttributeModel */
    public DefaultAttributeModel(int rows, int columns) {
        font = new Vector2d(rows, columns);
        fgColor = new Vector2d(rows, columns);
        bgColor = new Vector2d(rows, columns);
        borders = new Vector2d(rows, columns);        
        horizontalAlignment = new Vector2d(rows, columns);
        verticalAlignment = new Vector2d(rows, columns);
        
        EmptyBorder defaultBorder = new EmptyBorder(2, 2, 2, 2);
        
        Integer defaultHorizontalAlignment  = new Integer(DEFAULT_HORIZONTALALIGNMENT);
        Integer defaultVerticalAlignment    = new Integer(DEFAULT_VERTICALALIGNMENT);
        
        // initialise Vector2ds
        for(int row=0; row < rows; row++){
            for(int col=0; col < columns; col++){
                font.setElementAt(row, col, DEFAULT_FONT);
                fgColor.setElementAt(row, col, DEFAULT_FOREGROUND);
                bgColor.setElementAt(row, col, DEFAULT_BACKGROUND);
                borders.setElementAt(row, col, defaultBorder);
                horizontalAlignment.setElementAt(row, col, defaultHorizontalAlignment);
                verticalAlignment.setElementAt(row, col, defaultVerticalAlignment);
            }
        }
    }
    
    /** Get the font of cell (row, column)  */
    public Font getFont(int row, int column) {
        return (Font) font.getElementAt(row, column);
    }
    
    /** Set the font of cell (row, column) */
    public void setFont(int row, int column, Font cellFont){
        font.setElementAt(row, column, cellFont);
        AttributeModelEvent e = new AttributeModelEvent(this, AttributeModelEvent.CELLS_UPDATED, row, column, 1, 1);
        fireDataChanged(e);
    }
    
    /** Get the foreground color of cell (row, column)  */
    public Color getForeground(int row, int column) {
        return (Color) fgColor.getElementAt(row, column);
    }
    
    /** Set the foreground color of cell (row, column) */
    public void setForeground(int row, int column, Color foregroundColor){
        fgColor.setElementAt(row, column, foregroundColor);
        AttributeModelEvent e = new AttributeModelEvent(this, AttributeModelEvent.CELLS_UPDATED, row, column, 1, 1);
        fireDataChanged(e);
    }
    
    /** Get the border for cell (row, column) */
    public Border getBorder(int row, int column){
        return (Border) borders.getElementAt(row, column);
    }
    
    /** Set the border for cell (row, column) */
    public void setBorder(int row, int column, Border border){
        borders.setElementAt(row, column, border);

        // Set adjancent cells        
        
        AttributeModelEvent e = new AttributeModelEvent(this, AttributeModelEvent.CELLS_UPDATED, row, column, 1, 1);
        fireDataChanged(e);
    }
    
    /** Get the vertical alignment of cell (row, column)  */
    public int getVerticalAlignment(int row, int column) {
        Integer verticalAlign = (Integer) verticalAlignment.getElementAt(row, column);
        return verticalAlign.intValue();
    }
    
    /** Set the vertical alignment of cell (row, column) */
    public void setVerticalAlignment(int row, int column, int verticalAlign){
        verticalAlignment.setElementAt(row, column, new Integer(verticalAlign));
        AttributeModelEvent e = new AttributeModelEvent(this, AttributeModelEvent.CELLS_UPDATED, row, column, 1, 1);
        fireDataChanged(e);
    }
    
    /** Get the horizontal alignment of cell (row, column)  */
    public int getHorizontalAlignment(int row, int column) {
        Integer horizontalAlign = (Integer) horizontalAlignment.getElementAt(row, column);
        return horizontalAlign.intValue();
    }
    
    /** Set the horizontal alignment of cell (row, column) */
    public void setHorizontalAlignment(int row, int column, int horizontalAlign){
        horizontalAlignment.setElementAt(row, column, new Integer(horizontalAlign));
        AttributeModelEvent e = new AttributeModelEvent(this, AttributeModelEvent.CELLS_UPDATED, row, column, 1, 1);
        fireDataChanged(e);
    }
    
    /** Get the background color of cell (row, column)  */
    public Color getBackground(int row, int column) {
        return (Color) bgColor.getElementAt(row, column);
    }
    
    /** Set the background color of cell (row, column) */
    public void setBackground(int row, int column, Color color){
        bgColor.setElementAt(row, column, color);
        AttributeModelEvent e = new AttributeModelEvent(this, AttributeModelEvent.CELLS_UPDATED, row, column, 1, 1);
        fireDataChanged(e);
    }
}
