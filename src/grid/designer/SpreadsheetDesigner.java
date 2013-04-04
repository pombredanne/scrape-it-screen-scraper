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

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import scrape.it.main.Global;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.actions.CopyAction;
import scrape.it.widgets.tree.actions.DeleteAction;
import scrape.it.widgets.tree.actions.PasteAction;

import java.io.*;

import net.sf.jeppers.grid.*;

import com.capsicumcorp.swing.spreadsheet.*;

public class SpreadsheetDesigner extends JInternalFrame {
	// File Actions
	private Action actionNew;
	private Action actionOpen;
	private Action actionSave;
	private Action actionPrint;
	private Action actionExit;

	// Clipboard actions
	private Action actionCut;
	private Action actionCopy;
	private Action actionPaste;

	// Format actions
	private Action actionFormatCells;
	private Action actionAdjustRowHeight;
	private Action actionAdjustColumnWidth;
	private Action actionToggleHeaders;
	private Action actionToggleGrid;

	// Sheet actions
	private Action actionAddSheet;

	// Layout actions
	private Action actionLeftAlign;
	private Action actionCenterHorizontalAlign;
	private Action actionRightAlign;
	private Action actionTopAlign;
	private Action actionCenterVerticalAlign;
	private Action actionBottomAlign;

	private Action actionDeleteColumn;
	private Action actionInsertColumn;
	private Action actionDeleteRow;
	private Action actionInsertRow;

	public static JWorkbook workbook = null;

	private static int numOpenWorkbooks = 0;

	public SpreadsheetDesigner() {
		// create desktop
		this(new JWorkbook());
 
	}

	public SpreadsheetDesigner(JWorkbook workbook) {
		super("Spreadsheet Designer");

		this.workbook = workbook;
		numOpenWorkbooks++;



		//Create Actions
		createActions();

		//Create the toolbar.
		JToolBar toolBar = new JToolBar();
		addButtons(toolBar);

		//Create menubar
		setJMenuBar(createMenuBar());

		// Setup window
		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(400, 100));
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(workbook, BorderLayout.CENTER);
		setContentPane(contentPane);
		


		// Set default window size & center the window
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float heightToWidthRatio = screenSize.height / (float) screenSize.width;
        // make window 3/4 the width of the screen
        int defaultWidth = screenSize.width * 3 / 4;
        // set height so that it makes the window the same aspect ratio as the screen
        int defaultHeight = Math.round(defaultWidth * heightToWidthRatio);
		setBounds((screenSize.width - defaultWidth) / 2,
                  (screenSize.height - defaultHeight) / 2,
                  defaultWidth,
                  defaultHeight);
	}  

	private void exitProgram() {
		numOpenWorkbooks--;
		dispose();
		if (numOpenWorkbooks <= 0) {
			System.exit(0);
		}
	}

	

	private void createActions() {
		actionNew =
			new AbstractAction(
				"New",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/New16.gif"))) {
			public void actionPerformed(ActionEvent e) {
					//Action code
	}
		};

		actionOpen =
			new AbstractAction(
				"Open",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/Open16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				try {
					int returnVal = fc.showOpenDialog(SpreadsheetDesigner.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						FileInputStream fileIn =
							new FileInputStream(fc.getSelectedFile());
						//load workbook
						WorkbookCodec.decode(fileIn, workbook);
	
					} //end if
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		};

		actionSave =
			new AbstractAction(
				"Save",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/Save16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				try {
					int returnVal = fc.showSaveDialog(SpreadsheetDesigner.this);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						FileOutputStream fileOut =
							new FileOutputStream(fc.getSelectedFile());
						WorkbookCodec.encode(fileOut, workbook);
						fileOut.close();
					} //end if
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		};

		actionPrint = new AbstractAction("Print") {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				PrintPreview preview = new PrintPreview(sheet);
				preview.show();
			}
		};

		actionExit =
			new AbstractAction(
				"Exit",
				new ImageIcon(
					getClass().getResource("/grid/designer/blank16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				exitProgram();
			}
		};

		actionCut =
			new AbstractAction(
				"Cut",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/Cut16.gif"))) {
			public void actionPerformed(ActionEvent e) {
					//Action code
	}
		};

		actionCopy =
			new AbstractAction(
				"Copy",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/Copy16.gif"))) {
			public void actionPerformed(ActionEvent e) {
			}
		};

		actionPaste =
			new AbstractAction(
				"Paste",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/Paste16.gif"))) {
			public void actionPerformed(ActionEvent e) {
			}
		};

		actionLeftAlign =
			new AbstractAction(
				"Left",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/AlignLeft16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel sel = grid.getSelectionModel();
				DefaultStyleModel styleModel =
					(DefaultStyleModel) grid.getStyleModel();
				for (int row = sel.getFirstSelectedRow();
					row <= sel.getLastSelectedRow();
					row++) {
					for (int column = sel.getFirstSelectedColumn();
						column <= sel.getLastSelectedColumn();
						column++) {
						CellStyle style =
							new DefaultCellStyle(
								styleModel.getCellStyle(row, column));
						style.setHorizontalAlignment(SwingConstants.LEFT);
						styleModel.setCellStyle(style, row, column);
					}
				}
			} //end actionPerformed
		};

		actionCenterHorizontalAlign =
			new AbstractAction(
				"Center",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/AlignCenter16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel sel = grid.getSelectionModel();
				DefaultStyleModel styleModel =
					(DefaultStyleModel) grid.getStyleModel();
				for (int row = sel.getFirstSelectedRow();
					row <= sel.getLastSelectedRow();
					row++) {
					for (int column = sel.getFirstSelectedColumn();
						column <= sel.getLastSelectedColumn();
						column++) {
						CellStyle style =
							new DefaultCellStyle(
								styleModel.getCellStyle(row, column));
						style.setHorizontalAlignment(SwingConstants.CENTER);
						styleModel.setCellStyle(style, row, column);
					}
				}
			} //end actionPerformed
		};

		actionRightAlign =
			new AbstractAction(
				"Right",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/AlignRight16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel sel = grid.getSelectionModel();
				DefaultStyleModel styleModel =
					(DefaultStyleModel) grid.getStyleModel();
				for (int row = sel.getFirstSelectedRow();
					row <= sel.getLastSelectedRow();
					row++) {
					for (int column = sel.getFirstSelectedColumn();
						column <= sel.getLastSelectedColumn();
						column++) {
						CellStyle style =
							new DefaultCellStyle(
								styleModel.getCellStyle(row, column));
						style.setHorizontalAlignment(SwingConstants.RIGHT);
						styleModel.setCellStyle(style, row, column);
					}
				}
			}
		};

		actionTopAlign =
			new AbstractAction(
				"Top",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/AlignTop16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel sel = grid.getSelectionModel();
				DefaultStyleModel styleModel =
					(DefaultStyleModel) grid.getStyleModel();
				for (int row = sel.getFirstSelectedRow();
					row <= sel.getLastSelectedRow();
					row++) {
					for (int column = sel.getFirstSelectedColumn();
						column <= sel.getLastSelectedColumn();
						column++) {
						CellStyle style =
							new DefaultCellStyle(
								styleModel.getCellStyle(row, column));
						style.setVerticalAlignment(SwingConstants.TOP);
						styleModel.setCellStyle(style, row, column);
					}
				}
			} //end actionPerformed
		};

		actionCenterVerticalAlign =
			new AbstractAction(
				"Center",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/AlignCenter16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel sel = grid.getSelectionModel();
				DefaultStyleModel styleModel =
					(DefaultStyleModel) grid.getStyleModel();
				for (int row = sel.getFirstSelectedRow();
					row <= sel.getLastSelectedRow();
					row++) {
					for (int column = sel.getFirstSelectedColumn();
						column <= sel.getLastSelectedColumn();
						column++) {
						CellStyle style =
							new DefaultCellStyle(
								styleModel.getCellStyle(row, column));
						style.setVerticalAlignment(SwingConstants.CENTER);
						styleModel.setCellStyle(style, row, column);
					}
				}
			} //end actionPerformed
		};

		actionBottomAlign =
			new AbstractAction(
				"Bottom",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/general/AlignBottom16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel sel = grid.getSelectionModel();
				DefaultStyleModel styleModel =
					(DefaultStyleModel) grid.getStyleModel();
				for (int row = sel.getFirstSelectedRow();
					row <= sel.getLastSelectedRow();
					row++) {
					for (int column = sel.getFirstSelectedColumn();
						column <= sel.getLastSelectedColumn();
						column++) {
						CellStyle style =
							new DefaultCellStyle(
								styleModel.getCellStyle(row, column));
						style.setVerticalAlignment(SwingConstants.BOTTOM);
						styleModel.setCellStyle(style, row, column);
					}
				}
			} //end actionPerformed
		};

		actionAddSheet = new AbstractAction("Add sheet") {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = new JSpread();
				InputBox inputSheetName =
					new InputBox(SpreadsheetDesigner.this, "Sheet Name:");
				inputSheetName.setVisible(true);
				String sheetName = inputSheetName.getText();
				workbook.addSheet(sheetName, sheet);
			}
		};

		actionDeleteColumn =
			new AbstractAction(
				"Delete Column",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/table/ColumnDelete16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				SelectionModel sel = sheet.getGrid().getSelectionModel();
				sheet.removeColumns(
					sel.getFirstSelectedColumn(),
					(sel.getLastSelectedColumn()
						- sel.getFirstSelectedColumn()
						+ 1));
			} //end actionPerformed
		};

		actionInsertColumn =
			new AbstractAction(
				"Insert Column",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/table/ColumnInsertBefore16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				SelectionModel sel = sheet.getGrid().getSelectionModel();
				sheet.insertColumns(
					sel.getFirstSelectedColumn(),
					(sel.getLastSelectedColumn()
						- sel.getFirstSelectedColumn()
						+ 1));
			} //end actionPerformed
		};

		actionDeleteRow =
			new AbstractAction(
				"Delete Row",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/table/RowDelete16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				SelectionModel sel = sheet.getGrid().getSelectionModel();
				sheet.removeRows(
					sel.getFirstSelectedRow(),
					(sel.getLastSelectedRow() - sel.getFirstSelectedRow() + 1));
			} //end actionPerformed
		};

		actionInsertRow =
			new AbstractAction(
				"Insert Row",
				new ImageIcon(
					getClass().getResource(
						"/toolbarButtonGraphics/table/RowInsertBefore16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				SelectionModel sel = sheet.getGrid().getSelectionModel();
				for(int i =0; i<255;i++){
				sheet.insertRows(
					sel.getFirstSelectedRow(),
					(sel.getLastSelectedRow() - sel.getFirstSelectedRow() + 1));
				}
			} //end actionPerformed
		};

		actionAdjustRowHeight = new AbstractAction("Adjust Row Height") {
			private int rowHeight = 20;
			
			public void actionPerformed(ActionEvent e) {
				
				JFrame parent = new JFrame();

			    JOptionPane optionPane = new JOptionPane();
			    JSlider slider = getSlider(optionPane);
			    optionPane.setMessage(new Object[] { "Select a value: ", slider });
			    optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
			    optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
			    JDialog dialog = optionPane.createDialog(parent, "My Slider");
			    dialog.setVisible(true);
			    System.out.println("Input: " + optionPane.getInputValue());
				
				int height =  (Integer) optionPane.getInputValue();
				rowHeight = height;
				
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel selectionModel = grid.getSelectionModel();
				if (selectionModel.getFirstSelectedRow() == -1)
					return; //do nothing
				for (int row = selectionModel.getFirstSelectedRow();
					row <= selectionModel.getLastSelectedRow();
					row++) {
					grid.setRowHeight(row, height);
				}
			} //end actionPerformed

			private JSlider getSlider(final JOptionPane optionPane) {
			    JSlider slider = new JSlider();
			    slider.setMajorTickSpacing(5);
			    slider.setPaintTicks(true);
			    slider.setPaintLabels(false);
			    slider.setMaximum(200);
			    //slider.setMaximumSize(50);
			    slider.setMinimum(5);
			    slider.setOrientation(SwingConstants.VERTICAL);
			    slider.setValue(rowHeight);
			    
			    ChangeListener changeListener = new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent changeEvent) {
				        JSlider theSlider = (JSlider) changeEvent.getSource();
				        if (!theSlider.getValueIsAdjusting()) {
				          optionPane.setInputValue(new Integer(theSlider.getValue()));
				        }
						
					}

			    };
			    slider.addChangeListener(changeListener);
			    return slider;
			}
		};

		actionAdjustColumnWidth = new AbstractAction("Adjust Column Width") {
			private int columnwidth = 75;

			public void actionPerformed(ActionEvent e) {

				 	JFrame parent = new JFrame();

				    JOptionPane optionPane = new JOptionPane();
				    JSlider slider = getSlider(optionPane);
				    optionPane.setMessage(new Object[] { "Select a value: ", slider });
				    optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
				    optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
				    JDialog dialog = optionPane.createDialog(parent, "My Slider");
				    dialog.setVisible(true);
				    System.out.println("Input: " + optionPane.getInputValue());
				
				
				int width = (Integer) optionPane.getInputValue();
				columnwidth = width;
				
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel selectionModel = grid.getSelectionModel();
				if (selectionModel.getFirstSelectedRow() == -1)
					return; //do nothing
				for (int col = selectionModel.getFirstSelectedColumn();
					col <= selectionModel.getLastSelectedColumn();
					col++) {
					grid.setColumnWidth(col, width);
				}
			} //end actionPerformed

			private JSlider getSlider(final JOptionPane optionPane) {
			    JSlider slider = new JSlider();
			    slider.setMajorTickSpacing(25);
			    slider.setPaintTicks(true);
			    slider.setPaintLabels(false);
			    slider.setMaximum(750);
			    //slider.setMaximumSize(50);
			    slider.setMinimum(5);
			    slider.setValue(columnwidth);
			    
			    ChangeListener changeListener = new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent changeEvent) {
				        JSlider theSlider = (JSlider) changeEvent.getSource();
				        if (!theSlider.getValueIsAdjusting()) {
				          optionPane.setInputValue(new Integer(theSlider.getValue()));
				        }
						
					}

			    };
			    slider.addChangeListener(changeListener);
			    return slider;
			}
		};
		
		

		actionFormatCells =
			new AbstractAction(
				"Format Cells",
				new ImageIcon(
					getClass().getResource("/grid/designer/blank16.gif"))) {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				FormatCellDialog formatCell =
					new FormatCellDialog(SpreadsheetDesigner.this, grid);
				formatCell.setVisible(true);
//				FormatCellDialog2 formatCell = new FormatCellDialog2(
//						SpreadsheetDesigner.this);
//				formatCell.setVisible(true);
			} //end actionPerformed
		};

		actionToggleHeaders = new AbstractAction("Toggle Headers") {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				sheet.setShowHeader(!sheet.getShowHeader());
			} //end actionPerformed
		};

		actionToggleGrid = new AbstractAction("Toggle Grid") {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				sheet.setShowGrid(!sheet.getShowGrid());
			} //end actionPerformed
		};

		/*
		actionTemplate = new AbstractAction("Name", new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/icon.gif"))){
		    public void actionPerformed(ActionEvent e){
		    }
		};
		 */
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu;
		JMenuItem menuItem;

		//create file menu
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);

		//menuItem = menu.add(actionNew);
		//menuItem.setMnemonic(KeyEvent.VK_N);
		//menuItem.setAccelerator(
			//KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		if(Global.features.get("licensetype") == null || Global.features.get("licensetype").equals("") || Global.features.get("licensetype").equals("Free")){
			actionOpen.setEnabled(false);
			actionSave.setEnabled(false);	
			actionPrint.setEnabled(false);
			actionCut.setEnabled(false);
			actionCopy.setEnabled(false);
			actionPaste.setEnabled(false);
		}
		menuItem = menu.add(actionOpen);
		menuItem.setMnemonic(KeyEvent.VK_O);

		menuItem = menu.add(actionSave);
		menuItem.setMnemonic(KeyEvent.VK_S);

		menu.addSeparator();

		menuItem = menu.add(actionPrint);

		menu.addSeparator();

		menuItem = menu.add(actionExit);
		menuItem.setMnemonic(KeyEvent.VK_X);

		menuBar.add(menu);

		//create edit menu
		menu = new JMenu("Edit");
		menu.setMnemonic(KeyEvent.VK_E);

		menuItem = menu.add(actionCut);
		menuItem.setMnemonic(KeyEvent.VK_T);
		menuItem.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));

		menuItem = menu.add(actionCopy);
		menuItem.setMnemonic(KeyEvent.VK_C);
		menuItem.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));

		menuItem = menu.add(actionPaste);
		menuItem.setMnemonic(KeyEvent.VK_P);
		menuItem.setAccelerator(
			KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));

		menuBar.add(menu);

		//create insert menu
		menu = new JMenu("Insert");
		menu.setMnemonic(KeyEvent.VK_I);

		menuItem = menu.add(actionInsertColumn);
		menuItem.setText("Column");
		menuItem.setMnemonic(KeyEvent.VK_L);

		menuItem = menu.add(actionInsertRow);
		menuItem.setText("Row");
		menuItem.setMnemonic(KeyEvent.VK_R);

		menuBar.add(menu);

		//create format menu
		menu = new JMenu("Format");
		menu.setMnemonic(KeyEvent.VK_F);

		

/*
		JMenu colMenu = new JMenu("Column");
		colMenu.setMnemonic(KeyEvent.VK_L);
		colMenu.setIcon(
			new ImageIcon(
				getClass().getResource("/grid/designer/blank16.gif")));
*/
		menuItem = menu.add(actionAdjustColumnWidth);
		menuItem.setText("Change Column Width");
		menuItem.setMnemonic(KeyEvent.VK_W);

		//menu.add(colMenu);
		
		/*
		JMenu rowMenu = new JMenu("Row");
		rowMenu.setMnemonic(KeyEvent.VK_R);
		rowMenu.setIcon(
			new ImageIcon(
				getClass().getResource("/grid/designer/blank16.gif")));
*/
		menuItem = menu.add(actionAdjustRowHeight);
		menuItem.setText("Change Row Height");
		menuItem.setMnemonic(KeyEvent.VK_H);

		//menu.add(rowMenu);

		menu.addSeparator();
		
		menuItem = menu.add(actionFormatCells);
		menuItem.setText("Cells");
		menuItem.setMnemonic(KeyEvent.VK_C);

		menu.addSeparator();

		menuItem = menu.add(actionToggleHeaders);
		menuItem = menu.add(actionToggleGrid);

		menuItem = menu.add("Merge Cells");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel selectionModel = grid.getSelectionModel();
				int firstRow = selectionModel.getFirstSelectedRow();
				int lastRow = selectionModel.getLastSelectedRow();
				int firstColumn = selectionModel.getFirstSelectedColumn();
				int lastColumn = selectionModel.getLastSelectedColumn();
				CellSpan selectedSpan =
					new CellSpan(
						firstRow,
						firstColumn,
						(lastRow - firstRow + 1),
						(lastColumn - firstColumn + 1));
				((DefaultSpanModel) grid.getSpanModel()).addSpan(selectedSpan);
			}
		});

		menuItem = menu.add("Unmerge Cells");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				SelectionModel selectionModel = grid.getSelectionModel();
				int firstRow = selectionModel.getFirstSelectedRow();
				int lastRow = selectionModel.getLastSelectedRow();
				int firstColumn = selectionModel.getFirstSelectedColumn();
				int lastColumn = selectionModel.getLastSelectedColumn();
				CellSpan selectedSpan =
					grid.getSpanModel().getSpanOver(firstRow, firstColumn);
				((DefaultSpanModel) grid.getSpanModel()).removeSpan(
					selectedSpan);
			}
		});

		menuItem = menu.add("Lock cell");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				DefaultGridModel dataModel =
					(DefaultGridModel) grid.getGridModel();
				SelectionModel sel = grid.getSelectionModel();
				for (int row = sel.getFirstSelectedRow();
					row <= sel.getLastSelectedRow();
					row++) {
					for (int column = sel.getFirstSelectedColumn();
						column <= sel.getLastSelectedColumn();
						column++) {
						dataModel.setCellLock(true, row, column);
					}
				}
			} //end actionPerformed
		});

		menuItem = menu.add("UnLock cell");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSpread sheet = workbook.getActiveSheet();
				JGrid grid = sheet.getGrid();
				DefaultGridModel dataModel =
					(DefaultGridModel) grid.getGridModel();
				SelectionModel sel = grid.getSelectionModel();
				for (int row = sel.getFirstSelectedRow();
					row <= sel.getLastSelectedRow();
					row++) {
					for (int column = sel.getFirstSelectedColumn();
						column <= sel.getLastSelectedColumn();
						column++) {
						dataModel.setCellLock(false, row, column);
					}
				}
			} //end actionPerformed
		});

		menu.addSeparator();

		//menuItem = menu.add(actionAddSheet);

		menuBar.add(menu);

		return menuBar;
	}

	private void addButtons(JToolBar toolBar) {
		JButton button = null;
		Insets noPadding = new Insets(0, 0, 0, 0);

		//new button
		button = toolBar.add(actionNew);
		button.setToolTipText("New");
		button.setText("");

		//open button
		button = toolBar.add(actionOpen);
		button.setToolTipText("Open");
		button.setText(""); //an icon-only button

		//save button
		button = toolBar.add(actionSave);
		button.setToolTipText("Save");
		button.setText("");

		toolBar.addSeparator();

		// Clipboard buttons
		button = toolBar.add(actionCut);
		button.setToolTipText("Cut");
		button.setText("");

		button = toolBar.add(actionCopy);
		button.setToolTipText("Copy");
		button.setText("");

		button = toolBar.add(actionPaste);
		button.setToolTipText("Paste");
		button.setText("");

		toolBar.addSeparator();

		// layout buttons
		button = toolBar.add(actionLeftAlign);
		button = toolBar.add(actionCenterHorizontalAlign);
		button = toolBar.add(actionRightAlign);
		toolBar.addSeparator();
		button = toolBar.add(actionTopAlign);
		button = toolBar.add(actionCenterVerticalAlign);
		button = toolBar.add(actionBottomAlign);

		toolBar.addSeparator();
		button = toolBar.add(actionDeleteColumn);
		button = toolBar.add(actionInsertColumn);
		toolBar.addSeparator();
		button = toolBar.add(actionDeleteRow);
		button = toolBar.add(actionInsertRow);
	} //end addButtons    
} // end SpreadsheetDesigner
