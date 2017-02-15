/**FinalisedCheckOut.java
 * 
 * Produces a JFrame showing the receipt and the amount to be paid by the patient
 * */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

public class FinalisedCheckOut extends Frame{
	private Appointment appointment;
	private Patient patient;
	private Queries db = new Queries();
	//private double total;
	public FinalisedCheckOut(int aptId,ArrayList<Treatment> trtmnts){
		appointment = new Appointment(aptId);
		patient = new Patient(appointment.getPatientId());
		double cost = appointment.getTotalCost();
		
		JButton btnConfirm = new JButton("Confirm");
		for(Treatment t : trtmnts){
			if (t.getCost()==0){
				cost -=db.getTreatmentCost(t.getName());
			}
			add(new JLabel("Treatment:\t "+t.getName()+", original cost: "+db.getTreatmentCost(t.getName())+", amount due: "+t.getCost()));
		}
		setTitle("Order Receipt");
		add(new JLabel("Original amount due: "+appointment.getTotalCost()));
		add(new JLabel("New amount due (after plan deductions): "+cost));
		add(new JLabel("Credits left: Checkups->"+patient.getRemainingCheckups()+", hygiene visits->"+patient.getRemainingHygieneVisits()+", repairs left->"+patient.getRemainingRepairs()));
		
	}
	
}
