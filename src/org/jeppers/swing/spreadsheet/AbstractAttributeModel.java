/*
 * AbstractAttributeModel.java
 *
 * Created on 8 July 2002, 18:46
 */

package org.jeppers.swing.spreadsheet;
import javax.swing.*;
import javax.swing.event.*;
import java.util.EventListener;

/**
 *
 * @author  Cameron Zemek
 * @version 1.0
 */
public abstract class AbstractAttributeModel implements AttributeModel{
    /** List of listeners */
    protected EventListenerList listenerList = new EventListenerList();
    
    //
    // Manage Listeners
    //
    
    /** Add span model listener */
    public void addAttributeModelListener(AttributeModelListener listener){
        listenerList.add(AttributeModelListener.class, listener);
    }
    
    /** Remove span model listener */
    public void removeAttributeModelListener(AttributeModelListener listener){
        listenerList.remove(AttributeModelListener.class, listener);
    }
    
    //
    // Fire methods
    //
    
    /**
     * Forwards the given notification event to all
     * <code>AttributeModelListeners</code> that registered
     * themselves as listeners for this span model.
     *
     * @param e  the event to be forwarded
     *
     * @see #addAttributeModelListener
     * @see AttributeModelEvent
     * @see EventListenerList
     */
    public void fireDataChanged(AttributeModelEvent e){
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==AttributeModelListener.class) {
                ((AttributeModelListener)listeners[i+1]).attributeDataChanged(e);                
            }
        }
    }
    
    /**
     * Forwards the given notification event to all
     * <code>AttributeModelListeners</code> that registered
     * themselves as listeners for this span model.
     *
     * @param e  the event to be forwarded
     *
     * @see #addAttributeModelListener
     * @see AttributeModelEvent
     * @see EventListenerList
     */
    public void fireStructureChanged(AttributeModelEvent e){
                // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==AttributeModelListener.class) {
                ((AttributeModelListener)listeners[i+1]).attributeStructureChanged(e);
            }
        }
    }
    
    /**
     * Forwards the given notification event to all
     * <code>AttributeModelListeners</code> that registered
     * themselves as listeners for this span model.
     *
     * @param e  the event to be forwarded
     *
     * @see #addAttributeModelListener
     * @see AttributeModelEvent
     * @see EventListenerList
     */
    public void fireModelChanged(AttributeModelEvent e){
                // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==AttributeModelListener.class) {
                ((AttributeModelListener)listeners[i+1]).attributeModelChanged(e);
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
