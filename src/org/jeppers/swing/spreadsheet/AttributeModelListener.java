package org.jeppers.swing.spreadsheet;

/*
 * AttributeModelListener.java
 *
 * Created on 4 July 2002, 12:40
 */
import java.util.EventListener;
/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public interface AttributeModelListener extends EventListener{
    /** Called when an atttribute is modified */
    public void attributeDataChanged(AttributeModelEvent e);
    
    /** Called whenever the attibute model changes globally. */
    public void attributeModelChanged(AttributeModelEvent e); 
    
    /** Called when the shape of the underlying data model is modified 
        (rows or columns inserted or removed). */
    public void attributeStructureChanged(AttributeModelEvent e);
}