package scrape.it.helper;

/*
 * @(#)VsnetOptionsDialog.java
 *
 * Copyright 2002 - 2003 JIDE Software Inc. All rights reserved.
 */

import com.jidesoft.dialog.*;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.plaf.UIDefaultsLookup;
import com.jidesoft.swing.JideBorderLayout;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideSwingUtilities;
import com.jidesoft.swing.JideTitledBorder;
import com.jidesoft.swing.PartialEtchedBorder;
import com.jidesoft.swing.PartialLineBorder;
import com.jidesoft.swing.PartialSide;
import com.teamdev.jxbrowser.dom.DOMElement;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Demoed Component: {@link MultiplePageDialog} <br> Required jar files: jide-common.jar, jide-dialogs.jar <br> Required
 * L&F: Jide L&F extension required
 */
public class VsnetOptionsDialog extends MultiplePageDialog {
    public VsnetOptionsDialog(Frame owner, String title) throws HeadlessException {
        super(owner, title);
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
    }

    @Override
    protected void initComponents() {
        super.initComponents();
        getContentPanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        getIndexPanel().setBackground(Color.white);
        getButtonPanel().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getPagesPanel().setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 0), createSeparatorBorderAtBottom()));
    }

    @Override
    public ButtonPanel createButtonPanel() {
        ButtonPanel buttonPanel = super.createButtonPanel();
        AbstractAction okAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.okButtonText")) {
            private static final long serialVersionUID = -8212152795901394544L;

            public void actionPerformed(ActionEvent e) {
                setDialogResult(RESULT_AFFIRMED);
                setVisible(false);
                dispose();
            }
        };
        AbstractAction cancelAction = new AbstractAction(UIDefaultsLookup.getString("OptionPane.cancelButtonText")) {
            private static final long serialVersionUID = -9124324443138164926L;

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

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 400);
    }



    public static void showOptionsDialog(final boolean exit, DOMElement currentElement) {
        final MultiplePageDialog dialog = new VsnetOptionsDialog(null, "Choose an Option");
        dialog.setStyle(MultiplePageDialog.TREE_STYLE);
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
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // setup model
        AbstractDialogPage panel1 = new VsnetOptionPage("Clicks");

        AbstractDialogPage panel2 = new VsnetOptionPage("Extract Data");


        model.append(panel1);

        model.append(panel2);

        dialog.setPageList(model);
        dialog.setInitialPageTitle("Environment.General");

        dialog.pack();
        JideSwingUtilities.globalCenterWindow(dialog);
        dialog.setVisible(true);
    }

    public static class VsnetOptionPage extends AbstractDialogPage {
        public VsnetOptionPage(String name) {
            super(name);
        }

        public void lazyInitialize() {
            initComponents();
        }

        public void initComponents() {
            setLayout(new JideBorderLayout());
            System.out.println(getFullTitle());
            //add(new JLabel("This is just a demo. \"" + getFullTitle() + "\" page is not implemented yet.", JLabel.CENTER), BorderLayout.CENTER);
            if(getFullTitle().startsWith("Clicking")){
            	
            	
                  JPanel border = new JPanel();
                  border.setLayout(new BoxLayout(border,BoxLayout.PAGE_AXIS));
                  
                  border.setPreferredSize(new Dimension(300, 100));
                  border.add(new JRadioButton("Visit This Link"));
                  border.add(Box.createRigidArea(new Dimension(0,5)));
                  border.add(new JRadioButton("Visit Similar Links"));
                  border.setBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Clicking"));
                  add(border, JideBorderLayout.LINE_START);
            }else if(getFullTitle().startsWith("Extract Data")){
             	
                JPanel border = new JPanel();
                border.setLayout(new BoxLayout(border,BoxLayout.PAGE_AXIS));
                
                border.setPreferredSize(new Dimension(300, 100));
                border.add(new JRadioButton("Visit This Link"));
                border.add(Box.createRigidArea(new Dimension(0,5)));
                border.add(new JRadioButton("Visit Similar Links"));
                border.setBorder(new JideTitledBorder(new PartialEtchedBorder(PartialEtchedBorder.LOWERED, PartialSide.NORTH), "Clicking"));
                add(border, JideBorderLayout.LINE_START);
            }
        
        }
    }

    public static class VsnetOptionPage1 extends VsnetOptionPage {
        public VsnetOptionPage1(String name) {
            super(name);
        }

        @Override
        public void initComponents() {
            super.initComponents();
            add(createGeneralPage(), BorderLayout.CENTER);
        }
    }

    private static Border createSeparatorBorder(String title) {
        return BorderFactory.createCompoundBorder(new JideTitledBorder(
                new PartialEtchedBorder(EtchedBorder.LOWERED, PartialEtchedBorder.NORTH), title, TitledBorder.LEFT, TitledBorder.CENTER), BorderFactory.createEmptyBorder(0, 6, 4, 6));
    }

    private static Border createSeparatorBorderAtBottom() {
        return new PartialEtchedBorder(EtchedBorder.LOWERED, PartialEtchedBorder.SOUTH);
    }

    private static JPanel createGeneralPage() {
        JPanel documentPanel = new JPanel(new GridLayout(2, 1));
        JRadioButton radio1 = new JRadioButton("Tabbed documents");
        JRadioButton radio2 = new JRadioButton("MDI environment");
        documentPanel.add(radio1);
        documentPanel.add(radio2);
        ButtonGroup group1 = new ButtonGroup();
        group1.add(radio1);
        group1.add(radio2);
        documentPanel.setBorder(createSeparatorBorder("Settings "));

        JPanel optionPanel = new JPanel(new GridLayout(2, 1));
        JCheckBox check1 = new JCheckBox("Close button affects active tab only");
        JCheckBox check2 = new JCheckBox("Auto Hide button affects active tab only");
        optionPanel.add(check1);
        optionPanel.add(check2);
        ButtonGroup group = new ButtonGroup();
        group.add(check1);
        group.add(check2);
        optionPanel.setBorder(createSeparatorBorder("Dockable Tool Window Behavior "));

        JPanel panel = new JPanel();
        panel.setLayout(new JideBoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(documentPanel, JideBoxLayout.FLEXIBLE);
        panel.add(Box.createVerticalStrut(6), JideBoxLayout.FIX);
        panel.add(optionPanel, JideBoxLayout.FLEXIBLE);
        panel.add(Box.createGlue(), JideBoxLayout.VARY);
        return panel;
    }
}
