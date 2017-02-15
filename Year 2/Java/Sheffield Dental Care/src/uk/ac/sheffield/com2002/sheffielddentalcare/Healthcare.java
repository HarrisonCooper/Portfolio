/**Healthcare.java
 * 
 * This code allows the user to review a patient's subscription, including the code
 *  to reset the treatment credits remaining
 *  
 * */
package uk.ac.sheffield.com2002.sheffielddentalcare;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.*;

public class Healthcare extends SecretaryFrame implements ActionListener{
	//Define instances of Queries
	Queries dbQueries = new Queries();

	//JComponents for subscriptions
	private final String txtSubscribe = "Subscribe";
	private final String txtFind = "Search";

	private SearchResults data;
	private JLabel lbForename;
	private SearchPanel search = new SearchPanel();
	private JComboBox cmbPlans = new JComboBox();
	private JButton btnSubscribe,btnFind;
	
	//CONSTRUCTOR
	public Healthcare(){
		Container pane = getContentPane();
		search = new SearchPanel();
		
		//All JComponents

		lbForename = new JLabel("");
		btnSubscribe = new JButton(txtSubscribe);
		btnFind = new JButton(txtFind);
		btnFind.setEnabled(true);
		cmbPlans.setEnabled(false);
		btnSubscribe.setEnabled(false);
		
		btnSubscribe.addActionListener(this);
		btnFind.addActionListener(this);
		//Add components to the frame
		pane.add(search);
		pane.add(btnFind);
		pane.add(new JLabel("Select new healthcare plan:"));
		pane.add(cmbPlans);
		pane.add(btnSubscribe);
		
		populatePlanBox("");
	}
	
	public void actionPerformed(ActionEvent e){
		data = search.access();
		String event = e.getActionCommand();
		switch (event){
			case txtSubscribe:
				try {
					String newPlan = cmbPlans.getSelectedItem().toString();
					double costChange=dbQueries.getPlanDetails(newPlan)[4]-dbQueries.getPlanDetails(data.getPlanType())[4];
					
					//Open input dialog
					String dialogMessage = "If you continue, "+data.getTitle()+" "+data.getForename()+" "+data.getSurname()+" will be switched "+"\n"
							+"from their current plan, '"+data.getPlanType()+"', to the '"+newPlan+"'\n"
							+"with a monthly cost increase of "+costChange+"GBP.";
					int choice = JOptionPane.showOptionDialog(null,dialogMessage,"Continue?",JOptionPane.OK_CANCEL_OPTION,
							JOptionPane.INFORMATION_MESSAGE,null,null,"Cancel"); 
				   
					if (choice==0){
						if (data.resultExists()){
							//Change plan subscription
							dbQueries.changeSubscription(data.getAccountId(), dbQueries.getPlanDetails(newPlan)[0]);
							data = new SearchResults(data.getHouseNum(),data.getPostcode(),data.getBirthdate());
							updateResults(data);
						}else{
							System.out.println("Could not subscribe to plan, an error occurred");
						}
					}else {
						updateResults(search.access());
					}
				} catch (SQLException e2) {}
				search.onSearchClick();
				break;
				
			case txtFind: 
				search.onSearchClick();
				updateResults(search.access());
				break;
		}
		
	}
	void populatePlanBox(String planType){
		ResultSet rsPlans=dbQueries.otherPlans(planType);
		cmbPlans.removeAllItems();
		try {
			while(rsPlans.next()){
				cmbPlans.addItem(rsPlans.getString(1));
			}
		} catch (SQLException e) {}
	}
	
	
	void updateResults(SearchResults results){
		search.updateResultView(results);
		search.repaint();
		populatePlanBox(results.getPlanType());
		toggleComponents(results.getPlanType());
	}
	void toggleComponents(String planType){
		boolean empty=(planType=="");
		if (empty){
			btnSubscribe.setEnabled(false);
			cmbPlans.setEnabled(false);
		}else if(!empty){
			btnSubscribe.setEnabled(true);
			cmbPlans.setEnabled(true);
		}
	}

}


