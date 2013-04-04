package org.jeppers.swing.spreadsheet;
/*
 * SpanModelListener.java
 *
 * Created on 4 July 2002, 12:47
 */
import java.util.EventListener;

/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public interface SpanModelListener extends EventListener{
    /** Called when a span is modified, added or removed. */
    public void spanDataChanged(SpanModelEvent e);
    
    /** Called whenever the span model changes globally. */
    public void spanModelChanged(SpanModelEvent e); 
    
    /** Called when the shape of the underlying data model is modified 
        (rows or columns inserted or removed). */
    public void spanStructureChanged(SpanModelEvent e);
}

