package scrape.it.server;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import scrape.it.main.MySplash;

public class tsetast {
    private static JLabel label1;

	public static void main(String[] args) {
		MySplash.getInstance().showSplash();

        JFrame frame = new JFrame();
        label1 = new JLabel("This is my app");
		frame.getContentPane().add(label1, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
 
        MySplash.getInstance().hideSplash();

    }
}