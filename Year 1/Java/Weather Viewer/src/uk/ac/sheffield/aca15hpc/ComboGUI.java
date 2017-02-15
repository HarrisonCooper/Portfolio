/*
 * The user input GUI. uses a flow layout and places the drop down boxes and submit button in logical order.
 */
package uk.ac.sheffield.aca15hpc;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;

@SuppressWarnings("serial")

public class ComboGUI extends Frame{

	Weather weather = new Weather();

	public JComboBox<?> airport;
	public JComboBox<?> day;
	public JComboBox<?> month;
	public JComboBox<?> year;

	//The data values for each drop down box.
	String[] airportString = { "London Heathrow", "London Gatwick"};
	String[] dayString = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
	List<String> monthString = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
	String[] yearString = {"1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016"};

	//If no data is changed, the strings are set to the first element of each array.
	String selectedAirport = airportString[0];
	String selectedDay = dayString[0];
	String selectedMonth = monthString.get(0);
	String selectedYear = yearString[0];
	int monthInt;

	//When the submit button is pressed, this passes the information onto the URLReader and runs the rest of the program.
	private class ButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			new UrlReader(selectedAirport, selectedDay, monthInt, selectedYear);
			weather.run();
		}
	}
	
	//If a drop down box is selected, this takes that value.
	private class ComboHandler implements ItemListener{
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED){
				selectedAirport = (String) airport.getSelectedItem();
				selectedDay = (String) day.getSelectedItem();
				monthInt = monthString.indexOf((String) month.getSelectedItem());
				selectedYear = (String) year.getSelectedItem();
			}
		}
	}

	public ComboGUI() {
		setTitle("Choose your date and location.");

		airport = new JComboBox<Object>(airportString);
		day = new JComboBox<Object>(dayString);
		month = new JComboBox<Object>(monthString.toArray());
		year = new JComboBox<Object>(yearString);

		airport.addItemListener(new ComboHandler());
		day.addItemListener(new ComboHandler());
		month.addItemListener(new ComboHandler());
		year.addItemListener(new ComboHandler());

		JButton button = new JButton("Submit");
		button.addActionListener(new ButtonHandler());

		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		contentPane.add(airport);
		contentPane.add(day);
		contentPane.add(month);
		contentPane.add(year);
		contentPane.add(button);

	}
}

