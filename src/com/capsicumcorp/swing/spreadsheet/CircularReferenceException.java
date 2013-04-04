/*
 * Created on 13/04/2005
 */
package com.capsicumcorp.swing.spreadsheet;

/**
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class CircularReferenceException extends Exception {
    public CircularReferenceException() {
        super("Circular Reference");
    }
}
