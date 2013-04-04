package scrape.it.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class Pause extends JMenuItem implements ActionListener {
	
	public Pause(){
		super("Pause");
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();
		NodePro np = (NodePro) node.getUserObject();
		
		// TODO Auto-generated method stub
		np.setCommand("pause");
		Global.db.save(np);
		TreeModel1.getInstance().nodeChanged(node);
	}

}
