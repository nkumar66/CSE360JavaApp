package src.CSE360App.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import src.CSE360App.AccessLevel;
import src.CSE360App.Article;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;

/**
 * <p>
 * ArticleInterface Class
 * </p>
 * 
 * <p>
 * Description: The article interface class is used to display content to all
 * users and allow admins / instructors to customize content
 * 
 */
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

	// Button: Sends admin to settings page where they can backup, restore, add /
	// delete, etc.
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
		
		search.textProperty().addListener((observable, oldValue, newValue) -> {
		    applyFilters();
		});

		/***
		 * The code below calls and updates the filters everytime one is toggled.
		 */
		beginnerFilter.setOnAction(e -> applyFilters());
		intermediateFilter.setOnAction(e -> applyFilters());
		advancedFilter.setOnAction(e -> applyFilters());
		expertFilter.setOnAction(e -> applyFilters());
		allFilter.setOnAction(e -> applyFilters());

		for (MenuItem item : filterMenu.getItems()) {
		    if (item instanceof CheckMenuItem) {
		        CheckMenuItem groupFilter = (CheckMenuItem) item;
		        groupFilter.selectedProperty().addListener((observable, oldValue, newValue) -> {
		            applyFilters(); // Reapply filters when the group filter changes
		        });
		    }
		}

		//Sets the viewable list to the filtered articles.
		listView.setItems(filteredArticles);

		

		
		
		CustomMenuItem labelItem_groupFilter = new CustomMenuItem(groupFilter);
		CustomMenuItem labelItem_levelFilter = new CustomMenuItem(levelFilter);
		
		
		filterMenu.getItems().addAll(labelItem_levelFilter,beginnerFilter, intermediateFilter, advancedFilter, expertFilter, allFilter);
		allFilter.setSelected(true);
		
		//**ADD FILTER TO DB SYSTEM SO ONLY PEOPLE WITH ACESS CAN VIEW
		//** FOR NOW ONLY INSTRUCTORS AND ADMINS BUT MUST ADD PROPER PERMISSIONS
		if(role.contains("instructor")) {
			if(!GroupManager.getGroups().isEmpty()) {
			filterMenu.getItems().add(labelItem_groupFilter);
			}
		for(UserGroups groupToCheck : GroupManager.getGroups()) {
			 CheckMenuItem addGroupFilter = new CheckMenuItem(groupToCheck.getGroupName());
			filterMenu.getItems().add(addGroupFilter);
		}
		}
		
		// Limits the amount of responses allowed.
		responseCap.setPromptText("Response Limit");
		CustomMenuItem responseCapFilter = new CustomMenuItem(responseCap, false);
		responseCap.textProperty().addListener((observable, oldValue, newValue) -> {
		    try {
		        int cap = Integer.parseInt(newValue);
		        // Truncate articleList to the max response cap
		        ObservableList<Article> limitedList = FXCollections.observableArrayList(articleList.subList(0, Math.min(cap, articleList.size())));
		        listView.setItems(limitedList);
		    } catch (NumberFormatException e) {
		        // Handle invalid input (e.g., non-integer text)
		        System.out.println("Invalid response cap entered.");
		    }
		});

		filterMenu.getItems().add(responseCapFilter);
		HBox filterAndSearchContainer = new HBox(10);
		filterAndSearchContainer.getChildren().addAll(activeGroupLabel, articleCountLabel, filterMenu);
		

		VBox articleListContainer = new VBox(10);
		articleListContainer.getChildren().addAll(filterAndSearchContainer, search, listView);
		Article.setColumnArticleAttributes(listView);


		// The following section of code allows the ability to "select" articles by
		// clicking them, which calls display article method
		listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && listView.getSelectionModel().getSelectedItems().size() == 1) {
				displayArticle(newValue);

			}
		});

		// Uses border pane layout to utilize its modularity
		BorderPane layout = new BorderPane();
		layout.setPadding(new Insets(20));

		// **LEFT: Set article list on left side of border pane
		layout.setLeft(articleListContainer);

		// Wraps the content of an article so that it allows for easier, uniform
		// formatting
		VBox contentWrapper = new VBox(contentArea);
		contentWrapper.setAlignment(Pos.CENTER);
		contentArea.setTextAlignment(TextAlignment.CENTER);

		// ScrollPane: Allows users to scroll if content overflows given window size.
		ScrollPane contentScrollPane = new ScrollPane(contentWrapper);
		contentScrollPane.setFitToWidth(true);
		contentScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		layout.setCenter(contentScrollPane);

		// Box to format all content in the "BOTTOM" portion of BorderPane
		HBox bottomBox = new HBox(10);
		bottomBox.setAlignment(Pos.CENTER);

		// Shows role exclusive buttons.
		if (role.equals("admin") || role.equals("student-admin") ) {

			bottomBox.getChildren().addAll(deleteArticles, backupArticles, addArticle, modifyArticle, settingsButton, GroupSettings);
		} else if (role.equals("instructor")) {
			bottomBox.getChildren().addAll(deleteArticles,  addArticle, modifyArticle, GroupSettings);
		} else {
			bottomBox.getChildren().addAll(sendSpecQuestion, sendGeneralQuestion);
		}

		// Every user gets sign out option.
		bottomBox.getChildren().addAll(signoutButton,quitApplication);
		layout.setBottom(bottomBox);

		// If role allows, sends user to settings page.
		settingsButton.setOnAction(e -> {
			switch (role) {
			case "admin":
			case "student-admin":
				AdminInterface adminInterface = new AdminInterface(primaryStage, role);
			}
		});

		// If role allows, deletes selected articles.
		deleteArticles.setOnAction(e -> {
			ObservableList<Article> selectedItems = listView.getSelectionModel().getSelectedItems();
			deleteArticles(selectedItems);
			updateListView();
		});

		// If role allows, sends user to backup page.
		backupArticles.setOnAction(e -> {
			ObservableList<Article> selectedItems = listView.getSelectionModel().getSelectedItems();
			backupInterface bckupInterface = new backupInterface(primaryStage, role, selectedItems);
		});

		// If role allows, sends user to page to edit most recently clicked article.
		modifyArticle.setOnAction(e -> {
			Article selectedItem = listView.getSelectionModel().getSelectedItem();
			if (selectedItem != null) {
				ModifyArticle modifyArticle = new ModifyArticle(primaryStage, role, selectedItem.getTitle(),
						selectedItem.getAbstract(), selectedItem.getBody(), selectedItem.getKeywords(), selectedItem.getLinks());
			}
		});
		
		GroupSettings.setOnAction(e -> {
			GroupInterface groupInterface = new GroupInterface(primaryStage, role);
		});

		// If role allows, send user to page to create article.
		addArticle.setOnAction(e -> {
			ModifyArticle modifyArticle = new ModifyArticle(primaryStage, role);
		});

		// Signs user out.
		signoutButton.setOnAction(e -> {
			LoginInterface loginInterface = new LoginInterface(primaryStage);
		});
		
		sendGeneralQuestion.setOnAction(e -> {
			HelpInterface helpInterface = new HelpInterface(primaryStage, role, "general");
		});
		
		sendSpecQuestion.setOnAction(e -> {
			HelpInterface helpInterface = new HelpInterface(primaryStage, role, "spec");
		});
		
		quitApplication.setOnAction(e -> {
		    // Create the confirmation dialog
		    Alert confirmDialog = new Alert(AlertType.CONFIRMATION);
		    confirmDialog.setTitle("Exit Application");
		    confirmDialog.setHeaderText("Are you sure you want to close the application?");
		    confirmDialog.setContentText("All unsaved data will be lost.");

		    // Show the dialog and wait for the user's response
		    Optional<ButtonType> result = confirmDialog.showAndWait();
		    if (result.isPresent() && result.get() == ButtonType.OK) {
		        // Close the application
		        Platform.exit();
		    }
		    // If Cancel is clicked, do nothing
		});

		Scene articleScene = new Scene(layout, width, height);
//		articleScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		layout.getStyleClass().add("root");
		primaryStage.setScene(articleScene);
		primaryStage.setTitle("Article");
	}

	/**
	 * displayArticle: Function that formats and displays every attribute of an
	 * article in the article viewer.
	 * 
	 * @param article
	 */
	private void displayArticle(Article article) {
		contentArea.getChildren().clear();

		if (article != null) {
			// TITLE
			Text titleText = new Text(article.getTitle() + "\n");
			titleText.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 48));

			// HEADER / SKILL LEVEL
			Text headerText = new Text(article.getSkillLevel().toString() + "\n\n");
			headerText.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 12));

			// DESCRIPTION
			Text descriptionText = new Text(article.getAbstract() + "\n\n");
			descriptionText.setFont(javafx.scene.text.Font.font("Arial", 12));

			// KEYWORDS
			Text keywordText = new Text(article.getKeywordsAsString() + "\n\n");
			keywordText.setFont(javafx.scene.text.Font.font("Arial", 12));

			// LINKS
			Text linksText = new Text(article.getLinksAsString() + "\n\n");
			linksText.setFont(javafx.scene.text.Font.font("Arial", 12));

			// BODY
			Text bodyText = new Text(article.getBody());
			bodyText.setFont(javafx.scene.text.Font.font("Arial", 12));

			contentArea.getChildren().addAll(titleText, headerText, descriptionText, keywordText, linksText, bodyText);
		}
	}

	/***
	 * deleteArticles: Deletes articles that are selected by user / instructor.
	 * As-is: Just removes then from temporary array list system. NEEDS: Actual
	 * delete from DB logic.
	 * 
	 * @param selected
	 */
	private void deleteArticles(ObservableList<Article> selected) {
		for (Article article : selected) {

			if (article != null) {
//				System.out.printf("removing %s", article.getTitle());
				articles.remove(article);
			}
		}
	}

	/***
	 * updateListView: Just updates the visual component of the list.
	 */
	private void updateListView() {
		articleList.setAll(articles);
	}
	
	/***
	 * applyFilters: A hub method that checks each indvidual searchable / filterable component and returns result if it should be shown.
	 * It requires all 3 filters (search bar, filter and group) to match.
	 * 
	 */
	private void applyFilters() {
	    filteredArticles.setPredicate(article -> {
	    	
	        // Combine all active filters
	        boolean matchesSearch = matchesSearch(article);
	        boolean matchesLevel = matchesLevel(article);
	        boolean matchesGroup = matchesGroup(article);

	        // Article must satisfy all active filters
	        return matchesSearch && matchesLevel && matchesGroup;
	    });
	    
	    updateLabels();
	}


	
	/***
	 * matchesSearch: Compares title, abstract and authors string and returns any matches.
	 * 
	 * @param article
	 * @return
	 */
	private boolean matchesSearch(Article article) {
	    String searchText = search.getText().toLowerCase();

	    if (searchText.isEmpty()) {
	        return true; // No search filter applied
	    }

	    // Check against title, abstract, and author
	    boolean matchesTitle = article.getTitle() != null && article.getTitle().toLowerCase().contains(searchText);
	    boolean matchesAbstract = article.getAbstract() != null && article.getAbstract().toLowerCase().contains(searchText);
	    boolean matchesAuthor = article.getAuthor() != null && article.getAuthor().toLowerCase().contains(searchText);

	    return matchesTitle || matchesAbstract || matchesAuthor;
	}


	
	/***
	 * matchesLevel: Compares article level to the selected filter.
	 * 
	 * 
	 * @param article
	 * @return
	 */
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




	/***
	 * matchesGroup: Matches articles to group and returns the comparision.
	 * 				Uses helper method to compare the ids of the matched names to the groups
	 * 				If the group isn't null and it contains the current article, return true.
	 * @param article
	 * @return
	 */
	private boolean matchesGroup(Article article) {
	    // Get selected group IDs
	    List<Integer> selectedGroupIds = getSelectedGroupIds();

	    // If no groups are selected, consider it a match
	    if (selectedGroupIds.isEmpty()) {
	        return true;
	    }

	    // Check if the article belongs to any selected group
	    for (int groupId : selectedGroupIds) {
	        UserGroups group = GroupManager.getGroupByID(groupId);
	        if (group != null && group.getArticles().contains(article)) {
	            return true;
	        }
	    }

	    return false;
	}

	/***
	 * getSelectedGroupIds: Collects the IDs of all the groups that match the current filter.
	 * 
	 * @return
	 */
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
	
	/***
	 * UpdateLabels: Updates label that tracks the results of the filters and search
	 */
	private void updateLabels() {
	    // Get the active group
	    List<Integer> selectedGroupIds = getSelectedGroupIds();
	    String activeGroup = selectedGroupIds.isEmpty() ? "None" : GroupManager.getGroupByID(selectedGroupIds.get(0)).getGroupName();

	    // Count articles by skill level
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

	    // Update labels
	    activeGroupLabel.setText("Active Group: " + activeGroup);
	    articleCountLabel.setText(String.format(
	        "Articles by Level: B(%d), I(%d), A(%d), E(%d)",
	        beginnerCount, intermediateCount, advancedCount, expertCount
	    ));
	}














}
