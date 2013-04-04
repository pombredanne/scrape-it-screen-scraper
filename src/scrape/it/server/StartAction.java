package scrape.it.server;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.BuildTree;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class StartAction {
	
	public StartAction(){
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();
		final NodePro np = (NodePro) node.getUserObject();
		Global.showWaitCursor();
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				if (np != null &&
						TreeModel1.getInstance().isLeaf(node)){
					try {
						BuildTree bt = new BuildTree(node);

					} catch (Exception e1) {
						org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error occured while BuildTree() ",e1);
					}
				}
				
			}
			
		});
		
		Global.showDefaultCursor();
		
	SwingUtilities.invokeLater(new Runnable() {
	
		private int maxJobs;

		@Override
		public void run() {

			if(Global.features.get("jobs") != null){
				if(Global.features.get("jobs").equals("unlimited")){
					maxJobs = 10000000;
					
				}else{
					maxJobs = Integer.parseInt(Global.features.get("jobs"));
				}
			}
			
			if(Global.features.get("jobs") == null){
				maxJobs = 1;
			}
			
			np.setCommand("start");	
			TreeModel1.getInstance().nodeChanged(node);
						
		
			Scraper scraper = new Scraper();	
				
	
			Global.scraperList.put(np.getId(), scraper);
			System.out.println(Global.scraperList.size() + " SIZE " +  maxJobs);
			if(Global.scraperList.size() > maxJobs){
				Global.scraperList.remove(np.getId());
				if(Global.features.get("jobs") != null){
					JOptionPane.showMessageDialog(ClientGui.frame, "Maximum number of concurrent jobs in this version is " + Global.features.get("jobs") + "." +
							" Upgrade to increase concurrent jobs or stop jobs running already to start this one.");
		        	   org.slf4j.LoggerFactory.getLogger(this.getClass()).info("maximum jobs reached. upgrade");
		        	   return;
				}else{
					JOptionPane.showMessageDialog(ClientGui.frame, "Maximum number of concurrent jobs in this version is 1. Please upgrade to increase this limit.");
		        	   org.slf4j.LoggerFactory.getLogger(this.getClass()).info("maximum jobs reached.");
				}
				return;
			}

			
			scraper.start();
			
		}
	});
	}

}
