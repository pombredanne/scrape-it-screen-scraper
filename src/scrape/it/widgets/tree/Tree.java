package scrape.it.widgets.tree;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.JTree;
import javax.swing.event.MouseInputListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;


public class Tree extends JTree{
	private static Tree INSTANCE;
	private TreeSelectionListenerClass tsl;
	private TreePopupTriggerListener ptl;
	private TreeDoubleClickListener dbll;
	private KeyListener keyl;

	public Tree() {
		// TODO Auto-generated constructor stub
		super(TreeModel1.getInstance());
			ptl = new TreePopupTriggerListener();
			tsl = new TreeSelectionListenerClass();
			dbll = new TreeDoubleClickListener();
			keyl = new aKeyListener();
		addMouseListener(ptl);
		addTreeSelectionListener(tsl);
		addMouseListener(dbll);
		setToggleClickCount(0);
		setCellRenderer(new MyRenderer());
		addKeyListener(keyl);
		
	}
	
	public synchronized static Tree getInstance(){
		if (INSTANCE == null){
			INSTANCE = new Tree();			
		}
		return INSTANCE;
		
	}	

}
