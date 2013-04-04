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

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import net.sf.jeppers.grid.*;

import com.capsicumcorp.swing.border.*;

public class BorderDialog extends JDialog {

	public BorderDialog(Dialog parent, JGrid grid) {
		super(parent, true);

		//Dialog Close event
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog();
			}
		});

		//Setup dialog
		setTitle("Border");

		ThicknessPanel thicknessPanel = new ThicknessPanel();
		BorderPanel borderPanel = new BorderPanel(grid, thicknessPanel);
		Box box = new Box(BoxLayout.Y_AXIS);
		box.add(thicknessPanel);
		box.add(borderPanel);

		getContentPane().add(box, BorderLayout.CENTER);
		setResizable(false);
		pack();
	}

	private void closeDialog() {
		setVisible(false);
		dispose();
	}

	class ThicknessPanel extends JPanel {
		final JComboBox[] combos;

		ThicknessPanel() {
			String[] str = { "top", "left", "bottom", "right" };
			int n = str.length;
			setLayout(new GridLayout(n, 2));
			combos = new JComboBox[n];
			for (int i = 0; i < n; i++) {
				combos[i] = new JComboBox(new Object[] { "0", "1", "2", "3" });
				add(new JLabel(str[i]));
				add(combos[i]);
			}
		}

		public Insets getThickness() {
			Insets insets = new Insets(0, 0, 0, 0);
			insets.top = combos[0].getSelectedIndex();
			insets.left = combos[1].getSelectedIndex();
			insets.bottom = combos[2].getSelectedIndex();
			insets.right = combos[3].getSelectedIndex();
			return insets;
		}
	}

	class BorderPanel extends JPanel {
		JGrid grid;
		ThicknessPanel thicknessPanel;

		BorderPanel(JGrid grid, final ThicknessPanel thicknessPanel) {
			this.grid = grid;
			this.thicknessPanel = thicknessPanel;

			// Create buttons
			JButton b_apply = new JButton("Apply");
			JButton b_cancel = new JButton("Cancel");
			b_apply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setCellBorder();
					closeDialog();
				}
			});
			b_cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					closeDialog();
				}
			});

			setLayout(new GridLayout(1, 2));
			add(b_cancel);
			add(b_apply);
		}

		private void setCellBorder() {
			Insets insets = thicknessPanel.getThickness();
			Color color = Color.BLACK;

			SelectionModel sel = grid.getSelectionModel();
			DefaultStyleModel styleModel = (DefaultStyleModel) grid.getStyleModel();
			for (int row = sel.getFirstSelectedRow();
				row <= sel.getLastSelectedRow();
				row++) {
				for (int column = sel.getFirstSelectedColumn();
					column <= sel.getLastSelectedColumn();
					column++) {
					LinesBorder border =
						new LinesBorder(
							insets.top,
							insets.left,
							insets.bottom,
							insets.right,
							color,
							color,
							color,
							color);
					CellStyle style = new DefaultCellStyle(styleModel.getCellStyle(row, column));
					style.setBorder(border);
					styleModel.setCellStyle(style, row, column);
				}
			}
		} //end setCellBorder
	} //end BorderPanel inner class
} //end BorderDialog
