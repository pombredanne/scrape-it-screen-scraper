package scrape.it.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class MySplashScreen extends JWindow {
  private int duration;

  public MySplashScreen(int d) {
    duration = d;
  }

  // A simple little method to show a title screen in the center
  // of the screen for the amount of time given in the constructor
  public void showSplash() {

    // Wait a little while, maybe while loading resources


  }

  public void showSplashAndExit() {
    showSplash();

  }

  public static void main(String[] args) {
    // Throw a nice little title page up on the screen first
    MySplashScreen splash = new MySplashScreen(10000);
    // Normally, we'd call splash.showSplash() and get on with the program.
    // But, since this is only a test...
    splash.showSplashAndExit();
  }
}
