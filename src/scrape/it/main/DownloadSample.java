package scrape.it.main;

/*
 * Copyright (c) 2000-2011 TeamDev Ltd. All rights reserved.
 * TeamDev PROPRIETARY and CONFIDENTIAL.
 * Use is subject to license terms.
 */

import com.teamdev.jxbrowser.Browser;
import com.teamdev.jxbrowser.BrowserFactory;
import com.teamdev.jxbrowser.BrowserType;
import com.teamdev.jxbrowser.mozilla.MozillaBrowser;




import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class DownloadSample {
    public static void main(String[] args) {
    	
        final Browser browser = BrowserFactory.createBrowser(BrowserType.Mozilla);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(browser.getComponent(), BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        MozillaBrowser mozillaBrowser = (MozillaBrowser) browser;
        final WebBrowser browser1x = mozillaBrowser.getPeer();

        Xpcom.invokeLater(new Runnable() {
            public void run() {

        
                DownloadManager manager = DownloadManagerFactory.getInstance().getDownloadManager(browser1x);
                URL url = null;
                try {
                    url = new URL("http://www.teamdev.com/downloads/jxbrowser/docs/JxBrowser-PGuide.pdf");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                File file = new File("E:/1/a.pdf");
                File temp = new File("E:/1/a.crc");
                try {
                    manager.addDownload(url.toURI(), file, temp);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                manager.showDownloadWindow();
            }
        });
    
    }
}
