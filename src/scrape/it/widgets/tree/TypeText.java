package scrape.it.widgets.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import scrape.it.browser.MyBrowser;

public class TypeText extends JMenuItem implements ActionListener {

	public TypeText(){
		super("Type Text Here");
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		//simple user input so enter wahtever the fuck user wants.
		Object result = JOptionPane.showInputDialog(null, "Enter string to type into this input field. This doesn't remember what you type!");
	    String keyword = (String) result;
	    
	    MyBrowser.getInstance().browser.waitReady();
	    String js = "var headings = document.evaluate('" + SelectedNode.xpath + "', document, null, XPathResult.ANY_TYPE, null); var thisHeading = headings.iterateNext(); thisHeading.value = '" + keyword + "'";
	    MyBrowser.getInstance().browser.executeScript(js);
	}

}
