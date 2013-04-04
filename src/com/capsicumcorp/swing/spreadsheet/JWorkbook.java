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
import javax.swing.*;
import java.util.*;

/**
 * JWorkbook represents a number of worksheets (driven by <code>JSpread</code>) and 
 * adds formula support.
 * 
 * @version 1.0
 * 
 * @author  <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class JWorkbook extends JPanel {
	private JTabbedPane workbook =
		new JTabbedPane(JTabbedPane.BOTTOM, JTabbedPane.SCROLL_TAB_LAYOUT);
	private Workbook formulaHandler = new Workbook();
	private Hashtable worksheets = new Hashtable();
	private ArrayList sheetNames = new ArrayList(); // ordered list of sheet names
	
	/** Creates a new instance of JWorkbook */
	public JWorkbook() {
		super();

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(400, 100));
		add(workbook, BorderLayout.CENTER);
	}	

	/** Add new sheet */
	public void addSheet(String sheetName, JSpread sheet) {		
		sheetNames.add(sheetName);
		//Convert name that is suitable for expressions
		String displayName = sheetName;		
		sheetName = sheetName.replace(' ', '_').replace('&', '_').toUpperCase();
		
		workbook.addTab(displayName, sheet);
		worksheets.put(displayName, sheet);
		
		formulaHandler.addWorksheet(sheetName, sheet.getGrid().getGridModel());						
	} //end addSheet

	/** Return collection of sheet names */
	public Collection getSheetNames() {
		return sheetNames;
	} //end getNames

	/** Return the sheet with the specified name */
	public JSpread getSheet(String sheetName) {
		return (JSpread) worksheets.get(sheetName);
	} //end getSheet

	/** Return the active sheet */
	public JSpread getActiveSheet() {
		return (JSpread) workbook.getSelectedComponent();
	} //end getActiveSheet    

	/** Return the number of sheets */
	public int getSheetCount() {
		return workbook.getTabCount();
	} //end getSheetCount	

	public void focus(JSpread aworksheet) {
		// TODO Auto-generated method stub
		workbook.setSelectedComponent(aworksheet);
	}
} //end JWorkbook
