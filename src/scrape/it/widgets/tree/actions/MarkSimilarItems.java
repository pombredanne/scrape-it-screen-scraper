package scrape.it.widgets.tree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.table.TableModel;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class MarkSimilarItems extends JMenuItem implements ActionListener{
	
	private NodePro newnp;
	private DefaultMutableTreeNode saveRowNode;
	private DefaultMutableTreeNode node;

	public MarkSimilarItems(){
		super("Select Similar Elements");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();	

		NodePro np = (NodePro) node.getUserObject();
		
		String thisText = np.getText();
		String thisXpath = np.getXpath();
		
		FindSimilarXpath fsx = new FindSimilarXpath();
		String masterXpath = "";
		if(thisXpath.contains("following-sibling")){

			masterXpath = fsx.regenerateXpath(thisXpath,np.getNodeIndex());
		}else{
			masterXpath = fsx.generateXpath(thisXpath,999);
		}

		String mainout = "";
		String outindex = "";
		
		if(masterXpath.contains("#")){
	    	String[] newout = masterXpath.split("#");
  	    	mainout = newout[0];
  	    	outindex = newout[1];
		}else{
			mainout = masterXpath;
			outindex = "888";	
		}
		
		np.setText("<html><font color='#0000FF'>" + "SIMILAR TO " + thisText + "</font></html>");
		TreeModel1.getInstance().nodeChanged(node);
		np.setNodeType("similar");		
		np.setXpath(mainout);
		np.setNodeIndex(Integer.parseInt(outindex));
		
		Global.db.save(np);
		
		//create copy of selected node property
		/*
		NodePro newnp = new NodePro();
							  newnp.setParentID(SelectedNode.nodeProperty.getParentID());
							  newnp.setText("SIMILAR ELEMENTS");
		
		//modify original selected node property
		node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();					  
							  
		DefaultMutableTreeNode ruleNode = new DefaultMutableTreeNode(newnp);
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
		TreeModel1.getInstance().insertNodeInto(ruleNode, parentNode,TreeModel1.getInstance().getChildCount(parentNode));
		
		Global.db.save(newnp);		
		new CopyAction();
		new PasteAction(ruleNode);
		new DeleteAction();
		
		new SelectSimilarElements();
		*/
	}

}
