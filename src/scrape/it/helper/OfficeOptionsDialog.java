package scrape.it.helper;

/*
 * @(#)OfficeOptionsDialog.java 2/15/2005
 *
 * Copyright 2002 - 2005 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.dialog.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.JideSwingUtilities;

import javax.swing.*;

import scrape.it.main.Global;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Demoed Component: {@link com.jidesoft.dialog.MultiplePageDialog} <br> Required jar files: jide-common.jar,
 * jide-dialogs.jar <br> Required L&F: Jide L&F extension required
 */
public class OfficeOptionsDialog extends MultiplePageDialog {

    private static OfficeOptionPage1 panel1;
	private static OfficeOptionPage2 panel2;



	public OfficeOptionsDialog(Frame owner, String title) throws HeadlessException {
        super(owner, title);
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
    }

    public OfficeOptionsDialog() {
		// TODO Auto-generated constructor stub
    	super();
    	 LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
	}

	@Override
    protected void initComponents() {
        super.initComponents();
        getContentPanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        getButtonPanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    @Override
    public ButtonPanel createButtonPanel() {
        ButtonPanel buttonPanel = super.createButtonPanel();
        AbstractAction okAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {
            private Map mapdata;

			public void actionPerformed(ActionEvent e) {
            	Global.mapdata = getAllFormData();
            	
                setDialogResult(RESULT_AFFIRMED);
                setVisible(false);
                dispose();
            }
        };
        AbstractAction cancelAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.cancelButtonText")) {
            public void actionPerformed(ActionEvent e) {
                setDialogResult(RESULT_CANCELLED);
                setVisible(false);
                dispose();
            }
        };
        ((JButton) buttonPanel.getButtonByName(ButtonNames.OK)).setAction(okAction);
        ((JButton) buttonPanel.getButtonByName(ButtonNames.CANCEL)).setAction(cancelAction);
        setDefaultCancelAction(cancelAction);
        setDefaultAction(okAction);
        return buttonPanel;
    }

    public Map getAllFormData() {
    	Map hm = new HashMap();
    	

		// TODO Auto-generated method stub
    	hm.put("OK",true);
		if(panel1.r11.isSelected()) hm.put("Navigate_One", "true");
		if(panel1.r12.isSelected()) hm.put("Navigate_All", "true");
		
		return hm;
    }



	@Override
    public Dimension getPreferredSize() {
        return new Dimension(400, 500);
    }



    public static void showOptionsDialog(final boolean exit, String elementTag) {
        final MultiplePageDialog dialog = new OfficeOptionsDialog(null, "Microsoft Office Option Dialog");
        dialog.setStyle(MultiplePageDialog.TAB_STYLE);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                if (exit) {
                    System.exit(0);
                }
                else {
                    dialog.dispose();
                }
            }
        });
        PageList model = new PageList();

        
        if(elementTag.equals("A")){
        	
        }else{
        	panel1 = new OfficeOptionPage1("Action");
        	panel2 = new OfficeOptionPage2("Data");
        }


        
        model.append(panel1);
        model.append(panel2);



        dialog.setPageList(model);

        dialog.pack();
        JideSwingUtilities.globalCenterWindow(dialog);
        dialog.setVisible(true);
    }

}
