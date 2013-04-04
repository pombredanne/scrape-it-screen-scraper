package scrape.it.browser;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.Global;
import scrape.it.main.actions.ApplyRule;
import scrape.it.persistence.NodePro;
import scrape.it.utilities.XpathUtility;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

import com.teamdev.jxbrowser.dom.DOMDocument;
import com.teamdev.jxbrowser.dom.DOMElement;

public class MyMouseListener implements MouseInputListener{

	private String xpath;

	@Override
	public void mouseClicked(final MouseEvent evt) {

		// TODO Auto-generated method stub
        SwingUtilities.invokeLater(new Runnable() {
            private XpathUtility xg;

			public void run() {
				
		    	System.out.println("fuck");
		    	
				DOMDocument domDocument = MyBrowser.getInstance().getBrowserDocument();
				DOMElement documentElement = (DOMElement) domDocument.getDocumentElement();
				DOMElement currentElement = (DOMElement) domDocument.getElementFromPoint(evt.getX(), evt.getY());
				String tag = currentElement.getTagName().toUpperCase();
				
				if(currentElement.getNodeName().equalsIgnoreCase("html")) return;
				
				ClickedHtmlElement.element = currentElement;
				String elementText;

				if (currentElement != null) {            		
					elementText = currentElement.getTextContent();
					
					try{
						xpath = currentElement.getAttribute("xpath");
						ClickedHtmlElement.xpath = xpath;


					}catch(Exception e){
						org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error occured while getting xpath ",e);
					}
					               
					//if this was already clicked before...
					if (currentElement.getAttribute("clicked").equals("true")){
						org.slf4j.LoggerFactory.getLogger(this.getClass()).info("Already Clicked ");
					}else{
                                                       	                                	
						try{			
							if(Global.recording){
								new ApplyRule(currentElement,xpath);
							}
						}catch(Exception ew){
							org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while inserting element",ew);
						}
                   	                    
                }                                
               
				//tree.getPath(
                //System.out.println(getElementXpath(currentElement).toString());
        }    
            }
        });
        
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
