package uk.ac.sheffield.com2002.sheffielddentalcare;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class BookHolidayJPanel extends JPanel{
	
	Queries dbQueries= new Queries();
	RegistrationUtilities compUtils = new RegistrationUtilities();
	private String planType="";
	private int accountId;
	
	private String houseNum;
	private String postcode;
	private String dateOfBirth;
	private String fullName;
	
	private String searchResult="";
	private final String searchBtnTxt="Search";
	private final String bookBtnTxt="Book Appointment";
	
	private String patientDtls="";
	
	//public JTextField txtDateOfBirth;
	public JTextField txtHouseNumber;
	public JTextField txtPostCode;
	public JLabel lblWith;
	public JLabel lblWhen;
	public JLabel lblPatientResult;
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JComboBox<?> partner;
	public JComboBox<?> year;
	public JComboBox<?> month;
	public JComboBox<?> day;
	public JComboBox<?> startHour;
	public JComboBox<?> startMinute;
	public JComboBox<?> endHour;
	public JComboBox<?> endMinute;
	public JComboBox<?> birthYear;
	public JComboBox<?> birthMonth;
	public JComboBox<?> birthDay;
	public JLabel lblDate;
	public JLabel lblStart;
	public JLabel lblEnd;
	public JLabel lblDOB;
	public JButton findPerson;
	public JButton bookAppointment;
	public JLabel findPatientId;
	
		//The data values for each drop down box.
		String[] yearString = {"2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025"};
		String[] monthString = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		String[] dayString = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
		String[] partnerStrings = {"Please choose...", "Hygienist", "Dentist"};
		String[] startHourList = {"09", "10", "11", "12", "13", "14", "15", "16", "17"};
		String[] startMinuteList = {"00", "20", "40"};
		String[] endHourList = {"09", "10", "11", "12", "13", "14", "15", "16", "17"};
		String[] endMinuteList = {"00", "20", "40"};

		
		//If no data is changed, the values are set to the first element of each array.
		String selectedDay = dayString[0];
		String selectedMonth = monthString[0];
		String selectedYear = yearString[0];
		String selectedStartHour = startHourList[0];
		String selectedStartMinute = startMinuteList[0];
		String selectedEndHour = endHourList[0];
		String selectedEndMinute = endMinuteList[0];
		String selectedPartner = partnerStrings[0];

		private class ComboBoxHandler implements ItemListener{
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					selectedDay = (String) day.getSelectedItem();
					selectedMonth = (String) month.getSelectedItem();
					selectedYear = (String) year.getSelectedItem();
					selectedStartHour = (String) startHour.getSelectedItem();
					selectedStartMinute = (String) startMinute.getSelectedItem();
					selectedEndHour = (String) endHour.getSelectedItem();
					selectedEndMinute = (String) endMinute.getSelectedItem();
					selectedPartner = (String) partner.getSelectedItem();
				}
			}
		}
		
		private class ButtonHandler implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				String event = e.getActionCommand();
				switch(event){
					case bookBtnTxt:
					try {
						int emp_id = 0;
						if (selectedPartner == "Hygienist"){
							emp_id = 2;
						} else if (selectedPartner == "Dentist") {
							emp_id = 1;
						}
						ResultSet rs=dbQueries.findPatient(houseNum, postcode, dateOfBirth);
						try {
							if(rs.next()){
								accountId=rs.getInt(1);
							}
						} catch (SQLException e1) {
							System.out.println("No results found");
						}
						System.out.println("empid = " + emp_id);
						int patient_id = dbQueries.getPatientId(accountId);
						System.out.println("Patient ID is " + patient_id);
						
						String date = selectedYear+"-"+selectedMonth+"-"+selectedDay;
						String start_time = selectedStartHour + ":" + selectedStartMinute + ":00";
						String end_time = selectedEndHour + ":" + selectedEndMinute + ":00";
						System.out.println(date);
						System.out.println(start_time);
						System.out.println(end_time);
						
						dbQueries.bookAppointment(emp_id, patient_id, date, start_time, end_time);
					} catch (SQLException e2) {}
					break;
					case searchBtnTxt:
						String year =birthYear.getSelectedItem().toString();
						String month = birthMonth.getSelectedItem().toString();
						String day = birthDay.getSelectedItem().toString();
						houseNum = txtHouseNumber.getText();
						postcode=compUtils.formatPostcode(txtPostCode.getText());
						dateOfBirth=year+"-"+month+"-"+day;
						search();
						break;
					
					}
				}
		}
		
	public BookHolidayJPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		findPatientId = new JLabel("Find Patient ID:");
		lblWith = new JLabel("With:");
		lblWhen = new JLabel("When:");
		lblDate = new JLabel("Year:Month:Day");
		lblEnd = new JLabel("End : ");
		lblStart = new JLabel("Start : ");
		lblDOB = new JLabel("Date of Birth :");
		lblPatientResult = new JLabel();
		lblPatientResult.setAlignmentX(Component.CENTER_ALIGNMENT);
		//txtDateOfBirth = new JTextField("Date Of Birth");
		txtHouseNumber = new JTextField("House Number");
		txtPostCode = new JTextField("Post Code");
		lblWith.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblWhen.setAlignmentX(Component.CENTER_ALIGNMENT);
		findPatientId.setAlignmentX(Component.CENTER_ALIGNMENT);
		partner = new JComboBox<Object>(partnerStrings);
		startHour = new JComboBox<Object>(startHourList);
		startMinute = new JComboBox<Object>(startMinuteList);
		year = new JComboBox<Object>(yearString);
		month = new JComboBox<Object>(monthString);
		day = new JComboBox<Object>(dayString);
		endHour = new JComboBox<Object>(endHourList);
		endMinute = new JComboBox<Object>(endMinuteList);
		birthYear = new JComboBox<Object>(compUtils.getYears());
		birthMonth = new JComboBox<Object>(compUtils.getMonths());
		birthDay = new JComboBox<Object>(compUtils.getDays());
		panel = new JPanel();
		panel_1 = new JPanel();
		panel_2 = new JPanel();
		panel_3 = new JPanel();
		findPerson = new JButton(searchBtnTxt);
		findPerson.setAlignmentX(Component.CENTER_ALIGNMENT);
		bookAppointment = new JButton(bookBtnTxt);
		bookAppointment.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		startHour.addItemListener(new ComboBoxHandler());
		startMinute.addItemListener(new ComboBoxHandler());
		endHour.addItemListener(new ComboBoxHandler());
		endMinute.addItemListener(new ComboBoxHandler());
		day.addItemListener(new ComboBoxHandler());
		month.addItemListener(new ComboBoxHandler());
		year.addItemListener(new ComboBoxHandler());
		partner.addItemListener(new ComboBoxHandler());
		findPerson.addActionListener(new ButtonHandler());
		bookAppointment.addActionListener(new ButtonHandler());
		
		add(findPatientId);
		add(panel_3);
		panel_3.add(lblDOB);
		panel_3.add(birthYear);
		panel_3.add(birthMonth);
		panel_3.add(birthDay);
		//add(txtDateOfBirth);
		add(txtHouseNumber);
		add(txtPostCode);
		add(findPerson);
		add(lblPatientResult);
		add(lblWith);
		add(partner);
		add(lblWhen);
		add(panel);
		panel.add(lblDate);
		panel.add(year);
		panel.add(month);
		panel.add(day);
		add(panel_1);
		panel_1.add(lblStart);
		panel_1.add(startHour);
		panel_1.add(startMinute);
		add(panel_2);
		panel_2.add(lblEnd);
		panel_2.add(endHour);
		panel_2.add(endMinute);
		add(bookAppointment);
	}
	
	//A method to update the showing of a patient after search data has been changed/updated
	void updateDisplay(String searchResult,String planType){
		lblPatientResult.setText(searchResult);
		}
	
	void search(){
		ResultSet rs=dbQueries.findPatient(houseNum, postcode, dateOfBirth);
		searchResult="";
		planType="";
		try {
			if(rs.next()){
				accountId=rs.getInt(1);
				rs = dbQueries.getAccountDetails(accountId);
				//Test
				rs.next();
				String title=rs.getString(2);
				fullName=rs.getString(3)+" "+rs.getString(4);
				String dateOfBirth=rs.getString(5);
				String contactNumber=rs.getString(6);
				
				patientDtls = "RESULT FOUND: "+title+" "+fullName+", birth date: "+ dateOfBirth+", "+"contact number: "+contactNumber;
				planType = dbQueries.getPlanType(accountId);
				updateResultView(patientDtls, planType);
			}else{searchResult="No results found";}
			
			//update display
			updateDisplay(searchResult,planType);
		} catch (SQLException e1) {
			System.out.println("No results found");
		}
				
	}
	
	void book(){
		ResultSet rs=dbQueries.findPatient(houseNum, postcode, dateOfBirth);
		try {
			if(rs.next()) {
				accountId = rs.getInt(1);
				rs = dbQueries.getAccountDetails(accountId);
			}
		} catch (SQLException e1) {
			System.out.println("No Results found");
		}
	}
	
	void updateResultView(String patientDtls, String planType){
		searchResult="<html>"+patientDtls+"</html>";
		lblPatientResult.setText(searchResult);
	}
	
}
