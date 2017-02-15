package uk.ac.sheffield.com2002.sheffielddentalcare;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JButton;

public class AddCalendarAppointments implements ActionListener{
	String [] appTime;
	String [] appDescription;
    JButton button;
    boolean secretary = true; 
    public CalendarFrame calendarPanel = new CalendarFrame();
    int partnerNextDay = 0;
    int weekMultiplier = 0; // if zero, then displays this week
   /* String [] appTime = {"Tue Nov 15 10:00:00 GMT 2016", "Thu Nov 17 12:00:00 GMT 2016",
            "Thu Nov 10 10:20:00 GMT 2016", "Tue Nov 22 09:40:00 GMT 2016",
            "Thu Nov 24 10:00:00 GMT 2016"};
    String [] appDescription = {"Gerry appointment", "Kaleigh appointment",
            "Last week appointment", "next week appointment", "Thursday next week"}; */
    public AddCalendarAppointments(boolean dentist){

        calendarPanel.nextWeek.addActionListener(this);
        calendarPanel.lastWeek.addActionListener(this);
        AppointmentExtractor a = null;
		try {
			a = new AppointmentExtractor();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if(dentist){
        	appTime  = a.appointmentTimeDentist.toArray(new String[a.appointmentTimeDentist.size()]);
        	appDescription = a.patientForenamesDentist.toArray(new String[a.patientForenamesDentist.size()]);
        	
        }
        else{
        	appTime  = a.appointmentTimeHygienist.toArray(new String[a.appointmentTimeHygienist.size()]);
        	appDescription = a.patientForenamesHygienist.toArray(new String[a.patientForenamesHygienist.size()]);
        }
        
        addAppointments();
    }
    void cleanseCalendar(){
        for (int i=1; i<6; i++){
            for (int j=0; j<25; j++){
                calendarPanel.data.getData() [j] [i] = null;
            }
        }
        calendarPanel.table.repaint();
    }
    void addAppointments(){
        cleanseCalendar();
        Calendar cal = Calendar.getInstance();
        for (int k = 0; k<appTime.length; k++){
            cal.setTimeInMillis(getMonday());
            for (int i=1; i<6; i++){
                for (int j=0; j<25; j++){
                    if (appTime [k].equals(cal.getTime().toString())){
                        calendarPanel.data.getData() [j] [i] = appDescription [k];
                    }
                    cal.add(Calendar.MINUTE, 20);
                    if ((cal.getTime().getHours()==17) && (cal.getTime().getMinutes()==20)){
                        cal.add(Calendar.MINUTE, 40);
                        cal.add(Calendar.HOUR, 15);
                    }
                }
            }
        } 
        calendarPanel.table.repaint();
    }
    long getMonday(){
       Calendar cal = Calendar.getInstance();
       cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //set to monday this week
       cal.set(Calendar.HOUR_OF_DAY, 9);// set time to 09:00 in the morning
       cal.set(Calendar.MINUTE, 0);
       cal.set(Calendar.SECOND, 0);
       cal.set(Calendar.MILLISECOND, 0); 
       cal.add(Calendar.WEEK_OF_YEAR, (1*weekMultiplier)); // changes week
       return cal.getTimeInMillis();
   }
    public void actionPerformed(ActionEvent e) {
        button = (JButton) e.getSource();
       if (button == calendarPanel.nextWeek && secretary){
           weekMultiplier ++;
           addAppointments();
       }
       else if (button == calendarPanel.lastWeek && secretary){
           weekMultiplier --;
           addAppointments();
       }
   }
}