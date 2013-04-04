package scrape.it.main.actions;

import java.awt.Frame;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import scrape.it.browser.MyBrowser;
import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.ActivateNode;
import scrape.it.widgets.tree.ActivatedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class CreateNewTaskAction {
	

	private URL thisdomain;
	private String basedomain;

	private String url;
	private Object parent;
	private int nodeIndex;
	
	public CreateNewTaskAction(){
		
		
		Frame[] frames = ClientGui.getFrames();
		
		try{
			url = JOptionPane.showInputDialog(ClientGui.frame, "Enter Domain To Scrape:");
			if(url == null || url.isEmpty()) return;

		Global.url = url;

    	MyBrowser.getInstance().navigateTo(Global.url); //browse to url
				
		try {
			thisdomain = new URL(url); 
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			if (url.startsWith("http://")){
				
			}else{
				try {
					thisdomain = new URL("http://" + url);
				} catch (MalformedURLException ez) {
					org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while creating url", ez);
				}
			}
		}			
    	basedomain = thisdomain.getHost(); //get basedomain
    	Global.currentdomain = basedomain;
    	
    	parent = TreeModel1.getInstance().getRoot();
    	nodeIndex = TreeModel1.getInstance().getChildCount(parent);
    	
    	NodePro tasknode = new NodePro();
    	tasknode.setText("<html><b>" + basedomain + "</b></html>");
    	tasknode.setNodeType("head");
    	tasknode.setPageurl(thisdomain.toString());
    	tasknode.setNodeIndex(nodeIndex);
    	tasknode.setId(UUID.randomUUID().getMostSignificantBits());
    	//create task node on Tree
    	DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(tasknode);
    	
    	TreeModel1.getInstance().insertNodeInto(newNode, (MutableTreeNode) TreeModel1.getInstance().getRoot(), 0);
    	TreeModel1.getInstance().nodeChanged(newNode);
    	Tree.getInstance().setSelectionRow(1);
    	new ActivateNode();
    	//save task node
    	try{
    		Global.db.save(tasknode);
    	}catch(Exception ez){
    		org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while saving", ez);
    	}
		ActivatedNode.nodeProperty = tasknode;
		ActivatedNode.treeNode = newNode;
		ActivatedNode.id = tasknode.getId();
		ActivatedNode.index = tasknode.getNodeIndex();
		ActivatedNode.parentID = tasknode.getParentID();
		
		}catch(Exception ezz){
			org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while new task action", ezz);
		}
		
		
	}
}
