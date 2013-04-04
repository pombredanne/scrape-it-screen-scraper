package scrape.it.widgets;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.teamdev.jxbrowser.dom.DOMElement;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;


public class HtmlFormWindow
{
	JButton SUBMIT;
	JPanel panel;
	JLabel label1,label2;

	public boolean loggedin;
	private String unique;
	private String username;
	private String passwd;
	
	private JTextArea textbox;
	private JScrollPane scrollpane;
	
	private String inputType;
	private String atype;
	private DefaultListModel model;
	private JList list;
	
	public HtmlFormWindow(String type, DOMElement currentElement, NodePro nodep)
	{
		String id = String.valueOf(nodep.getId());
		
		if(atype.equals("TEXT")){
			JPanel panel = new JPanel(new BorderLayout());

			JTextArea textbox = new JTextArea("",10,10);

			
			int Ok = JOptionPane.showConfirmDialog(ClientGui.frame, panel,
					"Type following into HTML <input> field",JOptionPane.OK_CANCEL_OPTION);
			
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
			
			
		}else if(atype.equals("SELECT")){
				panel = new JPanel(new BorderLayout());
				label1.setText("Select Options to Click");
				panel.add(label1);
				


				

				
				//READ from existing file if not save new.
				try{
					
					list = new JList(readLines(id + ".txt"));
					
				}catch(Exception e){
					
					int Ok = JOptionPane.showConfirmDialog(ClientGui.frame, panel,
							"Select Following Options in <select> field",JOptionPane.OK_CANCEL_OPTION);
				
					if (Ok == JOptionPane.OK_OPTION) {
					
						try{
							

							FileWriter fstream = new FileWriter(id + ".txt"); 
							BufferedWriter out = new BufferedWriter(fstream);
							
							NodeList options = currentElement.getElementsByTagName("OPTION");
							ArrayList<String> data = new ArrayList<String>();
							for(int i=0;i < options.getLength();i++){
								DOMElement option = (DOMElement) options.item(i);
								data.add(option.getTextContent());
								out.write(option.getTextContent()); //writes the content to the file
							}
							out.close();
							list = new JList(data.toArray());
				
						}catch(Exception ed){
							
						}
					}
				}
			
			    scrollpane = new JScrollPane(list);

				panel.add(scrollpane);

		}

	}

	  public String[] readLines(String filename) throws IOException {
	        FileReader fileReader = new FileReader(filename);
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        ArrayList<String> lines = new ArrayList<String>();
	        String line = null;
	        while ((line = bufferedReader.readLine()) != null) {
	            lines.add(line);
	        }
	        bufferedReader.close();
	        return lines.toArray(new String[lines.size()]);
	    }
	
   
}