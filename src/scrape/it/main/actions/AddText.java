package scrape.it.main.actions;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileFilter;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.persistence.NodePro;
import scrape.it.widgets.tree.SelectedNode;

public class AddText{
	
	private String txtfilename = "";
	private JLabel filenameLabel = new JLabel("", JLabel.RIGHT);
	final Font f = filenameLabel.getFont();
	
	private Boolean clickedBox = false;
	private Boolean loadedFile = false;
	
	public AddText(){
		final String id = String.valueOf(SelectedNode.id);
		//display joptionpane w/ JfileChooser support
		
		JPanel panel = new JPanel(new BorderLayout());

		final JTextArea textbox = new JTextArea("",10,10);
		
		//load the absolute path to the import text file.
		if(SelectedNode.command != null){
			 filenameLabel.setText(SelectedNode.command);
			 filenameLabel.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
		}
		
		//load text file to textarea if file exists.
		if(SelectedNode.nodeProperty.getNodeSiblingIndex() == 99113){
			try{
				
				FileReader thisfile = new FileReader(id +".txt");			
				textbox.read(new BufferedReader(thisfile), null);
				loadedFile = true;
			}catch(Exception zce){
				org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while reading file to textarea", zce);
			}
		}

		
		if(textbox.getText().isEmpty())
			textbox.setText("String for input value to be placed on each new line.\n If text file from above is imported already,\n contents of this textarea will be executed first.");

		JScrollPane scrollpane = new JScrollPane(textbox);
		
		
		//File Chooser
		final FileFilter filter = new FileFilter() {
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean accept(File f) {
				if (f.getName().toLowerCase().endsWith("txt"))
			      {
					return true;
			      }
				return false;
			}
		};
		
		//display useful text in textarea by adding lsitener
		textbox.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!loadedFile) textbox.setText("");
				clickedBox = true;

			}
		});
		

		 
		 //Create 'import file' button
		 
		 
		 					
		 JButton openButton = new JButton("Open");
		 
		 openButton.addActionListener(new ActionListener() {
			
			private File fileToOpen;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser();					   		    				  
				fileChooser.setFileFilter(filter);

				int a=fileChooser.showOpenDialog(ClientGui.frame);
				
				if(a==JFileChooser.APPROVE_OPTION)
					{
							   fileToOpen=fileChooser.getSelectedFile();
							   txtfilename= fileToOpen.getAbsolutePath();
							   filenameLabel.setText(txtfilename);
							   filenameLabel.setFont(f.deriveFont(f.getStyle() ^ Font.BOLD));
							 
					}
			}
		});
		 
		
		panel.add(new JLabel("Import a .txt file containing keywords on each new line OR type it manually below."), BorderLayout.PAGE_START);
		panel.add(filenameLabel, BorderLayout.LINE_START);
		panel.add(openButton, BorderLayout.LINE_END);
		
		panel.add(scrollpane, BorderLayout.PAGE_END);

		//show dialog
		int Ok = JOptionPane.showConfirmDialog(ClientGui.frame, panel,
				"Type following into HTML <input> field",JOptionPane.OK_CANCEL_OPTION);
		
		//on click ok write to textfile and save directory path.
			if (Ok == 0) {
			
				try{
					NodePro selectedNode = SelectedNode.nodeProperty;
					
					
					String raw = textbox.getText();		
					
					if(clickedBox && !raw.isEmpty()){	
						try{
							FileWriter fstream = new FileWriter(id + ".txt"); 
							BufferedWriter out = new BufferedWriter(fstream);
							out.write(raw); //writes the content to the file
							out.close();
							selectedNode.setNodeSiblingIndex(99113);
						}catch(Exception aaa){
							org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while writing textarea to file", aaa);
						}
					}
					
					if(txtfilename != ""){
						
						selectedNode.setCommand(txtfilename);
						
					}
					
					Global.db.save(selectedNode);
					
				}catch(Exception ed){
					org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while executing action after textarea OK button", ed);
				}
			}
		
		
	}


}
