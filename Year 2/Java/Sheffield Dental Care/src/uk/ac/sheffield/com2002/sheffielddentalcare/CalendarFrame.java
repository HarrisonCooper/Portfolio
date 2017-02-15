/*
 * Creates a JPanel containing a calendar that will be used to populate frames 
 * for the secretary and partner calendars.
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class CalendarFrame extends JPanel{
	public JPanel panel;
	public JPanel panel_1;
	public JButton nextWeek;
	public JButton lastWeek;
	public JTable table;
	public JLabel dateLabel = new JLabel();
	CalendarData data = new CalendarData();
	CalendarFrame(){
		panel = new JPanel(); // create JPanel
        lastWeek = new JButton("<< Last Week");
        lastWeek.setContentAreaFilled(false);
        lastWeek.setFocusable(false);
        add(lastWeek);
        panel_1 = new JPanel();
        add(panel_1);
        panel_1.add(dateLabel);
        nextWeek = new JButton("Next Week >>");
        nextWeek.setContentAreaFilled(false);
        nextWeek.setFocusable(false);
        add(nextWeek);
        //add(dateLabel);
        addTable();// create table for calendar
        
        setPreferredSize(new Dimension(1430,600));
        // insert time slots into calendar
        for(int i=0; i< 25; i++){
        	for(int j=0; j<24; j++){
        		data.getData()[i] [0] = data.getTimeArray() [i];
        		
        	}
        }
	}
	void addTable(){
		table = new JTable(data.getData(), data.getColumnNames());
        table.setPreferredScrollableViewportSize(new Dimension(1400, 500));
        table.setFillsViewportHeight(true);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        table.setShowGrid(true);
        table.setShowVerticalLines(true);
        JTableHeader days = table.getTableHeader();
        days.setFont(new Font("Dialog", Font.CENTER_BASELINE, 16));
        days.setBackground(Color.GRAY);
        days.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        table.setRowHeight(40);
        table.setGridColor(Color.BLACK);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(false);
        table.setFocusable(false);
	}
}