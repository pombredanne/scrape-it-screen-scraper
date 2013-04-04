package scrape.it.widgets.tree;

import grid.designer.SpreadsheetDesigner;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import net.sf.jeppers.grid.GridModelEvent;

import com.capsicumcorp.swing.spreadsheet.JSpread;

public class Resume extends JMenuItem implements ActionListener {
	
	private JSpread existingSheet;

	public Resume(){
		super("testAction");
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JSpread sheet1 = new JSpread(5, 3);
		SpreadsheetDesigner.workbook.addSheet("dog", sheet1);
		SpreadsheetDesigner.workbook.focus(sheet1);
		
		if(sheet1 != null){
			//SpreadsheetDesigner.workbook.focus(existingSheet);
			final JSpread existingSheet1 = SpreadsheetDesigner.workbook.getSheet("dog");		
			//existingSheet.insertColumns(0, 1);
			//existingSheet1.insertRows(0, 1);

					
						
						Thread t = new Thread(){
							public void run(){
								for(int i =1; i <1255;i++){
								existingSheet1.insertRows(i, 1);
								
								existingSheet1.revalidate();
								existingSheet1.setValueAt("string", i, 0);	

								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									org.slf4j.LoggerFactory.getLogger(this.getClass()).error("error while resuming", e);
								}
								//existingSheet1.repaint();
								}
							}
						};
						t.start();
					
					
		

		}

	}

	private void writeRow(final int i) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {

				//existingSheet.getGrid().gridChanged();
				
				
			}
		});
		
	}

}
