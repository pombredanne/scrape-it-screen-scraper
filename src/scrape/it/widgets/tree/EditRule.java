package scrape.it.widgets.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.actions.ApplyRule;
import scrape.it.persistence.NodePro;

public class EditRule extends JMenuItem implements ActionListener {
	
	private DefaultMutableTreeNode node;

	public EditRule(){
		super("Edit Rule");
		addActionListener(this);
		setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	
		try {
			new ApplyRule(null, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
