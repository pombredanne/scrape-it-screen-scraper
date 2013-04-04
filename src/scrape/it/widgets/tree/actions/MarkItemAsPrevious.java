package scrape.it.widgets.tree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class MarkItemAsPrevious extends JMenuItem implements ActionListener{
	
	private NodePro nodeObject;
	private DefaultMutableTreeNode node;

	public MarkItemAsPrevious(){
		super("Set as Back");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();	

		NodePro np = (NodePro) node.getUserObject();
		String thisText = "GOTO PREVIOUS PAGE";
		np.setText("<html><font color='#0000FF'>" + thisText + "</font></html>");
		TreeModel1.getInstance().nodeChanged(node);
		np.setNodeType("back");
		Global.db.save(np);
		
		/*
		//modify original selected node property
		node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();					  
							  
		DefaultMutableTreeNode ruleNode = new DefaultMutableTreeNode(newnp);
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
		TreeModel1.getInstance().insertNodeInto(ruleNode, parentNode,TreeModel1.getInstance().getChildCount(parentNode));
		
		Global.db.save(newnp);		
		new CopyAction();
		new PasteAction(ruleNode);
		new DeleteAction();
		*/
	}

}
