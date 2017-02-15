/*
 * This class is the fourth quadrant of the graph frame. It displays the average temperature, the pressure trend, and the total precipitation.
 */
package uk.ac.sheffield.aca15hpc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class OutputText extends JPanel{

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		setBackground(Color.white);
		
		g2.setPaint(Color.black);
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		
		g2.drawString("The average temperature is: "+ Output.getAverageTemperature() +"°C", 100, 50);
		g2.drawString("The pressure trend is: " + Output.getPressureTrend(), 100, 150);
		g2.drawString("The total percipitation is: "+ Output.getTotalPercipitation() +"mm", 100, 250);
	}
}
