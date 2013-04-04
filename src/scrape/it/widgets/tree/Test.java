package scrape.it.widgets.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.server.Scraper;

public class Test extends JMenuItem implements ActionListener {
	
	public Test(){
		super("Test");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();
		final NodePro np = (NodePro) node.getUserObject();
		
	SwingUtilities.invokeLater(new Runnable() {
		
		@Override
		public void run() {
			np.setCommand("start");
			TreeModel1.getInstance().nodeChanged(node);
			final Scraper scraper = new Scraper();

			//add instance for Global access later.
		
			
		}
	});

	}

}
