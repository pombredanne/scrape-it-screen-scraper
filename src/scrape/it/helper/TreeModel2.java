package scrape.it.helper;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import com.teamdev.jxbrowser.dom.DOMElement;

import scrape.it.main.Global;
import scrape.it.main.actions.NewTask;
import scrape.it.main.actions.LoadTaskHeaders;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.MyLoginForm;


public class TreeModel2 extends DefaultTreeModel implements TreeModelListener{	
		private static TreeNode rot;
	private NewTask newtask;
	private LoadTaskHeaders loadtaskheaders;
	public static DefaultMutableTreeNode first;
	public static DefaultMutableTreeNode click;
	public static DefaultMutableTreeNode saved;

	public TreeModel2(DOMElement thisElement) {
		super(new DefaultMutableTreeNode("Rules"));
		addTreeModelListener(this);
		first = (DefaultMutableTreeNode) this.getRoot();

		
		if(thisElement.getTagName().equals("A")){
			click = new DefaultMutableTreeNode("Action");
			click.add(new DefaultMutableTreeNode("Click This Element Only"));
			click.add(new DefaultMutableTreeNode("Click All Similar Elements Like This"));
			saved = new DefaultMutableTreeNode("Save Data");
			saved.add(new DefaultMutableTreeNode("Text"));

			first.add(click);
			first.add(saved);
			
		}else{
			click = new DefaultMutableTreeNode("Action");
			click.add(new DefaultMutableTreeNode("Click This Element Only"));
			click.add(new DefaultMutableTreeNode("Click All Similar Elements Like This"));
			saved = new DefaultMutableTreeNode("Save Data");
			saved.add(new DefaultMutableTreeNode("Text"));

			first.add(click);
			//first.add(saved);

			
		}
	}
	

	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		// TODO Auto-generated method stub
		//Tree.getInstance().expandPath(e.getTreePath());	
	//	Global.lastInsertedNode = e.getTreePath().getLastPathComponent();
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
	//	Tree.getInstance().repaint();
	}
	
	

}
