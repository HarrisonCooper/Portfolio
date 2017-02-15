/**SearchPanel.java
 * 
 * A JPanel which encapsulates all JComponents used to search for a patient
 *  
 * */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class SearchPanel extends JPanel{
	Queries dbQueries= new Queries();
	RegistrationUtilities compUtils = new RegistrationUtilities();
	SearchResults results;
	private String planType="";
		
	private String houseNum;
	private String postcode;
	private String dateOfBirth;
	
	private final String searchBtnTxt="Search";
	
	JTextField tbHouseNum, tbPostcode;
	private JComboBox cmbDay,cmbMonth,cmbYear;
	JButton btnFind;
	JLabel lblPatientResult;
	
	private ResultSet rsPlans;
	
	public SearchPanel(){
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		tbHouseNum = new JTextField("20");
		tbPostcode = new JTextField("S101BA");
		cmbDay = new JComboBox(compUtils.getDays());
		cmbMonth = new JComboBox(compUtils.getMonths());
		cmbYear = new JComboBox(compUtils.getYears());
		cmbDay.setSelectedIndex(0);
		cmbMonth.setSelectedIndex(0);
		cmbYear.setSelectedIndex(0);
		
		lblPatientResult = new JLabel();
				
		add(new JLabel("House Number:"));
		add(tbHouseNum);
		add(new JLabel("Postcode:"));
		add(tbPostcode);
		add(new JLabel("Date of Birth:"));
		add(cmbYear);
		add(cmbMonth);
		add(cmbDay);
		add(lblPatientResult);
		
	}
	
	public void onSearchClick(){
		String year =cmbYear.getSelectedItem().toString();
		String month = cmbMonth.getSelectedItem().toString();
		String day = cmbDay.getSelectedItem().toString();
		houseNum = tbHouseNum.getText();
		postcode=compUtils.formatPostcode(tbPostcode.getText());
		dateOfBirth=year+"-"+month+"-"+day;
		search();
	}	
		
	void search(){
		results = new SearchResults(houseNum,postcode,dateOfBirth);
		updateResultView(results);
	}
	
	void updateResultView(SearchResults patientDtls){
		String resultView;
		if(patientDtls.resultExists()){
			String details = patientDtls.getTitle()+" "+patientDtls.getForename()+" "+patientDtls.getSurname()+", Born: "+patientDtls.getBirthdate();
			resultView="<html>"+details+"<br>with plan type: "+patientDtls.getPlanType()+"</html>";
		}else{
			resultView="NO RESULTS FOUND";
		}
		lblPatientResult.setText(resultView);
	}
	
	//Use to make search results from this panel available to the parent frame
	public SearchResults access(){
		return results;
	}
		
}