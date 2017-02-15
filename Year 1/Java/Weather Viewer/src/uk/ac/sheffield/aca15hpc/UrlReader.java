/*
 * This class takes the user input and passes it into a URL to retrieve the data for that date.
 */
package uk.ac.sheffield.aca15hpc;

import java.net.*;
import java.io.*;

public class UrlReader {
	
	private static String airport = "";
	private static String day = "";
	private static int month;
	private static String year = "";
	
	public UrlReader(String a, String d, int m, String y){
		airport = a;
		day = d;
		month = m+1;
		year = y;
		
		// Sets the month to the correct 4 letter abreviation for the URL.
		if (airport == "London Heathrow") {
			airport = "EGLL";
		} else if (airport == "London Gatwick") {
			airport = "EGKK";
		} 
	}
	
	public static String getInfo(){
		return airport +" on the: "+ day +"/"+ month +"/"+ year;
	}
	
	public static String getData(){	
		try {
		URL wunderground = new URL("https://www.wunderground.com/history/airport/"+ airport +"/"+ year +"/"+ month +"/"+ day +"/DailyHistory.html?HideSpecis=1&format=1");
		URLConnection yc = wunderground.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(
				yc.getInputStream()));
		String inputLine;
		inputLine = in.readLine();
		String full = "";
		//Adds the contents of the url to a string line by line
		while ((inputLine = in.readLine()) != null){
			full += inputLine + "\n";
		}
		in.close();
		
		return full;
		}
		catch (IOException e) {
			e.printStackTrace();
			return "error: " + e.getMessage();
		}
		
	}
}
