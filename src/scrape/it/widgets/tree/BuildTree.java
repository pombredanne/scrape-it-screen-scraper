package scrape.it.widgets.tree;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;


import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;

public class BuildTree {

	public BuildTree(DefaultMutableTreeNode treeNode){
		
		try {
			DefaultMutableTreeNode aParentNode = treeNode;
			processChildren(aParentNode);
		} catch (Exception e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while processChildren in BuildTree", e);
		}
	}
	
	public void processChildren(DefaultMutableTreeNode parentNode) throws Exception{
		final NodePro np = (NodePro) parentNode.getUserObject();		
		
		long npid = np.getId();
		List<NodePro> nodelist = Global.db.query(new OSQLSynchQuery<NodePro>("select  from NodePro where parentID = " + npid + " order by timestamp"));

		if (nodelist.isEmpty()){	
			org.slf4j.LoggerFactory.getLogger(this.getClass()).warn("sql returned empty result set in BuildTree");
			return;
		}else{
			
			 for (int i=0; i< nodelist.size(); i++)
			  {			   
				 
				 NodePro childnode = nodelist.get(i);
				 DefaultMutableTreeNode child = new DefaultMutableTreeNode(childnode);

				 TreeModel1.getInstance().insertNodeInto(child,parentNode,TreeModel1.getInstance().getChildCount(parentNode));
				
				 DefaultMutableTreeNode parent = (DefaultMutableTreeNode) 
					TreeModel1.getInstance().getChild(parentNode, i);

			     processChildren(parent);
			  }
			 
			return;
		}	
	}
		
	

}
