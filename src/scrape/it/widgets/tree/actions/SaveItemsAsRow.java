package scrape.it.widgets.tree.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.swing.JMenuItem;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.table.Table;
import scrape.it.widgets.table.TableModel;
import scrape.it.widgets.tree.ActivatedNode;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.Tree;
import scrape.it.widgets.tree.TreeModel1;

public class SaveItemsAsRow extends JMenuItem implements ActionListener{
	
	private DefaultMutableTreeNode parent;
	private int nodeIndex;
	private NodePro nodeObject;
	private DefaultMutableTreeNode node;
	private List<NodePro> modifiedCopiedNodes = new LinkedList<NodePro>();
	public long parentsIdentifier;

	public SaveItemsAsRow(){
		super("Save As Row");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new CopyAction();
		
		node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();		
		 TreePath[] nodes = Tree.getInstance().getSelectionPaths();		 
			
		NodePro np = (NodePro) node.getUserObject();
		
		parentsIdentifier = UUID.randomUUID().getMostSignificantBits();
		
		
		if(nodes.length == 1 && SelectedNode.nodeProperty.getNodeType().startsWith("column")){
			np.setCommand("write");
			np.setText("<html><font color='#FF4100'><b>LAST  </b></font><font color='#0000FF'>" +
					"" + np.getNodeType().toUpperCase() + " </font>" + np.getTreePath() + "</html>");
			np.setPageurl(Global.currentURL);
			Global.db.save(np);

			return;
		}
		
		String rootXpath = getCommonParents();
		//System.out.println(Global.copiedNodes.size());
		

		
		//get sibling xpaths
		FindSimilarXpath fsx = new FindSimilarXpath();
		String masterRootXpath = fsx.oldgenerateXpath(rootXpath);
		
		//getElement from xpath
	
		
		org.slf4j.LoggerFactory.getLogger(this.getClass()).info("Master RootXpath: " + masterRootXpath);
		
		NodePro npa = new NodePro();
		npa.setId(parentsIdentifier);
		npa.setParentID(ActivatedNode.id);
		npa.setText("<html><font color='#0000FF'><b>SAVE AS ROW </b></font></html>");
		TreeModel1.getInstance().nodeChanged(node);
		npa.setNodeType("row");
		npa.setCommand("row");
		npa.setPageurl(Global.currentURL);
		npa.setXpath(masterRootXpath);
		npa.setElement(getElementFromXpath(rootXpath));
		
		DefaultMutableTreeNode savenode = new DefaultMutableTreeNode(npa);
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
		//insertColumnRows(node, np);
		 TreeModel1.getInstance().insertNodeInto(savenode,parentNode,TreeModel1.getInstance().getChildCount(ActivatedNode.treeNode));
		 insertColumnRows(savenode, npa);
		 
		Global.copiedNodes.clear();	
		modifiedCopiedNodes.clear();
		Global.db.save(npa);
		new DeleteAction();
		return;
    }

	private String getElementFromXpath(String rootXpath) {
	
		String[] elements = rootXpath.split("/");
		String rawelement = elements[elements.length-1];
	
		if(rawelement.contains("[") != false){
			String[] rawelements = rawelement.split("\\[");
			return rawelements[0];
		}else{
			return rawelement;
		}
		
	}

	private void insertColumnRows(DefaultMutableTreeNode parentNode, NodePro parentNodePro) {
		
		 for (int i=0; i< modifiedCopiedNodes.size(); i++)
		  {		
			 NodePro np = modifiedCopiedNodes.get(i);
			 				//TreeModel1.getInstance().insertNodeInto(savenode,ActivatedNode.treeNode,TreeModel1.getInstance().getChildCount(ActivatedNode.treeNode));
			 
			 				parentNode.add(new DefaultMutableTreeNode(np));
			 
		  }
	}

	private String getCommonParents() {
		//Get Selected Nodes.
		
		List<NodePro> copiednodelist = Global.copiedNodes;
		List<String> xpaths = new LinkedList<String>();
		
		 for (int i=0; i< copiednodelist.size(); i++)
		  {		
			 NodePro np = copiednodelist.get(i);
			 String xpath = np.getXpath();
			 xpaths.add(xpath);
		  }
		
		 //sort array from 
		Collections.sort(xpaths, new Arrange());
		String parentXpath = greatestCommonPrefix(xpaths.get(0), xpaths.get(xpaths.size()-1));
		

		 for (int i=0; i< copiednodelist.size(); i++)
		  {		
			 

			 NodePro np = copiednodelist.get(i);
			 String xpath = np.getXpath();
			 String subxpath = "/" + xpath.replace(parentXpath, "");
			 String columndata = np.getNodeType();
			 			
			 NodePro npz = new NodePro();
			 				npz.setId(UUID.randomUUID().getMostSignificantBits());
			 				npz.setXpath(subxpath.replace("//", ""));
			 				npz.setNodeType(columndata);
			 				npz.setPageurl(Global.currentURL);
			 				npz.setParentID(parentsIdentifier);
			 				npz.setText("<html>" + np.getText() + "</html>");
			 				npz.setElement(getElementFromXpath(subxpath));
			 				npz.timestamp = npz.timestamp + i;
			 if(i == copiednodelist.size()-1){
				 npz.setCommand("write");
				 npz.setText("<html><font color='#FF4100'><b>LAST </b></font>" + np.getText() + "</html>");
			 }
			 
			 modifiedCopiedNodes.add(npz);
			 Global.db.save(npz);
		  }
			

		return parentXpath;
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

	}



class Arrange implements Comparator {

	@Override
	public int compare(Object o11, Object o22) {
		String o1 = o11.toString();
		String o2 = o22.toString();
		
        if (o1.length() > o2.length()) {
            return 1;
        } else if (o1.length() < o2.length()) {
            return -1;
        } else {
            return 0;
        }
        
	}
}

