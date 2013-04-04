package scrape.it.widgets.tree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import TurboActivate.TurboActivate;

import scrape.it.main.ClientGui;
import scrape.it.main.Global;

public class ProductActivation extends JMenuItem implements ActionListener {
	public ProductActivation(){
		super("Upgrade/Activate Product");
		addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	       try {
	    	   java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
               java.net.URI uri = new java.net.URI("http://scrape.it/plans-pricing");
               desktop.browse( uri );
           }
           catch ( Exception e ) {
        	   org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while opening desktop browser url",e);
        	   JOptionPane.showMessageDialog(ClientGui.frame, "Get a product activation key at http://scrape.it/plans-pricing");
           }

		String defaultKey;

		Object result = JOptionPane.showInputDialog(ClientGui.frame, "Enter Product Activation Key");
	    String enteredKey = (String) result;
	  
	    if(enteredKey != null && !enteredKey.isEmpty()){

			try
			{
				
				if (Global.isActivated)
		        {
		            // deactivate product without deleting the product key
		            // allows the user to easily reactivate
		            TurboActivate.Deactivate(true);
		            Global.isActivated = false;	
		        }
				
				
				
				
				boolean existingKey = TurboActivate.IsProductKeyValid();
				if ((existingKey && !TurboActivate.GetPKey().equals(enteredKey)) || !existingKey)
				{
					// save the new key
					if (!TurboActivate.CheckAndSavePKey(enteredKey)){
						throw new Exception("The product key is not valid.");
					}
				}

				// try to activate and close the form

				TurboActivate.Activate();
				JOptionPane.showMessageDialog(ClientGui.frame, "Product key successfully validated. Please restart the application to see the changes.");
	        	   org.slf4j.LoggerFactory.getLogger(this.getClass()).info("Product key has been activated successfully.");
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(ClientGui.frame, ex.getMessage());
	        	org.slf4j.LoggerFactory.getLogger(this.getClass()).error("Error while activating product key " + ex.getMessage(),ex);
			}
	    }

	}

}
