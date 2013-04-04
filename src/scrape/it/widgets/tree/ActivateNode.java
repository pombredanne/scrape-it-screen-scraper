package scrape.it.widgets.tree;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.teamdev.jxbrowser.dom.DOMDocument;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;
import scrape.it.main.actions.TreeCrawler;
import scrape.it.persistence.NodePro;

public class ActivateNode {

	public ActivateNode(){



		


		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();
		if(node == null){Global.showDefaultCursor();return;}
		
		Global.showWaitCursor();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
	
				highlightActiveNode();
			}
		});
		
		NodePro tnode = (NodePro) node.getUserObject();

		

			if(getCurrentURL().equals(SelectedNode.url)){

			}else{
				Global.allowNavigation = true;
				
				MyBrowser.getInstance().navigateTo(SelectedNode.url);
			}
	

		
		/*Save Currently Selected Node to ActivatedNode*/
		ActivatedNode.nodeProperty = SelectedNode.nodeProperty;
		ActivatedNode.treeNode = SelectedNode.treeNode;
		ActivatedNode.id = SelectedNode.id;
		ActivatedNode.url = SelectedNode.url;
		ActivatedNode.text = SelectedNode.text;
		ActivatedNode.xpath = SelectedNode.xpath;
		ActivatedNode.command = SelectedNode.command;
		ActivatedNode.type = SelectedNode.type;
		ActivatedNode.ancestors = SelectedNode.ancestors;
		ActivatedNode.siblingIndex = SelectedNode.siblingIndex;
		ActivatedNode.index = SelectedNode.index;
		ActivatedNode.treePath = SelectedNode.treePath;
		ActivatedNode.parentID = SelectedNode.parentID;
		if (SelectedNode.nodeProperty.getNodeType() == null) return;
		
		if (SelectedNode.nodeProperty.getNodeType() != null &&
				TreeModel1.getInstance().isLeaf(SelectedNode.treeNode)){
			try {
				BuildTree bt = new BuildTree(ActivatedNode.treeNode);


			} catch (Exception e1) {
			}
		}
		
		//mark items
		MyBrowser.getInstance().browser.waitReady();
		markElementsOnPage();


		Global.showDefaultCursor();
	}
	
	private static String getCurrentURL() {
		DOMDocument d = (DOMDocument) MyBrowser.getInstance().browser.getDocument();
		String url = d.getURL();
		return url;
	}

	public static void markElementsOnPage(){

		List<NodePro> nodelist = Global.db.query(new OSQLSynchQuery<NodePro>("select * from NodePro where parentID = " + ActivatedNode.id + " or id = " + ActivatedNode.id));
		
		//db.query(Pilot.class);
		if (nodelist.isEmpty()){	
			System.out.println("empty");	
			return;
		}else{
			//Global.showWaitCursor();
			 for (int i=0; i< nodelist.size(); i++)
			  {			   
				 
			     NodePro childnode = nodelist.get(i);
			     final String axpath = childnode.getXpath();
			     final String acommand = childnode.getCommand();
					System.out.println(childnode.getText());
			     SwingUtilities.invokeLater(new Runnable() {
					   public void run() {
						   if(axpath == null) return;
						   String newxpath = axpath;
						   if(axpath.startsWith("//")){
							   newxpath = axpath.substring(2);
						   }
		
			            	//String js = "var headings = document.evaluate('//" + axpath + "', document, null, XPathResult.ANY_TYPE, null);var thisHeading = headings.iterateNext();thisHeading.style.backgroundColor = 'red';thisHeading.setAttribute('xpath','" + axpath + "');";
			            	String js = "var nodes = document.evaluate('//" + newxpath + "', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.setAttribute('clicked','true'); node.style.border='solid red 2px'; node.style.outlineColor='red';node.style.outlineWidth='2px';node.style.outlineStyle='solid';};";

			            	MyBrowser.getInstance().browser.executeScript(js);
			            }
			     });
			  }
			 //Global.showDefaultCursor();
			return;
		}	
		
	}

	private static void highlightActiveNode() {
		

			  
				TreeCrawler tc = new TreeCrawler();
				tc.clearActiveIcon();

				  
				  DefaultMutableTreeNode current = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();
				  NodePro npt = (NodePro) current.getUserObject();
				  npt.setNodeancestors("activated");

				  TreeModel1.getInstance().nodeChanged(current);

	}
		
	


}
