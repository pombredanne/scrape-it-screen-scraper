package scrape.it.widgets.table;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel implements TableModelListener{

	private static TableModel INSTANCE;
	private static Object tabm;
	
	public TableModel(int row, int col){
		super(row,col);
	}
	
	public synchronized static TableModel getInstance(){
		if (INSTANCE == null){
			INSTANCE = new TableModel(0,0);			
		}
		return INSTANCE;
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}

}
