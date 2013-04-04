package scrape.it.widgets.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;

import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.mozilla.MozillaBrowser;


public class DownloadFile extends JMenuItem implements ActionListener {
	
	private DefaultMutableTreeNode node;

	public DownloadFile(){
		super("Download File");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		DownloadAction();
		Tree.getInstance().repaint();
	}

	private void DownloadAction() {

		Object result = JOptionPane.showInputDialog(null, "Enter a File Extension( ie. pdf) :");
	    String keyword = (String) result;
	    if(keyword != null && !keyword.isEmpty()){
	    
			node = (DefaultMutableTreeNode) Tree.getInstance().getLastSelectedPathComponent();	
			
			NodePro np = (NodePro) node.getUserObject();
			String thisText = "DOWNLOAD FILE FROM ";
			np.setText("<html><font color='#0000FF'>" + thisText + "</font></html>");
			np.filedl ="true";
			np.fileext = "." + keyword;
			
			Global.db.save(np);

	    }
	}

}
