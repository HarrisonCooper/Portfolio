/*
Authored By: Harry Cooper
Info: The frame where all the graphs are displayed in a 2x2 grid.
*/
package uk.ac.sheffield.aca15hpc;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")

public class GraphFrame extends JFrame {

	public GraphFrame(){

		// add title and set size
		setTitle("Results From: " + UrlReader.getInfo());
		setSize(1200, 822);
		setLayout(new GridLayout(2,2));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// get reference to the content pane of this object,
		// create a drawing panel, and add to the content pane
		Container contentPane = getContentPane();
		BaseGraph baseGraph = new BaseGraph();
		baseGraph.makeArray();
		TemperatureGraph temp = new TemperatureGraph();
		PressureGraph pressure = new PressureGraph();
		WindGraph wind = new WindGraph();
		OutputText text = new OutputText();
		contentPane.add(temp);
		contentPane.add(pressure);
		contentPane.add(wind);
		contentPane.add(text);
	}
}