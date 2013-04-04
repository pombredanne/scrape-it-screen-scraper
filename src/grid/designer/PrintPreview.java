/* 
 * Copyright (c) 2002, Cameron Zemek
 * 
 * This file is part of JSpread.
 * 
 * JSpread is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * JSpread is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package grid.designer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.print.*;
import javax.swing.*;

/**
 * @author <a href="grom@capsicumcorp.com">Cameron Zemek</a>
 */
public class PrintPreview extends JFrame {
	private JPanel viewPanel = new JPanel();

	private Dimension preferredSize = new Dimension(400, 500);

	public PrintPreview(final Printable target) {
		super("Print Preview");

		final PagePanel targetPanel = new PagePanel(target);
		JScrollPane printPreview = new JScrollPane(targetPanel);

		JButton prevPage = new JButton("<<");
		prevPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				targetPanel.pageIndex--;
				targetPanel.renderPage();
			}
		});
		JButton nextPage = new JButton(">>");
		nextPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				targetPanel.pageIndex++;
				targetPanel.renderPage();
			}
		});
        JButton print = new JButton("Print");
        print.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               final PrinterJob printJob = PrinterJob.getPrinterJob();
               PageFormat pf = printJob.defaultPage();
               printJob.setPrintable(target, pf);

               // Create print job
               Thread print = new Thread() {
                    public void run() {
                        try {
                            printJob.print();
                        } catch (final Exception e) {
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    JOptionPane.showMessageDialog(PrintPreview.this,
                                                               "An error occurred during printing.\n"
                                                                    + e.getMessage());
                                }
                            });
                        }
                    }
                };

               if (printJob.printDialog()) {
                   print.start();
               }
           }
        });

		Box navBox = new Box(BoxLayout.X_AXIS);
		navBox.add(prevPage);
		navBox.add(nextPage);
        navBox.add(print);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(printPreview, BorderLayout.CENTER);
		getContentPane().add(navBox, BorderLayout.SOUTH);
		pack();
	}

	public Dimension getPreferredSize() {
		return preferredSize;
	}

	private static class PagePanel extends JPanel {
		private Printable target;
		private JLabel previewImage;
		protected int pageIndex;

		public PagePanel(Printable target) {
			super();
			this.target = target;

			previewImage = new JLabel();
			renderPage();

			//layout panel
			setLayout(new BorderLayout());
			add(previewImage, BorderLayout.CENTER);
		}

		public void renderPage() {
			PrinterJob printerJob = PrinterJob.getPrinterJob();
			PageFormat pageFormat = printerJob.defaultPage();
			int m_wPage = (int) pageFormat.getWidth();
			int m_hPage = (int) pageFormat.getHeight();
			BufferedImage doubleBuffer =
				new BufferedImage(m_wPage, m_hPage, BufferedImage.TYPE_INT_RGB);            
			Graphics2D g2d = doubleBuffer.createGraphics();
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, m_wPage, m_hPage);            

			try {
				target.print(g2d, pageFormat, pageIndex);
			} catch (Exception e) {
				e.printStackTrace();
			}
            
			ImageIcon icon = new ImageIcon(doubleBuffer);
			previewImage.setIcon(icon);
            previewImage.validate();
            previewImage.repaint();
		}
	}
}
