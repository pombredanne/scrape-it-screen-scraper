package scrape.it.widgets.tree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.table.TableModel;
import scrape.it.widgets.tree.Tree;

public class CopyItems extends JMenuItem implements ActionListener{
	
	public CopyItems(){
		super("Copy");
		addActionListener(this);
		setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		new CopyAction();
	}
	

}
