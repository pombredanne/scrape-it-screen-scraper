package scrape.it.server;

import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class StopTaskAction {

	public StopTaskAction(){
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();
		final NodePro np = (NodePro) node.getUserObject();
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				np.setCommand("stop");
				TreeModel1.getInstance().nodeChanged(node);
				Scraper thisScraper = Global.scraperList.get(np.getId());
				thisScraper.end();	
				Global.scraperList.remove(np.getId());
			}
		});
		//get from list of instances

		
		// TODO Auto-generated method stub

	}
}
