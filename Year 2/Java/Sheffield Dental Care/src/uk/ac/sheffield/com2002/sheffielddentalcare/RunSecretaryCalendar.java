/*
 * Class displays a JFrame containing a weekday view of the calendar, there are two tabs; 
 * one for dentist and its corresponding appointments, and one for hygienist and its 
 * corresponding appointments.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class RunSecretaryCalendar extends JFrame implements ActionListener{
	AddCalendarAppointments dentistCal = new AddCalendarAppointments(true);
	AddCalendarAppointments hygienistCal = new AddCalendarAppointments(false);
	int month, day, year;
	int dWeekNo, hWeekNo = 0;
	
	public RunSecretaryCalendar(){
		JFrame calendarFrame = new JFrame("Secretary Calendar");
		calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(1430,600));
		JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP);
        tabs.addTab("Dentist", dentistCal.calendarPanel);
        tabs.addTab("Hygienist", hygienistCal.calendarPanel);
        calendarFrame.getContentPane().add(tabs);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        day = cal.getTime().getDate();
        month = cal.getTime().getMonth();
        year = cal.getTime().getYear();
        dentistCal.calendarPanel.dateLabel.setText("Week Commencing: "
        		+ "Monday "+String.valueOf(day)+" "+getMonthForInt(month));
        hygienistCal.calendarPanel.dateLabel.setText("Week Commencing: "
        		+ "Monday "+String.valueOf(day)+" "+getMonthForInt(month));
        dentistCal.calendarPanel.nextWeek.addActionListener(this);
		dentistCal.calendarPanel.lastWeek.addActionListener(this);
		hygienistCal.calendarPanel.nextWeek.addActionListener(this);
		hygienistCal.calendarPanel.lastWeek.addActionListener(this);
		calendarFrame.pack();
        calendarFrame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button==(dentistCal.calendarPanel.nextWeek)|| 
				button==(dentistCal.calendarPanel.lastWeek)){
			Calendar dCal = Calendar.getInstance();
			dCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			if(button==(dentistCal.calendarPanel.nextWeek)){dWeekNo++;}
			else{dWeekNo--;}
			dCal.add(Calendar.WEEK_OF_YEAR, (1*dWeekNo));
			int dDay = dCal.getTime().getDate();
	        int dMonth = dCal.getTime().getMonth();
	        dentistCal.calendarPanel.dateLabel.setText("Week Commencing: "
	        		+ "Monday "+String.valueOf(dDay)+" "+getMonthForInt(dMonth));
			dentistCal.calendarPanel.dateLabel.repaint();
		}
		else if(button==(hygienistCal.calendarPanel.nextWeek)|| 
				button==(hygienistCal.calendarPanel.lastWeek)){
			Calendar hCal = Calendar.getInstance();
			hCal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			if(button==(hygienistCal.calendarPanel.nextWeek)){hWeekNo++;}
			else{hWeekNo--;}
			hCal.add(Calendar.WEEK_OF_YEAR, (1*hWeekNo));
			int hDay = hCal.getTime().getDate();
	        int hMonth = hCal.getTime().getMonth();
	        hygienistCal.calendarPanel.dateLabel.setText("Week Commencing: "
	        		+ "Monday "+String.valueOf(hDay)+" "+getMonthForInt(hMonth));
			hygienistCal.calendarPanel.dateLabel.repaint();
		}
	}
	 String getMonthForInt(int num) {
	        String month = "";
	        DateFormatSymbols dfs = new DateFormatSymbols();
	        String[] months = dfs.getMonths();
	        if (num >= 0 && num <= 11 ) {
	            month = months[num];
	        }
	        return month;
	    }
}
