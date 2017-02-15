package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JCheckBox;

public class Dentist extends PartnerFrame{
	public Dentist(){
		RecordJPanel recordPanel = new RecordJPanel();
		//getContentPane().add(recordPanel);
		
		RunPartnerCalendar calendar = new RunPartnerCalendar(true);
		//calendar.toFront();
		//add(calendar);
		
		setTitle("Dentist");
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		//Calendar panel
		//JPanel calendar = new JPanel();
		//tabbedPane.addTab("Calendar", null, calendar, null);
		
		//Record Appointment panel
		tabbedPane.addTab("Record Appointment", null, recordPanel, null);
		recordPanel.setLayout(new BoxLayout(recordPanel, BoxLayout.Y_AXIS));
	}
}