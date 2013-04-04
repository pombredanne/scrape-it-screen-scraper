package scrape.it.main;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

class MySimpleSplash extends JWindow {
  private int duration;

  public MySimpleSplash(int d) {
    duration = d;

    JPanel content = (JPanel) getContentPane();
    content.setBackground(Color.white);
    int width = 450;
    int height = 115;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width - width) / 2;
    int y = (screen.height - height) / 2;
    setBounds(x, y, width, height);

    content.add(new JLabel("[Insert Splash Gif]...."), BorderLayout.CENTER);
    Color oraRed = new Color(156, 20, 20, 255);
    content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

    setVisible(true);
    try {
      Thread.sleep(duration);
    } catch (Exception e) {
    }
    setVisible(false);
  }

}
