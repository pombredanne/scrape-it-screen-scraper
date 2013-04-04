package scrape.it.widgets.tree.actions;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.ActivatedNode;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.TreeModel1;

public class SaveColumnAction {
	private DefaultMutableTreeNode node;

	public SaveColumnAction(){
		node = ActivatedNode.treeNode;
		NodePro originalNode = SelectedNode.nodeProperty;
		
		Object result = JOptionPane.showInputDialog(null, "Enter a column name to save text to:");
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
