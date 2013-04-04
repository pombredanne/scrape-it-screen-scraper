package scrape.it.widgets;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;

public class TextInputDialog {
	JPanel panel = new JPanel(new BorderLayout());

	JTextArea textbox = new JTextArea("",10,10);
	/*
	
	int Ok = JOptionPane.showConfirmDialog(
			ClientGui.frame, panel,"Type following into HTML <input> field",JOptionPane.OK_CANCEL_OPTION);
	
	//READ from existing file if not save new.
	try{
		
		FileReader thisfile = new FileReader(id +".txt");
		textbox.read(new BufferedReader(thisfile), null);
		
	}catch(Exception e){
	
		if (Ok == JOptionPane.OK_OPTION) {
		
			try{
				
				String raw = textbox.getText();
				FileWriter fstream = new FileWriter(id + ".txt"); 
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(raw); //writes the content to the file
				out.close();
				
			}catch(Exception ed){
				
			}
		}
	}
	
	JScrollPane scrollpane = new JScrollPane(textbox);
	
	panel.add(new JLabel("Each Keyword or Phrase Should Be On New Line"), BorderLayout.NORTH);
	panel.add(scrollpane);
	
	}
*/
}
