/*
 * Frame for hygienist that shows calendar and the record panel.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Hygienist extends PartnerFrame{
	private JTable table;
	private JTextField txtPatientId;
	public Hygienist() {
		
setTitle("Hygienist");
		RecordJPanel recordPanel = new RecordJPanel();
		RunPartnerCalendar calendar = new RunPartnerCalendar(false);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.NORTH);
		
		//tabbedPane.addTab("Calendar", null, calendar, null);
		
		//Record Appointment panel
		tabbedPane.addTab("Record Appointment", null, recordPanel, null);
		recordPanel.setLayout(new BoxLayout(recordPanel, BoxLayout.Y_AXIS));
		
	}
}