/*
 * Main class. Opens the user input GUI and also takes the data and paasses it on to the other classes for it to be corrected into a suitable form
 */
package uk.ac.sheffield.aca15hpc;

public class Weather {
	public static void main(String[] args) {
		ComboGUI Combo = new ComboGUI();
		Combo.setVisible(true);		
	}
	
	public void run(){
		DataHandling dataHandling = new DataHandling();
		dataHandling.handle();
		Observation.correctTemp();
		
		GraphFrame mainFrame = new GraphFrame();
		mainFrame.setVisible(true);
	}
}
