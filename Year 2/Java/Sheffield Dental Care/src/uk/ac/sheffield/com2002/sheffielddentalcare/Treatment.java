/**Treatment.java
 * 
 * A class to make the storage and call of treatment data much neater
 *  
 * */
package uk.ac.sheffield.com2002.sheffielddentalcare;

public class Treatment {
	private String treatmentName;
	private double treatmentCost;
	public Treatment(String name, double cost){
		treatmentName = name;
		treatmentCost=cost;
	}
	public String getName(){return treatmentName;}
	public double getCost(){return treatmentCost;}

}
