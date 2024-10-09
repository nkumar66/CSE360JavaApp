package CSE360App;
import java.sql.*;

class DatabaseHelper {
	
	// Setting up the driver name and database URL.
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/firstDatabase";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 
	
	private Connection connection = null;		//to identify the connection
	private Statement statement = null; 		//to enter a statement to receive data from the SQL tables
	
	public void connectToDataBase() throws SQLException {
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);		//attempts to establish a connection
			statement = connection.createStatement(); 			//to enter a statement to get data from the database
			createTables();  // Create the necessary tables if they don't exist by calling below function
		} catch (ClassNotFoundException e) {			//if not found, prints error message below
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}
	
	private void createTables() throws SQLException {			//function to create table since there doesn't exist one
		String userTable = "CREATE TABLE IF NOT EXISTS cse360users ("		//table name will be cse360users
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "		//column for id that auto increments and is primary key
				+ "userName VARCHAR(255),"					//column for userName
				+ "email VARCHAR(255) UNIQUE, "			//column for email
				+ "password VARCHAR(255), "			//column for password
				+ "role VARCHAR(20))";			//column for role
		statement.execute(userTable);			//executes the command
	}
	
	public boolean isDatabaseEmpty() throws SQLException {		//function to check if the database is empty
	    String query = "SELECT COUNT(*) AS count FROM cse360users";
	    ResultSet resultSet = statement.executeQuery(query);
	    if (resultSet.next()) {
	        return resultSet.getInt("count") == 0;
	    }
	    return true;
	}


}
