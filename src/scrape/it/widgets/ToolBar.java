package scrape.it.widgets;

import it.scrape.browser.resources.ResourceLoader;

import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.teamdev.jxbrowser.BrowserServices;
import com.teamdev.jxbrowser.proxy.AuthenticationHandler;
import com.teamdev.jxbrowser.proxy.ProxyConfig;
import com.teamdev.jxbrowser.proxy.ProxyServer;
import com.teamdev.jxbrowser.proxy.ProxyServerLogin;
import com.teamdev.jxbrowser.proxy.ServerType;

import scrape.it.browser.MyBrowser;
import scrape.it.main.ClientGui;
import scrape.it.main.Global;
import scrape.it.main.actions.ApplyRule;
import scrape.it.main.actions.CreateNewTaskAction;
import scrape.it.main.actions.NewTask;
import scrape.it.main.actions.RunTask;
import scrape.it.main.actions.TestTask;
import scrape.it.persistence.DBConnection;
import scrape.it.server.Start;
import scrape.it.server.StartAction;
import scrape.it.server.Stop;
import scrape.it.server.StopTaskAction;
import scrape.it.widgets.tree.ActivateNode;
import scrape.it.widgets.tree.CutItems;
import scrape.it.widgets.tree.MasterProxy;
import scrape.it.widgets.tree.SelectedNode;
import scrape.it.widgets.tree.SetProxy;
import scrape.it.widgets.tree.actions.CopyAction;
import scrape.it.widgets.tree.actions.CopyItems;
import scrape.it.widgets.tree.actions.DeleteAction;
import scrape.it.widgets.tree.actions.DeleteSelection;
import scrape.it.widgets.tree.actions.PasteAction;
import scrape.it.widgets.tree.actions.PasteItems;

import java.net.MalformedURLException;
import java.net.URL;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolBar extends JPanel
                         implements ActionListener {
    protected JTextArea textArea;
    protected String newline = "\n";
	private String url;
	private URL thisdomain;
	private String basedomain;
	private NewTask newtask;
	private JRootPane rootPane;
	private String userName;
	private String password;
	public static JButton editButton;
	public static JButton navButton;
	public static JButton newtaskButton;
	public static JButton proxyButton;
	public static JButton recordButton;
	public static JButton playButton;
	public static JButton stopButton;
	public static JButton copyButton;
	public static JButton cutButton;
	public static JButton pasteButton;
	public static JButton deleteButton;
	static public JButton button1;
	static public JButton button2;
	static public JButton button3;
    static final private String NEW = "new";
    static final private String TEST = "test";
    static final private String RUN = "run";
	private static final String RECORD = "record";
	private static final String PROXY = "proxy";
	private static final String PLAY = "play";
	private static final String EXTRACT = "extract";
	private static final String STOP = "stop";
	private static final String COPY = "copy";
	private static final String CUT = "cut";
	private static final String PASTE = "paste";
	private static final String DELETE = "delete";
	private static final String EDIT = "edit";
	private static final String NAVIGATE = "navigate";

    public ToolBar() {
        super(new BorderLayout());

        //Create the toolbar.
        JToolBar toolBar = new JToolBar("Still draggable");
        addButtons(toolBar);

        //Lay out the main panel.
        //setPreferredSize(new Dimension(450, 130));
        //add(toolBar, BorderLayout.PAGE_START);
        
        newtaskButton.setEnabled(true);
        recordButton.setEnabled(false);
        playButton.setEnabled(false);
        stopButton.setEnabled(false);
        copyButton.setEnabled(false);
        pasteButton.setEnabled(false);
        deleteButton.setEnabled(false);
        cutButton.setEnabled(false);
        proxyButton.setEnabled(true);
        editButton.setEnabled(false);
        navButton.setEnabled(false);

       Global.rootPane = getRootPane();
       setVisible(true);
       add(toolBar);
    }

    protected void addButtons(JToolBar toolBar) {
      
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
    	newtaskButton = makeNavigationButton("actiontasknew", NEW, "Create New Task", "New");
    	
        recordButton = makeNavigationButton("actionrecord", RECORD,
                "Record Your Browsing",
                "Record");
        
        editButton = makeNavigationButton("actionedit", EDIT, "Edit Rules For a Selection", "Edit");
        navButton = makeNavigationButton("actionnav", NAVIGATE, "Navigate to Selection's URL page", "Navigate");
        
        playButton = makeNavigationButton("actiontaskplay", RUN,
                "Run a Selection",
                "Run");
        
        stopButton = makeNavigationButton("actiontaskstop", STOP, "Stop Current Selection", "Stop");
        
        copyButton = makeNavigationButton("actioncopy", COPY, "Copy Selected & All Descendants", "Copy");
        cutButton = makeNavigationButton("actioncut", CUT, "Cut Selected & All Descendants", "Cut");
        pasteButton = makeNavigationButton("actionpaste", PASTE, "Paste into Selected", "Paste");
        deleteButton = makeNavigationButton("actiondelete", DELETE, "Delete Selected & All Descendants", "Delete");
        
        proxyButton = makeNavigationButton("actionproxy", PROXY, "Set Proxy for All Tasks and Browser View", "Set Global Proxy");
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
        toolBar.add(newtaskButton);
        toolBar.addSeparator();
        toolBar.add(recordButton);
        toolBar.add(editButton);
        toolBar.add(navButton);
        toolBar.addSeparator();
        toolBar.add(playButton);
        toolBar.add(stopButton);
        toolBar.addSeparator();
        toolBar.add(copyButton);
        toolBar.add(cutButton);
        toolBar.add(pasteButton);
        toolBar.add(deleteButton);
        toolBar.addSeparator();
        toolBar.add(proxyButton);
        
        
    }

    protected JButton makeNavigationButton(String imageName,
                                           String actionCommand,
                                           String toolTipText,
                                           String altText) {
        //Look for the image.

       
        //Create and initialize the button.
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        button.setIcon(ResourceLoader.loadIcon(imageName + ".png"));
        button.setText(altText);


        return button;
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String description = null;

        // Handle each button.
        if (RECORD.equals(cmd)) { //"new" button clicked

        	if(recordButton.getText().startsWith("Record")){
            	Global.allowNavigation = false;
            	Global.recording = true;
        		recordButton.setText("Finish Record");
        		recordButton.setIcon(ResourceLoader.loadIcon("actionstop.png"));
        		new ActionRecord();

        	}else if(recordButton.getText().startsWith("Finish")){
               	Global.allowNavigation = true;
            	Global.recording = false;
        		recordButton.setText("Record");	
        		recordButton.setIcon(ResourceLoader.loadIcon("actionstop.png"));
        		new ActionRecord(true);
        		
        	}
               
        } else if(EDIT.equals(cmd)){
    		try {
				new ApplyRule(null, "");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }else if(NAVIGATE.equals(cmd)){
        	new ActivateNode();
        	
        }else if (NEW.equals(cmd)){
        	Global.showWaitCursor();
    		new CreateNewTaskAction();
    		Global.showDefaultCursor();
        } else if (RUN.equals(cmd)){
        	new StartAction();
        }else if (STOP.equals(cmd)){
        	new StopTaskAction();
        }else if (COPY.equals(cmd)){
        	new CopyAction();
        }else if (CUT.equals(cmd)){
    		new CopyAction();
    		new DeleteAction();		
        }else if (PASTE.equals(cmd)){
    		new PasteAction(SelectedNode.treeNode);
        }else if (DELETE.equals(cmd)){
    		if(JOptionPane.showConfirmDialog(ClientGui.frame, "Confirm Delete. All Descendants Will Be Removed As Well!") == 0){
    			new DeleteAction();
    		}
        }else if(PROXY.equals(cmd)){
        	if(proxyButton.getText().startsWith("Set Global Proxy")){
        		setGlobalProxy();        		
        	}else{
        		setIndividualProxy();
        	}

        }
    }

	private void setGlobalProxy() {
		Object result = JOptionPane.showInputDialog(null, "Enter a master proxy applied to all jobs and browser.");
		String keyword = (String) result;
		if(keyword != null){
			setMasterProxy(keyword);
		}
		
	}

	private void setMasterProxy(String proxyAddress) {
		String ip = "";


		int port = 8080;
		//username:password@23.23.231.12:27302
		if(proxyAddress.contains("@")){
			try{
				String[] rawSplit = proxyAddress.split("@");
				String[] credentials = rawSplit[0].split(":");
							 userName = credentials[0];
							 password = credentials[1];
				
				String[] rawProxy = rawSplit[1].split(":");						    
							ip = rawProxy[0];
							port = Integer.parseInt(rawProxy[1]);
					
			}catch(Exception asdfa){}
		}else{
			try{
				String[] rawProxy = proxyAddress.split(":");						    
				ip = rawProxy[0];
				port = Integer.parseInt(rawProxy[1]);
			}catch(Exception asdf){asdf.printStackTrace();}
		}
		
		 ProxyConfig proxyConfig = BrowserServices.getInstance().getProxyConfig();
		 proxyConfig.setProxy(ServerType.HTTP, new ProxyServer(ip, port));
		 
			// Register authentication handler for HTTP Server type if required
	        proxyConfig.setAuthenticationHandler(ServerType.HTTP, new AuthenticationHandler() {

				@Override
				public ProxyServerLogin authenticationRequired(ServerType type) {
					   return new ProxyServerLogin(userName, password);
				}
			});
		
	}

	private void setIndividualProxy() {
		String proxy = "";
		if(SelectedNode.nodeProperty.selectedOptions != null){
			proxy  = SelectedNode.nodeProperty.selectedOptions;
		}
		Object result = JOptionPane.showInputDialog(null, "Enter a Proxy IP address (current proxy: " + proxy + ")");
	    String keyword = (String) result;
	    SelectedNode.nodeProperty.selectedOptions = keyword;
	    Global.db.save(SelectedNode.nodeProperty);
		
	}   

}