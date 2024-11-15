package src.CSE360App.GUI;

import java.util.ArrayList;

import src.CSE360App.Article;
import src.CSE360App.SkillLevel;
import src.CSE360App.GUI.Admin_Stuff.AdminInterface;
import src.CSE360App.GUI.Admin_Stuff.ModifyArticle;
import src.CSE360App.GUI.Admin_Stuff.backupInterface;
import src.CSE360App.GUI.Group_GUI.GroupInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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

	// Serves as the collection of data / attributes that go into an article (Allows
	// for easy formatting in GUI)
	private TextFlow contentArea = new TextFlow();
	
	private TextField search = new TextField();

	// Customizable window size for Article reader.
	private int width = 1000;
	private int height = 800;

	// Button: Sends admin to settings page where they can backup, restore, add /
	// delete, etc.
	private Button settingsButton = new Button("Admin Settings");
	private Button deleteArticles = new Button("Delete Selected");
	private Button backupArticles = new Button("Backup selected");
	private Button signoutButton = new Button("Sign out");
	private Button addArticle = new Button("Add Article");
	private Button modifyArticle = new Button("Modify Selected Article");
	private Button GroupSettings = new Button("Group Settings");

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

		// JavaFX array list that allows the use of "listeners" which interacts with UI
		articleList.addAll(articles);
		search.setPromptText("Search");
		ListView<Article> listView = new ListView<>(articleList);
		listView.setPrefWidth(width * .3);
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		VBox articleListContainer = new VBox(10);
		articleListContainer.getChildren().addAll(search, listView);

		/***
		 * The following code snippet just allows the listview to display the title
		 * rather than its own generated value
		 */
		listView.setCellFactory(param -> new ListCell<Article>() {

			// Essentially just override what is displayed
			@Override
			protected void updateItem(Article item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getTitle()); // Only display the title of the Article
				}
			}
		});

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
		}

		// Every user gets sign out option.
		bottomBox.getChildren().addAll(signoutButton);
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

		Scene articleScene = new Scene(layout, width, height);
		articleScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		layout.getStyleClass().add("root");
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



}
