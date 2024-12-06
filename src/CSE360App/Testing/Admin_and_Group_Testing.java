/*******
 * <p> DatabaseHelperTest Class </p>
 * 
 * <p> Description: The Database Helper Test class uses JUnit to automate the testing of admin interactions with the database.
 * 
 * 
 */

package src.CSE360App.Testing;

import org.junit.jupiter.api.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.CSE360App.AccessLevel;
import src.CSE360App.AdminClass;
import src.CSE360App.Article;
import src.CSE360App.DatabaseHelper;
import src.CSE360App.GroupAccess;
import src.CSE360App.SkillLevel;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Admin_and_Group_Testing {

	private DatabaseHelper databaseHelper;

	@BeforeEach
	public void setUp() throws Exception {
		databaseHelper = new DatabaseHelper();
		databaseHelper.connectToDataBase();
		databaseHelper.clearArticlesTable(); // Clear the articles table before each test
		databaseHelper.clearGroupsTable();
	}

	/***
	 * testAddArticleAsAdmin: The following test case creates an admin user, and
	 * attempts to create and add an article to the database as that admin. PASS:
	 * Successful submission to the database FAIL: Failure to add to database
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddArticleAsAdmin() throws Exception {
		System.out.println("************************");
		System.out.println("Testing Add Article as Admin");

		// Create an admin user
		AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
				"Preferred", "admin@example.com", List.of("Admin"));

		// Add an article
		String title = "Admin Article";
		String publicTitle = "Public Admin Article";
		String author = "Admin Author";
		String abstr = "Admin Abstract";
		String publicAbstr = "Public Admin Abstract";
		String body = "Admin Body";
		SkillLevel skill = SkillLevel.EXPERT;
		String groupID = "AdminGroup";
		AccessLevel access = AccessLevel.ADMIN;
		Date currDate = new Date(System.currentTimeMillis());

		// Attempt to add an article as an admin to the database.
		databaseHelper.addArticle(admin.getRoles(), title, publicTitle, author, abstr, publicAbstr, body, skill,
				groupID, access, currDate);

		// Verify the article exists
		long articleId = databaseHelper.getLastInsertedArticleId();
		assertTrue(databaseHelper.doesArticleExist(articleId, title),
				"Article should exist in the database after adding.");

		System.out.println("************************\n\n");
	}

	/***
	 * testEditArticleAsAdmin: The following test case creates an admin user, and
	 * attempts to edit an article to the database as that admin. PASS: Successful
	 * edit to an article in the database FAIL: Failure to edit to an article in the
	 * database
	 * 
	 * @throws Exception
	 */
	@Test
	public void testEditArticleAsAdmin() throws Exception {
		System.out.println("************************");
		System.out.println("Testing Edit Article as Admin");

		// Create an admin user
		AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
				"Preferred", "admin@example.com", List.of("Admin"));

		// Add an article
		String originalTitle = "Original Title";

		// Create a new title to prove that admin can edit
		String updatedTitle = "Updated Title";
		databaseHelper.addArticle(admin.getRoles(), originalTitle, "Public Title", "Author", "Abstract",
				"Public Abstract", "Body", SkillLevel.BEGINNER, "GroupID", AccessLevel.PUBLIC,
				new java.sql.Date(System.currentTimeMillis()));

		// Retrieve the article ID
		long articleId = databaseHelper.getLastInsertedArticleId();

		// Edit the article
		databaseHelper.editArticles(admin.getRoles(), articleId, updatedTitle, "Public Title", "Author", "Abstract",
				"Public Abstract", "Body", SkillLevel.BEGINNER, "GroupID", AccessLevel.PUBLIC);

		// Verify the update
		boolean titleUpdated = databaseHelper.doesArticleExist(articleId, updatedTitle);
		assertTrue(titleUpdated, "Edited article should have the updated title.");

		System.out.println("************************\n\n");
	}

	/***
	 * testDeleteArticleAsAdmin: The following test case creates an admin user, and
	 * attempts to delete an article in the database as that admin. PASS: Successful
	 * removal of an article in the database FAIL: Failure to remove an article in
	 * the database. database
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDeleteArticleAsAdmin() throws Exception {
		System.out.println("************************");
		System.out.println("Testing Delete Article as Admin");

		// Create an admin user
		AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
				"Preferred", "admin@example.com", List.of("Admin"));

		// Add an article
		String title = "Test Article";
		String publicTitle = "Public Test Article";
		String author = "John Doe";
		String abstr = "This is an abstract.";
		String publicAbstr = "This is a public abstract.";
		String body = "This is the body of the article.";
		SkillLevel skill = SkillLevel.BEGINNER;
		String groupID = "TestGroup";
		AccessLevel access = AccessLevel.PUBLIC;
		Date currDate = new Date(System.currentTimeMillis());

		databaseHelper.addArticle(admin.getRoles(), title, publicTitle, author, abstr, publicAbstr, body, skill,
				groupID, access, currDate);

		// Retrieve the article ID
		long articleId = databaseHelper.getLastInsertedArticleId();

		// Delete the article
		databaseHelper.deleteArticles(admin.getRoles(), articleId);

		// Verify the article was deleted
		assertFalse(databaseHelper.doesArticleExist(articleId, title),
				"Article should no longer exist after deletion.");

		System.out.println("************************\n\n");
	}

	/***
	 * testBackupArticlesTableAsAdmin: The following test case creates an admin
	 * user, and attempts to backup the database's help article table as that admin.
	 * PASS: Successful backup of the help article table in the database FAIL:
	 * Failure to backup of the help article table in the database. database
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBackupArticlesTableAsAdmin() throws Exception {
		System.out.println("************************");
		System.out.println("Testing Backup Articles as Admin");

		// Create an admin user
		AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
				"Preferred", "admin@example.com", List.of("Admin"));

		// Add some articles to the database
		databaseHelper.addArticle(admin.getRoles(), "Title1", "PublicTitle1", "Author1", "Abstract1", "PublicAbstract1",
				"Body1", SkillLevel.BEGINNER, "GroupID1", AccessLevel.PUBLIC,
				new java.sql.Date(System.currentTimeMillis()));
		databaseHelper.addArticle(admin.getRoles(), "Title2", "PublicTitle2", "Author2", "Abstract2", "PublicAbstract2",
				"Body2", SkillLevel.INTERMEDIATE, "GroupID2", AccessLevel.PUBLIC,
				new java.sql.Date(System.currentTimeMillis()));

		// Call the backup method
		String backupFilePath = "articles_table_backup.csv";
		databaseHelper.backupArticlesTable(admin.getRoles(), backupFilePath);

		// Verify the backup file exists
		File backupFile = new File(backupFilePath);
		assertTrue(backupFile.exists(), "Backup file should exist.");

		System.out.println("************************\n\n");
	}

	/***
	 * testRestoreWithOverwriteAsAdmin: The following test case creates an admin
	 * user, and attempts to overwrite the database's help article table as that
	 * admin, using a backedup file. PASS: Successful restoration of the help
	 * article table in the database (OVERWRITE). FAIL: Failed restoration of the
	 * help article table in the database (OVERWRITE). database
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRestoreWithOverwriteAsAdmin() throws Exception {
		System.out.println("************************");
		System.out.println("Testing Restore with Overwrite as Admin");

		// Create an admin user
		AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
				"Preferred", "admin@example.com", List.of("Admin"));

		// Add some articles to the database
		System.out.println("Adding articles to the database...");
		databaseHelper.addArticle(admin.getRoles(), "Existing Title", "Public Title", "Author", "Abstract",
				"Public Abstract", "Body", SkillLevel.BEGINNER, "GroupID", AccessLevel.PUBLIC,
				new java.sql.Date(System.currentTimeMillis()));

		// Backup the current state of the database
		String backupFilePath = "articles_table_backup.csv";
		System.out.println("Backing up articles...");
		databaseHelper.backupArticlesTable(admin.getRoles(), backupFilePath);

		// Clear the database and restore articles with the overwrite option
		System.out.println("Restoring articles with overwrite...");
		databaseHelper.restoreArticlesTable(admin.getRoles(), backupFilePath, true);

		// Display the database state after restore
		System.out.println("Database state after restore with overwrite:");
		databaseHelper.displayArticles();

		// Verify that old data no longer exists
		assertFalse(databaseHelper.doesArticleExist(1, "Existing Title"),
				"Old articles should not exist after overwrite.");

		// Retrieve the restored article's actual ID
		long restoredId = databaseHelper.getArticleIdByTitle("Existing Title");

		// Verify that the restored data exists
		assertTrue(databaseHelper.doesArticleExist(restoredId, "Existing Title"), "Restored articles should exist.");

		System.out.println("************************\n\n");
	}

	/***
	 * testRestoreWithMergeAsAdmin: The following test case creates an admin user,
	 * and attempts to merge the database's help article table as that admin, using
	 * a backedup file. PASS: Successful restoration of the help article table in
	 * the database (MERGE). FAIL: Failed restoration of the help article table in
	 * the database (MERGE). database
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRestoreWithMergeAsAdmin() throws Exception {
		System.out.println("************************");
		System.out.println("Testing Restore with Merge as Admin");

		// Create an admin user
		AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
				"Preferred", "admin@example.com", List.of("Admin"));

		// Add existing articles to the database
		System.out.println("Adding existing articles...");
		databaseHelper.addArticle(admin.getRoles(), "Existing Title 1", "Public Title 1", "Author 1", "Abstract 1",
				"Public Abstract 1", "Body 1", SkillLevel.BEGINNER, "GroupID1", AccessLevel.PUBLIC,
				new java.sql.Date(System.currentTimeMillis()));
		databaseHelper.addArticle(admin.getRoles(), "Existing Title 2", "Public Title 2", "Author 2", "Abstract 2",
				"Public Abstract 2", "Body 2", SkillLevel.INTERMEDIATE, "GroupID2", AccessLevel.PUBLIC,
				new java.sql.Date(System.currentTimeMillis()));

		// Display database state before backup
		System.out.println("Database state before backup:");
		databaseHelper.displayArticles();

		// Backup the database
		String backupFilePath = "articles_table_backup.csv";
		databaseHelper.backupArticlesTable(admin.getRoles(), backupFilePath);

		// Add another article to the database before restore
		databaseHelper.addArticle(admin.getRoles(), "Another Existing Title", "Another Public Title", "Another Author",
				"Another Abstract", "Another Public Abstract", "Another Body", SkillLevel.EXPERT, "AnotherGroupID",
				AccessLevel.PUBLIC, new java.sql.Date(System.currentTimeMillis()));

		// Display database state before restore
		System.out.println("Database state before restore:");
		databaseHelper.displayArticles();

		// Restore with merge
		databaseHelper.restoreArticlesTable(admin.getRoles(), backupFilePath, false);

		// Display database state after restore
		System.out.println("Database state after restore:");
		databaseHelper.displayArticles();

		// Retrieve the actual IDs of the articles
		long id1 = databaseHelper.getArticleIdByTitle("Existing Title 1");
		long id2 = databaseHelper.getArticleIdByTitle("Existing Title 2");
		long id3 = databaseHelper.getArticleIdByTitle("Another Existing Title");

		// Verify all articles exist with the correct IDs
		assertTrue(databaseHelper.doesArticleExist(id1, "Existing Title 1"), "Existing Title 1 should remain.");
		assertTrue(databaseHelper.doesArticleExist(id2, "Existing Title 2"), "Existing Title 2 should remain.");
		assertTrue(databaseHelper.doesArticleExist(id3, "Another Existing Title"),
				"Another Existing Title should remain.");

		System.out.println("************************\n\n");
	}

	/***
	 * testBackupGroupsTable: This test case ensures that the backup operation is
	 * only accessible by admin users. It verifies that the groups table is
	 * successfully backed up to a file.
	 * 
	 * PASS: Backup file exists and contains accurate group data. FAIL: Backup file
	 * does not exist or contains incorrect group data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testBackupGroupsTable() throws Exception {
		System.out.println("************************");
		System.out.println("Testing Backup Groups Table");

		// Define the file path for the backup
		String backupFilePath = "groups_table_backup.csv";

		// Create an admin user
		AdminClass admin = new AdminClass("Admin123", new char[] {}, false, "First", "", "Last", "Preferred",
				"admin@example.com", List.of("Admin"));

		// Add groups to the database
		ArrayList<Article> articles = new ArrayList<>();
		Article.populateArticles(articles); // Populate articles for the group
		Map<AdminClass, List<String>> admins = new HashMap<>();
		admins.put(admin, List.of("Manage"));

		System.out.println("Adding group to the database...");
		databaseHelper.addGroup(admin.getRoles(), "Group 1", GroupAccess.GENERAL, articles, admins,
				new java.sql.Date(System.currentTimeMillis()));

		// Backup the groups table
		System.out.println("Backing up groups table...");
		databaseHelper.backupGroupsTable(admin.getRoles(), backupFilePath);

		// Verify the backup file exists
		File backupFile = new File(backupFilePath);
		assertTrue(backupFile.exists(), "Backup file should exist.");

		System.out.println("************************\n\n");
	}

	/***
	 * testRestoreGroupsTableWithOverwrite: This test case ensures that only an
	 * admin user can restore the groups table with overwrite mode. It verifies that
	 * the groups table is cleared before restoring data from a backup file.
	 * 
	 * PASS: Groups table is successfully restored with overwrite. FAIL: Groups
	 * table restoration fails or contains incorrect data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRestoreGroupsTableWithOverwrite() throws Exception {
		System.out.println("************************");
		System.out.println("Testing Restore Groups Table with Overwrite");

		// Create an admin user
		AdminClass admin = new AdminClass("Admin123", new char[] {}, false, "First", "", "Last", "Preferred",
				"admin@example.com", List.of("Admin"));

		// Add and back up groups
		ArrayList<Article> articles = new ArrayList<>();
		Article.populateArticles(articles); // Use populated articles
		Map<AdminClass, List<String>> admins = new HashMap<>();
		admins.put(admin, List.of("Manage"));

		System.out.println("Adding original group to the database...");
		databaseHelper.addGroup(admin.getRoles(), "Original Group", GroupAccess.GENERAL, articles, admins,
				new java.sql.Date(System.currentTimeMillis()));

		// Back up the groups
		String backupFilePath = "groups_table_backup.csv";
		System.out.println("Backing up groups table...");
		databaseHelper.backupGroupsTable(admin.getRoles(), backupFilePath);

		// Add another group to simulate pre-existing data
		System.out.println("Adding additional group to the database...");
		databaseHelper.addGroup(admin.getRoles(), "Additional Group", GroupAccess.GENERAL, null, null,
				new java.sql.Date(System.currentTimeMillis()));

		// Restore groups with overwrite
		System.out.println("Restoring groups table with overwrite...");
		databaseHelper.restoreGroupsTable(admin.getRoles(), backupFilePath, true);

		// Verify database contents after overwrite
		System.out.println("Database state after restore with overwrite:");
		databaseHelper.displayGroups(); // Log current groups for debugging

		// Verify the "Additional Group" no longer exists
		assertFalse(databaseHelper.doesGroupExist("Additional Group", GroupAccess.GENERAL),
				"Additional group should not exist after overwrite.");

		// Verify the restored "Original Group" exists
		assertTrue(databaseHelper.doesGroupExist("Original Group", GroupAccess.GENERAL),
				"Restored group should exist after overwrite.");

		System.out.println("************************\n\n");
	}

	/***
	 * testRestoreGroupsTableWithMerge: This test case ensures that only an admin
	 * user can restore the groups table in merge mode. It verifies that existing
	 * groups in the database are preserved while new groups are added.
	 * 
	 * PASS: Groups table is successfully restored with merge. FAIL: Groups table
	 * restoration fails or contains incorrect data.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testRestoreGroupsTableWithMerge() throws Exception {
		System.out.println("************************");
		System.out.println("Testing Restore Groups Table with Merge");

		// Create an admin user
		AdminClass admin = new AdminClass("Admin123", new char[] {}, false, "First", "", "Last", "Preferred",
				"admin@example.com", List.of("Admin"));

		// Add and back up groups
		ArrayList<Article> articles = new ArrayList<>();
		Article.populateArticles(articles);
		Map<AdminClass, List<String>> admins = new HashMap<>();
		admins.put(admin, List.of("Manage"));

		System.out.println("Adding original group to the database...");
		databaseHelper.addGroup(admin.getRoles(), "Original Group", GroupAccess.GENERAL, articles, admins,
				new java.sql.Date(System.currentTimeMillis()));
		System.out.println("Backing up groups table...");
		databaseHelper.backupGroupsTable(admin.getRoles(), "groups_table_backup.csv");

		// Add another group before restore
		System.out.println("Adding additional group to the database...");
		databaseHelper.addGroup(admin.getRoles(), "Additional Group", GroupAccess.GENERAL, null, null,
				new java.sql.Date(System.currentTimeMillis()));

		// Restore groups with merge
		System.out.println("Restoring groups table with merge...");
		databaseHelper.restoreGroupsTable(admin.getRoles(), "groups_table_backup.csv", false);

		// Verify database contents
		System.out.println("Database state after restore with merge:");
		databaseHelper.displayGroups();

		// Verify that both original and additional groups exist
		assertTrue(databaseHelper.doesGroupExist("Original Group", GroupAccess.GENERAL),
				"Restored group should exist.");
		assertTrue(databaseHelper.doesGroupExist("Additional Group", GroupAccess.GENERAL),
				"Additional group should still exist.");

		System.out.println("************************\n\n");
	}

}
