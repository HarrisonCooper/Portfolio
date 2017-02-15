/*
 * Class containing the objects that will be used to populate any cells within
 * the calendar.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;

public class CalendarData {
	private String [] timeArray = {"09:00", "09:20", "09:40", "10:00", "10:20", "10:40", "11:00", "11:20", "11:40", "12:00", "12:20", "12:40",
			"13:00", "13:20", "13:40", "14:00", "14:20", "14:40", "15:00", "15:20", "15:40", "16:00", "16:20", "16:40", "17:00"};

	public String[] getTimeArray() {
		return timeArray;
	}
	
	private String[] columnNames = {"Time","Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

	public String[] getColumnNames() {
		return columnNames;
	}
	
	private Object [] [] data = new Object [25] [6];

	public Object[][] getData() {
		return data;
	}

	public void setData(Object[][] data) {
		this.data = data;
	}
	
	
}
