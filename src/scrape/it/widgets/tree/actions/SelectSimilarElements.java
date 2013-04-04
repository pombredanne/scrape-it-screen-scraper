package scrape.it.widgets.tree.actions;

import org.w3c.dom.NodeList;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;
import scrape.it.widgets.tree.SelectedNode;

import com.jniwrapper.Str;
import com.teamdev.jxbrowser.dom.DOMElement;


public class SelectSimilarElements {
	public SelectSimilarElements(){
  	    try {
  	    	Global.showWaitCursor();
  	    	String axpath = SelectedNode.xpath;
  	    	String element = SelectedNode.element;

  	    	//String js = "var headings = document.evaluate('//BODY/TABLE[1]/TBODY[1]/TR[1]/TD[2]/TABLE[1]/TBODY[1]/TR[1]/TD[2]/DIV[1]/DIV[1]/UL[1]/LI[1]/A[1]', document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); thisHeading.setAttribute('style','background-color:yellow;');";
  	    	
  	    	//String js1 = "document.onclick= function(event) { if (event===undefined) event= window.event; var target= 'target' in event? event.target : event.srcElement; path= getPathTo(target); var headings = document.evaluate('//' + path, document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); thisHeading.setAttribute('xpath',path); }; function getPathTo(element) { if (element===document.body) return element.tagName; var ix= 0; var siblings= element.parentNode.childNodes; for (var i= 0; i<siblings.length; i++) { var sibling= siblings[i]; if (sibling===element) return getPathTo(element.parentNode)+'/'+element.tagName+'['+(ix+1)+']'; if (sibling.nodeType===1 && sibling.tagName===element.tagName) ix++; } };";
  	    	String js = "var headings = document.evaluate('//" + axpath + "', document, null, XPathResult.ANY_TYPE, null);var thisHeading = headings.iterateNext();thisHeading.style.backgroundColor = 'red';thisHeading.setAttribute('xpath','" + axpath + "');";
  	    	MyBrowser.getInstance().browser.executeScript(js);
  	    	
  	    	NodeList tags = MyBrowser.getInstance().getBrowserDocument().getElementsByTagName(element);
  	    	for(int x=0;x<tags.getLength();x++){
  	    		DOMElement thisnode = (DOMElement) tags.item(x);
  	    		if(thisnode.hasAttribute("xpath") && thisnode.getAttribute("xpath").equalsIgnoreCase(axpath)){
  	    			thisnode.click();
  	    			break;
  	    		}
  	    	}

  	    	
			//focusedElement = MyBrowser.getInstance().getBrowserDocument().getE
  	    	//xg.getElementByXpath(SelectedNode.xpath, SelectedNode.element).click();
			
			//focusedElement.click();	
		} catch (Exception eee) {
			org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Selecting Similar Elements", eee);
			eee.printStackTrace();
		}
	}
}
