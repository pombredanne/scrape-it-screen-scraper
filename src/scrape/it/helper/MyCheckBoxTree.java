package scrape.it.helper;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import com.jidesoft.swing.CheckBoxTree;
import com.teamdev.jxbrowser.dom.DOMElement;

public class MyCheckBoxTree extends CheckBoxTree {
	private DOMElement thisElement;
	private DefaultMutableTreeNode first;
	private DefaultMutableTreeNode click;
	private DefaultMutableTreeNode saved;

	public MyCheckBoxTree(DOMElement element){
		this.thisElement = element;
		prepare();
		setCellRenderer();
	}

	private void setCellRenderer() {
		//TreeCellRenderer mycellrender = new MyTreeCellRenderer();
		// TODO Auto-generated method stub
		//setCellRenderer(new MyRenderer());
		setModel(new TreeModel2(thisElement));
		setRootVisible(false);
		System.out.println(thisElement.getTagName());
				
	
			
	}
	
	private TreePath[] getPaths(){
		return getCheckBoxTreeSelectionModel().getSelectionPaths();
	}

	private void prepare() {
		getCheckBoxTreeSelectionModel().setDigIn(true);
		// TODO Auto-generated method stub
		getCheckBoxTreeSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
			
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
}