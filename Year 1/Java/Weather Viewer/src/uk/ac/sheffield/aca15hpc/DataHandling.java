/*
 * Takes the information from the URL and splits it into its different components.
 */
package uk.ac.sheffield.aca15hpc;

public class DataHandling {

	String fileInput = UrlReader.getData();

	public void handle(){
		//Adds each line to the ArrayList with the break tags
		String[] lines = fileInput.split("\n");
		for (String j : lines) {
			Observation.listWithBreak.add(j);
		}
		try{
			//Removes the break tags from the first ArrayList and places them
			//in another ArrayList
			for(int p=0; p<Observation.listWithBreak.size()-1; p++){
				String t = Observation.listWithBreak.get(p);
				String[] d = t.split("<br />");
				Observation.listNoBreak.add(p,d[0]);
				Observation.addObservations(p);
			}
		}
		//If the user selects a date for which there is no data, this error is produced in the terminal.
		catch (IndexOutOfBoundsException e) {
			System.out.println("There is no information for this date, please try again.");
			System.exit(1);
		}
	}
}
