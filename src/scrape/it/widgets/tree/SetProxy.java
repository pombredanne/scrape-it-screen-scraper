package scrape.it.widgets.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;

public class SetProxy extends JMenuItem implements ActionListener {

	private String proxy = "";
	public SetProxy(){
		super("Set Proxy");
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		

			if(SelectedNode.nodeProperty.selectedOptions != null){
				proxy  = SelectedNode.nodeProperty.selectedOptions;
			}
			Object result = JOptionPane.showInputDialog(null, "Enter a Proxy IP address (current proxy: " + proxy + ")");
		    String keyword = (String) result;
		    SelectedNode.nodeProperty.selectedOptions = keyword;
		    Global.db.save(SelectedNode.nodeProperty);
	    
	}	
}
