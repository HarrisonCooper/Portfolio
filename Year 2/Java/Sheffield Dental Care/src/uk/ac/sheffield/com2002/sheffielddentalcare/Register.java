/**Register.java
 * 
 *Registration page, this is the page where the secretary can register a new patient
 *  
 * */
package uk.ac.sheffield.com2002.sheffielddentalcare;
import java.awt.Container;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class Register extends SecretaryFrame{
	Queries dbQueries = new Queries();
	RegistrationUtilities rUtils = new RegistrationUtilities();
	
	public Register() {
		String[] titles = { "Please Select...","Mr", "Mrs", "Dr", "Ms", "Prof" };
		String[] plans = {"none","nhschildren_plan","maintenance_plan","oralhealth_plan", "dentalrepair_plan"};
		Integer[] years = rUtils.getYears();
		Integer[] months = rUtils.getMonths();
		Integer[] days = rUtils.getDays();

		Container pane = getContentPane();
		/**Create the frame components**/
		
		//Name JComponents
		JLabel lblTitle = new JLabel("Title:");
		JComboBox cmbTitles = new JComboBox(titles);
		cmbTitles.setSelectedIndex(1);
		
		
		//JLabel lblAddress = new JLabel("Address:");
		//Address JComponents
		JTextField tbForename = new JTextField();
		JTextField tbSurname = new JTextField();
		JTextField tbHouseNum = new JTextField();
		JTextField tbStreetName = new JTextField();
		JTextField tbDistrName = new JTextField();
		JTextField tbCityName = new JTextField();
		JTextField tbPostcode = new JTextField();
		JTextField tbNumber = new JTextField();
		
		
		
		//Healthcare plans
		JLabel lblPlan = new JLabel("Healthcare Plans:");
		JComboBox cmbPlans = new JComboBox(plans);
		cmbPlans.setSelectedIndex(0);

	

		JComboBox cmbDay = new JComboBox(days);
		JComboBox cmbMonth = new JComboBox(months);
		JComboBox cmbYear = new JComboBox(years);
		
		cmbDay.setSelectedIndex(0);
		cmbMonth.setSelectedIndex(0);
		cmbYear.setSelectedIndex(4);
		
		
		
		
		//register button
		JButton btnSubmit = new JButton("Register");

		
		
		//Name and title
		pane.add(new JLabel("Title:"));
		pane.add(cmbTitles);
		pane.add(new JLabel("Name:"));
		pane.add(tbForename);
		pane.add(tbSurname);
		
		//Address
		pane.add(new JLabel("Address:"));
		pane.add(new JLabel("House number:"));
		pane.add(tbHouseNum);
		pane.add(new JLabel("Street name:"));
		pane.add(tbStreetName);
		pane.add(new JLabel("District name:"));
		pane.add(tbDistrName);
		pane.add(new JLabel("City name:"));
		pane.add(tbCityName);
		pane.add(new JLabel("Postcode:"));
		pane.add(tbPostcode);
		pane.add(new JLabel("Contact Number:"));
		pane.add(tbNumber);
		
		//Date of birth
		pane.add(new JLabel("Date of Birth:"));
		pane.add(cmbDay);
		pane.add(cmbMonth);
		pane.add(cmbYear);

		pane.add(new JLabel("Healthcare Plans"));
		pane.add(cmbPlans);
		
		pane.add(btnSubmit);



		//Run when 'btnSubmit' is clicked
		btnSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//Retreive all inputs from JComponents
				String title = cmbTitles.getSelectedItem().toString();
				String forename = tbForename.getText();
				String surname = tbSurname.getText();
				String houseNumber = tbHouseNum.getText();
				String streetName = tbStreetName.getText();
				String districtName = tbDistrName.getText();
				String cityName = tbCityName.getText();
				String postCode = tbPostcode.getText();
				String phoneNum = tbNumber.getText();
				String selectedPlan = cmbPlans.getSelectedItem().toString();
				String sYear = cmbYear.getSelectedItem().toString();
				String sMonth = cmbMonth.getSelectedItem().toString();
				String sDay = cmbDay.getSelectedItem().toString();
				
				
				int year = Integer.parseInt(sYear);
				int month = Integer.parseInt(sMonth);
				int day = Integer.parseInt(sDay);
				
				
				//Validate input
				//ArrayList errorHandler = new ArrayList<String>();
				boolean validPostcode = rUtils.validPostcode(postCode);
				boolean validHouseNumber=(0<houseNumber.length()&&houseNumber.length()<40);
				boolean validCity=(0<cityName.length()&&cityName.length()<20);
				boolean validDistrict=(0<districtName.length()&&districtName.length()<30);
				boolean validPhoneNumber=(0<phoneNum.length()&&phoneNum.length()<17&&rUtils.isNumeric(phoneNum));
				boolean validDob = rUtils.isValid(year,month,day);
				boolean validName = (title!="Please Select"&&
									 !forename.isEmpty()&&!surname.isEmpty()&&
									  forename.length()<30&&surname.length()<30);
				boolean validAddress = validPostcode&&validHouseNumber&&
										validPhoneNumber&&validCity&&validDistrict;
				boolean validForm=validAddress&&validDob&&validName;
				
				
				//run this code if inputs are valid
				if (validForm){
				    try {
					    String birthdate = year+"-"+month+"-"+day;
						dbQueries.registerPatient(title, forename, surname, houseNumber, streetName,
								districtName, cityName, rUtils.formatPostcode(postCode), phoneNum, selectedPlan, birthdate);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.println("Could not add records to database");
						e1.printStackTrace();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Some inputs are invalid, try and ensure no"+"\n"+"fields are left blank, and are sensible",
							"Invalid Input",JOptionPane.ERROR_MESSAGE);
				}				
			}
	
		});

	}
}
