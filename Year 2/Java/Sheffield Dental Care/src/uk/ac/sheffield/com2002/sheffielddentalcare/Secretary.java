/*
 * Main page for the secretary that takes them to the pages they use.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;
import javax.swing.JButton;
import javax.swing.JCheckBox;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Secretary extends Frame{
	
	public JButton registrations;
	public JButton appointments;
	public JButton healthcare;
	public JButton reviewPatientTreatment;
	public JButton calendar;
	public JButton checkout;
	
	public class ActionHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			if (button == registrations) {
				Register Frame = new Register();
				Frame.setVisible(true);
			} else if (button == appointments) {
				Appointments Frame = new Appointments();
				Frame.setVisible(true);
			} else if (button == healthcare) {
				Healthcare Frame = new Healthcare();
				Frame.setVisible(true);
			} else if (button == calendar) {
				new RunSecretaryCalendar();
			} else if (button == checkout) {
				CheckOut Frame = new CheckOut();
				Frame.setVisible(true);
			}
		}
	}
	
	public Secretary() {
		
		setTitle("Secretary");
		
		registrations = new JButton("Registrations");
		appointments = new JButton("Appointments");
		healthcare = new JButton("Healthcare");
		calendar = new JButton("Calendar");
		checkout = new JButton("Check Out");
		
		ActionListener actionListener = new ActionHandler();
		registrations.addActionListener(actionListener);
		appointments.addActionListener(actionListener);
		healthcare.addActionListener(actionListener);
		calendar.addActionListener(actionListener);
		checkout.addActionListener(actionListener);
		
		getContentPane().add(registrations);
		getContentPane().add(appointments);
		getContentPane().add(healthcare);
		getContentPane().add(calendar);
		getContentPane().add(checkout);
	}
}
