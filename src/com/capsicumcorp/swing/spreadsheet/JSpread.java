/* 
 * Copyright (c) 2002, Cameron Zemek
 * 
 * This file is part of JSpread.
 * 
 * JSpread is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * JSpread is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.capsicumcorp.swing.spreadsheet;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.StringTokenizer;
import java.awt.print.*;

import net.sf.jeppers.expression.*;
import net.sf.jeppers.grid.*;
import net.sf.jeppers.grid.print.*;

/**
 * Extends JGrid by adding spreadsheet functionality; printing, cut/copy/paste, row and column 
 * headers.
 * 
 * @version 1.0
 * 
 * @author  <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class JSpread extends JPanel implements SwingConstants, Printable {
	private JScrollPane scrollTable = null;
	private JGrid grid = null;

	private boolean showHeader = true; // header display flag

	// print variables
	transient private Printable printDelegate;

	// clipboard variables
	transient private Clipboard systemClipboard;
	transient private StringSelection stsel;

	public JSpread() {
		this(10, 10);
	}

	public JSpread(int rows, int columns) {
		super();
		init(new JGrid(rows, columns));
		
		
		
	}
	
	protected JSpread(JGrid grid) {
		super();
		init(grid);
	}
	
	private void init(JGrid grid) {
		// Add grid to panel
		scrollTable = new JScrollPane();
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 100));
		add(scrollTable, BorderLayout.CENTER);

		setGrid(grid);

		// Set system clipboard to use
		systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();		
	}

	protected void setGrid(JGrid grid) {
		this.grid = grid;
		scrollTable.setViewportView(grid);

		// Register editor & render for expressions
        DefaultStyleModel styleModel = (DefaultStyleModel) grid.getStyleModel();
        GridCellRenderer renderer = new ExpressionCellRenderer();
        styleModel.setRenderer(Expression.class, renderer);
        GridCellEditor editor = new ExpressionCellEditor();
        styleModel.setEditor(Expression.class, editor);
		
        // Setup print delegate
        printDelegate = new SimpleGridPrinter(grid);
		
		// Add headers to scrollTable
		setShowHeader(showHeader);

		// Add clipboard keyboard handlers
		Action cut = new AbstractAction("cut") {
			public void actionPerformed(ActionEvent evt) {
				cut();
			}
		};

		Action copy = new AbstractAction("copy") {
			public void actionPerformed(ActionEvent evt) {
				copy();
			}
		};

		Action paste = new AbstractAction("paste") {
			public void actionPerformed(ActionEvent evt) {
				paste();
			}
		};

		ActionMap actionMap = grid.getActionMap();
		actionMap.put(cut.getValue(Action.NAME), cut);
		actionMap.put(copy.getValue(Action.NAME), copy);
		actionMap.put(paste.getValue(Action.NAME), paste);

		InputMap inputMap = grid.getInputMap();
		inputMap.put(
			KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK),
			cut.getValue(Action.NAME));
		inputMap.put(
			KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK),
			copy.getValue(Action.NAME));
		inputMap.put(
			KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK),
			paste.getValue(Action.NAME));
	}

	public JSpread cloneView() {		
		return new JSpread(grid.cloneView());
	}	
	
	//
	// Print
	//
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException{
		return printDelegate.print(g, pageFormat, pageIndex);
	} //end print

	//
	// Clipboard functions
	//

	/** Cut the selected cells */
	public void cut() {
		SelectionModel sel = grid.getSelectionModel();
		int firstRow = sel.getFirstSelectedRow();
		int firstColumn = sel.getFirstSelectedColumn();
		int lastRow = sel.getLastSelectedRow();
		int lastColumn = sel.getLastSelectedColumn();
		cut(firstRow, firstColumn, lastRow, lastColumn);
	} //end cut

	/** Cut the cell at row, column */
	public void cut(int row, int column) {
		cut(row, column, row, column);
	} //end cut

	/** Cut the specified range of cells */
	public void cut(
		int firstRow,
		int firstColumn,
		int lastRow,
		int lastColumn) {
		//copy selection
		copy();

		// Clear selection
		GridModel model = grid.getGridModel();
		for (int row = firstRow; row <= lastRow; row++) {
			for (int column = firstColumn; column <= lastColumn; column++) {
				model.setValueAt(null, row, column);
			} //end for - column
		} //end for - row
	} //end cut

	/** Copy the selected cells */
	public void copy() {
		SelectionModel sel = grid.getSelectionModel();
		int firstRow = sel.getFirstSelectedRow();
		int firstColumn = sel.getFirstSelectedColumn();
		int lastRow = sel.getLastSelectedRow();
		int lastColumn = sel.getLastSelectedColumn();
		copy(firstRow, firstColumn, lastRow, lastColumn);
	} //end copy

	/** Copy the cell at row, column */
	public void copy(int row, int column) {
		copy(row, column, row, column);
	} //end copy

	/** Copy the specified range of cells */
	public void copy(
		int firstRow,
		int firstColumn,
		int lastRow,
		int lastColumn) {
		StringBuffer sbf = new StringBuffer();

		GridModel model = grid.getGridModel();
		for (int row = firstRow; row <= lastRow; row++) {
			for (int column = firstColumn; column <= lastColumn; column++) {
				sbf.append(model.getValueAt(row, column));
				if (column < lastColumn)
					sbf.append("\t");
			} //end for - column
			sbf.append("\n");
		} //end for - row
		stsel = new StringSelection(sbf.toString());

		systemClipboard.setContents(stsel, stsel);
	}

	/** Paste the clipboard cells */
	public void paste() {
		SelectionModel sel = grid.getSelectionModel();
		int firstRow = sel.getFirstSelectedRow();
		int firstColumn = sel.getFirstSelectedColumn();
		paste(firstRow, firstColumn);
	} //end paste

	/** Paste clipboard cells starting at row, column */
	public void paste(int startRow, int startColumn) {
		GridModel model = grid.getGridModel();
		try {
			String clipboard =
				(String) (systemClipboard
					.getContents(this)
					.getTransferData(DataFlavor.stringFlavor));
			StringTokenizer rowStringTokenizer =
				new StringTokenizer(clipboard, "\n");
			for (int row = startRow;
				rowStringTokenizer.hasMoreTokens() && row < model.getRowCount();
				row++) {
				String rowString = rowStringTokenizer.nextToken();
				StringTokenizer columnStringTokenizer =
					new StringTokenizer(rowString, "\t");
				for (int column = startColumn;
					columnStringTokenizer.hasMoreTokens()
						&& column < model.getColumnCount();
					column++) {
					String value = columnStringTokenizer.nextToken();
					model.setValueAt(value, row, column);
				} //end for - columnStringTokenizer
			} //end for - rowStringTokenizer
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	} //end paste

	public JGrid getGrid() {
		return grid;
	}

	//
	// Data access
	//

	/**
	 * Return the value of the cell at row, column
	 */
	public Object getValueAt(int row, int column) {
		return grid.getGridModel().getValueAt(row, column);
	} //end getValueAt

	/**
	 * Set the value of the cell at row, column
	 */
	public void setValueAt(Object value, int row, int column) {
		grid.getGridModel().setValueAt(value, row, column);

	} //end setValueAt

	/**
	 * Return true if the cell at row, column is editable
	 */
	public boolean isCellEditable(int row, int column) {
		return grid.getGridModel().isCellEditable(row, column);
	} //end isCellEditable

	public void setCellLock(boolean lock, int row, int column){
		((DefaultGridModel)grid.getGridModel()).setCellLock(lock, row, column);
	}

	/**
	 * Return the number of rows
	 */
	public int getRowCount() {
		return grid.getGridModel().getRowCount();
	} //end getRowCount

	/**
	 * Return the number of columns
	 */
	public int getColumnCount() {
		return grid.getGridModel().getColumnCount();
	} //end getColumnCount
	
	private void repaintHeaders(){
		boolean oldShowHeader = showHeader;
		setShowHeader(false);
		setShowHeader(oldShowHeader);
	}
	
	public void insertRows(int row, int rowCount){
		((ResizableGrid)grid.getGridModel()).insertRows(row, rowCount);
		repaintHeaders();
	}
	
	public void removeRows(int row, int rowCount) {
		((ResizableGrid)grid.getGridModel()).removeRows(row, rowCount);
		repaintHeaders();
	}

	public void insertColumns(int column, int columnCount) {
		((ResizableGrid)grid.getGridModel()).insertColumns(column, columnCount);
		repaintHeaders();
	}

	public void removeColumns(int column, int columnCount) {
		((ResizableGrid)grid.getGridModel()).removeColumns(column, columnCount);
		repaintHeaders();
	}

	//
	// Attributes
	//

	/**
	 * Return the style at cell row, column
	 */
	public CellStyle getStyleAt(int row, int column){
		return grid.getStyleModel().getCellStyle(row, column);
	}    
	
	/**
	 * Set the style at cell row, column
	 */
	public void setStyleAt(CellStyle style, int row, int column){
		((DefaultStyleModel)grid.getStyleModel()).setCellStyle(style, row, column);
	}
    
    /**
     * Return the default style
     */
    public CellStyle getDefaultCellStyle() {
        return ((DefaultStyleModel)grid.getStyleModel()).getDefaultCellStyle();
    }
    
    /**
     * Set the default style
     */
    public void setDefaultCellStyle(CellStyle style) {
        ((DefaultStyleModel)grid.getStyleModel()).setDefaultCellStyle(style);
    }
    
    boolean isCellSpan(int row, int column) {
        return grid.getSpanModel().isCellSpan(row, column);
    }

	/**
	 * Return the span over the cell at row, column
	 */
	public CellSpan getSpanOver(int row, int column) {
		return grid.getSpanModel().getSpanOver(row, column);
	} //end getSpanAt

	/**
	 * Span the specified range
	 */
	public void merge(CellSpan span) {
		((DefaultSpanModel) grid.getSpanModel()).addSpan(span);
	} //end merge
	
	/**
	 * Remove the specified span
	 */
	public void unmerge(CellSpan span){
		((DefaultSpanModel) grid.getSpanModel()).removeSpan(span);
	}

	/**
	 * Return the horizontal alignment of the cell at row, column
	 */
	public int getHorizontalAlignment(int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
		return style.getHorizontalAlignment();
	} //end getHorizontalAlignement

	/**
	 * Set the horizontal alignment of the cell at row, column
	 */
	public void setHorizontalAlignment(int hAlign, int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
	} //end setHorizontalAlignment

	/**
	 * Return the horizontal alignment of the cell at row, column
	 */
	public int getVerticalAlignment(int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
		return style.getVerticalAlignment();
	} //end getHorizontalAlignement

	/**
	 * Set the horizontal alignment of the cell at row, column
	 */
	public void setVerticalAlignment(int vAlign, int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
	} //end setHorizontalAlignment

	/**
	 * Return the foreground color of the cell at row, column
	 */
	public Color getForeground(int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
		return style.getForegroundColor();
	} //end getForeground

	/**
	 * Set the foreground color of the cell at row, column
	 */
	public void setForeground(Color color, int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
	} //end setForeground

	/**
	 * Return the background color of the cell at row, column
	 */
	public Color getBackground(int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
		return style.getBackgroundColor();
	} //end getBackground

	/**
	 * Set the background color of the cell at row, column
	 */
	public void setBackground(Color color, int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
	} //end setBackground

	/**
	 * Return the font for the cell at row, column
	 */
	public Font getFont(int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
		return style.getFont();
	} //end getFont

	/**
	 * Set the font for the cell at row, column
	 */
	public void setFont(Font font, int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
	} //end setFont

	/**
	 * Return the border for the cell at row, column
	 */
	public Border getBorder(int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
		return style.getBorder();
	} //end getBorder

	/**
	 * Set the border for the cell at row, column
	 */
	public void setBorder(Border border, int row, int column) {
		CellStyle style = grid.getStyleModel().getCellStyle(row, column);
	} //end setBorder

	/**
	 * Set the row height
	 */
	public void setRowHeight(int row, int height) {
		grid.setRowHeight(row, height);
	} //end setRowHeight

	/**
	 * Get the row height
	 */
	public int getRowHeight(int row) {
		return grid.getRowHeight(row);
	} //end getRowHeight

	/**
	 * Set the column width
	 */
	public void setColumnWidth(int column, int width) {
		grid.setColumnWidth(column, width);
	} //end setColumnWidth

	/**
	 * Get the column width
	 */
	public int getColumnWidth(int column) {
		return grid.getColumnWidth(column);
	} //end getColumnWidth

	/**
	 * Return true if grid is enabled
	 */
	public boolean getShowGrid() {
		return grid.getShowGrid();
	} //end getShowGrid

	/**
	 * Set whether the gridlines should be displayed
	 */
	public void setShowGrid(boolean showGrid) {
		grid.setShowGrid(showGrid);
	} //end setShowGrid

	/**
	* Return true if headers are enabled
	*/
	public boolean getShowHeader() {
		return showHeader;
	} //end isHeaderShown

	/**
	 * Set whether headers should be displayed
	 */
	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
		if (showHeader) {
			//Attach column headers
		   JGridHeader columnHeader =
				new JGridHeader(grid, SwingConstants.HORIZONTAL);
			JViewport viewport = new JViewport();
			viewport.setView(columnHeader);
			viewport.setPreferredSize(columnHeader.getPreferredSize());
			scrollTable.setColumnHeaderView(viewport);
			//Attach row headers
			JGridHeader rowHeader =
				new JGridHeader(grid, SwingConstants.VERTICAL);
			viewport = new JViewport();
			viewport.setView(rowHeader);
			viewport.setPreferredSize(rowHeader.getPreferredSize());
			scrollTable.setRowHeaderView(viewport);
		} else {
			scrollTable.setRowHeaderView(null);
			scrollTable.setColumnHeaderView(null);			
		}
		repaint();
	} //end setShowHeader
} // end class
