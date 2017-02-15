package uk.ac.sheffield.com2002.sheffielddentalcare;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class Appointments extends SecretaryFrame{

	public Appointments() {
		
		BookAppointmentJPanel book = new BookAppointmentJPanel();
		CancelAppointmentJPanel cancel = new CancelAppointmentJPanel();
		
		setTitle("Appointments");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		//Book Appointment panel
		tabbedPane.addTab("Book Appointment", null, book, null);
		//Find and Cancel Appointment
		tabbedPane.addTab("Find / Cancel Appointment", null, cancel, null);
	
	}
}
