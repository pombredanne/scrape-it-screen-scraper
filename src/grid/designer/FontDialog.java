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

public class FontDialog extends JDialog{
    
    public FontDialog(java.awt.Dialog parent, JGrid grid) {
        super(parent, true);
        
        //Dialog Close event
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog();
            }
        });
        
        //Setup dialog
        setTitle("Font");
        
        FontPanel fontPanel = new FontPanel(grid);
                
        getContentPane().add(fontPanel, BorderLayout.CENTER);
        setResizable(false);
        pack();
    }
    
    private void closeDialog() {
        setVisible(false);
        dispose();
    }
    
    class FontPanel extends JPanel {
        String[] str_size  = {"10","12","14","16","20"};
        String[] str_style = {"PLAIN","BOLD","ITALIC"};
        JComboBox name,style,size;
        
        FontPanel(final JGrid grid) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createTitledBorder("Font"));
            Box box = new Box(BoxLayout.X_AXIS);
            JPanel p2 = new JPanel(new GridLayout(3,1));
            JPanel p3 = new JPanel(new GridLayout(3,1));
            JPanel p4 = new JPanel(new GridLayout(1,2));
            p2.add(new JLabel("Name:"));
            p2.add(new JLabel("Style:"));
            p2.add(new JLabel("Size:"));

            // Get list of available fonts    
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            name = new JComboBox(ge.getAvailableFontFamilyNames());
            
            style = new JComboBox(str_style);
            size  = new JComboBox(str_size);
            size.setEditable(true);
            JButton b_cancel  = new JButton("Cancel");
            b_cancel.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // close dialog
                    closeDialog();
                }
            });
            
            JButton b_apply   = new JButton("Apply");
            b_apply.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {                    
                    Font font = new Font((String)name.getSelectedItem(),
                    style.getSelectedIndex(),
                    Integer.parseInt((String)size.getSelectedItem()));
                    
                    SelectionModel sel = grid.getSelectionModel();
                    DefaultStyleModel styleModel = (DefaultStyleModel) grid.getStyleModel();
                    for(int row = sel.getFirstSelectedRow(); row <= sel.getLastSelectedRow(); row++){
                        for(int column = sel.getFirstSelectedColumn(); column <= sel.getLastSelectedColumn(); column++){
                            CellStyle style = new DefaultCellStyle(styleModel.getCellStyle(row, column));
                            style.setFont(font);
                            styleModel.setCellStyle(style, row, column);
                        }
                    }
                    
                    // Close dialog
                    closeDialog();
                }
            });
            p3.add(name);
            p3.add(style);
            p3.add(size);
            p4.add(b_cancel);
            p4.add(b_apply);
            box.add(p2);
            box.add(p3);
            add(BorderLayout.CENTER,box);
            add(BorderLayout.SOUTH, p4);
        }
    }
}
