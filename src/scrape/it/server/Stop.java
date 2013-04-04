package scrape.it.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class Stop extends JMenuItem implements ActionListener {
	
	public Stop(){
		super("Stop");
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		new StopTaskAction();
		
	}

}
