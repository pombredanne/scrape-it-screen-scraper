package scrape.it.helper;

import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.jidesoft.dialog.AbstractDialogPage;

public class OfficeOptionPage2 extends AbstractDialogPage {

    public OfficeOptionPage2(String name) {
        super(name);
    }

    public OfficeOptionPage2(String name, Icon icon) {
        super(name, icon);
    }

    public void lazyInitialize() {
        initComponents();
    }

    public void initComponents() {
    	GridLayout gl = new GridLayout(3,1);
    	gl.setHgap(1);
        setLayout(new GridLayout(3,1));
        //add(new JLabel("This is just a demo. \"" + getFullTitle() + "\" page is not implemented yet.", JLabel.CENTER), BorderLayout.CENTER);
       JLabel lab = new JLabel("Select Data To Save");
        JRadioButton jr1 = new JRadioButton("Navigate By Clicking This Element");
       JRadioButton jr2 = new JRadioButton("Navigate ");
        
        JPanel form = new JPanel();
        form.setLayout(gl);
        form.add(lab);
        form.add(jr1);
        form.add(jr2);
       
        

       add(form);

    }
}
