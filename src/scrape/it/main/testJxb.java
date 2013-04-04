package scrape.it.main;


import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.BrowserType;
import com.teamdev.jxbrowser.mozilla.MozillaBrowser;
import com.teamdev.jxbrowser.webkit.WebBrowserFactory;


import javax.swing.*;

import org.mozilla.interfaces.nsIPrefBranch;
import org.mozilla.interfaces.nsIPrefService;
import org.mozilla.xpcom.Mozilla;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The sample demonstrates how to register HttpObserver to handle HTTP
 * request/response and get/set information about HTTP headers, methods and etc.
 */
public class testJxb {
    public static void main(String[] args) {
        MozillaBrowser browser = (MozillaBrowser) BrowserFactory.createBrowser(BrowserType.Mozilla);


        JFrame frame = new JFrame();
        frame.getContentPane().add(browser.getComponent(), BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);



        
       
  
        
        browser.navigate("http://vancouver.craigslist.org/");
        browser.waitReady();
    	browser.executeScript("function invokeHandler(el, etype){ if (!el){return true;} if (el.fireEvent) { (el.fireEvent('on' + etype)); } else { var evObj = d.createEvent('Events'); evObj.initEvent(etype, true, false); el.dispatchEvent(evObj); }; }; invokeHandler(document.getElementById('go'),'click');");

	
    
    }
}