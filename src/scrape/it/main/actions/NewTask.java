package scrape.it.main.actions;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.UUID;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;


import scrape.it.browser.MyBrowser;
import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.ActivatedNode;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.TreeModel1;
import scrape.it.widgets.tree.Tree;

public class NewTask extends JMenuItem implements ActionListener{


	public NewTask() {
		super("Add New Site");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Global.showWaitCursor();
		new CreateNewTaskAction();
		Global.showDefaultCursor();
	}

}
