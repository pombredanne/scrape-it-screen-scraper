package scrape.it.widgets.tree.actions;

import scrape.it.browser.MyBrowser;

public class FindSimilarXpath {
	
	public FindSimilarXpath() {
		// TODO Auto-generated constructor stub		
	}

	public String generateXpath(String axpath, int i){
		
			
	
		String js = "function returnSimilarElements(xpath,count){ var nodes = document.evaluate('//' + xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); if(nodes.snapshotLength > 0){ for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.style.backgroundColor='yellow'; } return xpath + '#' + count; }else{ return 'false'; } }; function dothis(aaxpath,position){ var mySplitResult = aaxpath.split('/'); var lastOriginalString = ''; for (var i = mySplitResult.length -1; i > 0; i--) { if(lastOriginalString !='') mySplitResult[i+1] = lastOriginalString; var originalString = mySplitResult[i]; lastOriginalString = originalString; var originalStringElement = originalString.split('['); originalStringElement = originalStringElement[0]; var newString = originalString + '/following-sibling::' + originalStringElement; mySplitResult[i] = newString; var trythis = mySplitResult.join('/'); var returned = ''; if(i >= position){ returned = 'false'; }else{ returned = returnSimilarElements(trythis,i); } if (returned != 'false'){ return returned; } } return 'nothing'; }; dothis('" + axpath +"','" + i + "');";
  	    	String out = MyBrowser.getInstance().browser.executeScript(js);
  	    	System.out.println("first " + js);

  	    	if(out.startsWith("nothing") || out.startsWith("false")) return "//" + axpath;
  	    	
  	    	return "//" + axpath + "|//" + out;
	}

	public String regenerateXpath(String axpath, int i) {
			String[] newStrings = axpath.split("\\|");
			String originalXpath = newStrings[0];
			String lastXpath = newStrings[1];
			String aaxpath = originalXpath;
			
			
			String clearjs = "var nodes = document.evaluate('" + lastXpath + "', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); if(nodes.snapshotLength > 0){ for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.style.backgroundColor='transparent'; }; }else{}; ";
			MyBrowser.getInstance().browser.executeScript(clearjs);
			
    		String js = "function returnSimilarElements(xpath,count){ var nodes = document.evaluate('' + xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); if(nodes.snapshotLength > 0){ for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.style.backgroundColor='yellow'; } return xpath + '#' + count; }else{ return 'false'; } }; function dothis(aaxpath,position){ var mySplitResult = aaxpath.split('/'); var lastOriginalString = ''; for (var i = mySplitResult.length -1; i > 0; i--) { if(lastOriginalString !='') mySplitResult[i+1] = lastOriginalString; var originalString = mySplitResult[i]; lastOriginalString = originalString; var originalStringElement = originalString.split('['); originalStringElement = originalStringElement[0]; var newString = originalString + '/following-sibling::' + originalStringElement; mySplitResult[i] = newString; var trythis = mySplitResult.join('/'); var returned = ''; if(i >= position){ returned = 'false'; }else{ returned = returnSimilarElements(trythis,i); } if (returned != 'false'){ return returned; } } return 'nothing'; }; dothis('" + aaxpath +"','" + i + "');"; 
  
	    	String out = MyBrowser.getInstance().browser.executeScript(js);
	    	
	    	if(out.startsWith("nothing") || out.startsWith("false")) return "" + axpath;
	    	
			return originalXpath +"|" + out;
	}

	public String oldgenerateXpath(String axpath){
		
    	String js = "function returnSimilarElements(xpath){ var nodes = document.evaluate('//' + xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); if(nodes.snapshotLength > 0){ for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.style.backgroundColor='yellow'; } return xpath; }else{ return 'false' } }; function dothis(aaxpath){ var mySplitResult = aaxpath.split('/'); var lastOriginalString = ''; for (var i = mySplitResult.length -1; i > 0; i--) { if(lastOriginalString !='') mySplitResult[i+1] = lastOriginalString; var originalString = mySplitResult[i]; lastOriginalString = originalString; var originalStringElement = originalString.split('['); originalStringElement = originalStringElement[0]; var newString = originalString + '/following-sibling::' + originalStringElement; mySplitResult[i] = newString; var trythis = mySplitResult.join('/'); var returned = returnSimilarElements(trythis); if (returned != 'false'){ return returned; } } return 'nothing'; }; dothis('" + axpath + "');";
	    String out = MyBrowser.getInstance().browser.executeScript(js);

	    	return "//" + axpath + "|//" + out;
	}
}
