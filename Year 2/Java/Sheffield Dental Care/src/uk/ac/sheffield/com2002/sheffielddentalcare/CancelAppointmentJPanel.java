/*
 * This panel allows for the secretary to cancel an appointment given the patients
 * unique details. If the patient has more than one appointment, it will list out 
 * all the appointments that patient has with the pratice.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CancelAppointmentJPanel extends JPanel{
	
	Queries dbQueries= new Queries();
	
	private JButton searchButton;
	private JButton submit;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblFindID;
	private JLabel lblCancelAppointment;
	private JList<String> appList;
	private JComboBox cmbAppList;
	
	private SearchPanel search = new SearchPanel();
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	
	public CancelAppointmentJPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		panel = new JPanel();
		panel_1 = new JPanel();
		panel_2 = new JPanel();
		lblFindID = new JLabel("Find Patient Appointment ID");
		lblFindID.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblCancelAppointment = new JLabel("Cancle Appointment Number:");
		lblCancelAppointment.setAlignmentX(Component.CENTER_ALIGNMENT);
		searchButton = new JButton("Search");
		searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		submit = new JButton("Submit");
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		appList = new JList<String>(listModel);
		cmbAppList = new JComboBox();
		
		searchButton.addActionListener(new ActionHandler());
		submit.addActionListener(new ActionHandler());
		
		search.onSearchClick();
		
		add(lblFindID);
		add(panel);
		panel.add(search);
		add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		panel_2.add(searchButton);
		panel_2.add(appList);
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		panel_1.add(lblCancelAppointment);
		panel_1.add(cmbAppList);
		panel_1.add(submit);
	}

	
	/*Action Listener works  by: 1) when search button activated, it returns a
	 * list of the appointments that patient currently has.
	 * 2) When submit button is activated, that appointment is deleted from the DB.
	 */
	public class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if (button == searchButton) {
				search.onSearchClick();
				int acc_id = search.results.getAccountId();
				int pat_id = dbQueries.getPatientId(acc_id);
				ResultSet rs = dbQueries.getAppointments(pat_id);
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
				try {
					int app_id = (int) cmbAppList.getSelectedItem();
					dbQueries.cancleAppointment(app_id);
				} catch (SQLException e1) {
				}
			}
		}
	}
	
	void updateResults(SearchResults results){
		search.updateResultView(results);
		search.repaint();
	}
	
}
