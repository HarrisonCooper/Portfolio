/*
 * Standard frame for which the other frames are based off. Sets size and position and also the exit button.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

@SuppressWarnings("serial")

public class SecretaryFrame extends JFrame{

	public SecretaryFrame() {
		// A Toolkit lets us retrieve system information.
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Dimension screenDimensions = toolkit.getScreenSize();
				setLayout(new GridLayout(0, 1, 0, 0));
				int height = (screenDimensions.height/2)+100;
				setSize(screenDimensions.width/2, height);		
				setLocation(new Point(screenDimensions.width/4, screenDimensions.height/4));
				setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}





