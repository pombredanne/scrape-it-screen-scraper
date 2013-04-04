package scrape.it.widgets.tree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import scrape.it.browser.MyBrowser;
import scrape.it.browser.ClickedHtmlElement;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.utilities.XpathUtility;
import scrape.it.widgets.table.Table;
import scrape.it.widgets.table.TableModel;
import scrape.it.widgets.tree.ActivatedNode;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;

import com.teamdev.jxbrowser.dom.DOMDocument;
import com.teamdev.jxbrowser.dom.DOMElement;

public class ClickItem extends JMenuItem implements ActionListener{

	public ArrayList<String> selectedNodes = new ArrayList();
	private XpathUtility xg;
	private DOMElement focusedElement;
	
	public ClickItem(){
		super("Click Element");
		addActionListener(this);
	}
		

	@Override
	public void actionPerformed(ActionEvent e) {
		new ClickingAction();
		
		//if(SelectedNode.nodeProperty.getNodeType().startsWith("column") || SelectedNode.nodeProperty.getNodeType().startsWith("row")){
			
		//}else{
			new ClickingAction();
	//	}
		
	}

}
