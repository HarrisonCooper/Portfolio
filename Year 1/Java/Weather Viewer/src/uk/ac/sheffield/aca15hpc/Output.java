/*
 * This class returns the data for the 4th quadrant of the graph frame, so all the text values.
 */
package uk.ac.sheffield.aca15hpc;

public class Output {

	String dateWithTime = Observation.date.get(1);
	String date = dateWithTime.substring(0, dateWithTime.indexOf(" "));

	//Returns the total precipitation by iterating through the precipitation list and adding the values to 0.
	public static String getTotalPercipitation() {
		Double correctedPercipitation = 0.0;
		for (int a = 1; a<Observation.time.size(); a++){
			try {
				correctedPercipitation = correctedPercipitation + Double.parseDouble(Observation.percipitation.get(a));		
			} catch (NumberFormatException e) {

			}
		}
		return Double.toString(correctedPercipitation);
	}

	//Returns the average temperature by adding all the temperature values and dividing by the number of temperature values.
	public static String getAverageTemperature() {
		double averageTemperature = 0;
		for (int h = 1; h<Observation.temp.size(); h++) {
			averageTemperature = averageTemperature + Double.parseDouble(Observation.temp.get(h));
		}
		averageTemperature = averageTemperature / Observation.temp.size();
		return Double.toString(averageTemperature);
	}

	//Returns the pressure trend by seeing the difference between the first and the last pressure values and determinging whether they rise or fall.
	public static String getPressureTrend() {
		int firstPressureValue = 0;
		int lastPressureValue = 0;
		firstPressureValue = Integer.parseInt((Observation.pressure.get(1)));
		lastPressureValue = Integer.parseInt((Observation.pressure.get(Observation.pressure.size()-1)));

		if (firstPressureValue > lastPressureValue){
			return ("pressure fall from " + firstPressureValue + " to " + lastPressureValue + " hPa");
		} else if (firstPressureValue < lastPressureValue){
			return ("pressure rise from " + firstPressureValue + " to " + lastPressureValue + " hPa");	
		} else {
			return ("Pressure stayed the same");	
		}
	}
}
