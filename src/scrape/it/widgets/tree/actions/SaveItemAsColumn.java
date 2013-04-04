package scrape.it.widgets.tree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.ActivatedNode;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class SaveItemAsColumn extends JMenuItem implements ActionListener {
	
	private DefaultMutableTreeNode parent;
	private int nodeIndex;
	private NodePro nodeObject;
	private DefaultMutableTreeNode node;
	
	public SaveItemAsColumn(){
		super("Save As Column");
		addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//new SaveColumnAction();
		node = ActivatedNode.treeNode;
		NodePro originalNode = SelectedNode.nodeProperty;
		
		Object result = JOptionPane.showInputDialog(null, "Enter a column name to extract data to:");
	    String colnum = (String) result;
	  
	    if(colnum != null && !colnum.isEmpty()){
			NodePro np = originalNode;
			String thisText = originalNode.getText();
			np.setNodeType("column_" + colnum);
			np.setTreePath(thisText);
			np.setText("<html><font color='#0000FF'>COLUMN_" + colnum + "</font> " + thisText + "</html>");
			TreeModel1.getInstance().nodeChanged(node);
			Global.db.save(np);
	    }

		
	}

}
