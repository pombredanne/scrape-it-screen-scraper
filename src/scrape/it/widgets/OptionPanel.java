package scrape.it.widgets;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import scrape.it.browser.MyBrowser;
import scrape.it.main.Global;
import scrape.it.main.actions.NewTask;
import scrape.it.main.actions.RunTask;
import scrape.it.main.actions.TestTask;
import scrape.it.persistence.DBConnection;

import java.net.MalformedURLException;
import java.net.URL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionPanel extends JPanel
                         implements ActionListener {
    protected JTextArea textArea;
    protected String newline = "\n";
	private String url;
	private URL thisdomain;
	private String basedomain;
	private NewTask newtask;
	private JRootPane rootPane;
	static public JButton recordButton;
	static public JButton repeatButton;
	static public JButton extractButton;
	static public JButton cancelButton;
	static public JButton navigateButton;
	static public JButton button1;
	static public JButton button2;
	static public JButton button3;
    static final private String NEW = "new";
    static final private String TEST = "test";
    static final private String RUN = "run";
	public static final String RECORD = "record";
	public static final String PROXY = "proxy";
	public static final String REPEAT = "repeat";
	public static final String EXTRACT = "extract";
	public static final String CANCEL = "cancel";
	public static final String NAVIGATE = "navigate";

    public OptionPanel() {

    	setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    	addButtons();
        //Lay out the main panel.
        setPreferredSize(new Dimension(350, 150));
        //add(toolBar, BorderLayout.PAGE_START);
        	
       Global.rootPane = getRootPane();
       setVisible(true);
       

    }

    protected void addButtons() {
      
/*
        //first button
        button1 = makeNavigationButton("New24", NEW,
                                      "Back to previous something-or-other",
                                      "New");
        toolBar.add(button1);

        //second button
        button2 = makeNavigationButton("Test24", TEST,
                                      "Up to something-or-other",
                                      "Test");
        toolBar.add(button2);

        //third button
        button3 = makeNavigationButton("Run24", RUN,
                                      "Forward to something-or-other",
                                      "Run");
        toolBar.add(button3);
        
        toolBar.addSeparator();
        */
        recordButton = makeNavigationButton("actionrecord", RECORD,
                "Record Action",
                "Begin Recording");
        
        repeatButton = makeNavigationButton("actionrepeat", REPEAT,
                "Repeat Action",
                "Repeat Action");
        
        extractButton = makeNavigationButton("actionextract", EXTRACT, "Extract", "Extract");
        
        cancelButton = makeNavigationButton("actioncancel",CANCEL,"Cancel","Cancel");
        
      
        /*
        button4 = makeNavigationButton("Record24", PLAY,
                "Record Action",
                "Record");
        
        button4 = makeNavigationButton("Record24", STOP,
                "Record Action",
                "Record");
        
   
        
        button4 = makeNavigationButton("Record24", PROXY,
                "Record Action",
                "Record");
        
        button4 = makeNavigationButton("Record24", PROXY,
                "Record Action",
                "Record");

        */

        
    }

    public JButton makeNavigationButton(String imageName,
                                           String actionCommand,
                                           String toolTipText,
                                           String altText) {
        //Look for the image.
        String imgLocation = "media_assets/"
                             + imageName
                             + ".png";
        URL imageURL = OptionPanel.class.getResource(imgLocation);
       
        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        button.setIcon(new ImageIcon(imgLocation, altText));
        button.setText(altText);


        return button;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String description = null;

        // Handle each button.
        if (RECORD.equals(cmd)) { //"new" button clicked
        	
        	if(recordButton.getText().startsWith("Begin")){
        		recordButton.setText("Finish Recording");
        		recordButton.setIcon(new ImageIcon("media_assets/actionstop.png"));
        		new ActionRecord();

        	}else if(recordButton.getText().startsWith("Finish")){
        		recordButton.setText("Begin Recording");	
        		recordButton.setIcon(new ImageIcon("media_assets/actionrecord.png"));
        		new ActionRecord(true);
        		
        	}
        } else if (REPEAT.equals(cmd)) { // second button clicked
        	new ActionRepeat();
        } else if (RUN.equals(cmd)) { // third button clicked
            new RunTask();
        } else if (EXTRACT.equals(cmd)){
        	new ExtractData();
        } else if(CANCEL.equals(cmd)){
        	
        } else if(NAVIGATE.equals(cmd)){
        	
        }
    }   

}