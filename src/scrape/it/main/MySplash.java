package scrape.it.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class MySplash {
	private static MySplash INSTANCE;
	private static JWindow jw;
	private static String caption;
	private JPanel content;
	private JLabel label;
	private JLabel copyrt;
	
	
	public MySplash(){
		createSplash();
	}

	private void createSplash() {
		jw = new JWindow();
	    content = (JPanel) jw.getContentPane();
	    content.setBackground(Color.white);

	    // Set the window's bounds, centering the window
	    int width = 328;
	    int height = 131;
	    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screen.width - width) / 2;
	    int y = (screen.height - height) / 2;
	    jw.setBounds(x, y, width, height);

	    // Build the splash screen
	    label = new JLabel(new ImageIcon("media_assets/splash.jpg"));
	    content.add(label, BorderLayout.CENTER);

	    Color oraRed = new Color(156, 20, 20, 255);
	    content.setBorder(BorderFactory.createLineBorder(oraRed, 0));

	}
	
	public synchronized static MySplash getInstance(){
		if(INSTANCE==null){
			INSTANCE = new MySplash();
		}
		
		return INSTANCE;
		
	}
	
	public void setCaption(String version, String licensee, String company){
		if(version.contains("Free Version")){
		     copyrt = new JLabel("<html>" + version + "<Br>Copyright 2009-2011, Scrape.it" + "</html>",
				        JLabel.CENTER);
		}else{
	     copyrt = new JLabel("<html>" + version + "<Br>Licensed To: " + licensee +"<Br>Company: " + company + "<Br>Copyright 2009-2011, Scrape.it" + "</html>",
		        JLabel.CENTER);
		}
	    			copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
	    

		    content.add(copyrt, BorderLayout.SOUTH);
	}
	
	public void setLoadingSplash(){
	}
	
	public void showSplash(){
		jw.setVisible(true);
	    jw.setAlwaysOnTop(true);
	    jw.toFront();
		return;
	}
	
	public void hideSplash(){
		jw.setVisible(false);
		jw.setAlwaysOnTop(false);
		jw.toBack();
		content.remove(copyrt);
		return;
	}
}
