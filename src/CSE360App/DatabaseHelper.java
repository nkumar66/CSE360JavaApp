package src.CSE360App;
import java.sql.*;

import org.bouncycastle.util.Arrays;


class DatabaseHelper {
	
	// Setting up the driver name and database URL.
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/cse360Database";  

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = ""; 
	
	Statement statement;
	
	private Connection connection = null;		//to identify the connection
	
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
	
	// Creates articleTable and userTable
	private void createTables() throws SQLException {
	    String articleTable = "CREATE TABLE IF NOT EXISTS cse360articles ("
	            + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "   // Unique article ID
	            + "title VARCHAR(255), "                     // Article title
	            + "publicTitle VARCHAR(255)"				 // Title for public use
	            + "author VARCHAR(255), "                    // Author's name
	            + "abstract TEXT, "                          // Abstract for the article
	            + "publicAbstract TEXT"						 // Abstract for public use
	            + "body LONGTEXT, "                          // Full article body
	            + "skillLevel VARCHAR(50), " 				 // Difficulty level
	            + "groupID VARCHAR(100), "                   // Group identifier for related articles
	            + "accessLevel VARCHAR(50), " 				 // Access level for viewing
	            + "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " // Timestamp for article creation
	            + "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" // Timestamp for last update
	            + ")";
	    String userTable = "CREATE TABLE IF NOT EXISTS cse360users ("
	    		+ "id INT AUTO-INCREMENT PRIMARY KEY, "		// Unique user ID
	    		+ "user VARCHAR(255) UNIQUE"				// Username
	    		+ "pass VARCHAR(255)"						// Password
	    		+ "email VARCHAR(255)"						// Email
	    		+ "firstName VARCHAR(255)"					// User First Name
	    		+ "middleName VARCHAR(255)"					// User Middle Name
	    		+ "lastName VARCHAR(255)"					// User Last Name
	    		+ "preferredFirstName VARCHAR(255)"			// User preferred First Name
	    		+ "isOTP BOOLEAN"							// If it is OTP password
	    		+ "oneTimePassword VARCHAR(255)"			// Current OTP
	    		+ "otpExpiration TIMESTAMP"					// When OTP expires
	    		+ ")";
	    statement.execute(articleTable);
	    statement.execute(userTable);
	}

	// Checks if the article database is empty
	public boolean isArticleDatabaseEmpty() throws SQLException {
	    String query = "SELECT COUNT(*) AS count FROM cse360articles"; // SQL Query
	    ResultSet resultSet = statement.executeQuery(query);
	    if (resultSet.next()) {
	        return resultSet.getInt("count") == 0; // If there is an article, return true
	    }
	    return true; // Else, return false
	}
	
	// Takes in inputs for an article and inserts it into the database
	public void addArticle(String title, String publicTitle, String author, String abstr, String publicAbstr, String body, SkillLevel skill, String groupID, AccessLevel access, Date currDate) throws SQLException {
		// SQL query 
		String insertArticle = "INSERT INTO cse360 (title, publicTitle, author, abstract, publicAbstract, body, skillLevel, groupID, accessLevel, createdAt, updatedAt) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(insertArticle)){
			pstmt.setString(1, title);
			pstmt.setString(2, publicTitle);
			pstmt.setString(3, author);
			pstmt.setString(4, abstr);
			pstmt.setString(5, publicAbstr);
			pstmt.setString(6, body);
			pstmt.setString(7, skill.toString());
			pstmt.setString(8, groupID);
			pstmt.setString(9, access.toString());
			pstmt.setDate(10, new java.sql.Date(currDate.getTime()));
			pstmt.setDate(11, new java.sql.Date(currDate.getTime()));
		}
	}
	
	// Checks based on title if an article exists with that title
	public boolean doesArticleExist(String title) {
	    String query = "SELECT COUNT(*) FROM cse360articles WHERE title = ?"; // SQL query
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, title);
	        ResultSet rs = pstmt.executeQuery(); // If there is a query/queries, it will show up in rs.
	        
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	// Displays all the articles in the database
	public void displayArticles() throws Exception {
		// SQL query
		String sql = "SELECT * FROM cse360articles"; 
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql); 

		while(rs.next()) { 
			String title = rs.getString("title");
			String  author = rs.getString("author"); 
			String abstr = rs.getString("abstr");
		} 
		/*
		 * IMPLEMENT DISPLAYING IN JAVAFX
		 */
	}
	
	// Edits the articles in whatever way
	public void editArticles(long id, String title, String publicTitle, String author, String abstr, String publicAbstr, String body, SkillLevel skill, String groupID, AccessLevel access) throws Exception {
		// SQL query
		String editSQL = "UPDATE articles SET title = ?, publicTitle = ?, author = ?, abstract = ?, publicAbstract = ?, body = ?, skillLevel = ?, groupID = ?, access_level = ?, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(editSQL)){
	    	pstmt.setString(1, title);
	    	pstmt.setString(2, publicTitle);
	    	pstmt.setString(3, author);
	    	pstmt.setString(4, abstr);
	    	pstmt.setString(5, publicAbstr);
	    	pstmt.setString(6, body);
	    	pstmt.setString(7, skill.toString());
			pstmt.setString(8, groupID);
			pstmt.setString(9, access.toString());
	        pstmt.setLong(10, id);
	        
	        int rowsUpdated = pstmt.executeUpdate();
	        // Checks and prints if the article was updated
	        if (rowsUpdated > 0) {
	        	System.out.println("Article updated successfully!");
	        }
	        else {
	        	System.out.println("No article found with the given ID.");
	        }
	    }
	}
	
	// Finds article based upon ID and deletes it from database
	public void deleteArticles(long id) throws SQLException {
		String deleteArticle = "DELETE FROM cse360articles WHERE id ?"; // SQL query
		try(PreparedStatement pstmt = connection.prepareStatement(deleteArticle)){
			pstmt.setLong(1, id);
			int rowsDeleted = pstmt.executeUpdate();
			// Checks and prints if the article was deleted
			if (rowsDeleted > 0) {
				System.out.print("Article was succesfully deleted");
			}
			
			else {
				System.out.print("No article found with the given ID.");
			}
		}
		catch(SQLException e){
			e.printStackTrace();	
			System.out.println("Error");
		}
	}
	
	// Searches articles by title and the substring for searching
	public void searchArticles(String substring, String filter) throws SQLException {
		// SQL query
	    String searchSQL = "SELECT * FROM cse360articles WHERE publicTitle LIKE ? or author LIKE ? or abstract LIKE ? AND skillLevel = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(searchSQL)) {
	        pstmt.setString(1, "%" + substring + "%");
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            // HOW DO YOU WANT TO DISPLAY ?
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	/*******
	 * USER DATABASE METHODS
	 */
	
	// Checks if the user database is empty
	public boolean isUserDatabaseEmpty() throws SQLException {
		String query = "SELECT COUNT(*) AS count FROM cse360users"; // SQL query
	    ResultSet resultSet = statement.executeQuery(query);
	    if (resultSet.next()) {
	        return resultSet.getInt("count") == 0;
	    }
	    return true;
	}
	
	// Changes password of user based upon the email
	public void changePassword(String email, String newPass) throws SQLException {
		String changeSQL = "UPDATE users SET pass = ? WHERE email = ?"; // SQL query
		try(PreparedStatement pstmt = connection.prepareStatement(changeSQL)){
			pstmt.setString(1, newPass);
			pstmt.setString(2, email);
			
			int rowsUpdated = pstmt.executeUpdate();
			// Checks and prints if password is updated
			if(rowsUpdated > 0) {
				System.out.println("Password updated successfully for email: " + email);
			}
			else {
				System.out.println("No user found with the given email.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Takes in input for a user and adds it to the database
	public void addUser(String username, String password, String email, String firstName, String middleName, String lastName, String preferredFirstName, boolean isOTP, String oneTimePassword, Timestamp otpExpiration) throws SQLException {
		if (doesUserExist(username)) {
	        // PROMPT USER TO SELECT DIFFERENT USERNAME
	        return;
	    }
		// SQL query
		String addUserSQL = "INSERT INTO users (user, pass, email, firstName, middleName, lastName, preferredFirstName, isOTP, oneTimePassword, otpExpiration) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(PreparedStatement pstmt = connection.prepareStatement(addUserSQL)){
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, email);
			pstmt.setString(4, firstName);
			pstmt.setString(5, middleName);
			pstmt.setString(6, lastName);	
			pstmt.setString(7, preferredFirstName);
			pstmt.setBoolean(8, isOTP);
			pstmt.setString(9, oneTimePassword);
			pstmt.setTimestamp(10, otpExpiration);
			
			int rowsUpdated = pstmt.executeUpdate();
			if(rowsUpdated > 0) {
				System.out.println("Password updated successfully for email: " + email);
			}
			else {
				System.out.println("No user found with the given email.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Checks if user with a certain username exists
	private boolean doesUserExist(String username) throws SQLException {
		// SQL query
	    String checkUserSQL = "SELECT COUNT(*) FROM users WHERE user = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(checkUserSQL)) {
	        pstmt.setString(1, username);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    }
	    return false;
	}
	
	// Deletes user using their email
	public void deleteUser(String email) {
		String deleteUserSQL = "DELETE FROM cse360users WHERE user ?"; // SQL query
		try(PreparedStatement pstmt = connection.prepareStatement(deleteUserSQL)){
			pstmt.setString(1, email);
			int rowsDeleted = pstmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.print("User was succesfully deleted");
			}
			
			else {
				System.out.print("No user found with the given email.");
			}
		}
		catch(SQLException e){
			e.printStackTrace();	
			System.out.println("Error");
		}
	}
}
