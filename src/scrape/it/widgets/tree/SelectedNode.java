package scrape.it.widgets.tree;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;


import com.teamdev.jxbrowser.dom.DOMElement;

import scrape.it.persistence.NodePro;

/*SelecteNode Exposes Currently Selected Node Data*/
public class SelectedNode {

	public static DefaultMutableTreeNode treeNode;
	public static NodePro nodeProperty;
	public static long id;
	public static String url;
	public static String text;
	public static String xpath;
	public static String command;
	public static String type;
	public static String ancestors;
	public static int siblingIndex;
	public static int index;
	public static String path;
	public static TreePath treePath;
	public static long parentID;
	public static String element;
	public static DOMElement rawElement;
	public static byte[] jsonElement;

	
}
