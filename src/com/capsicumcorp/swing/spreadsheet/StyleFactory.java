/*
 * Created on 16/03/2005
 */
package com.capsicumcorp.swing.spreadsheet;

import java.awt.Color;
import java.awt.Font;

import net.sf.jeppers.grid.CellStyle;
import net.sf.jeppers.grid.DefaultCellStyle;

/**
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class StyleFactory {
    static private ObjectCache fontCache = new ObjectCache();
    static private ObjectCache colorCache = new ObjectCache();
    
    public CellStyle getCachedStyle(CellStyle style) {
        CellStyle cacheStyle = new DefaultCellStyle(style);
        // Replace attributes with cached instances
        cacheStyle.setFont((Font) fontCache.get(style.getFont()));
        cacheStyle.setBackgroundColor((Color) colorCache.get(style.getBackgroundColor()));
        cacheStyle.setForegroundColor((Color) colorCache.get(style.getForegroundColor()));
        return cacheStyle;
    }
}
