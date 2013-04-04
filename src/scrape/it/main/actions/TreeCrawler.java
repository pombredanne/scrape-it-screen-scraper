package scrape.it.main.actions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class TreeCrawler {
	
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode theNode;

	public TreeCrawler(){
		theNode = null;
		root = (DefaultMutableTreeNode) TreeModel1.getInstance().getRoot();
		

	}
	
	public NodePro getHeaderNode(DefaultMutableTreeNode selected){
		NodePro selectedNp = (NodePro) selected.getUserObject();
		
		if(selectedNp.getNodeType() != null && selectedNp.getNodeType().contains("head")) return selectedNp;
		
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selected.getParent();
		
		while (parentNode != null){
			
			NodePro np = (NodePro) parentNode.getUserObject();
		
			if(np.getNodeType() != null && np.getNodeType().contains("head")){
				parentNode = null;
				return np;
			}
			
			parentNode = (DefaultMutableTreeNode) parentNode.getParent();
		}
		
		return null;
		
	}
	
	public void clearActiveIcon(){
		for (Enumeration e = root.preorderEnumeration(); e.hasMoreElements() && theNode == null;) {
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
		    		    
		    if ((node.getUserObject()) != null) {
		    	NodePro np = (NodePro) node.getUserObject();
		    	System.out.println(np.getText());
		    	if(np.getNodeancestors() != null){
		    		np.setNodeancestors(null);
		    		 TreeModel1.getInstance().nodeChanged(node);
		    	}
		    }
		}
	}
	
	public void search(){
		for (Enumeration e = root.preorderEnumeration(); e.hasMoreElements() && theNode == null;) {
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
		    		    
		    if ((node.getUserObject()) != null) {
		    	NodePro np = (NodePro) node.getUserObject();
		    	System.out.println(np.getText());
		    	String text = np.getText();
		    	
	    		if(text != null && text.contains("general")){
		    			//theNode = node;	
	    		}


		    }
		}
	}

	public void findTreeNodeByTimeStamp(long timestamp) {
		// TODO Auto-generated method stub
		
	}

	public void getAllRepeatsOnCurrentPage() {
		
		List<NodePro> columnNodes = new ArrayList<NodePro>();
		Multimap<String, NodePro> multimap = HashMultimap.create();
		
		for (Enumeration e = root.preorderEnumeration(); e.hasMoreElements() && theNode == null;) {
		    DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
		    		    
		    if ((node.getUserObject()) != null) {
		    	NodePro np = (NodePro) node.getUserObject();		    
	    		
	    		if(np.commands.containsKey("repeat_all")){
	    			multimap.put(np.getPageurl(), np);
	    		}

		    }
		}
		
		 Set keySet = multimap.keySet();
		    Iterator keyIterator = keySet.iterator();
		    while (keyIterator.hasNext() ) {
		        String key = (String) keyIterator.next();
		        List<NodePro> values = (List) multimap.get( key );
		        
		        if(values.size() > 1){
		        	modifyMDRnodes(values);
		        }

		    }
		
	}

	private void modifyMDRnodes(List values) {
		Collections.sort(values, new Arrange()); //rearrange nodepro with xpaths in ASC order.
		NodePro pre = (NodePro) values .get(0);
		NodePro post = (NodePro) values.get(values.size()-1);
		String parentXpath = greatestCommonPrefix(pre.getText(),post.getText());

		Iterator itr = values.iterator();
		while(itr.hasNext()){
			NodePro np = (NodePro) itr.next();
			String oldXpath = np.getText();
			String childXpath = oldXpath.replace(parentXpath, "");
			String newXpath = oldXpath + "|//" + parentXpath + "[not(" + childXpath.substring(1) + ")]";
			
				np.setXpath(newXpath);
				np.commands.put("repeat_all", newXpath);
			
			Global.db.save(np);
		}
		
		
	}
	
	public String greatestCommonPrefix(String first, String second) {
		String[] array1 = first.split("/");
		String[] array2 = second.split("/");
		StringBuffer sb = new StringBuffer();
		
		for(int i =0;i<array1.length;i++){
			String thisBlock = array1[i];
			String otherBlock = array2[i];
			
			if(thisBlock.equals(otherBlock)){
				sb.append(thisBlock + "/");
			}
					
		}
		
		int totalsize = sb.length();
		sb.deleteCharAt(totalsize-1);
		
		return sb.toString();
			
	}

	class Arrange implements Comparator {

		@Override
		public int compare(Object o11, Object o22) {
			NodePro first = (NodePro) o11;
			NodePro second = (NodePro) o22;
			
			String o1 = first.getText();
			String o2 = second.toString();
			
	        if (o1.length() > o2.length()) {
	            return 1;
	        } else if (o1.length() < o2.length()) {
	            return -1;
	        } else {
	            return 0;
	        }
	        
		}
	}

}
