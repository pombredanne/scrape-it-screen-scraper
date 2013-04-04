import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import org.jeppers.swing.spreadsheet.*;

public class BorderDialog extends JDialog{
    
    public BorderDialog(java.awt.Frame parent, JTable table, DefaultAttributeModel model) {
        super(parent, true);
        
        //Dialog Close event
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog();
            }
        });
        
        //Setup dialog
        setTitle("Border");
        
        ThicknessPanel thicknessPanel = new ThicknessPanel();
        BorderPanel borderPanel = new BorderPanel(table, thicknessPanel, model);
        Box box = new Box(BoxLayout.Y_AXIS);
        box.add(thicknessPanel);
        box.add(borderPanel);
        
        getContentPane().add(box, BorderLayout.CENTER);
        setResizable(false);
        pack();
    }
    
    private void closeDialog() {
        setVisible(false);
        dispose();
    }
    
    class ThicknessPanel extends JPanel {
        final JComboBox[] combos;
        
        ThicknessPanel() {
            String[] str = {"top","left","bottom","right"};
            int n = str.length;
            setLayout(new GridLayout(n,2));
            combos = new JComboBox[n];
            for (int i=0;i<n;i++) {
                combos[i] = new JComboBox(new Object[]{"0","1","2","3"});
                add(new JLabel(str[i]));
                add(combos[i]);
            }
        }
        
        public Insets getThickness() {
            Insets insets = new Insets(0,0,0,0);
            insets.top    = combos[0].getSelectedIndex();
            insets.left   = combos[1].getSelectedIndex();
            insets.bottom = combos[2].getSelectedIndex();
            insets.right  = combos[3].getSelectedIndex();
            return insets;
        }
    }
    
    
    class BorderPanel extends JPanel {
        JTable table;
        DefaultAttributeModel model;
        ThicknessPanel thicknessPanel;
        
        BorderPanel(JTable table, final ThicknessPanel thicknessPanel, final DefaultAttributeModel model) {
            this.table = table;
            this.model = model;
            this.thicknessPanel = thicknessPanel;
            
            // Create buttons
            JButton b_apply = new JButton("Apply");
            JButton b_cancel  = new JButton("Cancel");
            b_apply.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    setCellBorder();
                    closeDialog();
                }
            });
            b_cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    closeDialog();
                }
            });
            
            setLayout(new GridLayout(1,2));
            add(b_cancel);
            add(b_apply);
        }
        
        private void setCellBorder() {
            Insets insets = thicknessPanel.getThickness();
            int[] columns = table.getSelectedColumns();
            int[] rows    = table.getSelectedRows();
            int rowMax    = rows.length;
            int columnMax = columns.length;
            java.awt.Color color = new Color(0,0,0);
            
            for (int i=0;i<rowMax;i++) {
                int row = rows[i];
                for (int j=0;j<columnMax;j++) {
                    int column = columns[j];                                          
                    LinesBorder border = new LinesBorder(color, insets);
                    // Set the cell border
                    model.setBorder(row, column, border);
                }
            }
        }
    }
}
