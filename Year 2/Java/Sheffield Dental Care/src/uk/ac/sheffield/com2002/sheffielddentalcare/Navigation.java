package uk.ac.sheffield.com2002.sheffielddentalcare;


import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SpringLayout;

import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.CardLayout;
import java.awt.BorderLayout;

@SuppressWarnings("serial")

public class Navigation extends Frame{
	
	public Navigation() {
		
		setTitle("Navigation");
		
		JButton btnHygienist = new JButton("Hygienist");
		btnHygienist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Hygienist Frame = new Hygienist();
				Frame.setVisible(true);
			}
		});
		getContentPane().add(btnHygienist);
		
		JButton btnDentist = new JButton("Dentist");
		btnDentist.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Dentist Frame = new Dentist();
				Frame.setVisible(true);
			}
		});
		getContentPane().add(btnDentist);
		
		JButton btnSecretary = new JButton("Secretary");
		btnSecretary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Secretary Frame = new Secretary();
				Frame.setVisible(true);
			}
		});
		getContentPane().add(btnSecretary);
	}
	
}

