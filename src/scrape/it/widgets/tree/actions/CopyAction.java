package scrape.it.widgets.tree.actions;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.Tree;

public class CopyAction {
	public CopyAction(){
		  Global.copiedNodes.clear();	
	  	  TreePath[] nodes = Tree.getInstance().getSelectionPaths();
		  	 
	  	  int columnCount = 0;
	  	  //iterate through all selected nodes and save them in ArrayList		    	  
	  	  for(int i = 0; i < nodes.length ; i++)
	  	  {
	  		  TreePath temp = nodes[i];
	  		  Object tempObj = temp.getLastPathComponent();		    		  
	  		  DefaultMutableTreeNode treNode = (DefaultMutableTreeNode) tempObj;		    		  
	  		  Object dwg = treNode.getUserObject();		    	  
	  	
	  		  try{
	  			  NodePro nodeinfo = (NodePro) dwg;
	  			  Long thisid = nodeinfo.getId();
	  			  
	  			  System.out.println("ID " + thisid);
	  			  Global.copiedNodes.add(nodeinfo);
	  			  	  			  
	  			  columnCount++;
	  		  }catch(Exception eee){
	  		  }			    	  	    	
	  		  
	  	 }		
	}
}
