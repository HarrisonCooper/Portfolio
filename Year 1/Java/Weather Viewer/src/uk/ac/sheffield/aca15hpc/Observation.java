/*
 * This class contains all the ArrayLists used and also some of the data corretion code.
 */
package uk.ac.sheffield.aca15hpc;
import java.util.*;

public class Observation{
	public static ArrayList<String> listWithBreak = new ArrayList<String>();
	public static ArrayList<String> listNoBreak = new ArrayList<String>();
	public static ArrayList<String> time = new ArrayList<String>();
	public static ArrayList<String> temp = new ArrayList<String>();
	public static ArrayList<String> pressure = new ArrayList<String>();
	public static ArrayList<String> windDirectionCompass = new ArrayList<String>();
	public static ArrayList<String> windDirectionBearing = new ArrayList<String>();
	public static ArrayList<String> windSpeed = new ArrayList<String>();
	public static ArrayList<String> gustSpeed = new ArrayList<String>();
	public static ArrayList<String> percipitation = new ArrayList<String>();
	public static ArrayList<String> dewPoint = new ArrayList<String>();
	public static ArrayList<String> humidity = new ArrayList<String>();
	public static ArrayList<String> visibility = new ArrayList<String>();
	public static ArrayList<String> events = new ArrayList<String>();
	public static ArrayList<String> conditions = new ArrayList<String>();
	public static ArrayList<String> date = new ArrayList<String>();
	public static ArrayList<Double> tempList = new ArrayList<Double>();
	public static ArrayList<Integer> pressureList = new ArrayList<Integer>();
	public static ArrayList<Double> windSpeedList = new ArrayList<Double>();
	public static ArrayList<Double> gustSpeedList = new ArrayList<Double>();

	public static void addObservations(int p) {
		String s = Observation.listNoBreak.get(p);

		String[] array = s.split(",");
		time.add(p, array[0]);
		temp.add(p, array[1]);
		pressure.add(p, array[4]);
		windDirectionCompass.add(p, array[6]);
		windDirectionBearing.add(p, array[12]);
		windSpeed.add(p, array[7]);
		gustSpeed.add(p, array[8]);
		percipitation.add(p, array[9]);
		dewPoint.add(p, array[2]);
		humidity.add(p, array[3]);
		visibility.add(p, array[5]);
		events.add(p, array[10]);
		conditions.add(p, array[11]);
		date.add(p, array[13]);
	}

	public static void correctTemp() {
		for (int i = 1; i < temp.size(); ++i) { 
			tempList.add(Double.parseDouble(temp.get(i))); //store each element as a double in the array
		}
		for (int i = 1; i < pressure.size(); ++i) { 
			pressureList.add(Integer.parseInt(pressure.get(i))); //store each element as an Integer in the array
		}
		//if theres a negative 0 value, it's corrected to just 0.
		for (int i = 0; i< tempList.size(); i++){
			if (tempList.get(i).equals(-0.0)){
				tempList.set(i, 0.0);
			}
		}
		for (int i = 1; i < windSpeed.size(); i++){
			windSpeedList.add(Double.parseDouble(windSpeed.get(i)));//store each element as a double in the array
		}
		//If theres non-double data,  its corrected to displaying just 0.
		for (int i = 0; i< gustSpeed.size(); i++){
			if (gustSpeed.get(i).equals("-")){
				gustSpeed.set(i, "0.0");
			}
			if (gustSpeed.get(i).equals("")){
				gustSpeed.set(i, "0.0");
			}
		}
		for (int i = 1; i < gustSpeed.size(); i++){
			gustSpeedList.add(Double.parseDouble(gustSpeed.get(i)));
		}
	}

}
