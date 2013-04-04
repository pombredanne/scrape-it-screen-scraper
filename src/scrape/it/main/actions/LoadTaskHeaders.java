package scrape.it.main.actions;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.TreeModel1;

public class LoadTaskHeaders {

	public LoadTaskHeaders(){		
		
		List<NodePro> nodelist = Global.db.query(new OSQLSynchQuery<NodePro>("select * from NodePro where nodeType = 'head'"));
		
		if (nodelist.isEmpty()){	
			//String url = JOptionPane.showInputDialog(ClientGui.frame, "Enter URL To Scrape:");
			//new CreateNewTaskAction();
		}else{
			
			 for (int i=0; i< nodelist.size(); i++)
			  {			   
				 
				  NodePro headernode = nodelist.get(i);			  
			     TreeModel1.getInstance().insertNodeInto(new DefaultMutableTreeNode(headernode), (MutableTreeNode) TreeModel1.getInstance().getRoot(),TreeModel1.getInstance().getChildCount(TreeModel1.getInstance().getRoot()));
			     TreeModel1.getInstance().nodeChanged(new DefaultMutableTreeNode(headernode));
			  }
		}		

		TreeCrawler tc = new TreeCrawler();
		tc.clearActiveIcon();
	}
}
