package scrape.it.helper;

import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.jidesoft.dialog.AbstractDialogPage;

public class OfficeOptionPage1 extends AbstractDialogPage {

    public JRadioButton r11;
	public JRadioButton r12;

	public OfficeOptionPage1(String name) {
        super(name);
    }

    public OfficeOptionPage1(String name, Icon icon) {
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
       JLabel lab = new JLabel("Navigate Rules");
       r11 = new JRadioButton("Navigate By Clicking This Element");
       r12 = new JRadioButton("Navigate By Clicking Similar Elements");
        
        JPanel form = new JPanel();
        form.setLayout(gl);
        form.add(lab);
        form.add(r11);
        form.add(r12);
       
        

       add(form);

    }
}
