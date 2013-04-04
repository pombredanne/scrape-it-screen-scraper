import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class RowHeightDialog extends JDialog{
    
    public RowHeightDialog(java.awt.Frame parent, JTable table) {
        super(parent, true);
        
        //Dialog Close event
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog();
            }
        });
        
        //Setup dialog
        setTitle("Row Height");
        
        RowHeightPanel rowHeightPanel = new RowHeightPanel(table);
        
        getContentPane().add(rowHeightPanel, BorderLayout.CENTER);
        setResizable(false);
        pack();
    }
    
    private void closeDialog() {
        setVisible(false);
        dispose();
    }
    
    class RowHeightPanel extends JPanel {
        JTextField rowHeight;
        JTable table;
        
        RowHeightPanel(final JTable table){
            setLayout(new GridLayout(2,1));
            setBorder(BorderFactory.createTitledBorder("Row Height"));
            this.table = table;
            rowHeight = new JTextField();
            rowHeight.setHorizontalAlignment(SwingConstants.RIGHT);
            JButton b_apply = new JButton("Apply");
            b_apply.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    setRowHeight();
                }
            });
            add(rowHeight);
            add(b_apply);
        }
        
        private void setRowHeight(){
            int row = table.getSelectedRow();
            table.setRowHeight(row, Integer.valueOf(rowHeight.getText()).intValue());
            table.clearSelection();
            table.revalidate();
            table.repaint();
        }
    }
}