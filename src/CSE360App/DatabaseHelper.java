package src.CSE360App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import org.bouncycastle.util.Arrays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
//import src.CSE360App.Encryption.*;
import java.util.Map;

public class DatabaseHelper {

	// Setting up the driver name and database URL.
	static final String JDBC_DRIVER = "org.h2.Driver";
	static final String DB_URL = "jdbc:h2:~/cse360Database";

	private static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	// Database credentials
	static final String USER = "sa";
	static final String PASS = "";

	Statement statement;

	private Connection connection = null; // to identify the connection

	public void connectToDataBase() throws SQLException {
		if (isConnected()) {
			System.out.println("Database already connected");
			return;
		}
		try {
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS); // attempts to establish a connection
			statement = connection.createStatement(); // to enter a statement to get data from the database
			createTables(); // Create the necessary tables if they don't exist by calling below function
		} catch (ClassNotFoundException e) { // if not found, prints error message below
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
	}
	//Function to check if database is already connected
	public boolean isConnected() throws SQLException {
		return connection != null && !connection.isClosed();
	}
	

	// Creates articleTable and userTable
	private void createTables() throws SQLException {
		String articleTable = "CREATE TABLE IF NOT EXISTS cse360articles (" + "id BIGINT AUTO_INCREMENT PRIMARY KEY, " // Unique
																														// article
																														// ID
				+ "title VARCHAR(255), " // Article title
				+ "publicTitle VARCHAR(255), " // Title for public use
				+ "author VARCHAR(255), " // Author's name
				+ "abstract TEXT, " // Abstract for the article
				+ "publicAbstract TEXT, " // Abstract for public use
				+ "body LONGTEXT, " // Full article body
				+ "skillLevel VARCHAR(50), " // Difficulty level
				+ "groupID VARCHAR(100), " // Group identifier for related articles
				+ "accessLevel VARCHAR(50), " // Access level for viewing
				+ "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " // Timestamp for article creation
				+ "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" // Timestamp for last
																								// update
				+ ")";

		String userTable = "CREATE TABLE IF NOT EXISTS cse360users (" + "id INT AUTO_INCREMENT PRIMARY KEY, " // Unique
																												// user
																												// ID
				+ "`user` VARCHAR(255) UNIQUE, " // Username
				+ "pass VARCHAR(255), " // Password
				+ "email VARCHAR(255), " // Email
				+ "firstName VARCHAR(255), " // User First Name
				+ "middleName VARCHAR(255), " // User Middle Name
				+ "lastName VARCHAR(255), " // User Last Name
				+ "preferredFirstName VARCHAR(255), " // User preferred First Name
				+ "isOTP BOOLEAN, " // If it is OTP password
				+ "oneTimePassword VARCHAR(255), " // Current OTP
				+ "otpExpiration TIMESTAMP" // When OTP expires
				+ ")";
		statement.execute(articleTable);
		statement.execute(userTable);
		createUserGroupsTable();
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
	public void addArticle(List<String> roles, String title, String publicTitle, String author, String abstr,
			String publicAbstr, String body, SkillLevel skill, String groupID, AccessLevel access, Date currDate)
			throws Exception {
		// Check if the user has "Admin" role in its list of roles
		if (!roles.contains("Admin")) {
			throw new SecurityException("Permission denied: User does not have Admin rights.");
		}

		// Continue with adding the article
		// Encrypts the body
		String encryptedBody = Base64.getEncoder().encodeToString(EncryptionHelper.encrypt(body.getBytes(),
				EncryptionUtils.getInitializationVector(abstr.toCharArray())));

		String insertArticle = "INSERT INTO cse360articles (title, publicTitle, author, abstract, publicAbstract, body, skillLevel, groupID, accessLevel, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertArticle)) {
			pstmt.setString(1, title);
			pstmt.setString(2, publicTitle);
			pstmt.setString(3, author);
			pstmt.setString(4, abstr);
			pstmt.setString(5, publicAbstr);
			pstmt.setString(6, encryptedBody);
			pstmt.setString(7, skill.toString());
			pstmt.setString(8, groupID);
			pstmt.setString(9, access.toString());
			pstmt.setDate(10, new java.sql.Date(currDate.getTime()));
			pstmt.setDate(11, new java.sql.Date(currDate.getTime()));

			int rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Article added successfully.");
			} else {
				System.out.println("Failed to add article.");
			}
		}
	}

	// Checks based on title if an article exists in the database with that title
	public boolean doesArticleExist(long id, String title) throws SQLException {
		String query = "SELECT COUNT(*) FROM cse360articles WHERE id = ? AND title = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setLong(1, id);
			pstmt.setString(2, title);
			try (ResultSet rs = pstmt.executeQuery()) {
				boolean exists = rs.next() && rs.getInt(1) > 0;
				System.out.println("Checking existence for ID: " + id + ", Title: " + title + " => Exists: " + exists);
				return exists;
			}
		}
	}

	// Displays all the articles in the database
	public void displayArticles() throws SQLException {
		// SQL query
		String sql = "SELECT * FROM cse360articles";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			String title = rs.getString("title");
			String author = rs.getString("author");
			String abstr = rs.getString("abstract");
		}
		/*
		 * IMPLEMENT DISPLAYING IN JAVAFX
		 */
	}

	public long getLastInsertedArticleId() throws SQLException {
		String query = "SELECT MAX(id) AS last_id FROM cse360articles";
		try (ResultSet rs = statement.executeQuery(query)) {
			if (rs.next()) {
				return rs.getLong("last_id");
			}
		}
		return -1; // Return -1 if no articles exist
	}

	// Edits the articles in whatever way
	public void editArticles(List<String> roles, long id, String title, String publicTitle, String author, String abstr,
			String publicAbstr, String body, SkillLevel skill, String groupID, AccessLevel access) throws Exception {
		// Check if the user has "Admin" role
		if (!roles.contains("Admin")) {
			throw new SecurityException("Permission denied: User does not have Admin rights.");
		}

		// Encrypt the article body for storage
		String encryptedBody = Base64.getEncoder().encodeToString(EncryptionHelper.encrypt(body.getBytes(),
				EncryptionUtils.getInitializationVector(abstr.toCharArray())));

		String editSQL = "UPDATE cse360articles SET title = ?, publicTitle = ?, author = ?, abstract = ?, publicAbstract = ?, body = ?, skillLevel = ?, groupID = ?, accessLevel = ?, updatedAt = CURRENT_TIMESTAMP WHERE id = ?";

		try (PreparedStatement pstmt = connection.prepareStatement(editSQL)) {
			pstmt.setString(1, title);
			pstmt.setString(2, publicTitle);
			pstmt.setString(3, author);
			pstmt.setString(4, abstr);
			pstmt.setString(5, publicAbstr);
			pstmt.setString(6, encryptedBody);
			pstmt.setString(7, skill.toString());
			pstmt.setString(8, groupID);
			pstmt.setString(9, access.toString());
			pstmt.setLong(10, id);

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Article updated successfully.");
			} else {
				System.out.println("No article found with the given ID.");
			}
		}
	}

	// Finds article based upon ID and deletes it from database
	public void deleteArticles(List<String> roles, long id) throws SQLException {
		// Check if the user has "Admin" role
		if (!roles.contains("Admin")) {
			throw new SecurityException("Permission denied: User does not have Admin rights.");
		}

		String deleteArticle = "DELETE FROM cse360articles WHERE id = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(deleteArticle)) {
			pstmt.setLong(1, id);
			int rowsDeleted = pstmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Article was successfully deleted.");
			} else {
				System.out.println("No article found with the given ID.");
			}
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

	/****
	 * backupArticlesTable: If the user has admin permissions, writes the database
	 * data for the articlesTable to a file which can be restored using helper
	 * method below
	 * 
	 * @param roles
	 * @param backupFilePath
	 * @throws SQLException
	 * @throws IOException
	 */
	public void backupArticlesTable(List<String> roles, String backupFilePath) throws SQLException, IOException {
		// Check if the user has "Admin" role
		if (!roles.contains("Admin")) {
			throw new SecurityException("Permission denied: User does not have Admin rights.");
		}

		String query = "SELECT * FROM cse360articles";
		try (ResultSet rs = statement.executeQuery(query); FileWriter fileWriter = new FileWriter(backupFilePath)) {

			fileWriter.write(
					"id,title,publicTitle,author,abstract,publicAbstract,body,skillLevel,groupID,accessLevel,createdAt,updatedAt\n");

			// Converts articles table into a format that is easily readible for restoration
			while (rs.next()) {
				fileWriter.write(rs.getLong("id") + "," + rs.getString("title") + "," + rs.getString("publicTitle")
						+ "," + rs.getString("author") + "," + rs.getString("abstract") + ","
						+ rs.getString("publicAbstract") + "," + rs.getString("body") + "," + rs.getString("skillLevel")
						+ "," + rs.getString("groupID") + "," + rs.getString("accessLevel") + ","
						+ rs.getTimestamp("createdAt") + "," + rs.getTimestamp("updatedAt") + "\n");
			}
		}
		System.out.println("Articles table backed up successfully to " + backupFilePath);
	}

	/***
	 * restoreArticlesTable: Restores the database using a backup file and either
	 * overwrites or merges with current data.
	 * 
	 * @param roles
	 * @param backupFilePath
	 * @param overwrite
	 * @throws Exception
	 */
	public void restoreArticlesTable(List<String> roles, String backupFilePath, boolean overwrite) throws Exception {
		if (!roles.contains("Admin")) {
			throw new SecurityException("Permission denied: User does not have Admin rights.");
		}

		// Read in data from the specified file path.
		try (BufferedReader reader = new BufferedReader(new FileReader(backupFilePath))) {
			String line;
			boolean skipHeader = true;

			// Overwrite Mode: Clear the database first
			if (overwrite) {
				clearArticlesTable(); // Clear all articles from the database
				System.out.println("Database cleared for overwrite mode.");
			}

			// Basically a for every entry loop:
			// For every entry read it in, strip the data from csv format and store it into
			// appropriate data types, if we're in merge mode check if it exists and insert
			// it otherwise.
			while ((line = reader.readLine()) != null) {
				if (skipHeader) {
					skipHeader = false;
					continue;
				}

				// Parse the CSV line into article attributes
				String[] data = line.split(",");
				long id = Long.parseLong(data[0].trim());
				String title = data[1].trim();
				String publicTitle = data[2].trim();
				String author = data[3].trim();
				String abstr = data[4].trim();
				String publicAbstr = data[5].trim();
				String body = data[6].trim();
				String skillLevel = data[7].trim();
				String groupID = data[8].trim();
				String accessLevel = data[9].trim();
				Timestamp createdAt = parseTimestamp(data[10].trim());
				Timestamp updatedAt = parseTimestamp(data[11].trim());

				if (!overwrite) {
					// Merge Mode: Check for duplicates, if they exist skip it otherwise write it
					// in.
					if (doesArticleExist(id, title)) {
						System.out.println("Skipping duplicate article with ID: " + id + " and title: " + title);
						continue; // Skip duplicates
					}
				}

				// Insert new record
				String insertQuery = "INSERT INTO cse360articles (id, title, publicTitle, author, abstract, publicAbstract, body, "
						+ "skillLevel, groupID, accessLevel, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
					pstmt.setLong(1, id);
					pstmt.setString(2, title);
					pstmt.setString(3, publicTitle);
					pstmt.setString(4, author);
					pstmt.setString(5, abstr);
					pstmt.setString(6, publicAbstr);
					pstmt.setString(7, body);
					pstmt.setString(8, skillLevel);
					pstmt.setString(9, groupID);
					pstmt.setString(10, accessLevel);
					pstmt.setTimestamp(11, createdAt);
					pstmt.setTimestamp(12, updatedAt);
					pstmt.executeUpdate();
					System.out.println("Inserted article with ID: " + id);
				}
			}
			System.out.println("Articles table restored successfully from " + backupFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error restoring articles table from backup.");
		}
	}

	/***
	 * parseTimestamp: Helper function to help parse timestamps.
	 * 
	 * @param timestampStr
	 * @return
	 */
	private Timestamp parseTimestamp(String timestampStr) {
		try {
			java.util.Date parsedDate = TIMESTAMP_FORMAT.parse(timestampStr);
			return new Timestamp(parsedDate.getTime());
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid timestamp format: " + timestampStr, e);
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
	public void changePassword(String email, String newPass) throws Exception {
		String encryptedPassword = Base64.getEncoder().encodeToString(EncryptionHelper.encrypt(newPass.getBytes(),
				EncryptionUtils.getInitializationVector(email.toCharArray())));
		String changeSQL = "UPDATE users SET pass = ? WHERE email = ?"; // SQL query
		try (PreparedStatement pstmt = connection.prepareStatement(changeSQL)) {
			pstmt.setString(1, encryptedPassword);
			pstmt.setString(2, email);

			int rowsUpdated = pstmt.executeUpdate();
			// Checks and prints if password is updated
			if (rowsUpdated > 0) {
				System.out.println("Password updated successfully for email: " + email);
			} else {
				System.out.println("No user found with the given email.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Takes in input for a user and adds it to the database
	public void addUser(String username, String password, String email, String firstName, String middleName,
			String lastName, String preferredFirstName, boolean isOTP, String oneTimePassword, Timestamp otpExpiration)
			throws Exception {
		if (doesUserExist(username)) {
			// PROMPT USER TO SELECT DIFFERENT USERNAME
			return;
		}
		String encryptedPassword = Base64.getEncoder().encodeToString(EncryptionHelper.encrypt(password.getBytes(),
				EncryptionUtils.getInitializationVector(email.toCharArray())));
		// SQL query
		String addUserSQL = "INSERT INTO users (user, pass, email, firstName, middleName, lastName, preferredFirstName, isOTP, oneTimePassword, otpExpiration) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = connection.prepareStatement(addUserSQL)) {
			pstmt.setString(1, username);
			pstmt.setString(2, encryptedPassword);
			pstmt.setString(3, email);
			pstmt.setString(4, firstName);
			pstmt.setString(5, middleName);
			pstmt.setString(6, lastName);
			pstmt.setString(7, preferredFirstName);
			pstmt.setBoolean(8, isOTP);
			pstmt.setString(9, oneTimePassword);
			pstmt.setTimestamp(10, otpExpiration);

			int rowsUpdated = pstmt.executeUpdate();
			if (rowsUpdated > 0) {
				System.out.println("Password updated successfully for email: " + email);
			} else {
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
		try (PreparedStatement pstmt = connection.prepareStatement(deleteUserSQL)) {
			pstmt.setString(1, email);
			int rowsDeleted = pstmt.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.print("User was succesfully deleted");
			}

			else {
				System.out.print("No user found with the given email.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}

	public void clearArticlesTable() throws SQLException {
		String clearSQL = "DELETE FROM cse360articles";
		try (PreparedStatement pstmt = connection.prepareStatement(clearSQL)) {
			pstmt.executeUpdate();
			System.out.println("cse360articles table cleared.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error clearing cse360articles table.");
		}
	}
	
	
	/****
	 * GROUP DATABASE METHODS
	 * 
	 */

	/***
	 * Create User Groups Table:
	 * Creates the `user_groups` table in the database if it does not already exist.
	 * The table includes columns for id (primary key), group name, access level,
	 * serialized articles, serialized admins, and timestamps for creation and updates.
	 *
	 * @throws SQLException if there is an error during table creation.
	 */
	private void createUserGroupsTable() throws SQLException {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS user_groups (" + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
				+ "name VARCHAR(255) NOT NULL, " + "access_level VARCHAR(50) NOT NULL, " + "articles TEXT, " // Serialized
																												// articles
				+ "admins TEXT, " // Serialized admins
				+ "createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
				+ "updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP" + ")";
		try (Statement stmt = connection.createStatement()) {
			stmt.execute(createTableSQL);
			//System.out.println("user_groups table created or already exists.");
		}
	}

	
	/***
	 * Serialize Articles:
	 * Converts a list of `Article` objects into a single serialized string.
	 * Each article is converted to its string representation, and articles are 
	 * separated by a semicolon.
	 *
	 * @param articles List of `Article` objects to serialize.
	 * @return A semicolon-separated string representing the serialized articles.
	 */
	private String serializeArticles(List<Article> articles) {
		return articles.stream().map(article -> {
			String serialized = article.toString();
			System.out.println("Serialized article: " + serialized); // Debug log
			return serialized;
		}).reduce((a, b) -> a + ";" + b).orElse("");
	}

	
	/***
	 * Deserialize Articles:
	 * Converts a serialized string of articles back into a list of `Article` objects.
	 * Assumes articles in the string are separated by semicolons.
	 *
	 * @param serializedArticles The serialized string of articles.
	 * @return A list of `Article` objects.
	 */
	private List<Article> deserializeArticles(String serializedArticles) {
		List<Article> articles = new ArrayList<>();
		if (serializedArticles != null && !serializedArticles.isEmpty()) {
			for (String articleStr : serializedArticles.split(";")) {
				System.out.println("Deserializing article: " + articleStr); // Debug log
				articles.add(Article.fromString(articleStr));
			}
		}
		return articles;
	}

	
	/***
	 * Serialize Admins:
	 * Converts a map of `AdminClass` to their associated permissions into a single 
	 * serialized string. Each admin's username is followed by their permissions 
	 * (separated by '|'), and each admin entry is separated by a semicolon.
	 *
	 * @param admins Map of `AdminClass` to a list of their permissions.
	 * @return A serialized string representing the map of admins and their permissions.
	 */
	private String serializeAdmins(Map<AdminClass, List<String>> admins) {
		StringBuilder sb = new StringBuilder();
		admins.forEach((admin, permissions) -> {
			sb.append(admin.getUsername()).append(":").append(String.join("|", permissions)).append(";");
		});
		return sb.toString();
	}

	
	/***
	 * Deserialize Admins:
	 * Converts a serialized string of admins and their permissions back into a map 
	 * of `AdminClass` to a list of permissions. Assumes each admin entry is 
	 * separated by a semicolon and permissions are separated by '|'.
	 *
	 * @param serializedAdmins The serialized string of admins and permissions.
	 * @return A map of `AdminClass` to a list of their permissions.
	 */
	private Map<AdminClass, List<String>> deserializeAdmins(String serializedAdmins) {
		Map<AdminClass, List<String>> admins = new HashMap<>();

		if (serializedAdmins == null || serializedAdmins.equalsIgnoreCase("null") || serializedAdmins.isEmpty()) {
			return new HashMap<>(); // Return an empty map if null
		}
		if (serializedAdmins != null && !serializedAdmins.isEmpty()) {
			for (String entry : serializedAdmins.split(";")) {
				String[] parts = entry.split(":");
				AdminClass admin = new AdminClass(parts[0], new char[] {}, false, "", "", "", "", "",
						new ArrayList<>());
				List<String> permissions = List.of(parts[1].split("\\|"));
				admins.put(admin, permissions);
			}
		}
		return admins;
	}

	/***
	 * addGroup: Creates and pushes a group into database by serializing the admin users and articles
	 * 
	 * @param roles
	 * @param name
	 * @param accessLevel
	 * @param articles
	 * @param admins
	 * @param currDate
	 * @throws Exception
	 */
	public void addGroup(List<String> roles, String name, GroupAccess accessLevel, List<Article> articles,
			Map<AdminClass, List<String>> admins, Date currDate) throws Exception {
		// Check if the user has "Admin" role in its list of roles
		if (!roles.contains("Admin")) {
			throw new SecurityException("Permission denied: User does not have Admin rights.");
		}

		// Serialize articles and admins for storage
		String serializedArticles = articles != null ? serializeArticles(articles) : null;
		String serializedAdmins = admins != null ? serializeAdmins(admins) : null;

		String insertGroup = "INSERT INTO user_groups (name, access_level, articles, admins, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = connection.prepareStatement(insertGroup)) {
			pstmt.setString(1, name);
			pstmt.setString(2, accessLevel.toString());
			pstmt.setString(3, serializedArticles);
			pstmt.setString(4, serializedAdmins);
			pstmt.setDate(5, new java.sql.Date(currDate.getTime()));
			pstmt.setDate(6, new java.sql.Date(currDate.getTime()));

			int rowsInserted = pstmt.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Group added successfully.");
			} else {
				System.out.println("Failed to add group.");
			}
		}
	}

	/***
	 * Clear Groups Table: Clears Group data 
	 */
	public void clearGroupsTable() throws SQLException {
		String clearSQL = "DELETE FROM user_groups";
		statement.executeUpdate(clearSQL);
		System.out.println("Groups table cleared.");
	}

	/***
	 * backup groups table: Allows people with admin rights to backup the groups table as a whole
	 * 
	 * @param roles
	 * @param backupFilePath
	 * @throws SQLException
	 * @throws IOException
	 */
	public void backupGroupsTable(List<String> roles, String backupFilePath) throws SQLException, IOException {
		// Check if the user has "Admin" role in its list of roles
		if (!roles.contains("Admin")) {
			throw new SecurityException("Permission denied: User does not have Admin rights.");
		}
		String selectGroupsQuery = "SELECT * FROM user_groups";
		try (ResultSet rs = statement.executeQuery(selectGroupsQuery);
				FileWriter writer = new FileWriter(backupFilePath)) {

			writer.write("id,name,access_level,articles,admins\n"); // Header

			while (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String accessLevel = rs.getString("access_level");
				String articles = rs.getString("articles");
				String admins = rs.getString("admins");

				writer.write(String.format("%d,%s,%s,%s,%s\n", id, name, accessLevel, articles, admins));
			}

			System.out.println("Groups table backed up successfully to " + backupFilePath);
		}
	}

	/***
	 * Restore Groups Table: Allows people with admin rights to either overwrite or merge groups
	 * 
	 * @param roles
	 * @param backupFilePath
	 * @param overwrite
	 * @throws Exception
	 */
	public void restoreGroupsTable(List<String> roles, String backupFilePath, boolean overwrite) throws Exception {
		// Check if the user has "Admin" role in its list of roles
		if (!roles.contains("Admin")) {
			throw new SecurityException("Permission denied: User does not have Admin rights.");
		}
		if (overwrite) {
			String clearTableQuery = "DELETE FROM user_groups";
			statement.executeUpdate(clearTableQuery); // Clear the groups table
			System.out.println("Groups table cleared for overwrite mode.");
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(backupFilePath))) {
			String line;
			boolean skipHeader = true;

			while ((line = reader.readLine()) != null) {
				if (skipHeader) {
					skipHeader = false;
					continue; // Skip header
				}

				String[] data = line.split(",", -1); // Use -1 to include trailing empty fields
				if (data.length < 5) { // Ensure there are enough columns to process
					System.err.println("Failed to process line: " + line);
					continue;
				}

				long id = Long.parseLong(data[0].trim());
				String name = data[1].trim();
				GroupAccess accessLevel = GroupAccess.valueOf(data[2].trim().toUpperCase());

				// Deserialize articles and admins
				List<Article> articles = data[3].isEmpty() ? new ArrayList<>() : deserializeArticles(data[3].trim());
				Map<AdminClass, List<String>> admins = data[4].isEmpty() ? new HashMap<>()
						: deserializeAdmins(data[4].trim());

				// Debugging logs
				System.out.println("Restoring group:");
				System.out.println(" - ID: " + id);
				System.out.println(" - Name: " + name);
				System.out.println(" - Access Level: " + accessLevel);
				System.out.println(" - Articles: " + articles);
				System.out.println(" - Admins: " + admins);

				// Add the group
				addGroup(List.of("Admin"), name, accessLevel, articles, admins,
						new java.sql.Date(System.currentTimeMillis()));
			}

			System.out.println("Groups table restored successfully from " + backupFilePath);
		} catch (Exception e) {
			System.err.println("Error restoring groups table: " + e.getMessage());
			throw e;
		}
	}

	/***
	 * doesGroupExist: Searches for group by its name and access level
	 * 
	 * @param name
	 * @param accessLevel
	 * @return
	 * @throws SQLException
	 */
	public boolean doesGroupExist(String name, GroupAccess accessLevel) throws SQLException {
		String query = "SELECT COUNT(*) FROM user_groups WHERE name = ? AND access_level = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, name);
			pstmt.setString(2, accessLevel.toString());
			ResultSet rs = pstmt.executeQuery();
			return rs.next() && rs.getInt(1) > 0;
		}
	}

	/***
	 * displayGroups: Helper function to display all groups in database
	 * 
	 * @throws SQLException
	 */
	public void displayGroups() throws SQLException {
		String query = "SELECT * FROM user_groups";
		try (ResultSet rs = statement.executeQuery(query)) {
			System.out.println("Current groups in the database:");
			System.out.println("ID\tName\tAccess Level\tArticles\tAdmins");
			while (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String accessLevel = rs.getString("access_level");
				String articles = rs.getString("articles");
				String admins = rs.getString("admins");

				System.out.printf("%d\t%s\t%s\t%s\t%s%n", id, name, accessLevel, articles, admins);
			}
		}
	}


	/***
	 * getArticleIdByTitle: Searches for article by its ID
	 * @param title
	 * @return
	 * @throws SQLException
	 */
	public long getArticleIdByTitle(String title) throws SQLException {
		String query = "SELECT id FROM cse360articles WHERE title = ?";
		try (PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, title);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("id");
				}
			}
		}
		throw new SQLException("Article with title '" + title + "' not found.");
	}
}
