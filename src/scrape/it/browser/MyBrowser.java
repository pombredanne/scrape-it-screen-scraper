package scrape.it.browser;

import java.awt.Component;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.tree.DefaultMutableTreeNode;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;

import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.BrowserServices;
import com.teamdev.jxbrowser.BrowserType;
import com.teamdev.jxbrowser.dom.DOMDocument;
import com.teamdev.jxbrowser.dom.DOMElement;
import com.teamdev.jxbrowser.events.NavigationEvent;
import com.teamdev.jxbrowser.events.NavigationFinishedEvent;
import com.teamdev.jxbrowser.events.NavigationListener;
import com.teamdev.jxbrowser.events.ProgressChangedEvent;
import com.teamdev.jxbrowser.events.ProgressListener;
import com.teamdev.jxbrowser.events.StatusChangedEvent;
import com.teamdev.jxbrowser.events.StatusListener;
import com.teamdev.jxbrowser.script.ScriptErrorEvent;
import com.teamdev.jxbrowser.script.ScriptErrorListener;
import com.teamdev.jxbrowser.script.ScriptErrorWatcher;

public class MyBrowser {
	private  static MyBrowser INSTANCE;
	public static Browser browser;
	private static URL url;
	private ProgressListener progressListener;
	private StatusListener statusListener;
	
	public MyBrowser(){
		createBrowser();		
	}
	
	public synchronized static MyBrowser getInstance(){
		
		if (INSTANCE == null){
			INSTANCE = new MyBrowser();			
		}
		
		return INSTANCE;		
	}
	
	public Browser createBrowser(){
        browser = BrowserFactory.createBrowser(BrowserType.Mozilla);   
        browser.addNavigationListener(new MyNavigationListener());

        progressListener = new ProgressListener() {
            public void progressChanged(final ProgressChangedEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Global.progress.setMaximum((int) event.getMaxProgress());
                        Global.progress.setValue((int) event.getCurrentProgress());
                    }
                });
            }
        };

        statusListener = new StatusListener() {
            public void statusChanged(final StatusChangedEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        Global.statusText.setText(event.getStatusText());
                    }
                });
            }
        };

        browser.addProgressListener(progressListener);
        browser.addStatusListener(statusListener);
        
        
        
        browser.getComponent().addMouseListener(new MyMouseListener());
        getBrowserElement().addEventListener("click", new MyEventListener(), false);
        
        BrowserServices services = browser.getServices();
        ScriptErrorWatcher scriptErrorWatcher = services.getScriptErrorWatcher();
        scriptErrorWatcher.addScriptErrorListener(new ScriptErrorListener() {
        	
			@Override
			public void scriptErrorHappened(ScriptErrorEvent event) {
	               System.out.println("javascripterror = " + event.getMessage());
				
			}
        });
        
        return browser;
	}
	
	public Component getBrowserComponent(){
		Component thiscomponent = browser.getComponent();
		return thiscomponent;
	}
	
	public DOMElement getBrowserElement(){		
		return (DOMElement) browser.getDocument().getDocumentElement();
	}
	
	public DOMDocument getBrowserDocument(){
		return (DOMDocument) browser.getDocument();		
	}
	
	public void navigateTo(String aurl){
		if(aurl != null){
        browser.navigate(aurl.toString());
        browser.waitReady();
		}
        //Global.executeStuff();
	}

}
