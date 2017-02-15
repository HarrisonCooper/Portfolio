/**Patient.java
 * 
 * Class to create an object to store information about a patient record and it's attributes, 
 * given the patient id
 * 
 * */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient {
	private Queries db = new Queries();
	
	private int patientNumber, accountId, planId,checkupsLeft,hygienesLeft,repairsLeft;
	private double amountDue;
	public Patient(int patientId){
		ResultSet rs = db.getPatientDetails(patientId);
		try {
			if (rs.next()){
				patientNumber = rs.getInt(1);
				accountId=rs.getInt(2);
				planId=rs.getInt(3);
				checkupsLeft=rs.getInt(4);
				hygienesLeft=rs.getInt(5);
				repairsLeft=rs.getInt(6);
				amountDue=rs.getInt(7);
			}
		} catch (SQLException e) {e.printStackTrace();}
	}
	
	public int getPatientId(){return patientNumber;}
	public int getAccountId(){return accountId;}
	public int getPlanId(){return planId;}
	public int getRemainingCheckups(){return checkupsLeft;}
	public int getRemainingHygieneVisits(){return hygienesLeft;}
	public int getRemainingRepairs(){return repairsLeft;}
	public double getAmountOwed(){return amountDue;}


}
