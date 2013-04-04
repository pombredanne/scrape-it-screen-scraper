package scrape.it.widgets.table;

import javax.swing.JTable;

public class Table extends JTable{

	private static Table INSTANCE;
	private TableModel tm;
	
	public Table(TableModel tableModel){
		tm = tableModel;
	}
	
	public synchronized static Table getInstance(){
		if (INSTANCE == null){
			INSTANCE = new Table(TableModel.getInstance());			
		}
		return INSTANCE;
	}
}
