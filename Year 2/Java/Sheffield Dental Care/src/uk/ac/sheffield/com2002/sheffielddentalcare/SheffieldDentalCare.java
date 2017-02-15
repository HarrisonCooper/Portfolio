package uk.ac.sheffield.com2002.sheffielddentalcare;

import uk.ac.sheffield.com2002.sheffielddentalcare.Frame;

public class SheffieldDentalCare {
	public static SqlConnection db = new SqlConnection();
	public static void main(String[] args) {
		Navigation Main = new Navigation();
		Main.setVisible(true);
		Runtime.getRuntime().addShutdownHook(new Thread() {
		      public void run() {
		    	  db.closeConnection();
		      }
		    });
	}
}
