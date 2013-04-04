package scrape.it.widgets.tree;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import scrape.it.main.Global;
import scrape.it.main.actions.NewTask;
import scrape.it.main.actions.LoadTaskHeaders;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.MyLoginForm;


public class TreeModel1 extends DefaultTreeModel implements TreeModelListener{	
	private static TreeModel1 INSTANCE;
	private static TreeNode rot;
	private NewTask newtask;
	private LoadTaskHeaders loadtaskheaders;

	public TreeModel1() {
		super(new DefaultMutableTreeNode(new NodePro()));
		addTreeModelListener(this);
		Global.firstInsertedNode = this.getRoot();
	}
	
	public synchronized static TreeModel1 getInstance(){
		if (INSTANCE == null){
			INSTANCE = new TreeModel1();			
		}
		return INSTANCE;
		
	}

	@Override
	public void treeNodesChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void treeNodesInserted(TreeModelEvent e) {
		// TODO Auto-generated method stub
		Tree.getInstance().expandPath(e.getTreePath());	
		Global.lastInsertedNode = e.getTreePath().getLastPathComponent();
		


	}

	@Override
	public void treeNodesRemoved(TreeModelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void treeStructureChanged(TreeModelEvent e) {
		// TODO Auto-generated method stub
		Tree.getInstance().repaint();
	}
	
	

}
