package org.jeppers.swing.spreadsheet;
/*
 * AttributeModel.java
 *
 * Created on 4 July 2002, 12:40
 */
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public interface AttributeModel{
    /** Align to top edge */
    public final static int TOP     = SwingConstants.TOP;
    /** Align to center */
    public final static int CENTER  = SwingConstants.CENTER;
    /** Align to bottom edge */
    public final static int BOTTOM  = SwingConstants.BOTTOM;
    /** Align to left edge */
    public final static int LEFT    = SwingConstants.LEFT;
    /** Align to right edge */
    public final static int RIGHT   = SwingConstants.RIGHT;
    
    /** Get the background color of cell (row, column) */
    public Color getBackground(int row, int column);
    
    /** Get the foreground color of cell (row, column) */
    public Color getForeground(int row, int column);
    
    /** Get the border for cell (row, column) */
    public Border getBorder(int row, int column);
    
    /** Get the font of cell (row, column) */
    public Font getFont(int row, int column);
    
    /** Get the horizontal alignment of cell (row, column) */
    public int getHorizontalAlignment(int row, int column);
    
    /** Get the vertical alignment of cell (row, column) */
    public int getVerticalAlignment(int row, int column);
    
    /** Add listener to model */
    public void addAttributeModelListener(AttributeModelListener l);
    
    /** Remove listener from model */
    public void removeAttributeModelListener(AttributeModelListener l);
}