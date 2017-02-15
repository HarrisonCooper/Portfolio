/*
This class is the base frame for all the other graphs. It contains height and width constants and it also draws the standard x and y axis.
*/
package uk.ac.sheffield.aca15hpc;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BaseGraph extends JPanel {
	protected static final int WIDTH = 600;
	protected static final int HEIGHT = 400;
	protected static final int BORDER = 20;
	protected static final int HATCH_SIZE = 5;
	protected static final int NUM_X_VALUES = Observation.time.size();
	protected static final int PLOT_DIAMETER = 2;
	static Set<Double> unique = new HashSet<Double>(Observation.tempList);
	static List<Double> uniqueList = new ArrayList<Double>(unique);
	static Set<Integer> uniquePressure = new HashSet<Integer>(Observation.pressureList);
	static List<Integer> uniquePressureList = new ArrayList<Integer>(uniquePressure);
	static Set<Double> uniqueWind = new HashSet<Double>(Observation.windSpeedList);
	static List<Double> uniqueWindList = new ArrayList<Double>(uniqueWind);
	static Set<Double> uniqueGust = new HashSet<Double>(Observation.gustSpeedList);
	static List<Double> uniqueGustList = new ArrayList<Double>(uniqueGust);
	
	//This takes the unique values and sorts them into decending order.
	public static void makeArray(){
		Collections.sort(uniqueList);
		Collections.reverse(uniqueList);
		Collections.sort(uniquePressureList);
		Collections.reverse(uniquePressureList);
		Collections.sort(uniqueWindList);
		Collections.reverse(uniqueWindList);
		Collections.sort(uniqueGustList);
		Collections.reverse(uniqueGustList);
	}
	
	public static int getYLength(){
		int lengthY = (HEIGHT - BORDER) - BORDER;
		return lengthY;
	}

	public static int getXLength(){
		int lengthX = (WIDTH - BORDER) - BORDER;
		return lengthX;
	}
	
	//The spacing between the hatches for the x axis is the length / the number of times there is.
	public static int getGapX(){
		int gapX = (int) (getXLength() / (Observation.time.size()-1));
		return gapX;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		setBackground(Color.white);

		//y Axis
		g2.drawLine(BORDER, HEIGHT - BORDER, BORDER,  BORDER);
		//x Axis
		g2.drawLine(BORDER, HEIGHT - BORDER, WIDTH - BORDER, HEIGHT - BORDER);


		//hatchings for x axis
		for (int i = 1; i < NUM_X_VALUES; i++) {
			int x0 = BORDER + (i*getGapX());
			int x1 = x0;
			int x2 = BORDER + ((i*5)*getGapX());
			int y0 = HEIGHT - BORDER;
			int y1 = y0 - HATCH_SIZE;
			int y2 = y0 + HATCH_SIZE;
			g2.drawLine(x0, y0, x1, y1);
			
			//Ever 5th hatching is double the size. This indicates where the time is representing.
			if (i%5==0) {
				g2.drawLine(x0, y0, x1, y2);
			}
			
			//Every 5th hatching, the time is printed.
			try{
			String time = Observation.time.get(i*5);
			
			g2.setPaint(Color.black);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 8));
			g2.drawString(time, x2, (HEIGHT - 10));
			
			} catch (IndexOutOfBoundsException e) {
				
			}
			
		}
	}
	
	
	
}
