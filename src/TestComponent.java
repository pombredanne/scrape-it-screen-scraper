import java.awt.*;
import javax.swing.*;

import net.sf.jeppers.grid.*;

/**
 * @author Cameron Zemek
 */
public class TestComponent extends JFrame {
	/** Creates new TestComponent */
	public TestComponent() {
		super("Grid Test");

		//Quit this app when the main window closes
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JGrid grid = new JGrid();
		JScrollGrid scrollGrid = new JScrollGrid(grid);

		getContentPane().add(scrollGrid, BorderLayout.CENTER);
		pack();
		setSize(640, 480);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		TestComponent test = new TestComponent();
		test.show();
	}
}
