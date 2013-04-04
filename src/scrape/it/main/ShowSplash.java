package scrape.it.main;

import java.awt.*;
import javax.swing.border.*;
import javax.swing.*;

public final class ShowSplash extends JWindow
{
   private JLabel lblVersion = new JLabel();

   public ShowSplash()
   {
      init();
      center();
   }

   private void init()
   {
      JPanel    pnlImage   = new JPanel();

      ImageIcon image   = new ImageIcon("splash.gif");
    
      JLabel    lblBack = new JLabel( image );
      Border    raisedbevel  = BorderFactory.createRaisedBevelBorder();
      Border    loweredbevel = BorderFactory.createLoweredBevelBorder();

      lblBack.setBounds( 0, 0, image.getIconWidth(), image.getIconHeight() );
      getLayeredPane().add( lblBack, new Integer( Integer.MIN_VALUE ) );

      pnlImage.setLayout( null );
      pnlImage.setOpaque( false );
      pnlImage.setBorder( BorderFactory.createCompoundBorder( raisedbevel, loweredbevel ) );

      pnlImage.add( this.lblVersion );

      this.lblVersion.setForeground( Color.white );
      this.lblVersion.setFont( new Font( "Dialog", Font.PLAIN, 12 ) );
      this.lblVersion.setBounds( 15, 69, 120, 20 );

      setContentPane( pnlImage );
      setSize( image.getIconWidth(), image.getIconHeight() );
   }
   
   private void center()
   {
      Dimension scr = Toolkit.getDefaultToolkit().getScreenSize();
      int       nX  = (int) (scr.getWidth()  - getWidth()  ) / 2;
      int       nY  = (int) (scr.getHeight() - getHeight() ) / 2;

      setLocation( nX, nY );
   }
}
