package scrape.it.widgets.tree;

import java.awt.BorderLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

import org.w3c.dom.NodeList;

import com.teamdev.jxbrowser.dom.DOMElement;

import scrape.it.browser.MyBrowser;
import scrape.it.main.ClientGui;
import scrape.it.main.Global;

public class QueueOptions extends JMenuItem implements ActionListener {




	public QueueOptions(){
		super("Queue Select Options");
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel panel = new JPanel(new BorderLayout());
		
		JList list;
		
		JScrollPane scrollpane;
		
		final String id = String.valueOf(SelectedNode.id);
		JLabel label1 = new JLabel("Hold Shift and Click to Select Many Options");
		panel.add(label1);
		
		String selectXpath = SelectedNode.xpath;
		String[] grabbedOptions = getOptions(selectXpath);
		
		//build the list from the <select> html eekleemnt
		list = new JList(grabbedOptions);
		
		list.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		scrollpane = new JScrollPane(list);
		
		panel.add(scrollpane);
		
		//if already existing
		if(SelectedNode.nodeProperty.selectedOptions != null){
			if(!list.isSelectionEmpty()) list.clearSelection();
			int[] indices = stringToArray(SelectedNode.nodeProperty.selectedOptions);
			//SelectedNode.nodeProperty.selectedOptions = (List) Arrays.asList(selectedIndexes);
			list.setSelectedIndices(indices);
		}
		
		int Ok = JOptionPane.showConfirmDialog(ClientGui.frame, panel,
				"Queue/Click options in <select>",JOptionPane.OK_CANCEL_OPTION);
		
		//on click ok write to textfile and save directory path.
			if (Ok == 0) {
				try{
					int[] selectedIndexes = list.getSelectedIndices();
					SelectedNode.nodeProperty.selectedOptions = arrayToString(selectedIndexes, ",");		
					Global.db.save(SelectedNode.nodeProperty);	
				}catch(Exception ed){
					org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while saving selectedindices", ed);
				}
			}
			
	}
	
   private int[] stringToArray(String selectedOptions) {

		String[] splits = selectedOptions.split(",");
	   	int[] numbers = new int[splits.length];
	    	for (int i=0; i < numbers.length; i++){
	    		 numbers[i] = Integer.parseInt(splits[i]);
	    	}
		return numbers;
	}
   
   private String arrayToString(int[] a, String separator) {
		StringBuffer result = new StringBuffer();
		
		if (a.length > 0) {
			result.append(Integer.toString(a[0]));
			
			for (int i=1; i<a.length; i++) {
				result.append(separator);
				result.append(Integer.toString(a[i]));
			}
		}
		
		return result.toString();
	}

	  
	private String[] getOptions(String axpath) {

		  //search for the xpath
		  String js = "function getOptions(){ var nodes = document.evaluate('//" + axpath + "/option', document, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE,null); var options = ''; for (var i = 0; i < nodes.snapshotLength; i++) { var node = nodes.snapshotItem(i); options += node.text + ','; }; var trimmedOptions = options.substring(0, options.length-1); return trimmedOptions; }; getOptions();";

		  MyBrowser.getInstance().browser.waitReady();
		  String options = MyBrowser.getInstance().browser.executeScript(js);
		  String[] optionArray = options.split(",");
		return optionArray;
	}
	
	public ArrayList<String> readLines(String filename) throws IOException {
		
	        FileReader fileReader = new FileReader(filename);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        ArrayList<String> lines = new ArrayList<String>();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            lines.add(line);
	        }
	        bufferedReader.close();
	        return lines;
	    }

}
