package scrape.it.server;

import grid.designer.SpreadsheetDesigner;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import javax.swing.tree.DefaultMutableTreeNode;

import net.sf.jeppers.grid.SelectionModel;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.html.HTMLElement;
import org.w3c.dom.html.HTMLImageElement;

import scrape.it.browser.MyBrowser;
import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

import com.capsicumcorp.swing.spreadsheet.JSpread;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.util.concurrent.AbstractExecutionThreadService;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.BrowserFactory;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;




public class Scraper extends AbstractExecutionThreadService implements NavigationListener{
	
	private Browser browser;
	private LinkedList<NodePro> queue =  new LinkedList<NodePro>();
	private ODatabaseObjectTx db;
	private Boolean testMode = false;
	
	private HashMap<String, String> rowContainer =  new HashMap<String,String>();
	protected String userName = "";
	protected String password = "";
	private JSpread activeSheet;
	private boolean columnSetupAlready = false;
	private boolean backButtonPressed = false;
	private String previousPage;
	private String filePathString;
	private String filePathString2;
	private boolean isPaused;
	private NodePro headerNode;
	private int attemptCounter = 0;
	private NodePro backNode;
	private Configurable contentSettings;
	private Map columnNames;
	private NodePro root;
	private String columnSize;
	private Map commands;
	private String text;
	private HashMultimap<String, String> multimap;
	private HashBasedTable<Integer, String, String> table;
	

	public Scraper(){


	}
	
	public void begin(){
	try{
		headerNode = SelectedNode.nodeProperty;
		root = SelectedNode.nodeProperty;

		queue.add(root);
		
		isStartNode();
			
		while(!queue.isEmpty()){
			NodePro currentNode = queue.pollFirst();	
			
			if(currentNode.getNodeType() != null && currentNode.getNodeType().startsWith("head")){
				//getTempSheet or createTempSheet if it dont exist.
				
				initSpreadSheet(currentNode);

				 //load first page.
				browser.navigate(currentNode.getPageurl());
				browser.waitReady();
					
				process(currentNode);
				
			}else{
				
				currentNode.status = "working";
				Tree.getInstance().revalidate();
				Tree.getInstance().repaint();
				browser.waitReady();
				process(currentNode);
				
				currentNode.status = "done";
				TreeModel1.getInstance().nodeChanged(new DefaultMutableTreeNode(currentNode));
				Tree.getInstance().revalidate();
				Tree.getInstance().repaint();
				
			}
		}
		
		this.end();

		JOptionPane.showMessageDialog(ClientGui.frame, "Scraping job for " + root.getText().replaceAll("\\<.*?\\>", "") + " has finished successfully");

	}catch(Exception e){
		JOptionPane.showMessageDialog(ClientGui.frame, "Scraping job for " + root.getText().replaceAll("\\<.*?\\>", "") + " has stopped with some errors");
		org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while running scraping job",e);
		this.end();
	}
	
	}
	
	public void end(){
		//clear the queue.
		queue.clear();
		root.setCommand("stop");
		TreeModel1.getInstance().nodeChanged(new DefaultMutableTreeNode(root));
		Global.scraperList.remove(root.getId());
		this.stop();
		
	}
	
	public void pause(){
		
		//sets nodepro status as paused.
		headerNode.setNodeType("pause");
		//isPaused = true;		
		//save current queue into List<NodePro> queue;
		headerNode.kew = queue;
		Global.db.save(headerNode);

		
	}

	private void initSpreadSheet(NodePro currentNode) {
		JSpread existingSheet = SpreadsheetDesigner.workbook.getSheet(currentNode.getText() + " (" + currentNode.getId() + ")");		
		if(existingSheet != null){
			existingSheet.setShowHeader(false);
			activeSheet = existingSheet;
		}else{
			JSpread sheet1 = new JSpread(0, 0);
			SpreadsheetDesigner.workbook.addSheet(currentNode.getText() + " (" + currentNode.getId()+ ")", sheet1);
			sheet1.setShowHeader(false);
			activeSheet = sheet1;
		}
		
	}

	private boolean isStartNode() {
		if(queue.size() == 1) return true;
		return false;
	}

	private void process(NodePro node) {
		


		if(node.getNodeType() != null && node.getNodeType().startsWith("head")){

			if(node.selectedOptions != null && node.selectedOptions != ""){
				setProxy(node.selectedOptions);
			}
			
			if(node.columns.size() > 0){
				columnNames = node.columns;
				columnSize = String.valueOf(node.columns.size());
				
				//write column headers
				rowContainer.putAll(columnNames);
				writeToSpreadSheet();
				
				rowContainer.clear();
			}
			
				browser.navigate(node.getPageurl()); 
				addChildrenToQueue(node);
				
				return;                             
		}else{
			commands = node.commands;
			text = node.getText();
			
			if(commands.containsKey("back")){
				backButtonPressed = true;
				backNode = node;
				previousPage= node.getPageurl();		
				
				browser.goBack();
				return;
			}
			
			if(commands.containsKey("gotoNextPage")){
				
				if(click(node)){
					node.setId(node.getParentID());
					addChildrenToQueue(node);
				}else{
					if(click(node)){
						node.setId(node.getParentID());
						addChildrenToQueue(node);
					}else{
						updateErrorOnTree(node);
					}
			}
			return;
				
			}
						
			if(commands.containsKey("repeat_all")){
				String nodeXpath = (String) commands.get("repeat_all");
				org.slf4j.LoggerFactory.getLogger(this.getClass()).info("nodeType: " + node.getNodeType());
				List<NodePro> similarList = findSimilarElements(node);

				addToQueue(similarList);
				return;		
				
			}
			
			if(commands.containsKey("select")){ addSelectOptionsToQueue(node); return;}
			
				if(commands.containsKey("selectoption")){
					browser.waitReady();
					String js = "var nodes = document.evaluate('//" + node.getXpath() + "', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.removeAttribute('disabled'); node.selected='1'; };"; 
					
					addChildrenToQueue(node);
					return;
				}
			
			if(commands.containsKey("type")){addInputTextsToQueue(node); return;}

				if(commands.containsKey("inputtext")){
					browser.waitReady();
					String js = "var headings = document.evaluate('//" + node.getXpath() + "', document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); thisHeading.removeAttribute('disabled');thisHeading.value = '" + node.selectedOptions + "'";
					
					browser.executeScript(js);
					
					addChildrenToQueue(node);
			    	return;
				}
			
			if(commands.containsKey("text")){
				
				String index = (String) commands.get("column_index");

				
				String data = getElementText(node);
				
				if(node.getText().contains("Ze Similar")){ //for all data belonging to a column
					table = HashBasedTable.create();
					
			    	if(node.getNodeSiblingIndex() >= 0 && node.getNodeSiblingIndex() != 99113){
			    		NodeList nodes = getSimilarElementsByXpath(node.getXpath(),node.attrName);
			    		DOMElement thisnode = (DOMElement) nodes.item(node.getNodeSiblingIndex());
			    		if(thisnode.hasChildNodes()){
			    			table.put(node.getNodeSiblingIndex(),index, "undefined");
			    		}else{
			    			String thisValue = thisnode.getTextContent();
			    			table.put(node.getNodeSiblingIndex(), index, thisValue);
			    		}
			    	}
			    	
			    	//on the very last item of similar column, if this is the last column, begin writing.
			    	if(node.getText().equals("Ze Similar Elements Last")){
			    		if(index != null && index.equals(columnSize)){
			    			for(int z =0;z<table.size();z++){
			    				Map<String, String> aRow = table.row(z); //get the columns
			    				rowContainer.putAll(aRow); //put it in rowContainer
			    				writeToSpreadSheet(); //write it.
			    			}
			    		}
			    	}

				
				}else{
				
					rowContainer.put(index, data);
				
					if(index != null && index.equals(columnSize)){
						if(table.size() > 0){ //if there is multiple data records on current page then.
			    			for(int z =0;z<table.size();z++){
			    				Map<String, String> aRow = table.row(z); //get the columns
			    				rowContainer.putAll(aRow); //put it in rowContainer
			    				writeToSpreadSheet(); //write it.
			    			}
						}else{
							writeToSpreadSheet();
						}
					}
				
				}
				
			}
						
			if(commands.containsKey("href")){
				
				String index = (String) commands.get("column_index");
				String data = getElementAttr(node, "href");				

				rowContainer.put(index, data);
				
				if(index.equals(columnSize)){
					writeToSpreadSheet();
				}
			}
			
			if(commands.containsKey("file")){
				
				String index = (String) commands.get("column_index");
				String data = saveFile(node);				

				rowContainer.put(index, data);
				
				if(index.equals(columnSize)){
					writeToSpreadSheet();
				}
			}
			
			if(commands.containsKey("img")){
				
				String index = (String) commands.get("column_index");
				String data = savePicture(node);				

				rowContainer.put(index, data);
				
				if(index.equals(columnSize)){
					writeToSpreadSheet();
				}
			}
			

			if(commands.containsKey("click")){				
				
				if(!click(node)){				
					updateErrorOnTree(node);
					return;				
				}else{				
					addChildrenToQueue(node);
					return;				
				}
				
			}else{
				//addChildrenToQueue(node);
			}
			
			
		}
		

		return;
	}
	


	private void writeToSpreadSheet() {
		int colSize = activeSheet.getColumnCount();
		int columnSize = rowContainer.size();

		 	if(colSize < columnSize){
		 		int difference = columnSize - colSize;

		 		activeSheet.insertColumns(colSize, difference);
		 	}
		 		
				writeRow();				
	}

	private String getElementAttr(NodePro node, String attr) {
        browser.waitReady();
        String axpath = node.getXpath();
        String pre = "var headings = document.evaluate('//" + axpath + "', document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); thisHeading.getAttribute('" + attr + "'); ";
        //browser.executeScript(pre);
       //String js = "extractString(); ";        
        String text = browser.executeScript(pre);
        int i =0;
        
        while(text ==null){
        	text = browser.executeScript(pre);
        	if(i==5) return "no text found";
        	i++;
        }
        
        return text;
	}

	private void updateErrorOnTree(NodePro node) {
		node.status = "error";
		Global.db.save(node);
		TreeModel1.getInstance().nodeChanged(new DefaultMutableTreeNode(node));
	}

	private int[] stringToArray(String selectedOptions) {

			String[] splits = selectedOptions.split(",");
		   	int[] numbers = new int[splits.length];
		    	for (int i=0; i < numbers.length; i++){
		    		 numbers[i] = Integer.parseInt(splits[i]);
		    	}
			return numbers;
	}

	private void addSelectOptionsToQueue(NodePro node) {
		List<NodePro> nodelist = new LinkedList<NodePro>();
		int[] selectIndex = stringToArray(node.selectedOptions);

		//for 'multiple' <select>
		if(node.getNodeancestors() != null && node.getNodeancestors() =="multiple"){
			
			
			StringBuffer xpath = new StringBuffer();
			
			xpath.append(node.getXpath() + "/option[" + selectIndex[0] + "]");
			
			for(int i=1;i<selectIndex.length;i++){
				int sindex = selectIndex[i];
				xpath.append("|//" + node.getXpath() + "/option[" + sindex + "]");
			}
			
			NodePro np = new NodePro();
			np.setXpath(xpath.toString());
			np.setId(node.getId());
			np.commands.put("selectoption", "true");
			np.setElement(node.getElement());
			nodelist.add(np);
			
		}else{ //for 'single' <select>
			for(int i=0;i<selectIndex.length;i++){
				int sindex = selectIndex[i];
				NodePro np = new NodePro();
				np.setXpath(node.getXpath() + "/option[" + sindex + "]");
				np.setId(node.getId());
				np.commands.put("selectoption", "true");
				np.setElement(node.getElement());
				nodelist.add(np);
			}
		}
		
		addToQueue(nodelist);
		return;
	}

	private void addInputTextsToQueue(NodePro node) {
		// TODO Auto-generated method stub

		//get text files.
		if(node.getCommand() != null){
			filePathString = node.getCommand();
		}else{
			filePathString ="";
		}
						
			filePathString2 = node.getId() + ".txt";
			
			//get id.txt and getCommand(txtfile);
			File f2 = new File(filePathString2);
			File f = new File(filePathString);
					
			if(f.exists()) { /* read the imported txt file to queue */
				
				List<String> keywords = fileToList(filePathString2);
				List<NodePro> nodelist = new LinkedList<NodePro>();
				for(int i=0;i<keywords.size();i++){								
					NodePro np = new NodePro();
					np.setId(node.getId());
					np.setXpath(node.getXpath());
					np.selectedOptions = keywords.get(i);
					np.setElement(node.getElement());
					np.commands.put("inputtext","true");
							
					nodelist.add(np);
				}
				addToQueue(nodelist);						
			}
			
			if(f2.exists()) { /* read textarea field and stick it into queue */
				
				List<String> keywords2 = fileToList(filePathString2);
				List<NodePro> nodelist2 = new LinkedList<NodePro>();
				for(int i=0;i<keywords2.size();i++){								
					NodePro np = new NodePro();
					np.setId(node.getId());
					np.setXpath(node.getXpath());
					np.selectedOptions = keywords2.get(i);
					np.setElement(node.getElement());
					np.commands.put("inputtext","true");
					
					nodelist2.add(np);
				}
				addToQueue(nodelist2);
			}
			
		return;
	}

	 private List<String> fileToList(String a) {
		 try {
			return readLines(a);
		} catch (IOException e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error reading file to list: " + e);
		}
		return null;
	}

	public List<String> readLines(String filename) throws IOException {
	        FileReader fileReader = new FileReader(filename);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        List<String> lines = new ArrayList<String>();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            lines.add(line);
	        }
	        bufferedReader.close();
	        return lines;
	    }

	private void setProxy(String proxyAddress) {
		String ip = "";
		int port = 8080;
		//username:password@23.23.231.12:27302
		if(proxyAddress.contains("@")){
			try{
				String[] rawSplit = proxyAddress.split("@");
				String[] credentials = rawSplit[0].split(":");
							 userName = credentials[0];
							 password = credentials[1];
				
				String[] rawProxy = rawSplit[1].split(":");						    
							ip = rawProxy[0];
							port = Integer.parseInt(rawProxy[1]);
					
			}catch(Exception asdfa){
				org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while trying to extract credentials from proxy ip " + asdfa);
			}
		}else{
			try{
				String[] rawProxy = proxyAddress.split(":");						    
				ip = rawProxy[0];
				port = Integer.parseInt(rawProxy[1]);
			}catch(Exception asdf){
				org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while trying to extract ip and port from proxy " + asdf);
			}
		}
		
		 ProxyConfig proxyConfig = BrowserServices.getInstance().getProxyConfig();

			 try {
				createPac();
				proxyConfig.setAutoConfigUrl(new URL("proxy.pac"));
				proxyConfig.setProxy(ServerType.HTTP, new ProxyServer(ip, port));
			} catch (Exception e) {
				org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while setting proxypac" + e);
			}

		// Register authentication handler for HTTP Server type if required
        proxyConfig.setAuthenticationHandler(ServerType.HTTP, new AuthenticationHandler() {

			@Override
			public ProxyServerLogin authenticationRequired(ServerType type) {
				   return new ProxyServerLogin(userName, password);
			}
		});
		
	}

	private void createPac() throws IOException {
		List<NodePro> nodelist = db.query(new OSQLSynchQuery<NodePro>("select from NodePro where nodeType = 'head' and selectedOptions.length() > 3 order by timestamp"));
		
		//write the header section of pac

		FileWriter fstream = new FileWriter("proxy.pac"); 
		BufferedWriter out = new BufferedWriter(fstream);

			out.write("function FindProxyForURL(url, host){ if (shExpMatch(host, '*.d11rob8x62nx92gayhzd1oga.foooo*')){ return 'PROXY myproxy.foo.com:9050;DIRECT;'; } else if (shExpMatch(host, '*.dr221iigggu7272sbzksyeuw72.krauser*')){ return 'PROXY anotherproxy.bar.com:1050;DIRECT;'; }");
		
			//the mid section cases.
		for(int i=0;i<nodelist.size();i++){
			NodePro header = nodelist.get(i);
			String proxyaddress = header.selectedOptions;
			
			String condition = "else if (shExpMatch(host, '*." + header.getText() + "*')) { return 'PROXY " + proxyaddress + ";DIRECT;'; }"; 
			out.write(""); //writes the content to the file
		
		}
		
		//write bottom section
		out.write("else{ return 'DIRECT'; }; }; ");
		out.close();
	}

	private String getElementText(NodePro node) {
        browser.waitReady();
        String axpath = node.getXpath();
        String pre = "var headings = document.evaluate('//" + axpath + "', document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); if(thisHeading != null){thisHeading.textContent;} ";
        //browser.executeScript(pre);
       //String js = "extractString(); ";        
        String text = browser.executeScript(pre);
        int i =0;
        
        while(text ==null){
        	text = browser.executeScript(pre);
        	if(i==5) return "no text found";
        	i++;
        }
        
        return text;
	}

	private void writeRow() {
		
		//int colCount = activeSheet.getColumnCount();
		

		final int rowCount = activeSheet.getRowCount();				
		//check for number of null
		int totalColSize = rowContainer.size();
		int counter = 0;
		for (String key : rowContainer.keySet()) {
			
			String data = rowContainer.get(key);
			
			if(data == null || data =="" || data.isEmpty())
				counter++;
		}	
				
		
		//if the entire row is full of empty column, don't bother writing anything.
		if(counter >= totalColSize){
			return;
		}else{
			activeSheet.insertRows(rowCount, 1);
		}
				
		//writes into row.
		for (String key : rowContainer.keySet()) {
					
					String data = rowContainer.get(key);

					activeSheet.setValueAt(data, rowCount, Integer.parseInt(key)-1);
					
		}

			
			return;
		}

	//1. look for all similar nodes via xpath then get xpath for each individual nodes with same ids. also handles 'save as row' node.
	private List<NodePro> findSimilarElements(NodePro node) {
		
		String similarXpath = (String) node.commands.get("repeat_all");

		List<NodePro> nodelist = new LinkedList<NodePro>();

	    	NodeList tags = getSimilarElementsByXpath(similarXpath,node.attrName);
	    	DOMElement thisnode = (DOMElement) tags.item(0);
	    	
	    	//if next page command exists save this to be added later.

	    	//add the first sibling					
			NodePro npForQueue = createNodePro(node);	 
						   npForQueue.setXpath(similarXpath.replaceAll("/preceding-sibling::[^/]*",""));
						   npForQueue.setNodeSiblingIndex(0);
						   npForQueue.commands.remove("repeat_all");
						   npForQueue.commands.remove("nextpage");
						   npForQueue.setText("Ze Similar Element");
			nodelist.add(npForQueue);
	    	
	    	for(int x=0;x<3;x++){	    			
	    			//temproary nodes for placement in queue, same id so children will be same for each similar nodes.
	    			NodePro npForQueue2 = createNodePro(node);
	    						   npForQueue2.setNodeSiblingIndex(x);	    		
	    						   npForQueue2.setXpath(similarXpath);
	    						   npForQueue2.commands.remove("repeat_all");
	    						   npForQueue.commands.remove("nextpage");
	    						   
	    						   if(x == tags.getLength() -1){
	    							   npForQueue2.setText("Ze Similar Element");
	    						   }else{
	    							   npForQueue2.setText("Ze Similar Elements Last");	    							   
	    						   }
	    			nodelist.add(npForQueue2);

	    	}
	    	//if there is a nextpage command associated with this node, let's add the command node.
	    	if(node.commands.containsKey("nextpage")){
	    		String nextXpath = (String) node.commands.get("nextpage");
	    		
	    		node.commands.put("gotoNextPage", node.commands.get("nextpage"));
	    		NodePro nextPageQueue = createNodePro(node);
	    					   nextPageQueue.setNodeSiblingIndex(0);
	    					   nextPageQueue.setXpath(nextXpath);
	    					   nextPageQueue.commands.remove("repeat_all");
	    					   nextPageQueue.setText("Ze Next Page");
	    					   nextPageQueue.commands.remove("nextpage");
	    					   nextPageQueue.commands.put("gotoNextPage", nextXpath);
	    		nodelist.add(nextPageQueue);
	    	}
	    	
		return nodelist;
	}

	private NodePro createNodePro(NodePro node) {
		NodePro npForQueue = new NodePro();
		npForQueue.setXpath(node.getXpath());
		npForQueue.setNodeSiblingIndex(0);
		npForQueue.setId(node.getId()); //same id as original to repeat children get.
		npForQueue.setPageurl(node.getPageurl());
		npForQueue.setElement(node.getElement());
		npForQueue.commands.putAll(node.commands);
		
		return npForQueue;
	}

	private NodeList getElementsByXpath(String xpath, String attrName) {

		browser.waitReady();
		if(attrName.isEmpty()){
			String js = "var headings = document.evaluate('//" + xpath + "', document, null, XPathResult.ANY_TYPE, null);var thisHeading = headings.iterateNext();if (thisHeading.getAttribute('name')){}else{thisHeading.setAttribute('name','" + xpath + "');};";
			browser.executeScript(js);
			NodeList nodes = ((DOMDocument) browser.getDocument()).getElementsByName(xpath);
			int wut = nodes.getLength();
			return nodes;
		}else{
			NodeList nodes = ((DOMDocument) browser.getDocument()).getElementsByName(attrName);
			int wut = nodes.getLength();
			return nodes;			
		}
	}
	
	private NodeList getSimilarElementsByXpath(String xpath, String attrName){
	
		browser.waitReady();
		if(attrName.isEmpty()){
			String js = "var nodes = document.evaluate('//" + xpath + "', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); if(nodes.snapshotLength > 0){ for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); if(node.getAttribute('name')){ }else{ node.setAttribute('name','" + xpath + "'); }; }; 'true'; }else{ 'false'; }; ";
			browser.executeScript(js);
			NodeList nodes = ((DOMDocument) browser.getDocument()).getElementsByName(xpath);
			int wut = nodes.getLength();
			return nodes;
		}else{
			NodeList nodes = ((DOMDocument) browser.getDocument()).getElementsByName(attrName);
			int wut = nodes.getLength();
			return nodes;
		}
	}

	private void addChildrenToQueue(NodePro thisnode) {
		List<NodePro> childrenFromDB = getChildren(thisnode);	
	
		if(childrenFromDB.isEmpty()) {
			return;
		}

				List<NodePro> childrenModified = new ArrayList<NodePro>(); 
				
				Boolean backnodeExists = false;
					
				for(int i =0;i<childrenFromDB.size();i++){
						NodePro child = childrenFromDB.get(i);
						
						childrenModified.add(child);
						
						//if there already is a BACK then set backNodeExists to true;
						if(child.getNodeType() != null && child.getNodeType().startsWith("back")){
							backnodeExists = true;
						}
						//child.setXpath("");
					}
					
					//add Back Node if it don't exist already
					if(!backnodeExists){
						
							NodePro backNode = new NodePro();
							backNode.setText("Go Back");
							backNode.commands.put("back","true");
							backNode.setPageurl(thisnode.getPageurl()); //save parent's page url
							childrenModified.add(backNode);
							
					}
					
		addToQueue(childrenModified);				
	}

	private boolean click(NodePro node) {
		org.slf4j.LoggerFactory.getLogger(this.getClass()).info("Attempting to click item on " + getCurrentURL());

	    	
	    	DOMElement thisnode = null;	
			
	    	if(node.getNodeSiblingIndex() > 0 && node.getNodeSiblingIndex() != 99113){
	    		NodeList nodes = getSimilarElementsByXpath(node.getXpath(),node.attrName);
	    		thisnode = (DOMElement) nodes.item(node.getNodeSiblingIndex());

	    	}else{
	    		NodeList nodes = getElementsByXpath(node.getXpath(),node.attrName);
	    		thisnode = (DOMElement) nodes.item(0);	    	
	    	}
	    	
	    	System.out.println("clicking");
 			if(thisnode.hasAttribute("disabled") != false){
 				thisnode.removeAttribute("disabled");
 			}else{
 				
 			}
 			System.out.println("clicking3");
  			if(thisnode.hasAttribute("checked")) return true;
  			
	    	try{
	    		System.out.println("clicking");
	    		thisnode.click();
	    		return true;
	    	}catch(Exception zxcva){
	      	  	org.slf4j.LoggerFactory.getLogger(this.getClass()).warn("Could not find element to click on " + getCurrentURL());
				return false;
	    	}				
	    
	}

	private void addToQueue(List<NodePro> nl) {
		for (int i = nl.size() - 1; i >=0; i--) {
			NodePro node = nl.get(i);
		    queue.addFirst(node);
		}		
	}

	private List<NodePro> getChildren(final NodePro np) {	
		long npid = np.getId();
		List<NodePro> nodelist = db.query(new OSQLSynchQuery<NodePro>("select from NodePro where parentID = " + npid + " order by timestamp"));

		return nodelist;
	}

	private void initvar() {
		browser =  BrowserFactory.create();   
		browser.addLoadListener(new LoadAdapter() {
			@Override
			public void onFinishLoadingFrame(FinishLoadingEvent event){
				if (event.isMainFrame()){
					//web page has loaded completely.
				}
			}
		});

		db = Global.db;
		contentSettings = browser.getConfigurable();
		

		/*
        ScriptErrorWatcher scriptErrorWatcher = services.getScriptErrorWatcher();
        scriptErrorWatcher.addScriptErrorListener(new ScriptErrorListener() {
			@Override
			public void scriptErrorHappened(ScriptErrorEvent event) {
	               System.out.println("javascripterror = " + event.getMessage());
				
			}
        });*/

		//JSpread sheet = workbook.getActiveSheet();
		//SelectionModel sel = sheet.getGrid().getSelectionModel();
		//sheet.getGrid().setValueAt("dick", 1, 1);
	}
	
	private void setTestMode(Boolean test){
		testMode = test;
	}
	
	private String saveFile(NodePro node) {
		org.slf4j.LoggerFactory.getLogger(this.getClass()).info("Saving file on " + getCurrentURL());

    	DOMElement thisnode = null;	
		
    	if(node.getNodeSiblingIndex() > 0 && node.getNodeSiblingIndex() != 99113){
    		NodeList nodes = getSimilarElementsByXpath(node.getXpath(),node.attrName);
    		thisnode = (DOMElement) nodes.item(node.getNodeSiblingIndex());

    	}else{
    		NodeList nodes = getElementsByXpath(node.getXpath(),node.attrName);
    		thisnode = (DOMElement) nodes.item(0);	    	
    	}

		HTMLElement fileElement = (HTMLElement) thisnode;

		final String fileurl = thisnode.getAttribute("href");
		
		System.out.println(fileurl);
		String filename = getFileName(fileurl);
		long timestamp = System.currentTimeMillis()/100;
		String extension = node.fileext;
		final String filepath = "downloads\\" + filename + "_" + timestamp + extension;

				    			
        return filepath;
	}
	
	private String getAbsoluteUrl(String rawfileurl) {
		// TODO Auto-generated method stub
		return null;
	}

	private String savePicture(NodePro node) {
		org.slf4j.LoggerFactory.getLogger(this.getClass()).info("Saving image on " + getCurrentURL());
		
    	int imageLocationX;
		int imageLocationY;
		int imageWidth;
		int imageHeight;
		
		String imagepath = "no image";
				
    	DOMElement thisnode = null;	
		
    	if(node.getNodeSiblingIndex() > 0 && node.getNodeSiblingIndex() != 99113){
    		NodeList nodes = getSimilarElementsByXpath(node.getXpath(),node.attrName);
    		thisnode = (DOMElement) nodes.item(node.getNodeSiblingIndex());

    	}else{
    		NodeList nodes = getElementsByXpath(node.getXpath(),node.attrName);
    		thisnode = (DOMElement) nodes.item(0);	    	
    	}
    	
		HTMLImageElement imageElement = (HTMLImageElement) thisnode;
		
		String imgsrc = thisnode.getAttribute("src");
		String filename = getFileName(imgsrc);
		long timestamp = System.currentTimeMillis()/100;
		imagepath = "images\\" + filename + "_" + timestamp;

		
		imageLocationX = thisnode.getAbsoluteLocation().x;
		imageLocationY = thisnode.getAbsoluteLocation().y;
		
		imageWidth = Integer.parseInt(imageElement.getWidth());
		imageHeight = Integer.parseInt(imageElement.getHeight());

		org.slf4j.LoggerFactory.getLogger(this.getClass()).info(imageLocationX + " at " + imageLocationY + " : " +
				"" + imageWidth + " by " + imageHeight + " on " + getCurrentURL());
		
		//save document as image
		Image rawimage = browser.toImage(true);
		BufferedImage  dest = null;
		try {
	    	org.slf4j.LoggerFactory.getLogger(this.getClass()).info("Saving image at " + imagepath);
			BufferedImage src = (BufferedImage) rawimage;
			dest = src.getSubimage(imageLocationX, imageLocationY, imageWidth, imageHeight);			
			ImageIO.write(dest, "PNG", new File(imagepath + ".png"));
		} catch (IOException e) {
	    	org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error occured while saving file ",e);
		}
		    			
	return imagepath;

	}


	private String getFileName(String url) {
		
		return url.replace("http://","").replace("/","__").replace(".","").replace("%","_").replace("$", "_").replace("&", "_").replace("?", "_").replace("#","_")
		.replace("~","_").replace("`", "_").replace("@","_");
	}

	@Override
	public void navigationFinished(NavigationFinishedEvent e) {
			
		
		String url = getCurrentURL();
    	org.slf4j.LoggerFactory.getLogger(this.getClass()).info("Navigation Finished on " + url);
		
		if(backButtonPressed){
		if(previousPage != null && previousPage != browser.getCurrentLocation()) {
			if(attemptCounter == 1){
				backNode.status = "error";
				org.slf4j.LoggerFactory.getLogger(this.getClass()).warn("Error encountered while attempting to navigate backwards on " + url);
			}
			browser.navigate(previousPage);
			attemptCounter++;
		}else if(previousPage == url){
			//back behavior was successful!
			backButtonPressed = false;
		}
		}
		
		
	}

	@Override
	public void navigationStarted(NavigationEvent arg0) {
		// TODO Auto-generated method stub
		//getCurrentURL();

	}

	private String getCurrentURL() {
		try{
			DOMDocument d = (DOMDocument) browser.getDocument();
			String url = d.getURL();
			return url;
		}catch(Exception e){
			org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while attempting to get current URL ",e);
		}
		return "no URL found";
	}

	@Override
	protected void run() throws Exception {
		// TODO Auto-generated method stub
		initvar();
		begin();
	}
	
	

}
