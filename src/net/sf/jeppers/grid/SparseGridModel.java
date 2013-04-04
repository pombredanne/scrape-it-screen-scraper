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

import java.awt.Point;
import java.io.Serializable;
import java.util.*;

/**
 * This is an implementation of <code>GridModel</code> that uses
 * a <code>Map</code> of cell points to the cell object values.
 * 
 * @author <a href="grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class SparseGridModel
	extends AbstractGridModel
	implements ResizableGrid, Serializable {
	private int rowSize;
	private int colSize;
	private HashMap data = new HashMap();
	private HashSet lockedCells = new HashSet();

	private Point cell = new Point();

	public SparseGridModel(int rows, int columns) {
		rowSize = rows;
		colSize = columns;
	}

	public int getRowCount() {
		return rowSize;
	}

	public int getColumnCount() {
		return colSize;
	}

	public Object getValueAt(int row, int column) {
		cell.x = column;
		cell.y = row;
		return data.get(cell);
	}

	public boolean isCellEditable(int row, int column) {
		cell.x = column;
		cell.y = row;
		return !lockedCells.contains(cell);
	}

	/**
	 * Set whether cell at <code>row</code> and <code>column</code> 
	 * can be editted.
	 * 
	 * @param lock		lock flag of cell
	 * @param row		row index of cell
	 * @param column	column index of cell
	 */
	public void setCellLock(boolean lock, int row, int column) {
		if (lock) {
			lockedCells.add(new Point(column, row));
		} else {
			lockedCells.remove(new Point(column, row));
		}
	}

	public void setValueAt(Object value, int row, int column) {
		if (isCellEditable(row, column)) {
			data.put(new Point(column, row), value);

			// Notify listeners
			fireGridCellUpdated(row, column);
		}
	}

	public void insertRows(int row, int rowCount) {
		Map oldData = data;
		data = new HashMap();
		Iterator i = oldData.keySet().iterator();
		while (i.hasNext()) {
			Point cell = (Point) i.next();
			Object value = oldData.get(cell);
			if (cell.y < row) {
				// leave style in same position
				data.put(cell, value);
			} else {
				// move style down
				cell.y += rowCount;
				data.put(cell, value);
			}
		}
		
		Set oldLockedCells = lockedCells;
		lockedCells = new HashSet();
		Iterator j = oldLockedCells.iterator();
		while(j.hasNext()){
			Point cell = (Point) j.next();
			if (cell.y < row) {
				// leave lock in same position
				lockedCells.add(cell);
			} else {
				// move lock down
				cell.y += rowCount;
				lockedCells.add(cell);	
			}
		}
		rowSize += rowCount;
		fireGridRowsInserted(row, (row + rowCount - 1));
	}

	public void removeRows(int row, int rowCount) {
		Map oldData = data;
		data = new HashMap();
		Iterator i = oldData.keySet().iterator();
		while (i.hasNext()) {
			Point cell = (Point) i.next();
			Object value = oldData.get(cell);
			if (cell.y < row) {
				// leave style in same position
				data.put(cell, value);
			} else {
				// move style up
				cell.y -= rowCount;
				data.put(cell, value);
			}
		}
		
		Set oldLockedCells = lockedCells;
		lockedCells = new HashSet();
		Iterator j = oldLockedCells.iterator();
		while(j.hasNext()){
			Point cell = (Point) j.next();
			if (cell.y < row) {
				// leave lock in same position
				lockedCells.add(cell);
			} else {
				// move lock down
				cell.y -= rowCount;
				lockedCells.add(cell);	
			}
		}
		rowSize -= rowCount;
		fireGridRowsDeleted(row, (row + rowCount - 1));
	}

	public void insertColumns(int column, int columnCount) {
		Map oldData = data;
		data = new HashMap();
		Iterator i = oldData.keySet().iterator();
		while (i.hasNext()) {
			Point cell = (Point) i.next();
			Object value = oldData.get(cell);
			if (cell.x < column) {
				// leave style in same position
				data.put(cell, value);
			} else {
				// move style right
				cell.x += columnCount;
				data.put(cell, value);
			}
		}
		
		Set oldLockedCells = lockedCells;
		lockedCells = new HashSet();
		Iterator j = oldLockedCells.iterator();
		while(j.hasNext()){
			Point cell = (Point) j.next();
			if (cell.x < column) {
				// leave lock in same position
				lockedCells.add(cell);
			} else {
				// move lock right
				cell.x += columnCount;
				lockedCells.add(cell);	
			}
		}
		colSize += columnCount;
		fireGridColumnsInserted(column, (column + columnCount - 1));
	}

	public void removeColumns(int column, int columnCount) {
		Map oldData = data;
		data = new HashMap();
		Iterator i = oldData.keySet().iterator();
		while (i.hasNext()) {
			Point cell = (Point) i.next();
			Object value = oldData.get(cell);
			if (cell.x < column) {
				// leave style in same position
				data.put(cell, value);
			} else {
				// move style left
				cell.x -= columnCount;
				data.put(cell, value);
			}
		}
		
		Set oldLockedCells = lockedCells;
		lockedCells = new HashSet();
		Iterator j = oldLockedCells.iterator();
		while(j.hasNext()){
			Point cell = (Point) j.next();
			if (cell.x < column) {
				// leave lock in same position
				lockedCells.add(cell);
			} else {
				// move lock left
				cell.x -= columnCount;
				lockedCells.add(cell);	
			}
		}
		colSize -= columnCount;
		fireGridColumnsDeleted(column, (column + columnCount - 1));
	}
}
