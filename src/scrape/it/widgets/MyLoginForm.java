package scrape.it.widgets;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import scrape.it.main.Global;
import scrape.it.persistence.DBConnection;


public class MyLoginForm extends JDialog implements ActionListener
{
	JButton SUBMIT;
	JPanel panel;
	JLabel label1,label2;
	final JTextField  text1,text2;
	public boolean loggedin;
	private String unique;
	private String username;
	private String passwd;
	
	public MyLoginForm()
	{
		label1 = new JLabel();
		label1.setText("Username:");
		text1 = new JTextField(15);

		label2 = new JLabel();
		label2.setText("Password:");
		text2 = new JPasswordField(15);

		SUBMIT=new JButton("SUBMIT");
    
		panel=new JPanel(new GridLayout(3,1));
		panel.add(label1);
		panel.add(text1);
		panel.add(label2);
		panel.add(text2);
		panel.add(SUBMIT);
    
		add(panel,BorderLayout.CENTER);
		
		SUBMIT.addActionListener(this);
		setTitle("Log-in");
		setSize(300,100);        
		setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
   public void actionPerformed(ActionEvent ae)
   {
	   username = text1.getText();
	   passwd = text2.getText();        

             //HttpPoster hp = new HttpPoster();
	   try {
				loggedin = true;
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				loggedin = false;
		}
		
            if (loggedin == true) {
            	JOptionPane.showMessageDialog(getContentPane(),"Welcome",
            			"Success",JOptionPane.INFORMATION_MESSAGE);
            	this.setVisible(false);
            	Global.username = username;
            }else{
            	JOptionPane.showMessageDialog(getContentPane(),"Incorrect login or password",
            			"Failed to Login",JOptionPane.ERROR_MESSAGE);
            }
   }
       
}