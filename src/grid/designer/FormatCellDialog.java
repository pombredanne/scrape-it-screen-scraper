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

import net.sf.jeppers.grid.*;

/**
 *
 * @author  Cameron Zemek
 */
public class FormatCellDialog extends JDialog{
    protected Action actionFont;
    protected Action actionForeground;
    protected Action actionBackground;
    protected Action actionBorder;
    protected Action actionMergeCells;
    protected Action actionFormatCell;
    
    protected JGrid grid;
    
    /** Creates a new instance of FormatCellDialog */
    public FormatCellDialog(SpreadsheetDesigner spreadsheetDesigner, JGrid grid) {

        
        this.grid = grid;
        
        // Attach window listeners
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                closeDialog();
            }
        });
        
        // initalise actions
        initActions();
        
        //Content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new FlowLayout());
        
        JButton font = new JButton(actionFont);
        JButton fgColor = new JButton(actionForeground);
        JButton bgColor = new JButton(actionBackground);
        JButton border = new JButton(actionBorder);
        JButton merge = new JButton(actionMergeCells);
        JButton cellFormat = new JButton(actionFormatCell);
        contentPane.add(font);
        contentPane.add(fgColor);
        contentPane.add(bgColor);
        contentPane.add(border);
        contentPane.add(merge);
        contentPane.add(cellFormat);
        
        pack();
    } //end constructor
    
    protected void closeDialog(){
        setVisible(false);
        dispose();
    } //end closeDialog
    
    private void changeColor(boolean isForeground, JGrid grid) {        
        Color target    = null;
        Color reference = null;
        
        String title;
        if (isForeground) {
            target = grid.getForeground();
            reference = grid.getBackground();
            title = "Foreground Color";
        } else {
            target = grid.getBackground();
            reference = grid.getForeground();
            title = "Background Color";
        }
        TextColorChooser chooser =  new TextColorChooser(target, reference, isForeground);
        Color color = chooser.showDialog(FormatCellDialog.this,title);
        if(color == null)
            return;
        
        SelectionModel sel = grid.getSelectionModel();
        for(int row = sel.getFirstSelectedRow(); row <= sel.getLastSelectedRow(); row++){
            for(int column = sel.getFirstSelectedColumn(); column <= sel.getLastSelectedColumn(); column++){
            	DefaultStyleModel styleModel = (DefaultStyleModel) grid.getStyleModel();
                if (isForeground) {                	
                	CellStyle style = new DefaultCellStyle(styleModel.getCellStyle(row, column));
                    style.setForegroundColor(color);
                    styleModel.setCellStyle(style, row, column);
                } else {
                    CellStyle style = new DefaultCellStyle(styleModel.getCellStyle(row, column));
                    style.setBackgroundColor(color);
                    styleModel.setCellStyle(style, row, column);
                } //end if
            } //end for
        } //end for 
   } //end changeColor
    
    protected void initActions(){
        actionFont = new AbstractAction("Font"){
            public void actionPerformed(ActionEvent e){                
                FontDialog fontDialog = new FontDialog(FormatCellDialog.this, grid);
                fontDialog.setVisible(true);
                closeDialog();
            }
        };
        
        actionForeground = new AbstractAction("Foreground"){
            public void actionPerformed(ActionEvent e){               
                changeColor(true, grid);
                closeDialog();
            }
        };
        
        actionBackground = new AbstractAction("Background"){
            public void actionPerformed(ActionEvent e){
                changeColor(false, grid);
                closeDialog();
            }
        };

        actionBorder = new AbstractAction("Border"){
            public void actionPerformed(ActionEvent e){
                BorderDialog borderDialog = new BorderDialog(FormatCellDialog.this, grid);
                borderDialog.setVisible(true);
                closeDialog();
            }
        };
        
        actionMergeCells = new AbstractAction("Span Cells"){
            public void actionPerformed(ActionEvent e){/*
                CellRange selection = spread.getSelectionRange();
                spread.merge(selection);
                closeDialog();*/
            }
        };
        
        actionFormatCell = new AbstractAction("Cell Format"){
        	public void actionPerformed(ActionEvent e){
        		CellFormatDialog cellFormatDialog = new CellFormatDialog(FormatCellDialog.this, grid);
        		cellFormatDialog.setVisible(true);
        		closeDialog();
        	}
        };
    } //end initActions
} //end FormatCellDialog
