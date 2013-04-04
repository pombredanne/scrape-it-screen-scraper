package scrape.it.widgets.tree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import com.teamdev.jxbrowser.dom.DOMDocument;
import com.teamdev.jxbrowser.dom.DOMElement;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.utilities.XpathUtility;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.TreeModel1;

public class PasteItems extends JMenuItem implements ActionListener{

	private DefaultMutableTreeNode aParentNode;

	public PasteItems(){
		super("Paste");
		addActionListener(this);
		setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_V, ActionEvent.CTRL_MASK));
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		new PasteAction(SelectedNode.treeNode);
	}
}
