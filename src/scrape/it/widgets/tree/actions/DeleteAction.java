package scrape.it.widgets.tree.actions;

import java.util.Enumeration;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;
import scrape.it.main.actions.TreeCrawler;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.ActivateNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class DeleteAction {
	private DefaultMutableTreeNode parent;

	public DeleteAction(){
	  	  TreePath[] nodes = Tree.getInstance().getSelectionPaths();
		  	 
	  	  //iterate through all selected nodes and save them in ArrayList		    	  
	  	  for(int i = 0; i < nodes.length ; i++)
	  	  {
	  		  TreePath temp = nodes[i];
	  		  Object tempObj = temp.getLastPathComponent();		    		  
	  		  DefaultMutableTreeNode treNode = (DefaultMutableTreeNode) tempObj;		    		  
	  		 if(i == 0){
	  			 parent = (DefaultMutableTreeNode) treNode.getParent();
	  		 }
	  		  try{
	  			  deleteNodes(treNode);	  			  	  			  
	  		  }catch(Exception eee){
	        	   org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while deleting nodes",eee);
	  		  }
	  		  

	  		  
	  	 }
	  	  
	  	 //get the Parent of deleted node and activate it. if it's header, then just load blank.
	  	 NodePro np = (NodePro) parent.getUserObject();
	  	 if(np.getNodeType().contains("head")){
	  		 MyBrowser.getInstance().browser.navigate("about:blank");
	  	 }else{
	  		MyBrowser.getInstance().browser.navigate(np.getPageurl());
	  		TreePath path = new TreePath(parent.getPath());
			Tree.getInstance().setSelectionPath(path);
			Tree.getInstance().scrollPathToVisible(path);
			new ActivateNode();
	  	 }
	  	  
	}
	


	private void deleteNodes(DefaultMutableTreeNode treNode) throws Exception{
		
		//save last selectedNode's parent

		
		
		Enumeration<DefaultMutableTreeNode> children = treNode.children();
		DefaultMutableTreeNode node;
		while(children.hasMoreElements()){
			node = children.nextElement();
			deleteNodes(node);
		}
		//delete this node
		final NodePro nodepro = (NodePro) treNode.getUserObject();	
		
		
		
		long npid = nodepro.getId();
		List<NodePro> nodelist = Global.db.query(new OSQLSynchQuery<NodePro>("select * from NodePro where id  = " + npid));
		

		NodePro np = nodelist.get(0);
		Global.db.delete(np);

		System.out.println(nodelist.get(0));
		
		removeMarker(np);
		
		TreeModel1.getInstance().removeNodeFromParent(treNode);
		
		//on delete, activate parent Node
		
		
	}
	
	private void removeMarker(NodePro anp) {
	     final String axpath = anp.getXpath();
		 final String axpath1 = "";	
	     SwingUtilities.invokeLater(new Runnable() {
			   public void run() { 					            	
	            	
	            	String js = "var nodes = document.evaluate('//" + axpath + "', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.setAttribute('clicked',''); node.style.border='transparent'; node.style.outlineColor='transparent';node.style.outlineWidth='';node.style.outlineStyle='transparent';};";
	            	MyBrowser.getInstance().browser.executeScript(js);
	            }
	     });
		
	}
}
