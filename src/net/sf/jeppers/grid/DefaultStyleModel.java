/* 
 * Copyright (c) 2002, Cameron Zemek
 * 
 * This file is part of JGrid.
 * 
 * JGrid is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * JGrid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package net.sf.jeppers.grid;

import java.awt.*;
import java.io.Serializable;

import javax.swing.*;
import java.util.*;

/**
 * Default implementation of <code>StyleModel</code> that uses a <code>Map</code>
 * between class types and editors / renderers.
 * 
 * @author <a href="grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class DefaultStyleModel
	extends AbstractStyleModel
	implements ResizableGrid, Serializable {
	private HashMap editors = new HashMap();
	private HashMap renderers = new HashMap();
	private CellStyle defaultStyle;

	private Map styleMap = new HashMap();

	public DefaultStyleModel() {
		createDefaults();
	}

	private void createDefaults() {
		GridCellRenderer defaultRenderer = new GenericCellRenderer();
		renderers.put(Object.class, defaultRenderer);
		GridCellEditor defaultEditor = new GenericCellEditor(new JTextField());
		editors.put(Object.class, defaultEditor);		
		defaultStyle = new DefaultCellStyle();		
	}
	
	public GridCellEditor getEditor(
		Class aClass,
		int row,
		int column,
		JGrid grid) {
		GridCellEditor editor = (GridCellEditor) editors.get(aClass);
		if (editor != null) {
			return editor;
		} else {
			return getEditor(aClass.getSuperclass(), row, column, grid);
		}
	}

	public void setEditor(java.lang.Class clazz, GridCellEditor editor) {
		editors.put(clazz, editor);
	}

	public GridCellRenderer getRenderer(
		Class aClass,
		int row,
		int column,
		JGrid grid) {
		GridCellRenderer renderer = (GridCellRenderer) renderers.get(aClass);
		if (renderer != null) {
			return renderer;
		} else {
			return getRenderer(aClass.getSuperclass(), row, column, grid);
		}
	}

	public void setRenderer(java.lang.Class clazz, GridCellRenderer renderer) {
		renderers.put(clazz, renderer);
	}

	private void updateSubComponentUI(Object componentShell) {
		if (componentShell == null) {
			return;
		}
		Component component = null;
		if (componentShell instanceof Component) {
			component = (Component)componentShell;
		}
		if (componentShell instanceof GenericCellEditor) {
			component = ((GenericCellEditor)componentShell).getComponent();
		}

		if (component != null && component instanceof JComponent) {
			((JComponent)component).updateUI();
		}
	}

	public void updateUI() {
		Iterator rendererIter = renderers.values().iterator();
		while (rendererIter.hasNext()) {
			updateSubComponentUI(rendererIter.next());
		}
		
		Iterator editorIter = editors.values().iterator();
		while (editorIter.hasNext()) {
			updateSubComponentUI(editorIter.next());
		}
	}

	public CellStyle getDefaultCellStyle() {
		return defaultStyle;
	}
    
    public void setDefaultCellStyle(CellStyle defaultStyle) {
        this.defaultStyle = defaultStyle;
    }

	public CellStyle getCellStyle(int row, int column) {
		CellStyle style = (CellStyle) styleMap.get(new Point(column, row));
		if (style == null) {
			return getDefaultCellStyle();
		}
		return style;
	}

	public void setCellStyle(CellStyle style, int row, int column) {
		styleMap.put(new Point(column, row), style);
		fireCellStyleChanged(row, column);
	}
	
	public void insertRows(int row, int rowCount){
		Map oldStyleMap = styleMap;
		styleMap = new HashMap();
		Iterator i = oldStyleMap.keySet().iterator();
		while(i.hasNext()){
			Point cell = (Point) i.next();
			CellStyle style = (CellStyle) oldStyleMap.get(cell);
			if(cell.y < row){
				// leave style in same position
				styleMap.put(cell, style);
			}else{
				// move style down
				cell.y += rowCount;
				styleMap.put(cell, style);
			}		
		}
	}
	
	public void removeRows(int row, int rowCount){
		Map oldStyleMap = styleMap;
		styleMap = new HashMap();
		Iterator i = oldStyleMap.keySet().iterator();
		while(i.hasNext()){
			Point cell = (Point) i.next();
			CellStyle style = (CellStyle) oldStyleMap.get(cell);
			if(cell.y < row){
				// leave style in same position
				styleMap.put(cell, style);
			}else{
				// move style up
				cell.y -= rowCount;
				styleMap.put(cell, style);
			}		
		}
	}
	
	public void insertColumns(int column, int columnCount){
		Map oldStyleMap = styleMap;
		styleMap = new HashMap();
		Iterator i = oldStyleMap.keySet().iterator();
		while(i.hasNext()){
			Point cell = (Point) i.next();
			CellStyle style = (CellStyle) oldStyleMap.get(cell);
			if(cell.x < column){
				// leave style in same position
				styleMap.put(cell, style);
			}else{
				// move style right
				cell.x += columnCount;
				styleMap.put(cell, style);
			}		
		}
	}
	
	public void removeColumns(int column, int columnCount){
		Map oldStyleMap = styleMap;
		styleMap = new HashMap();
		Iterator i = oldStyleMap.keySet().iterator();
		while(i.hasNext()){
			Point cell = (Point) i.next();
			CellStyle style = (CellStyle) oldStyleMap.get(cell);
			if(cell.x < column){
				// leave style in same position
				styleMap.put(cell, style);
			}else{
				// move style left
				cell.x -= columnCount;
				styleMap.put(cell, style);
			}		
		}
	}
}
