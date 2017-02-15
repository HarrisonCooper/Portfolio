/*
 * Class displays a JFrame containing a single day view of the calendar.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RunPartnerCalendar extends JPanel implements ActionListener{
	int dayCounter = 0;
	int virtualDay;
	boolean dentist;
	AddCalendarAppointments calendar;
	public RunPartnerCalendar(boolean dentist){
		if(dentist){this.dentist = true;
		System.out.println("Should be true: " + dentist);}
		else{this.dentist = false;
		System.out.println("Shouldn't run: "+ dentist);}
		calendar = new AddCalendarAppointments(dentist);
		calendar.secretary = false;
		
		JFrame calendarFrame = new JFrame("Partner Calendar");
		//JFrame frame = new PartnerFrame();
		//frame.setTitle("Partner Calendar");
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();
		//setPreferredSize(new Dimension(screenDimensions.width/2), screenDimensions.height);		
		setLocation(new Point(screenDimensions.width/4, screenDimensions.height/4));
		calendarFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//setPreferredSize(new Dimension(1430,600));
		calendarFrame.getContentPane().add(calendar.calendarPanel);
		calendar.calendarPanel.lastWeek.setText("<< Previous Day");
        calendar.calendarPanel.nextWeek.setText("Next Day >>");
        calendar.calendarPanel.nextWeek.addActionListener(this);
		calendar.calendarPanel.lastWeek.addActionListener(this);
		calendarFrame.pack();
        calendarFrame.setVisible(true);
        calendarFrame.toFront();
        calendarFrame.repaint();
        calendar.addAppointments();
        displayToday();
        Calendar cal = Calendar.getInstance();
        virtualDay = cal.getTime().getDay();
        Calendar cal2 = Calendar.getInstance();
        calendar.calendarPanel.dateLabel.setText(String.valueOf(cal2.getTime().getDate()) +" "+ 
        		getMonthForInt(cal2.getTime().getMonth()));
	}
	void removeColumn(int column){
		calendar.calendarPanel.table.getColumnModel().getColumn(column).setMinWidth(0);
		calendar.calendarPanel.table.getColumnModel().getColumn(column).setMaxWidth(0);
		calendar.calendarPanel.table.getColumnModel().getColumn(column).setWidth(0);
	}
	void addColumn(int column){
		calendar.calendarPanel.table.getColumnModel().getColumn(column).setMinWidth(720);
		calendar.calendarPanel.table.getColumnModel().getColumn(column).setMaxWidth(720);
		calendar.calendarPanel.table.getColumnModel().getColumn(column).setWidth(750);
	}
	void addAllColumns(){
		for(int i= 0; i<6; i++){
			addColumn(i);
		}
	}
	void displayToday(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, (1*calendar.partnerNextDay));
		int today = cal.getTime().getDay();
		addAllColumns();
		for (int i=0; i<=5; i++){
			if(today!=i && i!=0){
				removeColumn(i);
				
			}
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
	public void actionPerformed(ActionEvent e) {
		calendar.button = (JButton) e.getSource();
		Calendar cal = Calendar.getInstance();
		
		if(calendar.button==calendar.calendarPanel.nextWeek){
			dayCounter++;
			cal.add(Calendar.DAY_OF_YEAR, (1*dayCounter));
			calendar.calendarPanel.dateLabel.setText(String.valueOf(cal.getTime().getDate()) +" "+ 
	        		getMonthForInt(cal.getTime().getMonth()));
			
			calendar.partnerNextDay = (calendar.partnerNextDay +1) % 7;
			displayToday();
			if(virtualDay==0){
				calendar.weekMultiplier++;
				calendar.addAppointments();
			}
			if(virtualDay<=6){virtualDay++;}
			if(virtualDay==7){virtualDay=0;}
		}
		
		if(calendar.button==calendar.calendarPanel.lastWeek){
			dayCounter--;
			cal.add(Calendar.DAY_OF_YEAR, (1*dayCounter));
			calendar.calendarPanel.dateLabel.setText(String.valueOf(cal.getTime().getDate()) +" "+ 
	        		getMonthForInt(cal.getTime().getMonth()));
			
			calendar.partnerNextDay = (calendar.partnerNextDay -1) % 7;
			displayToday();
			if(virtualDay==1){
				calendar.weekMultiplier--;
				calendar.addAppointments();
			}
			if(virtualDay==0){virtualDay=7;}
			if(virtualDay<=7){virtualDay--;}
		}
    } 
}
