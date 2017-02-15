/*
 * The partner frame for which the other partner frames are based off. Sets size to full screen.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

@SuppressWarnings("serial")

public class PartnerFrame extends JFrame{

	public PartnerFrame() {
		// A Toolkit lets us retrieve system information.
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Dimension screenDimensions = toolkit.getScreenSize();
				setSize(screenDimensions.width/2, screenDimensions.height);		
				setLocation(new Point(screenDimensions.width, screenDimensions.height/4));
				setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}





