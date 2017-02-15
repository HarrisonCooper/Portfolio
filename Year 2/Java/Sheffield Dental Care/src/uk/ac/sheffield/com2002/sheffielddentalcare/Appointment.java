/**Appointment.java
 * 
 * A class used to make an object for an appointment, allowing for the data retrieval
 *  of appointment records to be neater and more effective, given the appointment id
 *  
 * */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Appointment {
	private Queries db = new Queries();

	private int appId,patientId,emplId;
	private String startTime, date,endTime;
	private double totalCost;
	private boolean paid;
	private boolean notNull;
	
	public Appointment(int appointId){
		ResultSet rs = db.getAppointmentDetails(appointId);
			try {
				if (rs.next()){
					appId=rs.getInt(1);
					patientId=rs.getInt(2);
					emplId=rs.getInt(3);
					date=rs.getString(4);
					startTime=rs.getString(5);
					endTime=rs.getString(6);
					totalCost=rs.getDouble(7);
					if (rs.getString(8)=="null"){
						notNull = false;
						paid = false;
					}else if (rs.getInt(8)==1){
						notNull = true;
						paid = true;
					}else{
						notNull=true;
						paid=false;
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	//Accessors
	public int getId(){return appId;}
	public int getPatientId(){return patientId;}
	public int getEmployeeId(){return emplId;}
	public String getDate(){return date;}
	public String getStart(){return startTime;}
	public String getEnd(){return endTime;}
	public double getTotalCost(){return totalCost;}
	public boolean notNull(){return notNull;}
	public boolean hasPaid(){return paid;}
	
}
