import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import org.jeppers.swing.spreadsheet.*;

public class FontDialog extends JDialog{
    
    public FontDialog(java.awt.Frame parent, JTable table, DefaultAttributeModel model) {
        super(parent, true);
        
        //Dialog Close event
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog();
            }
        });
        
        //Setup dialog
        setTitle("Font");
        
        FontPanel fontPanel = new FontPanel(table, model);
                
        getContentPane().add(fontPanel, BorderLayout.CENTER);
        setResizable(false);
        pack();
    }
    
    private void closeDialog() {
        setVisible(false);
        dispose();
    }
    
    class FontPanel extends JPanel {
        String[] str_size  = {"10","12","14","16","20"};
        String[] str_style = {"PLAIN","BOLD","ITALIC"};
        JComboBox name,style,size;
        
        FontPanel(final JTable table, final DefaultAttributeModel model) {
            setLayout(new BorderLayout());
            setBorder(BorderFactory.createTitledBorder("Font"));
            Box box = new Box(BoxLayout.X_AXIS);
            JPanel p2 = new JPanel(new GridLayout(3,1));
            JPanel p3 = new JPanel(new GridLayout(3,1));
            JPanel p4 = new JPanel(new GridLayout(1,2));
            p2.add(new JLabel("Name:"));
            p2.add(new JLabel("Style:"));
            p2.add(new JLabel("Size:"));

            // Get list of available fonts    
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            name = new JComboBox(ge.getAvailableFontFamilyNames());
            
            style = new JComboBox(str_style);
            size  = new JComboBox(str_size);
            size.setEditable(true);
            JButton b_cancel  = new JButton("Cancel");
            b_cancel.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    // close dialog
                    closeDialog();
                }
            });
            
            JButton b_apply   = new JButton("Apply");
            b_apply.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int[] columns = table.getSelectedColumns();
                    int[] rows    = table.getSelectedRows();
                    if ((rows == null) || (columns == null)) return;
                    if ((rows.length<1)||(columns.length<1)) return;
                    Font font = new Font((String)name.getSelectedItem(),
                    style.getSelectedIndex(),
                    Integer.parseInt((String)size.getSelectedItem()));
                    
                    for(int i=0; i<rows.length; i++){
                        int row = rows[i];
                        for(int j=0; j<columns.length; j++){
                            int column = columns[j];
                            model.setFont(row, column, font);
                        }
                    }
                    
                    // Close dialog
                    closeDialog();
                }
            });
            p3.add(name);
            p3.add(style);
            p3.add(size);
            p4.add(b_cancel);
            p4.add(b_apply);
            box.add(p2);
            box.add(p3);
            add(BorderLayout.CENTER,box);
            add(BorderLayout.SOUTH, p4);
        }
    }
}
