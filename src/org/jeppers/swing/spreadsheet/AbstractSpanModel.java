package org.jeppers.swing.spreadsheet;
/*
 * AbstractSpanModel.java
 *
 * Created on 4 July 2002, 12:44
 */
import javax.swing.*;
import javax.swing.event.*;
import java.util.EventListener;

/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public abstract class AbstractSpanModel implements SpanModel{
    /** List of listeners */
    protected EventListenerList listenerList = new EventListenerList();
    
    /**
     *  This implementation is provided so users don't have to implement
     *  this method if spaning is not required. It returns mono-span cells.
     */
    public CellSpan getSpanAt(int row, int column){
        AtomicCell span = new AtomicCell(row, column);
        return ((CellSpan)span);
    }
    
    //
    // Manage Listeners
    //
    
    /** Add span model listener */
    public void addSpanModelListener(SpanModelListener listener){
        listenerList.add(SpanModelListener.class, listener);
    }
    
    /** Remove span model listener */
    public void removeSpanModelListener(SpanModelListener listener){
        listenerList.remove(SpanModelListener.class, listener);
    }
    
    //
    // Fire methods
    //
    
    /**
     * Forwards the given notification event to all
     * <code>SpanModelListeners</code> that registered
     * themselves as listeners for this span model.
     *
     * @param e  the event to be forwarded
     *
     * @see #addSpanModelListener
     * @see SpanModelEvent
     * @see EventListenerList
     */
    public void fireDataChanged(SpanModelEvent e){
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==SpanModelListener.class) {
                ((SpanModelListener)listeners[i+1]).spanDataChanged(e);
            }
        }
    }
    
    /**
     * Forwards the given notification event to all
     * <code>SpanModelListeners</code> that registered
     * themselves as listeners for this span model.
     *
     * @param e  the event to be forwarded
     *
     * @see #addSpanModelListener
     * @see SpanModelEvent
     * @see EventListenerList
     */
    public void fireStructureChanged(SpanModelEvent e){
                // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==SpanModelListener.class) {
                ((SpanModelListener)listeners[i+1]).spanStructureChanged(e);
            }
        }
    }
    
        /**
     * Forwards the given notification event to all
     * <code>SpanModelListeners</code> that registered
     * themselves as listeners for this span model.
     *
     * @param e  the event to be forwarded
     *
     * @see #addSpanModelListener
     * @see SpanModelEvent
     * @see EventListenerList
     */
    public void fireModelChanged(SpanModelEvent e){
                // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==SpanModelListener.class) {
                ((SpanModelListener)listeners[i+1]).spanModelChanged(e);
            }
        }
    }
    
    /**
     * Returns an array of all the listeners of the given type that
     * were added to this model.
     *
     * @returns all of the objects receiving <code>listenerType</code>
     *		notifications from this model
     */
    public EventListener[] getListeners(Class listenerType) {
        return listenerList.getListeners(listenerType);
    }
}
