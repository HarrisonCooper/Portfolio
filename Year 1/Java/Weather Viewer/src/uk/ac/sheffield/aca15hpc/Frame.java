/*
 * Standard frame for which the other frames are based off. Sets size and position and also the exit button.
 */
package uk.ac.sheffield.aca15hpc;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

@SuppressWarnings("serial")

public class Frame extends JFrame{

	public Frame() {
		// A Toolkit lets us retrieve system information.
				Toolkit toolkit = Toolkit.getDefaultToolkit();
				Dimension screenDimensions = toolkit.getScreenSize();
				setSize(screenDimensions.width/2, screenDimensions.height/2);		
				setLocation(new Point(screenDimensions.width/4, screenDimensions.height/4));
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
