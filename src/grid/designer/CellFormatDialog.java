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
package grid.designer;

import java.awt.*;
import java.awt.event.*;
import java.text.*;

import javax.swing.*;

import net.sf.jeppers.grid.*;

/**
 * @author <a href="grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class CellFormatDialog extends JDialog {
	public CellFormatDialog(Dialog parent, final JGrid grid) {
		super(parent, true);

		//Dialog Close event
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog();
			}
		});

		setTitle("Cell Format");

		String[] formats = { "Text", "Currency" };
		final JComboBox cboFormat = new JComboBox(formats);

		JButton applyFormat = new JButton("Apply");
		applyFormat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedFormat = cboFormat.getSelectedItem().toString();
				Format formatter = null;
				if (selectedFormat.equals("Currency")) {
					formatter = NumberFormat.getCurrencyInstance();	
				}
				applyFormat(formatter, grid);
				closeDialog();
			}
		});

		getContentPane().setLayout(
			new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().add(cboFormat);
		getContentPane().add(applyFormat);

		pack();
	}

	private void applyFormat(Format formatter, final JGrid grid) {
		SelectionModel selModel = grid.getSelectionModel();
		int firstRow = selModel.getFirstSelectedRow();
		int lastRow = selModel.getLastSelectedRow();
		int firstColumn = selModel.getFirstSelectedColumn();
		int lastColumn = selModel.getLastSelectedColumn();
		for (int row = firstRow; row <= lastRow; row++) {
			for (int column = firstColumn; column <= lastColumn; column++) {
				//update style format
				CellStyle style = grid.getStyleModel().getCellStyle(row, column).copy();
				style.setFormat(formatter);
				((DefaultStyleModel)grid.getStyleModel()).setCellStyle(style, row, column);						
			}
		}
	}

	private void closeDialog() {
		setVisible(false);
		dispose();
	}
}
