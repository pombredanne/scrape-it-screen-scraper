import java.awt.*;
import javax.swing.*;

import com.capsicumcorp.swing.spreadsheet.*;

/**
 * @author <a href="mailto:grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class TestSplitSpread {
	static public void main(String[] args) {
		JSpread spread = new JSpread(100, 25);
		
		JFrame frame = new JFrame("Split Spread");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(2, 2));
		contentPane.add(spread);
		contentPane.add(spread.cloneView());
		contentPane.add(spread.cloneView());
		contentPane.add(spread.cloneView());
		
		frame.setSize(640, 480);
		frame.show();
	}
}
