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

/**
 * @author <a href="grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class InputBox extends JDialog{
	protected Action actionApply;
    protected Action actionCancel;
    protected JTextField inputText;
    
    protected String text = "";

    public InputBox(SpreadsheetDesigner spreadsheetDesigner, String label) {

        
        // Attach window listeners
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                closeDialog();
            }
        });
        
        // initalise actions
        initActions();
        
        JLabel promptLabel = new JLabel(label);
        inputText = new JTextField();
        
        JButton applyButton = new JButton(actionApply);
        applyButton.setMnemonic(KeyEvent.VK_A);
        
        JButton cancelButton = new JButton(actionCancel);
        cancelButton.setMnemonic(KeyEvent.VK_C);
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setPreferredSize(new Dimension(100, 100));        
        // layout components
        contentPane.add(inputText, BorderLayout.CENTER);
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
            	text = inputText.getText();
                closeDialog();
            }
        };
        
        actionCancel = new AbstractAction("Cancel"){
            public void actionPerformed(ActionEvent e){
                closeDialog();
            }
        };
    } //end initActions 
    
    public String getText(){
    	return text;
    }
}
