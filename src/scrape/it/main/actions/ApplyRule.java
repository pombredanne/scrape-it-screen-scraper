package scrape.it.main.actions;


import it.scrape.browser.resources.ResourceLoader;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import com.jidesoft.dialog.JideOptionPane;
import com.jidesoft.dialog.StandardDialog;
import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.CheckBoxListWithSelectable;
import com.jidesoft.swing.JideButton;
import com.jidesoft.swing.JideTabbedPane;
import com.teamdev.jxbrowser.dom.DOMDocument;
import com.teamdev.jxbrowser.dom.DOMElement;

import scrape.it.browser.ClickedHtmlElement;
import scrape.it.browser.MyBrowser;
import scrape.it.helper.CustomDialog;
import scrape.it.helper.OfficeOptionsDialog;
import scrape.it.helper.StandardDialogExample1;
import scrape.it.main.ClientGui;
import scrape.it.main.Global;

import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.ActionRecord;
import scrape.it.widgets.ActionRepeat;
import scrape.it.widgets.ExtractData;
import scrape.it.widgets.HtmlFormWindow;
import scrape.it.widgets.OptionPanel;
import scrape.it.widgets.tree.ActivatedNode;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;
import scrape.it.widgets.tree.TreePopupTriggerListener;
import scrape.it.widgets.tree.actions.ClickingAction;
import scrape.it.widgets.tree.actions.SaveColumnAction;
import scrape.it.widgets.tree.actions.SaveItemAsColumn;

public class ApplyRule {
	
	private static final String NAVIGATE = "navigate";
	private static final String CANCEL = "cancel";
	private static final String REPEAT = "repeat";
	private static final String SAVETEXT = "text";
	private static final String SAVEURL = "url";
	private static final String APPLY = "apply";
	private static final String SAVEFILE = "download";
	private static final String TYPETEXT = "type";
	private static final String ADD = "applyadd";
	private static final String ALL = "applyall";
	private static final String NEXTPAGE = "nextpage";
	public String ce;
	private String ancestors;
	private int nodeIndex;
	private DefaultMutableTreeNode parent;
	private String tag;
	private NodePro nodep;
	public JPanel op;
	public GridBagLayout gbl;
	public GridBagConstraints gbc;

	private JCheckBox navButton;
	private JCheckBox extractTextButton;
	private JCheckBox extractUrlButton;
	private JButton applyButton;
	private JButton cancelButton;
	public DefaultMutableTreeNode newlyNode;
	public String elementXpath;
	public String columnname;
	public String columnname1;
	public String columnname2;
	public String columnname3;
	private JCheckBox typeTextButton;
	private JCheckBox extractFileButton;
	private JButton applyAddButton;
	private JButton applyAddAllButton;
	private JCheckBox nextPageButton;
	private JideTabbedPane jt;
	private JPanel savePanel;
	private JPanel navPanel;
	public String allXpath = "";
	private Map previousCommands;
	public boolean editMode;
	private DefaultMutableTreeNode selectedNode;
	private String inputType;
	private String[] columnList = {""};
	public Long headerid;

	
	

	public ApplyRule(final DOMElement currentElement, String xpath) throws Exception{
		
	
		
		if(currentElement == null) {
			editMode = true;
			selectedNode = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();
			nodep = (NodePro) selectedNode.getUserObject();
			elementXpath = nodep.getXpath();
			tag = nodep.getElement().toUpperCase();
			ce = nodep.attrText;
		}else{
		
		elementXpath = xpath;
		
		//setup OptionPanel

		
		if(xpath == null || xpath.isEmpty() || xpath.length() == 1) return;

		parent = ActivatedNode.treeNode;
		nodeIndex = TreeModel1.getInstance().getChildCount(parent);
				
		ce = currentElement.getTextContent();
		tag = currentElement.getTagName().toUpperCase();
		 
		if(ce.isEmpty() || ce == "") ce = tag;
		
			nodep = new NodePro();
			nodep.setText(ce);
			nodep.setElement(tag);
			nodep.setXpath(xpath);
			nodep.setPageurl(Global.currentURL);
			nodep.setParentID(ActivatedNode.id);		
			nodep.setNodeIndex(nodeIndex);
			nodep.setNodeType("normal");
			nodep.setId(UUID.randomUUID().getMostSignificantBits());
			nodep.attrId = currentElement.getAttribute("id");
			
			if(currentElement.hasAttribute("name")){
				nodep.attrName = currentElement.getAttribute("name");
			}else{
				nodep.attrName = "";
			}
			
			nodep.attrHref = currentElement.getAttribute("href");
			nodep.attrText = ce;


		 
			String inputType = currentElement.getAttribute("TYPE");
			String name = currentElement.getAttribute("name");
			String src = currentElement.getAttribute("src");
		
			if(name == null || name.isEmpty()){
				name = "";
			}
			
			if(src == null || src.isEmpty()){
				src = "";
			}
			
			
			
			if(inputType.equals("checkbox") || inputType.equals("radio")){
	
				currentElement.setAttribute("style", "outline-color:red;");
				currentElement.setAttribute("style", "outline-width:2px;");
				currentElement.setAttribute("style", "outline-style:solid;");

			}else{
				currentElement.setAttribute("style", "border:2px solid red;");
				currentElement.setAttribute("clicked", "true");
			}
			
	}
		

	
			if(tag.equals("INPUT")){
				System.out.println("THIS TYPE " + inputType);
			
				if (inputType.equals("text") || inputType.equals("password") || inputType.equals("")){
					if(inputType.equals("password")){
						createMenu("Password Field");	
					}else{
						createMenu("Text Field");
					}
					navPanel.remove(nextPageButton);
					jt.setEnabledAt(jt.getTabCount()-1,false);
					showDialog(op);		
					return;
				}
			
				if (inputType.equals("checkbox")){			
				
					createMenu("Checkbox Button");			
					navPanel.remove(nextPageButton);
					navPanel.remove(typeTextButton);
					jt.setEnabledAt(jt.getTabCount()-1,false);
					showDialog(op);			
					return;
				}
			
				if (inputType.equals("radio")){
				
					createMenu("Radio Button");			
					navPanel.remove(nextPageButton);
					navPanel.remove(typeTextButton);
					jt.setEnabledAt(jt.getTabCount()-1,false);
					showDialog(op);			
					return;							
				}
				
				if (inputType.equals("submit") || inputType.equals("button")){
					
					createMenu("Submit Button");			
					navPanel.remove(nextPageButton);
					navPanel.remove(typeTextButton);
					jt.setEnabledAt(jt.getTabCount()-1,false);
					showDialog(op);		
					return;							
				}
			
		}
		
		if(tag.equals("SELECT")){
			createMenu("Select Field");			
			navPanel.remove(nextPageButton);
			navPanel.remove(typeTextButton);
			jt.setEnabledAt(jt.getTabCount()-1,false);
			showDialog(op);	
			return;
		}
		
		if(tag.equals("BUTTON")){
				
			createMenu("Button");			
			navPanel.remove(nextPageButton);
			navPanel.remove(typeTextButton);
			jt.setEnabledAt(jt.getTabCount()-1,false);
			showDialog(op);	
			return;
				
		}
		
		if(tag.equals("IMG")){
			createMenu("Image");			
			savePanel.remove(extractUrlButton);
			navPanel.remove(nextPageButton);
			navPanel.remove(typeTextButton);
			savePanel.remove(extractTextButton);
			showDialog(op);	
			return;
					
		}
			


		if(tag.equals("A")){
			createMenu("Link");			
			navPanel.remove(typeTextButton);
			showDialog(op);	
			return;
		}

		createMenu(tag);	
		savePanel.remove(extractUrlButton);
		navPanel.remove(nextPageButton);
		navPanel.remove(typeTextButton);
		savePanel.remove(extractFileButton);

		showDialog(op);				
		return;
		


	}
	
	private void showDialog(final JPanel op) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				JPanel mop = op;
			  	if(Global.applyNext){
			  		mop = Global.tempPanel;
			  		
			  		Global.applyNext = false;
			  	}

				int r = JOptionPane.showOptionDialog(null, 
				        mop, 
				        "Choose Action", 
				        JOptionPane.NO_OPTION,
				        JOptionPane.PLAIN_MESSAGE, 
				        null, 
				        new String[]{}, // this is the array
				        "default");
				

				if(r == -1) {
					if(Global.apply || Global.applyAdd) return;
					
					if(Global.applyAll && !Global.applyNext){						
		            	String js = "var nodes = document.evaluate('//" + allXpath + "', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.setAttribute('clicked',''); node.style.border='transparent'; node.style.outlineColor='transparent';node.style.outlineWidth='';node.style.outlineStyle='transparent';};";
		            	MyBrowser.getInstance().browser.executeScript(js);
		            	Global.applyAll = false;
					}
					
					if(!Global.applyNext){	
						String js = "var nodes = document.evaluate('//" + ClickedHtmlElement.xpath + "', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.setAttribute('clicked',''); node.style.border='transparent'; node.style.outlineColor='transparent';node.style.outlineWidth='';node.style.outlineStyle='transparent';};";
						MyBrowser.getInstance().browser.executeScript(js);
					}
				}
				
			}
			
		});
		
	}

	private void createMenu(String tag2) {
		gbl = new GridBagLayout();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		op = new JPanel();
		
		op.setLayout(gbl);
		//op.setPreferredSize(new Dimension(200, 150));

		gbl.layoutContainer(op);	

		applyButton = makeButton("actionapply", APPLY, "Apply selected actions to this element on current page.", "Apply");
		cancelButton = makeButton("actioncancel1", CANCEL, "Abort all changes", "Cancel");
		applyAddButton = makeButton("actionapplyadd",ADD,"Apply Selected Actions To Another Elementon On Current Page.","Apply & Add Another Element");
		applyAddAllButton = makeButton("actionapplyall",ALL,"Apply selected actions to all elements like this on current page.", "Apply To Elements Like This");
		
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		jt = createTabbedPane(tag2);
		gbc.gridx = 0;
		gbc.gridy = 0;	
		gbc.gridwidth = 2;
		gbl.setConstraints(jt, gbc);					
	
		gbc.gridy = 1;
		gbc.insets = new Insets(3,3,3,3);

		gbc.gridx = 0;
		gbc.gridy = 2;	
		gbc.gridwidth = 2;
		gbl.setConstraints(applyButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;	
		gbc.gridwidth = 2;
		gbl.setConstraints(cancelButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;	
		gbc.gridwidth = 2;
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbl.setConstraints(applyAddButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;	
		gbc.gridwidth = 2;
		//gbc.fill = GridBagConstraints.HORIZONTAL;
		gbl.setConstraints(applyAddAllButton, gbc);
		
		op.add(jt);

		op.add(applyButton);
		op.add(cancelButton);
		op.add(applyAddButton);
		op.add(applyAddAllButton);
		
		op.setVisible(true);
	}

	private JideTabbedPane createTabbedPane(String tagname) {
		// TODO Auto-generated method stub
		JideTabbedPane tabbed = new JideTabbedPane();
		tabbed.addTab("Navigation", createNavigationTab(tagname));
		tabbed.addTab("Save",createSaveTab(tagname));



		if(Global.applyAll || Global.applyAdd || editMode){
			
			previousCommands = SelectedNode.nodeProperty.commands;
			if(previousCommands.containsKey("click")) navButton.setSelected(true);
			if(previousCommands.containsKey("type")) typeTextButton.setSelected(true);
			if(previousCommands.containsKey("text")) {
				columnname = (String) previousCommands.get(new String("text_column"));
				extractTextButton.setSelected(true);
			}
			
			if(previousCommands.containsKey("extract_url")){
				columnname1 = (String) previousCommands.get(new String("href_column"));
				extractUrlButton.setSelected(true);
			}
			
			if(previousCommands.containsKey("img")) {
				columnname2 = (String) previousCommands.get(new String("img_column"));
				extractFileButton.setSelected(true);
			}
						
			if(previousCommands.containsKey("file")) {
				columnname3 = (String) previousCommands.get(new String("file_column"));
				extractFileButton.setSelected(true);
			}
	
			
			if(Global.applyNext == true){
				System.out.println("true");
				navPanel.add(nextPageButton);
				navPanel.repaint();
				navPanel.revalidate();
				
				if(previousCommands.containsKey("nextpage")) nextPageButton.setSelected(true);
		
			}

		}
		

		
		return tabbed;
	}

	private JPanel createSaveTab(String tagname) {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		savePanel = new JPanel();
		
		savePanel.setLayout(gbl);
		//op.setPreferredSize(new Dimension(200, 150));
		savePanel.setVisible(true);
		gbl.layoutContainer(savePanel);	
		
		extractTextButton = makeCheckbox("actionsave", SAVETEXT, "Save Text to a Column", "Save " + tagname + " Text");
		extractUrlButton = makeCheckbox("actionsave", SAVEURL, "Save Link URL to a Column", "Save " + tagname + " href attribute.");
		extractFileButton = makeCheckbox("actionsave", SAVEFILE, "Download File from " + tagname, "Download File");		
		
		gbc.gridx = 0;
		gbc.gridy = 0;	
		gbc.gridwidth = 2;
		gbc.ipadx = 2;
		gbl.setConstraints(extractTextButton, gbc);					
		
		gbc.gridy=1;
		gbc.ipadx = 2;
		gbl.setConstraints(extractUrlButton, gbc);		
		
		gbc.gridy=2;
		gbc.ipadx = 2;
		gbl.setConstraints(extractFileButton, gbc);		

		savePanel.add(extractTextButton);
		savePanel.add(extractUrlButton);
		savePanel.add(extractFileButton);
		
		return savePanel;
	}

	private JPanel createNavigationTab(String tagname) {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		navPanel = new JPanel();
		
		navPanel.setLayout(gbl);
		//op.setPreferredSize(new Dimension(200, 150));
		navPanel.setVisible(true);
		gbl.layoutContainer(navPanel);	
		
		navButton = makeCheckbox("actionnavigate", NAVIGATE, "Click on " + tagname, "Click on " + tagname);
		typeTextButton = makeCheckbox("actiontype", TYPETEXT, "Type Text(s) in Textfield", "Types Text(s) into Text Field");
		nextPageButton = makeCheckbox("actionnextpage", NEXTPAGE, "Navigate to Next Page", "Navigate all pages");
		gbc.gridx = 0;
		gbc.gridy = 0;	
		gbc.gridwidth = 2;
		gbc.ipadx = 2;
		gbl.setConstraints(navButton, gbc);			
		
		gbc.gridy=1;
		gbc.ipadx = 2;
		gbl.setConstraints(typeTextButton,gbc);
		
		gbc.gridy=2;
		gbc.ipadx = 2;
		gbl.setConstraints(nextPageButton,gbc);

		navPanel.add(navButton);
		navPanel.add(typeTextButton);
		
		
		return navPanel;
	}

	private class ButtListener implements ActionListener{

		private int num;
		private String rawString;
		private Map columns;
	



		public ButtListener(){
			
			TreeCrawler tc = new TreeCrawler();
			 DefaultMutableTreeNode cN = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();
			NodePro header = tc.getHeaderNode(cN);
			headerid = header.getId();
			System.out.println(header.columns.size());
			if(header.columns.size() < 1){
				
			}else{
				
				Object[] fa = header.columns.values().toArray();
				
				columnList = Arrays.copyOf(fa, fa.length, String[].class);
			}
		
			System.out.println(columnList);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
	        String cmd = e.getActionCommand();
	        String description = null;
	        

	        // Handle each button.
	        if (REPEAT.equals(cmd)) { // second button clicked
	        	new ActionRepeat(); 
	        } else if (SAVETEXT.equals(cmd)){
	        	if(extractTextButton.isSelected()){
	        		
	        		showColumnDialog();
	        		

	        	}else{
	        		
	        	}
	        	
	        }else if(TYPETEXT.equals(cmd)){
	        	new AddText();
	        }else if(NEXTPAGE.equals(cmd)){
	        	if(nextPageButton.isSelected()){
	        				JOptionPane.getRootFrame().dispose();
	        			  	Global.tempPanel = op;
	      	        		JOptionPane.showMessageDialog(null, "Click the element which takes you to the next page.");
	      	        		
	      	       	   	Global.applyNext = true;
	      	       	   	
	        	}else{
	         	   	Global.applyNext = false;
	        	}
	        	
	        }else if(SAVEFILE.equals(cmd)){
	        
	        } else if (SAVEURL.equals(cmd)){
	        	if(extractUrlButton.isSelected()){
	          		Object result = JOptionPane.showInputDialog(null, "Enter a column name to save text to:");
	          		String colnum = (String) result;
        	  
	          		if(colnum != null && !colnum.isEmpty()){
	          			columnname2 = colnum;
	          		}
	        	}else{
	        	
	        	}
	        } else if(CANCEL.equals(cmd)){
	        	JOptionPane.getRootFrame().dispose(); 
	        } else if(NAVIGATE.equals(cmd)){
	        	
	        } else if(ALL.equals(cmd)){
	        	Global.applyAll = true;
	        	if(allXpath.isEmpty()){
	        		allXpath = elementXpath;
	        		num = 0;
	        	}else{
	        		String clearjs = "var nodes = document.evaluate('" + allXpath + "', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); if(nodes.snapshotLength > 0){ for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); node.style.border='transparent'; }; }else{}; ";
	    			MyBrowser.getInstance().browser.executeScript(clearjs);
	        	}
	        	String js = "function returnSimilarElements(xpath){ var nodes = document.evaluate('//' + xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); if(nodes.snapshotLength > 1){ for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); if(node.getAttribute('name')){  }else{ node.setAttribute('name',xpath); node.style.border='2px red solid'; }; } return xpath; }else{ return 'false'; } }; function dothis(axpath,iindex){ var mySplitResult = axpath.split('/'); var lastOriginalString = ''; var resultindex = mySplitResult.length; if(iindex != 0) resultindex = iindex; for (var i = resultindex -1; i > 0; i--) { if(lastOriginalString !='') mySplitResult[i+1] = lastOriginalString; var originalString = mySplitResult[i]; lastOriginalString = originalString; var originalStringElement = originalString.split('['); originalStringElement = originalStringElement[0]; var newString = originalString +  '/following-sibling::' + originalStringElement; mySplitResult[i] = newString; var trythis = mySplitResult.join('/'); var returned = returnSimilarElements(trythis); if (returned != 'false'){ return returned + ',' + i; }; } return 'nothing'; }; dothis('" + allXpath + "'," + num + ");";
	        	// this is a second algo : String js = "function returnSimilarElements(xpath){ var nodes = document.evaluate('//' + xpath, document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); if(nodes.snapshotLength > 1){ for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); if(node.getAttribute('name')){  }else{ node.setAttribute('name',xpath); node.style.border='2px red solid'; }; } return xpath; }else{ return 'false'; } }; function dothis(axpath,iindex){ var mySplitResult = axpath.split('/'); var lastOriginalString = ''; var resultindex = mySplitResult.length; if(iindex != 0) resultindex = iindex; for (var i = resultindex -1; i > 0; i--) { if(lastOriginalString !='') mySplitResult[i+1] = lastOriginalString; var originalString = mySplitResult[i]; lastOriginalString = originalString; var originalStringElement = originalString.split('['); originalStringElement = originalStringElement[0]; var newString = originalString.replace(/\\[(.*?)\\]/,'[following-sibling::' + originalStringElement + ']'); mySplitResult[i] = newString; var trythis = mySplitResult.join('/'); var returned = returnSimilarElements(trythis); if (returned != 'false'){ return returned + ',' + i; }; } return 'nothing'; }; dothis('" + allXpath + "'," + num + ");";

	        	System.out.println(js);
	        	MyBrowser.getInstance().browser.executeScript(js);
	        		        
	        	
	        	rawString = MyBrowser.getInstance().browser.executeScript(js);
	        	
	        	String[] rawArray = rawString.split(",");
	        	allXpath = rawArray[0];
	        	num = Integer.valueOf(rawArray[1]);
	        	
	        	//save this xpath to find the parent xpath in the next time ALL button is clicked.

	        	

	        	//nexy
	    		navPanel.add(nextPageButton);
	    		navPanel.revalidate();
	        	navPanel.repaint();
	        } else if(APPLY.equals(cmd) || ADD.equals(cmd)){
	        	Global.apply = true;
	        	
	        	StringBuffer sb = new StringBuffer();

	        	nodep.commands.clear();
	        	
	        	

	        	if(extractTextButton.isSelected()){
	        		
	        		if (Global.applyAdd){
	        			//columnname = (String) previousCommands.get("extract_text");
	        			System.out.println(columnname);
	        		}
	        		nodep.commands.put("text", elementXpath);
	        		nodep.commands.put("text_column", columnname);
	        	    sb.append("SAVE TEXT TO " + columnname + ", ");	
	        	    saveColumnName(columnname);
	        	}
	        	
	        	if(typeTextButton.isSelected()){
	        		nodep.commands.put("type", elementXpath);
	        		  sb.append("TYPE TEXTS INTO TEXT FIELD, ");	
	        	}
	        	
	        	if(nextPageButton.isSelected()){
	        		nodep.commands.put("nextpage", elementXpath);
	        	    sb.append("CLICK NEXT PAGE LINK, ");	
	        	}
	        	

	        	if(extractFileButton.isSelected()){
	        		
	        		if (Global.applyAdd){
	        			System.out.println(previousCommands);
	        		}
	        		
		        	if(tag.equals("IMG")){
		        		nodep.commands.put("img", elementXpath);
		        		nodep.commands.put("img_column", columnname2);
		           	    sb.append("DOWNLOAD IMAGE " + columnname2 + ", ");	
		           	    saveColumnName(columnname2);
		        	}else{
		        		nodep.commands.put("file", elementXpath);
		        		nodep.commands.put("file_column", columnname3);
		           	    sb.append("DOWNLOAD FILE " + columnname3 + ", ");	
		           	    saveColumnName(columnname3);
		        	}

	        	}
	        	
	        	if(extractUrlButton.isSelected()){
	        		
	        		if (Global.applyAdd){
	        			columnname1 = (String) SelectedNode.nodeProperty.commands.get("extract_text");	        			
	        		}
	        		
	        		nodep.commands.put("href", elementXpath);
	        		nodep.commands.put("href_column", columnname1);
	           	    sb.append("SAVE URL TO " + columnname1 + ", ");	
	           	    saveColumnName(columnname1);
	        	} 
	        	
	        	if(nextPageButton.isSelected()){
	        		nodep.commands.put("nextpage", ClickedHtmlElement.xpath);
	           	    sb.append("REPEAT FOR ALL NEXT PAGES, ");	
	        	}
	        	
	        	if(Global.applyAll){
	          		nodep.commands.put("repeat_all", allXpath);
	          		nodep.commands.put("group", String.valueOf(headerid));
	        		 sb.append(" FOR ALL SIMILAR ELEMENTS.");
	        		 Global.applyAll = false;
	        		 
	        		 
	        		
	        	}

	        	
	        	if(cmd.equals(ADD)){
	        	   	JOptionPane.getRootFrame().dispose(); 
	        	   	
	        		JOptionPane.showMessageDialog(null, "Click on another element to repeat actions on it.");
	        	   	Global.applyAdd = true;

	        	}
	        	
	        	
	        	if(navButton.isSelected()){
	        		
	        		nodep.commands.put("click", elementXpath);
	           	    sb.append("CLICK " + tag + " LIKE " + ce + ".");		        		
	           	    
	           	    Global.allowNavigation = true;
	            	String js = "var headings = document.evaluate('//" + elementXpath + "', document, null, XPathResult.ANY_TYPE, null);var thisHeading = headings.iterateNext();thisHeading.style.border = 'red solid 4px';thisHeading.style.outlineColor='red';thisHeading.style.outlineWidth='4px';thisHeading.style.outlineStyle='solid';if (thisHeading.getAttribute('name')){}else{thisHeading.setAttribute('name','" + elementXpath + "');};";
	      	    	System.out.println(js); 
	      	    	MyBrowser.getInstance().browser.executeScript(js);
	      	    	NodeList nodes = MyBrowser.getInstance().getBrowserDocument().getElementsByName(elementXpath);
	      	    	DOMElement thisnode = (DOMElement) nodes.item(0);
	      	    	
	      	    	if(Global.applyAdd){
	      	    		Global.allowNavigation = false;
	      	    	}else{
	      	    		try{
	      	    			thisnode.click();
	      	    		}catch(Exception ze){
	      	    			
	      	    		}
	      	    	}
	        		
	        	} 
	        	

	        	
	        	Global.applyNext = false;
	        	
        	//persist nodepro  

	        	String caption = sb.toString(); 
	        	
	        	if(caption.isEmpty()) return;
	        	
	        	//caption = caption.substring(0, caption.length() - 1);
	        	nodep.setText(caption);
	        	
				DefaultMutableTreeNode newlyNode1 = new DefaultMutableTreeNode(nodep);				
				System.out.println(nodep);
				
				//save column

					
	
	        	
				if(Global.navStarted && Global.applyAdd != true && Global.applyAll != true && navButton.isSelected()){
					System.out.println("nav");					
					JOptionPane.getRootFrame().dispose(); 
					
					ActivatedNode.treeNode = newlyNode1;
					ActivatedNode.id = nodep.getId();
					SelectedNode.nodeProperty = nodep;
					SelectedNode.treeNode = newlyNode1;
					
					if(!editMode){
					TreeModel1.getInstance().insertNodeInto(newlyNode1, 
							parent, nodeIndex);							
					}else{
						TreeModel1.getInstance().nodeChanged(selectedNode);
					}
					
					Global.db.save(nodep);
		
					Global.navStarted = false;
					
					if(Global.lastInsertedNode != null){
						//Deactivate previous node
						TreeCrawler tc = new TreeCrawler();
						tc.clearActiveIcon();

						  DefaultMutableTreeNode current = (DefaultMutableTreeNode) newlyNode1;
						  NodePro npt = (NodePro) current.getUserObject();
						  npt.setNodeancestors("activated");
						  TreeModel1.getInstance().nodeChanged(current);
					}
					
					return;
				}
				
					
					
					System.out.println("no nav");
					JOptionPane.getRootFrame().dispose(); 
					
					SelectedNode.nodeProperty = nodep;
					SelectedNode.treeNode = newlyNode1;
				
					if(!editMode){
						TreeModel1.getInstance().insertNodeInto(newlyNode1, 
								parent, nodeIndex);
					}else{
						TreeModel1.getInstance().nodeChanged(selectedNode);
					}
					
					Global.db.save(nodep);
					Global.navStarted = false;
					return;

	        }
			
		}

		private void saveColumnName(String columnString) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();
			TreeCrawler tc = new TreeCrawler();
			NodePro headNode = tc.getHeaderNode(currentNode);		
			if(headNode != null){
				String index = String.valueOf(headNode.columns.size() + 1);			
				nodep.commands.put("column_index", headNode.columns.size() + 1);
				
				Map thisN = headNode.columns;
				
				if(!(thisN.containsValue(columnString))){
					thisN.put(index, columnString);
				}
				
				Global.db.save(headNode);
			}
			
		}

		private void showColumnDialog() {
   
    		JComboBox jcb = new JComboBox(columnList);
    		jcb.setEditable(true);
    		JOptionPane.showMessageDialog( null, jcb, "Enter a new column name to save data to or select previously entered column names:", JOptionPane.QUESTION_MESSAGE);

			String colnum = (String) jcb.getSelectedItem();
			
			if(colnum != null && !colnum.isEmpty()){
				columnname = colnum;
			}
			
		}


		
	}

    public JCheckBox makeCheckbox(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) {
    		
    	//Look for the image.
    		String imgLocation = imageName + ".png";

    	//Create and initialize the button.
    		JCheckBox button = new JCheckBox(toolTipText);
    				
    					 button.setActionCommand(actionCommand);
    					 button.setToolTipText(toolTipText);
    					 button.addActionListener(new ButtListener());
    					 button.setIcon(ResourceLoader.loadIcon("unchecked.png"));
    					 button.setText(altText);
    					 button.setSelectedIcon(ResourceLoader.loadIcon("checked.png"));
    					 button.setDisabledIcon(ResourceLoader.loadIcon("disabled.png"));
    					 button.setHorizontalAlignment(SwingConstants.LEFT);
    					 button.setFocusable(false);
  
    		
    	   Font newButtonFont=new Font(button.getFont().getName(),button.getFont().getStyle(),16);  
    					 
    	   				button.setFont(newButtonFont);  
    				

    					 
    	   return button;
}
    
    public JButton makeButton(String imageName,
            String actionCommand,
            String toolTipText,
            String altText) {
    		
    	//Look for the image.
    		String imgLocation = imageName + ".png";

    	//Create and initialize the button.
    		JButton button = new JButton();
    					 button.setActionCommand(actionCommand);
    					 button.setToolTipText(toolTipText);
    					 button.addActionListener(new ButtListener());
    					 button.setIcon(ResourceLoader.loadIcon(imgLocation));
    					 button.setText(altText);
    					 button.setHorizontalAlignment(SwingConstants.LEFT);
    					 button.setFocusable(false);
  
    		
    	   //Font newButtonFont=new Font(button.getFont().getName(),button.getFont().getStyle(),12);  
    					 
    	   				//button.setFont(newButtonFont);  
    				

    					 
    	   return button;
    }
}
