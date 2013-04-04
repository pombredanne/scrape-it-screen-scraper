package scrape.it.widgets.tree.actions;

import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.TreeModel1;

public class PasteAction {
	private DefaultMutableTreeNode aParentNode;

	public PasteAction(DefaultMutableTreeNode selectedNode){
		NodePro selectedNodePro = (NodePro) selectedNode.getUserObject();
		
		List<NodePro> copiednodelist = Global.copiedNodes;
		
		 for (int i=0; i< copiednodelist.size(); i++)
		  {		
			 
			 NodePro copiedChildNode = copiednodelist.get(i);

			 NodePro nodep = new NodePro();
			 nodep.setId(UUID.randomUUID().getMostSignificantBits());
		     nodep.setText(copiedChildNode.getText());						 
			 nodep.setXpath(copiedChildNode.getXpath());
			 nodep.setPageurl(copiedChildNode.getPageurl());
			 nodep.setParentID(selectedNodePro.getId());		
			 nodep.setNodeIndex(TreeModel1.getInstance().getChildCount(selectedNode));
			 nodep.setNodeType(copiedChildNode.getNodeType());
			 nodep.setElement(copiedChildNode.getElement());
			 nodep.setCommand(copiedChildNode.getCommand());
			 nodep.selectedOptions = copiedChildNode.selectedOptions;
			 nodep.setNodeancestors(copiedChildNode.getNodeancestors());
			 nodep.setTreePath(copiedChildNode.getTreePath());
			 nodep.timestamp = nodep.timestamp + i;
			 
			 
			 DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodep);
			 
			 TreeModel1.getInstance().insertNodeInto(child,selectedNode,TreeModel1.getInstance().getChildCount(selectedNode));
			
				Global.db.save(nodep);			 	
				aParentNode =new DefaultMutableTreeNode(copiedChildNode);
				try {
					processChildren(aParentNode, (DefaultMutableTreeNode) TreeModel1.getInstance().getChild(selectedNode, i));
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

		  }
	
		
		
	
	}
	
	public void processChildren(DefaultMutableTreeNode parentNode, DefaultMutableTreeNode actualNode) throws Exception{
		final NodePro np = (NodePro) parentNode.getUserObject();	
		NodePro actual = (NodePro) actualNode.getUserObject();
	
		long npid = np.getId();
		List<NodePro> nodelist = Global.db.query(new OSQLSynchQuery<NodePro>("select * from NodePro where parentID = " + npid + " order by timestamp"));
		
		if (nodelist.isEmpty()){	
			System.out.println("empty");
			return;
		}else{
			
			 for (int i=0; i< nodelist.size(); i++)
			  {			   
				 
				 NodePro childnode = nodelist.get(i);
				
					 
						 NodePro nodep = new NodePro();
						 nodep.setId(UUID.randomUUID().getMostSignificantBits());
					     nodep.setText(childnode.getText());						 
						 nodep.setXpath(childnode.getXpath());
						 nodep.setPageurl(childnode.getPageurl());
						 nodep.setParentID(actual.getId());		
						 nodep.setNodeIndex(TreeModel1.getInstance().getChildCount(actualNode));
						 nodep.setNodeType(childnode.getNodeType());
						 nodep.setElement(childnode.getElement());
						 nodep.setCommand(childnode.getCommand());
						 nodep.selectedOptions = childnode.selectedOptions;
						 nodep.setNodeancestors(childnode.getNodeancestors());
						 nodep.setTreePath(childnode.getTreePath());
						 nodep.timestamp = nodep.timestamp + i;
						 
						 DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodep);
						 
						 TreeModel1.getInstance().insertNodeInto(child,actualNode,TreeModel1.getInstance().getChildCount(actualNode));

						Global.db.save(nodep);						 
						 DefaultMutableTreeNode actualParent = (DefaultMutableTreeNode) 
						 TreeModel1.getInstance().getChild(actualNode, i);
						 
						 DefaultMutableTreeNode databaseParent = new DefaultMutableTreeNode(childnode);

						 processChildren(databaseParent, actualParent);
			  }
			 
			return;
		}	
	}	
}
