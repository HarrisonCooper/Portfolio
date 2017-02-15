/**RegistrationUtilities.java
 * 
 * This class contains extra validation methods for the registration page
 */
package uk.ac.sheffield.com2002.sheffielddentalcare;


import java.util.*;
import java.time.YearMonth;

public class RegistrationUtilities {
	//Get current date
	private Calendar calendar = Calendar.getInstance();
	
	
	private final int START_YEAR=1945;
	
	//Declare current date variables
	private int currentYear =calendar.get(Calendar.YEAR);
	private int currentMonth=calendar.get(Calendar.MONTH);
	private int currentDay=calendar.get(Calendar.DAY_OF_MONTH); 
	private int yearsAvailable=currentYear-START_YEAR;

	
	
	public Integer[] getYears(){
		Integer[] yearArray=new Integer[yearsAvailable+1];
		for(Integer i=0;i<yearsAvailable;i++){
			yearArray[i]=currentYear-i;
		}
		return yearArray;
	}
	
	public Integer[] getMonths(){
		Integer[] months = {1,2,3,4,5,6,7,8,9,10,11,12};
		return months;
	}
	
	
	public Integer[] getDays(){
		Integer[] dayArray = new Integer[31];
		//Store in array
		for(int i=0;i<dayArray.length;i++)
			dayArray[i]=i+1;
		
		return dayArray;
	}


	//Check if the date of birth is valid
	public boolean isValid(int year, int month, int day){
		YearMonth yearMonthObject = YearMonth.of(year, month);
		int maxDays = yearMonthObject.lengthOfMonth();
		boolean validDate=false;
		if (year==currentYear&&month==currentMonth&&day<=maxDays){
			validDate=true;
		}else if(day<=maxDays){
			validDate=true;
		}
		return validDate;
	}

	//Returns true if valid, else returns false
	public boolean validPostcode(String str){
		String alphaNumRegex="^[a-zA-Z0-9]*$";
		String postcodeRegex = "^(GIR ?0AA|[A-PR-UWYZ]([0-9]{1,2}|([A-HK-Y][0-9]([0-9ABEHMNPRV-Y])?)|"
				+ "[0-9][A-HJKPS-UW]) ?[0-9][ABD-HJLNP-UW-Z]{2})$";
		str = formatPostcode(str);
		return str.matches(postcodeRegex)&&str.matches(alphaNumRegex);
	}
	
	//Input postcode to convert to upper case and omit spaces
	public String formatPostcode(String postcode){
		String formatted= "";
		char current;
 		postcode = postcode.replaceAll("\\s","");
		char[] postcodeArr = postcode.toCharArray(); 
		for (int i=0;i<postcodeArr.length;i++){
			current = postcodeArr[i];
			if (Character.isLetter(current)){
				if (Character.isLowerCase(current)){
					current= Character.toUpperCase(current);
				}
			}
			formatted += current;
		}
		return formatted;
	}
	
	//Check if the string can be parsed into int
	public boolean isNumeric(String str)  
	{  
		  return str.matches("-?\\d+(\\.\\d+)?"); 
	}
}
