package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Queries {

	private ResultSet rs;
	
	public Queries(){
		rs = null;
	}
	
	//Register patient
	public void registerPatient(String title, String forename, String surname,
								String houseNumber,String streetName, String districtName, 
								String cityName, String postCode,String phoneNum,
								String healthPlan, String dateOfBirth ) throws SQLException{
		if (!addressExists(houseNumber,postCode)){
			SheffieldDentalCare.db.insertIntoDatabase("INSERT INTO address VALUES('"+houseNumber+"','"+streetName+"','"+districtName+"','"+cityName+"','"+postCode+"');");
		}
		SheffieldDentalCare.db.insertIntoDatabase("INSERT INTO person VALUES(null,'"+title+"','"+forename+"','"+surname+"','"+dateOfBirth+"','"+phoneNum+"','"+houseNumber+"','"+postCode+"');");
		
		
		int[] planDetails = getPlanDetails(healthPlan);
		int planId = planDetails[0];
		int hygienes = planDetails[1];
		int checkups = planDetails[2];
		int repairs = planDetails[3];
		
		SheffieldDentalCare.db.insertIntoDatabase("INSERT INTO patient VALUES(null,"+getLatestAccountId()+","+planId+","+checkups+","+hygienes+","+repairs+","+0.0+");");
		
		
	}
	
	private ArrayList<String> getTreatmentsPerAppointment(int appId){

		Appointment appointment = new Appointment(appId);
		ArrayList<String> treatments = new ArrayList<String>();
		
		String query = "SELECT treatment_name FROM app_treatment WHERE app_id = "+appointment.getId()+";";
		try {
			rs = SheffieldDentalCare.db.getQuery(query);
			while (rs.next()){
				treatments.add(rs.getString(1));
			}
		} catch (SQLException e) {e.printStackTrace();}
		
		return treatments;
	}

	private Treatment updateTreatmentsRemaining(String treatmentType, int patientId){
		double cost = 0.0;
		String columnName="";
		if (treatmentType.equals("Check-up")){
			columnName="checkups_left";
		}else if(treatmentType.equals("Hygiene")){
			columnName="hygiene_visits_left";
		}else if(treatmentType.equals("Gold crown")||treatmentType.equals("Silver amalgam filling")||treatmentType.equals("White composite resin filling")){
			columnName="repairs_left";
		}
		String query = "SELECT "+columnName+" FROM patient WHERE patient_id = "+patientId+";";
		String updateCommand = "UPDATE patient SET "+columnName+"="+columnName+"-1 WHERE patient_id = "+patientId+";";
		//System.out.println("UPDATE patient SET "+columnName+"="+columnName+"-1 WHERE patient_id = "+patientId+";");
		try {
			rs=SheffieldDentalCare.db.getQuery(query);
			if (rs.next()){
				if (rs.getInt(1)>0){
					SheffieldDentalCare.db.insertIntoDatabase(updateCommand);
				}else{
					cost=getTreatmentCost(treatmentType);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Treatment(treatmentType,cost);
	}
	
	public ArrayList<Appointment> getUnpaidAppointments(int patientId){

		rs=null;
		ArrayList<Appointment> unpaidAppointments = new ArrayList<Appointment>();
		String query = "SELECT app_id FROM appointment WHERE patient_id = "+patientId+" AND paid IS NOT NULL AND paid=0;";
		Appointment app;
		try {
			rs=SheffieldDentalCare.db.getQuery(query);
			while(rs.next()){
				app = new Appointment(rs.getInt(1));
				if (app.notNull()){
					unpaidAppointments.add(app);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return unpaidAppointments;
	}
	
	public ArrayList<Treatment> payAppointment(int appointmentId){
		
		Appointment appoint = new Appointment(appointmentId);
		ArrayList<Treatment> treatmentsGiven = getAppointmentReceipt(appointmentId,appoint.getPatientId());
		ArrayList<Treatment> treatmentsToPrint = new ArrayList<Treatment>();
		//Patient patient = new Patient(appoint.getPatientId());
		
		for(Treatment tmnt: treatmentsGiven){
			treatmentsToPrint.add(updateTreatmentsRemaining(tmnt.getName(),appoint.getPatientId()));
		}
		String updateAmountOwed = "UPDATE patient SET total_amount_due = total_amount_due - "+appoint.getTotalCost()+" WHERE patient_id = "+appoint.getPatientId()+";";
		String updateHasPaid = "UPDATE appointment SET paid = 1 WHERE app_id="+appoint.getId()+";";
		try {
			SheffieldDentalCare.db.insertIntoDatabase(updateAmountOwed);
			SheffieldDentalCare.db.insertIntoDatabase(updateHasPaid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return treatmentsToPrint;
	}
	public ArrayList<Treatment> getAppointmentReceipt(int appointmentId,int patientId){

		//get treatments given in the appoinment
		ArrayList<Treatment> treatments = new ArrayList<Treatment>();
		
		//Appointment appointment = new Appointment(appointmentId);
		Patient patient = new Patient(patientId);
		ArrayList<String> treatmentsGiven = getTreatmentsPerAppointment(appointmentId);
		
		
		for (String treatmnt : treatmentsGiven){
			if (treatmnt.equals("Check-up")){
				if (patient.getRemainingCheckups()>0){
					//useTreatment("checkups_left",patientId);
					treatments.add(new Treatment(treatmnt,0));
				}else{
					treatments.add(new Treatment(treatmnt,getTreatmentCost(treatmnt)));
				}
			}else if(treatmnt.equals("Hygiene")){
				if (patient.getRemainingHygieneVisits()>0){
					//useTreatment("hygiene_visits_left",patientId);
					treatments.add(new Treatment(treatmnt,0));
				}else{
					treatments.add(new Treatment(treatmnt,getTreatmentCost(treatmnt)));
				}
			}else if(treatmnt.equals("Gold crown")||treatmnt.equals("Silver amalgam filling")||treatmnt.equals("White composite resin filling")){
				if (patient.getRemainingRepairs()>0){
					//useTreatment("repairs_left",patientId);
					treatments.add(new Treatment(treatmnt,0));
				}else{
					treatments.add(new Treatment(treatmnt,getTreatmentCost(treatmnt)));
				}
			}
		}
		
		return treatments;
	}
	
	public double getTreatmentCost(String treatmentName){

		rs = null;
		double cost = 0.0;
		String query = "SELECT cost FROM treatment WHERE treatment_name = '"+treatmentName+"';";
		try {
			rs=SheffieldDentalCare.db.getQuery(query);
			if (rs.next()){
				cost = rs.getDouble(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cost;
	}
	
	public ResultSet getAppointmentDetails(int appointmentId){

		String query = "SELECT * FROM appointment WHERE app_id = '"+appointmentId+"';";
		rs = null;
		try {
			rs = SheffieldDentalCare.db.getQuery(query);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	
	
	//Function which get the plan_id for the new patient from the database given the plan_type
	public int[] getPlanDetails(String planType) throws SQLException{

		//Execute query
		rs = SheffieldDentalCare.db.getQuery("SELECT plan_id,hygiene_visits,checkup_visits,planned_repairs, cost_monthly "
						+ "FROM healthcare_plans "
						+ "WHERE plan_type='"+planType+"';");
		
		int[] planDetails=new int[5];
		
		if (rs.next()){
			planDetails[0]=rs.getInt(1);
			planDetails[1]=rs.getInt(2);
			planDetails[2]=rs.getInt(3);
			planDetails[3]=rs.getInt(4);
			planDetails[4]=rs.getInt(5);
		}
		
		return planDetails;
	}

	//Check if an address exists
	private boolean addressExists(String houseNum, String postCode){
		try {
			rs = SheffieldDentalCare.db.getQuery("SELECT * FROM address WHERE house_number = '"+houseNum+"' AND post_code = '"+postCode+"';");
			if (rs.next()){
				return true;
			}else {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}
	}
	
	//Get the newly created account_id so it can be entered as a foreign key into patient
	private int getLatestAccountId(){
		try {
			rs = SheffieldDentalCare.db.getQuery("SELECT MAX(account_id) FROM person;");
			if (rs.next()){
				return rs.getInt(1);
			}else {
				return -1;}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{;}
		return -1;
		
	}

	//returns all applicable healthcare plans except the one specified as 'planType', if planType="", then all will be returned
	public ResultSet otherPlans(String planType){
		rs=null;
		String query = "SELECT plan_type FROM healthcare_plans WHERE plan_type <>'" +planType+"';";
		//Execute query
		try {
			rs =SheffieldDentalCare.db.getQuery(query);
		} catch (SQLException e) {}
		;
		return rs;
	}
	
	//This method will change and update a patient to a new healthcare plan using their accountid and a new planid
	public void changeSubscription(int accountId,int newPlanId){

		String planType=getPlanType(accountId);
		String query = "SELECT plan_type FROM healthcare_plans WHERE plan_id = "+newPlanId+";";
		
		//Change the relation between patient-plan
		try {
			rs = SheffieldDentalCare.db.getQuery(query);
			rs.next();
			planType=rs.getString(1);
			int[] details=getPlanDetails(planType);
			int hygiene=details[1];
			int checkup=details[2];
			int repairs=details[3];
			//renew available treatments
			String command = "UPDATE patient SET plan_id="+newPlanId+", checkups_left="+checkup+",hygiene_visits_left="+hygiene+",repairs_left="+repairs+" WHERE account_id="+accountId+";";
			SheffieldDentalCare.db.insertIntoDatabase(command);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//dateOfBirth must be in format 'YYYY-MM-DD'
	public ResultSet findPatient(String houseNum, String postCode, String dateOfBirth){
		rs = null;
		try {
			rs = SheffieldDentalCare.db.getQuery("SELECT account_id FROM person "
					+ "WHERE house_number = '"+houseNum+"'"
					+ "AND post_code = '"+postCode+"' "
					+ "AND date_of_birth = '"+dateOfBirth+"';");
			return rs;
		} catch (SQLException e) {
			System.out.println("Cannot complete query, something went wrong whilst interacting with mySQL");
			return rs;
		}
	}
	
	//Finds all details from account using only the id
	public ResultSet getAccountDetails(int accountId){
		rs=null;
		try{
			rs = SheffieldDentalCare.db.getQuery("SELECT * FROM person "
					+ "WHERE account_id = "+accountId+";");
			return rs;
		}catch(SQLException e){
			
		}
		return rs;
	}
	
	//Returns the type of plan which the patient is on, using accountId
	public String getPlanType(int accountId){

		int planId;
		try{
			rs = SheffieldDentalCare.db.getQuery("SELECT plan_id FROM patient "
					+ "WHERE account_id = "+accountId+";");
			if (rs.next()){
				planId=rs.getInt(1);
				rs = SheffieldDentalCare.db.getQuery("SELECT plan_type FROM healthcare_plans WHERE plan_id = "+planId+";");
				if (rs.next()){
					return rs.getString(1);
				}
			}
		}catch(SQLException e){}finally{;}
		
		return "not found";
	}
	
	public ResultSet getPatientDetails(int patientId){

		rs = null;
		String query = "SELECT * FROM patient WHERE patient_id="+patientId+";";
		try {
			rs = SheffieldDentalCare.db.getQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{;}
		return rs;
	}
	
	//Gets the patientId using the foreign key - accountId
	public int getPatientId(int accountId){

		int patientId=-1;
		rs=null;
		String query = "SELECT patient_id "
				+"FROM patient "
				+"WHERE account_id="+accountId+";";
		try {
			rs=SheffieldDentalCare.db.getQuery(query);
			if(rs.next())
				patientId=rs.getInt(1);
		} catch (SQLException e) {}
		return patientId;
	}
	


		
	public void commitTreatmentToAppTreatment(int app_id, String treatment_name) throws SQLException{
		SqlConnection db = new SqlConnection();	
		db.insertIntoDatabase("INSERT INTO app_treatment VALUES(null,"+app_id+",'"+treatment_name+"');");
	}
	
	public void commitTreatmentToAppointment(int app_id, int total_cost) throws SQLException{
		SqlConnection db = new SqlConnection();	
		db.insertIntoDatabase("UPDATE appointment SET total_cost="+total_cost+", paid=0 WHERE app_id = "+app_id+";");
	}
	
	
	//This takes an appointment ID and removes it from the Data Base.
	public void cancleAppointment(int app_id) throws SQLException{
		//Establish connection between server
		SqlConnection db = new SqlConnection();	
		db.insertIntoDatabase("DELETE FROM appointment WHERE app_id="+app_id+";");
	}
	
	public ResultSet getAppointments(int pat_id) {
		rs = null;
		try {
			rs = SheffieldDentalCare.db.getQuery("SELECT * FROM appointment WHERE patient_id="+pat_id+";");
			return rs;
		} catch (SQLException e) {
			System.out.println("Cannot complete query, something went wrong whilst interacting with mySQL");
			return rs;
		}
	}
	
	public ResultSet getAppointmentNoTreatment(int pat_id) {
		rs = null;
		try {
			rs = SheffieldDentalCare.db.getQuery("SELECT * FROM appointment WHERE patient_id="+pat_id+" AND paid IS NULL;");
			return rs;
		} catch (SQLException e) {
			System.out.println("Cannot complete query, something went wrong whilst interacting with mySQL");
			return rs;
		}
	}
	public void bookAppointment(int emp_id, int patient_id, String date, String start_time, String end_time) throws SQLException{
        //inserts appointment record into database after checking if appointment slot is used
        
        //Establish connection between server
        //SqlConnection db = new SqlConnection();
        if (!isAppSlotUsed(emp_id,date,start_time,end_time)){
            //System.out.println(isAppSlotUsed(emp_id,date,start_time,end_time));
            SheffieldDentalCare.db.insertIntoDatabase("INSERT INTO appointment VALUES(null,"
                    +patient_id+","
                    +emp_id+",'"
                    +date +"','"
                    +start_time+"','"
                    +end_time+"',"
                    + "null" +","+ "null);");
        }        
    }
    
    private boolean isAppSlotUsed(int emp_id, String date, String start_time, String end_time) throws SQLException{
        //checks if appointment slot has already been taken by the partner
        //takes employee id, date, new start time and new end time
        //if returns true, appoint slot has been taken
        //start and end times converted to integer for easier validation
        int ns = getIntsFromDates(start_time);
        int ne = getIntsFromDates(end_time);
        System.out.println("NS = " + ns);
        System.out.println("NE = " + ne);
        
        try {
            //runs sql select statement to get all appointments matching the selected date and partner
            rs = SheffieldDentalCare.db.getQuery("SELECT start_time, end_time FROM appointment WHERE date='"+date+"' AND emp_id="+emp_id+";");    
            
            //checks every existing appointment against the new appointment for clashes
            while (rs.next()){
                int es = getIntsFromDates(rs.getString(1));
                int ee = getIntsFromDates(rs.getString(2));
                System.out.print("ES = " + es);
                System.out.println(", EE = " + ee);
                
                //new appointment start time is equal to end time
                if (ns==ne) {
                    return true;
                }
                //new appointment is identical to existing appointment
                if (ns==es) {
                    return true;
                }
                //new appointment start time is within another appointment times
                else if (ns>es && ns<ee) {
                    return true;
                }
                //new app starts before existing app but ends during existing app
                else if (ns<es && ne<ee && ne >es) {
                    return true;
                }
                //new app starts before existing app and finishes after existing app
                else if (ns<es && ne > ee) {
                    return true;
                }
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        System.out.println("inserted");
        return false;
    }
	
	private int getIntsFromDates(String time) {		
		//gets hours and minutes from time and converts to int
		
		//regex for HH:MM:SS
		String timePattern = "(\\d\\d):(\\d\\d):(\\d\\d)";
		//splits timePattern into 3 groups, HH, MM and SS
		Pattern p = Pattern.compile(timePattern);
		Matcher m = p.matcher(time);
		int totalMinutes = 0;
		while(m.find()) {
			String hourString = m.group(1);
			String minuteString = m.group(2);
			int hoursInMins = Integer.parseInt(hourString) *60;
			totalMinutes = Integer.parseInt(minuteString) + hoursInMins;
		}
		int intDate = totalMinutes;
		return intDate;
	}
	public ResultSet getAppointmentsCal(int emp_id) throws SQLException {
		SqlConnection db = new SqlConnection();	

		rs = null;
		try {
			rs = db.getQuery("SELECT * FROM appointment WHERE emp_id="+emp_id);
			return rs;
		} catch (SQLException e) {
			System.out.println("Cannot complete query, something went wrong whilst interacting with mySQL");
			return rs;
		} 
	}
	
	public ResultSet getAppPatId(int emp_id) {
		SqlConnection db = new SqlConnection();	
		rs=null;
		try {
			rs = db.getQuery("SELECT patient_id FROM appointment WHERE emp_id="+emp_id);
			return rs;
		} catch (SQLException e) {
			System.out.println("Cannot complete query, something went wrong whilst interacting with mySQL");
			e.printStackTrace();
			return rs;
		}
	}
	
	public ResultSet getAppAccId(int pat_id){
		SqlConnection db = new SqlConnection();	
		rs=null;
		try {
			rs = db.getQuery("SELECT account_id FROM patient WHERE patient_id="+pat_id+";");
			return rs;
		} catch (SQLException e) {
			System.out.println("Cannot complete query, something went wrong whilst interacting with mySQL");
			e.printStackTrace();
			return rs;
		}
	}
	
	public ResultSet getPatForename(int acc_id){
		SqlConnection db = new SqlConnection();	
		rs=null;
		try {
			rs = db.getQuery("SELECT forename FROM person WHERE account_id="+acc_id+";");
			return rs;
		} catch (SQLException e) {
			System.out.println("Cannot complete query, something went wrong whilst interacting with mySQL");
			e.printStackTrace();
			return rs;
		}
	}
	/*
	public ResultSet getStartTimes(int emp_id){
		SqlConnection db = new SqlConnection();	
		rs=null;
		try {
			rs = db.getQuery("SELECT start_time FROM appointment WHERE emp_id="+emp_id+";");
			return rs;
		} catch (SQLException e) {
			System.out.println("Cannot complete query, something went wrong whilst interacting with mySQL");
			e.printStackTrace();
			return rs;
		}
	}
	
	public ResultSet getEndTimes(int emp_id){
		SqlConnection db = new SqlConnection();	
		rs=null;
		try {
			rs = db.getQuery("SELECT end_time FROM appointment WHERE emp_id="+emp_id+";");
			return rs;
		} catch (SQLException e) {
			System.out.println("Cannot complete query, something went wrong whilst interacting with mySQL");
			e.printStackTrace();
			return rs;
		}
	}*/
}
