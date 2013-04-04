package scrape.it.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.ActivatedNode;
import scrape.it.widgets.tree.BuildTree;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class Start extends JMenuItem implements ActionListener{
	

	public Start(){
		super("Start Scraping");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		new StartAction();

	
	}

}
