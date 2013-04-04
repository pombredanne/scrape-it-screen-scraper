package scrape.it.widgets.tree.actions;

import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.w3c.dom.NodeList;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.utilities.XpathUtility;
import scrape.it.widgets.tree.ActivatedNode;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;

import com.teamdev.jxbrowser.dom.DOMDocument;
import com.teamdev.jxbrowser.dom.DOMElement;

public class ClickingAction {
	
	public ArrayList<String> selectedNodes = new ArrayList();
	private XpathUtility xg;
	private DOMElement focusedElement;
	
	public ClickingAction(){
		  
		// TODO Auto-generated method stub
/*
  	  TreePath[] nodes = Tree.getInstance().getSelectionPaths();

  	  long thisid;
	//iterate through all selected nodes and save them in ArrayList		    	  
  	  for(int i = 0; i < nodes.length ; i++)
  	  {
  		  TreePath temp = nodes[i];
  		  Object tempObj = temp.getLastPathComponent();		    		  
  		  DefaultMutableTreeNode treNode = (DefaultMutableTreeNode) tempObj;		    		  
  		  Object dwg = treNode.getUserObject();		    	  
  	
  		  try{
  			  NodePro nodeinfo = (NodePro) dwg;
  			  thisid = nodeinfo.getId();
  			  nodeinfo.setCommand("click");
  			  
  			  System.out.println("ID " + thisid);			    			  
  		  }catch(Exception eee){
  			org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while setting click command", eee);
  		  }			    	  	    	
  		  
  	 }*/
		
		//enable navigation
		Global.allowNavigation = true;
		// TODO Auto-generated method stub
		DOMDocument domDocument = MyBrowser.getInstance().getBrowserDocument();
  	    DOMElement documentElement = (DOMElement) domDocument.getDocumentElement();
  	   // xg = new XpathUtility();
  	    
  	    


  	    try {
  	    	Global.showWaitCursor();
  	    	String axpath = SelectedNode.nodeProperty.getXpath();
  	    	if(axpath.contains("|")){
  	    		String[] elements = axpath.split("\\|");
  	    		axpath = elements[0];
  	    	}
  	    	
			String newxpath = axpath;
			if(axpath.startsWith("//")){
				   axpath = axpath.substring(2);
			}

  	    	//String js = "var headings = document.evaluate('//BODY/TABLE[1]/TBODY[1]/TR[1]/TD[2]/TABLE[1]/TBODY[1]/TR[1]/TD[2]/DIV[1]/DIV[1]/UL[1]/LI[1]/A[1]', document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); thisHeading.setAttribute('style','background-color:yellow;');";
  	    	
  	    	//String js1 = "document.onclick= function(event) { if (event===undefined) event= window.event; var target= 'target' in event? event.target : event.srcElement; path= getPathTo(target); var headings = document.evaluate('//' + path, document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); thisHeading.setAttribute('xpath',path); }; function getPathTo(element) { if (element===document.body) return element.tagName; var ix= 0; var siblings= element.parentNode.childNodes; for (var i= 0; i<siblings.length; i++) { var sibling= siblings[i]; if (sibling===element) return getPathTo(element.parentNode)+'/'+element.tagName+'['+(ix+1)+']'; if (sibling.nodeType===1 && sibling.tagName===element.tagName) ix++; } };";
  	    	String js = "var headings = document.evaluate('//" + axpath + "', document, null, XPathResult.ANY_TYPE, null);var thisHeading = headings.iterateNext();thisHeading.style.border = 'red solid 4px';thisHeading.style.outlineColor='red';thisHeading.style.outlineWidth='4px';thisHeading.style.outlineStyle='solid';thisHeading.setAttribute('name','" + axpath + "');";
  	    	System.out.println(js); 
  	    	MyBrowser.getInstance().browser.executeScript(js);
  	    	NodeList nodes = MyBrowser.getInstance().getBrowserDocument().getElementsByName(axpath);
  	    	DOMElement thisnode = (DOMElement) nodes.item(0);
  	    	thisnode.click();
  	    	/*
  	    	
  	    	NodeList tags = MyBrowser.getInstance().getBrowserDocument().getElementsByTagName(SelectedNode.nodeProperty.getElement());
  	    	for(int x=0;x<tags.getLength();x++){
  	    		DOMElement thisnode = (DOMElement) tags.item(x);
  	    		if(thisnode.hasAttribute("xpath") && thisnode.getAttribute("xpath").equalsIgnoreCase(axpath)){
  	    			if(thisnode.hasAttribute("clicked")){
  	    				thisnode.setAttribute("clicked", "");
  	    			}
  	    			thisnode.click();
  	    			break;
  	    		}
  	    	}
  	    	
  	    	
*/
  	    	
			//focusedElement = MyBrowser.getInstance().getBrowserDocument().getE
  	    	//xg.getElementByXpath(SelectedNode.xpath, SelectedNode.element).click();
			
			//focusedElement.click();	
  	    	
	
			
		} catch (Exception eee) {
			org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while sending ClickAction()",eee);

		}
  	    
  	    
		/*Save Currently Selected Node to ActivatedNode
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
		*/
		Global.showDefaultCursor();
	}
}
