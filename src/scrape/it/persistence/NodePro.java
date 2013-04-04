package scrape.it.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class NodePro {
	private long id;
	
	private String text;
	private String xpath;
	private int nodeIndex; 
	private String command;
	private String nodeType;
	private String nodeAncestors;
	private int nodeSiblingIndex;
	private String pageURL;
	private String treePath;
	private long parentID;
	public String selectedOptions;
	public List<NodePro> kew;
	public String status;
	public long timestamp;
	public String filedl;
	public String fileext;
	public String attrName;
	public String attrId;
	public String attrHref;
	public String attrText;
	public Map columns = new HashMap<String,String>();
	
	public Map commands = new HashMap<String,String>();
	
	public NodePro(){
		this.timestamp = System.currentTimeMillis();
	}


	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	private String element;
	
	
	public long getParentID() {
		return parentID;
	}

	public void setParentID(long id2) {
		this.parentID = id2;
	}

	public String getTreePath() {
		return treePath;
	}

	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	public int getNodeIndex() {
		return nodeIndex;
	}

	public void setNodeIndex(int nodeIndex) {
		this.nodeIndex = nodeIndex;
	}

	public String getNodeancestors() {
		return nodeAncestors;
	}

	public void setNodeancestors(String nodeancestors) {
		this.nodeAncestors = nodeancestors;
	}

	public int getNodeSiblingIndex() {
		return nodeSiblingIndex;
	}

	public void setNodeSiblingIndex(int nodeSiblingIndex) {
		this.nodeSiblingIndex = nodeSiblingIndex;
	}

	public void setNodeType(String typeofnode){
		this.nodeType = typeofnode;
	}
	
	public String getNodeType(){
		return nodeType;
	}
	
	public long getId(){
		return id;
	}
	
	public void setId(Long aid){
		this.id = aid;
	}
	
	public void setText(String atext){
		this.text = atext;
	}
	
	public String getText(){
		return text;
	}
	
	public void setXpath(String xpath){
		this.xpath = xpath;
	}
	
	public String getXpath(){
		return xpath;
	}
	
	public void setCommand(String action){
		this.command = action;
	}
	
	public String getCommand(){
		return command;
	}
	
	public String toString(){
		return text;
	}

	public String getPageurl() {
		return pageURL;
	}

	public void setPageurl(String pageurl) {
		this.pageURL = pageurl;
	}
		
}
