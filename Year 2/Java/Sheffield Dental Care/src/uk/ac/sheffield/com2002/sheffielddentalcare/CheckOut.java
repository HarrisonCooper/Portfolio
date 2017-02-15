/**CheckOut.java
 * 
 * Produces a JFrame where the secretary can check out an appointment which has been
 * reviewed by a partner and updates the affected attributes in the database
 * */

package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CheckOut extends SecretaryFrame implements ActionListener{
	private final String searchTxt = "Search";
	private final String checkoutTxt = "Check out";
	private SearchResults data;
	private Queries db = new Queries();

	private DefaultListModel<String> listModel= new DefaultListModel<String>();
	private ArrayList<String> appointmentComboList = new ArrayList<String>();
	
	JList appointmentList = new JList(listModel);
	SearchPanel search = new SearchPanel();
	JButton btnSearch = new JButton(searchTxt);
	JPanel appDisplay = new JPanel();
	JButton btnPay = new JButton(checkoutTxt);
	JComboBox cmbAppointments = new JComboBox();
	
	//Define the constructor
	public CheckOut(){
		setTitle("Check out patient");
		btnSearch.addActionListener(this);			
		btnPay.addActionListener(this);
		btnPay.setEnabled(false);
		cmbAppointments.setEnabled(false);
		appDisplay.setEnabled(false);
		
		appDisplay.add(appointmentList);
		//Load all JComponents
		add(search);
		add(btnSearch);
		add(appDisplay);
		add(new JLabel("Select appointment id from below:"));
		add(cmbAppointments);
		add(btnPay);
		
	}
	public void actionPerformed(ActionEvent e){
		data=search.access();
		String event = e.getActionCommand().toString();
		switch(event){
			case searchTxt:
				//run when the 'search' button is pressed
				search.onSearchClick();
				updateResults(search.access());
				break;
			case checkoutTxt:
				//run when the 'checkout' button is pressed
				Appointment appoint = new Appointment(Integer.parseInt(cmbAppointments.getSelectedItem().toString()));
				ArrayList<Treatment> receipts = db.getAppointmentReceipt(appoint.getId(), search.access().getPatientId());
				
				//Open input dialog
				String dialogMessage = "Customer will now pay for the selected appointment.";
				int choice = JOptionPane.showOptionDialog(null,dialogMessage,"Finalise Payment?",JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE,null,null,"Cancel"); 
				if(choice==0){
					//if the user clicks 'OK' then run open finalised checkout frame
					db.payAppointment(appoint.getId());
					updateResults(search.access());
					JFrame frame = new FinalisedCheckOut(appoint.getId(),receipts);
					frame.setVisible(true);
				}
				
		}
		
	}
	void updateResults(SearchResults results){
		search.updateResultView(results);
		buildList();
		search.repaint();
	}

	
	void buildList(){
		listModel.clear();
		cmbAppointments.removeAllItems();
		int treatmentCount=0;
		String treatments = "";
		ArrayList<Treatment> receipt;
		ArrayList<Appointment> unpaid = db.getUnpaidAppointments(search.access().getPatientId());
		for (Appointment app : unpaid){
			treatmentCount=0;
			//System.out.println("1 APPOINTMENT");
			receipt = db.getAppointmentReceipt(app.getId(),app.getPatientId());
			for (Treatment treatment : receipt){
				treatmentCount+=1;
				//System.out.println("1 TREATMENT");
			}
			treatments = "Appointment id = "+app.getId()+", number of treatments: "+treatmentCount+", ";
			listModel.addElement(treatments + app.getStart()+" to "+app.getEnd()+" on "+app.getDate()+". Total Cost: "+app.getTotalCost());
			cmbAppointments.addItem(app.getId());
		}
		if(unpaid.isEmpty()){
			btnPay.setEnabled(false);
		}else{
			btnPay.setEnabled(true);
			cmbAppointments.setEnabled(true);
		}
	}

}
