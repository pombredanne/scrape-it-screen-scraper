package scrape.it.widgets.tree;

import grid.designer.SpreadsheetDesigner;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;

import com.capsicumcorp.swing.spreadsheet.JSpread;
import com.teamdev.jxbrowser.dom.DOMElement;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.ToolBar;

public class TreeSelectionListenerClass implements javax.swing.event.TreeSelectionListener{
	public String thisid;
	public DefaultMutableTreeNode node;
	public NodePro focusedNode;
	public String pageurl;

	public TreeSelectionListenerClass() {
	}
	
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();

		  /* if nothing is selected */ 
		  if (node == null) {
	
		        ToolBar.recordButton.setEnabled(false);
		        ToolBar.playButton.setEnabled(false);
		        ToolBar.stopButton.setEnabled(false);
		        ToolBar.copyButton.setEnabled(false);
		        ToolBar.pasteButton.setEnabled(false);
		        ToolBar.deleteButton.setEnabled(false);
		        ToolBar.cutButton.setEnabled(false);
		        ToolBar.editButton.setEnabled(false);
		        ToolBar.navButton.setEnabled(false);
			  return;
		  }else{
		        ToolBar.recordButton.setEnabled(true);
		        ToolBar.copyButton.setEnabled(true);
		        ToolBar.pasteButton.setEnabled(true);
		        ToolBar.deleteButton.setEnabled(true);
		        ToolBar.cutButton.setEnabled(true);
		        ToolBar.editButton.setEnabled(true);
		        ToolBar.navButton.setEnabled(true);
		  }

		  /* retrieve the node that was selected */ 
		  focusedNode = (NodePro) node.getUserObject();		 		  
		  
		  /* React to the node selection. */
		  try{
		
			
			 //save fields of selection
			  SelectedNode.treeNode = node;
			  SelectedNode.nodeProperty = focusedNode;			  
			  SelectedNode.id = focusedNode.getId();
			  SelectedNode.url = focusedNode.getPageurl();
			  SelectedNode.text = focusedNode.getText();
			  SelectedNode.xpath = focusedNode.getXpath(); 
			  SelectedNode.command = focusedNode.getCommand();
			  SelectedNode.type = focusedNode.getNodeType();
			  SelectedNode.ancestors = focusedNode.getNodeancestors();
			  SelectedNode.siblingIndex = focusedNode.getNodeSiblingIndex();
			  SelectedNode.index = focusedNode.getNodeIndex();
			  SelectedNode.parentID = focusedNode.getParentID();
			  SelectedNode.element = focusedNode.getElement();
			  System.out.println(focusedNode.getNodeIndex() + " " + focusedNode.timestamp + " " + SelectedNode.element + " " + SelectedNode.url + " ID " + SelectedNode.id + "PARENT " + SelectedNode.parentID + " XPATH " + SelectedNode.xpath + " " + SelectedNode.type + " " + SelectedNode.nodeProperty.timestamp);
			  System.out.println(focusedNode.commands);
			  System.out.println(focusedNode.columns);
			  try{
				  //System.out.println(focusedNode.getNodeType());
					if(focusedNode.getNodeType() != null && focusedNode.getNodeType().contains("head")){
						
						   ToolBar.playButton.setEnabled(true);
						   ToolBar.editButton.setEnabled(false);
					        ToolBar.stopButton.setEnabled(true);
					        ToolBar.copyButton.setEnabled(false);
					        ToolBar.pasteButton.setEnabled(false);
					        ToolBar.deleteButton.setEnabled(true);
					        ToolBar.cutButton.setEnabled(false);
					        ToolBar.proxyButton.setText("Set Proxy to Task (Overrides Global Proxy)");
					        ToolBar.proxyButton.setEnabled(true);
					        ToolBar.navButton.setEnabled(true);
						
						final JSpread existingSheet = SpreadsheetDesigner.workbook.getSheet(SelectedNode.text + " (" + SelectedNode.id + ")");
						
						if(existingSheet != null){

							SpreadsheetDesigner.workbook.focus(existingSheet);
						}else{
							
							JSpread sheet1 = new JSpread(0,0);
							SpreadsheetDesigner.workbook.addSheet(SelectedNode.text + " (" + SelectedNode.id + ")", sheet1);
							SpreadsheetDesigner.workbook.focus(sheet1);
						}
					}else{
						   ToolBar.playButton.setEnabled(false);
					        ToolBar.stopButton.setEnabled(false);
					        ToolBar.copyButton.setEnabled(true);
					        ToolBar.pasteButton.setEnabled(true);
					        ToolBar.deleteButton.setEnabled(true);
					        ToolBar.cutButton.setEnabled(true);
					        ToolBar.proxyButton.setText("Set Global Proxy");
					        ToolBar.proxyButton.setEnabled(false);
					        ToolBar.editButton.setEnabled(true);
					        ToolBar.navButton.setEnabled(true);
					        
					}
			  	}catch(Exception NullPointerException){
			  		org.slf4j.LoggerFactory.getLogger(this.getClass()).error("null pointer while selecting head node " +
			  				"and creating spreadsheet", NullPointerException);
			  	}
			  
			  	
			  
		  }catch(Exception eee){
				org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while treeselectionlistener", eee);
		  }
		  
		  //System.out.println(browser.getContent());
	  //dblclicked = false;
	}

}