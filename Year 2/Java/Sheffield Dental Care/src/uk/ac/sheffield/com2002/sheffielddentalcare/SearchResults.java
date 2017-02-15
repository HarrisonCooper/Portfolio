/**SearchResults.java
 * 
 * This is a class which can be instantiated to make a SearchResult object,
 * get and set methods allow for data to be retrieved neatly from the database
 * 
 * **/
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchResults {
	private Queries dbQueries = new Queries();
	private RegistrationUtilities utils = new RegistrationUtilities();
	
	private String title,forename,surname,birthdate,postcode,houseNumber,planType,contactNumber;
	private int accountId, patientId;
	private boolean resultFound;
	
	public SearchResults(String houseNum, String postCode, String dob){
		setHouseNum(houseNum);
		setPostcode(utils.formatPostcode(postCode));
		setBirthdate(dob);
		newSearch();
		
	}
	
	public void newSearch(){
		ResultSet rs=dbQueries.findPatient(houseNumber, postcode, birthdate);
		planType="";
		try {
			if(rs.next()){
				setResultFound(true);
				setAccountId(rs.getInt(1));
				rs = dbQueries.getAccountDetails(accountId);
				
				//Get information about the patient
				rs.next();
				setTitle(rs.getString(2));
				setForename(rs.getString(3));
				setSurname(rs.getString(4));
				setBirthdate(rs.getString(5));
				setContactNum(rs.getString(6));
				setPlanType(dbQueries.getPlanType(getAccountId()));
				setPatientId(dbQueries.getPatientId(accountId));
			}else{
				setResultFound(false);
			}
			
			//update display
		} catch (SQLException e1) {
			System.out.println("No results found");
		}
	}
	
	
	//Accessors
	public boolean resultExists(){return resultFound;}
	public String getTitle(){return title;}
	public String getForename(){return forename;}
	public String getSurname(){return surname;}
	public String getBirthdate(){return birthdate;}
	public String getPostcode(){return postcode;}
	public String getHouseNum(){return houseNumber;}
	public int getAccountId(){return accountId;}
	public String getContactNum(){return contactNumber;}
	public int getPatientId(){return patientId;}
	public String getPlanType(){return planType;}
	
	//Set methods
	private void setResultFound(boolean resFound){resultFound=resFound;}
	private void setTitle(String tle){title=tle;}
	private void setForename(String fname){forename=fname;}
	private void setSurname(String sname){surname=sname;}
	private void setBirthdate(String dob){birthdate=dob;}
	private void setPostcode(String pstcd){postcode=pstcd;}
	private void setHouseNum(String houseN){houseNumber=houseN;}
	private void setAccountId(int accId){accountId=accId;}
	private void setPatientId(int patId){patientId=patId;}
	private void setPlanType(String plan){planType=plan;}
	private void setContactNum(String cntct){contactNumber=cntct;}


}
