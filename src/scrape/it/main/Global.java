package scrape.it.main;

import java.awt.Cursor;
import java.awt.Frame;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.tree.TreePath;

import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;

import scrape.it.browser.MyBrowser;
import scrape.it.persistence.DBConnection;
import scrape.it.persistence.NodePro;
import scrape.it.server.Scraper;

public class Global {
	public static String username;
	public static String currentdomain;
	public static JRootPane rootPane;
	public static String url;
	public static boolean allowNavigation = true;
	public static String currentURL;
	public static Object lastInsertedNode;
	public static Object firstInsertedNode;
	public static boolean treeLoaded;
	public static List<NodePro> copiedNodes = new ArrayList<NodePro>();
	public static ODatabaseObjectTx db = DBConnection.getInstance().getDB();
	public static TreePath previousTreePath;
	public static String previousNodeText;
	private static HashMap<Long, Scraper> scraperInstances =  new HashMap<Long,Scraper>();
	public static HashMap<Long,Scraper> scraperList = new HashMap<Long,Scraper>();
	public static boolean isActivated = false;
	public static HashMap<String,String> features = new HashMap<String,String>();
	public static Map mapdata;
	public static boolean recording;
	public static JProgressBar progress = new JProgressBar();
	public static JLabel statusText = new JLabel();
	public static boolean navStarted;
	public static boolean applyAll;
	public static boolean applyAdd;
	public static boolean apply;
	public static boolean applyNext;
	public static Frame tempFrame;
	public static JPanel tempPanel;
	public static HashMap<String,String> similarMap = new HashMap<String,String>();
	 
	public static void showWaitCursor(){
		Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
		ClientGui.frame.setCursor(hourglassCursor);	
	
	}
	
	public static void showDefaultCursor(){
		Cursor hourglassCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		ClientGui.frame.setCursor(hourglassCursor);		
	}
	
	public static byte[] toByteArray (Object obj)
	{
	  byte[] bytes = null;
	  ByteArrayOutputStream bos = new ByteArrayOutputStream();
	  try {
	    ObjectOutputStream oos = new ObjectOutputStream(bos); 
	    oos.writeObject(obj);
	    oos.flush(); 
	    oos.close(); 
	    bos.close();
	    bytes = bos.toByteArray ();
	  }
	  catch (IOException ex) {
	    //TODO: Handle the exception
	  }
	  return bytes;
	}
	    
	public static Object toObject (byte[] bytes)
	{
	  Object obj = null;
	  try {
	    ByteArrayInputStream bis = new ByteArrayInputStream (bytes);
	    ObjectInputStream ois = new ObjectInputStream (bis);
	    obj = ois.readObject();
	  }
	  catch (IOException ex) {
	    //TODO: Handle the exception
	  }
	  catch (ClassNotFoundException ex) {
	    //TODO: Handle the exception
	  }
	  return obj;
	}

	public static void validateMap(Map hm) {
		// TODO Auto-generated method stub
		Set entries = hm.entrySet();
		Iterator it = entries.iterator();
		
		while(it.hasNext()){
			 Map.Entry entry = (Map.Entry) it.next();
		     System.out.println(entry.getKey() + "-->" + entry.getValue());
		}
		
	}
	


}
