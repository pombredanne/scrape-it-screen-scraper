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
import java.util.*;

/**
 *
 * @author  Cameron Zemek
 */
public class ValidationDialog extends JDialog{
    protected Action actionApply;
    protected Action actionCancel;
    
    protected JComboBox typeCombo = null;
    
    /** Creates a new instance of ValidationDialog */
    public ValidationDialog(Dialog owner) {
        super(owner, "Validation", true);
        
        // Attach window listeners
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                closeDialog();
            }
        });
        
        // initalise actions
        initActions();
        
        Object items[] = { "String", "Integer", "Long", "Float", "Double", "Currency", "Date" };
        typeCombo = new JComboBox(items);
        
        JButton applyButton = new JButton(actionApply);
        applyButton.setMnemonic(KeyEvent.VK_A);
        
        JButton cancelButton = new JButton(actionCancel);
        cancelButton.setMnemonic(KeyEvent.VK_C);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(100, 100));
        // layout components
        contentPane.add(typeCombo, BorderLayout.CENTER);
        contentPane.add(applyButton, BorderLayout.SOUTH);
        setContentPane(contentPane);
        
        pack();
    }
    
    protected void closeDialog(){
        setVisible(false);
        dispose();
    } //end closeDialog
    
    protected void initActions(){
        actionApply = new AbstractAction("Apply"){
            public void actionPerformed(ActionEvent e){
                closeDialog();
            }
        };
        
        actionCancel = new AbstractAction("Cancel"){
            public void actionPerformed(ActionEvent e){
                closeDialog();
            }
        };
    } //end initActions
    
    public Class getClassType(){
        String typeName = typeCombo.getSelectedItem().toString();
        Class type = null;
        if(typeName.equals("String")){
            type = String.class;
        }else if(typeName.equals("Integer")){
            type = Integer.class;
        }else if(typeName.equals("Long")){
            type = Long.class;
        }else if(typeName.equals("Float")){
            type = Float.class;
        }else if(typeName.equals("Double")){
            type = Double.class;
        }else if(typeName.equals("Currency")){
            type = Currency.class;
        }else if(typeName.equals("Date")){
            type = Date.class;
        }
        return type;
    }
} //end ValidationDialog
