/*
 * This class draws the contents of the Temperature graph by taking their position in the array list and * by the gap distance.
 */
package uk.ac.sheffield.aca15hpc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Collections;

@SuppressWarnings("serial")

public class TemperatureGraph extends BaseGraph {

	public static double getMaxY(){
		double maxY = Collections.max(Observation.tempList);
		return maxY;
	}

	public static double getMinY(){
		double minY = Collections.min(Observation.tempList);
		return minY;
	}

	public static double getDiff(){
		double diff = getMaxY() - getMinY();
		return diff;
	}

	public static int getGap(){
		int gap = (int) (getYLength() / unique.size());
		return gap;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		//Graph Title
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g2.drawString("Temperature Graph °C", WIDTH/3, 10);

		//hatchings for y axis
		for (int i = 0; i < unique.size(); i++){
			int x0 = BORDER;
			int x1 = HATCH_SIZE + BORDER;
			int y0 = BORDER + (i*getGap());
			int y1 = y0;
			g2.drawLine(x0, y0, x1, y1);

			String temp = String.valueOf(uniqueList.get(i));

			g2.setPaint(Color.black);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 8));
			g2.drawString(temp, 5, y0);

		}

		//plotting the points
		//get the temp values from temp arrayList
		//find index of that value in the uniqueList, this is the effective y position
		//multiply the index by getGap() and + BORDER
		//x position is the (getGapX() + BORDER) * indexOf the temp
		for (int i = 1; i < Observation.temp.size(); i ++) {

			int x = (BORDER + (getGapX()*i));
			int y = (BORDER + (getGap()*uniqueList.indexOf(Double.parseDouble(Observation.temp.get(i)))));

			g2.setPaint(Color.red);
			g2.draw(new Ellipse2D.Double(x, y,PLOT_DIAMETER,PLOT_DIAMETER));

		}
	}
}