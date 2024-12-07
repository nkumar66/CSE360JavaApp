package src.CSE360App.Testing;

import org.junit.jupiter.api.*;
import java.io.File;
import java.sql.Date;
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

    @Test
    public void testAddArticleAsAdmin() throws Exception {
        AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
                "Preferred", "admin@example.com", List.of("Admin"));

        databaseHelper.addArticle(
            admin.getRoles(),
            "Admin Article",
            "Public Admin Article",
            "Admin Author",
            "Admin Abstract",
            "Public Admin Abstract",
            "Admin Body",
            SkillLevel.EXPERT,
            "AdminGroup",
            AccessLevel.ADMIN,
            new java.util.Date(),
            List.of("Keyword1", "Keyword2"),
            List.of("Link1", "Link2")
        );

        long articleId = databaseHelper.getLastInsertedArticleId();
        assertTrue(databaseHelper.doesArticleExist(articleId, "Admin Article"));
    }

    @Test
    public void testEditArticleAsAdmin() throws Exception {
        AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
                "Preferred", "admin@example.com", List.of("Admin"));

        databaseHelper.addArticle(
            admin.getRoles(),
            "Original Title",
            "Public Title",
            "Author",
            "Abstract",
            "Public Abstract",
            "Body",
            SkillLevel.BEGINNER,
            "GroupID",
            AccessLevel.PUBLIC,
            new java.util.Date(),
            List.of(),
            List.of()
        );

        long articleId = databaseHelper.getLastInsertedArticleId();
        databaseHelper.editArticles(
            admin.getRoles(),
            articleId,
            "Updated Title",
            "Public Title",
            "Author",
            "Abstract",
            "Public Abstract",
            "Body",
            SkillLevel.BEGINNER,
            "GroupID",
            AccessLevel.PUBLIC
        );

        assertTrue(databaseHelper.doesArticleExist(articleId, "Updated Title"));
    }

    @Test
    public void testDeleteArticleAsAdmin() throws Exception {
        AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
                "Preferred", "admin@example.com", List.of("Admin"));

        databaseHelper.addArticle(
            admin.getRoles(),
            "Test Article",
            "Public Test Article",
            "John Doe",
            "This is an abstract.",
            "This is a public abstract.",
            "This is the body of the article.",
            SkillLevel.BEGINNER,
            "TestGroup",
            AccessLevel.PUBLIC,
            new java.util.Date(),
            List.of(),
            List.of()
        );

        long articleId = databaseHelper.getLastInsertedArticleId();
        databaseHelper.deleteArticles(admin.getRoles(), articleId);

        assertFalse(databaseHelper.doesArticleExist(articleId, "Test Article"));
    }

    @Test
    public void testBackupArticlesTableAsAdmin() throws Exception {
        AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
                "Preferred", "admin@example.com", List.of("Admin"));

        databaseHelper.addArticle(
            admin.getRoles(),
            "Title1",
            "PublicTitle1",
            "Author1",
            "Abstract1",
            "PublicAbstract1",
            "Body1",
            SkillLevel.BEGINNER,
            "GroupID1",
            AccessLevel.PUBLIC,
            new java.util.Date(),
            List.of(),
            List.of()
        );

        databaseHelper.addArticle(
            admin.getRoles(),
            "Title2",
            "PublicTitle2",
            "Author2",
            "Abstract2",
            "PublicAbstract2",
            "Body2",
            SkillLevel.INTERMEDIATE,
            "GroupID2",
            AccessLevel.PUBLIC,
            new java.util.Date(),
            List.of(),
            List.of()
        );

        String backupFilePath = "articles_table_backup.csv";
        databaseHelper.backupArticlesTable(admin.getRoles(), backupFilePath);

        File backupFile = new File(backupFilePath);
        assertTrue(backupFile.exists());
    }

    @Test
    public void testRestoreWithOverwriteAsAdmin() throws Exception {
        AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
                "Preferred", "admin@example.com", List.of("Admin"));

        databaseHelper.addArticle(
            admin.getRoles(),
            "Existing Title",
            "Public Title",
            "Author",
            "Abstract",
            "Public Abstract",
            "Body",
            SkillLevel.BEGINNER,
            "GroupID",
            AccessLevel.PUBLIC,
            new java.util.Date(),
            List.of(),
            List.of()
        );

        String backupFilePath = "articles_table_backup.csv";
        databaseHelper.backupArticlesTable(admin.getRoles(), backupFilePath);
        databaseHelper.restoreArticlesTable(admin.getRoles(), backupFilePath, true);

        assertFalse(databaseHelper.doesArticleExist(1, "Existing Title"));
        long restoredId = databaseHelper.getArticleIdByTitle("Existing Title");
        assertTrue(databaseHelper.doesArticleExist(restoredId, "Existing Title"));
    }

    @Test
    public void testRestoreWithMergeAsAdmin() throws Exception {
        AdminClass admin = new AdminClass("adminUser", "password".toCharArray(), false, "First", "", "Last",
                "Preferred", "admin@example.com", List.of("Admin"));

        databaseHelper.addArticle(
            admin.getRoles(),
            "Existing Title 1",
            "Public Title 1",
            "Author 1",
            "Abstract 1",
            "Public Abstract 1",
            "Body 1",
            SkillLevel.BEGINNER,
            "GroupID1",
            AccessLevel.PUBLIC,
            new java.util.Date(),
            List.of(),
            List.of()
        );

        String backupFilePath = "articles_table_backup.csv";
        databaseHelper.backupArticlesTable(admin.getRoles(), backupFilePath);

        databaseHelper.addArticle(
            admin.getRoles(),
            "Another Existing Title",
            "Another Public Title",
            "Another Author",
            "Another Abstract",
            "Another Public Abstract",
            "Another Body",
            SkillLevel.EXPERT,
            "AnotherGroupID",
            AccessLevel.PUBLIC,
            new java.util.Date(),
            List.of(),
            List.of()
        );

        databaseHelper.restoreArticlesTable(admin.getRoles(), backupFilePath, false);

        long id1 = databaseHelper.getArticleIdByTitle("Existing Title 1");
        long id2 = databaseHelper.getArticleIdByTitle("Another Existing Title");

        assertTrue(databaseHelper.doesArticleExist(id1, "Existing Title 1"));
        assertTrue(databaseHelper.doesArticleExist(id2, "Another Existing Title"));
    }
}
