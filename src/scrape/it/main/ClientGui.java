package scrape.it.main;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import grid.designer.SpreadsheetDesigner;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.Graphics2D;


import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.xml.sax.InputSource;

import scrape.it.browser.MyBrowser;
import scrape.it.helper.OfficeOptionsDialog;
import scrape.it.main.actions.LoadTaskHeaders;
import scrape.it.main.actions.SplashDemo;

import scrape.it.widgets.*;
import scrape.it.widgets.table.Table;
import scrape.it.widgets.tree.BuildTree;
import scrape.it.widgets.tree.Tree;



import TurboActivate.BoolRef;
import TurboActivate.NotActivatedException;
import TurboActivate.TurboActivate;

import com.jidesoft.dialog.MultiplePageDialog;
import com.jidesoft.plaf.LookAndFeelFactory;
import com.jidesoft.swing.DefaultOverlayable;
import com.jidesoft.swing.JideBoxLayout;
import com.jidesoft.swing.JideSplitPane;
import com.jidesoft.swing.JideTabbedPane;
import com.jidesoft.swing.StyledLabelBuilder;
import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.BrowserServices;
import com.teamdev.jxbrowser.BrowserType;
import com.teamdev.jxbrowser.DefaultWebPolicyDelegate;
import com.teamdev.jxbrowser.WebPolicyDelegate;
import com.teamdev.jxbrowser.dom.DOMDocument;
import com.teamdev.jxbrowser.dom.DOMElement;
import com.teamdev.jxbrowser.dom.proxy.NamedNodeMapProxy;
import com.teamdev.jxbrowser.events.NavigationEvent;
import com.teamdev.jxbrowser.events.NavigationFinishedEvent;
import com.teamdev.jxbrowser.events.NavigationListener;
import com.teamdev.jxbrowser.script.ScriptErrorEvent;
import com.teamdev.jxbrowser.script.ScriptErrorListener;
import com.teamdev.jxbrowser.script.ScriptErrorWatcher;

public class ClientGui extends JFrame
         {

    private JLabel label;
	private JScrollPane browserPane;
	private JScrollPane treePane;
	private JScrollPane tablePane;
	private JideSplitPane jideplitPlane1;
	private JideSplitPane jideplitPlane;
	private JideTabbedPane jidetabbedPane;
	private JideSplitPane jideplitPlane2;
	private JScrollPane propertyPane;
	private JideTabbedPane jidetabbedPane1;
	private ToolBar toolBar;

    private static String titleCaption;
	public static ClientGui frame;

    public ClientGui() {
        super(titleCaption);
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
        	 public void windowClosing(WindowEvent e) {
        	        System.exit(0);
        	 }
		});
        
        
        addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			       if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			            // TODO ignore or warn user here.
			            // or call e.consume();
			    	   System.out.println("ogm");
			        }				
			}
		});
        
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			LookAndFeelFactory.installDefaultLookAndFeelAndExtension();

		} catch (Exception e) {
			org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while setting Look and Feel",e);			
		}	
		
		//try{ss
		
		
		SpreadsheetDesigner spreadsheet = new SpreadsheetDesigner();
    	spreadsheet.setVisible(true);
    	spreadsheet.setBorder(null);
    	((javax.swing.plaf.basic.BasicInternalFrameUI) spreadsheet.getUI()).setNorthPane(null);
    	//MyLoginForm login = new MyLoginForm(); //show login form
		
    	LoadTaskHeaders lth = new LoadTaskHeaders(); //load header tasks    	
    	
    	Global.treeLoaded = false;
    	
    	//construct browser view, tree view, table view.

        browserPane  = new JScrollPane(MyBrowser.getInstance().getBrowserComponent());
        treePane = new JScrollPane(Tree.getInstance());
        tablePane = new JScrollPane(spreadsheet);
       
        
        //Tree.getInstance().setRootVisible(false);
       // Tree.getInstance().setShowsRootHandles(true);
        
		propertyPane = new JScrollPane(new PropertyWindow());

        //hideSplash screen
        MySplash.getInstance().hideSplash();
        
        //split browser view and table view
        //splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
               // browserPane,tablePane);
        jidetabbedPane1 = new JideTabbedPane();
        jidetabbedPane1.addTab("PropertyPanel", propertyPane);
        jidetabbedPane1.setPreferredSize(new Dimension(50,50));
        jidetabbedPane1.setMaximumSize(new Dimension(50,50));
        jidetabbedPane1.setSize(new Dimension(25,25));
        
        
        //add overlay
        DefaultOverlayable dover = new DefaultOverlayable(jidetabbedPane1);
        dover.addOverlayComponent(StyledLabelBuilder.createStyledLabel("{Enter description here:f:gray}"));
       
        
        jideplitPlane1 = new JideSplitPane(JideSplitPane.VERTICAL_SPLIT);
        jideplitPlane1.setProportionalLayout(true);
        jideplitPlane1.add(browserPane,JideBoxLayout.FLEXIBLE);
        jideplitPlane1.add(tablePane,JideBoxLayout.FLEXIBLE);
        
        jideplitPlane2 = new JideSplitPane(JideSplitPane.VERTICAL_SPLIT);
        jideplitPlane2.setProportionalLayout(true);
        jideplitPlane2.add(treePane,JideBoxLayout.FLEXIBLE);

        //jideplitPlane2.add(jidetabbedPane1,JideBoxLayout.VARY);

        //jideplitPlane2.resetToPreferredSizes();
        
        
        jidetabbedPane = new JideTabbedPane();
        Icon webIcon = new ImageIcon("media_assets/web.gif");
        Icon sprdIcon = new ImageIcon("media_assets/spreadsheet.png");
		jidetabbedPane.addTab("Browser", webIcon,browserPane);
        jidetabbedPane.addTab("Spreadsheet", sprdIcon, tablePane);
        

        
        //main
        jideplitPlane = new JideSplitPane(JideSplitPane.HORIZONTAL_SPLIT);
        jideplitPlane.setProportionalLayout(true);
        jideplitPlane.addPane(jideplitPlane2);  
        jideplitPlane.addPane(jidetabbedPane);
        

        toolBar = new ToolBar();

        //Add the split pane to this frame
        //add(new ToolBar(), BorderLayout.PAGE_START);
        getContentPane().add(toolBar, BorderLayout.PAGE_START);
        getContentPane().add(jideplitPlane,BorderLayout.CENTER);
       // getContentPane().add(createStatusBar(),BorderLayout.PAGE_END);
    }
    
    private JPanel createStatusBar() {
 
        Global.progress.setPreferredSize(new Dimension(150, 20));
        Global.progress.setMinimumSize(new Dimension(150, 20));
        Global.progress.setVisible(true);


        JPanel result = new JPanel();
        result.setLayout(new BorderLayout());
        result.setBorder(BorderFactory.createEmptyBorder());
        result.setPreferredSize(new Dimension(Integer.MAX_VALUE, 20));
        result.add(Global.progress, EAST);
        result.add(Global.statusText, CENTER);
        return result;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        frame = new ClientGui();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    


    public static void main(String[] args) {

				//org.slf4j.LoggerFactory.getLogger(this.getClass()).info("Booting...");
		MySplash.getInstance().showSplash();
				
            	checkInstances();
                initLicense();
                createAndShowGUI();
    }
    
    public JFrame getFrame(){
    	return this;
    }


    
	private static void initLicense() {
		
		try
		{
	
		
			Global.isActivated = true;
	
								
			if (Global.isActivated)
			{
										
				String form = TurboActivate.GetFeatureValue("form");
				String form1 = TurboActivate.GetFeatureValue("image");
				String form2 = TurboActivate.GetFeatureValue("jobs");
				String form3 = TurboActivate.GetFeatureValue("js");
				String form4 = TurboActivate.GetFeatureValue("proxy");
				String form5 = TurboActivate.GetFeatureValue("licensetype");
				String form6 = TurboActivate.GetFeatureValue("firstname");
				String form7 = TurboActivate.GetFeatureValue("lastname");
				String form8 = TurboActivate.GetFeatureValue("company");
				//String form9 = TurboActivate.GetFeatureValue("download");
				
				if(genuine == true){

					if(form5 != null){
						titleCaption = "Scrape.it Screen Scraper - " + form5 + " Open Source" + " GPL Licensing Copyright Scrape.it 2009~2013" + 
																											form6 + " " + form7 + " at " + form8;
						
						MySplash.getInstance().setCaption(form5 + " Version 1.1 ", form6, form8);
						MySplash.getInstance().showSplash();
					}
				}
				
				Global.features.put("form", form);
				Global.features.put("image", form1);
				Global.features.put("jobs", form2);
				Global.features.put("js", form3);
				Global.features.put("proxy", form4);
				Global.features.put("licensetype", form5);
				Global.features.put("firstname", form6);
				Global.features.put("lastname", form7);
				Global.features.put("company", form8);
				//Global.features.put("download", form9);
				//TODO: do something with the featureValue
				
			}else{
				org.slf4j.LoggerFactory.getLogger(ClientGui.class).info("isActivate false");
				JOptionPane.showMessageDialog(null, "Product is not activated. If you have already " +
				"activated this product, make sure you are connected to the internet. Reverting to Free Version.");
				
				titleCaption = "Scrape.it Screen Scraper - Open Source";
				return;
			}


		}catch(NotActivatedException nae){
			org.slf4j.LoggerFactory.getLogger(ClientGui.class).error("Not Activated",nae);
			//JOptionPane.showMessageDialog(null, "Product not activated. If you have already " +"activated this product, make sure you are connected to the internet. Reverting to Free Version.");
			titleCaption = "Scrape.it Screen Scraper - Open Source";
			
			MySplash.getInstance().setCaption("Open Source", null, null);
			MySplash.getInstance().showSplash();
			return;
		}
		catch (Exception e)
		{
			org.slf4j.LoggerFactory.getLogger(ClientGui.class).error("Error During Checking Genuine/Activation",e);
			JOptionPane.showMessageDialog(null, e.getMessage());
            System.exit(1);
		}
		
	}
	
	private static void checkInstances() {
	     try {
	            ServerSocket SERVER_SOCKET = new ServerSocket(27889);

	        } catch (Exception x) {
				org.slf4j.LoggerFactory.getLogger(ClientGui.class).error("Error creating new server socket", x);
	            
	            JOptionPane.showMessageDialog(null, "Maximum of 1 running instance supported. " +
	            		"If this was the first time you ran it, please allow Scrape.it to run in your firewall settings.");
	            System.exit(1);
	        }
	}
}

