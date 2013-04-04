package scrape.it.widgets.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import scrape.it.main.Global;
import scrape.it.widgets.tree.actions.CopyAction;
import scrape.it.widgets.tree.actions.DeleteAction;
import scrape.it.widgets.tree.actions.DeleteSelection;
import scrape.it.widgets.tree.actions.PasteAction;

public class CutItems extends JMenuItem implements ActionListener {
	
	public CutItems(){
		super("Cut");
		addActionListener(this);
		setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_X, ActionEvent.CTRL_MASK));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		new CopyAction();
		new DeleteAction();			
	}

}
