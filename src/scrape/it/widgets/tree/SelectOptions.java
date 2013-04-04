package scrape.it.widgets.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class SelectOptions extends JMenuItem implements ActionListener {
	
	public SelectOptions(){
		super("Select Option(s)");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//display selection optionpane let user click one item. //if MULTIPLE, select multiple item.
		
	}

}
