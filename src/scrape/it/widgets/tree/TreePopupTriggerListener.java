package scrape.it.widgets.tree;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import scrape.it.main.actions.NewTask;
import scrape.it.persistence.NodePro;
import scrape.it.server.Pause;
import scrape.it.server.Start;
import scrape.it.server.Stop;
import scrape.it.widgets.ToolBar;
import scrape.it.widgets.tree.actions.ClickItem;
import scrape.it.widgets.tree.actions.CopyItems;
import scrape.it.widgets.tree.actions.DeleteSelection;
import scrape.it.widgets.tree.actions.MarkItemAsNext;
import scrape.it.widgets.tree.actions.MarkItemAsPrevious;
import scrape.it.widgets.tree.actions.MarkSimilarItems;
import scrape.it.widgets.tree.actions.PasteItems;
import scrape.it.widgets.tree.actions.SaveItemAsColumn;
import scrape.it.widgets.tree.actions.SaveItemsAsRow;

   public class TreePopupTriggerListener extends MouseAdapter {
     public JPopupMenu menu;
	static public JMenu submenu;
	private JPopupMenu headermenu;
	private JPopupMenu defaultmenu;
	private JPopupMenu textmenu;
	private JPopupMenu selectmenu;
	private Start start;
	private Stop stop;
	private SetProxy setproxy;
	private Pause pause;
	private Resume resume;
	private SaveItemAsColumn saveitemascol;
	private SaveItemsAsRow saveitemasrow;
	private MarkItemAsNext markitemasnext;
	private MarkSimilarItems getsimilaritems;
	private MarkItemAsPrevious markitemasback;
	private Test test;
	private DownloadFile downloadFile;
     
     public TreePopupTriggerListener(){          
    	 JPopupMenu.setDefaultLightWeightPopupEnabled(false);
    	 menu = new JPopupMenu("Popup"); 
    	 submenu = new JMenu("Rules");
    	 headermenu = new JPopupMenu("Header");
    	 defaultmenu = new JPopupMenu("Default");
    	 textmenu = new JPopupMenu("Text");
    	 selectmenu = new JPopupMenu("Select");
    	 
    	 
    	 
    	 //regular menu    	
    	 menu.add(new EditRule());
    	 menu.addSeparator();
    	 menu.add(new CopyItems());
    	 menu.add(new PasteItems());
    	 menu.add(new CutItems());
    	 menu.add(new DeleteSelection());

    	 
    	 //downloadFile = new DownloadFile();
    	// downloadFile.setEnabled(false);
    	 saveitemascol = new SaveItemAsColumn();
    	 saveitemasrow = new SaveItemsAsRow();
    	 getsimilaritems = new MarkSimilarItems();
    	 markitemasnext = new MarkItemAsNext();
    	 markitemasback = new MarkItemAsPrevious();
    	 
    	 
    	 
    	 //submenu.add(downloadFile);
    	// submenu.addSeparator();
    	 submenu.add(saveitemascol);
    	 submenu.add(saveitemasrow);
    	 submenu.addSeparator();
    	 submenu.add(markitemasnext);
    	 submenu.add(markitemasback);
    	 submenu.addSeparator();
    	 submenu.add(getsimilaritems);
    	 
    	 
    	 //header menu
    	 /*
     	clickAction.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_ENTER, ActionEvent.CTRL_MASK));
    	
    	copyAction.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
    	
    	pasteAction.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_V, ActionEvent.CTRL_MASK));
    	
    	deleteAction.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_DELETE, 0));
    	
    	pasteAction.setAccelerator(KeyStroke.getKeyStroke(
    	        KeyEvent.VK_X, ActionEvent.CTRL_MASK));
    	        */
    	 
    	 start = new Start();
    	 stop = new Stop();
    	 pause = new Pause();
    	 setproxy = new SetProxy();
    	 //test = new Test();
    	
    	 //headermenu.add(new Test());
    	 headermenu.add(start);

    	 headermenu.add(stop);
    	 //headermenu.add(pause);
    	 headermenu.add(setproxy);
    	 headermenu.add(new DeleteSelection());
    	 
    	 //default menu
    	 defaultmenu.add(new NewTask());
    	 defaultmenu.add(new DeleteSelection());
    	 defaultmenu.add(new MasterProxy());
    	 defaultmenu.add(new ProductActivation());
    	 
    	 //text menu
    	 textmenu.addSeparator();
    	 textmenu.add(new CopyItems());
    	 textmenu.add(new PasteItems());
    	 textmenu.add(new CutItems());
    	 textmenu.add(new DeleteSelection());

    	 
    	 //select menu
    	 selectmenu.add(new QueueOptions());
    	 selectmenu.addSeparator();
    	 selectmenu.add(new CopyItems());
    	 selectmenu.add(new PasteItems());
    	 selectmenu.add(new CutItems());
    	 selectmenu.add(new DeleteSelection());    	 

     }
     
      public void mousePressed(final MouseEvent ev) {  
    	  
    	  SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 handlePopup(ev);
			}
		});
    	 
      }

      public void mouseReleased(final MouseEvent ev) {
    	  SwingUtilities.invokeLater(new Runnable() {
  			
  			@Override
  			public void run() {
  				// TODO Auto-generated method stub
  				 handlePopup(ev);
  			}
  		});
      }

      private void handlePopup(MouseEvent ev) {
          if (ev.isPopupTrigger()) {
          	  
        	  TreePath path = Tree.getInstance().getPathForLocation(ev.getX(), ev.getY());
        	  
          	  if (path != null){
        		
          		  DefaultMutableTreeNode selected = (DefaultMutableTreeNode) path.getLastPathComponent();
        		  NodePro np = (NodePro) selected.getUserObject();
        		  
        		  if(np.getElement() != null){
        			  if(np.getElement().equalsIgnoreCase("INPUT") && np.getText().startsWith("TYPE TEXT")){
        				  textmenu.show(ev.getComponent(), ev.getX(), ev.getY());
        				  return;
        			  }
        			  else if(np.getElement().equalsIgnoreCase("SELECT") && np.getText().startsWith("SELECT MENU")){
        				  selectmenu.show(ev.getComponent(), ev.getX(), ev.getY());
        				  return;
        			  }else{
        				  
        			  }
        		  }
        		  
        		  if(np.getNodeType() != null && np.getNodeType().startsWith("head")){
        			 if(np.getCommand() != null){ 
        			  if(np.getCommand().contains("start")){  
        				  stop.setEnabled(true);
        				  start.setEnabled(false);
        				  setproxy.setEnabled(false);
        			  }else if(np.getCommand().contains("stop")){
        				  stop.setEnabled(false);
        				  start.setEnabled(true);
        				  setproxy.setEnabled(true);
        			  }else  if(np.getCommand().contains("pause")){
        				  //pause.setEnabled(false);
        				  //start.setEnabled(false);
        			  }
        			 }else{
        				 stop.setEnabled(false);
        			 }
    			  
        			  headermenu.show(ev.getComponent(), ev.getX(), ev.getY());
        		  

        		  }else if(np.getNodeType() != null && np.getNodeType().startsWith("row")){
        			  submenu.setEnabled(false);
        			  menu.show(ev.getComponent(), ev.getX(), ev.getY());
        		  }else if(np.getNodeType() != null && np.getNodeType().startsWith("column")){
        			  submenu.setEnabled(true);
        			  saveitemascol.setEnabled(false);
        			 // downloadFile.setEnabled(true);
        			  
        			  if(np.getCommand() != null && np.getCommand().startsWith("write")){
        				  saveitemasrow.setEnabled(false);
        			  }else{
        				  saveitemasrow.setEnabled(true);
        			  }

     		    	  getsimilaritems.setEnabled(false);
     		    	  markitemasnext.setEnabled(false);
     		    	  markitemasback.setEnabled(false);
        			  menu.show(ev.getComponent(), ev.getX(), ev.getY());
        		  }else if(np.getNodeType() != null && np.getNodeType().startsWith("next")){
        			  submenu.setEnabled(false);
        			  menu.show(ev.getComponent(), ev.getX(), ev.getY());
        		  }else if(np.getNodeType() != null && np.getNodeType().startsWith("back")){
        			  submenu.setEnabled(false);
        			  menu.show(ev.getComponent(), ev.getX(), ev.getY());
        		  }else if(np.getNodeType() != null && np.getNodeType().startsWith("similar")){
        			  submenu.setEnabled(true);
        			  saveitemascol.setEnabled(false);
     		    	  saveitemasrow.setEnabled(false);
     		    	  getsimilaritems.setEnabled(true);
     		    	  markitemasnext.setEnabled(false);
     		    	  markitemasback.setEnabled(false);
        			  
        			  menu.show(ev.getComponent(), ev.getX(), ev.getY());        			  
        		  }else{
        			  submenu.setEnabled(true);
        			  saveitemascol.setEnabled(true);
     		    	  saveitemasrow.setEnabled(false);
     		    	  getsimilaritems.setEnabled(true);
     		    	  markitemasnext.setEnabled(true);
     		    	  markitemasback.setEnabled(true);
        			  menu.show(ev.getComponent(), ev.getX(), ev.getY());
        		  }
        		  

        	  }else{
        		  defaultmenu.show(ev.getComponent(), ev.getX(), ev.getY());
        		  Tree.getInstance().clearSelection();
        	  }
              
            }else{
            	  TreePath path = Tree.getInstance().getPathForLocation(ev.getX(), ev.getY());
            	  
              	  if (path == null){
              		  Tree.getInstance().clearSelection();
              		  ToolBar.proxyButton.setText("Set Global Proxy");
              		ToolBar.proxyButton.setEnabled(true);
              	  }
            }
	}

	public void mouseClicked(MouseEvent ev) {
      }
      
    }
