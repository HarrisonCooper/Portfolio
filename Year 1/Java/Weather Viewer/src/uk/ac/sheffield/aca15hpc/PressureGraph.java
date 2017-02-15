/*
 * This class draws the contents of the Pressure graph by taking their position in the array list and * by the gap distance.
 */
package uk.ac.sheffield.aca15hpc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Collections;

@SuppressWarnings("serial")
public class PressureGraph extends BaseGraph {

	public static int getMaxY(){
		int maxY = Collections.max(Observation.pressureList);
		return maxY;
	}

	public static int getMinY(){
		int minY = Collections.min(Observation.pressureList);
		return minY;
	}

	public static int getDiff(){
		int diff = getMaxY() - getMinY();
		return diff;
	}

	public static int getGap(){
		int gap = (int) (getYLength() / uniquePressure.size());
		return gap;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		//Graph Title
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g2.drawString("Pressure Graph hPa", WIDTH/3, 10);

		//hatchings for y axis
		for (int i = 0; i < uniquePressure.size(); i++){
			int x0 = BORDER;
			int x1 = HATCH_SIZE + BORDER;
			int y0 = BORDER + (i*getGap());
			int y1 = y0;
			g2.drawLine(x0, y0, x1, y1);

			//Values that go on the y axis.
			String temp = String.valueOf(uniquePressureList.get(i));
			g2.setPaint(Color.black);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 8));
			g2.drawString(temp, 0, y0);

		}

		//plotting the points
		//get the pressure values from pressure arrayList
		//find index of that value in the uniquePressureList, this is the effective y position
		//multiply the index by getGap() and + BORDER
		//x position is the (getGapX() + BORDER) * indexOf the pressure

		for (int i = 1; i < Observation.pressure.size(); i ++) {

			int x = (BORDER + (getGapX()*i));
			int y = (BORDER + (getGap()*uniquePressureList.indexOf(Integer.parseInt(Observation.pressure.get(i)))));

			g2.setPaint(Color.blue);
			g2.draw(new Ellipse2D.Double(x, y,PLOT_DIAMETER,PLOT_DIAMETER));

		}
	}
	
}
