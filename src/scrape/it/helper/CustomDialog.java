package scrape.it.helper;

import java.awt.Frame;

import javax.swing.JOptionPane;

import scrape.it.main.ClientGui;

public class CustomDialog {
	String[] buttons = { "Click on Element", "Click on All Elements Like This", "Extract Data", "Cancel"};    
	Frame[] frame = ClientGui.getFrames();
	
		
	
	public CustomDialog(){
		
	}
	
	int returnValue = JOptionPane.showOptionDialog(frame[0], "Narrative", "Narrative",
			 JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, buttons, buttons[0]);

}
