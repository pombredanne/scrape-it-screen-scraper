package scrape.it.widgets;

import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class FileMenu extends JMenuBar {

	FileMenu() {
    	JMenu fileMenu = new JMenu("File"); // Create File menu
        
        Component paintingChild = null;
		//add sub menus under File seciton
        //fileMenu.add(new JMenuItem(new ShowAction(paintingChild)));
       
        //add these menus
        //add(fileMenu);
        
    }
}
