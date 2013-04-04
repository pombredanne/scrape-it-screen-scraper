package scrape.it.widgets.tree;

import it.scrape.browser.resources.ResourceLoader;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import scrape.it.main.Global;
import scrape.it.persistence.NodePro;

public class MyRenderer extends DefaultTreeCellRenderer{


    private ImageIcon startIcon;
	private ImageIcon stopIcon;
	private ImageIcon pauseIcon;
	private ImageIcon errorIcon;
	private ImageIcon okIcon;
	private ImageIcon workingIcon;
	private ImageIcon activeIcon;

	public MyRenderer() {
		
		startIcon = ResourceLoader.loadIcon("play.png");
		stopIcon = ResourceLoader.loadIcon("stop.png");
		pauseIcon = ResourceLoader.loadIcon("pause.png");
		errorIcon = ResourceLoader.loadIcon("error.gif");
		okIcon = ResourceLoader.loadIcon("ok.gif");
		workingIcon = ResourceLoader.loadIcon("working.png");
		activeIcon = ResourceLoader.loadIcon("activenode.png");
    }

    public Component getTreeCellRendererComponent(
                        JTree tree,
                        Object value,
                        boolean sel,
                        boolean expanded,
                        boolean leaf,
                        int row,
                        boolean hasFocus) {

        super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);
        
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        NodePro np = (NodePro)(node.getUserObject());
        
        setBackgroundSelectionColor(Color.LIGHT_GRAY);
        setTextSelectionColor(Color.BLACK);
        /*
        if(np.commands.size() >0){
        	//Global.validateMap(np.mapd);
        	String original = np.getText();
        	System.out.println("cAP " + original);
        	
        	StringBuffer sb = new StringBuffer();
        	sb.append("");
        	
        	// TODO Auto-generated method stub
    		Set entries = np.commands.entrySet();
    		Iterator it = entries.iterator();
    		
    		while(it.hasNext()){
    			 Map.Entry entry = (Map.Entry) it.next();
    		     String key = (String) entry.getKey();
    		     if(key.equals("OK")){
    		    	 
    		     }else if (key.equals("Navigate_One")){
    		    	 if(sb.length() > 0){
    		      		 sb.append(", click on " + original.toUpperCase() + "");
    		    	 }else{
    		    		 sb.append("Click on " + original.toUpperCase() + "");
    		    	 }
    		     }else if (key.equals("Navigate_All")){
    		    	 if(sb.length() > 0){
    		    		 sb.append(", click similar elements to " + original.toUpperCase() + "");
    		    	 }else{
    		    		 sb.append("Click similar elements to " + original.toUpperCase() + "");
    		    	 }
    		     }else if(key.startsWith("jxbsave_")){
    		    	 String attr = key.split("_")[1];
    		    	 
    		    	 if(sb.length() > 0){
    		    		 sb.append(", save " + original.toUpperCase() + " attribute ");
    		    	 }else{
    		    		 sb.append("Save " + original.toUpperCase() + " attribute ");
    		    	 }    		    	 
    		     }
    		}
    		
    		np.setText(sb.toString());
        }*/
        
        if(np.getCommand() != null){
        	if(np.getCommand().startsWith("start")){
        		setIcon(startIcon);
        	}
        	if(np.getCommand().startsWith("stop")){
        		setIcon(stopIcon);
        	}

        	if(np.getCommand().startsWith("pause")){
        		setIcon(pauseIcon);
        	}
        	       
        }
        
    	if(np.status != null){
        	if(np.status.startsWith("error")){
        		setIcon(errorIcon);
        	}
        	
        	if(np.status.startsWith("working")){
        		setIcon(workingIcon);
        		
        	}
        	
        	if(np.status.startsWith("done")){
        		if(node.isLeaf()){
        			setIcon(this.getDefaultLeafIcon());
        		}else{
        			setIcon(this.getDefaultOpenIcon());
        		}
        		TreeModel1.getInstance().nodeChanged(new DefaultMutableTreeNode(np));
        	}
        	
        	if(np.status.startsWith("ok")){
        		setIcon(okIcon);
        	}
        }
        
        if(np.getNodeancestors() != null){
        	if(np.getNodeancestors().contains("activated")){
        		setIcon(activeIcon);
        		
        		/*
        		setBackgroundNonSelectionColor(Color.YELLOW);
        		setTextNonSelectionColor(Color.BLACK);
        		setTextSelectionColor(Color.BLACK);
        		*/
        		
        		
           		//setBackgroundSelectionColor(Color.GREEN);
        		//setBackgroundSelectionColor(Color.YELLOW);
        		/*
        		for (Object key: UIManager.getDefaults().keySet())
        		{
        		    System.out.println(key + " : " + UIManager.getDefaults().get(key));
        		}
        		 */
        		
        	    		
        	}else{
        		setBackgroundNonSelectionColor(null);
        		setBackgroundSelectionColor(Color.LIGHT_GRAY);
        		//setBackgroundSelectionColor(null);
         		setTextNonSelectionColor(Color.BLACK);
        		setTextSelectionColor(Color.BLACK);
        	}
        }else{
        	setBackgroundNonSelectionColor(null);
        	setBackgroundSelectionColor(Color.LIGHT_GRAY);
        	//setBackgroundSelectionColor(null);
     		setTextNonSelectionColor(Color.BLACK);
    		setTextSelectionColor(Color.BLACK);
        }
        /*
        try{
        	if (np.getNodeType().equals("similar") || 
        			np.getNodeType().equals("next") || np.getNodeType().equals("previous")) {
        		
        		//setBackgroundNonSelectionColor(Color.BLUE);
        		
        	} 
        	
        	if (np.getNodeType().equals("similar")) setBackgroundNonSelectionColor(Color.DARK_GRAY);
        	
        }catch(Exception e){
        	//do nothing.
        }
        
        
*/
        return this;
    }

	public Dimension getPreferredSize() {
		Dimension dim = super.getPreferredSize();
		FontMetrics fm = getFontMetrics(getFont());
		char[] chars = getText().toCharArray();

		int w = getIconTextGap() + 16;
		for (char ch : chars)  {
			w += fm.charWidth(ch);
		}
		w += getText().length();
		dim.width = w;
	return dim;
	}

}
