package scrape.it.widgets.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.teamdev.jxbrowser.BrowserServices;
import com.teamdev.jxbrowser.proxy.AuthenticationHandler;
import com.teamdev.jxbrowser.proxy.ProxyConfig;
import com.teamdev.jxbrowser.proxy.ProxyServer;
import com.teamdev.jxbrowser.proxy.ProxyServerLogin;
import com.teamdev.jxbrowser.proxy.ServerType;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;

public class MasterProxy extends JMenuItem implements ActionListener {
	
	private String proxy ="";
	private String password;
	private String userName;
	public MasterProxy(){
		super("Set Master Proxy");
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {

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

}
