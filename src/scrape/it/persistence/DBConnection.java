package scrape.it.persistence;


import com.orientechnologies.orient.core.db.object.ODatabaseObjectPool;
import com.orientechnologies.orient.core.db.object.ODatabaseObjectTx;
import com.orientechnologies.orient.core.hook.ODocumentHookAbstract;
import com.orientechnologies.orient.core.record.impl.ODocument;

public class DBConnection {
	private static DBConnection INSTANCE;
	private ODatabaseObjectTx db;
	
	private DBConnection(){
		
		//create database
		try{
			db = new ODatabaseObjectTx("local:dbaa").create();
		}catch(Exception e){
     	   org.slf4j.LoggerFactory.getLogger(this.getClass()).warn("Error while creating database.",e.getMessage());
		}
		
		NodePro np = new NodePro();
		
		
		// OPEN THE DATABASE
		db = ODatabaseObjectPool.global().acquire("local:dbaa", "admin", "admin");

		db.getEntityManager().registerEntityClasses("scrape.it.persistence");
		
		db.save(np);
		
		/*
		db.registerHook(new ODocumentHookAbstract() {
			  public boolean onRecordBeforeCreate(ODocument iDocument) { 
				  
				    long timestamp = 
				    System.out.println("Current time stamp " + timestamp);
					iDocument.field("timestamp", timestamp); 
				    return true; 
				  } 
		})
		;
*/
	}
	
	
	
	
	public synchronized static DBConnection getInstance(){
		if (INSTANCE == null){
			INSTANCE = new DBConnection();			
		}
		return INSTANCE;
	}	
	
	public ODatabaseObjectTx getDB(){
		return this.db;
	}
}
