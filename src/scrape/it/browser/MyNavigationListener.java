package scrape.it.browser;

import javax.swing.SwingUtilities;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;

import com.teamdev.jxbrowser.dom.DOMDocument;
import com.teamdev.jxbrowser.dom.DOMElement;
import com.teamdev.jxbrowser.events.NavigationEvent;
import com.teamdev.jxbrowser.events.NavigationFinishedEvent;
import com.teamdev.jxbrowser.events.NavigationListener;

public class MyNavigationListener implements NavigationListener{

	@Override
	public void navigationFinished(NavigationFinishedEvent arg0) {
		
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (null != Global.progress) {
                    Global.progress.setVisible(false);
                }
            }});
		
		

        SwingUtilities.invokeLater(new Runnable() {
            public void run() { 
            	// TODO Auto-generated method stub
        		DOMDocument domDocument = MyBrowser.getInstance().getBrowserDocument();
        		
            	DOMElement documentElement = (DOMElement) domDocument.getDocumentElement();
            	//documentElement.addEventListener("click", disallowNav, false);
          
            	
            	if(!Global.recording) {
            		//Global.allowNavigation = false;
            		return;
            	}
            	
        		Global.currentURL = domDocument.getURL();
                if(MyBrowser.getInstance().browser.isNavigationFinished()){
        			//MyBrowser.getInstance().browser.waitReady();
                	Global.allowNavigation = false;

        		  	String js = "document.onclick= function(event) { if (event===undefined) event= window.event; var target= 'target' in event? event.target : event.srcElement; path= getPathTo(target); var headings = document.evaluate('//' + path, document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); thisHeading.setAttribute('xpath',path); }; function getPathTo(element) { if (element===document.body) return element.tagName; var ix= 0; var siblings= element.parentNode.childNodes; for (var i= 0; i<siblings.length; i++) { var sibling= siblings[i]; if (sibling===element) return getPathTo(element.parentNode)+'/'+element.tagName+'['+(ix+1)+']'; if (sibling.nodeType===1 && sibling.tagName===element.tagName) ix++; } };";
        	    	String js1 = "document.onmouseover= function(event) { if (event===undefined) event= window.event; var target= 'target' in event? event.target : event.srcElement; target.style.backgroundColor = 'pink'; }; document.onmouseout= function(event) { if (event===undefined) event= window.event; var target= 'target' in event? event.target : event.srcElement; target.style.backgroundColor = 'transparent'; };"; 	
        	    	
        	    	//this.browser.executeScript("alert('fuck');");
        	    	
        	    		MyBrowser.getInstance().browser.executeScript(js + js1);
        	    		
        		}  
            }
        }); 
        
        Global.showDefaultCursor();
	}

	@Override
	public void navigationStarted(NavigationEvent arg0) {
		
		Global.navStarted = true;
		
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (null != Global.progress) {
                    Global.progress.setVisible(true);
                    Global.progress.validate();
                }

            }
        });
        
		

        	if(Global.allowNavigation){        		        	
        		return;
        	}else{
        		MyBrowser.getInstance().browser.stop();
        		System.out.println("stopped");
		 	}


	}
}
