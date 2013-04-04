package scrape.it.widgets.tree;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JOptionPane;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.widgets.tree.actions.ClickingAction;
import scrape.it.widgets.tree.actions.CopyAction;
import scrape.it.widgets.tree.actions.DeleteAction;
import scrape.it.widgets.tree.actions.DeleteSelection;
import scrape.it.widgets.tree.actions.PasteAction;

public class aKeyListener implements KeyListener {

	@Override
	public void keyPressed(KeyEvent evt) {
		

		
        if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_C) {
    		if(SelectedNode.type.startsWith("head")){
    			JOptionPane.showMessageDialog(ClientGui.frame, "You cannot do that for the header node. Try it's children."); 
    			return;
    		}

            new CopyAction();

        } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_X) {
    		if(SelectedNode.type.startsWith("head")){
    			JOptionPane.showMessageDialog(ClientGui.frame, "You cannot do that for the header node. Try it's children."); 
    			return;
    		}

            new CopyAction();
            new DeleteAction();

        } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_V) {

        	new PasteAction(SelectedNode.treeNode);

        } else if (evt.getKeyCode() == KeyEvent.VK_DELETE){
        	if(JOptionPane.showConfirmDialog(ClientGui.frame, "Confirm Delete. All Descendants Will Be Removed As Well!") == 0){
        		new DeleteAction();
        	}
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER){
        	new ClickingAction();
        }

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
