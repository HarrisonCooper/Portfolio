package uk.ac.sheffield.com2002.sheffielddentalcare;


//Import required packages
import java.sql.*;

public class SqlConnection {

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://stusql.dcs.shef.ac.uk";

	//do not delete these are important and ARE used, eclipse lies
	private Connection con;
	private Statement st;
	private ResultSet rs;

	// Database credentials
	private static final String USER = "team027";
	private static final String PASS = "13d186b5";

	//Open the sql connection in the constructor
	public SqlConnection(){
		openConnection();
	}
	
	//Execute a query from the database and return the result set
	public ResultSet getQuery(String query) throws SQLException{
		st = con.createStatement();
		rs = st.executeQuery(query);
		return rs;
	}
	//Adding to a database
	public void insertIntoDatabase(String command) throws SQLException{
		st = con.createStatement();
		st.executeUpdate(command);
	}
	
	//Attempt to open the database
	private void openConnection(){
		try {
			Class.forName(JDBC_DRIVER);
			
			//Establish connection
			con = DriverManager.getConnection(
					"jdbc:mysql://stusql.dcs.shef.ac.uk/" + USER + "?user=" + USER + "&password=" + PASS);
			
		} catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException:\n" + ex.toString());
			ex.printStackTrace();

		} catch (SQLException ex) {
			System.out.println("SQLException:\n" + ex.toString());
			ex.printStackTrace();
		}
	}
	public void closeConnection(){
		//Attempt to close the database if open
		try{if (!con.isClosed()){con.close();}
			
		}catch (SQLException ex){
			System.out.println("SQLException:\n" + ex.toString());
			ex.printStackTrace();
		}
	}
	
	
	
}