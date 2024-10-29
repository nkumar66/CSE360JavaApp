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
	
	private void createTables() throws SQLException {
	    String articleTable = "CREATE TABLE IF NOT EXISTS cse360articles ("
	            + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "      // Unique article ID
	            + "title VARCHAR(255), "                     // Article title
	            + "publicTitle VARCHAR(255)"				 // Title for public use
	            + "author VARCHAR(255), "                    // Author's name
	            + "abstract TEXT, "                          // Abstract for the article
	            + "publicAbstract TEXT"						 // Abstract for public use
	            + "body LONGTEXT, "                          // Full article body
	            + "skillLevel VARCHAR(50), " 					 // Difficulty level
	            + "groupID VARCHAR(100), "                   // Group identifier for related articles
	            + "accessLevel VARCHAR(50), " 				 // Access level for viewing
	            + "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " // Timestamp for article creation
	            + "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" // Timestamp for last update
	            + ")";
	    statement.execute(articleTable);
	}

	public boolean isDatabaseEmpty() throws SQLException {		//function to check if the database is empty
	    String query = "SELECT COUNT(*) AS count FROM cse360articles";
	    ResultSet resultSet = statement.executeQuery(query);
	    if (resultSet.next()) {
	        return resultSet.getInt("count") == 0;
	    }
	    return true;
	}
	
	public void addArticle(String title, String publicTitle, String author, String abstr, String publicAbstr, String body, SkillLevel skill, String groupID, AccessLevel access, Date currDate) throws SQLException {
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
	
	public boolean doesArticleExist(String title) {
	    String query = "SELECT COUNT(*) FROM cse360articles WHERE title = ?";
	    try (PreparedStatement pstmt = connection.prepareStatement(query)) {
	        
	        pstmt.setString(1, title);
	        ResultSet rs = pstmt.executeQuery();
	        
	        if (rs.next()) {
	            return rs.getInt(1) > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	public void displayArticles() throws Exception {
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
	public void editArticles(long id, String title, String publicTitle, String author, String abstr, String publicAbstr, String body, SkillLevel skill, String groupID, AccessLevel access) throws Exception {
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
	        if (rowsUpdated > 0) {
	        	System.out.println("Article updated successfully!");
	        }
	        else {
	        	System.out.println("No article found with the given ID.");
	        }
	    }
	}
	
	public void deleteArticles(long id) throws SQLException {
		String deleteArticle = "DELETE FROM cse360articles WHERE id ?";
		try(PreparedStatement pstmt = connection.prepareStatement(deleteArticle)){
			pstmt.setLong(1, id);
			int rowsDeleted = pstmt.executeUpdate();
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
}
