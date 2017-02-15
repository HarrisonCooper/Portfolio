/*
 * This is the panel that allows the partner to record what treatments were 
 * given during the appointment, allowing them to commit it to the database
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;


public class RecordJPanel extends JPanel{

	Queries dbQueries= new Queries();
	
	public JButton submit;
	public JButton searchButton;
	public JComboBox<?> treatmentGiven;
	public JLabel treatment;
	public JLabel cost;
	public JLabel totalCost;
	public JLabel lblFindID;
	public JCheckBox checkUp;
	public JCheckBox hygiene;
	public JCheckBox amalgam;
	public JCheckBox composite;
	public JCheckBox goldFilling;
	public JPanel panel;
	public JPanel panel_1;
	private JList<String> appList;
	private JComboBox cmbAppList;
	
	public int addedCost = 0;
	
	//The data values for each drop down box.
	String[] titleString = { "Mr", "Mrs", "Dr", "Ms", "Prof"};
	int[] treatmentCosts = {45, 90, 150, 500};
	
	//If no data is changed, the strings are set to the first element of each array.
	String selectedTitle = titleString[0];
		
	//Each time a check box is selected, the price of that treatment is added to the total cost of the appointment
	//Also if you un-check a treatment, the price reduces.
	private class CheckBoxHandler implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			JCheckBox checkbox = (JCheckBox) e.getSource();
			if (checkbox.isSelected()) {
				if (checkbox == checkUp) {
					addedCost += 45;
					totalCost.setText("Total Cost : £" + addedCost);
				} else if (checkbox == hygiene) {
					addedCost += 45;
					totalCost.setText("Total Cost : £" + addedCost);
				} else if (checkbox == amalgam) {
					addedCost += 90;
					totalCost.setText("Total Cost : £" + addedCost);
				} else if (checkbox == composite) {
					addedCost += 150;
					totalCost.setText("Total Cost : £" + addedCost);
				} else if (checkbox == goldFilling) {
					addedCost += 500;
					totalCost.setText("Total Cost : £" + addedCost);
				}
			} else {
				if (checkbox == checkUp) {
					addedCost -= 45;
					totalCost.setText("Total Cost : £" + addedCost);
				} else if (checkbox == hygiene) {
					addedCost -= 45;
					totalCost.setText("Total Cost : £" + addedCost);
				} else if (checkbox == amalgam) {
					addedCost -= 90;
					totalCost.setText("Total Cost : £" + addedCost);
				} else if (checkbox == composite) {
					addedCost -= 150;
					totalCost.setText("Total Cost : £" + addedCost);
				} else if (checkbox == goldFilling) {
					addedCost -= 500;
					totalCost.setText("Total Cost : £" + addedCost);
				}
			}
		}
	}
	
	
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if (button == searchButton) {
				search.onSearchClick();
				int acc_id = search.results.getAccountId();
				int pat_id = dbQueries.getPatientId(acc_id);
				//Displays appointments where there is no treatment.
				ResultSet rs = dbQueries.getAppointmentNoTreatment(pat_id);
				try {
					while(rs.next()){
						int emp_id = rs.getInt("emp_id");
						String partner = "";
						if (emp_id == 2) {
							partner = "Hygienist";
						} else {
							partner = "Dentist";
						}
						String date = rs.getString("date");
						String start_time = rs.getString("start_time");
						String end_time = rs.getString("end_time");
						String string = ("Appointment number "+ rs.getInt(1) +" with : " + partner + " on the : " + date + " from " + start_time + " till " + end_time);
						listModel.addElement(string);
						cmbAppList.addItem(rs.getInt(1));
					}
				} catch (SQLException e1) {}
			}
			if (button == submit) {
				int app_id = (int) cmbAppList.getSelectedItem();
				
				ArrayList<String> treatmentsGiven = new ArrayList<String>();
				if (checkUp.isSelected()) {
					treatmentsGiven.add("Check-Up");
				}
				if (hygiene.isSelected()) {
					treatmentsGiven.add("Hygiene");
				}
				if (amalgam.isSelected()) {
					treatmentsGiven.add("Silver amalgam filling");
				}
				if (composite.isSelected()) {
					treatmentsGiven.add("White composite resing filling");
				}
				if (goldFilling.isSelected()){
					treatmentsGiven.add("Gold crown");
				}
				
				try {
				
					for (String s:treatmentsGiven) {
						dbQueries.commitTreatmentToAppTreatment(app_id, s);
					}
				
					dbQueries.commitTreatmentToAppointment(app_id, addedCost);
					
				} catch (SQLException e1) {}
			}
		}
	}
	
	private SearchPanel search = new SearchPanel();
	private DefaultListModel<String> listModel = new DefaultListModel<String>();

	
	public RecordJPanel() {
		
		submit = new JButton("Submit");
		searchButton = new JButton("Search");
		treatment = new JLabel("Treatments Given:");
		cost = new JLabel("Cost:");
		lblFindID = new JLabel("Find patients ID:");
		totalCost = new JLabel("Total Cost : £" + addedCost);
		checkUp = new JCheckBox("check-up : \t" + "£" + treatmentCosts[0]);
		hygiene = new JCheckBox("hygiene : \t" + "£" + treatmentCosts[0]);
		amalgam = new JCheckBox("Amalgam filling : \t" + "£" + treatmentCosts[1]);
		composite = new JCheckBox("White composite resin filling : \t" + "£" + treatmentCosts[2]);
		goldFilling = new JCheckBox("Gold crown filling : \t" + "£" + treatmentCosts[3]);
		panel = new JPanel();
		panel_1 = new JPanel();
		appList = new JList<String>(listModel);
		cmbAppList = new JComboBox();
		
		//Added Item listeners to the check-boxes and title box.
		CheckBoxHandler checkBoxHandler = new CheckBoxHandler();
		checkUp.addItemListener(checkBoxHandler);
		hygiene.addItemListener(checkBoxHandler);
		amalgam.addItemListener(checkBoxHandler);
		composite.addItemListener(checkBoxHandler);
		goldFilling.addItemListener(checkBoxHandler);
		submit.addActionListener(new ButtonHandler());
		searchButton.addActionListener(new ButtonHandler());
		
		add(lblFindID);
		add(panel);
		panel.add(search);
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		panel_1.add(searchButton);
		add(appList);
		add(cmbAppList);
		add(treatment);
		add(checkUp);
		add(hygiene);
		add(amalgam);
		add(composite);
		add(goldFilling);
		add(totalCost);
		add(submit);

	}
}
