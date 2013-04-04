package scrape.it.widgets.tree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class DeleteSelection extends JMenuItem implements ActionListener{
	
	public DeleteSelection(){
		super("Delete    ");
		addActionListener(this);
		setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_DELETE, 0));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(JOptionPane.showConfirmDialog(ClientGui.frame, "Confirm Delete. All Descendants Will Be Removed As Well!") == 0){
			new DeleteAction();
			
			
		}
		
	}

}
