package src.CSE360App.GUI;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import src.CSE360App.AccessLevel;
import src.CSE360App.Article;
import src.CSE360App.DatabaseHelper;
import src.CSE360App.GroupManager;
import src.CSE360App.SkillLevel;
import src.CSE360App.UserGroups;
import src.CSE360App.GUI.Admin_Stuff.AdminInterface;
import src.CSE360App.GUI.Admin_Stuff.ModifyArticle;
import src.CSE360App.GUI.Admin_Stuff.backupInterface;
import src.CSE360App.GUI.Group_GUI.GroupInterface;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.control.ScrollPane;


public class ArticleInterface {

    // TEMP: Temporary array list of articles for GUI testing
    private ArrayList<Article> articles = new ArrayList<>();
    private ObservableList<Article> articleList = FXCollections.observableArrayList();
    private FilteredList<Article> filteredArticles;

    // Serves as the collection of data / attributes that go into an article (Allows
    // for easy formatting in GUI)
    private TextFlow contentArea = new TextFlow();

    private TextField search = new TextField();

    // Customizable window size for Article reader.
    private int width = 1000;
    private int height = 800;

    private Label groupFilter = new Label("Groups");
    private Label levelFilter = new Label("Level");

    private Label activeGroupLabel = new Label("Active Group: None"); // Default text
    private Label articleCountLabel = new Label("Articles by Level: B(0), I(0), A(0), E(0)");

    private Button settingsButton = new Button("Admin Settings");
    private Button deleteArticles = new Button("Delete Selected");
    private Button backupArticles = new Button("Backup selected");
    private Button signoutButton = new Button("Sign out");
    private Button addArticle = new Button("Add Article");
    private Button modifyArticle = new Button("Modify Selected Article");
    private Button GroupSettings = new Button("Group Settings");
    private Button sendGeneralQuestion = new Button("Send General Question");
    private Button sendSpecQuestion = new Button("Send Specific Question");
    private Button quitApplication = new Button("Quit Application");

    private MenuButton filterMenu = new MenuButton("Filters");
    private CheckMenuItem beginnerFilter = new CheckMenuItem("Beginner");
    private CheckMenuItem intermediateFilter = new CheckMenuItem("Intermediate");
    private CheckMenuItem advancedFilter = new CheckMenuItem("Advanced");
    private CheckMenuItem expertFilter = new CheckMenuItem("Expert");
    private CheckMenuItem allFilter = new CheckMenuItem("All");
    private TextField responseCap = new TextField("");

    /**
     * ArticleInterface: Window that shows all articles on left with title and
     * keywords, and displays selected article on reader on right side.
     *
     * @param primaryStage
     * @param role
     */
    public ArticleInterface(Stage primaryStage, String role) {

        // TEMP: Populates fake articles for sake of testing GUI
        Article.populateArticles(articles);
        ListView<Article> listView = new ListView<>(articleList);
        listView.setPrefWidth(width * .3);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // JavaFX array list that allows the use of "listeners" which interacts with UI
        articleList.addAll(articles);
        search.setPromptText("Search");

        // Wrap articleList in filterList, so we can utilize javaFX filter system.
        filteredArticles = new FilteredList<>(articleList, p -> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> applyFilters());

        beginnerFilter.setOnAction(e -> applyFilters());
        intermediateFilter.setOnAction(e -> applyFilters());
        advancedFilter.setOnAction(e -> applyFilters());
        expertFilter.setOnAction(e -> applyFilters());
        allFilter.setOnAction(e -> applyFilters());

        for (MenuItem item : filterMenu.getItems()) {
            if (item instanceof CheckMenuItem) {
                CheckMenuItem groupFilter = (CheckMenuItem) item;
                groupFilter.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    applyFilters();
                });
            }
        }

        // Sets the viewable list to the filtered articles.
        listView.setItems(filteredArticles);

        CustomMenuItem labelItem_groupFilter = new CustomMenuItem(groupFilter);
        CustomMenuItem labelItem_levelFilter = new CustomMenuItem(levelFilter);

        filterMenu.getItems().addAll(labelItem_levelFilter, beginnerFilter, intermediateFilter, advancedFilter, expertFilter, allFilter);
        allFilter.setSelected(true);

        if (role.contains("instructor")) {
            if (!GroupManager.getGroups().isEmpty()) {
                filterMenu.getItems().add(labelItem_groupFilter);
            }
            for (UserGroups groupToCheck : GroupManager.getGroups()) {
                CheckMenuItem addGroupFilter = new CheckMenuItem(groupToCheck.getGroupName());
                filterMenu.getItems().add(addGroupFilter);
            }
        }

        responseCap.setPromptText("Response Limit");
        CustomMenuItem responseCapFilter = new CustomMenuItem(responseCap, false);
        responseCap.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                int cap = Integer.parseInt(newValue);
                ObservableList<Article> limitedList = FXCollections.observableArrayList(articleList.subList(0, Math.min(cap, articleList.size())));
                listView.setItems(limitedList);
            } catch (NumberFormatException e) {
                System.out.println("Invalid response cap entered.");
            }
        });

        filterMenu.getItems().add(responseCapFilter);
        HBox filterAndSearchContainer = new HBox(10);
        filterAndSearchContainer.getChildren().addAll(activeGroupLabel, articleCountLabel, filterMenu);

        VBox articleListContainer = new VBox(10);
        articleListContainer.getChildren().addAll(filterAndSearchContainer, search, listView);
        Article.setColumnArticleAttributes(listView);

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && listView.getSelectionModel().getSelectedItems().size() == 1) {
                displayArticle(newValue);
            }
        });

        BorderPane layout = new BorderPane();
        layout.setPadding(new Insets(20));

        layout.setLeft(articleListContainer);

        VBox contentWrapper = new VBox(contentArea);
        contentWrapper.setAlignment(Pos.CENTER);
        contentArea.setTextAlignment(TextAlignment.CENTER);

        ScrollPane contentScrollPane = new ScrollPane(contentWrapper);
        contentScrollPane.setFitToWidth(true);
        contentScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        layout.setCenter(contentScrollPane);

        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER);

        if (role.equals("admin") || role.equals("student-admin")) {
            bottomBox.getChildren().addAll(deleteArticles, backupArticles, addArticle, modifyArticle, settingsButton, GroupSettings);
        } else if (role.equals("instructor")) {
            bottomBox.getChildren().addAll(deleteArticles, addArticle, modifyArticle, GroupSettings);
        } else {
            bottomBox.getChildren().addAll(sendSpecQuestion, sendGeneralQuestion);
        }

        bottomBox.getChildren().addAll(signoutButton, quitApplication);
        layout.setBottom(bottomBox);

        settingsButton.setOnAction(e -> {
            switch (role) {
                case "admin":
                case "student-admin":
                    AdminInterface adminInterface = new AdminInterface(primaryStage, role);
            }
        });

        deleteArticles.setOnAction(e -> {
            ObservableList<Article> selectedItems = listView.getSelectionModel().getSelectedItems();
            deleteArticles(selectedItems);
            updateListView();
        });

        modifyArticle.setOnAction(e -> {
            Article selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                ModifyArticle modifyArticle = new ModifyArticle(
                    primaryStage,
                    role,
                    selectedItem.getTitle(),
                    selectedItem.getAbstract(),
                    selectedItem.getBody(),
                    selectedItem.getKeywords(),
                    selectedItem.getLinks(),
                    selectedItem.getPublicTitle(),
                    selectedItem.getPublicAbstract(),
                    selectedItem.getGroupID(),
                    selectedItem.getSkillLevel(),
                    selectedItem.getAccessLevel() != null ? selectedItem.getAccessLevel() : AccessLevel.PUBLIC // Default to PUBLIC
                );
            }
        });

        modifyArticle.setOnAction(e -> {
            Article selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                ModifyArticle modifyArticle = new ModifyArticle(
                    primaryStage,
                    role,
                    selectedItem.getTitle(),
                    selectedItem.getAbstract(),
                    selectedItem.getBody(),
                    selectedItem.getKeywords(),
                    selectedItem.getLinks(),
                    selectedItem.getPublicTitle(),
                    selectedItem.getPublicAbstract(),
                    selectedItem.getGroupID(),
                    selectedItem.getSkillLevel(),
                    selectedItem.getAccessLevel()
                );
            }
        });

        addArticle.setOnAction(e -> {
            new ModifyArticle(primaryStage, role); // Open the ModifyArticle interface
        });
        
        signoutButton.setOnAction(e -> {
            System.out.println("Signing out...");
            try {
                new LoginInterface(primaryStage); // Replace LoginPage with your login page class
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        quitApplication.setOnAction(e -> {
            System.out.println("Quitting application...");
            Platform.exit(); // Gracefully shuts down the JavaFX application
        });
        
        GroupSettings.setOnAction(e -> {
            // Navigate to the group management interface
            new GroupInterface(primaryStage, role);
        });
        backupArticles.setOnAction(e -> {
            ObservableList<Article> selectedArticles = listView.getSelectionModel().getSelectedItems();

            if (selectedArticles.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Articles Selected");
                alert.setHeaderText(null);
                alert.setContentText("Please select one or more articles to back up.");
                alert.showAndWait();
                return;
            }

            try {
                // Connect to the database
                DatabaseHelper dbHelper = DatabaseHelper.getInstance();
                dbHelper.connectToDataBase();

                // Generate a backup file
                String backupFilePath = "articles_backup.csv";
                dbHelper.backupArticlesTable(List.of("Admin"), backupFilePath);

                // Provide feedback to the user
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Backup Successful");
                alert.setHeaderText(null);
                alert.setContentText("Selected articles have been backed up to: " + backupFilePath);
                alert.showAndWait();
            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Backup Failed");
                alert.setHeaderText(null);
                alert.setContentText("An error occurred while backing up the articles.");
                alert.showAndWait();
            }
        });

        

        Scene articleScene = new Scene(layout, width, height);
        primaryStage.setScene(articleScene);
        primaryStage.setTitle("Article Interface");
    }

    private void displayArticle(Article article) {
        contentArea.getChildren().clear();

        if (article != null) {
            Text titleText = new Text(article.getTitle() + "\n");
            titleText.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 48));

            Text headerText = new Text(article.getSkillLevel().toString() + "\n\n");
            headerText.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 12));

            Text descriptionText = new Text(article.getAbstract() + "\n\n");
            descriptionText.setFont(javafx.scene.text.Font.font("Arial", 12));

            Text keywordText = new Text(article.getKeywordsAsString() + "\n\n");
            keywordText.setFont(javafx.scene.text.Font.font("Arial", 12));

            Text linksText = new Text(article.getLinksAsString() + "\n\n");
            linksText.setFont(javafx.scene.text.Font.font("Arial", 12));

            Text bodyText = new Text(article.getBody());
            bodyText.setFont(javafx.scene.text.Font.font("Arial", 12));

            contentArea.getChildren().addAll(titleText, headerText, descriptionText, keywordText, linksText, bodyText);
        }
    }

    private void deleteArticles(ObservableList<Article> selected) {
        DatabaseHelper dbHelper = null;
        try {
            dbHelper = DatabaseHelper.getInstance();
            dbHelper.connectToDataBase();

            // Check if the table exists using DatabaseHelper
            if (!dbHelper.doesTableExist("cse360articles")) {
                System.err.println("Table 'cse360articles' does not exist in the database.");
                return;
            }

            for (Article article : selected) {
                if (article != null) {
                    try {
                        dbHelper.deleteArticleByTitle(article.getTitle());
                        articles.remove(article); // Remove from local list only if DB deletion succeeds
                        System.out.println("Article deleted: " + article.getTitle());
                    } catch (SQLException e) {
                        System.err.println("Error deleting article: " + article.getTitle());
                        e.printStackTrace();
                    }
                }
            }
            updateListView(); // Update the visible list
        } catch (SQLException e) {
            System.err.println("Error initializing DatabaseHelper: " + e.getMessage());
            e.printStackTrace();
        }
    }









    private void updateListView() {
        articleList.setAll(articles);
    }

    private void applyFilters() {
        filteredArticles.setPredicate(article -> {
            boolean matchesSearch = matchesSearch(article);
            boolean matchesLevel = matchesLevel(article);
            boolean matchesGroup = matchesGroup(article);
            return matchesSearch && matchesLevel && matchesGroup;
        });

        updateLabels();
    }

    private boolean matchesSearch(Article article) {
        String searchText = search.getText().toLowerCase();

        if (searchText.isEmpty()) {
            return true;
        }

        boolean matchesTitle = article.getTitle() != null && article.getTitle().toLowerCase().contains(searchText);
        boolean matchesAbstract = article.getAbstract() != null && article.getAbstract().toLowerCase().contains(searchText);
        boolean matchesAuthor = article.getAuthor() != null && article.getAuthor().toLowerCase().contains(searchText);

        return matchesTitle || matchesAbstract || matchesAuthor;
    }

    private boolean matchesLevel(Article article) {
        if (allFilter.isSelected()) {
            return true;
        }

        SkillLevel level = article.getSkillLevel();
        if (beginnerFilter.isSelected() && level == SkillLevel.BEGINNER) {
            return true;
        }
        if (intermediateFilter.isSelected() && level == SkillLevel.INTERMEDIATE) {
            return true;
        }
        if (advancedFilter.isSelected() && level == SkillLevel.ADVANCED) {
            return true;
        }
        if (expertFilter.isSelected() && level == SkillLevel.EXPERT) {
            return true;
        }

        return false;
    }

    private boolean matchesGroup(Article article) {
        List<Integer> selectedGroupIds = getSelectedGroupIds();

        if (selectedGroupIds.isEmpty()) {
            return true;
        }

        for (int groupId : selectedGroupIds) {
            UserGroups group = GroupManager.getGroupByID(groupId);
            if (group != null && group.getArticles().contains(article)) {
                return true;
            }
        }

        return false;
    }

    private List<Integer> getSelectedGroupIds() {
        List<Integer> selectedGroupIds = new ArrayList<>();

        for (MenuItem item : filterMenu.getItems()) {
            if (item instanceof CheckMenuItem) {
                CheckMenuItem checkItem = (CheckMenuItem) item;
                if (checkItem.isSelected()) {
                    Integer groupId = GroupManager.getGroupIdByName(checkItem.getText());
                    if (groupId != null) {
                        selectedGroupIds.add(groupId);
                    }
                }
            }
        }

        return selectedGroupIds;
    }

    private void updateLabels() {
        List<Integer> selectedGroupIds = getSelectedGroupIds();
        String activeGroup = selectedGroupIds.isEmpty() ? "None" : GroupManager.getGroupByID(selectedGroupIds.get(0)).getGroupName();

        int beginnerCount = 0, intermediateCount = 0, advancedCount = 0, expertCount = 0;
        for (Article article : filteredArticles) {
            switch (article.getSkillLevel()) {
                case BEGINNER:
                    beginnerCount++;
                    break;

                case INTERMEDIATE:
                    intermediateCount++;
                    break;

                case ADVANCED:
                    advancedCount++;
                    break;

                case EXPERT:
                    expertCount++;
                    break;
            }
        }

        activeGroupLabel.setText("Active Group: " + activeGroup);
        articleCountLabel.setText(String.format("Articles by Level: B(%d), I(%d), A(%d), E(%d)", beginnerCount, intermediateCount, advancedCount, expertCount));
    }
}