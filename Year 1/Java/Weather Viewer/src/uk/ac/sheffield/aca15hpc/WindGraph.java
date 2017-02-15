/*
 * This class draws the contents of the Wind graph by taking their position in the array list and * by the gap distance.
 *There are 2 y axis, one for the Wind speed and the other for the Gust speed. This allows for better gaps between the values.
 */
package uk.ac.sheffield.aca15hpc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.Collections;

@SuppressWarnings("serial")
public class WindGraph extends BaseGraph {

	public static double getMaxY(){
		double maxYW = Collections.max(Observation.windSpeedList);
		double maxYG = Collections.max(Observation.gustSpeedList);
		if (maxYW > maxYG){
			double maxY = maxYW;
			return maxY;
		} else {
			double maxY = maxYG;
			return maxY;
		}
		
	}

	public static double getMinY(){
		double minYW = Collections.min(Observation.windSpeedList);
		double minYG = Collections.min(Observation.gustSpeedList);
		if (minYW > minYG){
			double minY = minYG;
			return minY;
		} else {
			double minY = minYW;
			return minY;
		}
		
	}

	public static double getDiff(){
		double diff = getMaxY() - getMinY();
		return diff;
	}

	public static int getGap(){
		int gap = (int) (getYLength() / uniqueWind.size());
		return gap;
	}
	public static int getGap2(){
		int gap2 = (int) (getYLength() / uniqueGust.size());
		return gap2;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
		//y axis 2
		g2.drawLine(WIDTH-BORDER, HEIGHT - BORDER, WIDTH-BORDER,  BORDER);
		
		//Graph Title
		g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
		g2.drawString("Wind Graph Km/h : Wind Speed (Magenta) : Gust Speed (Dark Grey)", WIDTH/5, 10);

		//hatchings for y axis
		for (int i = 0; i < uniqueWind.size(); i++){
			int x0 = BORDER;
			int x1 = HATCH_SIZE + BORDER;
			int y0 = BORDER + (i*getGap());
			int y1 = y0;
			g2.setPaint(Color.black);
			g2.drawLine(x0, y0, x1, y1);

			String temp = String.valueOf(uniqueWindList.get(i));

			g2.setPaint(Color.magenta);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 8));
			g2.drawString(temp, 0, y0);

		}
		for (int i = 0; i < uniqueGust.size(); i++){
			int x0 = WIDTH-BORDER;
			int x1 = (WIDTH-BORDER) - HATCH_SIZE;
			int y0 = BORDER + (i*getGap2());
			int y1 = y0;
			g2.setPaint(Color.black);
			g2.drawLine(x0, y0, x1, y1);
			
			String temp = String.valueOf(uniqueGustList.get(i));

			g2.setPaint(Color.darkGray);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 8));
			g2.drawString(temp, (WIDTH-BORDER)+5, y0);

		}

		//plotting the points
		//get the temp values from temp arrayList
		//find index of that value in the uniqueList, this is the effective y position
		//multiply the index by getGap() and + BORDER
		//x position is the (getGapX() + BORDER) * indexOf the temp

		for (int i = 1; i < Observation.windSpeed.size(); i ++) {

			int x = (BORDER + (getGapX()*i));
			int y = (BORDER + (getGap()*uniqueWindList.indexOf(Double.parseDouble(Observation.windSpeed.get(i)))));

			g2.setPaint(Color.magenta);
			g2.draw(new Ellipse2D.Double(x, y,PLOT_DIAMETER,PLOT_DIAMETER));

		}
		for (int i = 1; i < Observation.gustSpeed.size(); i ++) {

			int x = (BORDER + (getGapX()*i));
			int y = (BORDER + (getGap2()*uniqueGustList.indexOf(Double.parseDouble(Observation.gustSpeed.get(i)))));
					
			g2.setPaint(Color.darkGray);
			g2.draw(new Ellipse2D.Double(x, y,PLOT_DIAMETER,PLOT_DIAMETER));

		}
	}
	
}
