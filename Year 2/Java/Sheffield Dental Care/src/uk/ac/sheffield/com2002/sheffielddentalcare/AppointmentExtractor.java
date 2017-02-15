/*
 * Takes the appointment times and names from the database, and formats them into a
 * way the calendar class can read them in.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class AppointmentExtractor {
	String date;
	
	ArrayList<String> appointmentTimeDentist = new ArrayList<String>();
	ArrayList<String> appointmentTimeHygienist = new ArrayList<String>();
	ArrayList<String> patientForenamesDentist = new ArrayList<String>();
	ArrayList<String> patientForenamesHygienist = new ArrayList<String>();

	private SearchPanel search = new SearchPanel();
	Queries dbQueries= new Queries();
	
	public AppointmentExtractor() throws SQLException {
		appointmentFormatter();
		forenameExtractor();
	
	}
	int extractYear(){
		String[] all = date.split("-");
		return Integer.parseInt(all [0]);
	}
	int extractMonthNum(){
		String[] all = date.split("-");
		int monthNum = Integer.parseInt(all [1]);
		return monthNum;
	}
	String extractMonth(){
		String[] all = date.split("-");
		int monthNum = Integer.parseInt(all [1]);
		String month = null;
		switch (monthNum) {
        case 1:  month = "Jan";
                 break;
        case 2:  month = "Feb";
                 break;
        case 3:  month = "Mar";
                 break;
        case 4:  month = "Apr";
                 break;
        case 5:  month = "May";
                 break;
        case 6:  month = "Jun";
                 break;
        case 7:  month = "Jul";
                 break;
        case 8:  month = "Aug";
                 break;
        case 9:  month = "Sep";
                 break;
        case 10: month = "Oct";
                 break;
        case 11: month = "Nov";
                 break;
        case 12: month = "Dec";
                 break;
    }
	return month;	
	}
	int extractDay(){
		String[] all = date.split("-");
		return Integer.parseInt(all[2]);
	}
	
	String extractDayWord(){
		Calendar cal = Calendar.getInstance();
		cal.set(extractYear(), extractMonthNum()-1, extractDay());
		int dayNum = cal.getTime().getDay();
		//DateTime temp;
		String dayString = null;
		switch (dayNum){
		case 1: dayString = "Mon";
		break;
		case 2: dayString = "Tue";
		break;
		case 3: dayString = "Wed";
		break;
		case 4: dayString = "Thu";
		break;
		case 5: dayString = "Fri";
		break;
		case 6: dayString = "Sat";
		break;
		case 0: dayString = "Sun";
		break;
		}
		
		return dayString;
	} 
	
	void forenameExtractor() throws SQLException{
		ResultSet appPatIdDentist = dbQueries.getAppPatId(1);
		ResultSet appPatIdHygienist = dbQueries.getAppPatId(2);
		while(appPatIdDentist.next()) {
			int pat_id = appPatIdDentist.getInt(1);
			ResultSet appAccIdDentist = dbQueries.getAppAccId(pat_id);
			while(appAccIdDentist.next()){
				int acc_id = appAccIdDentist.getInt(1); 
				ResultSet appForenames = dbQueries.getPatForename(acc_id);
				while(appForenames.next()){
					String forename = appForenames.getString(1);
					patientForenamesDentist.add(forename);
				}
				appForenames.close();
			}
			appAccIdDentist.close();
		}
		appPatIdDentist.close();
		
		
		while(appPatIdHygienist.next()) {
			int pat_id = appPatIdHygienist.getInt(1);
			ResultSet appAccIdHygienist = dbQueries.getAppAccId(pat_id);
			while(appAccIdHygienist.next()){
				int acc_id = appAccIdHygienist.getInt(1); 
				ResultSet appForenames = dbQueries.getPatForename(acc_id);
				while(appForenames.next()){
					String forename = appForenames.getString(1);
					patientForenamesHygienist.add(forename+ "'s appointment");
				}
				appForenames.close();
			}
			appAccIdHygienist.close();
		}
		appPatIdHygienist.close();
		//System.out.println(patientForenamesDentist);
		//System.out.println(patientForenamesHygienist);
	}
	
	void appointmentFormatter() throws SQLException{
		ResultSet rsDentist = dbQueries.getAppointmentsCal(1);
		ResultSet rsHygienist = dbQueries.getAppointmentsCal(2);
		while(rsDentist.next()){
			String date = rsDentist.getString("date");
			this.date = date;
			String start_time = rsDentist.getString("start_time");
			String end_time = rsDentist.getString("end_time");
			String[] dateArray = date.split("-");
			String string = extractDayWord() + " " + extractMonth() + " " + extractDay()+ " "+ start_time + " GMT " + extractYear();
			
			appointmentTimeDentist.add(string);
			
		}
		
		while(rsHygienist.next()){
			String date = rsHygienist.getString("date");
			this.date = date;
			String start_time = rsHygienist.getString("start_time");
			String end_time = rsHygienist.getString("end_time");
			String[] dateArray = date.split("-");
			String string = extractDayWord() + " " + extractMonth() + " " + extractDay()+ " "+ start_time + " GMT " + extractYear();
			appointmentTimeHygienist.add(string);
			
		}
		rsDentist.close();
		rsHygienist.close();
	}
}
