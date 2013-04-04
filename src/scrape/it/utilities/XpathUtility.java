package scrape.it.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import scrape.it.browser.MyBrowser;

import com.teamdev.jxbrowser.dom.DOMDocument;
import com.teamdev.jxbrowser.dom.DOMElement;

public class XpathUtility {
	HashMap<Node, String> xpathCache = new HashMap<Node, String>();
	HashMap<Node, Integer> nodeIndexCache = new HashMap<Node, Integer>();
	private StringBuffer path;
	private XMLReader reader;
	private Set<Entry<String, Integer>> set;
	

				    
		public String getElementXpath(DOMElement elt){
            StringBuilder path =new StringBuilder();          

            for (Node fib = (Node) elt; fib != null; fib = fib.getParentNode()){      

                if (fib.getNodeType() == Node.ELEMENT_NODE){
                	
                	String cachedParentPath = xpathCache.get(fib);
                	
                	 if (cachedParentPath != null){
                         path.insert(0, cachedParentPath);
                         break;
                     }

                   
                    int idx = getElementIdx(fib);
                    StringBuilder xname = new StringBuilder(fib.getNodeName());

                        if (idx >= 1) xname.append( "[" + idx + "]");
                       xname.insert(0, "/");
                       
                        //path.append("/").append(xname).append(path);
                        
                        path.insert(0, xname);
                }
            }
            
            if (!xpathCache.containsKey((Node)elt)){
                xpathCache.put ((Node)elt, path.toString());
             }
            
            return path.toString();           
        }

        private int getElementIdx(Node elt) {
        	  Integer count = nodeIndexCache.get(elt);
              if (count != null){
                return count;
              }
              
             count = 1;
             for (Node sib = elt.getPreviousSibling(); sib != null; sib = sib.getPreviousSibling())
                {
                    if (sib.getNodeType() == Node.ELEMENT_NODE){
                        
                        if(sib.getNodeName().equals(elt.getNodeName())){
                            count++;
                        }
                    }
                }
             nodeIndexCache.put((Node) elt, count);
             
            return count;
        }
       
		
		public NodeList findElementsByXpath(DOMDocument doc, String axpath) throws XPathExpressionException{
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodes = (NodeList) xPath.evaluate(axpath, doc, XPathConstants.NODESET);			
			return nodes;			
		}
		
		public DOMElement getElementByXpath(String tpath, String tagname){
			
			
			NodeList nl = MyBrowser.getInstance().getBrowserDocument().getElementsByTagName(tagname);

			//input tags
			for(int i=0;i<nl.getLength();i++){
				DOMElement input = (DOMElement) nl.item(i);
				if(input.getAttribute("xpath") == tpath){
					return input;
				}
			}
			return null;		
		}
		 
		private String getTagName(String ttpath) {
			String[] xpathArray = ttpath.split("/");
			String ax = xpathArray[xpathArray.length - 1];
			String[] nextArray = ax.split("[");
			String tagname = nextArray[0];
			return tagname;
		}

		public DOMElement findElementByXpath(DOMDocument domdoc, String axpath) throws Exception{
			String[] xpathArray = axpath.split("/");
			ArrayList<String[]> xpathmap = new ArrayList<String[]>();
			
				for (int i =2;i<xpathArray.length;i++){					
					String[] item = xpathArray[i].split("\\[");
					String tagname = item[0];
					String index = item[1].replace("]","");		
					//System.out.println(index + " / " + tagname);
					String[] xpathitem = {tagname, index};
					 xpathmap.add(xpathitem);
				}			
				
				NodeList startingList = domdoc.getElementsByTagName("html");			
				Node startNode = startingList.item(0);
				
				Node targetNode = startNode;
				DOMElement childNode = (DOMElement) startNode;
				
				for (int i =0;i<xpathmap.size();i++) {
					String[] item = xpathmap.get(i);
					//System.out.println(item[0] + " : " + item[1]);
						NodeList children = childNode.getElementsByTagName(item[0]);
						targetNode = findNode(children, item);							
				 } 
				
	
			return (DOMElement) targetNode;
		}
		
		public Node findNode (NodeList nl, String[] item){
			ArrayList<Node> siblings = new ArrayList<Node>();
			String tagname = item[0];
			int ind = Integer.parseInt(item[1]) - 1;
			
			for(int i=0; i<nl.getLength(); i++){
				  Node thisnode = nl.item(i);
				  if (thisnode.getNodeName().equalsIgnoreCase(tagname)){
					  
					  //System.out.println(thisnode.getNodeName() + "  | " +tagname);
					  siblings.add(thisnode);
				  }		
				  //System.out.println("THIS NODE " + nl.item(i).getNodeName());
			}
			//System.out.print("index " + ind);
			//System.out.println("size " + siblings.size());
			return siblings.get(ind);
		}
		
				

		
		

}
