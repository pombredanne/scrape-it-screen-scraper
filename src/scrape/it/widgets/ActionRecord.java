package scrape.it.widgets;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;

public class ActionRecord {

	public ActionRecord(){
		Global.recording = true;
		  	String js = "document.onclick= function(event) { if (event===undefined) event= window.event; var target= 'target' in event? event.target : event.srcElement; path= getPathTo(target); var headings = document.evaluate('//' + path, document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); thisHeading.setAttribute('xpath',path); }; function getPathTo(element) { if (element===document.body) return element.tagName; var ix= 0; var siblings= element.parentNode.childNodes; for (var i= 0; i<siblings.length; i++) { var sibling= siblings[i]; if (sibling===element) return getPathTo(element.parentNode)+'/'+element.tagName+'['+(ix+1)+']'; if (sibling.nodeType===1 && sibling.tagName===element.tagName) ix++; } };";
	    	String js1 = "document.onmouseover= function(event) { if (event===undefined) event= window.event; var target= 'target' in event? event.target : event.srcElement; target.style.backgroundColor = 'pink'; }; document.onmouseout= function(event) { if (event===undefined) event= window.event; var target= 'target' in event? event.target : event.srcElement; target.style.backgroundColor = 'transparent'; };"; 	
	    	
	    	//this.browser.executeScript("alert('fuck');");

	    		MyBrowser.getInstance().browser.executeScript(js + js1);
	}
	
	public ActionRecord(Boolean finishedRecording){
		if(finishedRecording){
			Global.recording = false;
			
 		  	String js = "document.onclick= function(event) { };";
	    	String js1 = "document.onmouseover= function(event) { };"; 	
	    	
	    	//this.browser.executeScript("alert('fuck');");

	    	MyBrowser.getInstance().browser.executeScript(js + js1);
			
		}
	}
	
}
