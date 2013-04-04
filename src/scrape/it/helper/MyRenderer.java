package scrape.it.helper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.TreeModel1;

public class MyRenderer extends DefaultTreeCellRenderer{


    private ImageIcon startIcon;
	private ImageIcon stopIcon;
	private ImageIcon pauseIcon;
	private ImageIcon errorIcon;
	private ImageIcon okIcon;
	private ImageIcon workingIcon;

	public MyRenderer() {
		
		startIcon = new ImageIcon("media_assets/play.png");
		stopIcon = new ImageIcon("media_assets/stop.png");
		pauseIcon = new ImageIcon("media_assets/pause.png");
		errorIcon = new ImageIcon("media_assets/error.gif");
		okIcon = new ImageIcon("media_assets/ok.gif");
		workingIcon = new ImageIcon("media_assets/working.png");
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
        		setBackgroundNonSelectionColor(Color.YELLOW);
        		//setBackgroundSelectionColor(Color.GREEN);
        		//setBackgroundSelectionColor(Color.YELLOW);
        		setTextNonSelectionColor(Color.BLACK);
        		setTextSelectionColor(Color.BLACK);
        		
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
